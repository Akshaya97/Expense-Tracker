package com.srm.expensetracker.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.srm.expensetracker.db.Database;
import com.srm.expensetracker.fragments.ExpenseListFragment;
import com.srm.expensetracker.R;
import com.srm.expensetracker.fragments.HomeFragment;
import com.srm.expensetracker.fragments.IncomeListFragment;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    private FloatingActionButton floating_action_button;
    private Boolean isExpenseActivity = false;
    private Boolean doubleBackToExitPressedOnce = false;
    private GoogleApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        floating_action_button = findViewById(R.id.floating_action_button);
        floating_action_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddEntryInputActivity();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        setHomeFragment();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setupGoogleClient();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
        } else {
            doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getString(R.string.press_back_again), Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            askLogoutConfirmation();
        } else if (id == R.id.action_reset) {
            askResetConfirmation();
        } else if (id == R.id.action_share) {
            showShareText();
        }

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home) {
            setHomeFragment();
        } else if (id == R.id.income_list) {
            setIncomeListFragment();
        } else if (id == R.id.expense_list) {
            setExpenseListFragment();
        } else if (id == R.id.share) {
            showShareText();
        }

        return true;
    }

    private void setHomeFragment() {
        toolbar.setTitle(R.string.home);
        floating_action_button.setVisibility(View.INVISIBLE);
        setFragmentToDrawer(new HomeFragment());
    }

    private void setIncomeListFragment() {
        toolbar.setTitle(R.string.income_list);
        floating_action_button.setVisibility(View.VISIBLE);
        isExpenseActivity = false;
        setFragmentToDrawer(new IncomeListFragment());
    }

    private void setExpenseListFragment() {
        toolbar.setTitle(R.string.expense_list);
        isExpenseActivity = true;
        floating_action_button.setVisibility(View.VISIBLE);
        setFragmentToDrawer(new ExpenseListFragment());
    }

    private void showShareText() {
        closeDrawer();

        Database db = new Database(this);
        Double totalExpense = db.getTotalExpense();
        Double totalIncome = db.getTotalIncome();
        Double netAmount = totalIncome - totalExpense;
        String shareText = "My current Dashboard!\n\nNet amount: " + netAmount.toString() + "\nTotal income: "
                + totalIncome.toString() + "\nTotal expense: " + totalExpense.toString();

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share_dashboard)));
    }

    private void askLogoutConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.logout)
                .setMessage(R.string.logout_description)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (!apiClient.isConnected()) {
                            setupGoogleClient();
                        }
                        Auth.GoogleSignInApi.signOut(apiClient).setResultCallback(
                                new ResultCallback<Status>() {
                                    @Override
                                    public void onResult(Status status) {
                                        startActivity(new Intent(NavigationActivity.this, LoginActivity.class));
                                        finish();
                                    }
                                });
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    private void askResetConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.reset_data)
                .setMessage(R.string.reset_description)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Database db = new Database(NavigationActivity.this);
                        db.clearData();
                        Toast.makeText(NavigationActivity.this,
                                getString(R.string.reset_success), Toast.LENGTH_SHORT).show();
                        setHomeFragment();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    private void setFragmentToDrawer(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, fragment);
        fragmentTransaction.commit();
        closeDrawer();
    }

    private void closeDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void showAddEntryInputActivity() {
        Intent intent = new Intent(this, EntryInputActivity.class);
        intent.putExtra("isExpense", isExpenseActivity);
        startActivity(intent);
    }

    private void setupGoogleClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        apiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        if (!apiClient.isConnected()) {
            apiClient.connect();
        }
    }
}

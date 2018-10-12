package com.srm.expensetracker.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
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

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    private void setFragmentToDrawer(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content, fragment);
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void showAddEntryInputActivity() {
        Intent intent = new Intent(this, EntryInputActivity.class);
        intent.putExtra("isExpense", isExpenseActivity);
        startActivity(intent);
    }
}

package com.example.a0lambj41.budgetplannerapp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflates menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    // On selecting action bar icons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        if(item.getItemId() == R.id.action_search)
        {
            // search action
            return true;
        }
        else if(item.getItemId() == R.id.action_notifications)
        {
            // Notifications
            // Call action example: LocationFound();
            return true;
        }
        else if(item.getItemId() == R.id.action_transactions)
        {
            // Transactions
            // Call action example: LocationFound();
            return true;
        }
        else if(item.getItemId() == R.id.action_analytics)
        {
            // Analytics
            return true;
        }
        else if(item.getItemId() == R.id.action_rate_us)
        {
            // Rate us
            return true;
        }
        return false;
    }
}

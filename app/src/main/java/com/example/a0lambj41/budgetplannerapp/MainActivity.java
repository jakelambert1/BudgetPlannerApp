package com.example.a0lambj41.budgetplannerapp;


import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton)findViewById(R.id.fab1);
        fab.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                new AlertDialog.Builder(MainActivity.this).setPositiveButton("OK", null).
                        setMessage("The FloatingActionButton was clicked!").show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflates menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView sv = (SearchView) item.getActionView();
        sv.setOnQueryTextListener(new SearchHandler());
        return true;
    }

    // ASK NICK WHY THE SEARCH HANDLER IS NOT WORKING
    class SearchHandler implements SearchView.OnQueryTextListener {


        public SearchHandler(){

        }

        public boolean onQueryTextChange(String txt) {
            // do nothing... (this method runs when the user types a new character)
            return true;
        }

        public boolean onQueryTextSubmit(String txt) {
            // show the search text in an alert dialog
            new AlertDialog.Builder(MainActivity.this).setPositiveButton("OK", null).
                    setMessage(txt).show();
            return true;
        }
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

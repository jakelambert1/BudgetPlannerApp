package com.example.a0lambj41.budgetplannerapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    FloatingActionButton fab;
    private DrawerLayout drawer;

    EditText transaction, description, amount;
    TextView textView;
    MyHelper controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);


        // Navigation drawer
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Listen to click events on navigation view
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                navigationView.setNavigationItemSelectedListener(this);

        // Connect the drawer & toolbar views
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#da4747"));

        Bitmap bg = Bitmap.createBitmap(480,800, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bg);
        canvas.drawRect(50,50,200,200,paint);

        LinearLayout ll = (LinearLayout) findViewById(R.id.rectangle);
        ll.setBackground(new BitmapDrawable(bg));

    }

    public void btn_click(View view) {

        // SQLite g
        transaction = (EditText) findViewById(R.id.etNewTransaction);
        description = (EditText) findViewById(R.id.etNewDescription);
        amount = (EditText) findViewById(R.id.etNewAmount);
        textView = (TextView) findViewById(R.id.listText);

        controller = new MyHelper(this, "", null, 1);

        // Add array to spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this, R.array.spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // Floating action button
        fab = (FloatingActionButton) findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try{
                    controller.insert_transaction(transaction.getText().toString(),
                            description.getText().toString(), amount.getText().toString());
                }catch (SQLiteException e){
                    Toast.makeText(MainActivity.this, "ALREADY EXISTS", Toast.LENGTH_SHORT).show();
                }
            }
        });


        switch (view.getId()){
            case R.id.btnUpdate:
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("ENTER NEW AMOUNT");

                final EditText new_description = new EditText(this);
                dialog.setView(new_description);

                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        controller.update_transaction(description.getText().toString(),new_description.getText().toString());
                    }
                });
                dialog.show();
                break;
            case R.id.btnDelete:
                controller.delete_transaction(description.getText().toString());
                break;
            case R.id.btnView:
                controller.list_transactions(textView);
                break;
        }
    }


    // Spinner select listener
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    // Close navigation drawer without leaving activity
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.nav_dashboard:
                Intent intent = new Intent(this, MainActivity.class);
                startActivityForResult(intent, 0);
                return true;
            case R.id.nav_transactions:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TransactionsFragment()).commit();
            break;
            case R.id.nav_months:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MonthsFragment()).commit();
                break;
            case R.id.nav_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    // Handles the SearchView
    class SearchHandler implements SearchView.OnQueryTextListener {

        public SearchHandler() {

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
        if (item.getItemId() == R.id.action_search) {
            // search action
            return true;

        } else if (item.getItemId() == R.id.action_notifications) {
            // Notifications
            // Call action example: LocationFound();
            return true;
        } else if (item.getItemId() == R.id.action_transactions) {
            // Transactions
            // Call action example: LocationFound();
            return true;
        } else if (item.getItemId() == R.id.action_analytics) {
            // Analytics
            return true;
        } else if (item.getItemId() == R.id.action_rate_us) {
            // Rate us
            return true;
        }
        return false;
    }

}

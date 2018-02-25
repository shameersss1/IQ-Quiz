package com.androidbelieve.islamicquiz;


import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;


    Button b1,b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;



        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                if(menuItem.getItemId() == R.id.home)
                {
                    Intent i = new Intent(Main2Activity.this,Post.class);
                    startActivity(i);
                }

                if (menuItem.getItemId() == R.id.notify) {
                    Intent i =new Intent(Main2Activity.this,temp.class);
                    startActivity(i);

                }

                if (menuItem.getItemId() == R.id.feedback) {
                    Intent i =new Intent(Main2Activity.this,FeedBack.class);
                    startActivity(i);
                }


                if (menuItem.getItemId() == R.id.contact) {
                    Intent i =new Intent(Main2Activity.this,ContactUs.class);
                    startActivity(i);
                }
                if(menuItem.getItemId() ==R.id.inbox)
                {
                    Intent i = new Intent(Main2Activity.this,Inbox.class);
                    startActivity(i);
                }

                if(menuItem.getItemId() == R.id.pro)
                {
                    Intent i = new Intent(Main2Activity.this,UserProfile.class);
                    startActivity(i);
                }
                return false;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();








        setTitle("Mark Sheet");

        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Main2Activity.this,Mark.class);
                startActivity(i);
            }
        });




        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(Main2Activity.this,ScoreHistory.class);
                startActivity(i1);
            }
        });
    }
}

package com.androidbelieve.islamicquiz;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

    private EditText ephone;
    private EditText epassword;
    public static String PREFS_NAME="mypre";
    public static String PREF_USERNAME="username";
    public static String PREF_PASSWORD="password";
    public static String filename = "Valustoringfile";
    public static final String USER_NAME = "USERNAME";


    LoginDataBaseAdapter loginDataBaseAdapter;

    Intent i,i1;

   Button b2,b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button);





        i = new Intent(this,Register.class);
        i1 = new Intent(this,Forgot.class);






//            else {
//            SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
//            if (pref.getBoolean("activity_executed", false)) {
//                Intent intent = new Intent(this, Post.class);
//                startActivity(intent);
//                finish();
//            } else {
//                SharedPreferences.Editor ed = pref.edit();
//                ed.putBoolean("activity_executed", true);
//                ed.commit();
//            }
//        }


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(i1);
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
                startActivity(i);
            }
        });
    }










    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.androidbelieve.islamicquiz;


import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class temp extends PreferenceActivity implements TimePickerDialog.OnTimeSetListener{

    String temp[] = new String[10];
    String delimiter = ":";
    String temp1[] = new String[10];
    String temp2[] = new String[10];

    int i=0;

    Button b1;

    String t1,t2,t3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Settings");




        addPreferencesFromResource(R.xml.pref);

        PreferenceManager preferenceManager = getPreferenceManager();
        if (preferenceManager.getSharedPreferences().getBoolean("checkBox", true)) {
            Toast.makeText(getApplicationContext(), "Remainder is ON", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Remainder is OFF", Toast.LENGTH_SHORT).show();
        }

        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);
         t1 =  prefs.getString("R1", "10:0");
         t2 =  prefs.getString("R2", "17:0");
         t3 =  prefs.getString("R3", "22:0");







        Preference btnDateFilter = (Preference) findPreference("btnDateFilter1");



        btnDateFilter.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                i=1;
                showDateDialog();

                return false;
            }
        });
        Preference btnDateFilter2 = (Preference) findPreference("btnDateFilter2");
        btnDateFilter2.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                i=2;
                showDateDialog();

                return false;
            }
        });

        Preference btnDateFilter3 = (Preference) findPreference("btnDateFilter3");
        btnDateFilter3.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                i=3;
                showDateDialog();

                return false;
            }
        });


        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(temp.this);


        int ho1 = pref.getInt("h1",10);
        int mi1 = pref.getInt("m1",0);

        int ho2 = pref.getInt("h2",17);
        int mi2 = pref.getInt("m2",0);


        int ho3 = pref.getInt("h3",22);
        int mi3 = pref.getInt("m3",0);

        btnDateFilter.setSummary("Current Reminder is set at " + ho1+" : "+mi1);
        btnDateFilter2.setSummary("Current Reminder is set at " + ho2+" : "+mi2);
        btnDateFilter3.setSummary("Current Reminder is set at " + ho3+" : "+mi3);



    }
    private void showDateDialog(){
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

     new TimePickerDialog(this,this,hour,minute,false).show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


        String h = String.valueOf(hourOfDay);
        String m = String.valueOf(minute);

        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);


        SharedPreferences.Editor editor = prefs.edit();

        if(i==1)
        {
        editor.putInt("h1",hourOfDay);
        editor.putInt("m1",minute);
        editor.apply();
        }
        else if(i==2)
        {
            editor.putInt("h2",hourOfDay);
            editor.putInt("m2",minute);
            editor.apply();
        }

        if(i==3)
        {
            editor.putInt("h3",hourOfDay);
            editor.putInt("m3",minute);
            editor.apply();
        }
        Intent i = new Intent(getApplicationContext(), com.androidbelieve.islamicquiz.temp.class);
        startActivity(i);
    }
    @Override
    public void onBackPressed()
    {
        Intent i =new Intent(temp.this,Post.class);
        finish();
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_temp, menu);
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

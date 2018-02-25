package com.androidbelieve.islamicquiz;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseInstallation;
import com.startapp.android.publish.StartAppSDK;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Post extends AppCompatActivity{
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    ImageButton b1,b2,b3,b4;
    final static int RQS_1=1;
    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    String t1,t2,t3,date,t;
    String temp[] = new String[10];
    String delimiter = ":";
    String temp1[] = new String[10];
    String temp2[] = new String[10];

    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_post);
        b1 = (ImageButton) findViewById(R.id.imageButton);
        b2 = (ImageButton) findViewById(R.id.imageButton2);
        b3 = (ImageButton) findViewById(R.id.imageButton3);
        b4 = (ImageButton) findViewById(R.id.imageButton4);







        SharedPreferences pref12 = PreferenceManager
                .getDefaultSharedPreferences(Post.this);
        String ads = pref12.getString("ads", "");

        int l=ads.length();

        ads = ads.substring(1, l);



        TextView tv = (TextView) findViewById(R.id.textView8);

        tv.setText(ads);


        phone = pref12.getString("phone", "");


        if(phone==null)
        {
            Intent i =new Intent(this,MainActivity.class);
            startActivity(i);
        }




        boolean checkBox = pref12.getBoolean("checkBox", true);

        t= String.valueOf(checkBox);


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        date = dateFormat.format(new Date(System.currentTimeMillis()));


        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);





        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Post.this, Question.class);
                startActivity(i);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Post.this,anscheck.class);
                startActivity(i);
            }
        });


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Post.this, QHISTORY.class);
                startActivity(i);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Post.this, Main2Activity.class);
                startActivity(i);
            }
        });

        /**
         *Setup the DrawerLayout and NavigationView
         */

             mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
             mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */

             mFragmentManager = getSupportFragmentManager();
             mFragmentTransaction = mFragmentManager.beginTransaction();
             //mFragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
        /**
         * Setup click events on the Navigation View Items.
         */

             mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                 if(menuItem.getItemId() == R.id.home)
                 {
                     Intent i = new Intent(Post.this,Post.class);
                     startActivity(i);
                 }

                 if (menuItem.getItemId() == R.id.notify) {
                     Intent i =new Intent(Post.this,temp.class);
                     startActivity(i);

                 }

                if (menuItem.getItemId() == R.id.feedback) {
                    Intent i =new Intent(Post.this,FeedBack.class);
                    startActivity(i);
                }

                 if (menuItem.getItemId() == R.id.contact) {
                     Intent i =new Intent(Post.this,ContactUs.class);
                     startActivity(i);
                 }
                 if(menuItem.getItemId() ==R.id.inbox)
                 {
                     Intent i = new Intent(Post.this,Inbox.class);
                     startActivity(i);
                 }

                 if(menuItem.getItemId() == R.id.pro)
                 {
                     Intent i = new Intent(Post.this,UserProfile.class);
                     startActivity(i);
                 }
                 if(menuItem.getItemId() == R.id.web)
                 {
                     Intent i = new Intent(Post.this,Web.class);
                     startActivity(i);
                 }
                 if(menuItem.getItemId()==R.id.chat)
                 {
                     Intent i = new Intent(Post.this,LoginActivity.class);
                     startActivity(i);
                 }
                 if(menuItem.getItemId()==R.id.help)
                 {
                     Intent i = new Intent(Post.this,Winner.class);
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


        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(Post.this);
        t1 =  pref.getString("R1", "10:0");
        t2 =  pref.getString("R2", "17:0");
        t3 =  pref.getString("R3", "22:0");

        int ho1 = pref.getInt("h1",10);
        int mi1 = pref.getInt("m1",0);

        int ho2 = pref.getInt("h2",17);
        int mi2 = pref.getInt("m2",0);


        int ho3 = pref.getInt("h3",22);
        int mi3 = pref.getInt("m3",0);


        temp = t1.split(delimiter);
        temp1= t2.split(delimiter);
        temp2=t3.split(delimiter);

        int h1 = Integer.parseInt(temp[0]);
        int m1 = Integer.parseInt(temp[1]);

        int h2 = Integer.parseInt(temp1[0]);
        int m2 = Integer.parseInt(temp1[1]);

        int h3 = Integer.parseInt(temp2[0]);
        int m3 = Integer.parseInt(temp2[1]);

        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();

        calSet.set(Calendar.HOUR_OF_DAY, ho1);
        calSet.set(Calendar.MINUTE,mi1);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        if(calSet.compareTo(calNow) <= 0){
            //Today Set time passed, count to tomorrow
            calSet.add(Calendar.DATE, 1);
        }

        setAlarm(calSet,1);


        Calendar calNow1 = Calendar.getInstance();
        Calendar calSet1 = (Calendar) calNow1.clone();

        calSet1.set(Calendar.HOUR_OF_DAY, ho2);
        calSet1.set(Calendar.MINUTE, mi2);
        calSet1.set(Calendar.SECOND, 0);
        calSet1.set(Calendar.MILLISECOND, 0);

        if(calSet1.compareTo(calNow1) <= 0){
            //Today Set time passed, count to tomorrow
            calSet1.add(Calendar.DATE, 1);
        }

        setAlarm(calSet1,2);



        Calendar calNow2 = Calendar.getInstance();
        Calendar calSet2 = (Calendar) calNow2.clone();

        calSet2.set(Calendar.HOUR_OF_DAY, ho3);
        calSet2.set(Calendar.MINUTE, mi3);
        calSet2.set(Calendar.SECOND, 0);
        calSet2.set(Calendar.MILLISECOND, 0);

        if(calSet2.compareTo(calNow) <= 0){
            //Today Set time passed, count to tomorrow
            calSet2.add(Calendar.DATE, 1);
        }

        setAlarm(calSet2, 3);


        Calendar calNow3 = Calendar.getInstance();
        Calendar calSet3 = (Calendar) calNow3.clone();

        calSet3.set(Calendar.HOUR_OF_DAY, 23);
        calSet3.set(Calendar.MINUTE, 0);
        calSet3.set(Calendar.SECOND, 0);
        calSet3.set(Calendar.MILLISECOND, 0);

        if(calSet3.compareTo(calNow) <= 0){
            //Today Set time passed, count to tomorrow
            calSet3.add(Calendar.DATE, 1);
        }

        setAlarm1(calSet3, 4);




        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = preferences.getString("date", "");


        if(t.equals("false"))
        {

            for(int i=1;i<=3;i++)
            {
                Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(getBaseContext(), i, intent, 0);
                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);

            }





        }

       else if(name.equals(date))
        {
            calSet.add(Calendar.DATE, 1);
            setAlarm(calSet,1);

            calSet1.add(Calendar.DATE, 1);
            setAlarm(calSet1,2);

            calSet2.add(Calendar.DATE, 1);
            setAlarm(calSet2, 3);

            calSet3.add(Calendar.DATE, 1);
            setAlarm1(calSet3, 4);
        }

    }

    private void setAlarm(Calendar targetCal,int RQS_1){




        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
//                1000 * 60 * 20, pendingIntent);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

    }
    private void setAlarm1(Calendar targetCal,int RQS_1){




        Intent intent = new Intent(getBaseContext(), AlarmReceiver1.class);
        pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
//                1000 * 60 * 20, pendingIntent);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

    }


}
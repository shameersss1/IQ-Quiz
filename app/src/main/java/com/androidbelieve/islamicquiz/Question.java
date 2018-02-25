package com.androidbelieve.islamicquiz;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.ClipboardManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Question extends ActionBarActivity
{

    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;
    String myJSON;
    private static final String TAG_RESULTS="result";
    private static final String TAG_NAME = "date";
    private static final String TAG_ADD ="phone";

    Intent p;


    String sender;
    String result = null;
    String phone;
    Button b1,bo;
    private ClipboardManager myClipboard;
    private ClipData myClip;

    JSONArray peoples = null;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;

    ArrayList<HashMap<String, String>> personList;

    ListView list;
    Button b;
    String dayOfTheWeek;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        list = (ListView) findViewById(R.id.listView);
        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
//        AdView mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
//
//        SharedPreferences pref12 = PreferenceManager
//                .getDefaultSharedPreferences(Question.this);
//        String ads = pref12.getString("ads", "");
//
//        ads = ads.substring(0, 1);

 bo = (Button) findViewById(R.id.Button63);
        bo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Question.this,Answer.class);
                startActivity(i);
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
       dayOfTheWeek = sdf.format(d);




//        if(ads.equals("h")) {
//            mAdView.setVisibility(View.GONE);
//        }


        p=new Intent(this,Answer.class);

        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(Question.this);
        phone = pref.getString("phone", "");

        personList = new ArrayList<HashMap<String,String>>();
        login(phone);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;



        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                if(menuItem.getItemId() == R.id.home)
                {
                    Intent i = new Intent(Question.this,Post.class);
                    startActivity(i);
                }

                if (menuItem.getItemId() == R.id.notify) {
                    Intent i =new Intent(Question.this,temp.class);
                    startActivity(i);

                }

                if (menuItem.getItemId() == R.id.feedback) {
                    Intent i =new Intent(Question.this,FeedBack.class);
                    startActivity(i);
                }


                if (menuItem.getItemId() == R.id.contact) {
                    Intent i =new Intent(Question.this,ContactUs.class);
                    startActivity(i);
                }
                if(menuItem.getItemId() ==R.id.inbox)
                {
                    Intent i = new Intent(Question.this,Inbox.class);
                    startActivity(i);
                }

                if(menuItem.getItemId() == R.id.pro)
                {
                    Intent i = new Intent(Question.this,UserProfile.class);
                    startActivity(i);
                }
                return false;
            }

        });



        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();






    }



    protected void showList()
    {
        try
        {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<peoples.length();i++)
            {
                JSONObject c = peoples.getJSONObject(i);

                String date = c.getString(TAG_NAME);
                phone = c.getString(TAG_ADD);

                HashMap<String,String> persons = new HashMap<String,String>();

                persons.put(TAG_NAME,date);
                persons.put(TAG_ADD,phone);

                personList.add(persons);
            }
            ListAdapter adapter = new SimpleAdapter(Question.this, personList, R.layout.ll22,
                    new String[]{TAG_NAME,TAG_ADD},
                    new int[]{R.id.date, R.id.phone}

            );

            list.setAdapter(adapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String sel = list.getItemAtPosition(position).toString();


                    //startActivity(p);

                }
            });




        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void login(final String phone) {

        class LoginAsync extends AsyncTask<String, Void, String>{

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(Question.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String uname = params[0];



                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("phone", uname));


                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://islamquiz.in/Android/User/check.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                }  catch (IOException e) {
                    result = "1";
                }
                return result;
            }


            @Override
            protected void onPostExecute(String result){
                String s = result.trim();
                loadingDialog.dismiss();
                if(s.equals("NO")){

                    bo.setVisibility(View.GONE);


                    if(dayOfTheWeek.equals("Sunday"))
                    {
                        Toast.makeText(getApplicationContext(), "ஞாயிறு அன்று கேள்வி கிடையாது", Toast.LENGTH_LONG).show();
                    }
                    else{
                    Toast.makeText(getApplicationContext(), "இன்னும் கேள்வி ரெடியாகவில்லை", Toast.LENGTH_LONG).show();
                }}


                else if (s.equals("1"))
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            Question.this).create();

                    // Setting Dialog Title
                    alertDialog.setTitle("Alert");

                    // Setting Dialog Message
                    alertDialog.setMessage("Check Your Internet Connection And Restart The App ");


                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();
                }

                else {
                    myJSON = result;
                    showList();

                }
            }
        }
        LoginAsync g = new LoginAsync();
        g.execute(phone);
    }


    @Override
    public void onBackPressed()
    {
//        if (getIntent().getBooleanExtra("EXIT", false)) {
        finish();

        //return;
        // }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

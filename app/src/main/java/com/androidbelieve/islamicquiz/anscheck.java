package com.androidbelieve.islamicquiz;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class anscheck extends ActionBarActivity {

    String myJSON;
    private static final String TAG_RESULTS="result";
    private static final String TAG_NAME = "date";
    private static final String TAG_ADD ="question";
    private static final String TAG_SCORE ="answer";

    String sender,phone;

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;

    JSONArray peoples = null;
    LoginDataBaseAdapter loginDataBaseAdapter;

    ArrayList<HashMap<String, String>> personList;
    Common activity;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anscheck);
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(anscheck.this);
        phone = pref.getString("phone","");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();


//activity.pass("User History");

        StrictMode.setThreadPolicy(policy);
        list = (ListView) findViewById(R.id.listView);

        personList = new ArrayList<HashMap<String,String>>();
        login(phone);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */


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
                    Intent i = new Intent(anscheck.this,Post.class);
                    startActivity(i);
                }

                if (menuItem.getItemId() == R.id.notify) {
                    Intent i =new Intent(anscheck.this,temp.class);
                    startActivity(i);

                }

                if (menuItem.getItemId() == R.id.feedback) {
                    Intent i =new Intent(anscheck.this,FeedBack.class);
                    startActivity(i);
                }


                if (menuItem.getItemId() == R.id.contact) {
                    Intent i =new Intent(anscheck.this,ContactUs.class);
                    startActivity(i);
                }
                if(menuItem.getItemId() ==R.id.inbox)
                {
                    Intent i = new Intent(anscheck.this,Inbox.class);
                    startActivity(i);
                }

                if(menuItem.getItemId() == R.id.pro)
                {
                    Intent i = new Intent(anscheck .this,UserProfile.class);
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

                String name = c.getString(TAG_NAME);
                String phone = c.getString(TAG_ADD);
                String score = c.getString(TAG_SCORE);
                HashMap<String,String> persons = new HashMap<String,String>();

                persons.put(TAG_NAME,name);
                persons.put(TAG_ADD,phone);
                persons.put(TAG_SCORE,score);
                personList.add(persons);
            }
            ListAdapter adapter = new SimpleAdapter(anscheck.this, personList, R.layout.l3,
                    new String[]{TAG_NAME,TAG_ADD,TAG_SCORE},
                    new int[]{R.id.date, R.id.question,R.id.answer}
            );

            list.setAdapter(adapter);


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
                loadingDialog = ProgressDialog.show(anscheck.this, "Please wait", "Loading...");
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
                    HttpPost httpPost = new HttpPost("http://islamquiz.in/Android/User/iq_qhistory1.php");
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
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){

                loadingDialog.dismiss();
                String s=result.trim();

                     //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                    myJSON=result;
                    showList();

            }
        }
        LoginAsync g = new LoginAsync();
        g.execute(phone);
    }
    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(anscheck.this,Post.class);
        finish();
        startActivity(i);

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

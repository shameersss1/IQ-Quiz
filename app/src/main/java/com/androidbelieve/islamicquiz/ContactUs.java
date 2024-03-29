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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ContactUs extends ActionBarActivity {


    Button b1;
    RadioGroup rg,rg1;
    RadioButton rb,rb1;

    String url ="http://islamquiz.in/Android/User/contactus.php";

    String answer,date,phone;

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;

    EditText et;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);





        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(ContactUs.this);
        phone = pref.getString("phone", "");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        date = dateFormat.format(new Date(System.currentTimeMillis()));



        et = (EditText) findViewById(R.id.editText);

        b1 = (Button) findViewById(R.id.button);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer = et.getText().toString();
                login(phone, answer);


            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;



        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                if(menuItem.getItemId() == R.id.home)
                {
                    Intent i = new Intent(ContactUs.this,Post.class);
                    startActivity(i);
                }

                if (menuItem.getItemId() == R.id.notify) {
                    Intent i =new Intent(ContactUs.this,temp.class);
                    startActivity(i);

                }

                if (menuItem.getItemId() == R.id.feedback) {
                    Intent i =new Intent(ContactUs.this,FeedBack.class);
                    startActivity(i);
                }


                if (menuItem.getItemId() == R.id.contact) {
                    Intent i =new Intent(ContactUs.this,ContactUs.class);
                    startActivity(i);
                }
                if(menuItem.getItemId() ==R.id.inbox)
                {
                    Intent i = new Intent(ContactUs.this,Inbox.class);
                    startActivity(i);
                }

                if(menuItem.getItemId() == R.id.pro)
                {
                    Intent i = new Intent(ContactUs.this,UserProfile.class);
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











    private void login(final String phone, String answer) {

        class LoginAsync extends AsyncTask<String, Void, String>{

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(ContactUs.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String uname = params[0];
                String pass = params[1];


                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("phone", uname));
                nameValuePairs.add(new BasicNameValuePair("answer", pass));

                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(url);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));

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
                String s = result.trim();
                loadingDialog.dismiss();
                if(s.equals("no")){
                    Toast.makeText(getApplicationContext(), "Error Try Again", Toast.LENGTH_LONG).show();

                }




                else {
                    Toast.makeText(getApplicationContext(), "Successfully Posted", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ContactUs.this,Post.class);
                    finish();
                    startActivity(i);


                }




            }
        }

        LoginAsync la = new LoginAsync();
        la.execute(phone,answer);

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













































































































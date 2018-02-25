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


public class Answer extends ActionBarActivity {


    Button b1;
    RadioGroup rg,rg1;
    RadioButton rb,rb1;

    String loop;

    String url ="http://islamquiz.in/Android/User/answer1.php";

    String answer,date,phone;
    LoginDataBaseAdapter loginDataBaseAdapter;

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    Common activity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        rg = (RadioGroup) findViewById(R.id.radio);
        rg1 = (RadioGroup) findViewById(R.id.radio1);



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(Answer.this);
        phone = pref.getString("phone", "");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        date = dateFormat.format(new Date(System.currentTimeMillis()));

        if(phone==null)
        {
            Intent i =new Intent(this,MainActivity.class);
            startActivity(i);
        }

        String name = pref.getString("date", "");




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
                    Intent i = new Intent(Answer.this,Post.class);
                    startActivity(i);
                }

                if (menuItem.getItemId() == R.id.notify) {
                    Intent i =new Intent(Answer.this,temp.class);
                    startActivity(i);

                }

                if (menuItem.getItemId() == R.id.feedback) {
                    Intent i =new Intent(Answer.this,FeedBack.class);
                    startActivity(i);
                }

                if (menuItem.getItemId() == R.id.contact) {
                    Toast.makeText(getApplicationContext(),"OK",Toast.LENGTH_LONG).show();
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



    public void invokeLogin(View view){

        int l = rg1.getCheckedRadioButtonId();



        int selectedId=rg.getCheckedRadioButtonId();


if(l==-1 || selectedId==-1)
{
    Toast.makeText(getApplicationContext(),"Choose Any Of the Option",Toast.LENGTH_LONG).show();
}
        else
{
            rb1 = (RadioButton) findViewById(l);

           loop= rb1.getText().toString();

            rb=(RadioButton)findViewById(selectedId);

            answer = rb.getText().toString();



    if(l==2131558515)
{
    Toast.makeText(getApplicationContext(),"You Should recite & Read the Daily Aayats before you submit answer",Toast.LENGTH_LONG).show();

}
    else


    {

        login(phone,answer);
}

}




    }

    private void login(final String phone, String answer) {

        class LoginAsync extends AsyncTask<String, Void, String>{

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(Answer.this, "Please wait", "Loading...");
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
                String s = result.trim();
                loadingDialog.dismiss();
                if(s.equals("no")){
                    Toast.makeText(getApplicationContext(), "Error Today's Answer Already Posted", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Answer.this,Post.class);
                    finish();
                    startActivity(i);
                }




                else if(s.equals("yes")) {

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Answer.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("date",date);
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "Successfully Posted", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Answer.this,anscheck.class);
                    finish();
                    startActivity(i);


                }

                else if(s.equals("n"))
                {
                    Toast.makeText(getApplicationContext(), "Error..! Try Again", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Answer.this,anscheck.class);
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













































































































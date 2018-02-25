package com.androidbelieve.islamicquiz;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Splash extends Activity {

    ProgressBar bar;
    String t="4.1.1",phone="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //StartAppSDK.init(this, "201132527", true);
       // StartAppAd.showSplash(this, savedInstanceState);
        setContentView(R.layout.activity_splash);


        bar = (ProgressBar) this.findViewById(R.id.progressBar);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //boolean checkBox = prefs.getBoolean("checkBox", true);

        //t= String.valueOf(checkBox);
        phone = prefs.getString("phone", "");



        login(phone, t);

    }


    private void login(final String phone, String answer) {

        class LoginAsync extends AsyncTask<String, Void, String>{

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                bar.setVisibility(View.VISIBLE);

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
                    HttpPost httpPost = new HttpPost("http://islamquiz.in/Android/User/ads.php");
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
                }catch (IOException e) {
                    result = "1";


                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){

                if (result.equals("1")) {

                    AlertDialog alertDialog = new AlertDialog.Builder(
                            Splash.this).create();

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


                } else {


                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Splash.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("ads",result);
                    editor.apply();
                    bar.setVisibility(View.GONE);




                    String a = preferences.getString("skip","");



                    if(a.equals("yes"))
                    {
                        Intent i = new Intent(Splash.this,Post.class);
                        finish();
                        startActivity(i);
                    }
                    else {
                        Intent i = new Intent(Splash.this, MainActivity.class);
                        Splash.this.finish();


                        startActivity(i);

                    }

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

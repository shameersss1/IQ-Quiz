package com.androidbelieve.islamicquiz;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.ArrayList;
import java.util.List;


public class Verification extends ActionBarActivity {


    Button b1;
EditText et;
    Intent i,i1;
    String url ="http://islamquiz.in/Android/User/verification.php";

    String name,date,phone,phone1,key,app1,token;
    LoginDataBaseAdapter loginDataBaseAdapter;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        et = (EditText) findViewById(R.id.editText50);
        b1 = (Button) findViewById(R.id.button46);

        i = new Intent(this,UserProfile.class);
        Intent intent = getIntent();


        setTitle("Verification");

        phone = intent.getStringExtra("phone");
        name = intent.getStringExtra("name");
        app1 = intent.getStringExtra("app");
        token=intent.getStringExtra("token");
        key = intent.getStringExtra("key");




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                phone1 = et.getText().toString();
                Toast.makeText(getApplicationContext(), phone1, Toast.LENGTH_SHORT).show();

                login(phone, name, phone1,app1,token,key);


            }
        });



        Button b2 = (Button) findViewById(R.id.button4);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Verification.this,Confirm.class);
                startActivity(i);

            }
        });

    }



    private void login(final String phone, String answer , String ph , String app1 , String token,String key) {

        class LoginAsync extends AsyncTask<String, Void, String>{

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(Verification.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String uname = params[0];
                String pass = params[1];
                String ph = params[2];
                String app1 = params[3];
                String token = params[4];
                String key = params[5];


                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("phone", uname));
                nameValuePairs.add(new BasicNameValuePair("name", pass));
                nameValuePairs.add(new BasicNameValuePair("ph",ph));
                nameValuePairs.add(new BasicNameValuePair("app",app1));
                nameValuePairs.add(new BasicNameValuePair("token",token));
                nameValuePairs.add(new BasicNameValuePair("key",key));
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
                    Toast.makeText(getApplicationContext(), "Account Successfully Created!", Toast.LENGTH_LONG).show();
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Verification.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("phone",phone);
                    editor.apply();
                    finish();
                    startActivity(i);
                }

                else {
                    Toast.makeText(getApplicationContext(), "Verification Failed Try Again!", Toast.LENGTH_LONG).show();
                }

            }
        }

        LoginAsync la = new LoginAsync();
        la.execute(phone,answer,ph,app1,token,key);

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

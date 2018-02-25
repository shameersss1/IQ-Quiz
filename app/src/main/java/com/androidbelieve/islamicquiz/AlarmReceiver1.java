package com.androidbelieve.islamicquiz;


import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.NotificationCompat;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlarmReceiver1 extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);


        if(dayOfTheWeek.equals("Sunday"))
        {

        }
        else {
            login("a", "b",arg0);

        }



    }

    private void buildNotification(Context context, String s){
        NotificationManager notificationManager
                = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(context, Question.class);
        PendingIntent pendingIntent
                = PendingIntent.getActivity(context, 0, intent, 0);

        builder
                .setSmallIcon(R.drawable.iq)
                .setContentTitle("Islam Quiz - Reminder")
                .setContentText(s)
                .setTicker("Islam Quiz")
                .setLights(0xFFFF0000, 500, 500) //setLights (int argb, int onMs, int offMs)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(soundUri);

        Notification notification = builder.getNotification();

        notificationManager.notify(R.mipmap.ic_launcher, notification);
    }



    private void login(final String phone, String answer, final Context arg0) {

        class LoginAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

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
                    HttpPost httpPost = new HttpPost("http://islamquiz.in/Android/User/qncheck.php");
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
                    result = "100";


                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){

                if(result.equals("100"))
                {
                    buildNotification(arg0,"இன்றைய கேள்வி ரெடியா எனCheck பண்ண இங்கே கிளிக்");

                }
                else if(result.equals("1"))
                {
                    buildNotification(arg0,"1 மணி நேரமே உள்ளது. இப்போதே பதிலனுப்ப இங்கே கிளிக்");

                }






            }
        }

        LoginAsync la = new LoginAsync();
        la.execute(phone, answer);

    }



}

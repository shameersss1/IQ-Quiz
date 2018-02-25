package com.androidbelieve.islamicquiz;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

public class ParsePushApplication extends android.app.Application {

    public ParsePushApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "N93MLlS4yod1vngUrZfYGY2INx2gEygcFGQDBELR", "4Ck70mGwK8zHIyVjnWovGJCUVsGZjAszMuVLYQ0h");
        PushService.setDefaultPushCallback(this, Question.class, R.mipmap.ic_launcher);
        SharedPreferences pref12 = PreferenceManager
                .getDefaultSharedPreferences(ParsePushApplication.this);
        String phone = pref12.getString("phone", "");
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("phone",phone);


    }




}


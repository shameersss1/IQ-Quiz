package com.androidbelieve.islamicquiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import com.androidbelieve.islamicquiz.custom.BaseActivity;
import com.androidbelieve.islamicquiz.utils.Utils;

/**
 * @author Faheem
 * The Class RegisterActivity is the Activity class that shows user registration screen
 * that allows user to register itself on Parse server for this ChatActivity app.
 */
public class RegisterActivity extends BaseActivity
{

	/** The username EditText. */
	private EditText user;

	/** The password EditText. */
	private EditText pwd;

	/** The email EditText. */

String u;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		setTouchNClick(R.id.btnReg);

		user = (EditText) findViewById(R.id.user);
		pwd = (EditText) findViewById(R.id.pwd);

		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(RegisterActivity.this);

		u = pref.getString("phone", "");

		user.setText(u);
		pwd.setText("123");

	}


	@Override
	public void onClick(View v)
	{
		super.onClick(v);

		if (u.length() == 0)
		{
			Utils.showDialog(this, R.string.err_fields_empty);
			return;
		}
		final ProgressDialog dia = ProgressDialog.show(this, null,
				getString(R.string.alert_wait));

		final ParseUser pu = new ParseUser();
		pu.setPassword("123");
		pu.setUsername(u);
		pu.signUpInBackground(new SignUpCallback() {

			@Override
			public void done(ParseException e)
			{
				dia.dismiss();
				if (e == null)
				{
					UserListActivity.user = pu;
					startActivity(new Intent(RegisterActivity.this, UserListActivity.class));
					setResult(RESULT_OK);
					finish();
				}
				else
				{
					Utils.showDialog(
							RegisterActivity.this,
							getString(R.string.err_singup) + " "
									+ e.getMessage());
					e.printStackTrace();
				}
			}
		});

	}
}

package com.facebook.android.theSocial;

import java.io.IOException;
import java.net.MalformedURLException;

import com.facebook.android.Facebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class mainMenu extends Activity {
	
	private Facebook facebook;
	Button profile;
	Button post;
	Button logout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		setUpViews();
		facebook = getSocialApplication().getFacebook();
	}
	private theSocialApplication getSocialApplication(){
		theSocialApplication app = (theSocialApplication)getApplication();
		return app;
	}
	public void setUpViews(){
		profile = (Button)findViewById(R.id.profile);
		post = (Button)findViewById(R.id.post);
		logout = (Button)findViewById(R.id.logout);
	}
	public Context getcontext(){
		return this;
	}
	public void toFriendsList(View view){
		Intent intent = new Intent(this,FriendsListActivity.class);
		startActivity(intent);
	}
	@SuppressWarnings("deprecation")
	public boolean isValid(){
		return facebook.isSessionValid();
	}
	@SuppressWarnings("deprecation")
	public void logout(View view){
		SharedPreferences pref;
		pref = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
    	editor.putString("access_token", null);
    	editor.putLong("access_expires",0);
    	editor.commit();
		try {
			facebook.logout(getcontext());
			finish();
		} catch (MalformedURLException e) {
			Toast.makeText(getApplicationContext(), 
                    "Connection failure. Please try again.", Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			Toast.makeText(getApplicationContext(), 
                    "Connection failure. Please try again.", Toast.LENGTH_LONG).show();
		}
    }
		
	
	public void toProfile(View view){
		
		Intent intent = new Intent(this,ProfileActivity.class);
		startActivity(intent);
		
	}
	
}

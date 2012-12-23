package com.facebook.android.theSocial;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;

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
	Button group;
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
		group = (Button)findViewById(R.id.groups);
	}
	public Context getcontext(){
		return this;
	}
	public void toGroupList(View view){
		Intent intent = new Intent(this,GroupsListActivity.class);
		startActivity(intent);
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
    	if(facebook.isSessionValid()){
    		AsyncFacebookRunner runner = new AsyncFacebookRunner(facebook);
    		runner.logout(getcontext(), new RequestListener() {
				
				@Override
				public void onMalformedURLException(MalformedURLException e, Object state) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onIOException(IOException e, Object state) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onFileNotFoundException(FileNotFoundException e, Object state) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onFacebookError(FacebookError e, Object state) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onComplete(String response, Object state) {
					finish();
				}
			});
    	}
    	
		
    }
		
	
	public void toProfile(View view){
		
		Intent intent = new Intent(this,ProfileActivity.class);
		startActivity(intent);
		
	}
	
}

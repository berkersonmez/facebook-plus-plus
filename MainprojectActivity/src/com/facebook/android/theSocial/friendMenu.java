package com.facebook.android.theSocial;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.theSocial.Friends.Friend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class friendMenu extends Activity {
	
	private ImageView photo;
	private TextView friendName;
	private TextView gender;
	private TextView location;
	private TextView birthdate;
	private Button post;
	private Button message;
	private Button back;
	JSONObject me = null;
	JSONArray dataArray = null;
	Facebook facebook;
	private Friend friend;
	private int position;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getExtras()!=null)
			friend = getExtras();
		else
			finish();
		setContentView(R.layout.activity_friendmenu);
		facebook = getTheSocialApplication().getFacebook();
		try {
			Bundle bdle=new Bundle();
		    bdle.putString("fields","birthday,gender,location"); 
			me = new JSONObject(facebook.request("me/friends",bdle));
			dataArray = me.getJSONArray("data");
		} catch (MalformedURLException e) {
			Log.d("Error", e.getMessage());
		} catch (JSONException e) {
			Log.d("Error", "No birthday or location   "+e.getMessage());
		} catch (IOException e) {
			Log.d("Error",e.getMessage());
		}
		setUpViews();
		setUpAbout();
	}
	public Friend getExtras(){
		Bundle extra = getIntent().getExtras();
		if(extra==null)
			return null;
		else{
			String name = extra.getString("NAME");
			String id = extra.getString("ID");
			position = extra.getInt("POSITION");
			return new Friend(name,id,null);
		}
	}
	public void setUpViews(){
		photo = (ImageView)findViewById(R.id.friendPicture);
		friendName = (TextView)findViewById(R.id.friendName);
		gender = (TextView)findViewById(R.id.gender);
		location = (TextView)findViewById(R.id.location);
		birthdate = (TextView)findViewById(R.id.birthdate);
		post = (Button)findViewById(R.id.friendPost);
		message = (Button)findViewById(R.id.message);
		back = (Button)findViewById(R.id.back);
		
	}
	public void setUpAbout(){
		URL imagevalue = null;
		try {
			imagevalue = new URL("http://graph.facebook.com/"+friend.getId()+"/picture?type=normal");
		} catch (MalformedURLException e) {
			Log.d("Error", e.getMessage());
		}
		Bitmap profilePic = null;
		try {
			profilePic = BitmapFactory.decodeStream(imagevalue.openConnection().getInputStream());
		} catch (IOException e) {
			Log.d("Error", "File can not read !!!"+e.getMessage());
		}
		photo.setImageBitmap(profilePic);
		friendName.setText(friend.getName());
		try {
			gender.setText("Gender: "+dataArray.getJSONObject(position).getString("gender"));
			birthdate.setText("Birthday: "+dataArray.getJSONObject(position).getString("birthday"));
			location.setText("Location: "+dataArray.getJSONObject(position).getString("location").split("\"")[7]);
		} catch (JSONException e) {
			Log.d("Error", "Gender or location not found for this friends !!!"+e.getMessage());
		}
		
	}
	public Context getContext(){
		return this;
	}
	public theSocialApplication getTheSocialApplication(){
		theSocialApplication app = (theSocialApplication)getApplication();
		return app;
	}
	public void toBack(View view){
		finish();
	}
	public void toPost(View view){
		Intent intent = new Intent(friendMenu.this,FriendPostActivity.class);
		intent.putExtra("ID", friend.getId());
		startActivity(intent);
	}
	@SuppressWarnings("deprecation")
	public void toMessage(View view){
		
		facebook.dialog(getContext(), "send", new DialogListener() {
			
			@Override
			public void onFacebookError(FacebookError e) {
				Toast.makeText(getApplicationContext(), "Message can not be sent", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onError(DialogError e) {
				Toast.makeText(getApplicationContext(), "Message can not be sent", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onComplete(Bundle values) {
				Toast.makeText(getApplicationContext(), "Message is sent", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onCancel() {
				
			}
		});
	}
	
}

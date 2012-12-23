package com.facebook.android.theSocial;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.android.Facebook;
import com.facebook.android.theSocial.Friends.Friend;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class friendMenu extends Activity {
	
	private ImageView photo;
	private TextView friendName;
	private TextView gender;
	private TextView location;
	private TextView birthdate;
	private Button post;
	private Button message;
	private Button back;
	JSONObject me;
	Facebook facebook;
	private Friend friend;
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
			me = new JSONObject(facebook.request(friend.getId()+"?fields=locations,birthday,gender"));
		} catch (MalformedURLException e) {
			Log.d("Error", e.getMessage());
		} catch (JSONException e) {
			Log.d("Error", "No birthday or location   "+e.getMessage());
		} catch (IOException e) {
			Log.d("Error",e.getMessage());
		}
	}
	public Friend getExtras(){
		Bundle extra = getIntent().getExtras();
		if(extra==null)
			return null;
		else{
			String name = extra.getString("NAME");
			String id = extra.getString("ID");
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
			imagevalue = new URL("http://graph.facebook.com/"+friend.getId()+"/picture?type=large");
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
			gender.setText(me.getString("gender"));
			birthdate.setText(me.getString("birthday"));
			location.setText("Location: "+me.getString("location").split("\"")[7]);
		} catch (JSONException e) {
			Log.d("Error", "Gender or location not found for this friends !!!"+e.getMessage());
		}
		
	}
	public theSocialApplication getTheSocialApplication(){
		theSocialApplication app = (theSocialApplication)getApplication();
		return app;
	}
	public void toBack(View view){
		finish();
	}
	public void toPost(View view){
		
	}
	public void toMessage(View view){
		
	}
	
}

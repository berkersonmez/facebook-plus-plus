package com.facebook.android.theSocial;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.android.Facebook;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends Activity {
	
	ImageView photo;
	TextView username;
	TextView name;
	TextView gender;
	TextView location;
	TextView birthdate;
	Button menu;
	Button edit;
	JSONObject me;
	String id;
	Facebook facebook;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		facebook = getTheSocialApplication().getFacebook();
		try {
			me = new JSONObject(facebook.request("me"));
			id = me.getString("id");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setContentView(R.layout.activity_profile);
		setupViews();
		setupPhoto(id);
		setupAbout();
	}
	
	private void setupAbout() {
		
		try{
		username.setText(me.getString("username"));
		name.setText(me.getString("name"));
		birthdate.setText("Birthdate: "+me.getString("birthday"));
		gender.setText("Gender: "+me.getString("gender"));
		location.setText("Location: "+me.getString("location").split("\"")[7]);
		}catch(JSONException ex){
			Log.d("Error", "Gender or location not found for this friends !!!"+ex.getMessage());
		}
	}

	private theSocialApplication getTheSocialApplication(){
		theSocialApplication app = (theSocialApplication)getApplication();
		return app;
	}
	
	public void toEdit(View view){
		
		Intent intent = new Intent(this,ProfileEdit.class);
		startActivity(intent);
		
	}
	
	public void toMenu(View view){
		finish();
	}
	
	public void setupViews(){
		username = (TextView)findViewById(R.id.username);
		name = (TextView)findViewById(R.id.name);
		gender = (TextView)findViewById(R.id.gender);
		birthdate = (TextView)findViewById(R.id.birthdate); 
		location = (TextView)findViewById(R.id.location);
		menu = (Button)findViewById(R.id.menu);
		edit = (Button)findViewById(R.id.EditProfile);
		photo = (ImageView)findViewById(R.id.profilePicture);
	}

	private void setupPhoto(String id) {
		URL imagevalue = null;
		try {
			imagevalue = new URL("http://graph.facebook.com/"+id+"/picture?type=large");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Bitmap profilePic = null;
		try {
			profilePic = BitmapFactory.decodeStream(imagevalue.openConnection().getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		photo.setImageBitmap(profilePic);
	}

	
}

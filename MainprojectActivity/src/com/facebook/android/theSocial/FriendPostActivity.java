package com.facebook.android.theSocial;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.android.Facebook;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class FriendPostActivity extends Activity {
	
	private ListView list;
	private String[] posts;
	private String ID;
	JSONObject me = null;
	JSONArray dataArray = null;
	Facebook facebook;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		facebook = getSocialApplication().getFacebook();
		setContentView(R.layout.activity_friendpost);
		list = (ListView)findViewById(R.id.friendPost);
		getExtras();
		pullPosts();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1,posts);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(getApplicationContext(), "You can only see on list.", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	@SuppressWarnings("deprecation")
	public void pullPosts(){
		Bundle bdl = new Bundle();
		bdl.putString("fields", "posts&uid="+ID);
		List<String> friendposts = new LinkedList<String>();
		try {
			me = new JSONObject(facebook.request("me/friends",bdl));
			me = me.getJSONObject("posts");
			dataArray = me.getJSONArray("data");
			for(int i = 0;i<dataArray.length();i++){
				me = dataArray.getJSONObject(i);
				friendposts.add(me.getString("story"));
			}	
		} catch (MalformedURLException e) {
			Toast.makeText(getApplicationContext(), "Post can not showed.", Toast.LENGTH_SHORT).show();
		} catch (JSONException e) {
			Toast.makeText(getApplicationContext(), "Post can not showed.", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			Toast.makeText(getApplicationContext(), "Post can not showed.", Toast.LENGTH_SHORT).show();
		}
		if(!friendposts.isEmpty()){
			friendposts.toArray(posts);
		}else{
			Toast.makeText(getApplicationContext(), "Post can not showed.", Toast.LENGTH_SHORT).show();
			finish();
		}
		
	}
	public theSocialApplication getSocialApplication(){
		theSocialApplication app = (theSocialApplication)getApplication();
		return app;
	}
	public void getExtras(){
		Bundle extra = getIntent().getExtras();
		if(extra==null)
			finish();
		else
			ID = extra.getString("ID");
	}
	
}

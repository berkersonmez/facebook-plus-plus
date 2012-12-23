package com.facebook.android.theSocial;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.android.Facebook;
import com.facebook.android.theSocial.Friends.Friend;
import com.facebook.android.theSocial.Friends.FriendListAdapter;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FriendsListActivity extends ListActivity {
	
	private String name;
	private String id;
	private Facebook facebook;
	private JSONObject friendsdata = null;
	private List<Friend> friendsList = null;
	private ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dialog = new ProgressDialog(this);
		facebook = getTheSocialApplication().getFacebook();
		friendsList = new LinkedList<Friend>();
		new AsyncTask<Integer, Integer, Boolean>(){
			
			
			@Override
			protected void onCancelled() {
				super.onCancelled();
				finish();
			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				 dialog = ProgressDialog.show(FriendsListActivity.this, "Please Wait",
	                        "Loading your friends...");
			}
			
			@Override
			protected void onPostExecute(Boolean result) {
				dialog.dismiss();
				ArrayAdapter<Friend> adapter = new FriendListAdapter(FriendsListActivity.this, friendsList);
				setListAdapter(adapter);
			}

			@Override
			protected Boolean doInBackground(Integer... params) {
				if (params == null)
                {
                    return false;
                }
                try
                {
                    Thread.sleep(params[0]);
                    friendsList = setFriends();
                    
                }
                catch (InterruptedException e)
                {
                    Log.e("Error", e.getMessage());
                    return false;
                }

                return true;
            }
			
		}.execute(2000);
		
        
	}
	public Context getContext(){
		return this;
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(getContext(),friendMenu.class);
		intent.putExtra("ID",friendsList.get(position).getId());
		intent.putExtra("NAME", friendsList.get(position).getName());
		startActivity(intent);
		
	}
	public theSocialApplication getTheSocialApplication(){
		theSocialApplication app = (theSocialApplication)getApplication();
		return app;
	}
	@SuppressWarnings("deprecation")
	public List<Friend> setFriends(){
			List<Friend> list = new LinkedList<Friend>();
			try {
				JSONObject response = null;
				response = new JSONObject(facebook.request("me/friends"));
				JSONArray dataArray = response.getJSONArray("data");
				for(int i = 0;i<dataArray.length();i++){
					friendsdata = dataArray.getJSONObject(i);
					name = friendsdata.getString("name");
					id = friendsdata.getString("id");
					URL imagevalue = null;
					try {
						imagevalue = new URL("http://graph.facebook.com/"+id+"/picture?type=small");
					} catch (MalformedURLException e) {
						Log.d("Error",e.getMessage());
					}
					Bitmap profilePic = null;
					try {
						profilePic = BitmapFactory.decodeStream(imagevalue.openConnection().getInputStream());
					} catch (IOException e) {
						Log.d("Error",e.getMessage());
					}
					list.add(new Friend(name, id,profilePic));
				}
			} catch (MalformedURLException e) {
				Log.d("Error",e.getMessage());
			} catch (IOException e) {
				Log.d("Error",e.getMessage());
			} catch(JSONException e){
				Log.d("Error",e.getMessage());
			}
		return list;
	}
}

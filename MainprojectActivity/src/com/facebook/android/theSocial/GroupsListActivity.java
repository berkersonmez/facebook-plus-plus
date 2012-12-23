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
import android.app.Activity;
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
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
public class GroupsListActivity extends Activity {
private ListView list;
private String[] groups;
private String group;
private List<String> mygroups;
private Button back;
JSONObject me = null;
JSONArray dataArray = null;
Facebook facebook;
private ProgressDialog dialog;
@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
dialog = new ProgressDialog(this);
facebook = getTheSocialApplication().getFacebook();
mygroups = new LinkedList<String>();
groups = new String[10];
setContentView(R.layout.activity_groups);
list = (ListView)findViewById(android.R.id.list);
back = (Button)findViewById(R.id.back);

new AsyncTask<Integer, Integer, Boolean>(){


@Override
protected void onCancelled() {
super.onCancelled();
finish();
}
@Override
protected void onPreExecute() {
super.onPreExecute();
dialog = ProgressDialog.show(GroupsListActivity.this, "Please Wait",
"Loading your groups...");
}

@Override
protected void onPostExecute(Boolean result) {
dialog.dismiss();
if(mygroups.size()!=0){
ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,android.R.id.text1,mygroups.toArray(groups));
list.setAdapter(adapter);
}else{
Toast.makeText(getApplicationContext(), "Groups can not showed. Please Try Again.", Toast.LENGTH_SHORT).show();
}
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
mygroups = setGroups();

}
catch (InterruptedException e)
{
Log.e("Error", e.getMessage());
return false;
}
return true;
}

}.execute(2000);
/*list.setOnClickListener(new OnClickListener() {

@Override
public void onClick(View v) {
Toast.makeText(getApplicationContext(), "Please push the back to exit.", Toast.LENGTH_SHORT).show();
}
});*/

}
public void back(View view){
finish();
}
public Context getContext(){
return this;
}

public theSocialApplication getTheSocialApplication(){
theSocialApplication app = (theSocialApplication)getApplication();
return app;
}
@SuppressWarnings("deprecation")
public List<String> setGroups(){
List<String> list = new LinkedList<String>();
try {
JSONObject response = null;
Bundle bdl = new Bundle();
bdl.putString("fields", "groups");
response = new JSONObject(facebook.request("me",bdl));
response = response.getJSONObject("groups");
JSONArray dataArray = response.getJSONArray("data");
for(int i = 0;i<dataArray.length();i++){
me = dataArray.getJSONObject(i);
group = me.getString("name");

list.add(group);
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
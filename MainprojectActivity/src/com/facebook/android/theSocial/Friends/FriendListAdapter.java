package com.facebook.android.theSocial.Friends;


import java.util.List;

import com.facebook.android.theSocial.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendListAdapter extends ArrayAdapter<Friend> {
	
	private Activity context;
	private List<Friend> friendList;
	
	public FriendListAdapter(Activity context,List<Friend> list) {
		super(context, R.layout.activity_friendist, list);
		this.context = context;
		friendList = list;
	}
	static class ViewHolder {
	    protected TextView text;
	    protected ImageView imageView;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.activity_friendist,parent,false);
		TextView text = (TextView)rowView.findViewById(R.id.personName);
		ImageView imageView = (ImageView)rowView.findViewById(R.id.icon);
		if(friendList.get(position)!=null){
		text.setText(friendList.get(position).getName());
		imageView.setImageBitmap(friendList.get(position).getPhoto());
		}else{
			imageView.setImageResource(R.drawable.com_facebook_profile_default_icon);
		}
		return rowView;
	}
	
}

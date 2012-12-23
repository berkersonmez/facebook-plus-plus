package com.facebook.android.theSocial;


import com.facebook.android.Facebook;

import android.app.Application;

@SuppressWarnings("deprecation")
public class theSocialApplication extends Application {
	
	private Facebook facebook ;
	private String permissions[];
	
	@Override
	public void onCreate() {
		super.onCreate();
		permissions = new String[]{"email","user_status","user_photos","publish_actions", 
				"user_about_me","user_birthday","user_activities",
				"user_events","user_groups","user_likes","user_relationships"
				
		};
		facebook = new Facebook("443444329053062");
		
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	public String[] getPermissions() {
		return permissions;
	}
	public Facebook getFacebook() {
		return facebook;
	}
	public void setFacebook(Facebook facebook) {
		this.facebook = facebook;
	}
	
	
	
}

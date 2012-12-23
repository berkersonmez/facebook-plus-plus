package com.facebook.android.theSocial;


import com.facebook.android.Facebook;

import android.app.Application;

@SuppressWarnings("deprecation")
public class theSocialApplication extends Application {
	
	private Facebook facebook ;
	private final String[] permissions = { "user_about_me", "user_activities", "user_birthday",
            "user_checkins", "user_education_history", "user_events", "user_groups",
            "user_hometown", "user_interests", "user_likes", "user_location", "user_notes",
            "user_online_presence", "user_photos", "user_photo_video_tags", "user_relationships",
            "user_relationship_details", "user_religion_politics", "user_status", "user_videos",
            "user_website", "user_work_history", "friends_about_me", "friends_activities", "friends_birthday",
            "friends_checkins", "friends_education_history", "friends_events", "friends_groups",
            "friends_hometown", "friends_interests", "friends_likes", "friends_location",
            "friends_notes", "friends_online_presence", "friends_photos",
            "friends_photo_video_tags", "friends_relationships", "friends_relationship_details",
            "friends_religion_politics", "friends_status", "friends_videos", "friends_website",
            "friends_work_history","ads_management", "create_event", "create_note", "email",
            "export_stream", "manage_friendlists", 
            "offline_access", "publish_actions", "photo_upload", "publish_checkins",
            "publish_stream", "read_friendlists", "read_insights", "read_mailbox", "read_requests",
            "read_stream", "rsvp_event", "share_item", "status_update", "sms", "video_upload",
            "xmpp_login" };
	@Override
	public void onCreate() {
		super.onCreate();
		
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

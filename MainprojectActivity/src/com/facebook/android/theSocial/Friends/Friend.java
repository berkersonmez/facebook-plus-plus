package com.facebook.android.theSocial.Friends;

import android.graphics.Bitmap;

public class Friend {
	private String name;
	private String id;
	private Bitmap photo;
	public Friend(String name, String id,Bitmap photo) {
		super();
		this.name = name;
		this.id = id;
		this.photo = photo;
	}
	
	public Bitmap getPhoto() {
		return photo;
	}
	public void setPhoto(Bitmap photo) {
		this.photo = photo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}

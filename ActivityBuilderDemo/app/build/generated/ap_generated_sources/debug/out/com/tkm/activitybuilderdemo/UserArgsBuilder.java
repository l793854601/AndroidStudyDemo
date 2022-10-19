package com.tkm.activitybuilderdemo;
import android.os.Bundle;
public class UserArgsBuilder {
	private String name;
	public UserArgsBuilder setName(String name) {
		this.name=name;
		return this;
	}
	private String owner;
	public UserArgsBuilder setOwner(String owner) {
		this.owner=owner;
		return this;
	}
	private String url;
	public UserArgsBuilder setUrl(String url) {
		this.url=url;
		return this;
	}
	private long createAt;
	public UserArgsBuilder setCreateAt(long createAt) {
		this.createAt=createAt;
		return this;
	}
	public Bundle toBundle() {
		Bundle bundle = new Bundle();
		bundle.putString("NAME", this.name);
		bundle.putString("OWNER", this.owner);
		bundle.putString("URL", this.url);
		bundle.putLong("CREATEAT", this.createAt);
		return bundle;
	}
}

package com.sina.weibo.sdk.demo.dao;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.sina.weibo.sdk.demo.util.DownloadIcon;
import com.sina.weibo.sdk.openapi.models.User;

import android.graphics.drawable.Drawable;
import android.text.format.DateUtils;

public class WeiboInfo implements Serializable {
	// 微博id
	private String weibo_id;
	// 微博内容
	private String weibo_text;
	// 微博的时间
	private String weibo_time;
	// 发布人的id
	private String user_id;
	// 发布人的名字
	private String user_name;
	// 发布人的头像
	private String user_icon;
	// 微博内容是否有图片
	private Boolean havaImage = false;
	// 微博内容的图片
	private String image_url;
	// 微博中内容大图
	private String large_image_url;

	public String getLarge_image_url() {
		return large_image_url;
	}

	public void setLarge_image_url(String large_image_url) {
		this.large_image_url = large_image_url;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getWeibo_id() {
		return weibo_id;
	}

	public void setWeibo_id(String weibo_id) {
		this.weibo_id = weibo_id;
	}

	public String getWeibo_text() {
		return weibo_text;
	}

	public void setWeibo_text(String weibo_text) {
		this.weibo_text = weibo_text;
	}

	public String getWeibo_time() {
		return weibo_time;
	}

	public void setWeibo_time(String weibo_time) {
		this.weibo_time = weibo_time;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_icon() {
		return user_icon;
	}

	public void setUser_icon(String user_icon) {
		this.user_icon = user_icon;
	}

	public Boolean getHavaImage() {
		return havaImage;
	}

	public void setHavaImage(Boolean havaImage) {
		this.havaImage = havaImage;
	}

	// 解析WeiboInfo的json数据
	public static WeiboInfo parse(String jsonString) {
		if (null == jsonString) {
			return null;
		}
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(jsonString);
			WeiboInfo weiboinfo = WeiboInfo.parse(jsonObject);
			return weiboinfo;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static WeiboInfo parse(JSONObject jsonObject) {
		if (null == jsonObject) {
			return null;
		}
		WeiboInfo weibo = new WeiboInfo();

		weibo.weibo_id = jsonObject.optString("id", "");

		weibo.weibo_text = jsonObject.optString("text", "");
		// 设置时间格式
		String time = jsonObject.optString("created_at", "");
		Date date = new Date(time);
		SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd-hh:mm:ss");
		weibo.weibo_time = format.format(date);

		try {
			JSONObject user = jsonObject.getJSONObject("user");
			weibo.user_id = user.optString("id", "");
			weibo.user_name = user.optString("name", "");
			weibo.user_icon = user.optString("profile_image_url");

			weibo.havaImage = false;

			if (jsonObject.has("bmiddle_pic")) {
				weibo.image_url = jsonObject.optString("bmiddle_pic");
				weibo.havaImage = true;
			}

			if (jsonObject.has("original_pic")) {
				weibo.large_image_url = jsonObject.optString("original_pic");
				weibo.havaImage = true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return weibo;
	}
}

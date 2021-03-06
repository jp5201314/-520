package com.sina.weibo.sdk.demo.dao;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.drawable.Drawable;

import com.sina.weibo.sdk.demo.util.DownloadIcon;
import com.sina.weibo.sdk.openapi.models.Status;

public class User {

	/** 用户UID（int64） */
	private String id;
	/** 字符串型的用户 UID */
	private String idstr;
	/** 用户昵称 */
	private String screen_name;
	/** 友好显示名称 */
	private String name;
	/** 用户所在省级ID */
	private int province;
	/** 用户所在城市ID */
	private int city;
	/** 用户所在地 */
	private String location;
	/** 用户个人描述 */
	private String description;
	/** 用户博客地址 */
	private String url;
	/** 用户头像地址，50×50像素 */
	private String profile_image_url;
	/** 用户头像，50x50像素 */
	private Drawable user_head;
	/** 用户的微博统一URL地址 */
	private String profile_url;
	/** 用户的个性化域名 */
	private String domain;
	/** 用户的微号 */
	private String weihao;
	/** 性别，m：男、f：女、n：未知 */
	private String gender;
	/** 粉丝数 */
	private int followers_count;
	/** 关注数 */
	private int friends_count;
	/** 微博数 */
	private int statuses_count;
	/** 收藏数 */
	private int favourites_count;
	/** 用户创建（注册）时间 */
	private String created_at;
	/** 暂未支持 */
	private boolean following;
	/** 是否允许所有人给我发私信，true：是，false：否 */
	private boolean allow_all_act_msg;
	/** 是否允许标识用户的地理位置，true：是，false：否 */
	private boolean geo_enabled;
	/** 是否是微博认证用户，即加V用户，true：是，false：否 */
	private boolean verified;
	/** 暂未支持 */
	private int verified_type;
	/** 用户备注信息，只有在查询用户关系时才返回此字段 */
	private String remark;
	/** 用户的最近一条微博信息字段 */
	private Status status;
	/** 是否允许所有人对我的微博进行评论，true：是，false：否 */
	private boolean allow_all_comment;
	/** 用户大头像地址 */
	private String avatar_large;
	/** 用户高清大头像地址 */
	private String avatar_hd;
	/** 认证原因 */
	private String verified_reason;
	/** 该用户是否关注当前登录用户，true：是，false：否 */
	private boolean follow_me;
	/** 用户的在线状态，0：不在线、1：在线 */
	private int online_status;
	/** 用户的互粉数 */
	private int bi_followers_count;
	/** 用户当前的语言版本，zh-cn：简体中文，zh-tw：繁体中文，en：英语 */
	private String lang;

	/** 注意：以下字段暂时不清楚具体含义，OpenAPI 说明文档暂时没有同步更新对应字段 */
	private String star;
	private String mbtype;
	private String mbrank;
	private String block_word;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdstr() {
		return idstr;
	}

	public void setIdstr(String idstr) {
		this.idstr = idstr;
	}

	public String getScreen_name() {
		return screen_name;
	}

	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getProvince() {
		return province;
	}

	public void setProvince(int province) {
		this.province = province;
	}

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getProfile_image_url() {
		return profile_image_url;
	}

	public Drawable getUser_head() {
		return user_head;
	}

	public void setUser_head(Drawable user_head) {
		this.user_head = user_head;
	}

	public void setProfile_image_url(String profile_image_url) {
		this.profile_image_url = profile_image_url;
	}

	public String getProfile_url() {
		return profile_url;
	}

	public void setProfile_url(String profile_url) {
		this.profile_url = profile_url;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getWeihao() {
		return weihao;
	}

	public void setWeihao(String weihao) {
		this.weihao = weihao;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getFollowers_count() {
		return followers_count;
	}

	public void setFollowers_count(int followers_count) {
		this.followers_count = followers_count;
	}

	public int getFriends_count() {
		return friends_count;
	}

	public void setFriends_count(int friends_count) {
		this.friends_count = friends_count;
	}

	public int getStatuses_count() {
		return statuses_count;
	}

	public void setStatuses_count(int statuses_count) {
		this.statuses_count = statuses_count;
	}

	public int getFavourites_count() {
		return favourites_count;
	}

	public void setFavourites_count(int favourites_count) {
		this.favourites_count = favourites_count;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public boolean isFollowing() {
		return following;
	}

	public void setFollowing(boolean following) {
		this.following = following;
	}

	public boolean isAllow_all_act_msg() {
		return allow_all_act_msg;
	}

	public void setAllow_all_act_msg(boolean allow_all_act_msg) {
		this.allow_all_act_msg = allow_all_act_msg;
	}

	public boolean isGeo_enabled() {
		return geo_enabled;
	}

	public void setGeo_enabled(boolean geo_enabled) {
		this.geo_enabled = geo_enabled;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public int getVerified_type() {
		return verified_type;
	}

	public void setVerified_type(int verified_type) {
		this.verified_type = verified_type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public boolean isAllow_all_comment() {
		return allow_all_comment;
	}

	public void setAllow_all_comment(boolean allow_all_comment) {
		this.allow_all_comment = allow_all_comment;
	}

	public String getAvatar_large() {
		return avatar_large;
	}

	public void setAvatar_large(String avatar_large) {
		this.avatar_large = avatar_large;
	}

	public String getAvatar_hd() {
		return avatar_hd;
	}

	public void setAvatar_hd(String avatar_hd) {
		this.avatar_hd = avatar_hd;
	}

	public String getVerified_reason() {
		return verified_reason;
	}

	public void setVerified_reason(String verified_reason) {
		this.verified_reason = verified_reason;
	}

	public boolean isFollow_me() {
		return follow_me;
	}

	public void setFollow_me(boolean follow_me) {
		this.follow_me = follow_me;
	}

	public int getOnline_status() {
		return online_status;
	}

	public void setOnline_status(int online_status) {
		this.online_status = online_status;
	}

	public int getBi_followers_count() {
		return bi_followers_count;
	}

	public void setBi_followers_count(int bi_followers_count) {
		this.bi_followers_count = bi_followers_count;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getStar() {
		return star;
	}

	public void setStar(String star) {
		this.star = star;
	}

	public String getMbtype() {
		return mbtype;
	}

	public void setMbtype(String mbtype) {
		this.mbtype = mbtype;
	}

	public String getMbrank() {
		return mbrank;
	}

	public void setMbrank(String mbrank) {
		this.mbrank = mbrank;
	}

	public String getBlock_word() {
		return block_word;
	}

	public void setBlock_word(String block_word) {
		this.block_word = block_word;
	}

	public static User parse(String jsonString) {
		if (null == jsonString) {
			return null;
		}
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(jsonString);
			User user = parse(jsonObject);
			return user;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static User parse(JSONObject jsonObject) {
		if (null == jsonObject) {
			return null;
		}

		final User user = new User();
		user.id = jsonObject.optString("id", "");
		user.idstr = jsonObject.optString("idstr", "");
		user.screen_name = jsonObject.optString("screen_name", "");
		user.name = jsonObject.optString("name", "");
		user.province = jsonObject.optInt("province", -1);
		user.city = jsonObject.optInt("city", -1);
		user.location = jsonObject.optString("location", "");
		user.description = jsonObject.optString("description", "");
		user.url = jsonObject.optString("url", "");
		user.profile_image_url = jsonObject.optString("profile_image_url", "");
		user.user_head = DownloadIcon.getDrawableFromUrl(user.profile_image_url);
		user.profile_url = jsonObject.optString("profile_url", "");
		user.domain = jsonObject.optString("domain", "");
		user.weihao = jsonObject.optString("weihao", "");
		user.gender = jsonObject.optString("gender", "");
		user.followers_count = jsonObject.optInt("followers_count", 0);
		user.friends_count = jsonObject.optInt("friends_count", 0);
		user.statuses_count = jsonObject.optInt("statuses_count", 0);
		user.favourites_count = jsonObject.optInt("favourites_count", 0);
		user.created_at = jsonObject.optString("created_at", "");
		user.following = jsonObject.optBoolean("following", false);
		user.allow_all_act_msg = jsonObject.optBoolean("allow_all_act_msg",
				false);
		user.geo_enabled = jsonObject.optBoolean("geo_enabled", false);
		user.verified = jsonObject.optBoolean("verified", false);
		user.verified_type = jsonObject.optInt("verified_type", -1);
		user.remark = jsonObject.optString("remark", "");
		// user.status = jsonObject.optString("status", ""); // XXX: NO Need ?
		user.allow_all_comment = jsonObject.optBoolean("allow_all_comment",
				true);
		user.avatar_large = jsonObject.optString("avatar_large", "");
		user.avatar_hd = jsonObject.optString("avatar_hd", "");
		user.verified_reason = jsonObject.optString("verified_reason", "");
		user.follow_me = jsonObject.optBoolean("follow_me", false);
		user.online_status = jsonObject.optInt("online_status", 0);
		user.bi_followers_count = jsonObject.optInt("bi_followers_count", 0);
		user.lang = jsonObject.optString("lang", "");

		// 注意：以下字段暂时不清楚具体含义，OpenAPI 说明文档暂时没有同步更新对应字段含义
		user.star = jsonObject.optString("star", "");
		user.mbtype = jsonObject.optString("mbtype", "");
		user.mbrank = jsonObject.optString("mbrank", "");
		user.block_word = jsonObject.optString("block_word", "");

		return user;
	}
}

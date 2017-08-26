package com.sina.weibo.sdk.demo.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.sina.weibo.sdk.demo.db.DBInfo;
import com.sina.weibo.sdk.demo.db.SinaDBHelper;



public class UserApiDao {

	private static UserApiDao dao;
	private Context context;
	private List<User> list = null;
	private SinaDBHelper DBHelpter;
	private SQLiteDatabase db;

	private User user;
	private ContentValues values;
	private Drawable user_head;
	private int index;

	private String[] columns = { DBInfo.TableApi._ID, DBInfo.TableApi.ID,
			DBInfo.TableApi.IDSTR, DBInfo.TableApi.SCREEN_NAME,
			DBInfo.TableApi.NAME, DBInfo.TableApi.PROVINCE,
			DBInfo.TableApi.CITY, DBInfo.TableApi.LOCATION,
			DBInfo.TableApi.DESCRIPTION, DBInfo.TableApi.URL,
			DBInfo.TableApi.PROFILE_IMGE_URL, DBInfo.TableApi.USER_HEAD,
			DBInfo.TableApi.PROFILE_URL, DBInfo.TableApi.DOMAIN,
			DBInfo.TableApi.WEIHAO, DBInfo.TableApi.GENDER,
			DBInfo.TableApi.FOLLOWERS_COUNT, DBInfo.TableApi.FRIENDS_COUNT,
			DBInfo.TableApi.STATUSES_COUNT, DBInfo.TableApi.FAVOURITES_COUNT,
			DBInfo.TableApi.CREATED_AT, DBInfo.TableApi.FOLLOWING,
			DBInfo.TableApi.ALLOW_ALL_ACT_MSG, DBInfo.TableApi.GEO_ENABLED,
			DBInfo.TableApi.VERIFIED, DBInfo.TableApi.VERIFIED_TRPE,
			DBInfo.TableApi.REMARK, DBInfo.TableApi.STATUS,
			DBInfo.TableApi.ALLOW_ALL_COMMENT, DBInfo.TableApi.AVATAR_LARGE,
			DBInfo.TableApi.AVATAR_HD, DBInfo.TableApi.VERIFIED_REASON,
			DBInfo.TableApi.FOLLOW_ME, DBInfo.TableApi.ONLINE_STATUS,
			DBInfo.TableApi.BI_FOLLOWERS_COUNT, DBInfo.TableApi.LANG };

	private UserApiDao(Context context) {
		this.context = context;
		DBHelpter = new SinaDBHelper(context, DBInfo.DB.DB_NAME, null,
				DBInfo.DB.DB_VERSION);
	}

	public static UserApiDao getUserApiDao(Context context){
		if(dao==null){
			dao = new UserApiDao(context);
		}
		return dao;
	} 
	public long insertUserInfo(User user) {
		// 得到写数据库访问
		db = DBHelpter.getWritableDatabase();
		// 插入的数据集合
		values = new ContentValues();

		values.put(DBInfo.TableApi.ID, user.getId());
		values.put(DBInfo.TableApi.IDSTR, user.getIdstr());
		values.put(DBInfo.TableApi.SCREEN_NAME, user.getScreen_name());
		values.put(DBInfo.TableApi.NAME, user.getName());
		values.put(DBInfo.TableApi.PROVINCE, user.getProvince());
		values.put(DBInfo.TableApi.CITY, user.getCity());
		values.put(DBInfo.TableApi.LOCATION, user.getLocation());
		values.put(DBInfo.TableApi.DESCRIPTION, user.getDescription());
		values.put(DBInfo.TableApi.URL, user.getUrl());
		values.put(DBInfo.TableApi.PROFILE_IMGE_URL,
				user.getProfile_image_url());

		values.put(DBInfo.TableApi.PROFILE_URL, user.getProfile_url());
		values.put(DBInfo.TableApi.DOMAIN, user.getDomain());
		values.put(DBInfo.TableApi.WEIHAO, user.getWeihao());
		values.put(DBInfo.TableApi.GENDER, user.getGender());
		values.put(DBInfo.TableApi.FOLLOWERS_COUNT, user.getFollowers_count());
		values.put(DBInfo.TableApi.FRIENDS_COUNT, user.getFriends_count());
		values.put(DBInfo.TableApi.FAVOURITES_COUNT, user.getFavourites_count());
		values.put(DBInfo.TableApi.CREATED_AT, user.getCreated_at());
		values.put(DBInfo.TableApi.FOLLOWING, user.isFollowing());
		values.put(DBInfo.TableApi.ALLOW_ALL_ACT_MSG,
				user.isAllow_all_act_msg());
		values.put(DBInfo.TableApi.GEO_ENABLED, user.isGeo_enabled());
		values.put(DBInfo.TableApi.VERIFIED, user.isVerified());
		values.put(DBInfo.TableApi.VERIFIED_TRPE, user.getVerified_type());
		values.put(DBInfo.TableApi.REMARK, user.getRemark());
		values.put(DBInfo.TableApi.REMARK, String.valueOf(user.getStatus()));
		values.put(DBInfo.TableApi.ALLOW_ALL_COMMENT,
				user.isAllow_all_comment());
		values.put(DBInfo.TableApi.AVATAR_LARGE, user.getAvatar_large());
		values.put(DBInfo.TableApi.AVATAR_HD, user.getAvatar_hd());
		values.put(DBInfo.TableApi.VERIFIED_REASON, user.getVerified_reason());
		values.put(DBInfo.TableApi.FOLLOW_ME, user.isFollow_me());
		values.put(DBInfo.TableApi.ONLINE_STATUS, user.getOnline_status());
		values.put(DBInfo.TableApi.BI_FOLLOWERS_COUNT,
				user.getBi_followers_count());
		values.put(DBInfo.TableApi.LANG, user.getLang());

		user_head = user.getUser_head();
		// 插入图片，并做一定的格式转换
		if (user_head != null) {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			BitmapDrawable newImage = (BitmapDrawable) user_head;
			newImage.getBitmap().compress(CompressFormat.PNG, 100,
					byteArrayOutputStream);
			values.put(DBInfo.TableApi.USER_HEAD,
					byteArrayOutputStream.toByteArray());
		} else {
			values.put(DBInfo.TableApi.USER_HEAD, "");
		}
		// 进行插入操作
		long rowId = db.insert(DBInfo.TableApi.TABLEAPI_NAME, null, values);
		db.close();
		return rowId;
	}

	// 更新指定用户数据
	public boolean updateUserInfo(String user_id) {

		return false;
	}

	// 删除指定用户id的信息
	public int deleteUserInfo(String idStr) {
		db = DBHelpter.getWritableDatabase();
		int i = db.delete(DBInfo.TableApi.TABLEAPI_NAME, "idstr = ?",
				new String[] { idStr });
		db.close();
		return i;
	}

	// 查找单一用户的信息
	public User findSingleUserInfo(String user_id) {
		User user = null;
		db = this.DBHelpter.getReadableDatabase();
		Cursor cursor = db.query(DBInfo.TableApi.TABLEAPI_NAME, columns,
				"idstr = ?", new String[] { user_id }, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				user = getUser(cursor);
			}
		}
		cursor.close();
		db.close();
		return user;
	}

	public Drawable findUserHeadImage(String id) {

		db = DBHelpter.getWritableDatabase();
		
		Drawable user_image;
		Cursor cursor = db.query(DBInfo.TableApi.TABLEAPI_NAME,
				new String[] { DBInfo.TableApi.USER_HEAD }, "idstr=?",
				new String[] { id }, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				int n = cursor.getColumnIndex(DBInfo.TableApi.USER_HEAD);
				if (n != -1) {
					byte[] buf = new byte[1024];
					buf = cursor.getBlob(n);
					ByteArrayInputStream bis = new ByteArrayInputStream(buf);
					user_image = BitmapDrawable.createFromStream(bis, "image");
					return user_image;
				}
			}
		}
		cursor.close();
		db.close();

		return null;
	}

	// 查找所有用户的信息
	public  List<User> findAllUserInfo() {
		// 获取读取数据库数据
		db = this.DBHelpter.getReadableDatabase();
		
		// 查询数据库信息
		Cursor cursor = db.query(DBInfo.TableApi.TABLEAPI_NAME, columns, null,
				null, null, null, null);
		// 查找数据库数据并封装成对象存储在集合中
		if (cursor != null && cursor.getCount() > 0) {
			// 创建集合实例化，存储已登录用户信息
			list = new ArrayList<User>();
			while (cursor.moveToNext()) {
				user = new User();
				user = getUser(cursor);
				list.add(user);
			}
		}
		// 关闭游标
		cursor.close();
		// 关闭数据库连接
		db.close();
		return list;
	}

	private User getUser(Cursor cursor) {
		User user = new User();
		index = cursor.getColumnIndex(DBInfo.TableApi.ID);
		if (index != -1) {
			user.setId(cursor.getString(index));
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.IDSTR);
		if (index != -1) {
			user.setIdstr(cursor.getString(index));
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.SCREEN_NAME);
		if (index != -1) {
			user.setScreen_name(cursor.getString(index));
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.NAME);
		if (index != -1) {
			user.setName(cursor.getString(index));
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.PROVINCE);
		if (index != -1) {
			user.setProvince(cursor.getInt(index));
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.CITY);
		if (index != -1) {
			user.setCity(cursor.getInt(index));
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.LOCATION);
		if (index != -1) {
			user.setLocation(cursor.getString(index));
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.DESCRIPTION);
		if (index != -1) {
			user.setDescription(cursor.getString(index));
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.URL);
		if (index != -1) {
			user.setUrl(cursor.getString(index));
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.PROFILE_IMGE_URL);
		if (index != -1) {
			user.setProfile_image_url(cursor.getString(index));
		}

		index = cursor.getColumnIndex(DBInfo.TableApi.USER_HEAD);
		if (index != -1) {
			byte[] buf = cursor.getBlob(index);
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
					buf);
			Drawable drawable = Drawable.createFromStream(byteArrayInputStream,
					"image");
			user.setUser_head(user_head);
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.PROFILE_URL);
		if (index != -1) {
			user.setProfile_url(cursor.getString(index));
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.DOMAIN);
		if (index != -1) {
			user.setDomain(cursor.getString(index));
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.WEIHAO);
		if (index != -1) {
			user.setWeihao(cursor.getString(index));
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.GENDER);
		if (index != -1) {
			user.setGender(cursor.getString(index));
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.FOLLOWERS_COUNT);
		if (index != -1) {
			user.setFollowers_count(cursor.getInt(index));
		}

		index = cursor.getColumnIndex(DBInfo.TableApi.FRIENDS_COUNT);
		if (index != -1) {
			user.setFriends_count(cursor.getInt(index));
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.STATUSES_COUNT);
		if (index != -1) {
			user.setStatuses_count(cursor.getInt(index));
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.FAVOURITES_COUNT);
		if (index != -1) {
			user.setFavourites_count(cursor.getInt(index));
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.CREATED_AT);
		if (index != -1) {
			user.setCreated_at(cursor.getString(index));
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.FOLLOWING);
		if (index != -1) {
			user.setFollowing(Boolean.getBoolean(cursor.getString(index)));
		}

		index = cursor.getColumnIndex(DBInfo.TableApi.ALLOW_ALL_ACT_MSG);
		if (index != -1) {
			user.setAllow_all_act_msg(Boolean.getBoolean(cursor
					.getString(index)));
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.GEO_ENABLED);
		if (index != -1) {
			user.setGeo_enabled(Boolean.getBoolean(cursor.getString(index)));
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.VERIFIED);
		if (index != -1) {
			user.setVerified(Boolean.getBoolean(cursor.getString(index)));
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.VERIFIED_TRPE);
		if (index != -1) {
			user.setVerified_type(cursor.getInt(index));
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.REMARK);
		if (index != -1) {
			user.setRemark(cursor.getString(index));
		}

		index = cursor.getColumnIndex(DBInfo.TableApi.STATUS);
		if (index != -1) {
			user.setStatus(null);
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.ALLOW_ALL_COMMENT);
		if (index != -1) {
			user.setAllow_all_comment(Boolean.getBoolean(cursor
					.getString(index)));
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.AVATAR_LARGE);
		if (index != -1) {
			user.setAvatar_large(cursor.getString(index));
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.AVATAR_HD);
		if (index != -1) {
			user.setAvatar_hd(cursor.getString(index));
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.VERIFIED_REASON);
		if (index != -1) {
			user.setVerified_reason(cursor.getString(index));
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.FOLLOW_ME);
		if (index != -1) {
			user.setFollow_me(Boolean.getBoolean(cursor.getString(index)));
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.ONLINE_STATUS);
		if (index != -1) {
			user.setOnline_status(cursor.getInt(index));
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.BI_FOLLOWERS_COUNT);
		if (index != -1) {
			user.setBi_followers_count(cursor.getInt(index));
		}
		index = cursor.getColumnIndex(DBInfo.TableApi.LANG);
		if (index != -1) {
			user.setLang(cursor.getString(index));
		}
		return user;
	}
}

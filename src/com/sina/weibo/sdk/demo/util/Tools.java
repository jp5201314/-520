package com.sina.weibo.sdk.demo.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;

import com.sina.weibo.sdk.demo.dao.User;
import com.sina.weibo.sdk.demo.dao.WeiboInfo;
import com.sina.weibo.sdk.demo.keepoauthinformation.AccessTokenKeeper;

public class Tools {
	private HttpClient client;

	private HttpGet get;

	private HttpPost post;

	private HttpResponse rsp;

	private static Tools tool;

	private String userid;

	private String oauth_token;

	private Context context;

	private User user;

	String weiboJson = null;

	private Tools(Context context) {
		this.context = context;
	}

	public static Tools getToolsInstance(Context context) {
		if (tool == null) {
			tool = new Tools(context);
		}
		return tool;
	}

	// 用户发送微博,使用post方式,发送一条文字微博

	public Boolean sendWeibo(String status) {
		oauth_token = AccessTokenKeeper.readAccessToken(context).getToken();
		String uploadUrl = "https://api.weibo.com/2/statuses/update.json";
		if (oauth_token != null && status != null) {
			try {
				client = new DefaultHttpClient();

				post = new HttpPost(uploadUrl);

				ArrayList<NameValuePair> params = new ArrayList<>();
				params.add(new BasicNameValuePair("access_token", oauth_token));
				params.add(new BasicNameValuePair("status", status));
				post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));

				rsp = client.execute(post);
				Log.i("FSLog", rsp.getStatusLine().getStatusCode() + "");
				if (rsp.getStatusLine().getStatusCode() == 200) {
					String newWeibo = EntityUtils.toString(rsp.getEntity(),
							"utf-8");
					Log.i("FSLog", newWeibo);
					return true;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	// 用户发送微博,使用post方式,发布一条微博同时指定上传的图片或图片url

	public Boolean sendWeibo(String status, String url) {
		oauth_token = AccessTokenKeeper.readAccessToken(context).getToken();
		String uploadUrl = "https://api.weibo.com/2/statuses/upload_url_text.json";
		if (oauth_token != null && status != null && url != null) {
			try {
				client = new DefaultHttpClient();

				post = new HttpPost(uploadUrl);

				ArrayList<NameValuePair> params = new ArrayList<>();
				params.add(new BasicNameValuePair("access_token", oauth_token));
				params.add(new BasicNameValuePair("status", status));
				params.add(new BasicNameValuePair("pic_id", url));

				post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));

				rsp = client.execute(post);
				Log.i("FSLog", rsp.getStatusLine().getStatusCode() + "");
				if (rsp.getStatusLine().getStatusCode() == 200) {
					String newWeibo = EntityUtils.toString(rsp.getEntity(),
							"utf-8");
					Log.i("FSLog", newWeibo);
					return true;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	// 用户发送微博,使用post方式,上传图片并发布一条微博

	public Boolean sendWeibo(String status, Bitmap bitmap) {
		String uploadUrl = "https://upload.api.weibo.com/2/statuses/upload.json";
		try {
			client = new DefaultHttpClient();

			post = new HttpPost(uploadUrl);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Boolean flag = bitmap.compress(CompressFormat.PNG, 60, out);
			byte[] buf = out.toByteArray();
			String pic = Base64.encodeToString(buf, Base64.DEFAULT);
			ArrayList<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("status", status));
			params.add(new BasicNameValuePair("pic", pic));

			post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));

			rsp = client.execute(post);
			Log.i("FSLog", rsp.getStatusLine().getStatusCode() + "");
			if (rsp.getStatusLine().getStatusCode() == 200) {
				String newWeibo = EntityUtils
						.toString(rsp.getEntity(), "utf-8");
				Log.i("FSLog", newWeibo);
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	// 加载公共微博
	public ArrayList<WeiboInfo> loadAllWeiboData() {

		ArrayList<WeiboInfo> weiboList = new ArrayList<>();
		oauth_token = AccessTokenKeeper.readAccessToken(context).getToken();
		String weiboUrl = "https://api.weibo.com/2/statuses/public_timeline.json?access_token="
				+ oauth_token;

		weiboList = getWeiboList(weiboList, weiboUrl);
		return weiboList;
	}
	
	// 加载用户发布的微博数据
	public ArrayList<WeiboInfo> loadUserWeiboData() {

		ArrayList<WeiboInfo> weiboList = new ArrayList<>();
		oauth_token = AccessTokenKeeper.readAccessToken(context).getToken();
		String weiboUrl = "https://api.weibo.com/2/statuses/user_timeline.json?access_token="
				+ oauth_token;

		weiboList = getWeiboList(weiboList, weiboUrl);
		return weiboList;
	}

	private ArrayList<WeiboInfo> getWeiboList(ArrayList<WeiboInfo> weiboList,
			String weiboUrl) {
		try {
			client = new DefaultHttpClient();
			get = new HttpGet(weiboUrl);

			rsp = client.execute(get);

			if (rsp.getStatusLine().getStatusCode() == 200) {

				weiboJson = EntityUtils.toString(rsp.getEntity(), "utf-8");

				JSONObject object = new JSONObject(weiboJson);
				if (object != null) {
					JSONArray jsonArray = object.getJSONArray("statuses");
					int length = jsonArray.length();

					for (int i = 0; i < length; i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						if (jsonObject != null) {
							WeiboInfo weibo = WeiboInfo.parse(jsonObject);
							weiboList.add(weibo);
						}
					}

				}
				return weiboList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从流中获得服务器返回的字符串数据
	 * 
	 * @param response
	 * @return String
	 * @throws IOException
	 */
	private String getData(HttpResponse response) throws IOException {
		InputStream is = response.getEntity().getContent();
		Reader reader = new BufferedReader(new InputStreamReader(is), 4000);
		StringBuilder buffer = new StringBuilder();
		try {
			char[] tmp = new char[1024];
			int l;
			while ((l = reader.read(tmp)) != -1) {
				buffer.append(tmp, 0, l);
			}
		} finally {
			reader.close();
		}
		return buffer.toString();
	}

	// 得到用户信息
	public User getUser() {
		oauth_token = AccessTokenKeeper.readAccessToken(context).getToken();
		userid = AccessTokenKeeper.readAccessToken(context).getUid();
		String userUrl = "https://api.weibo.com/2/users/show.json?access_token="
				+ oauth_token + "&uid=" + userid;
		String userJson = null;
		try {
			client = new DefaultHttpClient();

			get = new HttpGet(userUrl);

			rsp = client.execute(get);

			if (rsp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				userJson = EntityUtils.toString(rsp.getEntity(), "utf-8");
				User user = new User();
				user = user.parse(userJson);
				return user;
			}

		} catch (IOException e) {
			e.printStackTrace();

		}
		return null;
	}

	/**
	 * 使用当前时间戳拼接一个唯一的文件名
	 * 
	 * @param format
	 * @return
	 */
	public static String getFileName() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_SS");
		return format.format(new Timestamp(System.currentTimeMillis()));
	}

	/**
	 * 获取照相机使用的目录
	 * 
	 * @return
	 */
	public static String getCamerPath() {
		return Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_DCIM).toString();
	}

	/**
	 * 获取SD卡中最新图片路径
	 * 
	 * @return
	 */
	public static String getLatestImage(Activity activity) {
		String latestImage = null;
		String[] items = { MediaStore.Images.Media._ID,
				MediaStore.Images.Media.DATA };
		Cursor cursor = activity.managedQuery(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, items, null,
				null, MediaStore.Images.Media._ID + " desc");

		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				latestImage = cursor.getString(1);
				break;
			}
		}

		return latestImage;
	}

	/**
	 * 根据Uri获得图片的绝对路径
	 * 
	 * @param uri
	 * @return String[]{imgNo,imgPath,imgName,imgSize}
	 */
	public static String[] getAbsoluteImagePath(Activity activity, Uri uri) {

		Cursor cursor = activity.getContentResolver().query(uri, null, null,
				null, null);
		cursor.moveToFirst();
		String imgNo = cursor.getString(0); // 图片编号
		String imgPath = cursor.getString(1); // 图片文件路径
		String imgSize = cursor.getString(2); // 图片大小
		String imgName = cursor.getString(3); // 图片文件名
		return new String[] { imgNo, imgPath, imgName, imgSize };
	}

}

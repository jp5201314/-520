package com.sina.weibo.sdk.demo.oauth;

import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.demo.HomeActivity;
import com.sina.weibo.sdk.demo.R;
import com.sina.weibo.sdk.demo.keepoauthinformation.AccessTokenKeeper;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 * 用于微博的认证，在此使用sso认证
 * 
 * @author Administrator
 * 
 */
@SuppressLint("SimpleDateFormat")
public class OauthActivity extends Activity {

	private Intent intent = null;
	private AlertDialog.Builder build;
	private AlertDialog ad;

	/*
	 * 注意：SsoHandler是仅当sdk支持SSo时有效
	 */
	private SsoHandler mSsoHandler;
	/**
	 * 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
	 */
	private Oauth2AccessToken mAccessToken = null;

	private AuthInfo mAuthInfo;
	// 判断是否具有有效的AccessToken信息
	private static boolean flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.oauth);

		mAuthInfo = new AuthInfo(this, Constants.APP_KEY,
				Constants.REDIRECT_URL, Constants.SCOPE);

		mSsoHandler = new SsoHandler(OauthActivity.this, mAuthInfo);
		intent = new Intent(OauthActivity.this, HomeActivity.class);

	}

	@Override
	protected void onResume() {
		super.onResume();
		// 调用开始官方认证
		startApprove();
	}

	// 开始官方认证
	public void startApprove() {
		// 弹出AlertDialog来提示用户进入官网认证授权
		build = new AlertDialog.Builder(this)
				.setIcon(R.drawable.ic_com_sina_weibo_sdk_logo)
				.setTitle(R.string.oauth_dialog_title)
				.setMessage(R.string.oauth_dialog_message)
				.setPositiveButton(R.string.oauth_dialog_button,
						new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								new Thread(new Runnable() {
									@Override
									public void run() {
										/**
										 * SSO授权，All IN ONE
										 * 手机安装了微博客户端就使用客户端授权，没有则进行网页授权
										 */
										mSsoHandler
												.authorize(new AuthListener());

									}
								}).start();
							}
						})
				.setNegativeButton(R.string.cancelweibo, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 取消授权
						ad.cancel();
						Toast.makeText(OauthActivity.this,
								R.string.cancle_oauth, Toast.LENGTH_SHORT)
								.show();

						startActivity(intent);
						// 设定Activity跳转时的动画
						overridePendingTransition(android.R.anim.fade_in,
								android.R.anim.slide_out_right);
						finish();
					}
				});
		ad = build.create();
		ad.show();

		// 从AccessTokenKeepter中读取上次保存的AccessToken信息
		// 第一次启动本应用，AccessToken不可用

		mAccessToken = AccessTokenKeeper.readAccessToken(this);

		// 如果获取到的mAccessToken信息是有效的就立即更新Token信息
		if (mAccessToken.isSessionValid()) {
			updateTokenView(true);
		}
	}

	/**
	 * 微博认证授权回调类。 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用
	 * {@link SsoHandler#authorizeCallBack} 后， 该回调才会被执行。 2. 非 SSO
	 * 授权时，当授权结束后，该回调就会被执行。 当授权成功后，请保存该 access_token、expires_in、uid 等信息到
	 * SharedPreferences 中。
	 */
	class AuthListener implements WeiboAuthListener {
		// 授权完成
		@Override
		public void onComplete(Bundle values) {

			// 从Bundle中解析出Token
			mAccessToken = Oauth2AccessToken.parseAccessToken(values);

			// 从mAccessThoken对象中拿到用户输入的电话信息
			String phoneNum = mAccessToken.getPhoneNum();

			// 判断mAccessToken是否有效
			if (mAccessToken.isSessionValid()) {
				flag = false;
				// 显示Token信息
				updateTokenView(flag);

				// 保存Token的信息到AccessTokenKeepter中
				AccessTokenKeeper.writeAccessToken(OauthActivity.this,
						mAccessToken);
				// 弹出吐司，提示授权成功
				Toast.makeText(OauthActivity.this, R.string.success_oauth,
						Toast.LENGTH_SHORT).show();
				// 第一次授权成功后，发送授权成功参数，当收到授权成功就添加账号信息
				intent.putExtra("oauth_success", "授权成功");
				startActivity(intent);
				// 设定Activity跳转时的动画
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.slide_out_right);
				finish();
			} else {
				// 以下几种情况，您会收到 Code：
				// 1. 当您未在平台上注册的应用程序的包名与签名时；
				// 2. 当您注册的应用程序包名与签名不正确时；
				// 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
				String code = values.getString("code");
				String message = getString(R.string.fail_oauth);
				if (!TextUtils.isEmpty(code)) {
					message = message + "\nObtained the code: " + code;
				}
				Toast.makeText(OauthActivity.this, message, Toast.LENGTH_LONG)
						.show();
			}
		}

		// 取消授权
		@Override
		public void onCancel() {
			// 弹出对话框取消授权
			Toast.makeText(OauthActivity.this, R.string.cancle_oauth,
					Toast.LENGTH_LONG).show();
		}

		/* 认证过程中抛出异常 */
		@Override
		public void onWeiboException(WeiboException e) {
			// 显示出抛出的异常信息
			Toast.makeText(OauthActivity.this, "认证抛出的异常信息为" + e.getMessage(),
					Toast.LENGTH_LONG).show();
		}
	}

	/* 判断是否当前已有Token并且合法，显示出当前的Token值 */
	private void updateTokenView(boolean hasExist) {
		// 设定从mAccessToken中得到的时间的格式
		String date = new SimpleDateFormat("yy:MM:dd--hh:mm:ss")
				.format(new java.util.Date(mAccessToken.getExpiresTime()));
		// 获取字符的显示格式
		String format = getString(R.string.weibosdk_demo_token_to_string_format_1);

		// 获得有一定格式的Token信息
		String message = String.format(format, mAccessToken.getToken(), date);
		// 原先存在Token在有效期，未注销
		if (hasExist) {
			message = getString(R.string.token_exsit) + "\n" + message;
			Toast.makeText(OauthActivity.this, message, Toast.LENGTH_SHORT)
					.show();
		} else {
			// 第一次认证Token的信息
			Toast.makeText(OauthActivity.this, message, Toast.LENGTH_SHORT)
					.show();
		}

	}

	@Override
	public void onBackPressed() {
		// 取消授权
		ad.cancel();
		Toast.makeText(OauthActivity.this, R.string.cancle_oauth,
				Toast.LENGTH_SHORT).show();

		startActivity(intent);
		// 设定Activity跳转时的动画
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.slide_out_right);

		finish();
	}

	// 提供公有的静态方法来获取SsoHandler对象
	public SsoHandler getSsoHandler() {
		return this.mSsoHandler;
	}

	// 提供公有的静态方法来获取Oauth2AccessToken对象
	public Oauth2AccessToken getOauth2AccessToken() {
		return this.mAccessToken;
	}
}

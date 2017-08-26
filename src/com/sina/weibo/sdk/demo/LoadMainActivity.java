package com.sina.weibo.sdk.demo;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.sina.weibo.sdk.demo.dao.User;
import com.sina.weibo.sdk.demo.dao.UserApiDao;
import com.sina.weibo.sdk.demo.oauth.OauthActivity;
import com.sina.weibo.sdk.demo.util.IsConnectNetWork;

/**
 * 定义开始界面动画和判断网络是否连接
 * 
 * @author Administrator
 * 
 */
public class LoadMainActivity extends Activity {

	private ImageView image = null;

	private List<User> list;

	private UserApiDao dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loadactivity_main);
		image = (ImageView) this.findViewById(R.id.load_image);
	}

	private void showConnectDialog() {
		// 没网络就弹出dialog来设置网络
		new AlertDialog.Builder(this)
				.setIcon(R.drawable.ic_com_sina_weibo_sdk_logo)
				.setTitle(R.string.connectNetwork)
				.setMessage(R.string.connectNetworkHint)
				.setPositiveButton(R.string.network_Dialog_Positive_Button,
						new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								// 跳转到系统设置网络的界面
								Intent intent = new Intent(
										android.provider.Settings.ACTION_WIRELESS_SETTINGS);
								startActivity(intent);
							}
						}).create().show();
	}

	// 跳转到主界面
	private void go2Home() {
		Intent intent = new Intent(LoadMainActivity.this, HomeActivity.class);
		startActivity(intent);
		// 设定Activity跳转时的动画
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.slide_out_right);
		finish();
	}

	// 跳转到认证界面
	private void go2Outh() {
		Intent intent = new Intent(LoadMainActivity.this, OauthActivity.class);
		startActivity(intent);
		// 设定Activity跳转时的动画
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.slide_out_right);
		fristIn();
		finish();
	}

	@Override
	protected void onResume() {
		super.onResume();

		// 认证是否连接到网络
		if (!IsConnectNetWork.checkNetWork(this)) {
			showConnectDialog();
		} else {
			if (isFristIn()) {
				// 设置渐变动画
				AlphaAnimation animation = new AlphaAnimation(0.0f, 0.9f);
				// 设置动画时间2秒
				animation.setDuration(4000);
				// 开始动画
				animation.start();
				// 为图片添加动画效果
				image.setAnimation(animation);
				animation.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {

					}

					@Override
					public void onAnimationRepeat(Animation animation) {

					}

					@Override
					public void onAnimationEnd(Animation animation) {

						// TODO 动画播放完毕后，跳转到授权页面
						go2Outh();
					}
				});

			} else {
				dao = UserApiDao.getUserApiDao(this);
				list = dao.findAllUserInfo();
				if (list==null||list.size()==0) {
					// TODO 跳转到授权页面
					go2Outh();
					finish();
				} else {
					Toast.makeText(LoadMainActivity.this, R.string.userexist,
							Toast.LENGTH_SHORT).show();
					go2Home();
					finish();
				}
			}
		}
	}

	// TODO 是否第一次进入,通过SharedPreference来记录是否第一次进入程序
	public boolean isFristIn() {
		// 创建SharedPreference来取出记录
		SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
		boolean isCheckedFristIn = preferences.getBoolean("isFristIn", true);
		return isCheckedFristIn;
	}

	public void fristIn() {
		SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("isFristIn", false);
		editor.commit();
	}

}

package com.sina.weibo.sdk.demo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.demo.fragment.My_Home_Frament;
import com.sina.weibo.sdk.demo.fragment.My_Information_Fragment;
import com.sina.weibo.sdk.demo.fragment.My_Me_Frament;
import com.sina.weibo.sdk.demo.fragment.My_More_Fragment;
import com.sina.weibo.sdk.demo.fragment.My_Search_Fragment;
import com.sina.weibo.sdk.demo.service.GetDataService;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;

public class HomeActivity extends Activity{
	private RadioButton tabbar_home, tabbar_message, tabbar_me, tabbar_discove,
			tabbar_more;
	private List<RadioButton> radioButtonList;
	private FragmentManager fragmentManager = null;
	private FragmentTransaction fragmentTransaction = null;
	private My_Home_Frament home_frament;
	private My_Information_Fragment information_fragment;
	private My_Me_Frament me_frament;
	private My_Search_Fragment search_fragment;
	private My_More_Fragment more_frament;
	private FrameLayout tabbar_frament = null;
	private RadioGroup radioGroup = null;

	private boolean isClicked = false;

	private long curTime, lastTime;

	// 收到认证界面传递过来的Intent
	private Intent intent;

	// 发送广播的Intent
	private Intent sd_broadcast;
	// 授权成功
	private String oauth_success;
	// 加载主界面的信号常量
	private static final int LOADHOME = 200;

	private TextView tv_show_home_title;

	public static Handler handler;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabbar);

		// 发送认证成功广播
		sendAuthorizeBroadcast();
		// 得到控件实例
		getWidgetInstance();
		// 得到RadioButton集合
		getRadioButtonList();
		
		// 加载Fragment
		loadFragment();

	}

	// 发送认证成功广播
	private void sendAuthorizeBroadcast() {
		intent = this.getIntent();
		oauth_success = intent.getStringExtra("oauth_success");
		String oauth_failed = intent.getStringExtra("cancle");
		if (oauth_success != null && oauth_success.equals("授权成功")) {
			sd_broadcast = new Intent();
			sd_broadcast.setAction("get.oauth.id.info");

			// 发送有序广播
			sendOrderedBroadcast(sd_broadcast, "get.oauth");
		}
	}

	// 得到radioButton集合
	private void getRadioButtonList() {

		radioButtonList = new ArrayList<>();

		radioButtonList.add(tabbar_home);

		radioButtonList.add(tabbar_message);

		radioButtonList.add(tabbar_me);

		radioButtonList.add(tabbar_discove);

		radioButtonList.add(tabbar_more);
	}

	// 修改RadioButton状态
	private void changeRadioButtonStatus(int position) {
		for (int i = 0; i < radioButtonList.size(); i++) {
			if (i == position) {
				radioButtonList.get(position).setBackgroundResource(
						R.color.aqua);

			} else {
				radioButtonList.get(i).setBackgroundResource(R.color.gray);
			}
		}
	}

	private void getWidgetInstance() {

		tabbar_home = (RadioButton) this.findViewById(R.id.tabbar_home);

		tabbar_message = (RadioButton) this.findViewById(R.id.tabbar_message);

		tabbar_me = (RadioButton) this.findViewById(R.id.tabbar_me);

		tabbar_discove = (RadioButton) this.findViewById(R.id.tabbar_discove);

		tabbar_more = (RadioButton) this.findViewById(R.id.tabbar_more);
	}

	// 加载Fragment到界面上
	public void loadFragment() {
		home_frament = new My_Home_Frament();
		information_fragment = new My_Information_Fragment();
		me_frament = new My_Me_Frament();
		search_fragment = new My_Search_Fragment();
		more_frament = new My_More_Fragment();
		tabbar_frament = (FrameLayout) this
				.findViewById(android.R.id.tabcontent);

		// 获取到管理该程序中的Fragment的管理器,里面所有的Fragment都是在里面进行管理的
		fragmentManager = getFragmentManager();

		// 开启事务
		fragmentTransaction = fragmentManager.beginTransaction();

		// 将指定的Fragment对象放到制定的容器View的containerViewId上去
		fragmentTransaction.add(android.R.id.tabcontent, home_frament);
		// 提交事务
		fragmentTransaction.commit();

		radioGroup = (RadioGroup) this.findViewById(R.id.main_radio111);

		radioGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup radioGroup, int i) {
						switch (i) {
						case R.id.tabbar_home:
							fragmentManager = getFragmentManager();
							fragmentTransaction = fragmentManager
									.beginTransaction();

							// 使用现有的Fragment对象来替代现有的Fragment，将指定的Fragment来加载到相应的容器型View上
							fragmentTransaction.replace(
									android.R.id.tabcontent, home_frament);
							// 提交事务
							fragmentTransaction.commit();
							changeRadioButtonStatus(0);
							break;
						case R.id.tabbar_message:

							fragmentManager = getFragmentManager();
							fragmentTransaction = fragmentManager
									.beginTransaction();
							fragmentTransaction.replace(
									android.R.id.tabcontent,
									information_fragment);
							fragmentTransaction.commit();
							changeRadioButtonStatus(1);
							break;
						case R.id.tabbar_me:

							fragmentManager = getFragmentManager();
							fragmentTransaction = fragmentManager
									.beginTransaction();
							fragmentTransaction.replace(
									android.R.id.tabcontent, me_frament);
							fragmentTransaction.commit();
							changeRadioButtonStatus(2);
							break;
						case R.id.tabbar_discove:

							fragmentManager = getFragmentManager();
							fragmentTransaction = fragmentManager
									.beginTransaction();
							fragmentTransaction.replace(
									android.R.id.tabcontent, search_fragment);
							fragmentTransaction.commit();
							changeRadioButtonStatus(3);
							break;
						case R.id.tabbar_more:
							fragmentManager = getFragmentManager();
							fragmentTransaction = fragmentManager
									.beginTransaction();
							fragmentTransaction.replace(
									android.R.id.tabcontent, more_frament);
							fragmentTransaction.commit();
							changeRadioButtonStatus(4);
							break;
						}
					}
				});
	}

	// 连续点击两次退出当前Activity
	@Override
	public void onBackPressed() {
		if (isClicked) {
			curTime = System.currentTimeMillis();
			if (curTime - lastTime < 2000) {
				finish();
			} else {
				// 超过两秒后将现在的点击作为第一次点击，第一次点击的时间就是当前的时间
				lastTime = curTime;
				isClicked = true;
				Toast.makeText(this, "再点击一次退出程序!", Toast.LENGTH_SHORT).show();
			}
		} else {
			isClicked = true;
			lastTime = System.currentTimeMillis();
			Toast.makeText(this, "再点击一次退出程序!", Toast.LENGTH_SHORT).show();
		}
	}

	
}

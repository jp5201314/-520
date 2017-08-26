package com.sina.weibo.sdk.demo.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.demo.R;
import com.sina.weibo.sdk.demo.ShowWeiboActivity;
import com.sina.weibo.sdk.demo.WriteWeiboActivity;
import com.sina.weibo.sdk.demo.adapter.WeiboListAdapter;
import com.sina.weibo.sdk.demo.dao.User;
import com.sina.weibo.sdk.demo.dao.UserApiDao;
import com.sina.weibo.sdk.demo.dao.WeiboInfo;
import com.sina.weibo.sdk.demo.util.Tools;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;

/**
 * Created by Administrator on 2016/7/30 0030.
 */
public class My_Home_Frament extends Fragment implements OnItemClickListener,
		OnClickListener {

	private TextView tv_home_name;
	private ListView weiboList;
	private View view;
	private User user;
	private List<User> list;
	private WeiboListAdapter adapter;
	private static ArrayList<WeiboInfo> weiboInfoList;
	private UserApiDao dao;
	private Tools tool;
	private StatusesAPI status;
	private Oauth2AccessToken mAccessToken = null;
	private Handler hanlder;
	AlertDialog.Builder builder;
	AlertDialog dialog;
	private ImageButton ib_write_weibo, ib_reload_weibo;

	private ProgressBar pb_reload_weibo;
	// 0表示用户自身发的微博 1表示公共的微博
	public static int flag = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		tool = Tools.getToolsInstance(getActivity());

		view = inflater.inflate(R.layout.home, container, false);
		// 获得加载布局的view
		View dialogView = inflater.inflate(R.layout.progress, null);
		// 获得AlertDialog的builder
		builder = new AlertDialog.Builder(getActivity());
		// 设置弹出框显示的内容
		builder.setView(dialogView);
		// 创建弹出加载框
		dialog = builder.create();
		// 显示加载框
		dialog.show();
		// 初始化控件
		getWidgetInstance();
		// 初始化操作
		init();
		// 为ListView设置项的监听器
		weiboList.setOnItemClickListener(this);

		return view;
	}

	public void init() {

		dao = UserApiDao.getUserApiDao(getActivity());
		if (dao != null) {
			list = dao.findAllUserInfo();
			if (list != null && list.size() > 0) {
				user = list.get(0);
				if (user != null) {
					if (flag == 0) {
						tv_home_name.setText(user.getName());
						/*
						 * mAccessToken =
						 * AccessTokenKeeper.readAccessToken(getActivity());
						 * status = new StatusesAPI(getActivity(),
						 * Constants.APP_KEY, mAccessToken);
						 * status.friendsTimeline(0, 0, 2, 1, false, 0, true,
						 * new myRequestListener());
						 */
						new Thread() {
							public void run() {
								// 得到微博列表数据源
								weiboInfoList = tool.loadUserWeiboData();
								Message msg = Message.obtain();
								msg.arg1 = 200;
								hanlder.sendMessage(msg);
							};
						}.start();

						hanlder = new Handler() {
							@Override
							public void handleMessage(Message msg) {

								super.handleMessage(msg);

								if (msg.arg1 == 200) {
									if (weiboInfoList != null) {
										adapter = new WeiboListAdapter(
												getActivity(), weiboInfoList,
												R.layout.home_item);
										// 为weibolist设置监听器
										weiboList.setAdapter(adapter);
										dialog.cancel();
									}
								}
							}
						};
					} else if (flag == 1) {
						new Thread() {
							public void run() {
								// 得到微博列表数据源
								weiboInfoList = tool.loadAllWeiboData();
								Message msg = Message.obtain();
								msg.arg1 = 200;
								hanlder.sendMessage(msg);
							};
						}.start();

						hanlder = new Handler() {
							@Override
							public void handleMessage(Message msg) {

								super.handleMessage(msg);

								if (msg.arg1 == 200) {
									if (weiboInfoList != null) {
										adapter = new WeiboListAdapter(
												getActivity(), weiboInfoList,
												R.layout.home_item);
										// 为weibolist设置监听器
										weiboList.setAdapter(adapter);
										dialog.cancel();
									}
								}
							}
						};
					}
				}
			}
		}

	}

	// 微博回调接口
	class myRequestListener implements RequestListener {

		@Override
		public void onComplete(String arg0) {
			Log.i("FSLog", arg0);
		}

		@Override
		public void onWeiboException(WeiboException arg0) {
			Log.i("FSLog", arg0.getMessage());
		}
	}

	public void getWidgetInstance() {
		tv_home_name = (TextView) view.findViewById(R.id.tv_home_name);

		weiboList = (ListView) view.findViewById(R.id.home_lv);
		ib_write_weibo = (ImageButton) view
				.findViewById(R.id.btn_home_write_weibo);

		ib_write_weibo.setOnClickListener(this);

		ib_reload_weibo = (ImageButton) view.findViewById(R.id.btn_home_reload);

		ib_reload_weibo.setOnClickListener(this);

		pb_reload_weibo = (ProgressBar) view
				.findViewById(R.id.progressbar_home_reload);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		WeiboInfo weibo = weiboInfoList.get(position);
		Intent intent = new Intent(getActivity(), ShowWeiboActivity.class);
		intent.putExtra("weibo", weibo);
		startActivity(intent);
		getActivity().overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home_write_weibo:
			Toast.makeText(getActivity(), "写微博", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(getActivity(), WriteWeiboActivity.class);
			intent.putExtra("name", "My_Home_Fragment");
			startActivity(intent);
			getActivity().overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
			break;

		case R.id.btn_home_reload:
			Toast.makeText(getActivity(), "重新加载微博", Toast.LENGTH_SHORT).show();
			ib_reload_weibo.setVisibility(View.GONE);
			pb_reload_weibo.setVisibility(View.VISIBLE);

			new Thread() {
				public void run() {
					if (flag == 0) {
						// 得到微博列表数据源
						weiboInfoList = tool.loadUserWeiboData();
						Message msg = Message.obtain();
						msg.arg1 = 200;
						hanlder.sendMessage(msg);
					} else if (flag == 1) {
						// 得到微博列表数据源
						weiboInfoList = tool.loadAllWeiboData();
						Message msg = Message.obtain();
						msg.arg1 = 200;
						hanlder.sendMessage(msg);
					}

				};
			}.start();

			hanlder = new Handler() {
				@Override
				public void handleMessage(Message msg) {

					super.handleMessage(msg);

					if (msg.arg1 == 200) {
						if (weiboInfoList != null) {
							adapter = new WeiboListAdapter(getActivity(),
									weiboInfoList, R.layout.home_item);
							// 为weibolist设置监听器
							weiboList.setAdapter(adapter);
							ib_reload_weibo.setVisibility(View.VISIBLE);
							pb_reload_weibo.setVisibility(View.GONE);
						}
					}
				}
			};
			break;
		default:
			break;
		}
	}

}

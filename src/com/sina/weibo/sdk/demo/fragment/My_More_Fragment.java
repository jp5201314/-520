package com.sina.weibo.sdk.demo.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.Toast;

import com.sina.weibo.sdk.demo.AccountManagerActivity;
import com.sina.weibo.sdk.demo.HomeActivity;
import com.sina.weibo.sdk.demo.R;

/**
 * Created by Administrator on 2016/7/30 0030.
 */
public class My_More_Fragment extends Fragment implements OnClickListener {

	// 更多界面的公共微博
	private TableRow more_allweibo;
	// 更多界面的我的微博
	private TableRow more_myweibo;

	// 更多界面的账号管理
	private TableRow more_nm;

	// 更多界面的寻找朋友
	private TableRow more_sinkFriend;

	// 更多界面的邀请朋友
	private TableRow more_inviteFriend;

	// 更多界面的设置
	private TableRow more_setting;

	// 更多界面的增加评论
	private TableRow more_addcomment;

	// 更多界面的检查更新
	private TableRow more_checkupdate;

	// 更多界面的关于
	private TableRow more_about;

	// 获取当前的布局文件转化成的View对象
	private View view;

	// 定义一个Intent
	private Intent intent;

	// 得到布局文件转化为View的对象
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.more, container, false);
		// 获取所有控件实例
		getWidgetInstance();
		return view;
	}

	// 获取所有控件实例，并注册监听
	private void getWidgetInstance() {

		// 获取更多界面的账号管理的公共微博的对象
		more_allweibo = (TableRow) view
				.findViewById(R.id.more_page_row_allweibo);

		// 获取到更多界面的我的微博的对象
		more_myweibo = (TableRow) view
				.findViewById(R.id.more_page_row0_myweibo);
		// 获取到更多界面的账号管理的控件对象
		more_nm = (TableRow) view
				.findViewById(R.id.more_page_row1_myNumManager);

		// 获取到更多界面的我的微博的对象
		more_sinkFriend = (TableRow) view
				.findViewById(R.id.more_page_row2_sinkFriend);
		// 获取到更多界面的账号管理的控件对象
		more_inviteFriend = (TableRow) view
				.findViewById(R.id.more_page_row3_inviteFriend);

		// 获取到更多界面的我的微博的对象
		more_setting = (TableRow) view
				.findViewById(R.id.more_page_row4_setting);
		// 获取到更多界面的账号管理的控件对象
		more_addcomment = (TableRow) view
				.findViewById(R.id.more_page_row5_addcomment);

		// 获取到更多界面的我的微博的对象
		more_checkupdate = (TableRow) view
				.findViewById(R.id.more_page_row6_checkupdate);

		// 获取到更多界面的账号管理的控件对象
		more_about = (TableRow) view.findViewById(R.id.more_page_row7_about);

		// 为所有控件设置监听器
		more_myweibo.setOnClickListener(this);

		more_nm.setOnClickListener(this);

		more_sinkFriend.setOnClickListener(this);

		more_inviteFriend.setOnClickListener(this);

		more_setting.setOnClickListener(this);

		more_addcomment.setOnClickListener(this);

		more_checkupdate.setOnClickListener(this);

		more_about.setOnClickListener(this);

	}

	// 为控件对象设置监听
	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.more_page_row_allweibo:
			Toast.makeText(getActivity(), "more_page_row_allweibo",
					Toast.LENGTH_SHORT).show();
			new My_Home_Frament().flag = 1;

			break;
		case R.id.more_page_row0_myweibo:
			Toast.makeText(getActivity(), "more_page_row0_myweibo",
					Toast.LENGTH_SHORT).show();
			new My_Home_Frament().flag = 0;

			break;

		case R.id.more_page_row1_myNumManager:
			Toast.makeText(getActivity(), "more_page_row1_myNumManager",
					Toast.LENGTH_SHORT).show();
			intent = new Intent(getActivity(), AccountManagerActivity.class);
			startActivity(intent);

			break;

		case R.id.more_page_row2_sinkFriend:
			Toast.makeText(getActivity(), "more_page_row2_sinkFriend",
					Toast.LENGTH_SHORT).show();
			break;

		case R.id.more_page_row3_inviteFriend:
			Toast.makeText(getActivity(), "more_page_row3_inviteFriend",
					Toast.LENGTH_SHORT).show();
			break;

		case R.id.more_page_row4_setting:
			Toast.makeText(getActivity(), "more_page_row4_setting",
					Toast.LENGTH_SHORT).show();
			break;

		case R.id.more_page_row5_addcomment:
			Toast.makeText(getActivity(), "more_page_row5_addcomment",
					Toast.LENGTH_SHORT).show();
			break;

		case R.id.more_page_row6_checkupdate:
			Toast.makeText(getActivity(), "more_page_row6_checkupdate",
					Toast.LENGTH_SHORT).show();
			break;

		case R.id.more_page_row7_about:
			Toast.makeText(getActivity(), "more_page_row7_about",
					Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}

}

package com.sina.weibo.sdk.demo.fragment;

import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sina.weibo.sdk.demo.R;
import com.sina.weibo.sdk.demo.dao.User;
import com.sina.weibo.sdk.demo.dao.UserApiDao;


/**
 * Created by Administrator on 2016/7/30 0030.
 */
public class My_Me_Frament extends Fragment implements OnClickListener {

	
	private View view;
	private EditText head_decription_selfinfo;
	private TextView tv_msg_name, userAddress_selfinfo, userNum_selfinfo,
			fans_selfinfo, topic_selfinfo, attention_selfinfo,
			microblog_selfinfo, collect_selfinfo, blacklist_selfinfo;
	private ImageView head_image_selfinfo;
	private Button head_edit_selfinfo;

	private UserApiDao dao;
	private List<User> list;

	private String str;

	private User user;

	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.selfinfo, container, false);
		getAllWidget();
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		dao = UserApiDao.getUserApiDao(getActivity());
		list = dao.findAllUserInfo();
		if (list != null && list.size() > 0) {
			user = list.get(0);
			if (user != null) {
				tv_msg_name.setText(user.getName());
				
				head_decription_selfinfo.setText(user.getDescription());
				head_image_selfinfo.setImageDrawable(user.getUser_head());
				userAddress_selfinfo.setText(user.getLocation());
				userNum_selfinfo.setText(user.getScreen_name());
				fans_selfinfo.setText(user.getFollowers_count() + "");
				topic_selfinfo.setText(user.getFriends_count() + "");
				attention_selfinfo.setText(user.getFriends_count() + "");
				microblog_selfinfo.setText(user.getStatuses_count() + "");
				collect_selfinfo.setText(user.getFavourites_count() + "");
				// blacklist_selfinfo.setText(text);
			}
		}
	}

	// 获取所有的控件对象
	public void getAllWidget() {
		tv_msg_name = (TextView) view.findViewById(R.id.tv_msg_name);
		head_decription_selfinfo = (EditText) view
				.findViewById(R.id.head_decription_selfinfo);
		userAddress_selfinfo = (TextView) view
				.findViewById(R.id.userAddress_selfinfo);
		userNum_selfinfo = (TextView) view.findViewById(R.id.userNum_selfinfo);
		fans_selfinfo = (TextView) view.findViewById(R.id.fans_selfinfo);
		topic_selfinfo = (TextView) view.findViewById(R.id.topic_selfinfo);
		attention_selfinfo = (TextView) view
				.findViewById(R.id.attention_selfinfo);
		microblog_selfinfo = (TextView) view
				.findViewById(R.id.microblog_selfinfo);
		collect_selfinfo = (TextView) view.findViewById(R.id.collect_selfinfo);
		blacklist_selfinfo = (TextView) view
				.findViewById(R.id.blacklist_selfinfo);
		head_image_selfinfo = (ImageView) view
				.findViewById(R.id.head_image_selfinfo);
		head_edit_selfinfo = (Button) view
				.findViewById(R.id.head_edit_selfinfo);
		head_edit_selfinfo.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		head_decription_selfinfo.setEnabled(true);
		String newStr = head_decription_selfinfo.getText().toString();
		user.setDescription(newStr);
	}

}

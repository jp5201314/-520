package com.sina.weibo.sdk.demo.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sina.weibo.sdk.demo.R;
import com.sina.weibo.sdk.demo.dao.User;
import com.sina.weibo.sdk.demo.dao.UserApiDao;

public class ShowAccountAdapter extends BaseAdapter {

	private Context context;
	// private List<UserInfo> list;
	private List<User> list;
	Drawable drawable;
	private UserApiDao dao;

	// 创建该适配器对象时传入一个上下文对象
	public ShowAccountAdapter(Context context, List<User> list, UserApiDao dao) {
		this.context = context;
		this.list = list;
		this.dao = dao;
	}

	class HolderView {
		ImageView iv_head_image;
		TextView username;
	}
	
	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Object getItem(int position) {

		return list.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		HolderView hv;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.showaccount, null);
			hv = new HolderView();
			hv.iv_head_image = (ImageView) convertView
					.findViewById(R.id.show_account_image);
			hv.username = (TextView) convertView
					.findViewById(R.id.show_account_name);

			convertView.setTag(hv);
		} else {
			hv = (HolderView) convertView.getTag();
		}
		if (list != null) {
			
			hv.iv_head_image
					.setImageDrawable(list.get(position).getUser_head());
			hv.username.setText(list.get(position).getName());
		}
		if (position == 0) {
			hv.username.setBackgroundColor(R.color.azure);
		}
		
		return convertView;
	}
	
}

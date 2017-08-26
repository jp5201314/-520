package com.sina.weibo.sdk.demo.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sina.weibo.sdk.demo.R;
import com.sina.weibo.sdk.demo.dao.WeiboInfo;
import com.sina.weibo.sdk.demo.util.AsyncImageLoader;
import com.sina.weibo.sdk.demo.util.AsyncImageLoader.ImageCallback;

public class WeiboListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<WeiboInfo> weiboList;
	private int resId;
	AsyncImageLoader loader;

	public WeiboListAdapter(Context context, ArrayList<WeiboInfo> weiboList,
			int resId) {
		this.context = context;
		this.weiboList = weiboList;
		this.resId = resId;
		loader = new AsyncImageLoader();
	}

	@Override
	public int getCount() {

		return weiboList.size();
	}

	@Override
	public Object getItem(int position) {

		return weiboList.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(resId, null);
			holder = new ViewHolder();
			holder.userImage = (ImageView) convertView
					.findViewById(R.id.content_head);
			holder.weiboImage = (ImageView) convertView
					.findViewById(R.id.content_image);
			holder.userName = (TextView) convertView
					.findViewById(R.id.content_user);
			holder.showTime = (TextView) convertView
					.findViewById(R.id.content_time);
			holder.weiboText = (TextView) convertView
					.findViewById(R.id.content_text);
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();
		WeiboInfo weibo = weiboList.get(position);
		// 加载头像
		if (weibo != null) {
			Drawable image = loader.loadDrawable(weibo.getUser_icon(),
					holder.userImage, new ImageCallback() {
						@Override
						public void imageLoaded(Drawable drawable,
								ImageView iv, String url) {
							iv.setImageDrawable(drawable);
						}
					});
			if (image == null) {
				holder.userImage.setImageResource(R.drawable.angel);
			} else {
				holder.userImage.setImageDrawable(image);
			}
			// 设置内容，时间，发布微博的姓名
			holder.userName.setText(weibo.getUser_name());
			holder.showTime.setText(weibo.getWeibo_time());
			holder.weiboText.setText(weibo.getWeibo_text());
			// 加载微博图片
			if (weibo.getHavaImage() == true) {
				Drawable content_image = loader.loadDrawable(
						weibo.getImage_url(), holder.userImage,
						new ImageCallback() {
							@Override
							public void imageLoaded(Drawable drawable,
									ImageView iv, String url) {
								iv.setImageDrawable(drawable);
							}
						});
				if (content_image == null) {
					holder.weiboImage.setImageResource(R.drawable.btn_a);
				} else {
					holder.weiboImage.setImageDrawable(content_image);
				}
			}
		}

		return convertView;
	}

	class ViewHolder {
		ImageView userImage;
		ImageView weiboImage;
		TextView userName;
		TextView showTime;
		TextView weiboText;

	}

}

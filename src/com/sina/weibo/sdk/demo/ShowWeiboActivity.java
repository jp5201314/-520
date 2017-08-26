package com.sina.weibo.sdk.demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.demo.dao.WeiboInfo;
import com.sina.weibo.sdk.demo.util.AsyncImageLoader;
import com.sina.weibo.sdk.demo.util.AsyncImageLoader.ImageCallback;

public class ShowWeiboActivity extends Activity implements OnClickListener {

	Intent intent;
	ImageView show_userImage;
	ImageView show_weiboImage;
	TextView show_userName;
	TextView show_showTime;
	TextView show_weiboText;
	AsyncImageLoader imageLoader;

	private ImageButton ib_write_weibo, ib_reload_weibo;

	private ProgressBar pb_reload_weibo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.showweibo);
		getWidgetInstance();
		init();
	}

	private void init() {

		imageLoader = new AsyncImageLoader();

		intent = getIntent();
		WeiboInfo weibo = (WeiboInfo) intent.getSerializableExtra("weibo");
		if (weibo != null) {
			show_userName.setText(weibo.getUser_name());
			show_showTime.setText(weibo.getWeibo_time());
			show_weiboText.setText(weibo.getWeibo_text());
			Drawable head_image = imageLoader.loadDrawable(
					weibo.getUser_icon(), show_userImage, new ImageCallback() {
						@Override
						public void imageLoaded(Drawable drawable,
								ImageView iv, String url) {
							iv.setImageDrawable(drawable);
						}
					});
			if (head_image == null) {
				show_userImage.setImageResource(R.drawable.angel);
			} else {
				show_userImage.setImageDrawable(head_image);
			}
			if (weibo.getHavaImage() == true) {

				Drawable content_image = imageLoader.loadDrawable(
						weibo.getLarge_image_url(), show_weiboImage,
						new ImageCallback() {
							@Override
							public void imageLoaded(Drawable drawable,
									ImageView iv, String url) {
								iv.setImageDrawable(drawable);
							}
						});
				if (content_image != null) {
					show_weiboImage.setImageDrawable(content_image);
				} else {
					show_userImage.setImageResource(R.drawable.btn_a);
				}
			}
		}

	}

	private void getWidgetInstance() {
		ib_write_weibo = (ImageButton) this
				.findViewById(R.id.btn_home_write_weibo);

		ib_write_weibo.setOnClickListener(this);

		ib_reload_weibo = (ImageButton) this.findViewById(R.id.btn_home_reload);

		ib_reload_weibo.setOnClickListener(this);

		pb_reload_weibo = (ProgressBar) this
				.findViewById(R.id.progressbar_home_reload);

		show_userImage = (ImageView) this.findViewById(R.id.show_image_head);
		show_weiboImage = (ImageView) this
				.findViewById(R.id.show_content_image);
		show_userName = (TextView) this.findViewById(R.id.show_content_user);
		show_showTime = (TextView) this.findViewById(R.id.show_content_time);
		show_weiboText = (TextView) this.findViewById(R.id.show_content_text);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home_write_weibo:
			Toast.makeText(this, "写微博", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, WriteWeiboActivity.class);
			intent.putExtra("name","ShowWeiboActivity");
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
			break;

		case R.id.btn_home_reload:
			init();
			break;
		default:
			break;
		}
	}

}

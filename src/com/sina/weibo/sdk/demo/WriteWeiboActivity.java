package com.sina.weibo.sdk.demo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.demo.keepoauthinformation.AccessTokenKeeper;
import com.sina.weibo.sdk.demo.oauth.Constants;
import com.sina.weibo.sdk.demo.util.FileUtils;
import com.sina.weibo.sdk.demo.util.Tools;
import com.sina.weibo.sdk.openapi.StatusesAPI;

public class WriteWeiboActivity extends Activity implements OnClickListener {

	private Bitmap bitmap_Image = null;

	private LinearLayout ll_write;

	private ImageButton choosecamera, chooseimage;

	private ImageView images;

	private Button cancle_send, send_weibo, add_pic;

	private TextView edit_weibo, weibotext_limit;

	private String weiboText;

	private int maxNum;

	private static final int WEIBO_MAX_LENGTH = 140;

	private Tools tool;

	private String pic_path = "";

	private boolean flag = false;

	private View view = null;

	private Handler handler;
	// 写微博成功
	private static final int WRITEWEIBOSUCCESS = 200;
	// 写微博失败
	private static final int WRITEWEIBOFAILED = 400;

	private PopupWindow window = null;
	// 标志发送的微博的样式 0是纯文字 1是上传图片并发布一条微博,2是发布一条微博同时指定上传的图片或图片url
	private int markWeibo = 0;
	/**
	 * 表示去sdcard获得图片
	 */
	private static final int REQUEST_CODE_GETIMAGE_BYSDCARD = 0;
	/**
	 * 表示去相机获得图片
	 */
	private static final int REQUEST_CODE_GETIMAGE_BYCAMERA = 1;

	private String thisLarge = null;

	private ByteArrayOutputStream stream;

	private StatusesAPI sa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.write_weibo);
		tool = Tools.getToolsInstance(this);
		sa = new StatusesAPI(this, Constants.APP_KEY,
				AccessTokenKeeper.readAccessToken(this));
		init();
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == WRITEWEIBOSUCCESS) {
					Log.i("FSLog", flag + "");
					if (flag == true) {
						Toast.makeText(WriteWeiboActivity.this,
								R.string.sendsuccess, Toast.LENGTH_SHORT)
								.show();
						startActivity(new Intent(WriteWeiboActivity.this,
								HomeActivity.class));
						overridePendingTransition(android.R.anim.fade_in,
								android.R.anim.fade_out);
						WriteWeiboActivity.this.finish();
					}

				}
			}
		};
	}

	@SuppressLint("ResourceAsColor")
	private void init() {

		cancle_send = (Button) this.findViewById(R.id.btn_cancle_send);
		cancle_send.setOnClickListener(this);
		send_weibo = (Button) this.findViewById(R.id.btn_send_weibo);
		send_weibo.setOnClickListener(this);
		add_pic = (Button) this.findViewById(R.id.add_pic);
		add_pic.setOnClickListener(this);

		images = (ImageView) this.findViewById(R.id.add_image);

		edit_weibo = (TextView) this.findViewById(R.id.weibo_txt);

		weibotext_limit = (TextView) this.findViewById(R.id.tv_text_limit);

		ll_write = (LinearLayout) this.findViewById(R.id.ll_write_weibo);

		ll_write.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (window != null && window.isShowing()) {
					window.dismiss();
					window = null;
				}
				return false;
			}
		});
		edit_weibo.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				boolean flag = false;
				// 获得输入框的内容
				weiboText = edit_weibo.getText().toString();
				int len = weiboText.length();// 获得输入框的内容长度
				if (len <= WEIBO_MAX_LENGTH) {// 比较已经输入的内容长度是不是超过了规定的长度（140）
					len = WEIBO_MAX_LENGTH - len;// 计算还允许输入内容个数
					weibotext_limit.setTextColor(R.color.chartreuse);// 设置提示text颜色
					if (send_weibo.getVisibility() == View.GONE) {// 判断发送按钮是不是启用状态
						send_weibo.setVisibility(View.VISIBLE);// 显示发送按钮
					}
				} else {
					len = len - WEIBO_MAX_LENGTH;// 计算输入内容超过允许输入的个数
					weibotext_limit.setTextColor(Color.RED);// 设置提示text颜色
					if (send_weibo.getVisibility() == View.VISIBLE) {// 判断发送按钮是不是启用状态
						send_weibo.setVisibility(View.GONE);// 隐藏发送按钮
					}
					flag = true;
				}
				weibotext_limit.setText(flag ? "-" + len : String.valueOf(len));// 设置允许输入内容个数提示内容
			}

			/**
			 * 当输入框内容改变后执行
			 */
			public void afterTextChanged(Editable s) {
			}

			/**
			 * 当输入框内容改前执行
			 */
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_cancle_send:
			Toast.makeText(this, "取消发送", Toast.LENGTH_SHORT).show();
			Intent intent = getIntent();
			if (intent != null) {
				String name = intent.getStringExtra("name");
				if (name != null) {
					if (name.equals("ShowWeiboActivity")) {
						startActivity(new Intent(this, ShowWeiboActivity.class));
					} else if (name.equals("My_Home_Fragment")) {
						startActivity(new Intent(this, HomeActivity.class));
					}
				}
			}

			break;

		case R.id.btn_send_weibo:

			new Thread() {
				public void run() {
					if (weiboText != "") {
						Log.i("FSLog", weiboText + "   " + pic_path);
						if (markWeibo == 0) {
							// flag =
							// tool.sendWeibo(URLEncoder.encode(weiboText));
							String str = sa.uploadSync(weiboText, null, null,
									null);
							if (str != null) {
								flag = true;
							}
						} else if (markWeibo == 1) {
							// flag =
							// tool.sendWeibo(URLEncoder.encode(weiboText),
							// bitmap_Image);
							String str = sa.uploadSync(weiboText, bitmap_Image,
									null, null);
							if (str != null) {
								flag = true;
							}
						} else if (markWeibo == 2) {
							// flag =
							// tool.sendWeibo(URLEncoder.encode(weiboText),
							// pic_path);
							String str = sa.uploadUrlTextSync(weiboText,
									null, pic_path, null, null);
							if (str != null) {
								flag = true;
							}
						}

						if (flag == true) {
							handler.sendEmptyMessage(WRITEWEIBOSUCCESS);
						} else {
							handler.sendEmptyMessage(WRITEWEIBOFAILED);
						}
					}
				}
			}.start();

			break;
		case R.id.add_pic:
			window = new PopupWindow(this);
			LayoutInflater inflater = getLayoutInflater().from(this);
			view = inflater.inflate(R.layout.chooseimage, null, false);
			window.setContentView(view);
			
			int w = this.getWindowManager().getDefaultDisplay().getWidth();
			int h = this.getWindowManager().getDefaultDisplay().getHeight();
			window.setWidth(w);
			window.setHeight(h / 4);
			window.showAtLocation(view, Gravity.BOTTOM, 0, 0);
			view.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (window != null && window.isShowing()) {
						window.dismiss();
						window = null;
					}
					return true;
				}
			});

			
			choosecamera = (ImageButton) view
					.findViewById(R.id.ib_choose_camera);

			choosecamera.setOnClickListener(this);

			chooseimage = (ImageButton) view.findViewById(R.id.ib_choose_image);

			chooseimage.setOnClickListener(this);
			break;

		case R.id.ib_choose_camera:
			markWeibo = 1;
			Toast.makeText(this, "选择相机", Toast.LENGTH_SHORT).show();
			if (window != null && window.isShowing()) {
				window.dismiss();
				window = null;
			}
			Intent cIntent = new Intent("android.media.action.IMAGE_CAPTURE");

			String camerName = Tools.getFileName();
			String fileName = "crazy_" + camerName + ".png";

			File camerFile = new File(Tools.getCamerPath(), fileName);

			pic_path = camerFile.getPath();
			thisLarge = Tools.getLatestImage(WriteWeiboActivity.this);

			Uri originalUri = Uri.fromFile(camerFile);
			cIntent.putExtra(MediaStore.EXTRA_OUTPUT, originalUri);
			startActivityForResult(cIntent, REQUEST_CODE_GETIMAGE_BYCAMERA);
			break;

		case R.id.ib_choose_image:
			markWeibo = 1;
			Toast.makeText(this, "选择图片文件", Toast.LENGTH_SHORT).show();
			if (window != null && window.isShowing()) {
				window.dismiss();
				window = null;
			}
			Intent pIntent = new Intent(Intent.ACTION_GET_CONTENT);
			pIntent.setType("image/*");
			startActivityForResult(pIntent, REQUEST_CODE_GETIMAGE_BYSDCARD);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_GETIMAGE_BYSDCARD) {
			if (resultCode != RESULT_OK) {
				return;
			}
			if (data == null) {
				return;
			}
			String[] imageArray = Tools.getAbsoluteImagePath(this,
					data.getData());

			String suffix = FileUtils.getFileFormat(imageArray[1]);
			if (suffix.equals("")) {
				Toast.makeText(this, "请选择图片文件！", Toast.LENGTH_SHORT).show();
				return;
			}

			// 对取出的图片二次采样
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			File file = new File(imageArray[1]);
			BitmapFactory.decodeFile(file.getPath(), options);

			int width = options.outWidth;
			int height = options.outHeight;
			int widthRate = width / 320;
			int heightRate = height / 480;
			int rate = heightRate > widthRate ? heightRate : widthRate;
			options.inJustDecodeBounds = false;
			options.inSampleSize = rate;
			File file1 = new File(imageArray[1]);
			bitmap_Image = BitmapFactory.decodeFile(file1.getPath(), options);

			if (bitmap_Image != null) {

				images.setVisibility(View.VISIBLE);
				images.setImageBitmap(bitmap_Image);
				pic_path = file.getPath();
				thisLarge = file.getPath();
			}

		} else if (requestCode == REQUEST_CODE_GETIMAGE_BYCAMERA) {// 拍摄图片

			// 对取出的图片二次采样
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			File file = new File(pic_path);
			BitmapFactory.decodeFile(file.getPath(), options);

			int width = options.outWidth;
			int height = options.outHeight;
			int widthRate = width / 320;
			int heightRate = height / 480;
			int rate = heightRate > widthRate ? heightRate : widthRate;
			options.inJustDecodeBounds = false;
			options.inSampleSize = rate;
			File file1 = new File(pic_path);
			bitmap_Image = BitmapFactory.decodeFile(file1.getPath(), options);

			if (bitmap_Image != null) {

				images.setVisibility(View.VISIBLE);
				images.setImageBitmap(bitmap_Image);
				thisLarge = file.getPath();
			}
		}

		images.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(android.content.Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(new File(thisLarge)),
						"image/*");
				startActivity(intent);
			}
		});

		super.onActivityResult(requestCode, resultCode, data);
	}
}

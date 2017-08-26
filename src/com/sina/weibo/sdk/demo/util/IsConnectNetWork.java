package com.sina.weibo.sdk.demo.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.sina.weibo.sdk.demo.LoadMainActivity;
import com.sina.weibo.sdk.demo.R;

public class IsConnectNetWork {

	// 判断是否应用已连接到网络
	public static  boolean checkNetWork(Context context) {
		// 标志变量，根据其值来判断当前是否连接到网络
		boolean flag = false;
		// 获取到网络管理器
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
		// 判断网络管理器是否为空
		if (manager != null) {
			// 获取到网络管理器的所有信息
			NetworkInfo[] net_info = manager.getAllNetworkInfo();
			// 判断网络是否已连接，连接就返回true
			if (net_info != null) {
				for (NetworkInfo networkInfo : net_info) {
					if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
						flag = true;
					}
				}
				if (flag == false) {
					Toast.makeText(context, R.string.nonetwork, 2000).show();
					return flag;
				}
			} else {
				Toast.makeText(context, R.string.nullnetworkinfo, 2000).show();
				return flag;
			}
		} else {
			Toast.makeText(context, R.string.null_networkmanager, 2000).show();
		}
		return flag;
	}

}

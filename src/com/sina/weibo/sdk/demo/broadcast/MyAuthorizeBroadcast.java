package com.sina.weibo.sdk.demo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sina.weibo.sdk.demo.service.GetDataService;

public class MyAuthorizeBroadcast extends BroadcastReceiver {

	private Intent serviceintent;

	@Override
	public void onReceive(Context context, Intent intent) {

		switch (intent.getAction()) {
		case "get.oauth.id.info":
			serviceintent = new Intent(context, GetDataService.class);
			context.startService(serviceintent);
			break;

		default:
			break;
		}
	}
}

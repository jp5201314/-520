package com.sina.weibo.sdk.demo.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Message;
import android.util.Log;

import com.sina.weibo.sdk.demo.HomeActivity;
import com.sina.weibo.sdk.demo.dao.User;
import com.sina.weibo.sdk.demo.dao.UserApiDao;
import com.sina.weibo.sdk.demo.util.Tools;

public class GetDataService extends IntentService {

	private String info;
	private long i;

	private UserApiDao dao;

	private Tools tool;

	// 加载主界面
	private static final int LOADHOME = 200;

	public GetDataService() {
		// 字符串是给HandlerThread线程命名的
		super("getData");
		dao = UserApiDao.getUserApiDao(this);
		tool = Tools.getToolsInstance(this);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		User user = tool.getUser();
		if(dao!=null){
			i = dao.insertUserInfo(user);
		}
		
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}

package com.sina.weibo.sdk.demo;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.sina.weibo.sdk.demo.adapter.ShowAccountAdapter;
import com.sina.weibo.sdk.demo.dao.User;
import com.sina.weibo.sdk.demo.dao.UserApiDao;
import com.sina.weibo.sdk.demo.oauth.OauthActivity;

@SuppressLint("ResourceAsColor")
public class AccountManagerActivity extends Activity implements OnClickListener {

	private ListView account_list;
	private LinearLayout add_account;
	private Button exit_account;

	private Intent oauthIntent;

	private Intent userIntent;
	private ShowAccountAdapter sa;

	private UserApiDao dao;

	private List<User> list;
	private Intent intent2Oauth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account);
		// 初始化控件实例
		getWidgetInstance();
		dao = UserApiDao.getUserApiDao(this);
		list = dao.findAllUserInfo();
		if (list != null && list.size() > 0) {
			sa = new ShowAccountAdapter(this, list, dao);
			sa.notifyDataSetChanged();
			account_list.setAdapter(sa);
		}
		MyItemClickListener listener = new MyItemClickListener();
		account_list.setOnItemClickListener(listener);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.add_account_id:
			Toast.makeText(this, "add_account_id", Toast.LENGTH_SHORT).show();
			intent2Oauth = new Intent(this, OauthActivity.class);
			startActivity(intent2Oauth);
			break;

		case R.id.exit_current_account:
			Toast.makeText(this, "exit_current_account", Toast.LENGTH_SHORT)
					.show();
			// 清除Token信息
			// AccessTokenKeeper.clear(this);

			break;

		default:
			break;
		}
	}

	// 获取所有控件的对象并注册监听
	private void getWidgetInstance() {
		account_list = (ListView) this.findViewById(R.id.add_account_list);

		add_account = (LinearLayout) this.findViewById(R.id.add_account_id);

		exit_account = (Button) this.findViewById(R.id.exit_current_account);

		add_account.setOnClickListener(this);

		exit_account.setOnClickListener(this);
	}
	
	class MyItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			
		}
		
	}
	
	public static class CurrentUser{
		static User curUser;
	}
}

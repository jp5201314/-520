package com.sina.weibo.sdk.demo.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sina.weibo.sdk.demo.R;

/**
 * Created by Administrator on 2016/7/30 0030.
 */
public class My_Information_Fragment extends Fragment {
	private ListView msgList;
	private List <String>msgData;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.message, container, false);
		msgList = (ListView) view.findViewById(R.id.msglist);
		msgData = new ArrayList<String>();
		msgData.add("@我的");
		msgData.add("评论");
		msgData.add("赞");
		
		msgList.setAdapter(new MyMsgAdapter(getActivity(),msgData));
		
		return view;
	}
	
}
class MyMsgAdapter extends BaseAdapter{

	private Context context;
	private List <String>msgData;
	public MyMsgAdapter(Context context,List<String> msgData){
		this.context = context;
		this.msgData = msgData;
	}
	@Override
	public int getCount() {
		
		return msgData.size();
	}

	@Override
	public Object getItem(int position) {
		
		return msgData.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = View.inflate(context, android.R.layout.simple_list_item_1, null);
		TextView text1 = (TextView) convertView.findViewById(android.R.id.text1);
		text1.setText(msgData.get(position));
		return convertView;
	}
	
}

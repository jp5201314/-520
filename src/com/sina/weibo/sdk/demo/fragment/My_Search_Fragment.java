package com.sina.weibo.sdk.demo.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sina.weibo.sdk.demo.R;

/**
 * Created by Administrator on 2016/7/30 0030.
 */
public class My_Search_Fragment extends Fragment {

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View view = inflater.inflate(R.layout.searchinfo, container, false);
		return view;
	}
}

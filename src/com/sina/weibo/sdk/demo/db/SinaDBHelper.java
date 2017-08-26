package com.sina.weibo.sdk.demo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SinaDBHelper extends SQLiteOpenHelper {
	// 构造方法
	public SinaDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);

	}

	// 创建用户数据表
	@Override
	public void onCreate(SQLiteDatabase db) {
		// 创建USERAPI这个用户信息表
		db.execSQL(DBInfo.TableApi.CREATE_USERAPI_INFO_TABLE);
	}

	// 更新数据库版本
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// 删除表结构 delete是删除表中数据
		db.execSQL(DBInfo.TableApi.USERAPI_INFO_DROP);
		onCreate(db);
	}

}

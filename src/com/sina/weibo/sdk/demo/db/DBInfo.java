package com.sina.weibo.sdk.demo.db;

public class DBInfo {
	// 数据库的信息
	public class DB {
		// 数据库名
		public static final String DB_NAME = "crazyit_weibo.db";
		// 数据库的版本号
		public static final int DB_VERSION = 1;
	}


	public class TableApi {
		public static final String TABLEAPI_NAME = "userapi_info";

		public static final String _ID = "_id";
		/** 用户UID（int64） */
		public static final String ID = "id";
		/** 字符串型的用户 UID */
		public static final String IDSTR = "idstr";
		/** 用户昵称 */
		public static final String SCREEN_NAME = "screen_name";
		/** 友好显示名称 */
		public static final String NAME = "name";
		/** 用户所在省级ID */
		public static final String PROVINCE = "province";
		/** 用户所在城市ID */
		public static final String CITY = "city";
		/** 用户所在地 */
		public static final String LOCATION = "location";
		/** 用户个人描述 */
		public static final String DESCRIPTION = "description";
		/** 用户博客地址 */
		public static final String URL = "url";
		/** 用户头像地址，50×50像素 */
		public static final String PROFILE_IMGE_URL = "profile_image_url";
		/** 用户的头像*/
		public static final String USER_HEAD = "user_head";
		/** 用户的微博统一URL地址 */
		public static final String PROFILE_URL = "profile_url";
		/** 用户的个性化域名 */
		public static final String DOMAIN = "domain";
		/** 用户的微号 */
		public static final String WEIHAO = "weihao";
		/** 性别，m：男、f：女、n：未知 */
		public static final String GENDER = "gender";
		/** 粉丝数 */
		public static final String FOLLOWERS_COUNT = "followers_count";
		/** 关注数 */
		public static final String FRIENDS_COUNT = "friends_count";
		/** 微博数 */
		public static final String STATUSES_COUNT = "statuses_count";
		/** 收藏数 */
		public static final String FAVOURITES_COUNT = "favourites_count";
		/** 用户创建（注册）时间 */
		public static final String CREATED_AT = "created_at";
		/** 暂未支持 */
		public static final String FOLLOWING = "following";
		/** 是否允许所有人给我发私信，true：是，false：否 */
		public static final String ALLOW_ALL_ACT_MSG = "allow_all_act_msg";
		/** 是否允许标识用户的地理位置，true：是，false：否 */
		public static final String GEO_ENABLED = "geo_enabled";
		/** 是否是微博认证用户，即加V用户，true：是，false：否 */
		public static final String VERIFIED = "verified";
		/** 暂未支持 */
		public static final String VERIFIED_TRPE = "verified_type";
		/** 用户备注信息，只有在查询用户关系时才返回此字段 */
		public static final String REMARK = "remark";
		/** 用户的最近一条微博信息字段 */
		public static final String STATUS = "status";
		/** 是否允许所有人对我的微博进行评论，true：是，false：否 */
		public static final String ALLOW_ALL_COMMENT = "allow_all_comment";
		/** 用户大头像地址 */
		public static final String AVATAR_LARGE = "avatar_large";
		/** 用户高清大头像地址 */
		public static final String AVATAR_HD = "avatar_hd";
		/** 认证原因 */
		public static final String VERIFIED_REASON = "verified_reason";
		/** 该用户是否关注当前登录用户，true：是，false：否 */
		public static final String FOLLOW_ME = "follow_me";
		/** 用户的在线状态，0：不在线、1：在线 */
		public static final String ONLINE_STATUS = "online_status";
		/** 用户的互粉数 */
		public static final String BI_FOLLOWERS_COUNT = "bi_followers_count";
		/** 用户当前的语言版本，zh-cn：简体中文，zh-tw：繁体中文，en：英语 */
		public static final String LANG = "lang";

		public static final String CREATE_USERAPI_INFO_TABLE = "create table if not exists "
				+ TABLEAPI_NAME
				+ "("
				+ _ID
				+ " integer primary key autoincrement ,"
				+ ID
				+ " text UNIQUE,"
				+ IDSTR
				+ " text UNIQUE ,"
				+ SCREEN_NAME
				+ " text UNIQUE ,"
				+ NAME
				+ " text UNIQUE ,"
				+ PROVINCE
				+ " integer,"
				+ CITY
				+ " integer ,"
				+ LOCATION
				+ " text ,"
				+ DESCRIPTION
				+ " text ,"
				+ URL
				+ " text ,"
				+ PROFILE_IMGE_URL
				+ " text ,"
				+USER_HEAD
				+" BLOB ,"
				+ PROFILE_URL
				+ " text ,"
				+ DOMAIN
				+ " text ,"
				+ WEIHAO
				+ " text ,"
				+ GENDER
				+ " text ,"
				+ FOLLOWERS_COUNT
				+ " integer ,"
				+ FRIENDS_COUNT
				+ " integer ,"
				+ STATUSES_COUNT
				+ " integer ,"
				+ FAVOURITES_COUNT
				+ " integer ,"
				+ CREATED_AT
				+ " text ,"
				+ FOLLOWING
				+ " text ,"
				+ ALLOW_ALL_ACT_MSG
				+ " text ,"
				+ GEO_ENABLED
				+ " text ,"
				+ VERIFIED
				+ " text ,"
				+ VERIFIED_TRPE
				+ " integer ,"
				+ REMARK
				+ " text ,"
				+ STATUS
				+ " text ,"
				+ ALLOW_ALL_COMMENT
				+ " text ,"
				+ AVATAR_LARGE
				+ " text ,"
				+ AVATAR_HD
				+ " text ,"
				+ VERIFIED_REASON
				+ " text ,"
				+ FOLLOW_ME
				+ " integer ,"
				+ ONLINE_STATUS
				+ " integer ,"
				+ BI_FOLLOWERS_COUNT
				+ " integer ,"
				+ LANG
				+ " text" + ")";
		// 删除用户表语句
		public static final String USERAPI_INFO_DROP = "DROP TABLE"
				+ TABLEAPI_NAME;
	}
}

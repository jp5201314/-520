package com.sina.weibo.sdk.demo.customview;

import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ListView;

public class PullToRefreshListView extends ListView{

	private Context context = null;
	private Paint paint = null;
	public PullToRefreshListView(Context context){
		super(context);
		this.context = context;
	}
	public PullToRefreshListView(Context context,AttributeSet set){
		super(context, set);
		this.context = context;
		 paint = new Paint();
		 
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setColor(color.white);
		canvas.drawColor(com.sina.weibo.sdk.demo.R.color.beige);	
	}

}

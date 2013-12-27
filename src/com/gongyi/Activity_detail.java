package com.gongyi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class Activity_detail extends Activity {  // ��ʾ�����
	String actid;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);//�趨���֣�activity_detail.xml
		TextView title = (TextView) findViewById(R.id.activity_title);//�ӷ�����ȡֵ
		TextView content = (TextView) findViewById(R.id.activity_detail);
		ImageButton baoming = (ImageButton) findViewById(R.id.baoming);
		
		title.setText(getIntent().getExtras().getString("title"));//���ӷ�����ȡ����ֵ����ǰ��title
		content.setText(getIntent().getExtras().getString("content"));
		actid=getIntent().getExtras().getString("actid");
		System.out.println("actid1="+actid);
		
		baoming.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("actid", actid);
				intent.setClass(Activity_detail.this, BaoMing.class);
				startActivity(intent);
			}
		});
	}
}
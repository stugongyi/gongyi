package com.gongyi;

import org.json.JSONException;
import org.json.JSONObject;

import com.gongyi.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Main_Activity extends ListActivity {   //显示活动列表

	JSONObject result = null;
//	String url = "http://10.0.2.2:8080/gongyi/Action";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);//activity_main.xml   ??????
	//	TextView actidb = (TextView) findViewById(R.id.actid);
		//actidb.setVisibility(8);
		// setContentView(R.layout.list);
		// listv = (ListView) findViewById(R.id.title);
		JSONHandle jh = new JSONHandle();
		JSONObject post = new JSONObject();
		try {
			post.put("pageSize", 2);
			post.put("pageIndex", 1);
			post.put("refreshTime", 3);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// post.put("actid", "3");

		// System.out.println(post.toString());

		try {
			result = jh.getJsonRequest(1001,post);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(result.toString());
		// table = getIntent().getExtras().getString("table");
		SimpleAdapter adapter;
		try {
			adapter = new SimpleAdapter(this, jh.getData(result),
					R.layout.listact, new String[] {"title", "content", "orgname", "time" ,"actid" },
					new int[] { R.id.title, R.id.content,R.id.orgname, R.id.time,R.id.actid });
			setListAdapter(adapter);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListenerImpl());
	}

	private class OnItemClickListenerImpl implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long id) {
			// TODO Auto-generated method stub
	//		System.out.print("result="+result.toString());
			TextView title = (TextView) view.findViewById(R.id.title);//从服务器取值
			TextView content = (TextView) view.findViewById(R.id.content);
		//	TextView orgid = (TextView) view.findViewById(R.id.orgid);
			TextView actid = (TextView) view.findViewById(R.id.actid);
			
		//	orgid.setVisibility(8);
		//	actid.setVisibility(8);
		//	actid.setText(result.getString("id"));
			
			//设定跳转，将main的内容获取之后传给Activity_detail
			Intent intent = new Intent();
			intent.setClass(Main_Activity.this, Activity_detail.class);
		//	System.out.println("actid0="+actid.getText().toString());
		//	Bundle bundle = new Bundle();
			//传内容
			intent.putExtra("title",title.getText().toString());
			intent.putExtra("content",content.getText().toString());
		//	intent.putExtra("orgid",result.getString("orgid"));
		//	intent.putExtra("actid",result.getString("id"));
			intent.putExtra("actid",actid.getText().toString());
			startActivity(intent);//跳转
		}
	}

}

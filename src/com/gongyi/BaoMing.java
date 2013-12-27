package com.gongyi;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class BaoMing extends Activity {   //处理报名时的动作
	String url = "http://10.0.2.2:8080/gongyi/Action";
	JSONObject result = null;
	JSONHandle jh = null;
	JSONObject post = null;
	TextView name;
	TextView phone;
	TextView email;
	ImageButton ok;
	ImageButton clear;
	String actid;
	//q..
	private boolean isChinese(String str) {
		for(int i=0;i<str.length();i++){
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(str.charAt(i));
            if (!(ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)) {
                return false;
            }
		}
		return true;
    }
    //q..
	public boolean emailValidation(String email) {
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        return email.matches(regex);
    }
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.baoming);//baoming.xml
		name = (TextView) findViewById(R.id.name);
		phone = (TextView) findViewById(R.id.phone);
		email = (TextView) findViewById(R.id.email);
		ok = (ImageButton) findViewById(R.id.OK);
		clear = (ImageButton) findViewById(R.id.clear);
		jh = new JSONHandle();
		actid = getIntent().getExtras().getString("actid");//将从一个类中传回来的actid参数传回当前的actid
		System.out.println("actid2=" + actid);
		ok.setOnClickListener(new OnClickListener() {//ok的监听器
			@Override
			public void onClick(View v) {
				//q..
				int len=phone.getText().length();
				if(!isChinese("啊啊")){
					Toast.makeText(getApplicationContext(), "姓名错误",Toast.LENGTH_SHORT).show();
				}
				else if(!(len==11)||(len>3&&len<7)) 
					Toast.makeText(getApplicationContext(), "电话号码错误",Toast.LENGTH_SHORT).show();
				else if(!emailValidation(email.getText().toString())){
					     Toast.makeText(getApplicationContext(), "邮箱错误",Toast.LENGTH_SHORT).show();
					 }
				     else{
				         // TODO Auto-generated method stub
				          String cmd = "{\"cmdcode\":\"1002\",\"name\":\""
						  + name.getText().toString() + "\",\"phone\":\""
				     	  + phone.getText().toString() + "\",\"email\":\""
						  + email.getText().toString() + "\",\"actid\":" + actid
						  + "}";
				         try {
					         post = new JSONObject(cmd);
					         result = jh.getJsonRequest(1002, post);
					         // System.out.println(result.toString());
					         if (result.getString("stat").equals("Succ")) {
						         Toast.makeText(getApplicationContext(), "报名成功",Toast.LENGTH_SHORT).show();
						         BaoMing.this.finish();
						         // startActivity(new
						         // Intent(BaoMing.this,Activity_detail.class));
					         } else {
						        Toast.makeText(getApplicationContext(), "报名失败",Toast.LENGTH_SHORT).show();
					           }
				         } catch (Exception e) {
					     // TODO Auto-generated catch block
					     e.printStackTrace();
				     }
				}
			}
		});
		//q..
		clear.setOnClickListener(new OnClickListener() {//clear的监听器
			@Override
			public void onClick(View v) {
				name.setText("");
				phone.setText("");
				email.setText("");
			}
		});
	}
}
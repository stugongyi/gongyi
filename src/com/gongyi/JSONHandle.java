package com.gongyi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONHandle {    //处理与服务器交互及解析JSON数据

	public JSONObject getJsonRequest(int cmdCode, JSONObject cmd)
			throws Exception {
		String url = "http://gongyi.aws.af.cm/Action";
		JSONObject result = null;
		// String con = null;
		StringBuilder sb = new StringBuilder();
		HttpGet httpGet = new HttpGet(url + "?" + "cmdCode=" + cmdCode
				+ "&cmd=" + URLEncoder.encode(cmd.toString(), "UTF-8"));
		HttpClient client = new DefaultHttpClient();
		HttpParams httpParams = client.getParams();
		// httpPost.setEntity(new StringEntity(json.toString()));
		// HttpResponse hr = hc.execute(httpPost);
		httpGet.setHeader("Content-Type", "application/json");
		httpGet.setHeader("Accept", "JSON");
		// 设置网络超时参数
		HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
		HttpConnectionParams.setSoTimeout(httpParams, 5000);

		HttpResponse response = client.execute(httpGet);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					entity.getContent(), "UTF-8"), 8);
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();
		}
		// System.out.println("sb="+sb.toString());
		result = new JSONObject(sb.toString());
		return result;
	}

	public List<Map<String, Object>> getData(JSONObject json) throws JSONException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		//System.out.print(list);
		JSONArray arr = new JSONArray(json.getString("list"));//数组arr将list中知全部找出
		for (int i = 0; i < arr.length(); i++) { //循环arr.length()
			map = new HashMap<String, Object>();
			JSONObject temp = (JSONObject) arr.get(i);
			// System.out.println(arr.length());
			map.put("actid", temp.getString("id"));//将所需的内容取出放到map
			map.put("title", temp.getString("title"));
			map.put("content", temp.getString("content"));
			map.put("orgname", "发布单位:" + temp.getString("orgname"));
			map.put("time", "发布时间:" + temp.getString("time"));
			list.add(map);//
		}
		return list;
	}

}

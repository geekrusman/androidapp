package com.example.project;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Next extends Activity{

	String diss="";
	
	String comb="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.next);
		
		
		
		Intent intent=getIntent();
		String s=intent.getStringExtra(MainActivity.STRING1);
		String s1=intent.getStringExtra(MainActivity.STRING2);
		comb=s.concat(s1);
		GetData();
		
		//textV.setText(diss);
		
		
		
	}
	
	
	
	public void GetData()
	{
		String result="";
		InputStream isr=null;
		try
		{
			HttpClient httpclient=new DefaultHttpClient();
			
			HttpPost httppost= new HttpPost("http://172.168.250.117/getData3.php");
			HttpResponse response=httpclient.execute(httppost);
			HttpEntity entity=response.getEntity();
			isr=entity.getContent();
		}
		catch(Exception e)
		{
			Log.e("log_tag","Error in http connection"+e.toString());
			
		}
		try
		{
			BufferedReader reader= new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
			StringBuilder sb=new StringBuilder();
			String line=null;
			while((line=reader.readLine())!=null)
			{
				sb.append(line+"\n");
			}
			isr.close();
			result=sb.toString();
		}
		catch(Exception e)
		{
			Log.e("log_tag","Error converting result "+e.toString());
		}
		
		try
		{
			
			JSONArray jArray=new JSONArray(result);
			final String[] srt=new String[jArray.length()];
			for(int i=0;i<jArray.length();i++)
			{
				JSONObject json=jArray.getJSONObject(i);
				srt[i]=(json.getString("src").trim()).concat(json.getString("dest").trim());	
			}
			TextView tex=(TextView)findViewById(R.id.textVie);
			TextView tex1=(TextView)findViewById(R.id.textVi);
			tex1.setTextColor(Color.rgb(72, 118, 255));
			tex1.setTextSize(18);
			for(int i=0;i<srt.length;i++)
			{
				if(srt[i].equals(comb))
				{	
					JSONObject json=jArray.getJSONObject(i);
					diss=diss+json.getString("route")+"\n\n";
					
				}
			}
			tex.setText(diss);
			tex.setTextColor(Color.WHITE);
			tex.setTextSize(15);
		}
		catch(Exception e)
		{
			Log.e("log_tag","error parsing data "+e.toString());
		}
		
		
		
	}
	
	

}

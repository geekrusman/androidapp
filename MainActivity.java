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

import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import java.net.InetAddress;


@SuppressLint("NewApi")
public class MainActivity extends Activity {
	
	boolean b;
	public final static String STRING1="com.example.project.STRING1";
	public final static String STRING2="com.example.project.STRING2";
	TextView textView4;
	AutoCompleteTextView autotext;
	AutoCompleteTextView autotext2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StrictMode.enableDefaults();
		TextView textView1=(TextView)findViewById(R.id.textView1);
		textView1.setTextColor(Color.RED);
		TextView textView2=(TextView)findViewById(R.id.textView2);
		textView2.setTextColor(Color.rgb(0, 191, 255));
		TextView textView3=(TextView)findViewById(R.id.textView3);
		textView3.setTextColor(Color.rgb(0, 191, 255));
		
		final Intent intent=new Intent("com.example.project.NEXT");
		
		
		
		
		textView4=(TextView)findViewById(R.id.textView4);
		textView4.setTextColor(Color.RED);
		textView4.setSingleLine();
		final Button button=(Button)findViewById(R.id.button1);
		button.setTextSize(15);
		button.setTextColor(Color.WHITE);
		button.setClickable(true);
	    autotext=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
		autotext2=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView2);
		autotext.setSingleLine();
		autotext2.setSingleLine();
		autotext.setTextSize(15);
		autotext2.setTextSize(15);
		GetData();
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String st=autotext.getText().toString();
				String st1=autotext2.getText().toString();
				String s=st.trim();
				String s1=st1.trim();
				
				if(s.equals("") || s1.equals(""))
				{
					textView4.setText("Fill Each and Every Field");
				}
				else if(s.equals(s1))
				{
					textView4.setText("Source and Destination Same!!!");
				}
				else
				{
					GetData2(s,s1);
					if(b)
					{
						
							
							intent.putExtra(STRING1, s);
							intent.putExtra(STRING2, s1);
							startActivity(intent);
						
					}
				}
			}
		});
	}
	public void GetData()
	{
		String result="";
		InputStream isr=null;
		try
		{
			HttpClient httpclient=new DefaultHttpClient();
			
			HttpPost httppost= new HttpPost("http://172.168.250.117/getData2.php");
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
				srt[i]=json.getString("city");
				
				
			}
			final List<String> list=new ArrayList<String>();
			
			for(int i=0;i<srt.length;i++)
			{
				list.add(srt[i]);
			}
			Collections.sort(list);
			ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,srt);
			autotext.setThreshold(1);
			autotext.setAdapter(adapter);
			autotext2.setThreshold(1);
			autotext2.setAdapter(adapter);
			
			
			
			
		}
		catch(Exception e)
		{
			Log.e("log_tag","error parsing data "+e.toString());
		}
		
	}
	public void GetData2(String s,String s1)
	{
		
		String result="";
		InputStream isr=null;
		try
		{
			HttpClient httpclient=new DefaultHttpClient();
			
			HttpPost httppost= new HttpPost("http://172.168.250.117/getData2.php");
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
				srt[i]=json.getString("city");
				
				
				
			}
			int c1=0;
			int c2=0;
			for(int i=0;i<srt.length;i++)
			{
				if(s.equals(srt[i]))
					c1++;
				if(s1.equals(srt[i]))
					c2++;	
			}
			if(c1==0 && c2==0)
			{
				b=false;
				textView4.setText("Source and Destination Points Invalid");
			}
			else if(c2==0)
			{
				b=false;
				textView4.setText("Destination Point Invalid");
			}
			else if(c1==0)
			{
				b=false;
				textView4.setText("Source Point Invalid");
			}
			else
			{
				b=true;
			}
			
		}
		catch(Exception e)
		{
			Log.e("log_tag","error parsing data "+e.toString());
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

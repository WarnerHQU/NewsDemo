package com.newsdemo.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsDetailActivity extends Activity 
{
	private WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.news_detail_layout);
		
		webView=(WebView) findViewById(R.id.show_detail);
		//设置webView的属性
		//可以支持Javascript脚本语言
		webView.getSettings().setJavaScriptEnabled(true);
		//从一个网页跳转到另外一个网页，目标网页还在当前webView中显示，不打开浏览器
		webView.setWebViewClient(new WebViewClient());
		
		String urlAddress;
		//获取传入的新闻具体路径
		Intent intent=getIntent();
		
		urlAddress=intent.getStringExtra("URL");
		
		webView.loadUrl(urlAddress);
	}
	
}

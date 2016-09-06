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
		//����webView������
		//����֧��Javascript�ű�����
		webView.getSettings().setJavaScriptEnabled(true);
		//��һ����ҳ��ת������һ����ҳ��Ŀ����ҳ���ڵ�ǰwebView����ʾ�����������
		webView.setWebViewClient(new WebViewClient());
		
		String urlAddress;
		//��ȡ��������ž���·��
		Intent intent=getIntent();
		
		urlAddress=intent.getStringExtra("URL");
		
		webView.loadUrl(urlAddress);
	}
	
}

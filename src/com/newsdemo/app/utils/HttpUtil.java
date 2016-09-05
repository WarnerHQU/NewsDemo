package com.newsdemo.app.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 * ȫ������ʡ���ص����ݶ��Ǵӷ������˻�ȡ�ġ�
 * �������ͷ������Ľ���ʽ�ز����ٵģ����Կ�����util������������һ��HttpUtil�ࡣ
 */
public class HttpUtil
{
	public static void sendHttpRequest(final String address,
			                           final HttpCallbackListener listener)
	{
		new Thread(new Runnable()
		{

			public void run()
			{
				// TODO Auto-generated method stub
				HttpURLConnection connection=null;
				try
				{
					URL url=new URL(address);
					connection=(HttpURLConnection)url.openConnection();
					connection.setRequestMethod("GET");
					connection.setReadTimeout(8000);
					connection.setConnectTimeout(8000);
					//�������������ж�ȡ���ݣ������浽StringBuilder��ȥ
					InputStream in=connection.getInputStream();
					BufferedReader reader=new BufferedReader(new InputStreamReader(in));
					StringBuilder response=new StringBuilder();
					String line;
					while((line=reader.readLine())!=null)
					{
						response.append(line);
					}
					if(listener!=null)
					{
						//�ص�onFinish()����
						listener.onFinish(response.toString());
					}
					
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					//e.printStackTrace();
					if(listener!=null)
					{
						//�ص�onError()����
						listener.onError(e);
					}
				}
				finally
				{
					if(connection!=null)
						connection.disconnect();
				}	
			}
			
		}).start();
	}
}
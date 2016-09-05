package com.newsdemo.app;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.newsdemo.app.utils.HttpCallbackListener;
import com.newsdemo.app.utils.HttpUtil;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity
{
	private ListView listView;
	private TextView textView;
	private List<String> dataList=new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	private static final int SHOW_RESPONSE=0;
	private TextView responseText;
	private Handler handler=new Handler()
								{
									public void handleMessage(Message msg)
									{
										switch(msg.what)
										{
										case SHOW_RESPONSE:
											//在这里进行UI操作
											String response=(String)msg.obj;
											responseText.setText(response);
											parseJSONWithJSONObject(response);
											break;
											
										}
									}
								};
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        responseText=(TextView) findViewById(R.id.response_text);
        listView=(ListView) findViewById(R.id.list_view);
        textView=(TextView) findViewById(R.id.title_name);
        textView.setText("谢峰");
       
        dataList.add("item1");
        dataList.add("item2");
        dataList.add("item3");
        dataList.add("item4");
        dataList.add("item5");
        dataList.add("item6");
        dataList.add("item7");
        dataList.add("item8");
        dataList.add("item9");
        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        
        String category="top";
        //发出请求
        String address="http://v.juhe.cn/toutiao/index?type=top&key=bad7cfabe911c929d7fbae195d1ee49c";
        
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener()
							        {

										public void onFinish(String response)//收到数据 
										{
											
											Message message=new Message();
											message.what=SHOW_RESPONSE;
											//将服务器返回的结果存储到message中
											message.obj=response.toString();
											handler.sendMessage(message);
										}

										public void onError(Exception e) 
										{
											runOnUiThread(new Runnable()
											{
												public void run()
												{
											
													Toast.makeText(MainActivity.this,
															"获取数据失败", Toast.LENGTH_SHORT).show();
													
												}
												
											});
										}
						
					});
											
									
    }
    
    /**
     * 解析JSON数据
     * @param jsonData
     */
    private void parseJSONWithJSONObject(String jsonData)
    {
    	try 
    	{
    		//数据清空
    		dataList.clear();
			JSONObject jsonObject=new JSONObject(jsonData);
			JSONObject result=jsonObject.getJSONObject("result");
			String data=result.getString("data");
			JSONArray jsonArray=new JSONArray(data);
			int sum=jsonArray.length();
			for(int i=0;i<jsonArray.length();++i)
			{
				//重要信息的提取
				JSONObject jsonObject2=jsonArray.getJSONObject(i);
				String title=jsonObject2.getString("title");
				dataList.add(title);	
				
				String date=jsonObject2.getString("date");
				String author_name=jsonObject2.getString("author_name");
				String urlAdd=jsonObject2.getString("url");
				String type=jsonObject2.getString("realtype");
			}			
			adapter.notifyDataSetChanged();	
			listView.setSelection(1);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		
		
    }
}
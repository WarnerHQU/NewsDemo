package com.newsdemo.app;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.newsdemo.app.utils.HttpCallbackListener;
import com.newsdemo.app.utils.HttpUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener
{
	private ListView listView;
	private TextView textView;
	
	private List<CategoryItem> dataList=new ArrayList<CategoryItem>();
	
	private CategoryItemAdapter adapter;
	
	private static final int SHOW_RESPONSE=0;
	private TextView responseText;
	private View tempView;
	private LinearLayout ll;
	private HorizontalScrollView hsv;
	//新闻类型
	private String[] categoryList={"头条","社会","国内","国际"
			,"娱乐","体育","军事","科技","财经","时尚"};
	private Map<String,String> categoryCode=new HashMap<String,String>();
	//对控件的设置需要在主线程中进行
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
        
       initData();
        
        responseText=(TextView) findViewById(R.id.response_text);
        listView=(ListView) findViewById(R.id.list_view);
        textView=(TextView) findViewById(R.id.title_name);
        textView.setText("谢峰");
       
        
        tempView=LayoutInflater.from(this).
        		       inflate(R.layout.news_category_layout, null);
        ll=(LinearLayout) tempView.findViewById(R.id.news_category);
        hsv=(HorizontalScrollView) tempView.findViewById(R.id.news_horizontalSV);
        
        for(int i=0;i<categoryList.length;++i)
        {
        	View view=LayoutInflater.from(this).
        			inflate(R.layout.news_category_name_layout, null);
        	TextView txt=(TextView) view.findViewById(R.id.news_category_name);
        	txt.setText(categoryList[i]);
        	ll.addView(view);
        }
        listView.addHeaderView(tempView,null,false);
        
       adapter=new CategoryItemAdapter(this,R.layout.title_item_layout,dataList); 
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        
        //默认启动时是头条top新闻
        String category="top";
        //发出请求
        String address="http://v.juhe.cn/toutiao/index?type=top&key=bad7cfabe911c929d7fbae195d1ee49c";
        sendRequest(address);
        
        hsv.setOnClickListener(new OnClickListener()
						        {

									public void onClick(View v) 
									{
										// TODO Auto-generated method stub
										Toast.makeText(MainActivity.this, v.getId(), Toast.LENGTH_SHORT).show();
									}
						        	
						        });
        
									
    }
    
    /**
     * 初始化数据
     */
    private void initData() 
    {
    	if(categoryCode.isEmpty())
    	{
			categoryCode.put("头条", "top");
			categoryCode.put("社会", "shehui");
			categoryCode.put("国内", "guonei");
			categoryCode.put("国际", "guoji");
			categoryCode.put("娱乐", "yule");
			categoryCode.put("体育", "tiyu");
			categoryCode.put("军事", "junshi");
			categoryCode.put("科技", "keji");
			categoryCode.put("财经", "caijing");
			categoryCode.put("时尚", "shishang");
			
		}
	}
    
    /**
     * 发送网络请求
     * @param address
     */
    private void sendRequest(String address)
    {
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
			//按关键字查找result
			JSONObject result=jsonObject.getJSONObject("result");
			//按关键字查找data
			String data=result.getString("data");
			//根据data的数据成立数据，进行提取
			JSONArray jsonArray=new JSONArray(data);
			int sum=jsonArray.length();
			for(int i=0;i<jsonArray.length();++i)
			{
				
				//重要信息的提取
				JSONObject jsonObject2=jsonArray.getJSONObject(i);
				String title=jsonObject2.getString("title");					
				String date=jsonObject2.getString("date");
				String author_name=jsonObject2.getString("author_name");
				String urlAdd=jsonObject2.getString("url");
				String type=jsonObject2.getString("realtype");
				
				CategoryItem tempCate=new CategoryItem(author_name,date,title,urlAdd);
				dataList.add(tempCate);
			}			
			adapter.notifyDataSetChanged();	
			listView.setSelection(1);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
    }
    
    /**
     *点击ListView时候的响应
     * @param parent
     * @param view
     * @param position
     * @param id
     */
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{
		//因为在ListView之前加了addHeaderView，故点击的时候位置会+1，故要相应地-1
		CategoryItem tempCategoryItem=dataList.get(position-1);
		
		String urlAddress=tempCategoryItem.getNewsUrl();
		
		Intent intent=new Intent(this,NewsDetailActivity.class);
		intent.putExtra("URL", urlAddress);
		startActivity(intent);
		
	}
}
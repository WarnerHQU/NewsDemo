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
	//��������
	private String[] categoryList={"ͷ��","���","����","����"
			,"����","����","����","�Ƽ�","�ƾ�","ʱ��"};
	private Map<String,String> categoryCode=new HashMap<String,String>();
	//�Կؼ���������Ҫ�����߳��н���
	private Handler handler=new Handler()
								{
									public void handleMessage(Message msg)
									{
										switch(msg.what)
										{
										case SHOW_RESPONSE:
											//���������UI����
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
        textView.setText("л��");
       
        
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
        
        //Ĭ������ʱ��ͷ��top����
        String category="top";
        //��������
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
     * ��ʼ������
     */
    private void initData() 
    {
    	if(categoryCode.isEmpty())
    	{
			categoryCode.put("ͷ��", "top");
			categoryCode.put("���", "shehui");
			categoryCode.put("����", "guonei");
			categoryCode.put("����", "guoji");
			categoryCode.put("����", "yule");
			categoryCode.put("����", "tiyu");
			categoryCode.put("����", "junshi");
			categoryCode.put("�Ƽ�", "keji");
			categoryCode.put("�ƾ�", "caijing");
			categoryCode.put("ʱ��", "shishang");
			
		}
	}
    
    /**
     * ������������
     * @param address
     */
    private void sendRequest(String address)
    {
    	HttpUtil.sendHttpRequest(address, new HttpCallbackListener()
        {

			public void onFinish(String response)//�յ����� 
			{
				
				Message message=new Message();
				message.what=SHOW_RESPONSE;
				//�����������صĽ���洢��message��
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
								"��ȡ����ʧ��", Toast.LENGTH_SHORT).show();
						
					}
					
				});
			}

});
			
    }
    
	/**
     * ����JSON����
     * @param jsonData
     */
    private void parseJSONWithJSONObject(String jsonData)
    {
    	try 
    	{
    		//�������
    		dataList.clear();
			JSONObject jsonObject=new JSONObject(jsonData);
			//���ؼ��ֲ���result
			JSONObject result=jsonObject.getJSONObject("result");
			//���ؼ��ֲ���data
			String data=result.getString("data");
			//����data�����ݳ������ݣ�������ȡ
			JSONArray jsonArray=new JSONArray(data);
			int sum=jsonArray.length();
			for(int i=0;i<jsonArray.length();++i)
			{
				
				//��Ҫ��Ϣ����ȡ
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
     *���ListViewʱ�����Ӧ
     * @param parent
     * @param view
     * @param position
     * @param id
     */
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{
		//��Ϊ��ListView֮ǰ����addHeaderView���ʵ����ʱ��λ�û�+1����Ҫ��Ӧ��-1
		CategoryItem tempCategoryItem=dataList.get(position-1);
		
		String urlAddress=tempCategoryItem.getNewsUrl();
		
		Intent intent=new Intent(this,NewsDetailActivity.class);
		intent.putExtra("URL", urlAddress);
		startActivity(intent);
		
	}
}
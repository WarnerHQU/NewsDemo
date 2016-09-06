package com.newsdemo.app;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CategoryItemAdapter extends ArrayAdapter<CategoryItem> 
{
	private int resourceID;
	public CategoryItemAdapter(Context context, int textViewResourceId,
			List<CategoryItem> objects) 
	{
		super(context, textViewResourceId, objects);
		resourceID=textViewResourceId;
	}
	/**
	 * getView��ÿ��������ص���Ļ�ڵ�ʱ��ᱻ����
	 * convertView�����洢֮ǰ���غõĲ��֣����Խ����ظ�����
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		CategoryItem category=getItem(position);
		
		View view;
		
		ViewHolder viewHolder;
		//����Ż������ظ����ز��֣�����л���Ĳ��֣�ֱ���û���ļ���
		if(convertView==null)
		{
			view=LayoutInflater.from(getContext()).inflate(resourceID, null);
			
			viewHolder=new ViewHolder();
			
			viewHolder.dateText=(TextView) view.findViewById(R.id.date);
			viewHolder.authorText=(TextView) view.findViewById(R.id.author_name);
			viewHolder.newsTitle=(TextView) view.findViewById(R.id.news_title);
			
			//��viewHolder����洢��view��
			view.setTag(viewHolder);
		}
		else
		{
			view=convertView;
			
			viewHolder=(ViewHolder) view.getTag();
		}
		
		viewHolder.dateText.setText(category.getDate());
		viewHolder.authorText.setText(category.getAuthor_name());
		viewHolder.newsTitle.setText(category.getNewsTitle());
		
		return view;
	}
	
	/**
	 * �½�ViewHolder�Կؼ����л��棬�����ظ�����findViewById()���ҿؼ�
	 */
	class ViewHolder
	{
		TextView dateText;
		TextView authorText;
		TextView newsTitle;
	}

}

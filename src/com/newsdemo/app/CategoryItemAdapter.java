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
	 * getView是每个子项被加载到屏幕内的时候会被调用
	 * convertView用来存储之前加载好的布局，可以进行重复调用
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		CategoryItem category=getItem(position);
		
		View view;
		
		ViewHolder viewHolder;
		//如此优化不会重复加载布局，如果有缓存的布局，直接用缓存的即可
		if(convertView==null)
		{
			view=LayoutInflater.from(getContext()).inflate(resourceID, null);
			
			viewHolder=new ViewHolder();
			
			viewHolder.dateText=(TextView) view.findViewById(R.id.date);
			viewHolder.authorText=(TextView) view.findViewById(R.id.author_name);
			viewHolder.newsTitle=(TextView) view.findViewById(R.id.news_title);
			
			//将viewHolder对象存储在view中
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
	 * 新建ViewHolder对控件进行缓存，避免重复调用findViewById()查找控件
	 */
	class ViewHolder
	{
		TextView dateText;
		TextView authorText;
		TextView newsTitle;
	}

}

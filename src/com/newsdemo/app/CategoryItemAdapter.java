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
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		CategoryItem category=getItem(position);
		//动态加载布局
		View view=LayoutInflater.from(getContext()).inflate(resourceID, null);
		
		TextView dateText=(TextView) view.findViewById(R.id.date);
		TextView authorText=(TextView) view.findViewById(R.id.author_name);
		TextView newsTitle=(TextView) view.findViewById(R.id.news_title);
		
		dateText.setText(category.getDate());
		authorText.setText(category.getAuthor_name());
		newsTitle.setText(category.getNewsTitle());
		
		return view;
	}
	
	

}

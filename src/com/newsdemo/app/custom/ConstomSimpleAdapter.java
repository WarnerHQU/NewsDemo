package com.newsdemo.app.custom;

import java.util.List;
import java.util.Map;

import com.newsdemo.app.R;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class ConstomSimpleAdapter extends SimpleAdapter 
{
	private int clickTemp = -1;
	private int clickTemp2 = -1;
	
	public ConstomSimpleAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
	}
	public void setSeclection(int position) 
	{
		clickTemp = position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = super.getView(position, convertView, parent);
		// ���µ�һ��TextView�ı���
		//if(position == 0) 
		if(clickTemp2==-1)//Ϊ��ʹ�߶Ⱥ��ʣ���������һ��
		{
			TextView categoryTitle = (TextView) v;
			categoryTitle.setBackgroundResource(R.drawable.image_categorybar_item_selected_background);
			//categoryTitle.setTextColor(0xFFFFFFFF);
			
		}
		if(clickTemp==position)//���ѡ��ʱ���ɫ
		{
			TextView categoryTitle = (TextView) v;
			categoryTitle.setBackgroundResource(R.drawable.image_categorybar_item_selected_background);
			categoryTitle.setTextColor(Color.RED);
		}
		
		return v;
	}
	

}

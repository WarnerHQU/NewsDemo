package com.newsdemo.app;

/**
 * 创建一个类用于显示新闻的信息
 * @author 谢峰
 *
 */
public class CategoryItem 
{
	private String author_name;
	private String date;
	private String newsTitle;
	private String newsUrl;
	
	CategoryItem(String author_name,String date,String newsTitle)
	{
		this.setAuthor_name(author_name);
		this.setDate(date);
		this.setNewsTitle(newsTitle);
	}
	
	CategoryItem(String author_name,String date,String newsTitle,String newsUrl)
	{
		this.setAuthor_name(author_name);
		this.setDate(date);
		this.setNewsTitle(newsTitle);
		this.setNewsUrl(newsUrl);
	}

	public String getAuthor_name() {
		return author_name;
	}

	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getNewsUrl() {
		return newsUrl;
	}

	public void setNewsUrl(String newsUrl) {
		this.newsUrl = newsUrl;
	}
	
}

package com.owner.entity;

import java.util.List;

public class Data {
    private long code;
    private String text;
    private String url;
    private Object list;
    /*private List<Novel> novels;
    private List<News> newss; 
    private List<App> apps;
    private List<Train> trains;
    private List<Flight> flights;
    private List<Groupon> groupons;
    private List<Privilege> privileges;
    private List<Hotel> hotels;
    private List<Lottery> lotterys;
    private List<Price> prices;
    private List<Restaurant> restaurants;*/
	public long getCode() {
		return code;
	}
	public void setCode(long code) {
		this.code = code;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Object getList() {
		return list;
	}
	public void setList(Object list) {
		this.list = list;
	}
}

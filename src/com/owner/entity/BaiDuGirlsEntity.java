package com.owner.entity;

import java.io.Serializable;

public class BaiDuGirlsEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2264134887069296402L;
	String tag1;
	String tag2;
	Long totalNum;
	int start_index;
	int return_number;
    Object data;
	public String getTag1() {
		return tag1;
	}
	public void setTag1(String tag1) {
		this.tag1 = tag1;
	}
	public String getTag2() {
		return tag2;
	}
	public void setTag2(String tag2) {
		this.tag2 = tag2;
	}
	public Long getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}
	public int getStart_index() {
		return start_index;
	}
	public void setStart_index(int start_index) {
		this.start_index = start_index;
	}
	public int getReturn_number() {
		return return_number;
	}
	public void setReturn_number(int return_number) {
		this.return_number = return_number;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}

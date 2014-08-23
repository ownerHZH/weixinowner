package com.owner.entity;

import java.io.Serializable;

public class PicDataEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1357556783855738434L;
	String id;
    String image_url;
    int image_width;
	int image_height;
	String download_url;
	String thumbnail_url;
	String thumb_large_url;
	int thumbnail_width;
	int thumbnail_height;
	int thumb_large_width;
	int thumb_large_height;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public int getImage_width() {
		return image_width;
	}
	public void setImage_width(int image_width) {
		this.image_width = image_width;
	}
	public int getImage_height() {
		return image_height;
	}
	public void setImage_height(int image_height) {
		this.image_height = image_height;
	}
	public String getDownload_url() {
		return download_url;
	}
	public void setDownload_url(String download_url) {
		this.download_url = download_url;
	}
	public String getThumbnail_url() {
		return thumbnail_url;
	}
	public void setThumbnail_url(String thumbnail_url) {
		this.thumbnail_url = thumbnail_url;
	}
	public String getThumb_large_url() {
		return thumb_large_url;
	}
	public void setThumb_large_url(String thumb_large_url) {
		this.thumb_large_url = thumb_large_url;
	}
	public int getThumbnail_width() {
		return thumbnail_width;
	}
	public void setThumbnail_width(int thumbnail_width) {
		this.thumbnail_width = thumbnail_width;
	}
	public int getThumbnail_height() {
		return thumbnail_height;
	}
	public void setThumbnail_height(int thumbnail_height) {
		this.thumbnail_height = thumbnail_height;
	}
	public int getThumb_large_width() {
		return thumb_large_width;
	}
	public void setThumb_large_width(int thumb_large_width) {
		this.thumb_large_width = thumb_large_width;
	}
	public int getThumb_large_height() {
		return thumb_large_height;
	}
	public void setThumb_large_height(int thumb_large_height) {
		this.thumb_large_height = thumb_large_height;
	}
}

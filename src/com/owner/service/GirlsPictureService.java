package com.owner.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.owner.entity.BaiDuGirlsEntity;
import com.owner.entity.PicDataEntity;
import com.owner.utils.GsonUtil;

public class GirlsPictureService {
	public static final Type photo_list_type=(Type) new TypeToken<List<PicDataEntity>>(){}.getType();
	
	public static String downloadUrlToStream(int page) {
		final String urlString = "http://image.baidu.com/channel/listjson?pn="+page+"&rn="+1+"&tag1=%E7%BE%8E%E5%A5%B3&tag2=%E5%85%A8%E9%83%A8&ie=utf8";	
		HttpURLConnection urlConnection = null;
		ByteArrayOutputStream out = null;
		BufferedInputStream in = null;
		//OutputStream outputStream = null;
		try {
			final URL url = new URL(urlString);
			urlConnection = (HttpURLConnection) url.openConnection();
			in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
			out = new ByteArrayOutputStream();
			int b=0;
			byte[] buffer = new byte[1024];
			while ((b = in.read(buffer)) != -1) {
				out.write(buffer,0,b);
			}
			byte[] result = out.toByteArray();
            String resultString = new String(result, "utf-8");
            return parseJson(resultString);
		} catch ( IOException e) {
			e.printStackTrace();
			return "";
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		
	}


	protected static  String parseJson(String resultString2) {
		//System.out.println(resultString2);
		
		BaiDuGirlsEntity bentity=GsonUtil.getGson().fromJson(resultString2, BaiDuGirlsEntity.class);
		String dataString=GsonUtil.getGson().toJson(bentity.getData());
		List<PicDataEntity> datas=GsonUtil.getGson().fromJson(dataString, photo_list_type);
		if(datas!=null&&datas.size()>0)
		{
			//System.out.println(datas.get(0).getImage_url());
			return datas.get(0).getImage_url();
			//page++;
			//setUrlString(page);
/*			Data4Item d2 = new Data4Item("点击下一张", "点击",datas.get(0).getImage_url(),datas.get(0).getImage_url());
			Msg4ImageText mit = new Msg4ImageText();
            mit.setFromUserName(msg.getToUserName());
            mit.setToUserName(msg.getFromUserName()); 
            mit.setCreateTime(msg.getCreateTime());
            mit.addItem(d2);
            session.callback(mit);*/
            
			/*Msg4Image mi=new Msg4Image();
			mi.setFromUserName(msg.getToUserName());
	        mi.setToUserName(msg.getFromUserName()); 
	        mi.setCreateTime(msg.getCreateTime());
	        mi.setPicUrl(datas.get(0).getImage_url());
	        session.callback(mi);
	        
	        Msg4Link ml=new Msg4Link();
	        ml.setFromUserName(msg.getToUserName());
	        ml.setToUserName(msg.getFromUserName()); 
	        ml.setCreateTime(msg.getCreateTime());
	        ml.setTitle("下一页");
	        ml.setDescription("下一张");*/                     
		}
		return "";
		
	}
	
	public static void main(String[] args) {
		downloadUrlToStream(0);
	}
	
}

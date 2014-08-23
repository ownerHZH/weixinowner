package com.owner.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.marker.weixin.DefaultSession;
import org.marker.weixin.HandleMessageAdapter;
import org.marker.weixin.MySecurity;
import org.marker.weixin.msg.Data4Item;
import org.marker.weixin.msg.Msg4Image;
import org.marker.weixin.msg.Msg4ImageText;
import org.marker.weixin.msg.Msg4Music;
import org.marker.weixin.msg.Msg4Text;


import com.owner.entity.App;
import com.owner.entity.Data;
import com.owner.entity.Flight;
import com.owner.entity.Groupon;
import com.owner.entity.Hotel;
import com.owner.entity.Lottery;
import com.owner.entity.News;
import com.owner.entity.Novel;
import com.owner.entity.Price;
import com.owner.entity.Privilege;
import com.owner.entity.Restaurant;
import com.owner.entity.Train;
import com.owner.entity.resp.Music;
import com.owner.entity.resp.MusicMessage;
import com.owner.onsants.Consant;
import com.owner.service.BaiduMusicService;
import com.owner.service.Client;
import com.owner.service.FaceService;
import com.owner.service.GirlsPictureService;
import com.owner.utils.GsonUtil;
import com.owner.utils.MessageUtil;

/**
 * 处理微信服务器请求的Servlet URL地址：http://xxx/weixin/dealwith.do
 * 
 * 注意：官方文档限制使用80端口哦！
 * 
 * @author owner
 *@qq 756699074
 */
public class WeixinServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
  
     
    public WeixinServlet() {  }
     
     
    //TOKEN 是你在微信平台开发模式中设置的哦
    public static final String TOKEN = "owner";
    /**
     * 处理微信服务器验证
     * 
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String signature = request.getParameter("signature");// 微信加密签名
        String timestamp = request.getParameter("timestamp");// 时间戳
        String nonce = request.getParameter("nonce");// 随机数
        String echostr = request.getParameter("echostr");// 随机字符串
 
        // 重写totring方法，得到三个参数的拼接字符串
        List<String> list = new ArrayList<String>(3) {
            private static final long serialVersionUID = 2621444383666420433L;
            public String toString() {
                return this.get(0) + this.get(1) + this.get(2);
            }
        };
        list.add(TOKEN);
        list.add(timestamp);
        list.add(nonce);
        Collections.sort(list);// 排序
        String tmpStr = new MySecurity().encode(list.toString(),
                MySecurity.SHA_1);// SHA-1加密
        Writer out = response.getWriter();
        if (signature.equals(tmpStr)) {
            out.write(echostr);// 请求验证成功，返回随机码
        } else {
            out.write("");
        }
        out.flush();
        out.close();
    }
 
    /**
     * 处理微信服务器发过来的各种消息，包括：文本、图片、地理位置、音乐等等
     * 
     * 
     */
    protected void doPost(HttpServletRequest request,
            final HttpServletResponse response) throws ServletException, IOException {
    	// 将请求、响应的编码均设置为UTF-8（防止中文乱码）  
        request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8"); 
    	InputStream is = request.getInputStream();
        OutputStream os = response.getOutputStream();
         
         
        final DefaultSession session = DefaultSession.newInstance(); 
         
        session.addOnHandleMessageListener(new HandleMessageAdapter(){
             
        	//处理文字信息
            @Override
            public void onTextMsg(Msg4Text msg) {
            	//m  代表美女图
            	String rText=msg.getContent();
                if("mm".equalsIgnoreCase(rText.trim())){
                	int picid=new Random().nextInt(999)+1;
                	String url=GirlsPictureService.downloadUrlToStream(picid);           	
                    //回复一条消息
                    Data4Item d1 = new Data4Item("", "",url,url); //"http://3.weixinowner.sinaapp.com/BaiDuPicServLet?picid="+picid    
                    Msg4ImageText mit = new Msg4ImageText();
                    mit.setFromUserName(msg.getToUserName());
                    mit.setToUserName(msg.getFromUserName()); 
                    mit.setCreateTime("");
                    mit.addItem(d1);
                    session.callback(mit);
                }else if(rText.startsWith("歌曲")||rText.startsWith("音乐")||rText.startsWith("music")){
                	/*String keyWord="";
                	// 将歌曲2个字及歌曲后面的+、空格、-等特殊符号去掉  
                	if(rText.startsWith("歌曲"))
                	{
                		keyWord = rText.replaceAll("^歌曲[\\+ ~!@#%^-_=]?", "");
                	}else if(rText.startsWith("音乐"))
                	{
                		keyWord = rText.replaceAll("^音乐[\\+ ~!@#%^-_=]?", "");
                	}else if(rText.startsWith("music"))
                	{
                		keyWord = rText.replaceAll("^music[\\+ ~!@#%^-_=]?", "");
                	}*/         
                	String keyWord = rText.replaceAll("^歌曲[\\+ ~!@#%^-_=]?", "");
                    // 如果歌曲名称为空  
                    if ("".equals(keyWord)) {  
                    	Msg4Text mt=new Msg4Text();
        				mt.setFromUserName(msg.getToUserName());
        				mt.setToUserName(msg.getFromUserName());
        				mt.setContent(getUsage());
        				session.callback(mt);
                    } else {  
                        String[] kwArr = keyWord.split("@");  
                        // 歌曲名称  
                        String musicTitle = kwArr[0];  
                        // 演唱者默认为空  
                        String musicAuthor = "";  
                        if (2 == kwArr.length)  
                            musicAuthor = kwArr[1];  
  
                        // 搜索音乐  
                        Music music = BaiduMusicService.searchMusic(musicTitle, musicAuthor);  
                        // 未搜索到音乐  
                        if (null == music) {  
                            Msg4Text mt=new Msg4Text();
            				mt.setFromUserName(msg.getToUserName());
            				mt.setToUserName(msg.getFromUserName());
            				mt.setContent("对不起，没有找到你想听的歌曲<" + musicTitle + ">。");
            				session.callback(mt);
                        } else {  
                        	Msg4Music mm=new Msg4Music();
                        	mm.setFromUserName(msg.getToUserName());
            				mm.setToUserName(msg.getFromUserName());
            				mm.setCreateTime(new Date().getTime()+"");
            				mm.setDescription(music.getDescription());
            				mm.setTitle(music.getTitle());
            				mm.sethQMusicUrl(music.getMusicUrl());
            				mm.setHQMusicUrl(music.getHQMusicUrl());
            				mm.setMusicUrl(music.getMusicUrl());
            				mm.setThumbMediaId("");
            				session.callback(mm);
            				
            				/*Msg4Text mt=new Msg4Text();
            				mt.setFromUserName(msg.getToUserName());
            				mt.setToUserName(msg.getFromUserName());
            				mt.setContent("找到了"+music.getDescription());
            				session.callback(mt);*/
                        	// 音乐消息  
                            /*MusicMessage musicMessage = new MusicMessage();  
                            musicMessage.setToUserName(msg.getFromUserName());  
                            musicMessage.setFromUserName(msg.getToUserName());  
                            musicMessage.setCreateTime(new Date().getTime());  
                            musicMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);  
                            musicMessage.setMusic(music);  
                            // 响应消息  
                            PrintWriter out = null;
							try {
								out = response.getWriter();
							} catch (IOException e) {
								e.printStackTrace();
							}  
                            out.print(MessageUtil.musicMessageToXml(musicMessage));  
                            out.flush();
                            out.close();*/
                        }  
                    }
                }else
                {
                	//机器人
                	String rJsonString=sendToTarget(rText);
                	if(rJsonString!=null)
                	{
                		json2Entity(rJsonString,session,msg.getToUserName(),msg.getFromUserName());
                	}               	
                }                               
            }

            //发送的是图片信息的时候
			@Override
			public void onImageMsg(Msg4Image msg) {
				Msg4Text mt=new Msg4Text();
				mt.setFromUserName(msg.getToUserName());
				mt.setToUserName(msg.getFromUserName());
				mt.setContent(FaceService.detect(msg.getPicUrl()));
				session.callback(mt);
			}
            
        });
         
        //必须调用这两个方法
                //如果不调用close方法，将会出现响应数据串到其它Servlet中。
        session.process(is, os);//处理微信消息 
        session.close();//关闭Session
    }
    
    /** 
     * 歌曲点播使用指南 
     *  
     * @return 
     */  
    public static String getUsage() {  
        StringBuffer buffer = new StringBuffer();  
        buffer.append("歌曲点播操作指南").append("\n\n");  
        buffer.append("回复：歌曲+歌名").append("\n");  
        buffer.append("例如：歌曲存在").append("\n");  
        buffer.append("或者：歌曲存在@汪峰");   
        return buffer.toString();  
    }
    
    //图灵机器人的解析
    public void json2Entity(String object,DefaultSession session,String fromUser,String toUser) {
		final Data data=GsonUtil.convertStringToData(object);
		if(data!=null)
		{
			long code=data.getCode();
			String listString=GsonUtil.getGson().toJson(data.getList());
			listString=listString==null?"":listString;
			
			if(code==100000)//文本类数据
			{
				Msg4Text mt=new Msg4Text();
				mt.setFromUserName(fromUser);
				mt.setToUserName(toUser);
				mt.setContent(data.getText());
				session.callback(mt);
				
			}else if(code==200000)//网址类数据
			{
				Msg4Text mt=new Msg4Text();
				mt.setFromUserName(fromUser);
				mt.setToUserName(toUser);
				String cl="<a href='"+data.getUrl()+"'>"+data.getText()+"</a>";
				mt.setContent(cl);
				session.callback(mt);
				
			}else if(code==301000)//小说
			{		
				List<Novel> novels=GsonUtil.getGson().fromJson(listString,Consant.novel_list_type);	
				Msg4ImageText mi=new Msg4ImageText();
				mi.setFromUserName(fromUser);
				mi.setToUserName(toUser);
				for(Novel novel:novels)
				{
					Data4Item di=new Data4Item(novel.getName(), "作者:"+novel.getAuthor(), novel.getIcon(), novel.getDetailurl());
					mi.addItem(di);
				}
				session.callback(mi);
			}else if(code==302000)//新闻
			{	
				List<News> newss=GsonUtil.getGson().fromJson(listString,Consant.news_list_type);
				Msg4ImageText mi=new Msg4ImageText();
				mi.setFromUserName(fromUser);
				mi.setToUserName(toUser);
				for(News news:newss)
				{
					Data4Item di=new Data4Item(news.getArticle(), "新闻来源："+news.getSource(), news.getIcon(), news.getDetailurl());
					mi.addItem(di);
				}
				session.callback(mi);
				
			}else if(code==304000)//应用、软件、下载
			{	
				List<App> apps=GsonUtil.getGson().fromJson(listString,Consant.app_list_type);
				Msg4ImageText mi=new Msg4ImageText();
				mi.setFromUserName(fromUser);
				mi.setToUserName(toUser);
				for(App app:apps)
				{
					Data4Item di=new Data4Item(app.getName(), "下载量："+app.getCount(), app.getIcon(), app.getDetailurl());
					mi.addItem(di);
				}
				session.callback(mi);
				
			}else if(code==305000)//列车
			{	
				List<Train> trains=GsonUtil.getGson().fromJson(listString,Consant.train_list_type);
				Msg4ImageText mi=new Msg4ImageText();
				mi.setFromUserName(fromUser);
				mi.setToUserName(toUser);
				for(Train train:trains)
				{
					Data4Item di=new Data4Item(train.getStart()+"-->"+train.getTerminal(),train.getStarttime()+"<-->"+train.getEndtime(), train.getIcon(), train.getDetailurl());
					mi.addItem(di);
				}
				session.callback(mi);
				
			}else if(code==306000)//航班
			{	
				List<Flight> flights=GsonUtil.getGson().fromJson(listString,Consant.flight_list_type);
				Msg4ImageText mi=new Msg4ImageText();
				mi.setFromUserName(fromUser);
				mi.setToUserName(toUser);
				for(Flight flight:flights)
				{
					Data4Item di=new Data4Item(flight.getFlight()+"  "+flight.getState(),flight.getRoute()+"/r/n"+flight.getStarttime()+"<-->"+flight.getEndtime(), flight.getIcon(), flight.getDetailurl());
					mi.addItem(di);
				}
				session.callback(mi);
				
			}else if(code==307000)//团购
			{	
				List<Groupon> groupons=GsonUtil.getGson().fromJson(listString,Consant.groupon_list_type);
				Msg4ImageText mi=new Msg4ImageText();
				mi.setFromUserName(fromUser);
				mi.setToUserName(toUser);
				for(Groupon groupon:groupons)
				{
					Data4Item di=new Data4Item(groupon.getName()+"参团数量："+groupon.getCount(), groupon.getPrice()+"/r/n"+groupon.getInfo(), groupon.getIcon(), groupon.getDetailurl());
					mi.addItem(di);
				}
				session.callback(mi);
				
			}else if(code==308000)//优惠
			{	
				List<Privilege> privileges=GsonUtil.getGson().fromJson(listString,Consant.privilege_list_type);
				Msg4ImageText mi=new Msg4ImageText();
				mi.setFromUserName(fromUser);
				mi.setToUserName(toUser);
				for(Privilege privilege:privileges)
				{
					Data4Item di=new Data4Item(privilege.getName(),privilege.getInfo(), privilege.getIcon(), privilege.getDetailurl());
					mi.addItem(di);
				}
				session.callback(mi);
				
			}else if(code==310000)//酒店
			{	
				List<Hotel> hotels=GsonUtil.getGson().fromJson(listString,Consant.hotel_list_type);
				Msg4ImageText mi=new Msg4ImageText();
				mi.setFromUserName(fromUser);
				mi.setToUserName(toUser);
				for(Hotel hotel:hotels)
				{
					Data4Item di=new Data4Item(hotel.getName(),hotel.getPrice()+"/r/n"+hotel.getSatisfaction()+"/r/n"+hotel.getCount(), hotel.getIcon(), hotel.getDetailurl());
					mi.addItem(di);
				}
				session.callback(mi);
				
			}else if(code==311000)//彩票
			{		
				List<Lottery> lotterys=GsonUtil.getGson().fromJson(listString,Consant.lottery_list_type);
				
				Msg4ImageText mi=new Msg4ImageText();
				mi.setFromUserName(fromUser);
				mi.setToUserName(toUser);
				for(Lottery lottery:lotterys)
				{
					Data4Item di=new Data4Item(lottery.getNumber(), lottery.getInfo(), lottery.getIcon(), lottery.getDetailurl());
					mi.addItem(di);
				}
				session.callback(mi);
				
			}else if(code==312000)//价格
			{		
				List<Price> prices=GsonUtil.getGson().fromJson(listString,Consant.price_list_type);
				
				Msg4ImageText mi=new Msg4ImageText();
				mi.setFromUserName(fromUser);
				mi.setToUserName(toUser);
				for(Price price:prices)
				{
					Data4Item di=new Data4Item(price.getName(),price.getPrice(), price.getIcon(), price.getDetailurl());
					mi.addItem(di);
				}
				session.callback(mi);
				
			}else if(code==301000)//餐厅
			{		
				List<Restaurant> restaurants=GsonUtil.getGson().fromJson(listString,Consant.restaurant_list_type);
				
				Msg4ImageText mi=new Msg4ImageText();
				mi.setFromUserName(fromUser);
				mi.setToUserName(toUser);
				for(Restaurant restaurant:restaurants)
				{
					Data4Item di=new Data4Item(restaurant.getName(),restaurant.getPrice(), restaurant.getIcon(), restaurant.getDetailurl());
					mi.addItem(di);
				}
				session.callback(mi);
				
			}
		}						
	}
    
    /**
     * 根据发送的数据返回相应的JSON  String
     * @param sendtextString
     * @return
     */
    public String sendToTarget(final String sendtextString) {
    	String resultString="";
    	HttpClient client=Client.getInstance();
    	HttpPost httpPost = new HttpPost("http://www.tuling123.com/openapi/api");
           
        List<NameValuePair> param = new ArrayList<NameValuePair>();
        param.add(new BasicNameValuePair("key", "1cd20475b702bc4819831e88e2292d97"));
        param.add(new BasicNameValuePair("info", sendtextString));
        try {
			httpPost.setEntity(new UrlEncodedFormEntity(param, "utf-8"));
			HttpResponse response = client.execute(httpPost);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
			    InputStream is = response.getEntity().getContent();
			    ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    int len = 0;
			    byte[] buffer = new byte[1024];
			    while ((len = is.read(buffer)) != -1) {
			        baos.write(buffer, 0, len);
			    }
			    is.close();
			    baos.close();
			    byte[] result = baos.toByteArray();
			    resultString = new String(result, "utf-8");
			} else {
			    resultString=null;
			}
		} catch (UnsupportedEncodingException e) {
			resultString=null;
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			resultString=null;
			e.printStackTrace();
		} catch (IllegalStateException e) {
			resultString=null;
			e.printStackTrace();
		} catch (IOException e) {
			resultString=null;
			e.printStackTrace();
		}
        return resultString;
    }
}
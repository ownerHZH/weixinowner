package com.owner.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owner.onsants.Consant;
import com.owner.service.GirlsPictureService;
import com.owner.utils.GsonUtil;

public class BaiDuPicServLet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -778564842299038811L;

	/**
	 * Constructor of the object.
	 */
	public BaiDuPicServLet() {
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String basePath = request.getSession().getServletContext().getRealPath("/");
		request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8");
        System.out.println("get-----");
        int picid=Integer.parseInt(request.getParameter("picid"));
		/*PrintWriter out = response.getWriter();
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("url", GirlsPictureService.downloadUrlToStream(picid));
		map.put("picid", picid+"");
		out.print(GsonUtil.getGson().toJson(map));*/
        String url=GirlsPictureService.downloadUrlToStream(picid);
        String extString=url;
        String ext=extString.substring(extString.lastIndexOf("."));
        System.out.println("后缀--"+ext);
		String fName=picid+ext;
		File f=new File(basePath+"images//"+fName);
		if(!f.exists())
		{
			try {
				download(url,fName ,basePath+"images//");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        
        System.out.println("Get--"+url);
		request.setAttribute("url","images//"+fName);
		request.setAttribute("picid", picid);
		System.out.println("Get id--"+picid+"");
		request.getRequestDispatcher("./picshow.jsp").forward(request,response);
		//response.sendRedirect("picshow.jsp");
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String basePath = request.getSession().getServletContext().getRealPath("/");
		System.out.println("basepath--"+basePath+"");
		System.out.println("post-----");
		request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8");
        int picid=Integer.parseInt(request.getParameter("picid"));
        int dir=Integer.parseInt(request.getParameter("dir"));
        if(dir==0)
        {
        	picid--;
        	if(picid<0)
        		picid=0;
        }else
        {
        	picid++;
        }
        
		PrintWriter out = response.getWriter();
		HashMap<String, String> map=new HashMap<String, String>();
		String url=GirlsPictureService.downloadUrlToStream(picid);
		String extString=url;
        String ext=extString.substring(extString.lastIndexOf("."));
        System.out.println("后缀--"+ext);
		String fName=picid+ext;
		File f=new File(basePath+"images//"+fName);
		if(!f.exists())
		{
			try {
				download(url,fName ,basePath+"images//");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Post--"+url);
		map.put("url", "images//"+fName);
		map.put("picid", picid+"");
		System.out.println("Post id--"+picid+"");
		out.print(GsonUtil.getGson().toJson(map));
		out.flush();
		out.close();
	}
	
	 public static void download(String urlString, String filename,String savePath) throws Exception {  
	        // 构造URL  
	        URL url = new URL(urlString);  
	        // 打开连接  
	        URLConnection con = url.openConnection();  
	        //设置请求超时为5s  
	        con.setConnectTimeout(5*1000);  
	        // 输入流  
	        InputStream is = con.getInputStream();  
	      
	        // 1K的数据缓冲  
	        byte[] bs = new byte[1024];  
	        // 读取到的数据长度  
	        int len;  
	        // 输出的文件流  
	       File sf=new File(savePath);  
	       if(!sf.exists()){  
	           sf.mkdirs();  
	       }  
	       OutputStream os = new FileOutputStream(sf.getPath()+"\\"+filename);  
	        // 开始读取  
	        while ((len = is.read(bs)) != -1) {  
	          os.write(bs, 0, len);  
	        }  
	        // 完毕，关闭所有链接  
	        os.close();  
	        is.close();  
	    }

}

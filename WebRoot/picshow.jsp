<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
    
    <title>My JSP 'picshow.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
	  .pre{
	       float: left;
	       z-index: 9999;
	       height: 100%;
	       width: 50px;
	       background-position: 19px center; 
	       background: url('<c:url value="images/previous-arrow.png"></c:url>') no-repeat 24px center;
	  }
	  .next{
	      float:right;
	      z-index: 9999;
	      height: 100%;
	      width: 50px;
	      background-position: 19px center;
	       background: url('<c:url value="images/next-arrow.png"></c:url>') no-repeat 24px center;
	  }
	</style>

    <script type="text/javascript" src='<c:url value="js/jquery.js" ></c:url>' ></script>
    </head>
  
  <script type="text/javascript">
     var pp=${picid};
     $(function(){
       $("#meinv").attr("src","<c:url value='${url}'></c:url>");
     
       $("#next").click(function(){
          $.post("BaiDuPicServLet", { picid:pp,dir:1},     
                  function (data, textStatus){    
				        $("#meinv").attr("src",data.url);
				        //$("#meinv").src=data.url;
		                pp=data.picid;        
			  }, "json");
       });
       
       $("#pre").click(function(){
		 $.post("BaiDuPicServLet", { picid:pp,dir:0},     
                  function (data, textStatus){    
                        //$("#meinv").attr("src","");
				        $("#meinv").attr("src",data.url);
				        //$("#meinv").src=data.url;
		                pp=data.picid;        
			  }, "json");
		 });
		 
     });
  </script>
  
  <body style="background-color: #009ddc">
  

	  <a class="pre" id="pre"></a>

	  <div align="center"  style=" float:left; foverflow: hidden;">
	     <img alt="美图" align="middle" style="display: block;" id="meinv"/>
	  </div>

	  <a id="next" class="next" ></a>
	     
  </body>
</html>

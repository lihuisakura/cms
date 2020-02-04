<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- 引入bootstrap样式 -->
<link rel="stylesheet" type="text/css"
	href="/resource/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="/resource/css/sb-admin.css" />
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="/resource/js/bootstrap.min.js"></script>
<script type="text/javascript">
//给左侧(left.jsp)中的所有的连接绑定点击事件
$(function(){

	
	$("[style='text-decoration:none;']").click(function(){
		$("[style='text-decoration:none;']").attr("class","my-btn btn-outline-danger");
		$(this).attr("class","my-btn btn-outline-danger active");
		//获得跳转路径
		var url=$(this).attr("data");
		//跳转到url  查询的结果得在index页面中id="content-wrapper"的div中显示
		$("#content-wrapper").load(url);
	})
	
	
})
</script>
<style type="text/css">
.my-btn{
  display: inline-block;
  width:110px;
  font-weight: 400;
  color: #212529;
  text-align: center;
  vertical-align: middle;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  background-color: transparent;
  border: 1px solid transparent;
  padding: 0.375rem 1.25rem;
  font-size: 1.25rem;
  line-height: 1.5;
  border-radius: 0.25rem;
  transition: color 0.15s ease-in-out, background-color 0.15s ease-in-out, border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}
</style>
</head>

<body id="page-top">
	
	
	<!-- 今日头条系统顶部 -->
	<nav  style="background-color: black;height:34px;">
		<c:if test="${user==null}">
			<a class="navbar-brand mr-1" style="color: white;font-size: 1rem;margin-left: 1100px;background-color: red;width:50px;text-align: center;"   href="user/login">登录</a>
			<a class="navbar-brand mr-1" style="color: white;font-size: 1rem;margin-left: 20px;text-align: center;"   href="user/register">注册</a>
		</c:if>
		<c:if test="${user!=null}">
			<a class="navbar-brand mr-1" style="color: white;font-size: 1rem;margin-left:1100px;text-align: center;"   href="my">${user.nickname}</a>
			<a class="navbar-brand mr-1" style="color: white;font-size: 1rem;margin-left: 20px;background-color: red;width:50px;text-align: center;"   href="user/logout">注销</a>
			<a class="navbar-brand mr-1" style="color: white;font-size: 1rem;margin-left: 20px;text-align: center;"   href="my">个人中心</a>
		</c:if>
		
		<a class="navbar-brand mr-1" style="color: white;font-size: 1rem;margin-left: 20px;text-align: center;"   href="javascript:void(0)">侵权投诉</a>
		<a class="navbar-brand mr-1" style="color: white;font-size: 1rem;margin-left: 20px;text-align: center;"   href="javascript:void(0)">头条产品</a>
	</nav>
	
	<div id="wrapper">
		<!-- 今日头条系统左部菜单 -->
		<ul style="background-color: white;" class="sidebar navbar-nav"></ul>
		<ul  style="text-align:center;list-style-type:none;background-color: white;" class="sidebar navbar-nav" >
			<li style="display:inline;list-style-type:none;margin-top: 20px;margin-bottom: 10px" class="nav-item">
				<a  href="index">
					<img src="//s3.pstatp.com/toutiao/static/img/logo.271e845.png"  alt="今日头条" style="width: 108px; height: 27px;">
				</a>
			</li>
			<li style="margin-top: 3px">
				<a  class=" my-btn btn-outline-danger active" style="text-decoration:none;"   href="javascript:void(0)" data="1111">
						 热点
				</a>
			</li>
			<c:forEach items="${channelList}" var="c">
				<li style="margin-top: 3px">
					<a  class="my-btn btn-outline-danger" style="text-decoration:none;"   href="javascript:void(0)" data="111">
							 ${c.name}
					</a>
				</li>
			</c:forEach>
			
		</ul>
		
		<!-- 中间内容显示区域 -->
		<div id="content-wrapper">
			
				
			

		</div>
		<ul style="background-color: white;" class="sidebar navbar-nav"></ul>
	</div>
	
</body>
</html>
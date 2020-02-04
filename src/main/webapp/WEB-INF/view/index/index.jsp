<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
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
  padding: 0.5rem 1.25rem;
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
		<ul style="background-color: white;width:173px !important" class="sidebar navbar-nav"></ul>
		<ul  style="text-align:center;list-style-type:none;background-color: white; " class="sidebar navbar-nav" >
			<li style="display:inline;list-style-type:none;margin-top: 20px;margin-bottom: 10px" class="nav-item">
				<a  href="index">
					<img src="//s3.pstatp.com/toutiao/static/img/logo.271e845.png"  alt="今日头条" style="width: 108px; height: 27px;">
				</a>
			</li>
			<li style="margin-top: 3px">
				<a  class=" my-btn btn-outline-danger ${article.channel_id==null?'active':''}" style="text-decoration:none;"   href="/index">
						 热点
				</a>
			</li>
			<c:forEach items="${channelList}" var="channel">
				<li style="margin-top: 3px">
					<a  class="my-btn btn-outline-danger ${article.channel_id==channel.id?'active':'' }" style="text-decoration:none;"   href="/index?channel_id=${channel.id}" >
							 ${channel.name}
					</a>
				</li>
			</c:forEach>
			
		</ul>
		
		<!-- 中间内容显示区域 -->
		<div id="content-wrapper" style="width:1000px !important">
			<c:if test="${article.channel_id==null}">
				<!-- 	轮播图 -->
				  <div id="carouselExampleCaptions" class="carousel slide" data-ride="carousel">
						<ol class="carousel-indicators">
							<li data-target="#carouselExampleCaptions" data-slide-to="0" class="active"></li>
							<li data-target="#carouselExampleCaptions" data-slide-to="1"></li>
							<li data-target="#carouselExampleCaptions" data-slide-to="2"></li>
						</ol>
						<div class="carousel-inner">
							<c:forEach items="${slides}" var="s" varStatus="i">
								<div class="carousel-item ${i.index==0?'active':'' }">
									<img src="/pic/${s.picture}" class="d-block w-100" alt="..."
										width="200px" height="200px">
									<div class="carousel-caption d-md-block d-none">
										<h5>${s.title }</h5>
									</div>
								</div>
							</c:forEach>
						</div>
						<!-- 左右切换轮播图 -->
						<a class="carousel-control-prev" href="#carouselExampleCaptions"
							role="button" data-slide="prev"> <span
							class="carousel-control-prev-icon" aria-hidden="true"></span> <span
							class="sr-only">Previous</span>
						</a> <a class="carousel-control-next" href="#carouselExampleCaptions"
							role="button" data-slide="next"> <span
							class="carousel-control-next-icon" aria-hidden="true"></span> <span
							class="sr-only">Next</span>
						</a>
					</div>
					
				<div id="hotArticle" >
				<hr>
						<ul class="list-unstyled">
							<c:forEach items="${hots}" var="h">
								<li class="media">
									<img src="/pic/${h.picture }" class="mt-1" alt="..." width="150px" height="100px">
									<div class="media-body " style="margin-left: 50px;padding-top: 10px">
										<h5 class="mt-0 mb-1 ">
											<a href="/index/select?id=${h.id }" 
											style="font-size: 25px;color: black;font-weight: bold;">${h.title }</a> 
										</h5>
										<br> ${h.user.username }&nbsp;&nbsp;&nbsp;
										<fmt:formatDate value="${h.created }" pattern="yyyy-MM-dd" />
									</div></li>
								<hr>
							</c:forEach>
						</ul>
				</div>
				
			</c:if>
			<c:if test="${article.channel_id!=null}">
				<div >
				  <button type="button" class="btn" style="margin-right: 10px;font-weight: bolder;${article.category_id==null?'color:red':''}" onclick="location='/index?channel_id=${article.channel_id}'">全部</button>
				  <c:forEach items="${cates}" var="cate">
				  	<button type="button" class="btn " style="margin-right: 10px;font-weight: bolder; ${cate.id==article.category_id?'color:red':''}" onclick="location='/index?channel_id=${article.channel_id}&category_id=${cate.id}'">${cate.name}</button>
				  </c:forEach>
				  <hr style="background-color:#F0F0F0;height:2px;border:none;">
				</div>
				<div>
					<ul class="list-unstyled">
						<c:forEach items="${articles}" var="a">
							<li class="media">
								<img src="/pic/${a.picture }" class="mt-1" alt="..." width="150px" height="100px">
								<div class="media-body " style="margin-left: 50px;padding-top: 10px">
									<h5 class="mt-0 mb-1 ">
										<a href="/index/select?id=${a.id }" 
										style="font-size: 25px;color: black;font-weight: bold;">${a.title }</a> 
									</h5>
									<br> ${a.user.username }&nbsp;&nbsp;&nbsp;
									<fmt:formatDate value="${a.created }" pattern="yyyy-MM-dd" />
								</div>
							</li>
							<hr>
						</c:forEach>
					</ul>
				</div>
			</c:if>	
			

		</div>
		<div style="background-color: white;" class="sidebar navbar-nav">
				<div style="padding: 10px">
					<h5 style="margin-top: 10px">最新文章</h5>
				</div>
		</div>
		<div style="background-color: white;" class="sidebar navbar-nav"></div>
	</div>
	
</body>
</html>
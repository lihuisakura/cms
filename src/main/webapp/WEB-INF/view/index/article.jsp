<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="/resource/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="/resource/css/sb-admin.css" />
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="/resource/js/bootstrap.min.js"></script>
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
<script type="text/javascript">
$(function(){
	var id="${article.id}";
	$("#commentList").load("/comment/commentList?id="+id);
})
function comment(){
	
	var formData=$("#form01").serialize();
	$.post("/comment/addComment",formData,function(result){
		var id="${article.id}";
		$("#commentList").load("/comment/commentList?id="+id);
	});
	
}
function goPage(page){
	var id="${article.id}";
	$("#commentList").load("/comment/commentList?id="+id+"&pageNum="+page);
}
</script>
</head>
<body id="page-top">
	
	
	<!-- 今日头条系统顶部 -->
	<nav  style="background-color: black;height:34px;">
		<c:if test="${user==null}">
			<a class="navbar-brand mr-1" style="color: white;font-size: 1rem;margin-left: 1100px;background-color: red;width:50px;text-align: center;"   href="/user/login">登录</a>
			<a class="navbar-brand mr-1" style="color: white;font-size: 1rem;margin-left: 20px;text-align: center;"   href="/user/register">注册</a>
		</c:if>
		<c:if test="${user!=null}">
			<a  style="margin-left:1010px;text-decoration: none;"   href="my">
				<img src="/pic/${user.photo}" style="border-radius:50%" alt="..." width="30px" height="30px">
			</a> 
			<a class="navbar-brand mr-1" style="color: white;font-size: 1rem;margin-left:10px;text-align: center;"   href="my">${user.nickname}</a>
			<a class="navbar-brand mr-1" style="color: white;font-size: 1rem;margin-left: 20px;background-color: red;width:50px;text-align: center;"   href="/user/logout">注销</a>
			<a class="navbar-brand mr-1" style="color: white;font-size: 1rem;margin-left: 20px;text-align: center;"   href="/my">个人中心</a>
		</c:if>
		
		<a class="navbar-brand mr-1" style="color: white;font-size: 1rem;margin-left: 20px;text-align: center;"   href="javascript:void(0)">侵权投诉</a>
		<a class="navbar-brand mr-1" style="color: white;font-size: 1rem;margin-left: 20px;text-align: center;"   href="javascript:void(0)">头条产品</a>
	</nav>
	
	<nav style="height:40px;margin-top: 10px">
		<img src="//s3.pstatp.com/toutiao/static/img/logo.271e845.png"  alt="今日头条" style="width: 108px; height: 27px;margin-left: 180px;margin-right: 30px">
	    	<button type="button" class="btn"  onclick="location='/index'">首页</button>--
	    	<button type="button" class="btn"  onclick="location='/index?channel_id=${articleChannel.id}'">${articleChannel.name}</button>--
	    	<button class="btn" style="pointer-events: none;color:#8F8F8F" >正文</button>
	    <hr>
	</nav>
		
		
	
	<!-- 中间显示内容 -->
	<div id="wrapper" style="margin-top: 20px">
		<!-- 中间左侧边框 --> 
		<ul style="background-color: white;width:130px !important" class="sidebar navbar-nav"></ul>
		<ul  style="text-align:center;list-style-type:none;background-color: white;" class="sidebar navbar-nav" >
			<li style="margin-top: 3px">
				
			</li>
		</ul>
		
		
		<!-- 中间内容显示区域 -->
		<div id="content-wrapper" style="width:800px !important">
			<div style="padding: 0px 20px">
				<h1 style="font-weight: bold;">${article.title }</h1>
				<h6 style="padding: 15px 0px;">${article.user.username }
				<span style="margin-right: 15px"></span>
				<fmt:formatDate value="${article.created }" pattern="yyyy-MM-dd HH-mm-ss"/>
				</h6>
				<span>${article.content }</span>
				<hr style="background-color:#F0F0F0;height:2px;border:none;">
				<c:if test="${user!=null}">
					<form id="form01">
						<div class="form-group">
							<input type="hidden" name="user_id" value="${user.id}">
							<input type="hidden" name="article_id" value="${article.id }">
							<label for="Textarea1">评论</label>
							<textarea class="form-control" name="content" id="Textarea1" placeholder="写下您的评论..." rows="2"></textarea>
							<button type="button" onclick="comment()" class="btn btn-dark mb-2" style="margin-left: 560px">评论</button>
						</div>
					</form>
				</c:if>
				<c:if test="${user==null}">
					<a href="/user/login?id=${article.id }"  class="btn btn-link my-3">请登录后再评论</a>
				</c:if>
				<hr style="background-color:#F0F0F0;height:2px;border:none;">
				<ul id="commentList" class="list-unstyled">
					<!-- 此处为评论区 -->
				</ul>
			</div>
		</div>
		
		
		<!-- 中间右侧边框 -->
		<div style="background-color: #F4F4F4;width:350px !important" class="sidebar navbar-nav">
			
			<div style="padding: 10px 20px">
				<h5 style="margin-top: 10px">相关文章</h5>
				<ul class="list-unstyled">
						<c:forEach items="${relevantArticle}" var="relevant">
							<li class="media">
								<a href="/index/select?id=${relevant.id }" >
									<img src="/pic/${relevant.picture }" class="mt-1" alt="..." width="50px" height="50px">
								</a>
								<div class="media-body " style="margin-left: 5px;">
									<a href="/index/select?id=${relevant.id }" 
									style="font-size: 10px;height:10px;color: black;overflow: hidden;white-space: nowrap;text-overflow:ellipsis ;">${relevant.title }</a>
									<p style="font-size: 10px;padding-top: 10px">${relevant.user.username }&nbsp;&nbsp;
								<fmt:formatDate value="${relevant.created }" pattern="yyyy-MM-dd HH-mm-ss" /></p> 
								</div>
							</li>
							<hr>
						</c:forEach>
				</ul>
			</div>
		</div>
		<div style="background-color: white;" class="sidebar navbar-nav"></div>
		
	</div>
	
	
	
</body>

</html>
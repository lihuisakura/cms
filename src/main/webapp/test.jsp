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
	$.post("/comment/findCommentNum",{id:id},function(num){
		$("#commentNum").html(num);
	});
	$("#commentList").load("/comment/commentList?id="+id);
})
function comment(){
	var formData=$("#form01").serialize();
	$.post("/comment/addComment",formData,function(result){
		$("#Textarea1").val("");
		var id="${article.id}";
		$("#commentList").load("/comment/commentList?id="+id);
	});
	var id="${article.id}";
	$.post("/comment/findCommentNum",{id:id},function(num){
		$("#commentNum").html(num);
	});
}
function goPage(page){
	var id="${article.id}";
	$("#commentList").load("/comment/commentList?id="+id+"&pageNum="+page);
}
function addFavorite(){
	var user_id="${user.id}";
	var id="${article.id }";
	if(null==user_id || user_id==""){
		alert("请登录后收藏");
		location="/user/login?id="+id;
		return ;
	}
	var text="${article.title}";
	var url= window.location.href;
	$.post(
			"/user/addFavorite",
			{text:text,url:url,user_id:user_id},
			function(result){
				if(result==true){
					alert("收藏成功");
				}else if(result=="haveFavorite"){
					alert("该内容已收藏");
					return;
				}
			}
	);
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
			<a  style="margin-left:1010px;text-decoration: none;"   href="/user/homePage">
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
				<!-- <button type="button" class="btn btn-primary" onclick="addFavorite()">收藏</button> -->
				<div class="media" style="align-items: center;padding-top: 20px">
				  	<img src="/pic/7cda1e6db1f84fd00d40d67cfdeb6ebe.jpg" title="评论数量" class="mr-3" style="width: 45px;height: 45px;border-radius:20%;margin-left:70px" alt="评论数">
				    <h5 id="commentNum" style="color: #FF6347"></h5>
				</div>
				<hr style="margin-left:70px">
			</li>
			<li style="margin-top: 3px">
				<div class="media" style="align-items: center;">
				  <a href="javascript:void(0)" onclick="addFavorite()" >
				  	<img src="/pic/5aa193ce89129448b920d6c81237a588.jpg" class="mr-3 repost-img" style="width: 35px;height: 35px;margin-left:74px" >
				  </a>
				  <a href="javascript:void(0)" onclick="addFavorite()"  style="text-decoration: none;color: black">
				  	<h6>收藏</h6>
				  </a>
				</div>
			</li>
			<li style="margin-top: 3px">
				<div class="media" style="align-items: center;margin-top: 10px">
				  <img src="//s3.pstatp.com/toutiao/static/img/repost.021bf16.png" class="mr-3 repost-img" style="width: 35px;height: 35px;margin-left:74px" >
				  <h6>转发</h6>
				</div>
			</li>
			<li style="margin-top: 3px">
				<div class="media" style="align-items: center;margin-top: 10px">
				  <img src="/pic/4f5640643c3e593870a864b2a2c7377a.jpg" class="mr-3 repost-img" style="width: 35px;height: 35px;margin-left:74px" >
				  <h6>微信</h6>
				</div>
			</li>
			<li style="margin-top: 3px">
				<div class="media" style="align-items: center;margin-top: 10px">
				  <img src="/pic/8dbf641c5a6e0f2d5c41139b585241ed.jpg" class="mr-3 repost-img" style="width: 35px;height: 35px;margin-left:74px" >
				  <h6>QQ</h6>
				</div>
			</li>
			<li style="margin-top: 3px">
				<div class="media" style="align-items: center;margin-top: 10px">
				  <img src="/pic/a85435274cc0a5ef7fbb01b2615a6a12.jpg" class="mr-3 repost-img" style="width: 35px;height: 35px;margin-left:74px" >
				  <h6>微博</h6>
				</div>
			</li>
			
		</ul>
		
		
		<!-- 中间内容显示区域 -->
		<div id="content-wrapper" style="width:800px !important">
			<div style="padding: 0px 20px">
				<h1 style="font-weight: bold;">${article.title }</h1>
				<h6 style="padding: 15px 0px;">${article.user.nickname }
				<span style="margin-right: 15px"></span>
				<fmt:formatDate value="${article.created }" pattern="yyyy-MM-dd HH-mm-ss"/>
				</h6>
				
<span>



<article class="article" id="mp-editor"> 
 <!-- 政务处理 --> 
 <p data-role="original-title" style="display:none">原标题：柳青鸾就是岳绮罗？追《无心法师3》，这些彩蛋千万不能错过</p> 
 <p>作为《无心法师》系列的续作，《无心法师3》在开拍之初，就被很多纳入到2020年必追剧清单中。所以，这部剧刚一上线，就迅速引起了大量观众的关注。目前，《无心法师3》在口碑和热度上，都有非常不错的表现。在热度方面，该剧已经是爱奇艺电视剧热度榜第一名，而口碑方面，该剧在豆瓣的评分高达7.2分。</p> 
 <p >
 <img src="http://5b0988e595225.cdn.sohucs.com/images/20200305/a43a2dac59c34b01a85db5c04f3949ab.png" ></p> 
 <p>当然，随着《无心法师3》热度的不断上涨，该剧的争议也在慢慢增加。其中，大家最不满意的一点，就是这部剧现在的剧情，和宣传的时候不一致。该剧在播出之前，宣传该剧时表示，该剧剧情会和岳绮罗有关系，可以把整部剧看做是无心和岳绮罗的起源。但现在看来，《无心法师3》的剧情和当时宣传的完全不一样。</p> 
 <p >
 <img src="http://5b0988e595225.cdn.sohucs.com/images/20200305/77fe7cf2556745e7aea5ac2f402860c1.jpeg" style="width: 650px"></p> 
 <p>对此，《无心法师3》的编剧徐子沅在接受采访时，也回答了这个问题。徐子沅表示，如果大家在追剧的时候足够仔细，会发现这部剧里是有岳绮罗的影子的，在《无心法师3》的前期，该剧和岳绮罗的联系，会以彩蛋的形式出现。通过剧中的这些彩蛋，大家完全可以看得出来，剧中柳青鸾和柳玄鹄姐弟，和岳绮罗有着非常密切的关系。</p> 
 <p class="ql-align-center"><img src="http://5b0988e595225.cdn.sohucs.com/images/20200305/88e5d083f3ba4bee95cd732674e36c57.jpeg" style="width: 650px"></p> 
 <p>剧中第六集，柳青鸾跟着无心去查案子，在街上无心给她买了一直簪子。大家注意，这个簪子就是彩蛋之一，如果大家看过第一部《无心法师》的话，会觉得这个簪子非常熟悉。不错，这个簪子的形状和第一部《无心法师》中岳绮罗施法用的纸片人，几乎一模一样。岳绮罗的纸片人，大家都不陌生，那可是她的拿手好戏。</p> 
 <p class="ql-align-center"><img src="http://5b0988e595225.cdn.sohucs.com/images/20200305/62f45a567a1942b79bfb8f746974cafb.jpeg" max-width="600"></p> 
 <p>就岳绮罗而言，和她相关的最重要的几个标记，除了纸片人之外，再有就是她的大红斗篷。在第一部《无心法师》中，岳绮罗第一次穿着红色斗篷亮相时，很多观众被惊艳到。而在《无心法师3》的预告中，我们可以看到，柳青鸾也有穿着一件红色斗篷。当然，目前还不清楚穿着斗篷的，到底是弟弟柳玄鹄还是姐姐柳青鸾。</p> 
 <p class="ql-align-center"><img src="http://5b0988e595225.cdn.sohucs.com/images/20200305/752ebb659bb4461689f5cb3b55d63017.jpeg" max-width="600"></p> 
 <p>纸片人有了，红色斗篷也有了，在硬件上，柳青鸾和岳绮罗越来越相近。但是，在软件上，柳青鸾和岳绮罗，还差两点。其中一点，是岳绮罗的笑。看过第一部《无心法师》的朋友，对岳绮罗的笑肯定印象深刻，因为岳绮罗的笑中，有一种非常邪魅的感觉，让人一看上去就觉得，这不是人类能有的笑。</p> 
 <p class="ql-align-center"><img src="http://5b0988e595225.cdn.sohucs.com/images/20200305/a02f0fecd0de4267a2efcfb27fe2d4a3.jpeg" max-width="600"></p> 
 <p>最后，岳绮罗和柳青鸾之间，她们心里都有一个无心。在第一部《无心法师》中，岳绮罗就喜欢无心，虽然他身边有个痴情的张显宗，但岳绮罗心心念念的，始终是无心。在《无心法师3》中，柳青鸾也喜欢无心，这一点相信大家都没什么意见。而且，如果柳青鸾就是岳绮罗的话，那么岳绮罗喜欢无心，应该就是从柳青鸾开始的。</p> 
 <p class="ql-align-center"><img src="http://5b0988e595225.cdn.sohucs.com/images/20200305/3ca3b16b0b3b43b0b63416f1c9f7d570.jpeg" max-width="600"></p> 
 <p>现在，我们可以梳理一下，岳绮罗和柳青鸾之间的关系了。她们有一样的纸人和红色斗篷，她们都有一种非常邪魅的笑容，而且她们两个都喜欢无心。当然，除了一样的地方，还有不一样的，就是岳绮罗会法术，而柳青鸾不会。注意，关键点来了，柳青鸾不会，但是柳玄鹄是会法术的。虽然现在的柳玄鹄还不会，但柳玄鹄混进妖族，就是为了学法术。</p> 
 <p class="ql-align-center"><img src="http://5b0988e595225.cdn.sohucs.com/images/20200305/8de21729c13b4d05916dac7960bb2af5.jpeg" max-width="600"></p> 
 <p>所以，之后的柳玄鹄肯定会法术，而且应该是很强的那种法术。另外，柳玄鹄去瑶族是为了学习长生术，大家知道，岳绮罗就是长生不死的怪物，由此来看，柳玄鹄应该是练成了不死术。由此来看，岳绮罗就是柳青鸾和柳玄鹄他们姐弟俩的合体，而且他们本身就是一体，后面再次合体，倒也不是不可能。</p> 
 <p></p> 
 <p class="ql-align-center"><img src="http://5b0988e595225.cdn.sohucs.com/images/20200305/7b410618f3d54809afd50742f006bfb6.jpeg" max-width="600"></p> 
 <p>当然，以上都只是我们根据剧中和预告片中的一些细节作出的推测，仅供大家参考。如果大家有什么更靠谱理解，也欢迎在留言区留言，大家一起聊聊无心和岳绮罗的那些事儿。</p> 
 <p>当然，这只是《无心法师3》中众多彩蛋中的一个。其余还有很多彩蛋，都和岳绮罗有关，大家在追剧的时候，一定要注意不要错过。</p> 
 <!-- 政务账号添加来源标示处理 --> 
 <!-- 政务账号添加来源标示处理 --> 
 <p data-role="editor-name">责任编辑：<span></span></p> 
</article>

</span>
				
				<c:if test="${article.content_type=='IMAGE'}">
					
					<div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
					  <div class="carousel-inner">
					    <div class="carousel-item active">
					      <img src="/pic/${article.content}" class="d-block w-100" alt="...">
					    </div>
					    <div class="carousel-item">
					      <img src="/pic/${article.picture}" class="d-block w-100" alt="...">
					    </div>
					    <div class="carousel-item">
					      <img src="/pic/${article.content}" class="d-block w-100" alt="...">
					    </div>
					  </div>
					  <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
					    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
					    <span class="sr-only">Previous</span>
					  </a>
					  <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
					    <span class="carousel-control-next-icon" aria-hidden="true"></span>
					    <span class="sr-only">Next</span>
					  </a>
					</div>
				</c:if>
				
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
				<h5 style="margin-top: 10px">相关推荐</h5>
				<ul class="list-unstyled">
						<c:forEach items="${relevantArticle}" var="relevant">
							<li class="media">
								<a href="/index/select?id=${relevant.id }" >
									<img src="/pic/${relevant.picture }" class="mt-1" alt="..." width="50px" height="50px">
								</a>
								<div class="media-body " style="margin-left: 5px;">
									<a href="/index/select?id=${relevant.id }" 
									style="font-size: 10px;height:10px;color: black;overflow: hidden;white-space: nowrap;text-overflow:ellipsis ;">${relevant.title }</a>
									<p style="font-size: 10px;padding-top: 10px">${relevant.user.nickname }&nbsp;&nbsp;
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
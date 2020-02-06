<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript">

function check(){
	var text=$("[name=text]").val();
	var url="/link/selects?text="+text;
	$("#content-wrapper").load(url);
}
function goPage(pageNum){
	var text=$("[name=text]").val();
	var url="/link/selects?pageNum="+pageNum+"&text="+text;
	$("#content-wrapper").load(url);
}
//修改
function update(id){
	//根据id查看链接 
	$.post(
		"/link/selectLinkID",
		{id:id},
		function(result){
			//把值给谁?
					
			$("#updID").val(result.id);
			$("#updText").val(result.text);
			$("#updUrl").val(result.url);
			
		}
	); 
}
function toUpdate(){
	var updateLink=$("#updateLink").serialize();
	$.post(
			"/link/update",
			updateLink,
			function(result){
				if(result){
					alert("修改成功");
					
					$("#content-wrapper").load("link/selects");
					
				}else{
					alert("修改失败");
				}
			}
	);
}
//新增
function add(){
	
}
</script>
</head>
<body>
	<div style="padding: 0 10px 0 10px">


		<div>
			<form id="f1">
				链接名称: <input type="text" name="text" value="${text}" /> 
				<button type="button" class="btn btn-success" onclick="check()">搜索</button>
				<button type="button" class="btn btn-success" onclick="add()">添加链接</button>
			</form>
		</div>
		<br>

		<div class="table-responsive-sm">
			<table class="table table-striped">
				<thead>
				<tr>
					<th>编号</th>
					<th>链接名称</th>
					<th>链接地址</th>
					<th>创建时间</th>
					<th>操作</th>
				</tr>
				</thead>
				<c:forEach items="${link}" var="l" varStatus="i">
					<tr>
						<td>${l.id }</td>
						<td>${l.text}</td>
						<td>${l.url}</td>
						<td><fmt:formatDate value="${l.created}" /></td>
						<td>
							<button type="button" onclick="update(${l.id})" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalScrollable">
							 修改
							</button>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<jsp:include page="/WEB-INF/view/public/page.jsp"></jsp:include>

	</div>
<!-- Modal -->
		 <form id="updateLink" method="post">
		<div class="modal fade" id="exampleModalScrollable" tabindex="-1" role="dialog" aria-labelledby="exampleModalScrollableTitle" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-scrollable" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		       <h5 class="modal-title" id="exampleModalLabel">链接修改</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		       		<div id="alert" class="alert alert-danger my-2" style="display: none;margin-left: 20px;width: 404px" role="alert">
						  
					</div>
					<input type="hidden" id="updID" name="id" /> 
					链接名称: <input type="text" id="updText" name="text" /><br> 
					链接地址: <input type="text" id="updUrl" name="url"  /> 
			
		      </div>
		      <div class="modal-footer">
		       <button type="button"  class="btn btn-success" id="toUpdateLink"
					data-dismiss="modal" onclick="toUpdate()">确定修改</button>
		      </div>
		    </div>
		  </div>
		</div>
		</form>
		
</body>
</html>
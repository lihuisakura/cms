<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript">
	$(function(){
		$.post("/article/channelList",function(list){
			for ( var i in list) {
				$("[name='channel_id']").append("<option value="+list[i].id+">"+list[i].name+"</option>");
			}
		});
		$("[name='channel_id']").change(function(){
			var id=$("[name='channel_id']").val();
			if(id==0){
				$("[name='category_id']").empty();
				$("[name='category_id']").append("<option value='0'>---请选择分类---</option>");
			}else{
				$.post("/article/categoryList",{id:id},function(list){
					$("[name='category_id']").empty();
					$("[name='category_id']").append("<option value='0'>---请选择分类---</option>");
					for ( var i in list) {
						$("[name='category_id']").append("<option value="+list[i].id+">"+list[i].name+"</option>");
					}
				});
			}
			
		});
		
	})
	
	
	$("[name=myFiles]").change(function(){
		if(typeof(FileReader)!='undefined'){
			var show=$("#showImg");
			show.html("");
			var regex=/^([a-zA-Z0-9\s_\\.\-:])+(.jpg|.jpeg|.gif|.png|.bmp)$/;
			$($(this)[0].files).each(function(){
				var file=$(this);
				if(regex.test(file[0].name.toLowerCase())){
					var reader=new FileReader();
					reader.onload=function(e){
						show.append("<img src='"+e.target.result+"' style='height:100px;width:100px;'/>");
						show.append("<textarea name='myMessages' style='height:100px;width:100px;'></textarea>");
						show.append("<br/>");
					}
					reader.readAsDataURL(file[0]);
				}else{
					alert(file[0].name+"不是一个合法的图片");
					show.html("");
					return false;
				}
				
			})
		}else{
			alert("当前浏览器不支持图片预览");
		}
	});
	
	
	//发布
	function add(){
		//ajax提交    上传 
		//序列化表单数据 带文件
		var formData=new FormData($("#f1")[0]);
		//ajax提交
		$.ajax({
			// 告诉jQuery不要去处理发送的数据
			processData : false,
			// 告诉jQuery不要去设置Content-Type请求头
			contentType : false,
			cache:false,
			url:"/article/addImage",
			async:false,
			data:formData,
			type:"post",
			success:function(msg){
				if(msg){
					alert("发布完成");
					$("#content-wrapper").load("/article/toAdd");
				}else{
					alert("发布失败");
				}
			}
			
		})
	}

</script>
</head>
<body>

<form style="margin-left:50px" name="example" id="f1">
		文章标题:
		<input style="width: 920px" type="text" name="title" class="form-control"/><br>
		<input type="hidden" name="content_type" value="IMAGE"/>
		<input type="hidden" name="user_id" value="${user.id}"/>
		<div class="form-inline">
		栏目:<select name="channel_id" class="form-control">
			<option value="0">---请选择栏目---</option>
			</select>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		分类:<select name="category_id" class="form-control">
			<option value="0">---请选择分类---</option>
		</select>
		</div>
		<br>
			标题图片:
		<input type="file" name="file"/>
		<br>
		<br>
		<div class="form-inline">
			图片内容:
		<input type="file" accept="image/*" name="myFiles" multiple="multiple" class="up-file"/>
		<div id="showImg" ></div>
		</div>
		<font style="color: red">注：请同时选中多个图片上传</font>
		<br>
		<br>
		<button type="button" class="btn btn-primary" onclick="add()">发布</button>
	</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.jojo.model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>我的主页</title>
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/image/title.ico">

<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/bootstrap3/css/bootstrap.min.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script
	src="${pageContext.request.contextPath}/bootstrap3/js/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script
	src="${pageContext.request.contextPath }/bootstrap3/js/bootstrap.min.js"></script>


<%
	// 权限验证
	if (session.getAttribute("currentUser") == null) {
		response.sendRedirect(request.getContextPath() + "/index");
		return;
	}
%>
</head>
<body>

	<!-- 头部导航条 -->
	<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#"> Brand</a>
		</div>

		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="${pageContext.request.contextPath }/index">首页</a></li>
				<li><a href="#">Link</a></li>
			</ul>

			<form class="navbar-form navbar-left" role="search" action="index"
				method="post">
				<div class="form-group">
					<input type="text" class="form-control" placeholder="Search"
						id="keyword" name="keyword"> <input type="hidden"
						value="selectFood" id="action" name="action" />
				</div>
				<!-- Single button -->
				<div class="btn-group">
					<button type="submit" class="btn btn-default ">搜一搜</button>
				</div>
			</form>


			<ul class="nav navbar-nav navbar-right"
				style="margin: auto 80px auto auto;">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">个人中心 <span class="caret"></span></a>
					<ul class="dropdown-menu" role="menu">
						<li><a
							href="${pageContext.request.contextPath }/userMain.jsp">我的主页</a></li>
						<li><a
							href="${pageContext.request.contextPath }/cartDetail?userId=${currentUser.userId}">购物车</a></li>
						<li class="divider"></li>
						<li><a href="#"onclick="logout()">退出</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
	</nav>
<form id="logout" action="userInfo" method="post" style="display: none;">
<input type="hidden" id="action" name="action" value="logout" />
</form>
<script>
function logout(){
	if(confirm("确认退出吗？")){
		$("#logout").submit();
	}
}
</script>


	<div class="container" style="margin: 80px;">
		<div class="row clearfix">
			<div class="col-md-8 column">
				<div class="row clearfix">
					<div class="col-md-12 column">
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="row clearfix">
									<div class="col-md-2 column">
										<div style="height: 100px; width: 100px;">
											<!-- 这里要设个最大宽度和高度 -->
											<img alt="${currentUser.userName }"
												src="${pageContext.request.contextPath }/${currentUser.pic}">
										</div>
									</div>
									<div class="col-md-6 column">
										<div class="page-header">
											<h3>Hi ! &nbsp;&nbsp;${currentUser.userName }</h3>
										</div>
										<h4>
											<span class="label label-primary">学号</span>&nbsp;&nbsp; <span>${currentUser.userIdCard }</span>
											<span class="label label-primary" style="margin-left: 15px;">性别</span>&nbsp;&nbsp;
											<span>${currentUser.sex }</span>
										</h4>
										<h4>
											<span class="label label-primary">电话</span>&nbsp;&nbsp; <span>${currentUser.userTel }</span>
											<span class="label label-primary" style="margin-left: 15px;">年龄</span>&nbsp;&nbsp;
											<span>${currentUser.age }</span>
										</h4>
										<h4>
											<span class="label label-primary">送货地址</span>&nbsp;&nbsp;
											${currentUser.address }
										</h4>
									</div>
									<div class="col-md-4 column"></div>
								</div>
								<div class="row clearfix">
									<div class="col-md-2 column"></div>
									<div class="col-md-4 column">
										<h4>
											<span class="label label-warning">累计剁手</span>&nbsp;&nbsp;${currentUser.sum }
										</h4>
									</div>
									<div class="col-md-3 column"></div>
									<div class="col-md-3 column">
										<!-- 模态框准备 -->
										<button type="button" class="btn btn-success"
											data-toggle="modal" data-target="#myModal">修改我的资料</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row clearfix">
					<div class="col-md-12 column">
						<div class="panel panel-default">
							<div class="panel-body">
								<!-- 订单信息导航 -->
								<ul class="nav nav-pills" role="tablist">
									<li role="presentation"><a
										href="${pageContext.request.contextPath }/orderList?id=${currentUser.userId}&status=0&flag=true&page=1">已付款</a></li>
									<li role="presentation"><a
										href="${pageContext.request.contextPath }/orderList?id=${currentUser.userId}&status=1&flag=true&page=1">待处理</a></li>
									<li role="presentation"><a
										href="${pageContext.request.contextPath }/orderList?id=${currentUser.userId}&status=2&flag=true&page=1">已完成</a></li>
								</ul>

								<%
									if (request.getAttribute("xxxjsp") != null) {
								%>
								<jsp:include page="${xxxjsp }"></jsp:include>
								<%
									} else {
								%>
								<div class="jumbotron" style="margin-top: 20px;">
									<h2>点击上方按钮，查询自己的订单~</h2>
									<h3>~\(≧▽≦)/~~\(≧▽≦)/~~\(≧▽≦)/~</h3>
								</div>
								<%
									}
								%>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-4 column">
				<div class="panel panel-default" id="favourite">
					<div class="panel-body">
						<div class="page-header">
							<h3>我的收藏夹</h3>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 模态框代码，我就把你放在最下面了 -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel"
						style="text-align: center;">个人信息修改</h4>
				</div>
				<div class="modal-body">

					<form class="form-horizontal" role="form" id="updateUserForm"
						method="post"
						action="${pageContext.request.contextPath }/userInfo">

						<input type="text" name="action" id="action" value="update"
							style="display: none;">

						<div class="form-group">
							<label for="userName" class="col-sm-2 control-label">用户名</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="userName"
									id="userName" value="${currentUser.userName }">
							</div>
						</div>

						<div class="form-group">
							<label for="password" class="col-sm-2 control-label">密码</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" name="password"
									id="password" value="${currentUser.password }">
							</div>
						</div>

						<div class="form-group">
							<label for="userIdCard" class="col-sm-2 control-label">学号</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="userIdCard"
									id="userIdCard" value="${currentUser.userIdCard }">
							</div>
						</div>

						<div class="form-group">
							<label for="userRealName" class="col-sm-2 control-label">真实姓名</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="userRealName"
									id="userRealName" value="${currentUser.userRealName }">
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label">性别</label>&nbsp;&nbsp;&nbsp;
							<label class="radio-inline"> <input type="radio"
								name="sex" id="sex" value="男"
								<%if ("男".equals(((User) session.getAttribute("currentUser")).getSex())) {
				out.print(" checked");
			}%>>男
							</label> <label class="radio-inline"> <input type="radio"
								name="sex" id="sex" value="女"
								<%if ("女".equals(((User) session.getAttribute("currentUser")).getSex())) {
				out.print(" checked");
			}%>>女
							</label>
						</div>

						<div class="form-group">
							<label for="age" class="col-sm-2 control-label">年龄</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="age" id="age"
									value="${currentUser.age }">
							</div>
						</div>

						<div class="form-group">
							<label for="userTel" class="col-sm-2 control-label">联系电话</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="userTel"
									id="userTel" value="${currentUser.userTel }">
							</div>
						</div>

						<div class="form-group">
							<label for="address" class="col-sm-2 control-label">送货地址</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="address"
									id="address" value="${currentUser.address }">
							</div>
						</div>

					</form>

					<div class="modal-footer">
						<button type="button" class="btn btn-danger btn-lg btn-block"
							data-dismiss="modal">确认修改个人信息</button>
					</div>
				</div>
			</div>
		</div>
	</div>


</body>
<script type="text/javascript">

	function deleteFavourite(){
		var srcElement = $(window.event.srcElement);
		$.ajax({
			url:"alterFavourite",
			type:"POST",
			data:{
				action:"delete",
				favouriteId:srcElement.parent().attr("id")
			}
		}).done(function(){
			var favourite = srcElement.parent().parent().parent().parent().parent();
			favourite.fadeOut("fast", function(){ favourite.remove(); });
			alert("删除成功！");
		});
	}

	$(document).ready(function() {
		$("button.btn-block").click(function() {
			$("#updateUserForm").submit();
		});
	});

	$(document).ready(function() {
		$.ajax({
			url:"alterFavourite",
			type:"POST",
			data:{
				action:"select"
			}
		}).done(function(data){
			var favouriteList = $.parseJSON(data);
			for(var i = 0; i < favouriteList.length; i++){
				var node = "	<div class=\"panel panel-default\">"
									+		"<div class=\"panel-body\">"
									+			"<div class=\"row clearfix\">"
									+				"<div class=\"col-md-3 column\">"+"缩略图"+"</div>"
									+				"<div class=\"col-md-5 column\"><a href=\"foodDetail?foodId="+favouriteList[i].foodId+"\">"+favouriteList[i].foodName+"</a></div>"
									+				"<div class=\"col-md-2 column\">"+favouriteList[i].foodPrice+"</div>"
									+				"<div class=\"col-md-2 column\">"
									+					"<button type=\"button\" class=\"close\" onclick=\"deleteFavourite()\" id=\""+favouriteList[i].favouriteId+"\">"
									+						"<span aria-hidden=\"true\">&times;</span>"
									+						"<span class=\"sr-only\">Close</span>"
									+					"</button>"
									+				"</div>"
									+			"</div>"
									+		"</div>"
									+	"</div>";
				$("#favourite").append($(node));
			}
		});
	});
</script>
</html>
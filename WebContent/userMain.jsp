<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

			<form class="navbar-form navbar-left" role="search">
				<div class="form-group">
					<input type="text" class="form-control" placeholder="Search">
				</div>
				<!-- Single button -->
				<div class="btn-group">
					<button type="button" class="btn btn-default dropdown-toggle"
						data-toggle="dropdown">
						搜一搜 <span class="caret"></span>
					</button>
					<ul class="dropdown-menu" role="menu">
						<li><a href="#">搜店家</a></li>
						<li><a href="#">搜小菜</a></li>
					</ul>
				</div>
			</form>


			<ul class="nav navbar-nav navbar-right"
				style="margin: auto 80px auto auto;">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">个人中心 <span class="caret"></span></a>
					<ul class="dropdown-menu" role="menu">
						<li><a href="${pageContext.request.contextPath }/userMain.jsp">我的主页</a></li>
						<li><a href="#">购物车</a></li>
						<li class="divider"></li>
						<li><a href="#">退出</a></li>
					</ul></li>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid --> </nav>

	<div class="container" style="margin: 80px;">
		<div class="row clearfix">
			<div class="col-md-8 column">
				<div class="thumbnail">
					<div class="caption">
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
									<span class="label label-primary">学号</span>&nbsp;&nbsp;
									${currentUser.userIdCard }
								</h4>
								<h4>
									<span class="label label-primary">电话</span>&nbsp;&nbsp;
									${currentUser.userTel }
								</h4>
								<h4>
									<span class="label label-primary">送货地址</span>
									&nbsp;&nbsp;${currentUser.address }
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
								<button type="button" class="btn btn-success" data-toggle="modal" data-target="#myModal">
									修改我的资料
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-4 column">
				<div class="page-header">
					<h3>我的购物车</h3>
				</div>
				<table class="table table-striped table-condensed table-hover">
					<thead>
						<tr>
							<th>编号</th>
							<th>产品</th>
							<th>交付时间</th>
							<th>状态</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>TB - Monthly</td>
							<td>01/04/2012</td>
							<td>Default</td>
						</tr>
						<tr class="success">
							<td>1</td>
							<td>TB - Monthly</td>
							<td>01/04/2012</td>
							<td>Approved</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>

		<div class="row clearfix">
			<div class="col-md-8 column">
			<div class="panel panel-default">
			  <div class="panel-body">
			   <!-- 订单信息导航 -->
					 	<ul class="nav nav-pills" role="tablist">
						  <li role="presentation" class="active">
						  		<a href="${pageContext.request.contextPath }/orderList?id=${currentUser.userId}&status=0&flag=true&page=1">已付款</a></li>
						  <li role="presentation">
						  		<a href="${pageContext.request.contextPath }/orderList?id=${currentUser.userId}&status=1&flag=true&page=1">待处理</a></li>
						  <li role="presentation">
						  		<a href="${pageContext.request.contextPath }/orderList?id=${currentUser.userId}&status=2&flag=true&page=1">已完成</a></li>
						</ul>
						
						<%if(request.getAttribute("xxxjsp") != null){ %>
							<jsp:include page="background/userStatus.jsp"></jsp:include>	
						<%}else{ %>
							<div class="jumbotron" style="margin-top: 20px;">
							  <h2>点击上方按钮，查询自己的订单~</h2>
						  	  <h3>~\(≧▽≦)/~~\(≧▽≦)/~~\(≧▽≦)/~</h3>
							</div>
						<%} %>
			  </div>
			</div>
			
				 
			</div>
			<div class="col-md-4 column">
				<div class="panel panel-default">
				  <div class="panel-body">
				    历史记录
				  </div>
				</div>
			</div>
		</div>
	</div>

<!-- 模态框代码，我就把你放在最下面了 -->
<div class="modal fade" id="myModal" tabindex="-1" 
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">
        	<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
        </button>
        <h4 class="modal-title" id="myModalLabel" style="text-align: center;">个人信息修改</h4>
      </div>
      <div class="modal-body">
       
					<form class="form-horizontal" role="form" id="updateUserForm"  method="post"
							action="${pageContext.request.contextPath }/userInfoUpdate">

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
							name="sex" id="sex" value="男"> 男
						</label> 
						<label class="radio-inline"> <input type="radio"
							name="sex" id="sex" value="option3"> 女
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
							<input type="text" class="form-control" name="userTel" id="userTel"
								value="${currentUser.userTel }">
						</div>
					</div>
					
					<div class="form-group">
						<label for="address" class="col-sm-2 control-label">送货地址</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="address" id="address"
								value="${currentUser.address }">
						</div>
					</div>

					</form>
			
      <div class="modal-footer">
        <button type="button" class="btn btn-danger btn-lg btn-block" 
        				data-dismiss="modal" onclick="updateUser()">
        	确认修改个人信息
        </button>
      </div>
    </div>
  </div>
</div>


</body>
<script type="text/javascript">
	function updateUser() {
		var form = document.getElementById("updateUserForm");
		form.submit();
	}
</script>
</html>
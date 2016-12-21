<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>商品详情</title>
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/images/title.ico">


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
	if (session.getAttribute("currentUser") == null && session.getAttribute("currentMerchant") == null) {
		response.sendRedirect(request.getContextPath() + "/index");
		return;
	}
%>

<style type="text/css">
.pictures {
	float: left;
	width: 100px;
	height: 87px;
	margin: 5px, auto, auto, 10px;
}

</style>
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
				<a class="navbar-brand" href="#">Suzhou University</a>
			</div>
	
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li><a href="${pageContext.request.contextPath }/index">首页</a></li>
				</ul>
	
				<form class="navbar-form navbar-left" role="search" action="index" method="post">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search" id="keyword" name="keyword">
						<input type="hidden" value="selectFood" id="action" name="action" />
					</div>
					<!-- Single button -->
					<div class="btn-group">
						<button type="submit" class="btn btn-default " >
							搜一搜 
						</button>
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

<input type="hidden" id="fid" value="${food.foodId}" />
<input type="hidden" id="uid" value="${currentUser.userId}" />

	<div class="container">
		<div class="row clearfix" style="margin-top: 70px">
			<div class="col-md-9 column">

				<div class="row clearfix">
					<div id="left" class="col-md-6 column">
						<div id="top">
							<img alt="156*124" width="400px" height="260px" src="images/duck.jpg"
								id="picture">
						</div>
						<div id="bottom" style="margin-top: 5px">
							<div style="margin-left: 5px" class="pictures">
								<img alt="150*50" id="" width="98px" height="85px" src="images/duck.jpg">
							</div>
							<div style="margin-left: 5px" class="pictures">
								<img alt="150*50" id="" width="98px" height="85px" src="images/duck.jpg">
							</div>
							<div style="margin-left: 5px" class="pictures">
								<img alt="150*50" id="" width="98px" height="85px" src="images/duck.jpg">
							</div>
						</div>
					</div>
					<div id="right" class="col-md-6 column">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">${food.foodName }</h3>
							</div>
							<div class="panel-body">
								<h4>
									<span class="label label-primary">描述</span>&nbsp;&nbsp;
									${food.description }
								</h4>
								<h4>
									<span class="label label-primary">价格</span>&nbsp;&nbsp;
									${food.foodPrice }
								</h4>
								<h4>
									<span class="label label-primary">剩余</span>&nbsp;&nbsp;
									<span id="fn">${food.num }</span>
								</h4>
								<!-- 数量与输入框 -->
								<div class="row clearfix">
									<div class="col-md-2 column">
										<h4>
											<span class="label label-primary">数量</span>
										</h4>
									</div>
									<div class="col-md-4 column">
										<div class="input-group">
											<span class="input-group-btn">
												<button class="btn btn-default" type="button"
													onclick="minus()">-</button>
											</span>
											 <input type="text" class="form-control" value="1" id="foodNum"> 
											<span class="input-group-btn">
												<button class="btn btn-default" type="button"
													onclick="plus()">+</button>
											</span>
										</div>
									</div>
								</div>
								<ul class="nav nav-list">
									<li class="divider"></li>
								</ul>
								<div class="row clearfix">
									<div class="col-md-6 column">
										<button type="button" style="margin-top: 25px;" class="btn btn-default"
										onclick="cart()">加购物车</button>
									</div>
									<div class="col-md-6 column">
										<button type="button" style="margin-top: 25px;" class="btn btn-danger"
										onclick="generateOrder()">提交订单</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div><!-- 头部终了 -->
				
			<div style="margin-top: 50px;">	<!-- 评论 -->
				<jsp:include page="background/commentList.jsp"></jsp:include>		
			</div><!-- 评论 -->
				
			</div>
			<div class="col-md-3 column">	<!-- 历史记录 -->
				<div class="page-header">
				  <h2>footprints</h2>
				</div>
				<c:forEach var="food" items="${historyList}">
					<div class="panel panel-default">
					  <div class="panel-body">
					  	<a href="${pageContext.request.contextPath }/foodDetail?foodId=${food.foodId}">
					    	<img src="images/selectFood.png" alt="缩略图" style="margin-left:50px; height:125px; width:125px;"/>
					    </a>
					    <h3>${food.foodName}</h3>
					    <h3>￥：<small>${food.foodPrice }</small></h3>
					  </div>
					</div>
				</c:forEach>
			</div>	<!-- 历史记录 -->
		</div>
	</div>
	
	
		
	<form style="display:none;" method="post" action="${pageContext.request.contextPath }/alterOrder" id="a">
		<input type="text" id="action" name="action" value="generate">
		<input type="text" id="userId" name="userId" value="">
		<input type="text" id="foodId" name="foodId" value="">
		<input type="text" id="num" name="num" value="">
	</form>
</body>
<script type="text/javascript">
function minus(){
	var foodNum = document.getElementById("foodNum");
	if(foodNum.value > 1){
		foodNum.value -= 1;
	}
}
function plus(){
	var foodNum = document.getElementById("foodNum");
		foodNum.value = foodNum.value-1+2;
}
function generateOrder(){
	$("#userId").val($("#uid").val());
	$("#foodId").val($("#fid").val());
	$("#num").val($("#foodNum").val());
	$("#a").submit();
}
function cart(){
	$.ajax({
		url: "alterCart",
		type: "POST",
		data: {
			action:"add",
			foodId: foodId,
			userId: $("#uId").val(),
			num: $("#foodNum").val()
		}
	}).done(function(){
		alert("添加成功 ！ ");
	}).fail(function(){
		alert("抱歉失败了！")
	});
}
</script>
</html>
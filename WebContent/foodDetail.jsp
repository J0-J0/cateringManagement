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
							<li><a
								href="${pageContext.request.contextPath }/userMain.jsp">我的主页</a></li>
							<li><a 
								href="${pageContext.request.contextPath }/cartDetail?userId=${currentUser.userId}">购物车</a></li>
							<li class="divider"></li>
							<li><a href="#">退出</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
	</nav>

<input type="hidden" id="fid" value="${food.foodId}" />

	<div class="container">
		<div class="row clearfix" style="margin-top: 70px">
			<div class="col-md-9 column">

				<div class="row clearfix">
					<div id="left" class="col-md-6 column">
						<div id="top">
							<img alt="156*124" width="400px" height="260px" src=""
								id="picture">
						</div>
						<div id="bottom" style="margin-top: 5px">
							<div style="margin-left: 5px" class="pictures">
								<img alt="150*50" id="" width="98px" height="85px" src="">
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
									${food.num }
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
											</span> <input type="text" class="form-control" value="1"
												id="foodNum"> <span class="input-group-btn">
												<button class="btn btn-default" type="button"
													onclick="plus()">+</button>
											</span>
										</div>
									</div>
								</div>
								<ul class="nav nav-list">
									<li class="divider"></li>
								</ul>
								<button data-toggle="modal" data-target="#myModal" type="button"
									style="margin-top: 25px;" class="btn btn-warning"
									onclick="fillModal()">提交订单</button>
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
					    	<img src="" alt="缩略图" style="margin-left:50px; height:125px; width:125px;"/>
					    </a>
					    <h3>${food.foodName}</h3>
					    <h3>￥：<small>${food.foodPrice }</small></h3>
					  </div>
					</div>
				</c:forEach>
			</div>	<!-- 历史记录 -->
		</div>
	</div>

	<!-- 模态框 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">想好了再下单，要为自己的订单负责哦~</h4>
      </div>
      <div class="modal-body">
					<form class="form-horizontal" role="form" id="generateOrder"
						method="post"
						action="${pageContext.request.contextPath }/alterOrder">

						<input type="hidden" id="action" name="action" value="add" />
						<input type="hidden" id="foodId" name="foodId" value="${food.foodId }" />
						<input type="hidden" id="userId" name="userId" value="${currentUser.userId}" />

						<div class="form-group">
							<label for="inputText3" class="col-sm-2 control-label">从</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="merchantName"
									id="merchantName" value="${merchantName}" disabled>
							</div>
						</div>

						<div class="form-group">
							<label for="inputText3" class="col-sm-2 control-label">购买</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="foodName"
									id="foodName" value="${food.foodName }" disabled>
							</div>
						</div>

						<div class="form-group">
							<label for="inputText3" class="col-sm-2 control-label">数量</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="num" id="num"
									value="" onchange="updateSum()">
							</div>
						</div>

						<div class="form-group">
							<label for="inputText3" class="col-sm-2 control-label">单价</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="foodPrice"
									id="foodPrice" value="${food.foodPrice }" disabled>
							</div>
						</div>
						
						<div class="form-group">
							<label for="inputText3" class="col-sm-2 control-label">总价</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="sum"
									id="sum" value=" " disabled>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label">取货方式</label>&nbsp;&nbsp;&nbsp;
							<label class="radio-inline"> <input type="radio"
								name="way" id="way" value="外送"> 外送
							</label> <label class="radio-inline"> <input type="radio"
								name="way" id="way" value="自取"> 自取
							</label>
						</div>

						<div class="form-group">
							<label for="inputText3" class="col-sm-2 control-label">联系电话</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="userTel"
									id="userTel" value="${currentUser.userTel }">
							</div>
						</div>

						<div class="form-group" id="addressDIV">
							<label for="inputText3" class="col-sm-2 control-label">送货地址</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="address"
									id="address" value="${currentUser.address }">
							</div>
						</div>

					</form>
				</div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">再回去看看吧</button>
        <button type="button" class="btn btn-primary" data-dismiss="modal"onclick="sub()">就决定是它了</button>
      </div>
    </div>
  </div>
</div>
</body>
<script type="text/javascript">
function updateSum(){
	var num = document.getElementById("num").value;
	var foodPrice = document.getElementById("foodPrice").value;
	document.getElementById("sum").value = num*foodPrice;
}

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

function fillModal(){
	var foodNum = document.getElementById("foodNum");
	var num = document.getElementById("num");
	var sum = document.getElementById("sum");
	var foodPrice = document.getElementById("foodPrice");
	num.value = foodNum.value;
	sum.value = num.value * foodPrice.value;
}

function sub(){
	var form = document.getElementById("generateOrder");
	form.submit();
}
</script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.jojo.model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>购物车</title>
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
			<a class="navbar-brand" href="#">Suzhou University</a>
		</div>

		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="${pageContext.request.contextPath }/index">首页</a></li>
				<li><a href="#">Link</a></li>
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
						<li><a href="${pageContext.request.contextPath }/userMain.jsp">我的主页</a></li>
						<li><a href="${pageContext.request.contextPath }/cartDetail?userId=${currentUser.userId}">购物车</a></li>
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
			<div class="col-md-12 column">
				<h2 class="text-center">My Cart</h2>
			</div>
		</div>
		
		
		<div class="row clearfix" style="margin-top: 10px;">
			<div class="col-md-2 column"></div>
			<div class="col-md-8 column">
<%
	// 啊，还是Java代码看着亲切啊!
	List<List<Cart>> cartList = (List<List<Cart>>)request.getAttribute("cartList");
	Iterator<List<Cart>> itList = cartList.iterator();
	while(itList.hasNext()){
		List<Cart> list = itList.next();
		StringBuilder foodIdList = new StringBuilder();
		StringBuilder numList = new StringBuilder();
%>
				<div class="panel panel-success"><!-- 外面板，此处标明商家名称 -->
					<div class="panel-heading"><%=list.get(0).getMerchantName() %></div>
					<div class="panel-body">
				<%
					Iterator<Cart> itCart = list.iterator();
					double totalSum = 0;
					while(itCart.hasNext()){
						Cart cart = itCart.next();
						totalSum += cart.getSum();
						foodIdList.append(":"+cart.getFoodId());
						numList.append(":"+cart.getNum());
				%>
						<div class="panel panel-default"><!-- 内面板，具体商品信息 -->
							<div class="panel-body">	<!-- 分三段 -->
								<div class="row clearfix">
									<div class="col-md-7 column"><!-- 商品信息 -->
										<div class="row clearfix">
											<div class="col-md-4 column">缩略图</div>
											<div class="col-md-6 column"><%=cart.getFoodName() %></div>
											<div class="col-md-2 column"><%=cart.getFoodPrice() %></div>
										</div>
									</div>
									
									<div class="col-md-3 column"><!-- 数量按钮 -->
										<div class="input-group" id="<%=cart.getCartId() %>" >
											<span class="input-group-btn">
												<button class="btn btn-default" type="button"
													onclick="minus()" id="<%=cart.getFoodPrice()%>">-</button>
											</span> 
											<input type="text" class="form-control"
												value="<%=cart.getNum()%>" disabled />
											<span class="input-group-btn">
												<button class="btn btn-default" type="button"
													onclick="plus()" id="<%=cart.getFoodPrice()%>">+</button>
											</span>
										</div>
									</div>
									<div class="col-md-1 column"><%=cart.getSum() %></div>
									<div class="col-md-1 column"><!-- 关闭按钮 -->
										<button type="button" class="close" id="<%=cart.getCartId() %>">
											<span aria-hidden="true">&times;</span>
											<span class="sr-only">Close</span>
										</button>
									</div>
								</div>
							</div>
						</div>
				<%
					}
				%>
						<div style="float:right;">
							<span style="font-size: medium;">累计：</span>
							<span style="font-size: medium;"><%=totalSum %></span>
							<form method="post" action="${pageContext.request.contextPath }/alterOrder">
								<input type="hidden" id="action" name="action" value="generate">
								<input type="hidden" id="source" name="source" value="fromCart">
								<input type="hidden" id="userId" name="userId" value="${currentUser.userId }">
								<input type="hidden" id="foodIdList" name="foodIdList" value="<%out.print(foodIdList.toString());%>">
								<input type="hidden" id="numList" name="numList" value="<%=numList.toString()%>">
								<input type="submit" class="btn btn-danger" value="check out !">
							</form>
							<!-- <a href="#" class="btn btn-danger">check out !</a> -->
						</div>
					</div>
				</div>
<%
	}
%>
			</div>
			<div class="col-md-2 column"></div>
		</div>
	</div>
</body>
<script>
function minus(){
	var srcElement = $(window.event.srcElement);
	var foodNum = srcElement.parent().next();
	var num = foodNum.val();
	if(num > 1){
		num = num - 1;
		var foodPrice = srcElement.attr("id");
		var sum = num * foodPrice;
		$.ajax({
			url:"alterCart",
			type:"POST",
			data:{
				action:"update",
				num: num,
				cartId: srcElement.parent().parent().attr("id"),
				sum: sum
			}
		}).done(function(){
			foodNum.val(num);
			srcElement.parent().parent().parent().next().text(sum);
		});
	}
}
function plus(){
	var srcElement = $(window.event.srcElement);
	var foodNum = srcElement.parent().prev();
	var num = foodNum.val() - 1 + 2;
	var foodPrice = srcElement.attr("id");
	var sum = num * foodPrice;
	$.ajax({
		url:"alterCart",
		type:"POST",
		data:{
			action:"update",
			num: num,
			cartId: srcElement.parent().parent().attr("id"),
			sum: sum
		}
	}).done(function(){
		foodNum.val(num);
		srcElement.parent().parent().parent().next().text(sum);
	});
}

$(document).ready(function(){
	$("button.close").click(function(){
		var srcElement = $(window.event.srcElement);
		$.ajax({
			url:"alterCart",
			type:"POST",
			data:{
				action:"delete",
				cartId:srcElement.parent().attr("id")
			}
		}).done(function(){
			var cart = srcElement.parent().parent().parent().parent().parent();
			cart.fadeOut("fast", function(){ cart.remove(); });
		});
	});
});

</script>
</html>
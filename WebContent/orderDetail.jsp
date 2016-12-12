<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.jojo.model.*, java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>苏应食堂~</title>
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

<link href="bootstrap3/css/date/jquery-ui-1.10.1.css" rel="stylesheet">
<link href="bootstrap3/css/date/siena.datepicker.css" rel="stylesheet">
<link href="bootstrap3/css/date/santiago.datepicker.css" rel="stylesheet">
<link href="bootstrap3/css/date/latoja.datepicker.css" rel="stylesheet">
<link href="bootstrap3/css/date/lugo.datepicker.css" rel="stylesheet">
<link href="bootstrap3/css/date/cangas.datepicker.css" rel="stylesheet">
<link href="bootstrap3/css/date/vigo.datepicker.css" rel="stylesheet">
<link href="bootstrap3/css/date/nigran.datepicker.css" rel="stylesheet">

</head>
<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-2 column"></div>
			<div class="col-md-8 column">
				<div class="page-header">
				  <h1>想好了再下单，要为自己的订单负责哦~</h1>
				</div>
				<form class="form-horizontal" role="form" id="1"
					method="post"
					action="${pageContext.request.contextPath }/alterOrder">

					<input type="hidden" id="action" name="action" value="add" />

					<div class="form-group">
						<label for="inputText3" class="col-sm-2 control-label">从</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="merchantName"
								id="merchantName" value="${order.merchantName}" disabled>
						</div>
					</div>
					<%
						List<OrderFood> orderFoodList = (List<OrderFood>) session.getAttribute("orderFoodList");
						for (OrderFood of : orderFoodList) {
					%>
					<div class="form-group">
						<label for="inputText3" class="col-sm-2 control-label">购买</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="foodName"
								id="foodName" value="<%=of.getFoodName() %>" disabled>
						</div>
					</div>

					<div class="form-group">
						<label for="inputText3" class="col-sm-2 control-label">数量</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="num" id="num"
								value="<%=of.getNum() %>" disabled>
						</div>
					</div>

					<div class="form-group">
						<label for="inputText3" class="col-sm-2 control-label">单价</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="foodPrice"
								id="foodPrice" value="<%=of.getFoodPrice() %>" disabled>
						</div>
					</div>
					<%} %>
					<div class="form-group">
						<label for="inputText3" class="col-sm-2 control-label">总价</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="sum" id="sum"
								value="${order.sum }" disabled>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">取货方式</label>&nbsp;&nbsp;&nbsp;
						<label class="radio-inline"> <input type="radio"
							name="way" id="way" value="外送" onclick="showAddress()"> 外送
						</label> <label class="radio-inline"> <input type="radio"
							name="way" id="way" value="自取" onclick="fadeAddress()"> 自取
						</label>
					</div>

					<div class="form-group">
						<label for="inputText3" class="col-sm-2 control-label">联系电话</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="userTel"
								id="userTel" value="${currentUser.userTel }">
						</div>
					</div>
					
					<div class="form-group">
						<label for="inputText3" class="col-sm-2 control-label">预定下单时间</label>
						<div class="col-sm-10">
							<article>
								<div>
									<input type="text" id="addTime" name="addTime" />
								</div>
							</article>
							<script src="bootstrap3/js/jquery-1.9.1.js"></script>
							<script src="bootstrap3/js/jquery-ui-1.10.1.min.js"></script>
							<script>
								$(function() {
									$( "#addTime" ).datepicker({
										inline: true,
										showOtherMonths: true
									})
									.datepicker('widget').wrap('<div class="ll-skin-lugo"/>');
								});
							</script>
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
			<button type="button" class="btn btn-primary btn-block btn-lg" onclick="addOrder()">就决定是它了</button>
			</div>
			<div class="col-md-2 column"></div>
		</div>
	</div>
</body>
<script type="text/javascript">
	function fadeAddress(){
		$("#addressDIV").fadeOut();
	}
	function showAddress(){
		$("#addressDIV").fadeIn();
	}
	function addOrder(){
		$("#1").submit();
	}
</script>
</html>
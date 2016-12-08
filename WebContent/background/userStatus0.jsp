<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/bootstrap3/css/bootstrap.min.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script
	src="${pageContext.request.contextPath}/bootstrap3/js/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script
	src="${pageContext.request.contextPath }/bootstrap3/js/bootstrap.min.js"></script>
</head>
<body>
<!-- 给ajax请求做准备 -->
<input id="userId" type="text" style="display:none;" value="${currentUser.userId }">
<input id="status" type="text" style="display:none;" value="${orderList[0].status }">

<div id="orderListDIV">
	<c:forEach var="userOrder" items="${orderList }">
		<div class="panel panel-default" style="margin: 20px auto auto auto;">
			<div class="panel-heading">
			<div class="checkbox-inline" style="margin: auto auto 15px auto;">
				<label><input type="checkbox"></label>
			</div>
			<span>${userOrder.addTime_}</span>
			<span style="margin-left: 40px;">
				<strong>商家名称 : </strong>
				<font id="merchantName">${userOrder.merchantName }</font>
			</span> 
			<span style="margin-left: 40px;">
				<strong>总金额 : </strong>
				<font id="sum">${userOrder.sum }</font>
			</span>
			<button type="button" class="close">
				<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
			</button>
		</div>

			<div class="panel-body">
				<table>
					<c:forEach var="food" items="${userOrder.foodList }">
						<tr>
							<td width="25%">缩略图</td>
							<td width="50%">${food.foodName }</td>
							<td width="17%">${food.num }</td>
							<td>${food.foodSum }</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</c:forEach>
</div>


	<div class="row clearfix" style="margin-top: 20px;">

		<div class="col-lg-4">
			<div class="input-group">
				<span class="input-group-btn">
					<button class="btn btn-default" type="button" id="left">
						<span class="glyphicon glyphicon-chevron-left"></span>上一页
					</button>
				</span> <input type="text" class="form-control" value="1" id="currentPage" /> <span
					class="input-group-btn">
					<button class="btn btn-default" type="button" id="right">
						下一页<span class="glyphicon glyphicon-chevron-right"></span>
					</button>
				</span>
			</div>
		</div>
		<div class="col-lg-4">
			<div style="margin-top: 6px; font-size: 18px;">
				共<span id="totalPages">${totalPages}</span>页
			</div>
		</div>
		<div class="col-lg-4">
			<div class="input-group">
				<input type="text" class="form-control"  id="Go"/> <span
					class="input-group-btn">
					<button class="btn btn-default" type="button">Go!</button>
				</span>
			</div>
		</div>

	</div>

</body>
<script type="text/javascript">

	// keep it simple and stupid !
	$(document).ready(function() {
		// 左钮 
		$("#left").click(function() {
			var currentPage = $("#currentPage").val();
			// 检查是否可以跳转 
			if (currentPage != 1) {
				currentPage = currentPage - 1;    // 向左 
				$("#currentPage").val(currentPage);
				$.ajax({
					url : "orderList",
					type : "POST",
					data:{
						id:$("#userId").val(),
						status:$("#status").val(),
						flag:"true",
						page:currentPage,
						isAJAX:"true"
					}
				}).done(function(data, textStatus, jqXHR) {
					fill(data);
				});
			}
		});
		
		// 跳转钮 
		$("#left").click(function() {
			var currentPage = $("#currentPage").val();
			// 检查是否可以跳转 
			if (currentPage != $("#totalPages").text()) {
				currentPage = currentPage - 1 + 2;    
				$("#currentPage").val(currentPage);
				$.ajax({
					url : "orderList",
					type : "POST",
					data:{
						id:$("#userId").val(),
						status:$("#status").val(),
						flag:"true",
						page:currentPage,
						isAJAX:"true"
					}
				}).done(function(data, textStatus, jqXHR) {
					fill(data);
				});
			}
		});
		
		// 右钮 
		$("#left").click(function() {
			var Go = $("#Go").val();
			// 检查是否可以跳转 
			if (Go>= 1 && Go<= $("#totalPages").text()) {
				$("#currentPage").val(Go);
				$.ajax({
					url : "orderList",
					type : "POST",
					data:{
						id:$("#userId").val(),
						status:$("#status").val(),
						flag:"true",
						page:Go,
						isAJAX:"true"
					}
				}).done(function(data, textStatus, jqXHR) {
					fill(data);
				});
			}
		});
	});

	
	function fill(data) {
		var orderList = $.parseJSON(data);
		$("#orderListDIV").empty();
		for (var i = 0; i < orderList.length; i++) {
			var panel = $("<div class=\"panel panel-default\" style=\"margin-top : 20px;\"></div>");

			var panel_heading = $("<div class=\"panel-heading\"></div>");
			// 复选框 
			panel_heading
					.append($("<div class=\"checkbox-inline\" style=\"margin: auto auto 15px auto;\">"
							+ "<label><input type=\"checkbox\"></label></div>"));
			// 订单生成时间 
			panel_heading
					.append($("<span>" + orderList[i].addTime_ + "</span>"));
			// 商家名称 
			panel_heading.append($("<span style=\"margin-left: 40px\">"
					+ "<strong>商家名称 : </strong>" + orderList[i].merchantName
					+ "</span>"));
			// 总金额 
			panel_heading
					.append($("<span style=\"margin-left: 40px\">"
							+ "<strong>总金额 : </strong>" + orderList[i].sum
							+ "</span>"));
			// X钮 
			panel_heading
					.append($("<button type=\"button\" class=\"close\">"
							+ "<span aria-hidden=\"true\">&times;</span> <span class=\"sr-only\">Close</span>"
							+ "</button>"));
			panel.append(panel_heading); // panel_heading结束 

			var table = $("<table class=\"table table-hover\"></table>");
			for (var j = 0; j < orderList[i].foodList.length; j++) {
				var str = "<tr>" + "	<td width=\"25%\">缩略图</td>"
						+ "	<td width=\"50%\">"
						+ orderList[i].foodList[j].foodName + "</td>"
						+ "	<td width=\"17%\">" + orderList[i].foodList[j].num
						+ "</td>" + "	<td>" + orderList[i].foodList[j].foodSum
						+ "</td>" + "</tr>";
				table.append($(str));
			}
			panel.append($("<div class=\"panel-body\"></div>").append(table)); // panel_body结束

			$("#orderListDIV").append(panel);
		}
	}
</script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.jojo.model.*, java.util.*, com.jojo.util.*" %>		
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	
	<div class="panel panel-default">
		<div class="panel-body">
			<div class="page-header"><!-- 页头 -->
			  <h2>自取订单</h2>
			</div>

			<%
				List<Order> undeliver = (List<Order>) request.getAttribute("undeliver");
				HashMap<String, Integer> map = new HashMap<String, Integer>();
				for (Order o : undeliver) {
			%>
		
			<div class="panel panel-default"> <!-- 订单的详细信息 -->
			  <div class="panel-heading">
			    	<span><%=DateUtil.formatDate(o.getAddTime(), "yyyy-MM-dd") %></span>
					<span style="margin-left: 40px;">
						<strong>学号 : </strong><%=o.getUserIdCard() %>
					</span> 
					<span style="margin-left: 40px;">
						<strong>总金额 : </strong><%=o.getSum() %>
					</span>
					<span style="margin-left: 40px;">
						<strong>联系电话 : </strong><%=o.getUserTel() %>
					</span>
					<button type="button" class="close">
						<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
					</button>
			  </div>
			  <div class="panel-body">
			  <%
			  	for(OrderFood f : o.getFoodList()){
			  		if(map.get(f.getFoodName()) == null){
			  			map.put(f.getFoodName(), 1);
			  		}else{
			  			map.put(f.getFoodName(), map.get(f.getFoodName()) + 1);
			  		}
			  		%>
			  		<div class="row clearfix">
				    	<div class="col-md-3 column">缩略图</div>
				    	<div class="col-md-3 column"><%=f.getFoodName() %></div>
				    	<div class="col-md-2 column"><%=f.getNum() %></div>
				    	<div class="col-md-2 column"><%=f.getFoodSum() %></div>
				    	<div class="col-md-2 column">
				    		<button type="button" class="btn btn-primary btn-sm" id="<%=o.getOrderId() %>"
									onclick="takeOrder()">确认并发货</button>
				    	</div>
				    </div>
			  		<%
			  	}
			  %>
			  </div>
			</div>
	<%
		}
	%>
			<!-- 统计信息 -->
			<span><Strong>累计：</Strong>
			<%
				for(String key : map.keySet()){
					%>
					<a href="#"><%=key %>
						<span class="badge"><%=map.get(key) %></span>
					</a>&nbsp;&nbsp;
					<%
				}
			%>
			</span>
		</div>
	</div>


<div class="panel panel-default" style="margin-top: 20px;">
		<div class="panel-body">
			<div class="page-header"><!-- 页头 -->
			  <h2>外送订单</h2>
			</div>

			<%
				List<Order> deliver = (List<Order>) request.getAttribute("deliver");
				HashMap<String, Integer> map2 = new HashMap<String, Integer>();
				for (Order o : deliver) {
			%>
		
			<div class="panel panel-default"> <!-- 订单的详细信息 -->
			  <div class="panel-heading">
			    	<span><%=DateUtil.formatDate(o.getAddTime(), "yyyy-MM-dd") %></span>
					<span style="margin-left: 40px;">
						<strong>学号 : </strong><%=o.getUserIdCard() %>
					</span> 
					<span style="margin-left: 40px;">
						<strong>总金额 : </strong><%=o.getSum() %>
					</span>
					<span style="margin-left: 40px;">
						<strong>联系电话 : </strong><%=o.getUserTel() %>
					</span>
					<span style="margin-left: 40px;">
						<strong>外送地址 : </strong><%=o.getAddress() %>
					</span>
					<button type="button" class="close">
						<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
					</button>
			  </div>
			  <div class="panel-body">
			  <%
			  	for(OrderFood f : o.getFoodList()){
			  		if(map2.get(f.getFoodName()) == null){
			  			map2.put(f.getFoodName(), 1);
			  		}else{
			  			map2.put(f.getFoodName(), map2.get(f.getFoodName()) + 1);
			  		}
			  		%>
			  		<div class="row clearfix">
				    	<div class="col-md-3 column">缩略图</div>
				    	<div class="col-md-3 column"><%=f.getFoodName() %></div>
				    	<div class="col-md-2 column"><%=f.getNum() %></div>
				    	<div class="col-md-2 column"><%=f.getFoodSum() %></div>
				    	<div class="col-md-2 column">
				    		<button type="button" class="btn btn-primary btn-sm" id="<%=o.getOrderId() %>"
									onclick="takeOrder()">确认并发货</button>
				    	</div>
				    </div>
			  		<%
			  	}
			  %>
			  </div>
			</div>
	<%
		}
	%>
			<!-- 统计信息 -->
			<span><Strong>累计：</Strong>
			<%
				for(String key : map2.keySet()){
					%>
					<a href="#"><%=key %>
						<span class="badge"><%=map2.get(key) %></span>
					</a>&nbsp;&nbsp;
					<%
				}
			%>
			</span>
		</div>
	</div>




	<div class="row clearfix" style="margin-top: 20px;">
		<div class="col-lg-4">
			<div class="input-group">
				<span class="input-group-btn">
					<button class="btn btn-default" type="button" id="left">
						<span class="glyphicon glyphicon-chevron-left"></span>上一页
					</button>
				</span> 
				<input type="text" class="form-control" value="1"  id="currentPage" />
				 <span class="input-group-btn">
					<button class="btn btn-default" type="button" id="right">下一页
						<span class="glyphicon glyphicon-chevron-right"></span>
					</button>
				</span>
			</div>
		</div>
		<div class="col-lg-4">
			<div style="margin-top: 6px; font-size: 18px;">
				共<span>${totalPages}</span>页
			</div>
		</div>
		<div class="col-lg-4">
			<div class="input-group">
				<input type="text" class="form-control" value="" /> <span
					class="input-group-btn">
					<button class="btn btn-default" type="button" id="Go">Go!</button>
				</span>
			</div>
		</div>
	</div>
	
	<form id="nextPage" style="display:none;"action="orderList" method="post">
		<input type="hidden" id="id" name="id" value="${currentMerchant.merchantId}" />
		<input type="hidden" id="status" name="status" value="0" />
		<input type="hidden" id="flag" name="flag" value="false" />
		<input type="hidden" id="page" name="page" value="" />
	</form>
</body>
<script type="text/javascript">
function takeOrder(){
	var srcElement = $(window.event.srcElement);
	var order = srcElement.parent().parent().parent().parent();
	$.ajax({
		url: "alterOrder",
		type: "POST",
		data: {
			action: "updateStatus",
			orderId: srcElement.attr("id"),
			status: 1
		}
	}).done(function(data, textStatus, jqXHR){
		order.css("display", "none");
	});
}

function deleteOrder(){
	var srcElement = $(window.event.srcElement);
	var order = srcElement.parent().parent();
	$.ajax({
		url: "alterOrder",
		type: "POST",
		data: {
			action: "updateStatus",
			orderId: srcElement.attr("id"),
			status: 1
		}
	}).done(function(data, textStatus, jqXHR){
		order.css("display", "none");
	});
}

$(document).ready(function() {				// keep it simple and stupid !
	// 左钮 
	$("#left").click(function() {
		var currentPage = $("#currentPage").val();
		// 检查是否可以跳转 
		if (currentPage > 1) {
			currentPage = currentPage - 1;    					// 向左 
			$("#currentPage").val(currentPage);				// 修改当前页码
			$("#page").val(currentPage);								// 给隐藏表单赋值
			$("#nextPage").submit();									// 表单提交
		}
	});
	
	// 跳转钮 
	$("#right").click(function() {
		var currentPage = $("#currentPage").val();
		if (currentPage < $("#totalPages").text()) {		// 向右 
			currentPage = currentPage - 1 + 2;    				// 修改当前页码
			$("#currentPage").val(currentPage);				
			$("#page").val(currentPage);								// 给隐藏表单赋值
			$("#nextPage").submit();									// 表单提交
		}
	});
	
	// 右钮 
	$("#Go").click(function() {
		var Go = $("#Go").val();
		// 检查是否可以跳转 
		if (Go>= 1 && Go<= $("#totalPages").text()) {
			$("#currentPage").val(Go);
			$("#page").val(currentPage);								// 给隐藏表单赋值
			$("#nextPage").submit();									// 表单提交
		}
	});
});
</script>
</html>
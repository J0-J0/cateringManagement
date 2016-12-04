<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

<title>商家主页</title>

<%
	if(session.getAttribute("currentMerchant") == null){
		response.sendRedirect(request.getContextPath() +"/merchantLogin.jsp");
	}
%>

</head>
<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-3 column">

				<div>
					<a data-toggle="modal" href="#updateMerchant"
						class="btn btn-warning" role="button"
						style="margin: 10px auto auto auto; width: 100%;">
						<h5>Hi ! &nbsp;&nbsp;${currentMerchant.merchantName}</h5>
					</a> 
					<input id="merchantId" type="text"
						value="${currentMerchant.merchantId}" style="display: none;" />
				</div>
				
				
				<div style="margin: 20px auto auto auto;">
					<ul class="list-group">
						<li class="list-group-item"><span class="label label-success">累计收入</span>&nbsp;&nbsp;
							${currentMerchant.sum}</li>
						<li class="list-group-item"><span class="label label-primary">真实姓名</span>&nbsp;&nbsp;
							${currentMerchant.merchantRealName}</li>
						<li class="list-group-item"><span class="label label-primary">身份证号</span>&nbsp;&nbsp;
							${currentMerchant.merchantIdCard}</li>
						<li class="list-group-item"><span class="label label-primary">联系方式</span>&nbsp;&nbsp;
							${currentMerchant.merchantTel}</li>
					</ul>
				</div>


				<div class="page-header">
					<h3>订单处理</h3>
				</div>
				<div>
					<ul class="nav nav-pills nav-stacked" role="tablist">
						<li role="presentation"><a
							href="${pageContext.request.contextPath }/orderList?id=${currentMerchant.merchantId}&status=0&flag=flase&page=1"
							class="btn btn-default"> 待处理<span class="badge" id="pending"></span></a></li>
						<li role="presentation"><a
							href="${pageContext.request.contextPath }/orderList?id=${currentMerchant.merchantId}&status=1&flag=flase&page=1"
							class="btn btn-default"> 等待收货<span class="badge" id="waiting">42</span></a></li>
						<li role="presentation"><a
							href="${pageContext.request.contextPath }/orderList?id=${currentMerchant.merchantId}&status=2&flag=flase&page=1"
							class="btn btn-default"> 已完成</a></li>
					</ul>
				</div>


				<div class="page-header">
					<h3>食品管理</h3>
				</div>
				<div>
					<ul class="nav nav-pills nav-stacked" role="tablist">
						<li role="presentation"><a
							href="${pageContext.request.contextPath }/alterFood?action=selectList&merchantId=${currentMerchant.merchantId}"
							class="btn btn-default"> 已发布<span class="badge" id="published"></span></a></li>
						<li role="presentation"><a
							href="${pageContext.request.contextPath }/alterFood?action=showAdd"
							class="btn btn-default"> 添加食品<span class="badge"></span></a></li>
					</ul>
				</div>

			</div>


			<div class="col-md-9 column">

				<div style="height: 150px;">这里贴张图</div>

				<div class="row clearfix">
					<div class="col-md-12 column">
						<%if(request.getAttribute("xxxjsp") != null){ %>
							<div class="row clearfix">
								<div class="col-md-1 column"></div>
								<div class="col-md-10 column">
									<jsp:include page="${xxxjsp }"></jsp:include>
								</div>
							</div>
						<% }else{%>
						<div class="jumbotron">
							<h1>点击左侧按钮，开始您一天的美好工作吧~</h1>
							<h2>\(^o^)/YES!</h2>
						</div>
						<%} %>
					</div>
				</div>

			</div>
			<!-- row结束，哇，这一行好牛逼啊 -->
		</div>
		<!-- container结束 -->
	</div>



	<!-- 修改商家个人信息模态框，本人很爱模态框 -->
	<div class="modal fade" id="updateMerchant" tabindex="-1" role="dialog"
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

					<form class="form-horizontal" role="form" id="updateMerchantForm"
						method="post"
						action="${pageContext.request.contextPath }/merchantInfo">

						<input type="text" id="action" name="action" value="update"
							style="display: none;" />

						<div class="form-group">
							<label for="merchantName" class="col-sm-2 control-label">用户名</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="merchantName"
									id="merchantName" value="${currentMerchant.merchantName}">
							</div>
						</div>

						<div class="form-group">
							<label for="password1" class="col-sm-2 control-label">密码</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" name="password1"
									id="password1" placeholder="这里输密码">
							</div>
						</div>

						<div class="form-group">
							<label for="password2" class="col-sm-2 control-label">确认密码</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" name="password2"
									id="password2" placeholder="这里输密码">
							</div>
						</div>

						<div class="form-group">
							<label for="merchantIdCard" class="col-sm-2 control-label">身份证号</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="merchantIdCard"
									id="merchantIdCard" value="${currentMerchant.merchantIdCard}">
							</div>
						</div>

						<div class="form-group">
							<label for="merchantRealName" class="col-sm-2 control-label">真实姓名</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="merchantRealName"
									id="merchantRealName"
									value="${currentMerchant.merchantRealName}">
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label">性别</label>&nbsp;&nbsp;&nbsp;
							<label class="radio-inline"> <input type="radio"
								name="sex" id="sex" value="男"> 男
							</label> <label class="radio-inline"> <input type="radio"
								name="sex" id="sex" value="option3"> 女
							</label>
						</div>

						<div class="form-group">
							<label for="age" class="col-sm-2 control-label">年龄</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="age" id="age"
									value="${currentMerchant.age}">
							</div>
						</div>

						<div class="form-group">
							<label for="merchantTel" class="col-sm-2 control-label">联系电话</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="merchantTel"
									id="merchantTel" value="${currentMerchant.merchantTel}">
							</div>
						</div>

						<div class="form-group">
							<label for="description" class="col-sm-2 control-label">一句话描述</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="description"
									id="description" value="${currentMerchant.description}">
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
	$(document).ready(function(){
		$("button.btn-block").click(function(){
			$("#updateMerchantForm").submit();
		});
		
		
	$("#pending").load("alterOrder", {
			action : "selectOrderNum",
			merchantId : $("#merchantId").val(),
			status : 0
		});
	$("#waiting").load("alterOrder", {
			action : "selectOrderNum",
			merchantId : $("#merchantId").val(),
			status : 1
		});
	$("#published").load("alterFood", {
			action : "selectFoodNum",
			merchantId : $("#merchantId").val(),
		});
	});
</script>
</html>
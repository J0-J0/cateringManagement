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

</head>
<body>
	<div class="panel panel-default"
		style="width: 80%; margin-left: 100px;">
		<div class="panel-body">
			<div class="page-header">
				<!-- 页头 -->
				<h2>基础信息</h2>
			</div>

			<form class="form-horizontal" role="form" method="post"
				action="${pageContext.request.contextPath }/alterFood">

				<input id="action" name="action" value="update" type="hidden" /> 
				<input id="foodId" name="foodId" value="${food.foodId }" type="hidden" />

				<div class="form-group">
					<label for="foodName" class="col-sm-2 control-label">食物名称</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="foodName"
							name="foodName" value="${food.foodName }">
					</div>
				</div>

				<div class="form-group">
					<label for="foodPrice" class="col-sm-2 control-label">食物价格</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="foodPrice"
							name="foodPrice" value="${food.foodPrice }">
					</div>
				</div>

				<div class="form-group">
					<label for="foodType" class="col-sm-2 control-label">食物类别</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="foodType"
							name="foodType" value="${food.foodType }">
					</div>
				</div>

				<div class="form-group">
					<label for="description" class="col-sm-2 control-label">一句话描述</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="description"
							name="description" value="${food.description }">
					</div>
				</div>

				<div class="form-group">
					<label for="num" class="col-sm-2 control-label">数量</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="num" name="num"
							value="${food.num }">
					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-12">
						<button type="submit" class="btn btn-danger btn-lg btn-block">提交</button>
					</div>
				</div>
			</form>

		</div>
	</div>

	<div class="panel panel-default"
		style="width: 80%; margin-left: 100px;">
		<div class="panel-body">
			<div class="page-header">
				<!-- 页头 -->
				<h2>上传图片</h2>
			</div>
			<form action="${pageContext.request.contextPath}/image" method="post"
				enctype="multipart/form-data">
				<input id="foodId" name="foodId" value="${food.foodId }" type="hidden" />
				<div class="form-group">
					<label for="foodPic" class="col-sm-2 control-label">上传图片</label>
					<div class="col-sm-10">
						<input type="file" id="foodPic" name="foodPic">
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
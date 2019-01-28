<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Wait</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/common-frame.css">
<link rel="icon" href="img/favicon.ico">
</head>
<body>
	<!-- 头部 -->
	<div class="container headBg">
		<div class="headContent">Network-based Essential Protein Discovery</div>
	</div>

	<!-- 内容 -->
	<div class="container main">
		<div class="mainContent">
			<div style="text-align: right;"><a href="index.jsp">Home <span class="glyphicon glyphicon-home" aria-hidden="true"></span></a></div>
			
			<div class="alert alert-warning" style="width: 100%;">
					NOTICE: Your data has been submitted successfully! 
					It may take a long time if you uploaded many nodes. 
					Please waiting for the result...
			</div>
			<div style="text-align: center;">
			
				<img alt="Waiting..." src="img/loading.gif" class="img-thumbnail" width="280px">
				
				<div style="margin-top:10px;">
					<!-- ${infoId}&nbsp;&nbsp; -->
				</div>
			</div>
		</div>
	</div>

	<!-- 页脚部分 -->
	<footer class="footer">
		<div class="container copyright">
			Copyright © 2016 <strong>CSU-Bioinformatics Group</strong>.All rights reserved.
		</div>
	</footer>
	
	<script src="js/jquery-1.12.0.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			
			//等待一段时间自动链接到下一个页面
			setTimeout(function(){window.location.href = "evaluationalgo"}, 1000);
			
		});
	</script>
</body>
</html>
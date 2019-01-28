<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="renderer" content="webkit" />
<title>Visualization</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/common-frame.css">
<link rel="stylesheet" href="css/networkinfo.css">
<link rel="icon" href="img/favicon.ico">
<style>
#divBtn button {
    margin-top : 10px;
    width: 160px;
    height : 36px;
}
</style>
</head>
<body>
	<!-- 头部 -->
	<%@ include file="header.jsp" %>

	<!-- 内容 -->
	<div class="container main">
		<div class="mainContent">
			<div style="text-align: right;"><a href="index.jsp">Home <span class="glyphicon glyphicon-home" aria-hidden="true"></span></a></div>
			
			<div style="width:1000px;margin-top:10px;">
				<div class="center-block panel panel-info" style="width:100%;">
					<div class="panel-heading">
						<h3 class="panel-title">NetworkVisualization</h3>
					</div>
					<div class="panel-body">
						<div class="alert alert-warning">
							NOTICE: Enter or upload a list of identifiers which is a
							tab-delimited or space-delimited string for each row, for example:<br>
							<div style="margin-left: 60px;">
								P35202&#09;P14164<br>P35202&#09;Q04174<br>
							</div>
						</div>
						<form method="post" action="displaynetwork" enctype="multipart/form-data">
							<div style="width: 600px; margin: 0 auto;">
								<label>Input Data</label><br>
								<textarea id="textData" name="inputData"
									class="form-control form-area" rows="8"></textarea>
								<div>
									<div style="float: left">
										<label><strong>OR</strong> upload your own file:</label> 
										<input id="select-file" type="file" name="file" onchange="fileChange();">
										<div id="divFileupload">
											<input type="button" class="btn btn-info" id="btn-upload" onclick="$('#select-file').click();" value="select file"> 
											<input id="filename" name="fileName" class="input-large" type="text"  placeholder="the filename is..." readonly>
										</div>
									</div>
									<div style="float: right;">
										<input class="btn btn-success btn-submit" type="submit" id="sub-net" value="Visual">
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
				
			</div>

		</div>
	</div>

	<!-- 页脚部分 -->
	<%@ include file="footer.jsp" %>
	
	<script src="js/jquery-1.12.0.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script type="text/javascript">

		//根据用户上传文件名改变文本内容
		function fileChange() {
			var path = $("#select-file").val();
			var pos1 = path.lastIndexOf('/');
			var pos2 = path.lastIndexOf('\\');
			var pos = Math.max(pos1,pos2);
			if(pos<0)
				$("#filename").val(path);
			else
				$("#filename").val(path.substring(pos+1));
			//设定文本区域样式
			if($("#filename").val() != ""){
				$("#textData").val("");
				$("#textData").css("border-color","");
			}
		}
		$(document).ready(function(){
			$("#sub-net").click(function(){
				if($("#filename").val() == "" && $("#textData").val() == ""){
					$("#textData").focus();
					$("#textData").css("border-color","red");
					//恢复正常颜色
					//$("#textData").css("border-color","");
					return false;
				}
			});
			$("#textData").bind("input propertychange",function(){
				
				if($("#filename").val() != ""){
					var fileBox = $("#select-file");
					alert("456456");
					fileBox.after(fileBox.clone().val(""));
					fileBox.remove();
					
					$("#filename").val("");
				}
				$("#textData").css("border-color","");
			});
		});
	</script>
</body>
</html>
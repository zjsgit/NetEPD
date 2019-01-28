<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="renderer" content="webkit" />
<title>Valuation</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/common-frame.css">
<link rel="stylesheet" href="css/evaluation.css">
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
						<h3 class="panel-title">Evaluation</h3>
					</div>
					<div class="panel-body">
						<div class="alert alert-warning">
							NOTICE&#09;1: Upload a list of identifiers which are predicted for each row, for example:<br>
							<div style="margin-left: 60px;">
								P35202<br />P14164<br />P35202<br />Q04174<br />
							</div>
							<br />
							NOTICE&#09;2: Upload a list of essential nodes which are known for each row, for example:<br>
							<div style="margin-left: 60px;">
								P35202<br />P14164<br />P35202<br />Q04174<br />
							</div>
						</div>
						<form method="post" enctype="multipart/form-data" >
							<div style="width: 600px; margin: 0 auto;">
								<div>
									<div style="float: left">
										<label>upload your own predicted file:</label> 
										<input id="predicted-file" type="file" name="files" accept="application/vnd.ms-excel" onchange="predictedFileChange();">
										<div id="divFileupload">
											<input type="button" class="btn btn-info" id="btn-upload" onclick="$('#predicted-file').click();" value="select file"> 
											<input id="predictedFileName" name="predictedFileName" class="input-large" type="text"  placeholder="the filename is..." readonly>
										</div>
									</div>
									<div style="float: left">
										<label>upload your own known file:</label> 
										<input id="known-file" type="file" name="files" accept="text/plain" onchange="knownFileChange();">
										<div id="divFileupload">
											<input type="button" class="btn btn-info" id="btn-upload" onclick="$('#known-file').click();" value="select file"> 
											<input id="knownFileName" name="knownFileName" class="input-large" type="text"  placeholder="the filename is..." readonly>
										</div>
									</div>
									
									<div style="float: right;">
										<input class="btn btn-success btn-submit" id="sub-net" value="Evaluate">
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
	<script src="js/ajaxfilesupload.js"></script>
	
	<script type="text/javascript">

		//根据用户上传文件名改变文本内容
		function predictedFileChange() {
			var path = $("#predicted-file").val();
			var pos1 = path.lastIndexOf('/');
			var pos2 = path.lastIndexOf('\\');
			var pos = Math.max(pos1,pos2);
			if(pos<0)
				$("#predictedFileName").val(path);
			else
				$("#predictedFileName").val(path.substring(pos+1));
			//设定文本区域样式
			if($("#predictedFileName").val() != ""){
				$("#predictedFileName").css("border-color","");
			}
		}
		
		//根据用户上传文件名改变文本内容
		function knownFileChange() {
			var path = $("#known-file").val();
			var pos1 = path.lastIndexOf('/');
			var pos2 = path.lastIndexOf('\\');
			var pos = Math.max(pos1,pos2);
			if(pos<0)
				$("#knownFileName").val(path);
			else
				$("#knownFileName").val(path.substring(pos+1));
			//设定文本区域样式
			if($("#knownFileName").val() != ""){
				$("#knownFileName").css("border-color","");
			}
		}
		
		$(document).ready(function(){
			$("#sub-net").click(function(){
				if($("#predictedFileName").val() == "" ){
					$("#predictedFileName").focus();
					$("#predictedFileName").css("border-color","red");
					//恢复正常颜色
					//$("#textData").css("border-color","");
					return false;
				}
				
				if($("#knownFileName").val() == "" ){
					$("#knownFileName").focus();
					$("#knownFileName").css("border-color","red");
					//恢复正常颜色
					//$("#textData").css("border-color","");
					return false;
				}
				
				//alert(JSON.stringify(forminfo));
				$.ajaxFileUpload({
					type:"POST",
	        		url : "evaluation",
	        		//data:forminfo,
	        		secureuri : false,
	        		fileElementId : ['predicted-file','known-file'],
	        		dataType : "json",
	        		success : function(data) {
	        			if(data.status == "OK"){
							location.href = "evaluationresult.jsp";
						}else if(data.status=="OVER"){
							location.href = "oversize.html";
						}
	        		},
	        		error : function(data) {
	        			
	        			alert("this is s a error!!");
	        		}
	    		});
			
			});
		});
	</script>
</body>
</html>
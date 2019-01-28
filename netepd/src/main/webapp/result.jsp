<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Result</title>
<!-- External CSS -->
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/bootstrap-table.min.css">
<link rel="stylesheet" href="css/common-frame.css">
<link rel="stylesheet" href="css/result.css">
<link rel="icon" href="img/favicon.ico">

</head>
<body data-spy="scroll" data-target="#myScrollspy">
	<!-- 标题部分 -->
	<div class="container headBg">
		<div class="headContent">Network-based Essential Protein Discovery</div>
	</div>
	
	<!-- 内容 -->
	<div class="container main">
		<div class="row mainContent">
			<!-- 附加导航 -->
			<div style="width:200px;float:left;margin-top:30px;" id="myScrollspy">
				<ul class="nav nav-tabs nav-stacked" data-spy="affix" data-offset-top="190" id="myNav">
					<li id="navTitle"><a href="#"><span class="glyphicon glyphicon-menu-hamburger"></span> <strong>Display</strong></a></li>
					<li><a href="#algorithms"><span class="glyphicon glyphicon-hand-right"></span> Algorithms</a></li>
					<li><a href="#evaluation"><span class="glyphicon glyphicon-hand-right"></span> Evaluation</a></li>
					<li><a href="visualization" target="_blank"><span class="glyphicon glyphicon-hand-down"></span> Network</a></li>
				</ul>
			</div>
			<!-- 结果展示 -->
			<div style="width:800px;float:right;">
				<div style="text-align: right;"><a href="index.jsp">Home <span class="glyphicon glyphicon-home" aria-hidden="true"></span></a></div>
				
				<!-- 展示预测算法计算的结果并提供下载 -->
				<div id="algorithms" class="center-block panel panel-info" style="width: 800px;margin: 10px auto;">
					<div class="panel-heading">
						<h3 class="panel-title">Algorithms</h3>
					</div>
					<div class="panel-body">
						<div style="width:600px;">
						<select id="selAlgorithm" class="form-control"></select>
						<a id="btDownload" class="btn btn-info" href="upload/${infoId}/result.zip">Download All</a>
					</div>
					
					<table id="showResult" data-pagination="true" data-show-columns="true" 
						data-search="true" data-striped="true" data-toggle="table" data-search-on-enter-key="true">
						<thead>
							<tr>
								<th data-field="ranking" data-sortable="true">Ranking</th>
								<th data-field="name" data-sortable="true">Name</th>
								<th data-field="param" data-sortable="true">Parameter</th>
							</tr>
						</thead>
					</table>
					</div>
				</div>
				
				<!-- 提交预测算法评估的数据 -->
				<div id="evaluation" class="center-block panel panel-info" style="width:800px;">
					<div class="panel-heading">
						<h3 class="panel-title">Evaluation</h3>
					</div>
					<div class="panel-body">
						<div class="alert alert-warning" style="width:100%;">
							NOTICE: By default, using the essential protein data set stored in the database. 
							Or you can upload your own essential protein data.
						</div>
						<form method="post" enctype="multipart/form-data">
							<!--  
							<select id="organism" class="form-control">
								<option value="yeast">Yeast</option>
							</select>
							-->
							<input id="selectFile" type="file" name="file" style="display:none">
							<div>
								<input type="text" id="filename" name="filename" placeholder="the filename is..." readonly>
								<input type="button" style="width:100px;line-height: 10px;" class="btn btn-info btn-lg" id="btnSelect" onclick="$('input[id=selectFile]').click();" value="select file">
							</div>
							<div style="margin-top:10px;">
								<input style="float:left;height:46px;width: 230px;" type="text" id="topNum" class="form-control" placeholder="input the top number...">
								<input type="button" style="width:132px;margin-left:4px;" class="btn btn-success btn-lg" id="btnUpload" value="evaluate">
							</div>
						</form>
						
						<table id="assess" data-pagination="true" data-show-columns="true" 
							data-search="true" data-striped="true" data-toggle="table" data-search-on-enter-key="true">
							<thead>
								<tr>
									<th data-field="name">Name</th>
									<th data-field="sn" data-sortable="true">SN</th>
									<th data-field="sp" data-sortable="true">SP</th>
									<th data-field="npv" data-sortable="true">NPV</th>
									<th data-field="ppv" data-sortable="true">PPV</th>
									<th data-field="acc" data-sortable="true">ACC</th>
									<th data-field="f" data-sortable="true">F</th>
								</tr>
							</thead>
						</table>
				
						<!-- 折线图展示 
						<img id="aucGrapg" class="img-thumbnail center-block" src="" alt="AUC Graph"/>
						-->
						<!-- ECharts应用 -->
						<div id="myEchart" class="center-block" style="height:480px;border:1px solid #ddd;padding: 10px 10px;"></div>
					</div>
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
	<script src="js/bootstrap-table.min.js"></script>
	<script src="js/ajaxfileupload.js"></script>
	<script src="js/echarts.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			
			//页面加载时从后台获取下拉框值
			$.ajax({
				type: "GET",
				url: "getuseralgos",
				data: {infoId:"${infoId}"},
				dataType: "json",
				success: function(data){
					var userAlgos = data.userAlgos;
					//alert(userAlgos);
					var selAlgo = document.getElementById("selAlgorithm");
					for(var i=0;i<userAlgos.length;i++){
						selAlgo.options.add(new Option(userAlgos[i],userAlgos[i]));
					}
					selAlgo.options[0].selected = true;
					result();
				},
				error: function(res){
					alert("error!");
				}
			});
			
			//加载关键蛋白预测算法计算的结果
			$("#selAlgorithm").change(function(){
				result();
			});
			function result(){
				$("#showResult").bootstrapTable("refresh",{
					url: "showAlgoRes",
					method: "get",
					query: {infoId:"${infoId}","selectAlgo":$("#selAlgorithm").val()}
				});
			}
			
			//动态改变文件路径标签域
			$("input[id=selectFile]").change(function(){
				var path = $("#selectFile").val();
				var pos1 = path.lastIndexOf("/");
				var pos2 = path.lastIndexOf("\\");
				var pos = Math.max(pos1,pos2);
				if(pos<0)
					$("#filename").val(path);
				else
					$("#filename").val(path.substring(pos+1));
			});
			
			//提交关键蛋白数据和参数信息
			$("#btnUpload").click(function(){
				//表单数据验证
				if($("#topNum").val() == ""){
					$("#topNum").focus();
					$("#topNum").css("border-color","red");
					return false;
				}
				$("#topNum").css("border-color","");
				var evainfo = {
					infoId : "${infoId}",
					//organism : $("#organism").val(),
					topNum : $("#topNum").val()
				};
				//alert(evainfo.infoId+"\n"+evainfo.organism+"\n"+evainfo.topNum);
				$.ajaxFileUpload({
					type : "POST",
					url : "evainfo",
					data : evainfo,
					secureuri : false,
					fileElementId : "selectFile",
					dataType : "json",
					success : function(data){
						
						if (data.status == "success") {
							//加载表格数据
							$("#assess").bootstrapTable("load",{data:data.tableDatas});
							//加载折线图
							loadMyCharts(data.chartDatas);
							
						} else if (data.status == "error") {
							alert("评估过程出错！")
						}
						
					},
					error : function(data){
						alert("error");
					}
				});
			});
			function loadMyCharts(chartDatas){
					var chartTitle = "AUC of TOP"+$("#topNum").val();
					//基于准备好的dom，初始化echarts图表
					var myChart = echarts.init(document.getElementById("myEchart"));
					//显示空的坐标
					myChart.setOption({
						//设定背景色
						title : {
							text : chartTitle
						},
						tooltip : {
							trigger : 'axis'
						},
						legend : {
							data : chartDatas.chartName,
							left : 'right',
							orient : 'vertical'
						},
						toolbox : {
							show : false,
							feature : {
								saveAsImage : {
									show : true,
									title : "Save As Image"
								}
							}
						},
						xAxis : {
							type : 'category',
							boundaryGap : false,
							name : "Top Number",
							nameLocation : 'middle',
							nameGap : 30,
							nameTextStyle:{
								fontWeight : 'bold'
							},
							splitLine : {
								show : true
							},
							data : chartDatas.xdata
						},
						yAxis : {
							type : 'value',
							name : 'The Number of Essential Protein',
							nameLocation : 'middle',
							nameGap : 48,
							nameTextStyle:{
								fontWeight : 'bold'
							},
							splitLine : {
								show : true
							}
						},
						series : chartDatas.ydatas
					});
			}
		});
	</script>
</body>
</html>
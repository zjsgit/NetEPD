<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Success</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<style type="text/css">
	/* 页脚部分 */
	html{
		position:relative;
		min-height:100%;
	}
	body{
		margin-bottom:60px;
		min-width: 1200px;
	}
	.footer{
		position:absolute;
		bottom:0;
		width:100%;
		height:50px;
  		background-color:#f5f5f5;
		padding: 15px 0;
	}
	.copyright {
		width: 1000px;
		font-size: 12px;
		color: #999;
		text-align: center;
		margin: 0 auto;
	}
	/* 主题部分 */
	.main{
		width: 100%;
		heigth: auto;
		margin-to: 20px;
	}
	.mainContent{
		margin: 0 auto;
		width:1000px;
		min-width: 1000px;
/* 		height:1000px; */
	}
	/*附加导航*/
    /* Custom Styles */
    ul.nav-tabs{
        width: 160px;
/*         margin-top: 10px; */
        border-radius: 4px;
        border: 1px solid #e9e9e9;
        box-shadow: 0 1px 4px rgba(0, 0, 0, 0.067);
    }
    ul.nav-tabs li{
        margin: 0;
        border-top: 1px solid #e9e9e9;
    }
    ul.nav-tabs li:first-child{
        background-color: #e9e9e9;
    }
	ul.nav-tabs li a{
		color:#428bca;
     }
    ul.nav-tabs li.active a, ul.nav-tabs li.active a:hover{
        margin:1px;
        border-radius:2px;
        border: 1px solid #e9e9e9;
        color:#f0ad4e;
    }
    ul.nav-tabs.affix{
        top: 70px; /* Set the top position of pinned element */ 
    }
</style>
</head>
<body data-spy="scroll" data-target="#myScrollspy">
	<div style="text-align:center;">
		<h1>Welcome to this Page!</h1>
	</div>
	<div class="container main">
		<div class="row mainContent">
			<div style="width:200px;float: left;" id="myScrollspy">
				<ul class="nav nav-tabs nav-stacked" data-spy="affix" data-offset-top="69"  id="myNav">
					<li><a href="#"><span class="glyphicon glyphicon-menu-hamburger"></span> <strong>Display</strong></a></li>
					<li><a href="#partOne"><span class="glyphicon glyphicon-hand-right"></span> Network</a></li>
					<li><a href="#partTwo"><span class="glyphicon glyphicon-hand-right"></span> Algorithms</a></li>
					<li><a href="#partThree"><span class="glyphicon glyphicon-hand-right"></span> Evaluation</a></li>
				</ul>
			</div>
			<div style="width: 800px;float: right;">
				<div id="partOne">
					<h4>This is "Success.jsp".</h4>
					<a href="index.jsp">Homepage <span class="glyphicon glyphicon-home"></span></a>
					<p><a href="default.jsp">default.jsp</a></p>
					<p><a href="result.jsp">result.jsp</a></p>
					
				</div>
				<div id="partTwo">
					<h1>partTwo</h1>
					
					<!-- ECharts应用 -->
					<div id="myEchart" style="width:600px;height:400px;"></div>
					
				</div>
				<div id="partThree">
					<h1>partThree</h1>
				</div>
			</div>
		</div>
	</div>
	
	<footer class="footer">
		<div class="container copyright">
			Copyright © 2016 <strong>CSU-Bioinformatics Group</strong>.All rights reserved.
		</div>
	</footer>
	<script src="js/jquery-1.12.0.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/echarts.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			var mydata = {
				algo : [ 'DC', 'LAC', 'CC' ],
				data : [ 5, 20, 40, 10, 24, 20, 24, 32 ],
				cate : [ '1', '2', '3', '4', '5', '6', '7' ]
			};
			var dataset = [ {
				name : mydata.algo[2],
				type : 'line',
				smooth : true,
				data : [ 10, 12, 21, 54, 260, 830, 710 ]
			}, {
				name : mydata.algo[1],
				type : 'line',
				smooth : true,
				data : [ 30, 182, 434, 791, 390, 30, 10 ]
			}, {
				name : mydata.algo[0],
				type : 'line',
				smooth : true,
				data : [ 1320, 1132, 601, 234, 120, 90, 20 ]
			} ];

			//基于准备好的dom，初始化echarts图表
			var myChart = echarts.init(document.getElementById("myEchart"));
			//显示空的坐标
			myChart.setOption({
				//设定背景色
				backgroundColor : "#eee",
				title : {
					text : 'Algorithms'
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					//data:['意向','预购','成交']
					data : mydata.algo
				},
				toolbox : {
					show : true,
					feature : {
						//magicType: {show: true, type: ['stack', 'tiled']},
						saveAsImage : {
							show : true,
							title : "Save As Image"
						}
					}
				},
				xAxis : {
					type : 'category',
					boundaryGap : false,
					splitNumber : 10,
					interval : 0.1,
					data : mydata.cate
				},
				yAxis : {
					type : 'value'
				},
				series : dataset
			});
		});
	</script>
</body>
</html>
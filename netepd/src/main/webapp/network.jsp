<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Network</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/common-frame.css">
<link rel="stylesheet" href="css/network.css">
<link rel="icon" href="img/favicon.ico">
</head>
<body>
	<!-- 头部 -->
	<%@ include file="header.jsp" %>

	<!-- 内容 -->
	<div class="container main">
		<div class="mainContent">
			<!-- 网络图展示 -->
			<div style="width:1000px;margin-top:10px;">
				<div style="width:100%;">
				    <nav id="navbar-example" class="navbar navbar-default navbar-static">
				        <div class="container-fluid">
				            <div class="navbar-header">
				                <a class="navbar-brand" href="#cy">Network Visualization</a>
				            </div>
				            <div class="collapse navbar-collapse">
				                <ul class="nav navbar-nav">
				                    <li class="dropdown">
				                        <a id="drop2" href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
				                            <span class="glyphicon glyphicon-cog" aria-hidden="true"></span> Layout&nbsp;
				                            <span class="caret"></span>
				                        </a>
				                        <ul class="dropdown-menu" aria-labelledby="drop2">
				                        	<li><button type="button" class="btn btn-default btn-block btn-layout" value="circle">Circle(default)</button></li>
				                        	<li><button type="button" class="btn btn-default btn-block btn-layout" value="grid">Grid</button></li>
				                        	<li><button type="button" class="btn btn-default btn-block btn-layout" value="concentric">Concentric</button></li>
				                        	<li><button type="button" class="btn btn-default btn-block btn-layout" value="breadthfirst">Breadthfirst</button></li>
				                        </ul>
				                    </li>
				                    <li class="dropdown">
				                		<button type="button" class="btn btn-default btn-block" id="btn-export">
				                		<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span> Export</button>
				                	</li>
				                </ul>
				                <div class="nav navbar-nav navbar-right" style="padding: 15px 0;">
				                    <a id="link-home" href="index.jsp">Home <span class="glyphicon glyphicon-home" aria-hidden="true"></span></a>
				                </div>
				            </div>
				        </div>
				    </nav>
				</div>
				<div style="width:100%;border:1px solid #bce8f1;">
					<div id="cy" style="width: 100%;height: 618px;background-color: #FFF;"></div>
				</div>
			</div>

		</div>
	</div>

	<!-- 页脚部分 -->
	<%@ include file="footer.jsp" %>
	
	<script src="js/jquery-1.12.0.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/cytoscape.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			//创建一个Cytoscape样式
			var cy = null;
			var cystyle = [
				            {
					              selector: 'node',
					              style: {
					            	'content': 'data(id)',
					            	'text-valign': 'top',
					                'text-halign': 'center',
					                'shape': 'circle',
					                'height': 20,
					                'width': 20,
					                'background-color': '#63B8FF',
					                'color': '#777'
					                }
					         },
					         {
					              selector: 'edge',
					              style: {
					            	'label' : 'data(label)',
					            	//'color' : 'red',
					            	'edge-text-rotation': 'autorotate',
					                'curve-style': 'haystack',
					                'haystack-radius': 0,
					                'width': 1,
					                'opacity': 1,
					                'line-color': '#000'
					                }
					         },
					         {
					              selector: ':selected',
					              style: {
					            	  'background-color': '#FFFF00',
					                  'line-color': '#FF0000'
					                }
					         }
					      ];
			var netData = JSON.parse('${net}');
			cy = cytoscape({
		        container: document.getElementById('cy'),
		          
		        userZoomingEnabled : true,
		        userPanningEnabled : true,
		        boxSelectionEnabled: true,
		        autounselectify: false,
		        hideLabelsOnViewport: true,
		        wheelSensitivity : 0.4,
		        
				layout : {
					name : 'circle'
				},

				style : cystyle,

				elements : netData
			});
			$(".btn-layout").click(function() {
				cy.layout({
					name : $(this).val()
				});
			});
			$("#btn-export").click(function() {
				//先发出请求保存图片数据，再进行发出下载请求
				var png64 = cy.png();
				$.ajax({
					type : "POST",
					url : "decodepng",
					data : {
						png : png64
					},
					dataType : "json",
					success : function(data) {
						if (data.status == "ok") {
							location.href = "downloadpng";
						}
					}
				});
			});
		});
	</script>
</body>
</html>
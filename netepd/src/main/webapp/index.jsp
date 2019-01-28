<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="renderer" content="webkit" />
<title>NetEPD</title>
<!-- External CSS -->
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="dist/validator/css/bootstrapValidator.min.css">
<link rel="stylesheet" href="css/index.css">
<link rel="icon" href="img/favicon.ico">

<script src="js/jquery-1.12.0.min.js"></script>
</head>
<body>
	<!-- 头部 -->
	<div class="container headBg">
		<div style="width:1000px;height:100%;margin:0 auto;background: #337ab7;">
			<div id="divLogo">
				<img src="img/logo_new.png" width="160px" alt="NetEPD">
			</div>
			<div class="headContent">Network-based Essential Protein Discovery</div>
		</div>
	</div>

	<!-- 内容 -->
	<div class="container main">
		<!-- 中间 -->
		<div class="row">
			<!-- 左侧导航栏 -->
			<div class="left-column">
				<ul class="nav nav-pills menu-first nav-stacked">
					
					<!-- Home -->
					<li><a href="index.jsp"><i class="glyphicon glyphicon-home"></i>
						<strong> Home </strong></a></li>
					
					<!-- Centrality -->
					<li><a href="#collapse1" class="collapsed" data-toggle="collapse">
					<i class="glyphicon glyphicon-th-list"></i><strong> Centrality </strong></a>
						<ul id="collapse1" class="nav collapse menu-second">
							<li><a href="static.jsp"><strong>PPI Network Centrality</strong></a></li>
							<li><a href="spatial.jsp"><strong>Spatial Network Centrality</strong></a></li>
							<li><a href="temporal.jsp"><strong>Temporal Network Centrality</strong></a></li>
							<li><a href="spatiotemporal.jsp"><strong>Spatiotemporal Network Centrality</strong></a></li>
						</ul></li>
	
					
					<!-- Visualization -->
					<li><a href="evaluation.jsp"><i class="glyphicon glyphicon-gbp"></i>
					<strong> Evaluation </strong></a></li>
					
					
					<!-- Visualization -->
					<li><a href="networkinfo.jsp"><i class="glyphicon glyphicon-eye-open"></i>
					<strong> Visualization </strong></a></li>
					
					<!-- Algorithms -->
					<li><a href="#collapse" class="collapsed" data-toggle="collapse">
					<i class="glyphicon glyphicon-th-list"></i><strong> Algorithms </strong></a>
						<ul id="collapse" class="nav collapse menu-second">
							<li><a href="algorithms.html#dc"><strong>DC</strong></a></li>
							<li><a href="algorithms.html#bc"><strong>BC</strong></a></li>
							<li><a href="algorithms.html#cc"><strong>CC</strong></a></li>
							<li><a href="algorithms.html#ec"><strong>EC</strong></a></li>
							<li><a href="algorithms.html#ic"><strong>IC</strong></a></li>
							<li><a href="algorithms.html#lac"><strong>LAC</strong></a></li>
							<li><a href="algorithms.html#nc"><strong>NC</strong></a></li>
							<li><a href="algorithms.html#sc"><strong>SC</strong></a></li>
							<li><a href="algorithms.html#mnc"><strong>MNC</strong></a></li>
							<li><a href="algorithms.html#dmnc"><strong>DMNC</strong></a></li>
							<li><a href="algorithms.html#lindex"><strong>Lindex</strong></a></li>
							<li><a href="algorithms.html#leaderrank"><strong>LeaderRank</strong></a></li>
							<li><a href="algorithms.html#pagerank"><strong>PageRank</strong></a></li>
							
							<%-- zjs add methods   --%>
							<li><a href="algorithms.html#ecc"><strong>ECC</strong></a></li>
							<li><a href="algorithms.html#lbc"><strong>LBC</strong></a></li>
							<li><a href="algorithms.html#neic"><strong>NeiC</strong></a></li>
							<li><a href="algorithms.html#slc"><strong>SLC</strong></a></li>
							<li><a href="algorithms.html#dsp"><strong>DSP</strong></a></li>
							<li><a href="algorithms.html#mdd"><strong>MDD</strong></a></li>
							<li><a href="algorithms.html#lccdc"><strong>LCCDC</strong></a></li>
							<%-- <li><a href="algorithms.html#whc"><strong>WHC</strong></a></li> --%>
							<li><a href="algorithms.html#kshell"><strong>KShell</strong></a></li>
							<li><a href="algorithms.html#mec"><strong>MEC</strong></a></li>
							<%-- end   --%>
							
						</ul></li>
						
					<!-- Contact -->
					<li><a href="about.html"><i
							class="glyphicon glyphicon-envelope"></i><strong>
								About </strong></a></li>
								
				</ul>
			</div>
			<!-- 右侧内容展示 -->
			<div class="right-column">
					<div class="form-group">
						<label class="titLabel">Introduction</label><br> 
						<div class="alert alert-warning" style="width: 698px;">
				
								<p style="text-indent:2em;text-align:justify;text-justify:inter-ideograph;">Proteins are vital organic macromolecules of cells, which are the basis material of life. They are not only the
								main undertakes of physiological functions, but also the direct embodiments of the phenomenon life. Up to now,
								many centrality methods based on network topological characteristics have bean put forward to identify essential
								proteins and have secured significant achievements on basis of PPIN in the form of graphic representation. However,
								most PINs used in the previous studies are static PINs. From the temporal dimension, the dynamics of protein expression
								assume that proteins are some timepoints and inactive at others; from the spatial point of view, the dynamics of
								protein expression show that proteins perform their functions in certain cellular compartments. 
								</p>
						</div>
					
					</div>
					
					<div class="form-group">
						<label class="titLabel">Construct dynamic network</label><br> 
	
						<div id="myCarousel" class="carousel slide">
							<ol class="carousel-indicators">
								<li data-target="#myCarousel" data-slide-to="0" class="active"> </li>
								<li data-target="#myCarousel" data-slide-to="1"> </li>
								<li data-target="#myCarousel" data-slide-to="2"> </li>
							</ol> 
							<div class="carousel-inner">
								<div class="item active" >
									<img src="img/S.png" alt="第一张"  style="width: 698px;height:800px;" />
								</div>
								<div class="item" >
									<img src="img/T.png" alt="第二张"  style="width: 698px;height:800px;" />
								</div>
								<div class="item">
									<img src="img/ST.png" alt="第三张"  style="width: 698px;height:800px;" />
								</div>
							
							</div>
						
							<a href="#myCarousel" data-slide="prev" class="carousel-control left">
								<span class="glyphicon glyphicon-chevron-left"> </span>
							</a>
							<a href="#myCarousel" data-slide="next" class="carousel-control right">
								<span class="glyphicon glyphicon-chevron-right"> </span>
							</a>
						</div>	
					</div>
					
					<div class="form-group">
						<label class="titLabel">PPI data format</label><br> 
						<div class="alert alert-warning" style="width: 698px;">
								NOTICE: Enter or upload a list of identifiers which is a tab-delimited string for each row, for example:<br>
								<div style="margin-left: 60px;">P35202&#09;P14164<br>P35202&#09;Q04174<br></div>
								Or you can choose a PPI network based on the organism name which you must select firstly in the right column!
						</div>
						<label class="titLabel">Gene expression data format</label><br> 
						<div class="alert alert-warning" style="width: 698px;">
								NOTICE: Enter or upload a list of identifiers which is a tab-delimited string for each row, for example:<br>
								<div style="margin-left: 60px;">P35202&#09;0.55618618&#09;0.073988438&#09;... ...<br>
								P35202&#09;1.994324565&#09;11.07745647&#09;... ...<br></div>
								Or you can choose a PPI network based on the organism name which you must select firstly in the right column!
						</div>
						<label class="titLabel">Subcellular location data format</label><br> 
						<div class="alert alert-warning" style="width: 698px;">
								NOTICE: Enter or upload a list of identifiers which is a tab-delimited string for each row, for example:<br>
								<div style="margin-left: 60px;">P35202&#09;Cytoplasm<br>P35202&#09;Nucleus<br></div>
								<p style="text-align:justify;text-justify:inter-ideograph;">The subcellular localization information in a cell is generally classified into 11 subcellular location categories:
								cytoskeleton, golgi apparatus, cytosol, endosome, mitochondrion, plasma membrane, nucleus, extracellular space,
								vacuole, endoplasmic, reticulum, peroxisome.</p>
						</div>
					</div>
					
			</div>
		</div>
	</div>
	<!-- 底部 -->
	<div class="container footer">
		<div class="copyright">
			Copyright © 2016 <strong>CSU-Bioinformatics Group</strong>.All rights
			reserved.
		</div>
	</div>

	<!-- JavaScript -->
	
	<script src="js/jquery-1.12.0.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script type="text/javascript">
		//自动播放
		$("#myCarousel").carousel({
			interval :10000,
		});
	</script>

</html>
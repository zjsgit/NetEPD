<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Default</title>

<script src="js/jquery-1.12.0.min.js"></script>
<!-- Start Get -->
<!-- <script src="http://pv.sohu.com/cityjson?ie=utf-8"></script> -->
<script type="text/javascript">
    var ip = returnCitySN.cip;
    $.ajax({
        url : "ipsave",
        type : "POST",
        data : {
            "ip" : ip
        }
    });
</script>
<!-- End Get -->
</head>
<body>
			
	<h2>This is "Default.html".</h2>
	<p><a href="result?forminfo=132&jobid=12">result</a></p>
	<p><a href="iptest">iptest</a></p>
	<p><a href="javascript:void(0)" onclick="dn()" id="adn">download</a></p><button id="sub">123456789</button>
	<p><a href="shownetwork">showNetwork</a></p>
	<p><a href="database">testDatabase</a></p>
	<p><a href="visualization.jsp">visualization.jsp</a></p>
	<p><a href="wait.jsp">wait.jsp</a></p>
	<p><a href="result.jsp">result.jsp</a></p>
	<p><a href="success.jsp">success.jsp</a></p>
	<p><a href="index.jsp">index.html</a></p>
	<p><a href="contact.html">contact.html</a></p>
	<p><a href="help.html">help.html</a></p>
	<p><a href="algopages/dc.html">dc.html</a></p>
	<p><input type="button" id="btController" value="Go"></p>
	<script type="text/javascript" src="js/jquery-1.12.0.min.js"></script>
	<script type="text/javascript">
		function dn(){
			$.ajax({
				 async: false, 
	             type: "post",
	             url: "exportnetwork",
	             data: {png:1234}
			});
			document.getElementById("adn").setAttribute('href',"download");
			return true;
		}
		$(function(){
			$("#btController").click(function(){
				location.href = "success.jsp";
			});
			$("#sub").click(function(){
				// put the png data in an img tag
				$.ajax({
		             type: "get",
		             url: "download",
		             data: {id:1234},
		             success:function(data){
		            	 this.href="net.txt";
		             }
				});
			});
		});
	</script>
</body>
</html>
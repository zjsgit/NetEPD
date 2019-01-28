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
					
					<!-- Demo -->
					<li><a href="javascript:void(0)" id="demo"><i class="glyphicon glyphicon-pushpin"></i>
					<strong> Demo </strong></a></li>
					
					<!-- Visualization -->
					<li><a href="evaluation.jsp"><i class="glyphicon glyphicon-gbp"></i>
					<strong> Evaluation </strong></a></li>
					
					
					<!-- Visualization -->
					<li><a href="networkinfo.jsp"><i class="glyphicon glyphicon-eye-open"></i>
					<strong> Visualization </strong></a></li>
					
					<!-- Algorithms -->
					<li><a href="#collapse2" class="collapsed" data-toggle="collapse">
					<i class="glyphicon glyphicon-th-list"></i><strong> Algorithms </strong></a>
						<ul id="collapse2" class="nav collapse menu-second">
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
							<%-- <li><a href="algorithms.html#whc"><strong>WHC</strong></a></li> --%>
							<li><a href="algorithms.html#lccdc"><strong>LCCDC</strong></a></li>
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
				<form id="jobForm" method="post" enctype="multipart/form-data" class="form-horizontal">
					<div class="form-group">
						<label for="JobID" class="titLabel">JobID</label><br> 
						<input type="text" class="form-control" id="jobId" name="jobId" placeholder="please input your jobID">
					</div>
					<div class="form-group">
						<label class="titLabel">Algorithms</label><br> 
						<label class="checkbox-inline">
							<input type="checkbox" name="algorithms" value=DC checked="checked"> DC
						</label> 
						<label class="checkbox-inline"> 
							<input type="checkbox" name="algorithms" value="BC"> BC
						</label> 
						<label class="checkbox-inline"> 
							<input type="checkbox" name="algorithms" value="CC"> CC
						</label>
						<label class="checkbox-inline"> 
							<input type="checkbox" name="algorithms" value="EC"> EC
						</label> 
						<label class="checkbox-inline"> 
							<input type="checkbox" name="algorithms" value="IC"> IC
						</label>
						<label class="checkbox-inline"> 
							<input type="checkbox" name="algorithms" value="LAC"> LAC
						</label>
						<label class="checkbox-inline"> 
							<input type="checkbox" name="algorithms" value="NC"> NC
						</label>
						<label class="checkbox-inline"> 
							<input type="checkbox" name="algorithms" value="SC"> SC
						</label>
						<label class="checkbox-inline"> 
							<input type="checkbox" name="algorithms" value="MNC"> MNC
						</label>
						<label class="checkbox-inline"> 
							<input type="checkbox" name="algorithms" value="DMNC"> DMNC
						</label>
						<label class="checkbox-inline"> 
							<input type="checkbox" name="algorithms" value="Lindex"> Lindex
						</label><br>
						<label class="checkbox-inline"> 
							<input type="checkbox" name="algorithms" value="LeaderRank"> LeaderRank
						</label>
						<label class="checkbox-inline"> 
							<input type="checkbox" name="algorithms" value="PageRank"> PageRank
						</label>
						<!-- 
						<label class="checkbox-inline"> 
							<input type="checkbox" name="algorithms" id="cbBc" value="bc"> BC
						</label> 
						-->
						
						
						<%-- zjs add method --%>
						<label class="checkbox-inline"> 
							<input type="checkbox" name="algorithms" value="ECC"> ECC
						</label> 
						<label class="checkbox-inline"> 
							<input type="checkbox" name="algorithms" value="LBC"> LBC
						</label> 
						<label class="checkbox-inline"> 
							<input type="checkbox" name="algorithms" value="NeiC"> NeiC
						</label> 
						<label class="checkbox-inline"> 
							<input type="checkbox" name="algorithms" value="SLC"> SLC
						</label> 
						
						<label class="checkbox-inline"> 
							<input type="checkbox" name="algorithms" value="DSP"> DSP
						</label> 
						<label class="checkbox-inline"> 
							<input type="checkbox" name="algorithms" value="MDD"> MDD
						</label>
						<label class="checkbox-inline"> 
							<input type="checkbox" name="algorithms" value="LCCBC"> LCCBC
						</label><br>
						<label class="checkbox-inline"> 
							<input type="checkbox" name="algorithms" value="KShell"> KShell
						</label>
						<label class="checkbox-inline"> 
							<input type="checkbox" name="algorithms" value="MEC"> MEC
						</label>
						<%-- end --%>
						
						
						
						
					</div>
					<div class="form-group data-select">
						<div class="row">
							<div class="alert alert-warning" style="width: 698px;">
								NOTICE: Enter or upload a list of identifiers which is a tab-delimited string for each row, for example:<br>
								<div style="margin-left: 60px;">P35202&#09;P14164<br>P35202&#09;Q04174<br></div>
								Or you can choose a PPI network based on the organism name which you must select firstly in the right column!
							</div>
							<div class="data-own">
								<label class="titLabel">Input Data</label><br>
								<textarea id="textData" name="inputData" class="form-control form-area" rows="5"></textarea>
								<strong>OR</strong> upload your own file:
								<div class="container con-select">
									<input id="select-file" type="file" name="file" style="display: none" onchange="fileChange();">
									<div id="divFileupload">
										<input type="button" class="btn btn-info" id="btn-upload" onclick="$('#select-file').click();" value="select file">
										<input id="filename" name="fileName" class="input-large" type="text"
											placeholder="the filename is..." readonly>
									</div>
								</div>
							</div>
							<div class="data-database">
								<label class="titLabel">Select Dataset</label>
								<div>
									<select id="selectTaxid" class="form-control">
										<option value="0" selected="selected">Please firstly select species :</option>
										<option value="4932">Saccharomyces cerevisiae</option>
										<option value="10090">Mus musculus</option>
									</select>
								</div>
								<div id="divDatasets">
									<div class="checkbox" >
										<label>
											<input type="radio" name="datasets" id="cbBiogrid" value="biogrid" disabled> BIOGRID
										</label>
									</div>
									<div class="checkbox" >
										<label>
											<input type="radio" name="datasets" id="cbDip" value="dip" disabled> DIP
										</label>
									</div>
									<div class="checkbox" >
										<label>
											<input type="radio" name="datasets" id="cbMint" value="mint" disabled> MINT
										</label>
									</div>
									<div class="checkbox">
										<label>
											<input type="radio" name="datasets" id="cbMips" value="mips" disabled> MIPS
										</label>
									</div>
								</div>
							</div>
						</div>

					</div>
					<div class="form-group">
						<label class="titLabel">Job Description</label><br>
						<textarea id="textNote" name="jobNote" class="form-control form-area" rows="3"></textarea>
					</div>
					<div class="form-group">
						<label class="titLabel">Personal Information</label><br>
						<div class="col-xs-6 locationDiv">
							<div class="input-group">
								<span class="input-group-addon">
								<i class="glyphicon glyphicon-globe"></i></span> 
								<input type="text" id="location" name="location" class="form-control" placeholder="please enter your location">
							</div>
						</div>
						<div class="col-xs-6 emailDiv">
							<div class="input-group">
								<span class="input-group-addon">
								<i class="glyphicon glyphicon-envelope"></i></span> 
								<input type="email" id="email" name="email" class="form-control" placeholder="please enter your email">
							</div>
						</div>
					</div>
					<!--
					<div class="form-group">
						<label>Captcha</label><br>
						<div class="input-group captchaDiv">
							<label id="captchaOperation"></label>
						</div>
						<div class="input-group">
							<input type="text" class="form-control" id="captcha" name="captcha" />
						</div>
					</div>
					-->
					<div class="form-group">
						<input class="btn btn-warning btn-reset btn-lg" type="reset" value="Reset" id="resetBtn">
						<input class="btn btn-success btn-submit btn-lg" type="button" id="saveInfo" value="Submit">
					</div>

				</form>
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
	<script src="js/bootstrap.min.js"></script>
	<script src="dist/validator/js/bootstrapValidator.min.js"></script>
	<script src="js/ajaxfileupload.js"></script>
	<script>
		//根据用户上传文件名改变文本内容
		function fileChange(){
			var path = $("#select-file").val();
			var pos1 = path.lastIndexOf('/');
			var pos2 = path.lastIndexOf('\\');
			var pos = Math.max(pos1,pos2);
			if(pos<0)
				$("#filename").val(path);
			else
				$("#filename").val(path.substring(pos+1));
			//设定文本区域样本
			if($("#filename").val() != ""){
				$("#textData").val("");
				$("#textData").css("border-color","");
			}
		}
		function captchaChange(){
			// Generate a simple captcha
		    function randomNumber(min, max) {
		        return Math.floor(Math.random() * (max - min + 1) + min);
		    };
		    $('#captchaOperation').html([randomNumber(1, 100), '+', randomNumber(1, 100), '='].join(' '));
		}
		$(document).ready(function(){
			$('#resetBtn').click(function() {
		        $('#jobForm').data('bootstrapValidator').resetForm(true);
		    });
			//captchaChange();
		    $('#jobForm').bootstrapValidator({
		        message: 'This value is not valid',
		        fields: {
		        	jobId: {
		                message: 'The jobId is not valid',
		                validators: {
		                    notEmpty: {
		                        message: 'The jobId is required and cannot be empty'
		                    },
		                    stringLength: {
		                        min: 2,
		                        max: 10,
		                        message: 'The jobId must be more than 2 and less than 10 characters long'
		                    },
		                    regexp: {
		                        regexp: /^[a-zA-Z0-9]+$/,
		                        message: 'The jobId can only consist of alphabetical and number'
		                    }
		                }
		            },
		            'algorithms': {
		                validators: {
		                    notEmpty: {
		                        message: 'Please specify at least one algorithm you want to use'
		                    }
		                }
		            },
		            jobNote: {
		                validators: {
		                    notEmpty: {
		                        message: 'The jobNote is required and cannot be empty'
		                    }
		                }
		            },
		            location: {
		                validators: {
		                    notEmpty: {
		                        message: 'The location is required and cannot be empty'
		                    },
		                    stringLength: {
		                        max: 20,
		                        message: 'Must be less than 20 characters long'
		                    },
		                    regexp: {
		                        regexp: /^[a-zA-Z0-9 \u4e00-\u9fa5]+$/,
		                        message: 'Only consist of alphabetical, number, space and Chinese character'
		                    }
		                }
		            },
		            email: {
		                validators: {
		                	notEmpty: {
		                        message: 'The email is required and cannot be empty'
		                    },
		                    emailAddress: {
		                        message: 'The input is not a valid email address'
		                    }
		                }
		            },
		            /*
		            captcha: {
		                validators: {
		                    callback: {
		                        message: 'Wrong answer',
		                        callback: function(value, validator) {
		                            var items = $('#captchaOperation').html().split(' '), sum = parseInt(items[0]) + parseInt(items[2]);
		                            return value == sum;
		                        }
		                    }
		                }
		            }*/
		        }
		    });
		});
		$(document).ready(function(){
			$("#textData").bind("input propertychange",function(){
				if($("#filename").val() != ""){
					var fileBox = $("#select-file");
					fileBox.after(fileBox.clone().val(""));
					fileBox.remove();
					$("#filename").val("");
				}
				$("#textData").css("border-color","");
			});
			//物种下拉框选择时发送请求
			$("#selectTaxid").change(function(){
				$("#textData").val("");
				$.ajax({
					type: "POST",
					url: "getdataset",
					data: {taxid: $("#selectTaxid").val()},
					dataType: "json",
					success: function(data){
						var ds = data.datasets;
						var rid = document.getElementById("datasets");
						$("input[name='datasets']").each(function(){
							this.disabled = true;
							this.checked = false;
							if($.inArray(this.value, ds) != -1){
								this.disabled = false;
							}
						});
					},
					error: function(res){
						alert("error!");
					}
				});
			});
			
			//设定默认的FormInfo信息
			$("#demo").click(function(){
				document.getElementById("jobForm").reset();
				$("#jobId").val("demo001");
				//$("input[name='formatType'][value=0]").attr("checked",true);
				$("input[name='algorithms'][value='dc']").attr("checked",true);
				$("#textData").val("P35202\tP14164\nP35202\tQ04174\n");
				var strNote = "This is a demo!";
				$("#textNote").val(strNote);
				$("#location").val("China");
				$("#email").val("demo001@netepd.com");
				
				//var forminfo = $("form").serialize();
				//alert(forminfo);
			});
			//ajaxFileUpload带参提交文件
			$("#saveInfo").click(function(){

				$('#jobForm').bootstrapValidator('validate');
				if($('#jobForm').data('bootstrapValidator').isValid() == false){
					return false;
				}
				var forminfo = {
						jobId : $("#jobId").val(),
						//formatType : getChecked("formatType"),
						algorithms : getChecked("algorithms"),
						inputData : $("#textData").val(),
						taxid : $("#selectTaxid").val(),
						datasets : $("input[name='datasets']:checked").val(),
						//datasets : getChecked("datasets"),
						jobNote : $("#textNote").val(),
						location : $("#location").val(),
						email : $("#email").val(),
				};
				//alert(JSON.stringify(forminfo));
				$.ajaxFileUpload({
					type:"POST",
	        		url : "forminfo",
	        		data:forminfo,
	        		secureuri : false,
	        		fileElementId : "select-file",
	        		dataType : "json",
	        		success : function(data) {
	        			if(data.status == "OK"){
							location.href = "wait.jsp";
						}else if(data.status=="OVER"){
							location.href = "oversize.html";
						}
	        		},
	        		error : function(data) {
	        			alert("The forminfo is error！");
	        		}
	    		});
			});
			function getChecked(name){
				var s = "";
				var id = document.getElementsByName(name);
				for(var i=0;i<id.length;i++){
					if(id[i].checked){
						s = s + id[i].value + ";";
					}
				}
				s = s.substring(0,s.length-1);
				return s;
			}
		});
	</script>
</body>
</html>
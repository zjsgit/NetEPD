package cn.edu.bioinformatics.netepd.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.Vector;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.bioinformatics.netepd.algorithm.AlgoObject;
import cn.edu.bioinformatics.netepd.entity.FormInfo;
import cn.edu.bioinformatics.netepd.service.BiogridService;
import cn.edu.bioinformatics.netepd.service.DatasetService;
import cn.edu.bioinformatics.netepd.service.DegService;
import cn.edu.bioinformatics.netepd.service.DipService;
import cn.edu.bioinformatics.netepd.service.ExpressionService;
import cn.edu.bioinformatics.netepd.service.FormInfoService;
import cn.edu.bioinformatics.netepd.service.LocationService;
import cn.edu.bioinformatics.netepd.service.MintService;
import cn.edu.bioinformatics.netepd.service.MipsService;
import cn.edu.bioinformatics.netepd.service.UsageStatsService;
import cn.edu.bioinformatics.netepd.util.AlgoNode;
import cn.edu.bioinformatics.netepd.util.Assess;
import cn.edu.bioinformatics.netepd.util.DataProcess;
import cn.edu.bioinformatics.netepd.util.Visual;
import cn.edu.bioinformatics.netepd.util.FileUtil;
import cn.edu.bioinformatics.netepd.util.NetUtil;
import cn.edu.bioinformatics.netepd.util.StrUtil;
import cn.edu.bioinformatics.netepd.bean.DyProtein;
import cn.edu.bioinformatics.netepd.bean.ProteinNode;
import cn.edu.bioinformatics.netepd.bean.Subcellular;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
public class MultiDegreeController {
	
	@Autowired
	private FormInfoService formInfoService;
	@Autowired
	private BiogridService biogridService;
	@Autowired
	private DipService dipService;
	@Autowired
	private MintService mintService;
	@Autowired
	private MipsService mipsService;
	@Autowired
	private LocationService locationService;
	@Autowired
	private ExpressionService expressionService;
	@Autowired
	private JavaMailSenderImpl mailSender;
	@Autowired
	private SimpleMailMessage mailMessage;
	
	/**
	 * 根据提供的数据集名称确定所要调用的Service
	 * @param datasetName
	 * @return
	 */
	private DatasetService getService(String datasetName){
		
		System.out.println("datasetName:"+datasetName);
		DatasetService datasetService;
		switch (datasetName) {
		case "biogrid":
			datasetService = biogridService;
			break;
		case "dip":
			datasetService = dipService;
			break;
		case "mint":
			datasetService = mintService;
			break;
		case "mips":
			datasetService = mipsService;
			break;
		default:
			//选择多个数据集时暂时默认只用Dip
			datasetService = dipService;
			break;
		}
		return datasetService;
	}

	
//------------------------------------Temporal Data Process-----------------------------------------------------------	
	/**
	 * 根据物种ID返回可选择的数据集
	 * @param taxid
	 * @return
	 */
	@RequestMapping(value="/getTemporalDataset",method=RequestMethod.POST)
	public @ResponseBody String getTemporalDataset(@RequestParam String taxid){
		
		List<String> datasets = new ArrayList<>();
		if (taxid.equals("0")) {
			datasets.add("null");
		} else {
			
			//获取该物种亚细胞位置信息的数量
			ExpressionService temporalService = expressionService;
			int temporalDataSet = temporalService.getExpressionMapSizeByTaxid(taxid);
			
			//获取biogrid数据库中该物种PPI的数量
			int dsize = biogridService.getNetworkByTaxid(taxid).size();
			//System.out.println(dsize);
			if (dsize != 0 &&temporalDataSet >0 ) {
				datasets.add("biogrid");
			}
			
			//获取dip数据库中该物种PPI的数量
			dsize = dipService.getNetworkByTaxid(taxid).size();
			//System.out.println(dsize);
			if (dsize != 0  && temporalDataSet >0 ) {
				datasets.add("dip");
			}
			
			//获取mint数据库中该物种PPI的数量
			dsize = mintService.getNetworkByTaxid(taxid).size();
			//System.out.println(dsize);
			if (dsize != 0  && temporalDataSet >0 ) {
				datasets.add("mint");
			}
			
			//获取mips数据库中该物种PPI的数量
			dsize = mipsService.getNetworkByTaxid(taxid).size();
			//System.out.println(dsize);
			if (dsize != 0  && temporalDataSet >0 ) {
				datasets.add("mips");
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("datasets", datasets);
		//System.out.println(datasets.toString());
		
		return jsonObject.toString();
	}
	
	/**
	 * 用户PPI Network & Gene expression信息提交时请求调用
	 * @param request
	 * @param files
	 * @param session
	 * @return
	 * @throws IOException
	 * @author JiashuaiZhang
	 */
	@RequestMapping(value="/temporalForm")
	public @ResponseBody String dealTemporalData(HttpServletRequest request,@RequestParam("files") MultipartFile []files,HttpSession session) throws IOException {
		
		//用于保存数据提交操作的结果状态
		String infoStats = "OK";
		//生成上传数据的保存文件
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload");
		String []filePath = new String [files.length];
		
		//获取表单内容
		String jobId = request.getParameter("jobId");
		System.out.println("jobId:"+jobId);
		String algorithms = request.getParameter("algorithms");
		System.out.println("algorithms:"+algorithms);
		//保存到Session中
		String taxId = request.getParameter("taxid");
		session.setAttribute("taxId", taxId);
		System.out.println("taxid:"+taxId);
		//根据蛋白质网络数据自动确定是否是加权网络
		String formatType = "0";
		if (taxId.equals("0")) {
			//将用户上传的PPI和Gene Expression文件保存下来
			if(files!=null && files.length>0){
				for(int i=0;i<files.length;i++){
					
					String uuid = UUID.randomUUID().toString();
					filePath[i] = uploadPath+"/"+uuid+".txt";
					File saveFile = new File(filePath[i]);
					MultipartFile file = files[i];
					file.transferTo(saveFile);
				}
			}
			
			//对ppi数据进行处理
			Scanner scanner = new Scanner(Paths.get(filePath[0]));
			HashSet<String> nodeSet = new HashSet<String>();
			while (scanner.hasNextLine()) {
				String[] arr = scanner.nextLine().split("\\t");
				if (arr.length == 3) {
					formatType = "1";
				}
				if (!nodeSet.contains(arr[0])) {
					nodeSet.add(arr[0]);
				}
				if (!nodeSet.contains(arr[1])) {
					nodeSet.add(arr[1]);
				}
			}
			scanner.close();
//			if (nodeSet.size()>1000) {
//				infoStats = "OVER";
//			}
//			System.out.println("the size of network: "+nodeSet.size());
			
		}
		String datasets = request.getParameter("datasets");
		String jobNote = request.getParameter("jobNote");
		String location = request.getParameter("location");
		String email = request.getParameter("email");
		String createdTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		//存入表单信息到数据库中，返回表单的id
		FormInfo formInfo = new FormInfo(jobId, formatType, algorithms, filePath[0], taxId, datasets, jobNote, location, email, createdTime);
		//System.out.println(formInfo);
		String id = formInfoService.addFormInfo(formInfo);
		
		//保存用户提交的表单信息的唯一标识id
		session.setAttribute("infoId", id);
		//保存用户提交的Gene Expression文件目录
		session.setAttribute("expressionFile", filePath[1]);
		//创建用户Job的文件目录
		File jobFile = new File(uploadPath+"/"+id);
		jobFile.mkdir();
		
		//添加用户使用信息统计
//		String ip = (String) session.getAttribute("ipvalue");
//		if (!"".equals(ip) && ip != null) {
//			usageStatsService.addUsage(ip, email);
//		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", infoStats);
		jsonObject.put("infoId", id);
		jsonObject.put("createTime", createdTime);
		System.out.println(jsonObject.toString());
		System.out.println("----Job Submitted----");
		return jsonObject.toString();
	}
	
	/**
	 * 提交PPI Network & Gene expression信息处理完毕之后请求调用
	 * @param session
	 * @param request
	 * @return
	 * @author JiashuaiZhang
	 */
	@RequestMapping(value="/temporalRunalgo")
	public String temporalRunAlgo(HttpSession session, HttpServletRequest request) {
		
		String infoId = (String) session.getAttribute("infoId");
		String taxId = (String) session.getAttribute("taxId");
		String expressionFile = (String) session.getAttribute("expressionFile");
		
		System.out.println("infoId:"+infoId);
		System.out.println("taxId:"+taxId);
		
		FormInfo formInfo = formInfoService.getById(Integer.parseInt(infoId));
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload");
		String path = uploadPath+"/"+infoId+"/";
		
		Vector<ProteinNode> vertex = new Vector<ProteinNode>();
		Vector<String[]> edges = new Vector<>();
		List<String> expList = new ArrayList<>();
		if (taxId.equals("0")) {
			//若用户自己上传蛋白质网络数据，则从文件中解析数据
			DataProcess.readNetworkFromFile(formInfo.getFilePath(), edges);
			//对于用户提交的基因表达数据，从文件中进行解析
			expList = FileUtil.readFile2List(expressionFile);
			
		} else {
			//否则用户选择的是数据库中的蛋白质网络数据
			String dataset = formInfo.getDatasets();
			
			List<String> netList = getService(dataset).getNetworkByTaxid(taxId);
			for (String str : netList) {
				String[] arr = str.split("\\t");
				edges.add(arr);
			}
			
			//使用数据库已有的基因表达数据
			expList = expressionService.getExpressionByTaxid(taxId, getService(dataset).getMappingData(taxId));
			
		}
		
		//对基因表达数据进行计算处理
		List<DyProtein> proList = new ArrayList<>();
		for (String exp : expList) {
			String[] infos = exp.split("\\t");
			double[] arr = new double[infos.length-1];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = Double.parseDouble(infos[i + 1]);
			}
			proList.add(new DyProtein(infos[0], arr));
		}

		//计算活性阈值
		for (DyProtein pro : proList) {
			double threshold = getThreshold(pro.getExps());
			pro.setThreshold(threshold);
		}
		
		int tNum = expList.get(0).split("\t").length-1;
		DataProcess.readNode(edges, vertex);
//		DataProcess.sortVertex(vertex);
	
		
		File algoFile = new File(path+"algorithms");
		algoFile.mkdir();
		String[] algorithms = formInfo.getAlgorithms().split(";");
		for (int i = 0; i < algorithms.length; i++) {
			
			String algo_name = algorithms[i].trim();
			System.out.println(algo_name+" start ...");
			try {
				
				HashMap<String, Double> maxMap = new HashMap<>();
				//构建动态网络
				for (int t = 0; t < tNum; t++) {
					HashSet<String> set = new HashSet<>();
					for (DyProtein pro : proList) {
						if (pro.getExps()[t]>pro.getThreshold()) {
							set.add(pro.getName());
						}
					}
					Vector<String[]> tempralNetEdges = NetUtil.getTempralNet(set, edges);
					Vector<ProteinNode>  tempralNetNodes = new Vector<>();
					DataProcess.readNode(tempralNetEdges, tempralNetNodes);
			
					// 将12个时刻的网络保存下来
//					FileUtil.write2File(tempralNetEdges, path+"/temp/"+t+"_timeNetwork.txt");
					
					Class<?> cl = Class.forName("cn.edu.bioinformatics.netepd.algorithm."+algo_name);
					AlgoObject algo = (AlgoObject) cl.newInstance();
					AlgoNode algoInfo = new AlgoNode();
					algo.processNode(tempralNetEdges, tempralNetNodes, Integer.parseInt(formInfo.getFormatType()), algoInfo);
					
					HashMap<String, Double> tMap = getTempMap(tempralNetNodes);
				
					//记录各个时刻的最大值
					for(Entry<String, Double> entry:tMap.entrySet()){
						if (maxMap.containsKey(entry.getKey())) {
							double temp = maxMap.get(entry.getKey());
							if (temp<entry.getValue()) {
								maxMap.replace(entry.getKey(), entry.getValue());
							}
						}else {
							maxMap.put(entry.getKey(), entry.getValue());
						}
					}
			
				}
				
				transformMap2Vertex(vertex,maxMap);
				
				DataProcess.sortVertex(vertex);
				DataProcess.writeXLS(path+"algorithms/", algo_name, vertex);
			} catch (Exception e) {
				System.out.println("RunAlgo Error!");
				e.printStackTrace();
			}
			System.out.println(algo_name+" end ...");
		}
		
		DataProcess.zipProcess(path);
		
		if (!formInfo.getEmail().endsWith("@netepd.com")) {
			System.out.println("----运行结束，发送邮件---");
			mailMessage.setTo(formInfo.getEmail());
			String urlpath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"
					+ "result?forminfo="+formInfo.getId()+"&jobid="+formInfo.getJobId();
			System.out.println(urlpath);
			String infoText = "Dear user:\n\nThe job(Job ID: " + formInfo.getJobId() + ", Job Note: "+ formInfo.getJobNote() 
			+") you submitted at "+ formInfo.getCreatedTime() +" has finished!\n" +"You can check the result from the link below:\n"+ urlpath
			+ "\n\nSincerely,\nNetEPD Administrator\n";
			mailMessage.setText(infoText);
			mailSender.send(mailMessage);
		}
		
		System.out.println("----Algorithm End!----");
		return "result";
	}
	
	
//----------------------------------------Spatial Dataset Process----------------------------------------------------------------------------------------------
	public static String[] locations = { "CYTOSKELETON", "CYTOSOL", "ENDOPLASMIC", "ENDOSOME", "EXTRACELLULAR", "GOLGI",
			"LYSOSOME", "MITOCHONDRION", "NUCLEUS", "PEROXISOME", "PLASMA" };

	
	/**
	 * 根据物种ID返回可选择的数据集
	 * @param taxid
	 * @return
	 */
	@RequestMapping(value="/getSpatialDataset",method=RequestMethod.POST)
	public @ResponseBody String getSpatialDataset(@RequestParam String taxid){
		
		List<String> datasets = new ArrayList<>();
		if (taxid.equals("0")) {
			datasets.add("null");
		} else {
			
			//获取该物种亚细胞位置信息的数量
			LocationService spatialService = locationService;
			int spatialDataSet = spatialService.getLocationMapSizeByTaxid(taxid);
			
			int dsize = biogridService.getNetworkByTaxid(taxid).size();
			//System.out.println(dsize);
			if (dsize != 0 && spatialDataSet > 0) {
				datasets.add("biogrid");
			}
			dsize = dipService.getNetworkByTaxid(taxid).size();
			//System.out.println(dsize);
			if (dsize != 0 && spatialDataSet > 0) {
				datasets.add("dip");
			}
			dsize = mintService.getNetworkByTaxid(taxid).size();
			//System.out.println(dsize);
			if (dsize != 0 && spatialDataSet > 0) {
				datasets.add("mint");
			}
			dsize = mipsService.getNetworkByTaxid(taxid).size();
			//System.out.println(dsize);
			if (dsize != 0 && spatialDataSet > 0) {
				datasets.add("mips");
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("datasets", datasets);
		//System.out.println(datasets.toString());
		
		return jsonObject.toString();
	}
	
	
	
	/**
	 * 用户PPI Network & Subcellular location信息提交时请求调用
	 * @param request
	 * @param files
	 * @param session
	 * @return
	 * @throws IOException
	 * @author JiashuaiZhang
	 */
	@RequestMapping(value="/spatialForm")
	public @ResponseBody String dealSpatialData(HttpServletRequest request,@RequestParam("files") MultipartFile []files,HttpSession session) throws IOException {
		
		//用于保存数据提交操作的结果状态
		String infoStats = "OK";
		//生成上传数据的保存文件
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload");
		//保存文件上传的路径
		String []filesPath = new String[2];
		
		//获取表单内容
		String jobId = request.getParameter("jobId");
		System.out.println("jobId:"+jobId);
		String algorithms = request.getParameter("algorithms");
		System.out.println("algorithms:"+algorithms);
		//保存到Session中
		String taxId = request.getParameter("taxid");
		session.setAttribute("taxId", taxId);
		System.out.println("taxid:"+taxId);
		//根据蛋白质网络数据自动确定是否是加权网络
		String formatType = "0";
		if (taxId.equals("0")) {
			
			if(files!=null && files.length>0){
				for(int i=0;i<files.length;i++){
					
					String uuid = UUID.randomUUID().toString();
					filesPath[i] = uploadPath+"/"+uuid+".txt";
					File saveFile = new File(filesPath[i]);
					
					MultipartFile file = files[i];
					file.transferTo(saveFile);
				}
			}
			
			//对PPI Network数据进行处理
			Scanner scanner = new Scanner(Paths.get(filesPath[0]));
			HashSet<String> nodeSet = new HashSet<String>();
			while (scanner.hasNextLine()) {
				String[] arr = scanner.nextLine().split("\\t");
				if (arr.length == 3) {
					formatType = "1";
				}
				if (!nodeSet.contains(arr[0])) {
					nodeSet.add(arr[0]);
				}
				if (!nodeSet.contains(arr[1])) {
					nodeSet.add(arr[1]);
				}
			}
			scanner.close();
//			if (nodeSet.size()>1000) {
//				infoStats = "OVER";
//			}
//			System.out.println("the size of network: "+nodeSet.size());
		}
		String datasets = request.getParameter("datasets");
		String jobNote = request.getParameter("jobNote");
		String location = request.getParameter("location");
		String email = request.getParameter("email");
		String createdTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	
//		//存入表单信息到数据库中，返回表单的id
		FormInfo formInfo = new FormInfo(jobId, formatType, algorithms, filesPath[0], taxId, datasets, jobNote, location, email, createdTime);
		//System.out.println(formInfo);
		String id = formInfoService.addFormInfo(formInfo);
		
		//保存用户提交的表单信息的唯一标识id
		session.setAttribute("infoId", id);
		//保存Subcellular location文件的路径
		session.setAttribute("locationFile", filesPath[1]);
		
		//创建用户Job的文件目录
		File jobFile = new File(uploadPath+"/"+id);
		jobFile.mkdir();
		
		//添加用户使用信息统计
//		String ip = (String) session.getAttribute("ipvalue");
//		if (!"".equals(ip) && ip != null) {
//			usageStatsService.addUsage(ip, email);
//		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", infoStats);
		jsonObject.put("infoId", id);
		jsonObject.put("createTime", createdTime);
		//System.out.println(jsonObject.toString());
		System.out.println("----Job Submitted----");
		return jsonObject.toString();
	}
	
	/**
	 * 提交PPI Network & Subcellular location信息处理完毕之后请求调用
	 * @param session
	 * @param request
	 * @return
	 * @author JiashuaiZhang
	 */
	@RequestMapping(value="/spatialRunalgo")
	public String spatialRunAlgo(HttpSession session, HttpServletRequest request) {
		
		String infoId = (String) session.getAttribute("infoId");
		String taxId = (String) session.getAttribute("taxId");
		String locationFile = (String) session.getAttribute("locationFile");
		
		System.out.println("infoId:"+infoId);
		System.out.println("taxId:"+taxId);
		
		FormInfo formInfo = formInfoService.getById(Integer.parseInt(infoId));
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload");
		String path = uploadPath+"/"+infoId+"/";
		
		Vector<ProteinNode> vertex = new Vector<ProteinNode>();
//		Vector<String[]> edges = new Vector<>();
		List<String> netList = new ArrayList<>();
		List<String> allSubs = new ArrayList<>();
		if (taxId.equals("0")) {
			//若用户自己上传蛋白质网络数据，则从文件中解析数据
//			DataProcess.readNetworkFromFile(formInfo.getFilePath(), edges);
			netList = FileUtil.readFile2List(formInfo.getFilePath());
			//对于用户提交的亚细胞位置数据，从文件中进行解析
			allSubs = FileUtil.readFile2List(locationFile);
			
		} else {
			//否则用户选择的是数据库中的蛋白质网络数据
			String dataset = formInfo.getDatasets();
			
			netList = getService(dataset).getNetworkByTaxid(taxId);
//			for (String str : netList) {
//				String[] arr = str.split("\\t");
//				edges.add(arr);
//			}
			
			//使用数据库已有的基因表达数据
			
			allSubs = locationService.getLocationByTaxid(taxId, getService(dataset).getMappingData(taxId));
			
		}
		
		//对亚细胞位置数据进行计算处理
		List<Subcellular> resList = new ArrayList<>();
		//获取符合亚细胞条件的蛋白质节点
		List<HashSet<String>> subList = new ArrayList<>();
		Map<String,String> nodePlace=new HashMap<String,String>();//节点的位置信息
		for(String location:locations){
			HashSet<String> proteins = new HashSet<>();
			
			for(String str:allSubs){
				String[] infos = str.toUpperCase().split("\t");
				for(int m=1;m<infos.length;m++){
					if (infos[m].contains(location)) {
						proteins.add(infos[0]);
						if(!nodePlace.containsKey(infos[0])){
							nodePlace.put(infos[0], location);
							
						}
					}
				}
			}
			
			subList.add(proteins);
		}
		
		//获取每个亚细胞子网中的点和边		
		for (int i = 0; i < locations.length; i++) {
			HashSet<String> proteins = subList.get(i);
			List<String> interactions = new ArrayList<>();
			for(String str:netList){
				String[] arr = str.toUpperCase().split("\t");
				if (proteins.contains(arr[0])&&proteins.contains(arr[1])) {
					interactions.add(str);
				}
			}
			
			//初始化亚细胞对象
			Subcellular subCell = new Subcellular(locations[i]);
			List<String> teNodes = getNodes(interactions);
			subCell.setNodes(teNodes);
			List<String[]> teEdges = getEdges(interactions);
			subCell.setEdges(teEdges);
			double result = 1.0 * teNodes.size();
			subCell.setReliability(result);
			
			resList.add(subCell);
		}
				
		//根据亚细胞区间的可信度值由小到大进行排序
		Collections.sort(resList, new Comparator<Subcellular>() {
			@Override
			public int compare(Subcellular o1, Subcellular o2) {
				return Double.compare(o2.getReliability(), o1.getReliability());
			}
		});
		
		
		Vector<String[]> edges = new Vector<>();	
		transformList2Vector(edges,resList.get(0).getEdges());
		DataProcess.readNode(edges, vertex);
		
		File algoFile = new File(path+"algorithms");
		algoFile.mkdir();
		String[] algorithms = formInfo.getAlgorithms().split(";");
		for (int i = 0; i < algorithms.length; i++) {
			
			String algo_name = algorithms[i].trim();
			System.out.println(algo_name+" start ...");
			try {
				
				Class<?> cl = Class.forName("cn.edu.bioinformatics.netepd.algorithm."+algo_name);
				AlgoObject algo = (AlgoObject) cl.newInstance();
				AlgoNode algoInfo = new AlgoNode();
				algo.processNode(edges, vertex, Integer.parseInt(formInfo.getFormatType()), algoInfo);
				
				DataProcess.sortVertex(vertex);
				DataProcess.writeXLS(path+"algorithms/", algo_name, vertex);
			} catch (Exception e) {
				System.out.println("RunAlgo Error!");
				e.printStackTrace();
			}
			System.out.println(algo_name+" end ...");
		}
		
		DataProcess.zipProcess(path);
		
		if (!formInfo.getEmail().endsWith("@netepd.com")) {
			System.out.println("----运行结束，发送邮件---");
			mailMessage.setTo(formInfo.getEmail());
			String urlpath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"
					+ "result?forminfo="+formInfo.getId()+"&jobid="+formInfo.getJobId();
			System.out.println(urlpath);
			String infoText = "Dear user:\n\nThe job(Job ID: " + formInfo.getJobId() + ", Job Note: "+ formInfo.getJobNote() 
			+") you submitted at "+ formInfo.getCreatedTime() +" has finished!\n" +"You can check the result from the link below:\n"+ urlpath
			+ "\n\nSincerely,\nNetEPD Administrator\n";
			mailMessage.setText(infoText);
			mailSender.send(mailMessage);
		}
		
		System.out.println("----Algorithm End!----");
		return "result";
	}
	
//-----------------------------Spatial & Temporal Dataset Process---------------------------------------------------------------------------------------	
	
	/**
	 * 根据物种ID返回可选择的数据集
	 * @param taxid
	 * @return
	 */
	@RequestMapping(value="/getSpatioTemporalDataset",method=RequestMethod.POST)
	public @ResponseBody String getSpatioTemporalDataset(@RequestParam String taxid){
		
		List<String> datasets = new ArrayList<>();
		if (taxid.equals("0")) {
			datasets.add("null");
		} else {
			
			//获取该物种亚细胞位置信息的数量
			ExpressionService temporalService = expressionService;
			int temporalDataSet = temporalService.getExpressionMapSizeByTaxid(taxid);
			
			//获取该物种亚细胞位置信息的数量
			LocationService spatialService = locationService;
			int spatialDataSet = spatialService.getLocationMapSizeByTaxid(taxid);
			
			
			//获取biogrid数据库中该物种PPI的数量
			int dsize = biogridService.getNetworkByTaxid(taxid).size();
			//System.out.println(dsize);
			if (dsize != 0 &&temporalDataSet >0 && spatialDataSet >0) {
				datasets.add("biogrid");
			}
			
			//获取dip数据库中该物种PPI的数量
			dsize = dipService.getNetworkByTaxid(taxid).size();
			//System.out.println(dsize);
			if (dsize != 0  && temporalDataSet >0 && spatialDataSet >0) {
				datasets.add("dip");
			}
			
			//获取mint数据库中该物种PPI的数量
			dsize = mintService.getNetworkByTaxid(taxid).size();
			//System.out.println(dsize);
			if (dsize != 0  && temporalDataSet >0 && spatialDataSet >0) {
				datasets.add("mint");
			}
			
			//获取mips数据库中该物种PPI的数量
			dsize = mipsService.getNetworkByTaxid(taxid).size();
			//System.out.println(dsize);
			if (dsize != 0  && temporalDataSet >0 && spatialDataSet >0) {
				datasets.add("mips");
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("datasets", datasets);
		//System.out.println(datasets.toString());
		
		return jsonObject.toString();
	}
	
	
	
	/**
	 * 用户信息提交时请求调用
	 * @param request
	 * @param files
	 * @param session
	 * @return
	 * @throws IOException
	 * @author JiashuaiZhang
	 */
	@RequestMapping(value="/spatiotemporalForm")
	public @ResponseBody String dealPata3(HttpServletRequest request,@RequestParam("files") MultipartFile []files,HttpSession session) throws IOException {
		
		//用于保存数据提交操作的结果状态
		String infoStats = "OK";
		//生成上传数据的保存文件
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload");
		String []filesPath = new String[3];
		
		//获取表单内容
		String jobId = request.getParameter("jobId");
		System.out.println("jobId:"+jobId);
		String algorithms = request.getParameter("algorithms");
		System.out.println("algorithms:"+algorithms);
		//保存到Session中
		String taxId = request.getParameter("taxid");
		session.setAttribute("taxId", taxId);
		System.out.println("taxid:"+taxId);
		//根据蛋白质网络数据自动确定是否是加权网络
		String formatType = "0";
		if (taxId.equals("0")) {
			//将上传的多个文件进行保存
			if(files!=null && files.length>0){
				for(int i=0;i<files.length;i++){
					
					String uuid = UUID.randomUUID().toString();
					String filePath = uploadPath+"/"+uuid+".txt";
					File saveFile = new File(filePath);
					
					MultipartFile file = files[i];
					filesPath [i] = filePath;
					
					file.transferTo(saveFile);
				}
			}
			
			
			Scanner scanner = new Scanner(Paths.get(filesPath[0]));
			HashSet<String> nodeSet = new HashSet<String>();
			while (scanner.hasNextLine()) {
				String[] arr = scanner.nextLine().split("\\t");
				if (arr.length == 3) {
					formatType = "1";
				}
				if (!nodeSet.contains(arr[0])) {
					nodeSet.add(arr[0]);
				}
				if (!nodeSet.contains(arr[1])) {
					nodeSet.add(arr[1]);
				}
			}
			scanner.close();
//			if (nodeSet.size()>1000) {
//				infoStats = "OVER";
//			}
			System.out.println("the size of network: "+nodeSet.size());
		}
		String datasets = request.getParameter("datasets");
		String jobNote = request.getParameter("jobNote");
		String location = request.getParameter("location");
		String email = request.getParameter("email");
		String createdTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		//存入表单信息到数据库中，返回表单的id
		FormInfo formInfo = new FormInfo(jobId, formatType, algorithms, filesPath[0], taxId, datasets, jobNote, location, email, createdTime);
		//System.out.println(formInfo);
		String id = formInfoService.addFormInfo(formInfo);
		
		//保存用户提交的表单信息的唯一标识id
		session.setAttribute("infoId", id);
		//保存用户提交的亚细胞位置信息
		session.setAttribute("locationFile", filesPath[1]);
		//保存用户提交的基因表达数据文件路径
		session.setAttribute("expressionFile", filesPath[2]);
		
		//创建用户Job的文件目录
		File jobFile = new File(uploadPath+"/"+id);
		jobFile.mkdir();
		
		//添加用户使用信息统计
//		String ip = (String) session.getAttribute("ipvalue");
//		if (!"".equals(ip) && ip != null) {
//			usageStatsService.addUsage(ip, email);
//		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", infoStats);
		jsonObject.put("infoId", id);
		jsonObject.put("createTime", createdTime);
		//System.out.println(jsonObject.toString());
		System.out.println("----Job Submitted----");
		return jsonObject.toString();
	}
	
	/**
	 * 提交信息处理完毕之后请求调用
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/spatiotemporalRunalgo")
	public String spatiotemporalRunAlgo(HttpSession session, HttpServletRequest request) {
		
		String infoId = (String) session.getAttribute("infoId");
		String taxId = (String) session.getAttribute("taxId");
		String locationFile = (String) session.getAttribute("locationFile");
		String expressionFile = (String) session.getAttribute("expressionFile");
		
		System.out.println("infoId:"+infoId);
		System.out.println("taxId:"+taxId);
		
		FormInfo formInfo = formInfoService.getById(Integer.parseInt(infoId));
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload");
		String path = uploadPath+"/"+infoId+"/";
		
		Vector<ProteinNode> vertex = new Vector<ProteinNode>();
		Vector<String[]> edges = new Vector<>();
		List<String> expList = new ArrayList<>();
		List<String> locList = new ArrayList<>();
		if (taxId.equals("0")) {
			//若用户自己上传蛋白质网络数据，则从文件中解析数据
			DataProcess.readNetworkFromFile(formInfo.getFilePath(), edges);
			//对于用户提交的基因表达数据，从文件中进行解析
			expList = FileUtil.readFile2List(expressionFile);
			//对于用户提交的亚细胞位置数据，从文件中进行解析
			locList = FileUtil.readFile2List(locationFile);
			
		} else {
			//否则用户选择的是数据库中的蛋白质网络数据
			String dataset = formInfo.getDatasets();
			
			List<String> netList = getService(dataset).getNetworkByTaxid(taxId);
			for (String str : netList) {
				String[] arr = str.split("\\t");
				edges.add(arr);
			}
			
			//使用数据已用的亚细胞位置数据
			locList = locationService.getLocationByTaxid(taxId, getService(dataset).getMappingData(taxId));
			
			//使用数据库已有的基因表达数据
			expList = expressionService.getExpressionByTaxid(taxId, getService(dataset).getMappingData(taxId));
			
		}
		
		//对基因表达数据进行计算处理
		List<DyProtein> proList = new ArrayList<>();
		for (String exp : expList) {
			String[] infos = exp.split("\\t");
			double[] arr = new double[infos.length-1];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = Double.parseDouble(infos[i + 1]);
			}
			proList.add(new DyProtein(infos[0], arr));
		}

		//计算活性阈值
		for (DyProtein pro : proList) {
			double threshold = getThreshold(pro.getExps());
			pro.setThreshold(threshold);
		}
		
		//每个亚细胞位置中对应的蛋白质节点集合
		HashMap<String, HashSet<String>> subMap = new HashMap<>();
		for (String location:locations) {
			HashSet<String> proteins = new HashSet<>();
			for (String str:locList) {
				String[] infos = str.toUpperCase().split("\t");
				for(int m=1;m<infos.length;m++){
					if (infos[m].contains(location)) {
						proteins.add(infos[0]);
					}
				}
			}
			subMap.put(location, proteins);

		}
		
		int tNum = expList.get(0).split("\t").length-1;
		DataProcess.readNode(edges, vertex);	
		
		File algoFile = new File(path+"algorithms");
		algoFile.mkdir();
		String[] algorithms = formInfo.getAlgorithms().split(";");
		for (int i = 0; i < algorithms.length; i++) {
			
			String algo_name = algorithms[i].trim();
			System.out.println(algo_name+" start ...");
			try {
				
				HashMap<String, Double> maxMap = new HashMap<>();
				//构建动态网络
				for (int t = 0; t < tNum; t++) {
					HashSet<String> set = new HashSet<>();
					for (DyProtein pro : proList) {
						if (pro.getExps()[t]>pro.getThreshold()) {
							set.add(pro.getName());
						}
					}
					
					Vector<String[]> tempralNetEdges = NetUtil.getTempralNet(set, edges);
					
					for (String location:locations) {
						HashSet<String> proteins = subMap.get(location);
						List<String []> subEdges = new ArrayList<>();
						for(String []str:tempralNetEdges){
							String arr1 = str[0].toUpperCase();
							String arr2 = str[1].toUpperCase();
							if (proteins.contains(arr1)&&proteins.contains(arr2)) {
								subEdges.add(str);
							}
						}
						
						//对构建的时空子网进行存储格式的转换
						Vector<String[]> spatioTemporalNetEdges = new Vector<>();
						transformList2Vector(spatioTemporalNetEdges,subEdges);
						
						Vector<ProteinNode>  spatioTemporalNetNodes = new Vector<>();
						DataProcess.readNode(spatioTemporalNetEdges, spatioTemporalNetNodes);
				
						Class<?> cl = Class.forName("cn.edu.bioinformatics.netepd.algorithm."+algo_name);
						AlgoObject algo = (AlgoObject) cl.newInstance();
						AlgoNode algoInfo = new AlgoNode();
						algo.processNode(spatioTemporalNetEdges, spatioTemporalNetNodes, Integer.parseInt(formInfo.getFormatType()), algoInfo);
						
						HashMap<String, Double> tMap = getTempMap(spatioTemporalNetNodes);
					
						//记录各个时刻的最大值
						for(Entry<String, Double> entry:tMap.entrySet()){
							if (maxMap.containsKey(entry.getKey())) {
								double temp = maxMap.get(entry.getKey());
								if (temp<entry.getValue()) {
									maxMap.replace(entry.getKey(), entry.getValue());
								}
							}else {
								maxMap.put(entry.getKey(), entry.getValue());
							}
						}
			
					}
				}
				transformMap2Vertex(vertex,maxMap);
				
				DataProcess.sortVertex(vertex);
				DataProcess.writeXLS(path+"algorithms/", algo_name, vertex);
			} catch (Exception e) {
				System.out.println("RunAlgo Error!");
				e.printStackTrace();
			}
			System.out.println(algo_name+" end ...");
		}
		
		DataProcess.zipProcess(path);
		
		if (!formInfo.getEmail().endsWith("@netepd.com")) {
			System.out.println("----运行结束，发送邮件---");
			mailMessage.setTo(formInfo.getEmail());
			String urlpath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"
					+ "result?forminfo="+formInfo.getId()+"&jobid="+formInfo.getJobId();
			System.out.println(urlpath);
			String infoText = "Dear user:\n\nThe job(Job ID: " + formInfo.getJobId() + ", Job Note: "+ formInfo.getJobNote() 
			+") you submitted at "+ formInfo.getCreatedTime() +" has finished!\n" +"You can check the result from the link below:\n"+ urlpath
			+ "\n\nSincerely,\nNetEPD Administrator\n";
			mailMessage.setText(infoText);
			mailSender.send(mailMessage);
		}
		
		System.out.println("----Algorithm End!----");
		return "result";
	}
	
//------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 获取对应蛋白质的活性阈值
	 * @param arr
	 * @return
	 */
	private double getThreshold(double[] arr){
		DescriptiveStatistics ds = new DescriptiveStatistics(arr);
		double mu = ds.getMean();
		double sigma = ds.getStandardDeviation();
		double threshold = mu+ 3.0*sigma*(1-1.0/(1+sigma*sigma));
		return threshold;
	}
	
	/**
	 * 根据时间子网获取中心性的值，
	 * @param proteinNodes
	 * @return HashMap<String,Double>
	 * @author JiashuaiZhang
	 */
	private HashMap<String,Double> getTempMap(Vector <ProteinNode> proteinNodes){
		HashMap <String, Double> tMap = new HashMap<>();
		for(ProteinNode proteinNode:proteinNodes){
			tMap.put(proteinNode.getName(),proteinNode.getParam());
		}
		return tMap;
	}
	
	/**
	 * 在构建时间子网过程中，将最大中心性赋值给节点
	 * @param proteinNodes
	 * @param maxMap
	 */
	private void transformMap2Vertex(Vector <ProteinNode> proteinNodes,HashMap <String,Double> maxMap){
		
		for(Entry<String, Double> entry:maxMap.entrySet()){
			for(int i=0;i<proteinNodes.size();i++){
				String proteinName = proteinNodes.get(i).getName();
				if(entry.getKey().equals(proteinName)){
					proteinNodes.get(i).setParam(entry.getValue());
				}
			}
		}
	}
	
	/**
	 * 根据网络列表获取所有节点集合
	 * @param tList
	 * @return
	 * @author JiashuaiZhang
	 */
	private List<String> getNodes(List<String> tList) {
		HashSet<String> set = new HashSet<>();
		for (String str : tList) {
			String[] arr = str.toUpperCase().split("\t");
			set.add(arr[0]);
			set.add(arr[1]);
		}
		return new ArrayList<>(set);
	}
	
	/**
	 * 根据网络列表获取所有边集合
	 * @param tList
	 * @return
	 * @author JiashuaiZhang
	 */
	private List<String[]> getEdges(List<String> tList) {
		List<String[]> list = new ArrayList<>();
		for (String str : tList) {
			String[] arr = str.toUpperCase().split("\t");
			list.add(arr);
		}
		return list;
	}
	
	/**
	 * 将结合亚细胞位置信息的PPI子网进行存储格式的转化
	 * @param vectorEdges
	 * @param listEdges
	 * @author JiashuaiZhang
	 */
	private void transformList2Vector(Vector <String[]> vectorEdges, List<String []> listEdges){
		
		for(String [] str: listEdges){
			vectorEdges.add(str);
		}
	}
}


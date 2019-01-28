package cn.edu.bioinformatics.netepd.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
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
import cn.edu.bioinformatics.netepd.bean.ProteinNode;
import cn.edu.bioinformatics.netepd.entity.FormInfo;
import cn.edu.bioinformatics.netepd.service.BiogridService;
import cn.edu.bioinformatics.netepd.service.DatasetService;
import cn.edu.bioinformatics.netepd.service.DegService;
import cn.edu.bioinformatics.netepd.service.DipService;
import cn.edu.bioinformatics.netepd.service.FormInfoService;
import cn.edu.bioinformatics.netepd.service.MintService;
import cn.edu.bioinformatics.netepd.service.MipsService;
import cn.edu.bioinformatics.netepd.service.UsageStatsService;
import cn.edu.bioinformatics.netepd.util.AlgoNode;
import cn.edu.bioinformatics.netepd.util.Assess;
import cn.edu.bioinformatics.netepd.util.DataProcess;
import cn.edu.bioinformatics.netepd.util.FileUtil;
import cn.edu.bioinformatics.netepd.util.Visual;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
public class FormInfoController {
	
	@Autowired
	private FormInfoService formInfoService;
	@Autowired
	private DegService degService;
	@Autowired
	private BiogridService biogridService;
	@Autowired
	private DipService dipService;
	@Autowired
	private MintService mintService;
	@Autowired
	private MipsService mipsService;
	@Autowired
	private UsageStatsService usageStatsService;
	@Autowired
	private JavaMailSenderImpl mailSender;
	@Autowired
	private SimpleMailMessage mailMessage;
	
	/**
	 * 根据提供的数据集名称确定所要调用的Service
	 * @param datasetName
	 * @return
	 */
	public DatasetService getService(String datasetName){
		
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
	
	/**
	 * 根据物种ID返回可选择的数据集
	 * @param taxid
	 * @return
	 */
	@RequestMapping(value="/getdataset",method=RequestMethod.POST)
	public @ResponseBody String getAvailableDataset(@RequestParam String taxid){
		
		List<String> datasets = new ArrayList<>();
		if (taxid.equals("0")) {
			datasets.add("null");
		} else {
			int dsize = biogridService.getNetworkByTaxid(taxid).size();
			//System.out.println(dsize);
			if (dsize != 0) {
				datasets.add("biogrid");
			}
			dsize = dipService.getNetworkByTaxid(taxid).size();
			//System.out.println(dsize);
			if (dsize != 0) {
				datasets.add("dip");
			}
			dsize = mintService.getNetworkByTaxid(taxid).size();
			//System.out.println(dsize);
			if (dsize != 0) {
				datasets.add("mint");
			}
			dsize = mipsService.getNetworkByTaxid(taxid).size();
			//System.out.println(dsize);
			if (dsize != 0) {
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
	 * @param file
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/forminfo")
	public @ResponseBody String dealPata(HttpServletRequest request,@RequestParam("file") MultipartFile file,HttpSession session) throws IOException {
		
		//用于保存数据提交操作的结果状态
		String infoStats = "OK";
		//生成上传数据的保存文件
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload");
		String uuid = UUID.randomUUID().toString();
		String filePath = uploadPath+"/"+uuid+".txt";
		File saveFile = new File(filePath);
		
		//获取表单内容
		String jobId = request.getParameter("jobId");
		System.out.println("jobId:"+jobId);
		String algorithms = request.getParameter("algorithms");
		//保存到Session中
		String taxId = request.getParameter("taxid");
		session.setAttribute("taxId", taxId);
		//System.out.println("taxid:"+taxId);
		//根据蛋白质网络数据自动确定是否是加权网络
		String formatType = "0";
		if (taxId.equals("0")) {
			if (file.isEmpty()) {
				String inputData = request.getParameter("inputData");
				//System.out.println("inputData:"+inputData);
				FileWriter fileWriter = new FileWriter(saveFile);
				fileWriter.write(inputData);
				fileWriter.close();
			} else {
				file.transferTo(saveFile);
			}
			Scanner scanner = new Scanner(Paths.get(filePath));
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
		FormInfo formInfo = new FormInfo(jobId, formatType, algorithms, filePath, taxId, datasets, jobNote, location, email, createdTime);
		//System.out.println(formInfo);
		String id = formInfoService.addFormInfo(formInfo);
		
		//保存用户提交的表单信息的唯一标识id
		session.setAttribute("infoId", id);
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
	@RequestMapping(value="/runalgo")
	public String runAlgo(HttpSession session, HttpServletRequest request) {
		
		String infoId = (String) session.getAttribute("infoId");
		String taxId = (String) session.getAttribute("taxId");
		
		System.out.println("infoId:"+infoId);
		System.out.println("taxId:"+taxId);
		
		FormInfo formInfo = formInfoService.getById(Integer.parseInt(infoId));
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload");
		String path = uploadPath+"/"+infoId+"/";
		
		Vector<ProteinNode> vertex = new Vector<ProteinNode>();
		Vector<String[]> edges = new Vector<>();
		if (taxId.equals("0")) {
			//若用户自己上传蛋白质网络数据，则从文件中解析数据
			DataProcess.readNetworkFromFile(formInfo.getFilePath(), edges);
		} else {
			//否则用户选择的是数据库中的蛋白质网络数据
			String dataset = formInfo.getDatasets();
			
			List<String> netList = getService(dataset).getNetworkByTaxid(taxId);
			for (String str : netList) {
				String[] arr = str.split("\\t");
				edges.add(arr);
			}
		}
		DataProcess.readNode(edges, vertex);
		
		DataProcess.sortVertex(vertex);
		File algoFile = new File(path+"algorithms");
		algoFile.mkdir();
		String[] algorithms = formInfo.getAlgorithms().split(";");
		for (int i = 0; i < algorithms.length; i++) {
			
			String algo_name = algorithms[i].trim();
			System.out.println(algo_name+" start ...");
//			if (algo_name.equals("DC")) {
//				DataProcess.writeXLS(path+"algorithms/", algo_name, vertex);
//			} else {
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
//			}
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
	
	/**
	 * 自动填充结果展示页面下拉框的用户算法
	 * @param infoId
	 * @return
	 */
	@RequestMapping(value="/getuseralgos",method=RequestMethod.GET)
	public @ResponseBody String getUserAlgos(@RequestParam String infoId) {
		
		FormInfo formInfo = formInfoService.getById(Integer.parseInt(infoId));
		String[] algorithms = formInfo.getAlgorithms().split(";");
		
		JSONObject obj = new JSONObject();
		obj.put("userAlgos", algorithms);
		
		System.out.println("----GetUserAlgos----");
		return obj.toString();
	}
	
	/**
	 * 预测算法显示下拉框的值发生改变时请求调用
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/showAlgoRes",method=RequestMethod.GET)
	public @ResponseBody String showAlgoResults(HttpServletRequest request) {
		
		String infoId = (String) request.getParameter("infoId");
		String selectAlgo = (String) request.getParameter("selectAlgo").trim();
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload");
		String algoPath = uploadPath+"/"+infoId+"/algorithms/"+selectAlgo+".xls";
		//System.out.println("AlgoPath:" + algoPath);
		
		Vector<ProteinNode> data = DataProcess.readXls(algoPath);
		JSONArray jsonData = new JSONArray();
		for (ProteinNode protein : data) {
			jsonData.add(protein);
		}
		System.out.println("----Result Check----");
		return jsonData.toString();
	}
	
	/**
	 * 当用户进行算法评估，提交信息时会处理该请求
	 */
	@RequestMapping(value="/evainfo",method=RequestMethod.POST)
	public @ResponseBody String evaInfo(HttpSession session,HttpServletRequest request,@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
		
		String infoId = request.getParameter("infoId");
		String proPath = request.getSession().getServletContext().getRealPath("/");
		String jobPath = proPath+"upload/"+infoId+"/";
		int topNum = Integer.parseInt(request.getParameter("topNum"));
		FormInfo formInfo = formInfoService.getById(Integer.parseInt(infoId));
		if (!file.isEmpty()) {
			File assessFile = new File(jobPath+"assess.txt");
			file.transferTo(assessFile);
		}
		
		//1.分情况处理蛋白质网络数据
		HashMap<String, String> mapACs;
		String taxId = (String) session.getAttribute("taxId");
		if (taxId.equals("0")) {
			//1.1处理用户提交的ppi数据
			mapACs = new HashMap<>();
			Scanner scanner = new Scanner(Paths.get(formInfo.getFilePath()));
			while(scanner.hasNextLine()){
				String[] arr = scanner.nextLine().split("\\t");
				if (arr.length >= 2) {
					if (!mapACs.containsKey(arr[0])) {
						mapACs.put(arr[0], arr[0]);
					}
					if (!mapACs.containsKey(arr[1])) {
						mapACs.put(arr[1], arr[1]);
					}
				}
			}
			scanner.close();
			
		} else {
			//1.2处理数据库数据
			mapACs = new HashMap<>();
			String dataset = formInfo.getDatasets();
			
			mapACs = getService(dataset).getMappingData(taxId);
		}
		//2.分情况进行关键蛋白质识别
		HashSet<String> mappedACs;
		if (!file.isEmpty()) {
			//2.1若使用用户提交的关键蛋白数据
			mappedACs = Assess.getEPList(mapACs, jobPath+"assess.txt");
		} else {
			//2.2若使用数据库中的关键蛋白数据
			System.out.println("mapACs size:"+mapACs.size());
			mappedACs = degService.getMappedList(mapACs);
		}
		//3.进行评估
		String[] algos = formInfo.getAlgorithms().split(";");
		String assessResult = Assess.runAssess(jobPath, topNum, algos, mappedACs);
		
		//System.out.println("AssessResult:"+assessResult);
		System.out.println("----Evaluation End----");
		return assessResult;
	}
	
	@RequestMapping(value="/visualization")
	public String networkVisualization(HttpSession session,HttpServletRequest request) {

		String infoId = (String) session.getAttribute("infoId");
		String taxId = (String) session.getAttribute("taxId");
		// 测试代码。。。
		if (infoId == null) {
			infoId = "18";
			taxId = "4932";
		}
		System.out.println("infoID:"+infoId);
		System.out.println("taxId:"+taxId);
		
		FormInfo formInfo = formInfoService.getById(Integer.parseInt(infoId));
		List<String> datasets;
		if (taxId.equals("0")) {
			//使用用户提交的网络数据
			String filePath = formInfo.getFilePath();
			datasets = DataProcess.getNetworkFromFile(filePath);
		} else {
			//使用用户选择的数据库网络数据
			datasets = new ArrayList<>();
			String networks = formInfo.getDatasets();
			
			datasets = getService(networks).getNetworkByTaxid(taxId);
		}

		request.setAttribute("net", Visual.encapsulateNet2Json(datasets));
		System.out.println("----Visualization End----");
		return "network";
	}
	
	@RequestMapping(value="/displaynetwork",method=RequestMethod.POST)
	public String showNetwork(HttpServletRequest request,@RequestParam("file") MultipartFile file,@RequestParam("inputData") String inputData){
		
		List<String> datasets;
		if (file.isEmpty()) {
			datasets = Arrays.asList(inputData.split("\r\n"));
			System.out.println("没有上传文件");
		}else {
			try {
				datasets = new ArrayList<>();
				Scanner scanner = new Scanner(file.getInputStream());
				while (scanner.hasNextLine()) {
					datasets.add(scanner.nextLine().trim());
				}
				scanner.close();
			} catch (IOException e) {
				datasets = null;
				e.printStackTrace();
			}
			
		}
		request.setAttribute("net", Visual.encapsulateNet2Json(datasets));
		System.out.println("----Show Network----");
		return "network";
	}
	//------------------------------------------------------------------------------
	
	/**
	 * 
	 * @param request
	 * @param files
	 * @return
	 * @author JiashuaiZhang
	 */
	@RequestMapping(value="/evaluation",method=RequestMethod.POST)
	public @ResponseBody String evaluateNodes(HttpServletRequest request,@RequestParam("files") MultipartFile []files, HttpSession session) throws IOException {
		
//		System.out.println("提交文件"+ files.length);
		
		//用于保存数据提交操作的结果状态
		String infoStats = "OK";
		//生成上传数据的保存文件
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload");
		String []filePath = new String [files.length];
		
		
		//保存到Session中
		//将用户上传的PPI和Gene Expression文件保存下来
		if(files!=null && files.length>0){
			for(int i=0;i<files.length;i++){
				
				String uuid = UUID.randomUUID().toString();
				
				if(files[i].getContentType().equals("text/plain")){
//					System.out.println("文件类型为"+files[i].getContentType());
					filePath[i] = uploadPath+"/"+uuid+".txt";
				}else{
					filePath[i] = uploadPath+"/"+uuid+".xls";
				}
				
//				System.out.println(filePath[i]);
				File saveFile = new File(filePath[i]);
				
				MultipartFile file = files[i];
				file.transferTo(saveFile);
				
			}
		}
		
//		if (nodeSet.size()>1000) {
//			infoStats = "OVER";
//		}
//		System.out.println("the size of network: "+nodeSet.size());
	
		//保存用户提交的表单信息的唯一标识id
		session.setAttribute("filesPath", filePath);
		//创建用户Job的文件目录
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", infoStats);
//		System.out.println(jsonObject.toString());
		System.out.println("----Files Submitted----");
		return jsonObject.toString();

	}
	
	
	/**
	 * 上传预测节点和已知节点后请求调用
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/evaluationalgo",method = RequestMethod.GET)
	public @ResponseBody String valuationRunAlgo(HttpSession session, HttpServletRequest request) throws FileNotFoundException {
		
		String []filesPath = (String []) session.getAttribute("filesPath");
		
//		String uploadPath = request.getSession().getServletContext().getRealPath("/upload");
//		String path = uploadPath+"/"+infoId+"/";
		
		List<String> essentialNodes = FileUtil.readFile2List(filesPath[1]);
//		System.out.println("已知的关键节点数为：" + essentialNodes.size());
		
		int topNum = 0;
		if(request.getParameter("topNum") == null){
			topNum = 100;
		}else{
			topNum = Integer.parseInt(request.getParameter("topNum"));
		}
		
		int i, j, Tp, Fp, Tn, Fn, margin;
		float SN = 0, SP = 0, ACC = 0, PPV = 0, NPV = 0, F = 0;
		String nodeName;
		JSONObject assessResult = new JSONObject();
		JSONArray tableDatas = new JSONArray();
		JSONObject chartDatas = new JSONObject();
		JSONArray chartName = new JSONArray();
		JSONArray xdata = new JSONArray();
		JSONArray ydatas = new JSONArray();
		
		try {
			InputStream inputStream = new FileInputStream(filesPath[0]);
			POIFSFileSystem poi= new POIFSFileSystem(inputStream);
			HSSFWorkbook workbook = new HSSFWorkbook(poi);
			int sheetSize = workbook.getNumberOfSheets();
			for(i=0; i<sheetSize; i++){
				Sheet sheet = workbook.getSheetAt(i);
				JSONObject rowDatas = new JSONObject();
				String algoName = sheet.getSheetName();
				
				rowDatas.put("name", algoName);
				chartName.add(algoName);
				JSONObject ydata = new JSONObject();
				ydata.put("name", algoName);
				ydata.put("type", "line");
				ydata.put("smooth", false);
				JSONArray epNum = new JSONArray();
				Tp = 0;
				Fp = 0;
				Tn = 0;
				Fn = 0;
				SN = 0;
				SP = 0;
				ACC = 0;
				PPV = 0;
				NPV = 0;
				int rowNum = sheet.getLastRowNum() + 1;
				topNum = topNum > rowNum ? rowNum : topNum;
				margin = topNum > 1000 ? 10 : 1;
				for (j = 0; j < rowNum; j++) {
					Row row =  sheet.getRow(j);
					nodeName =  row.getCell(0).getStringCellValue();
					if (essentialNodes.contains(nodeName)) {
						if (j <= topNum) {
							Tp++;
						} else {
							Fn++;
						}
					} else {
						if (j <= topNum) {
							Fp++;
						} else {
							Tn++;
						}
					}
					if (j < topNum && j % margin == 0) {
						//echarts中X轴的top数据
						xdata.add(j);
						//echarts中Y轴的关键蛋白数量
						epNum.add(Tp);
					}
				}
				ydata.put("data", epNum);
				ACC = (float) (Tn + Tp) / rowNum;
				SN = (float) Tp / (Tp + Fn);
				PPV = (float) Tp / (Tp + Fp);
				SP = (float) Tn / (Tn + Fp);
				NPV = (float) Tn / (Tn + Fn);
				F = 2 * SN * PPV / (SN + PPV);
				rowDatas.put("acc", ACC);
				rowDatas.put("sn", SN);
				rowDatas.put("ppv", PPV);
				rowDatas.put("sp", SP);
				rowDatas.put("npv", NPV);
				rowDatas.put("f", F);
				tableDatas.add(rowDatas);
				ydatas.add(ydata);
			}
			
			chartDatas.put("chartName", chartName);
			chartDatas.put("xdata", xdata);
			chartDatas.put("ydatas", ydatas);
			
			assessResult.put("status", "success");
			assessResult.put("tableDatas", tableDatas);
			assessResult.put("chartDatas", chartDatas);
			
			
			
		} catch (IOException fileException) {
			// TODO Auto-generated catch block
			fileException.printStackTrace();
		}
		System.out.println("-------------------Assess has been completed----------------------------");
		return assessResult.toString();
		
//		List<String> predictedNodes = FileUtil.readFile2List(filesPath[0]);
//		System.out.println("被预测的节点数为：" + predictedNodes.size());
//		List<String> essentialNodes = FileUtil.readFile2List(filesPath[1]);
//		System.out.println("已知的关键节点数为：" + essentialNodes.size());
		
//		int topNum = 100;
//		String [] algos = {"own"};
//		int i, j, Tp, Fp, Tn, Fn, margin;
//		float SN = 0, SP = 0, ACC = 0, PPV = 0, NPV = 0, F = 0;
//		String nodeName;
//		JSONObject assessResult = new JSONObject();
//		JSONArray tableDatas = new JSONArray();
//		JSONObject chartDatas = new JSONObject();
//		JSONArray chartName = new JSONArray();
//		JSONArray xdata = new JSONArray();
//		JSONArray ydatas = new JSONArray();
		
//		for (i = 0; i < algos.length; i++) {
//			JSONObject rowDatas = new JSONObject();
//			String algoName = algos[i].trim();
//			rowDatas.put("name", algoName);
//			chartName.add(algoName);
//			JSONObject ydata = new JSONObject();
//			ydata.put("name", algoName);
//			ydata.put("type", "line");
//			ydata.put("smooth", false);
//			JSONArray epNum = new JSONArray();
//			Tp = 0;
//			Fp = 0;
//			Tn = 0;
//			Fn = 0;
//			SN = 0;
//			SP = 0;
//			ACC = 0;
//			PPV = 0;
//			NPV = 0;
//			F = 0;
//			try {
//				
//				int rowNum = predictedNodes.size();
//				topNum = topNum > rowNum ? rowNum : topNum;
//				margin = topNum > 1000 ? 10 : 1;
//				for (j = 0; j < rowNum; j++) {
//					nodeName =  predictedNodes.get(j);
//					if (essentialNodes.contains(nodeName)) {
//						if (j <= topNum) {
//							Tp++;
//						} else {
//							Fn++;
//						}
//					} else {
//						if (j <= topNum) {
//							Fp++;
//						} else {
//							Tn++;
//						}
//					}
//					if (j < topNum && j % margin == 0) {
//						//echarts中X轴的top数据
//						xdata.add(j);
//						//echarts中Y轴的关键蛋白数量
//						epNum.add(Tp);
//					}
//				}
//				ydata.put("data", epNum);
//				ACC = (float) (Tn + Tp) / rowNum;
//				SN = (float) Tp / (Tp + Fn);
//				PPV = (float) Tp / (Tp + Fp);
//				SP = (float) Tn / (Tn + Fp);
//				NPV = (float) Tn / (Tn + Fn);
//				F = 2 * SN * PPV / (SN + PPV);
//				rowDatas.put("acc", ACC);
//				rowDatas.put("sn", SN);
//				rowDatas.put("ppv", PPV);
//				rowDatas.put("sp", SP);
//				rowDatas.put("npv", NPV);
//				rowDatas.put("f", F);
//			} catch (Exception e) {
//				System.out.println("RunAssess Error!");
//				e.printStackTrace();
//			}
//			//xydata.addSeries(series);
//			tableDatas.add(rowDatas);
//			ydatas.add(ydata);
//		}
//		
//		System.out.println("ACC\tSN\tSP\tNPV\tPPV\tF\tpv");
//		System.out.println(ACC + "\t" + SN+ "\t" +SP+ "\t" +NPV+ "\t" +PPV+ "\t" +F+ "\t" +NPV);
//		
//		chartDatas.put("chartName", chartName);
//		chartDatas.put("xdata", xdata);
//		chartDatas.put("ydatas", ydatas);
//		
//		assessResult.put("status", "success");
//		assessResult.put("tableDatas", tableDatas);
//		assessResult.put("chartDatas", chartDatas);
		
//		System.out.println("----Assess----");
//		return assessResult.toString();
		
	}
	
	
	
	
	//---------------------------------------------------------------------------------------
	@RequestMapping(value="/decodepng",method=RequestMethod.POST)
	public @ResponseBody String decodePng(HttpSession session,HttpServletResponse response,@RequestParam("png") String png){
		
		System.out.println("----DecodePNG----");
		int index = png.indexOf(",");
		png = png.substring((index+1));
		Base64 base64 = new Base64();
		byte[] decoderBytes = base64.decode(png);
		session.setAttribute("png", decoderBytes);
		JSONObject json = new JSONObject();
		json.put("status", "ok");
		System.out.println(json.toString());
		return json.toString();
	}
	
	@RequestMapping(value="/downloadpng")
	public void downloadPng(HttpSession session, HttpServletResponse response){
		
		System.out.println("----DownloadPNG----");
		byte[] decoderBytes = (byte[]) session.getAttribute("png");
		session.removeAttribute("png");
		response.setContentType("image/png");
		response.addHeader("Content-Disposition", "attachment; filename=net.png");
		OutputStream os;
		try {
			os = response.getOutputStream();
			os.write(decoderBytes);
			os.close();
		} catch (IOException e) {
			System.out.println("DownloadPNG Error!");
			e.printStackTrace();
		}
		
	}
	
}

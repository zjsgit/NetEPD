package cn.edu.bioinformatics.netepd.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 评估方法类，包含了SN、SP、PPV、NPV、ACC、F和折线图在内的7种评估方法。
 * @author lwk
 *
 */
public class Assess {
	
	/**
	 * 当使用用户提交的关键蛋白数据集时调用该方法，
	 * 返回所有InteractorAC中属于关键蛋白质的InteractorID数据列表。
	 * @param mapACs
	 * @param assessPath
	 */
	public static HashSet<String> getEPList(HashMap<String, String> mapACs, String assessPath){
		HashSet<String> epSet = new HashSet<>();
		try {
			Scanner scanner = new Scanner(Paths.get(assessPath));
			while(scanner.hasNextLine()){
				String epAC = scanner.nextLine().trim();
				if (mapACs.containsKey(epAC)) {
					String epIDs = mapACs.get(epAC);
					if (epIDs.contains(";")) {
						String[] arr = epIDs.split(";");
						for (String str : arr) {
							if (!epSet.contains(str)) {
								epSet.add(str);
							}
						}
					}else if (!epSet.contains(epIDs)) {
						epSet.add(epIDs);
					}
				}
			}
			scanner.close();
		} catch (IOException e) {
			System.out.println("GetEPList Error!");
			e.printStackTrace();
		}
		
		return epSet;
	}
	
	/**
	 * 该方法将通过评估方法得到评估结果tableDatas，并得到折线图数据chartDatas；
	 * 通过JSON将两个数据和处理的结果状态status进行封装，传递到前台展示。
	 * 评估方法的计算过程：
	 * 其中，TP表示计算认为的关键蛋白质的确是关键蛋白质的数目，
	 * FP表示计算认为的关键蛋白质并非是关键蛋白质的数目，
	 * TN表示计算认为的非关键蛋白质的确是非关键蛋白质的数目，
	 * FN表示计算认为的非关键蛋白质是关键蛋白质的数目
	 */
	public static String runAssess(String jobPath, int topNum,  String[] algos, HashSet<String> mappedACs) {
		
		InputStream inputStream ;
		int i, j, Tp, Fp, Tn, Fn, margin;
		float SN, SP, ACC, PPV, NPV, F;
		String nodeName;
		JSONObject assessResult = new JSONObject();
		JSONArray tableDatas = new JSONArray();
		JSONObject chartDatas = new JSONObject();
		JSONArray chartName = new JSONArray();
		JSONArray xdata = new JSONArray();
		JSONArray ydatas = new JSONArray();
		for (i = 0; i < algos.length; i++) {
			JSONObject rowDatas = new JSONObject();
			String algoName = algos[i].trim();
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
			F = 0;
			try {
				inputStream = new FileInputStream(jobPath + "algorithms/" + algoName + ".xls");
				POIFSFileSystem poi = new POIFSFileSystem(inputStream);
				HSSFWorkbook workbook = new HSSFWorkbook(poi);
				Sheet sheet = workbook.getSheetAt(0);
				int rowNum = sheet.getLastRowNum() + 1;
				topNum = topNum > rowNum ? rowNum : topNum;
				margin = topNum > 1000 ? 10 : 1;
				for (j = 1; j < rowNum; j++) {
					Row row = sheet.getRow(j);
					nodeName = row.getCell(1).getStringCellValue();
					if (mappedACs.contains(nodeName)) {
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
				inputStream.close();
			} catch (Exception e) {
				System.out.println("RunAssess Error!");
				e.printStackTrace();
			}
			//xydata.addSeries(series);
			tableDatas.add(rowDatas);
			ydatas.add(ydata);
		}
		
		chartDatas.put("chartName", chartName);
		chartDatas.put("xdata", xdata);
		chartDatas.put("ydatas", ydatas);
		
		assessResult.put("status", "success");
		assessResult.put("tableDatas", tableDatas);
		assessResult.put("chartDatas", chartDatas);
		
		System.out.println("----Assess----");
		return assessResult.toString();
	}
	
	/**
	 * 该方法将通过评估方法得到评估结果tableDatas，并得到折线图数据chartDatas；
	 * 通过JSON将两个数据和处理的结果状态status进行封装，传递到前台展示。
	 */
	public static String runAssess(File file, String path, int topNum, String[] algo) throws IOException {
		
		//读取关键蛋白文件中的数据保存到哈希表中
		//====后续要改写成从数据库中读取对应物种的关键蛋白数据====
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"UTF-8");
		BufferedReader br = new BufferedReader(isr);
		String rowStr = null;
		while((rowStr = br.readLine()) != null) {
			String[] arr = rowStr.split("\\t");
			hashMap.put(arr[0].trim(), Integer.parseInt(arr[1].trim()));
		}
		br.close();
		
		/*
		 * 评估方法的计算过程：
		 * 其中，TP表示计算认为的关键蛋白质的确是关键蛋白质的数目，
		 * FP表示计算认为的关键蛋白质并非是关键蛋白质的数目，
		 * TN表示计算认为的非关键蛋白质的确是非关键蛋白质的数目，
		 * FN表示计算认为的非关键蛋白质是关键蛋白质的数目
		 */
		InputStream inputStream ;
		int i, j, Tp, Fp, Tn, Fn, margin;
		float SN, SP, ACC, PPV, NPV, F;
		String nodeName;
		JSONObject assessResult = new JSONObject();
		JSONArray tableDatas = new JSONArray();
		JSONObject chartDatas = new JSONObject();
		JSONArray chartName = new JSONArray();
		JSONArray xdata = new JSONArray();
		JSONArray ydatas = new JSONArray();
		for (i = 0; i < algo.length; i++) {
			JSONObject rowDatas = new JSONObject();
			String algoName = algo[i].trim().toUpperCase();
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
			F = 0;
			inputStream = new FileInputStream(path+"algorithms/"+algoName+".xls");
			POIFSFileSystem poi = new POIFSFileSystem(inputStream);
			HSSFWorkbook workbook = new HSSFWorkbook(poi);
			Sheet sheet = workbook.getSheetAt(0);
			int rowNum = sheet.getLastRowNum()+1;
			topNum = topNum>rowNum?rowNum:topNum;
			margin = topNum>1000?10:1;
			for (j = 1; j < rowNum; j++) {
				Row row = sheet.getRow(j);
				nodeName = row.getCell(1).getStringCellValue();
				if (hashMap.containsKey(nodeName) && hashMap.get(nodeName) == 1) {
					if (j <= topNum) {
						Tp++;
					} else {
						Fn++;
					}
				} else if (hashMap.containsKey(nodeName) && hashMap.get(nodeName) == 0) {
					if (j <= topNum) {
						Fp++;
					} else {
						Tn++;
					}
				}
				if (j<topNum && j%margin == 0) {
					//echarts中X轴的top数据
					xdata.add(j);
					//echarts中Y轴的关键蛋白数量
					epNum.add(Tp);
				}
			}
			ydata.put("data", epNum);
			
			ACC = (float) (Tn+Tp)/rowNum;
			SN = (float) Tp/(Tp+Fn);
			PPV = (float) Tp/(Tp+Fp);
			SP = (float) Tn/(Tn+Fp);
			NPV = (float) Tn/(Tn+Fn);
			F = 2*SN*PPV/(SN+PPV);
			rowDatas.put("acc", ACC);
			rowDatas.put("sn", SN);
			rowDatas.put("ppv", PPV);
			rowDatas.put("sp", SP);
			rowDatas.put("npv", NPV);
			rowDatas.put("f", F);
			inputStream.close();
			
			//xydata.addSeries(series);
			tableDatas.add(rowDatas);
			ydatas.add(ydata);
		}
		
		chartDatas.put("chartName", chartName);
		chartDatas.put("xdata", xdata);
		chartDatas.put("ydatas", ydatas);
		
		assessResult.put("status", "success");
		assessResult.put("tableDatas", tableDatas);
		assessResult.put("chartDatas", chartDatas);
		
		System.out.println("----Assess----");
		return assessResult.toString();
	}

}

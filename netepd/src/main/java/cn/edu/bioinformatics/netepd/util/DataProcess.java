package cn.edu.bioinformatics.netepd.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import cn.edu.bioinformatics.netepd.bean.ProteinNode;

public class DataProcess {
	
	/**
	 * 从文件中读取蛋白质网络数据
	 * @param filePath
	 * @return
	 */
	public static void readNetworkFromFile(String filePath, Vector<String[]> edges){
		
		try {
			Scanner scanner = new Scanner(Paths.get(filePath));
			while(scanner.hasNextLine()){
				String[] arr = scanner.nextLine().split("\\t");
				edges.add(arr);
			}
			scanner.close();
		} catch (IOException e) {
			System.out.println("ReadNetworkFromFile Error!\n"+e.toString());
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 度中心性(degree centrality)的节点中心性测度方法
	 * 该段代码后期可以考虑将Vector<ProteinNode>改写成用HashMap进行存储，从而节省效率
	 * @param edges
	 * @param vertex
	 */
	public static void readNode(Vector<String[]> edges, Vector<ProteinNode> vertex) {
		
//		boolean f_flag, s_flag;
//		for (String[] arr : edges) {
//			f_flag = true;
//			s_flag = true;
//			String fis = arr[0];
//			String sec = arr[1];
//			if (vertex != null) {
//				for (int i = 0; i < vertex.size(); i++) {
//					if (vertex.get(i).getName().equals(fis)) {
//						vertex.get(i).setParam(vertex.get(i).getParam() + 1);
//						f_flag = false;
//						break;
//					}
//				}
//				for (int i = 0; i < vertex.size(); i++) {
//					if (vertex.get(i).getName().equals(sec)) {
//						vertex.get(i).setParam(vertex.get(i).getParam() + 1);
//						s_flag = false;
//						break;
//					}
//				}
//				if (f_flag) {
//					vertex.add(new ProteinNode(fis, 1));
//				}
//				if (s_flag) {
//					vertex.add(new ProteinNode(sec, 1));
//				}
//			}
//		}
		
		
		HashSet<String> set = new HashSet<>();
		for (String str[] : edges) {
			set.add(str[0]);
			set.add(str[1]);
		}
		
		for(String protein: set){
			ProteinNode proteinNode = new ProteinNode();
			proteinNode.setName(protein);
			proteinNode.setParam(0);
			vertex.add(proteinNode);
		}
		
	}
	
	
	/**
	 * 根据关键节点识别方法计算出来的每个节点的属性值进行排序，由大到小排列
	 * @param vertex
	 */
	public static void sortVertex(Vector<ProteinNode> vertex) {
		
		Collections.sort(vertex, new Comparator<ProteinNode>() {

			@Override
			public int compare(ProteinNode o1, ProteinNode o2) {
				/*
				 * 会报Comparison method violates its general contract异常信息
				if (o1.getParam() > o2.getParam()) {
					return -1;
				} else if (o1.getParam() == o2.getParam()) {
					return 0;
				} else {
					return 1;
				}*/
				return Double.compare(o2.getParam(), o1.getParam());
			}
		});
		
	}
	/**
	 * 将蛋白质节点信息保存到Excel文件中，文件名为算法的名称
	 * @param path
	 * @param algoName
	 * @param vertex
	 */
	public static void writeXLS(String path, String algoName,Vector<ProteinNode> vertex) {
		
		Workbook book = new HSSFWorkbook();
		CellStyle style = book.createCellStyle();
		Font font = book.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);
		Sheet sheet = book.createSheet(algoName);
		Row row = sheet.createRow(0);
		row.setRowStyle(style);
		row.createCell(0).setCellValue("Ranking");
		row.createCell(1).setCellValue("Name");
		row.createCell(2).setCellValue("Parameter");
		for (int i = 0; i < vertex.size(); i++) {
			ProteinNode node = vertex.get(i);
			row = sheet.createRow(i+1);
			row.createCell(0).setCellValue(i+1);
			row.createCell(1).setCellValue(node.getName());
			row.createCell(2).setCellValue(node.getParam());
		}
		try {
			File file = new File(path+algoName+".xls");
			//System.out.println(file.getPath());
			FileOutputStream output = new FileOutputStream(file);
			book.write(output);
			output.flush();
			output.close();
		} catch (Exception e) {
			System.out.println("WriteXLS Error!");
			e.printStackTrace();
		}
	}
	
	/**
	 * 将关键蛋白预测方法计算出来的结果文件进行打包，以供用户下载
	 * @param path
	 */
	public static void zipProcess(String path) {
		try {
			ZipOutputStream output = new ZipOutputStream(new FileOutputStream(path + "result.zip"));
			File file = new File(path + "algorithms");
			File[] files = file.listFiles();
			for (File f : files) {
				InputStream in = new FileInputStream(f);
				ZipEntry en = new ZipEntry("algorithms"+"/"+f.getName());
				en.setSize(f.length());
				output.putNextEntry(en);
				
				int len = 0;
				byte[] buffer = new byte[1024];
				while(-1 != (len = in.read(buffer))) {
					output.write(buffer, 0, len);
				}
				in.close();
			}
			output.flush();
			output.close();
		} catch (Exception e) {
			System.out.println("ZipProcess Error!");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 获取Excel中保存的算法计算结果
	 * @param path
	 * @return
	 */
	public static Vector<ProteinNode> readXls(String path) {
		
		InputStream inputStream;
		Vector<ProteinNode> data = new Vector<ProteinNode>();
		try {
			inputStream = new FileInputStream(path);
			POIFSFileSystem poi = new POIFSFileSystem(inputStream);
			HSSFWorkbook workbook = new HSSFWorkbook(poi);
			Sheet sheet = workbook.getSheetAt(0);
			int rowNum = sheet.getLastRowNum()+1;
			for (int i = 1; i < rowNum; i++) {
				Row row = sheet.getRow(i);
				ProteinNode node = new ProteinNode();
				node.setName(row.getCell(1).getStringCellValue());
				node.setParam(row.getCell(2).getNumericCellValue());
				node.setRanking(i);
				
				data.add(node);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * 网络可视化时调用，用来将用户提交数据解析成字符串列表返回
	 * @param filePath
	 * @return
	 */
	public static List<String> getNetworkFromFile(String filePath) {
		
		try {
			List<String> network = new ArrayList<>();
			File file = new File(filePath);
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String rowStr = null;
			//int countRow = 0;
			while ((rowStr = br.readLine()) != null) {
				network.add(rowStr.trim());
				//countRow++;
			}
			br.close();
			//System.out.println("getNetworkFromFile->countRow:"+countRow);
			return network;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}

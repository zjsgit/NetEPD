package cn.edu.bioinformatics.netepd.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @author JiashuaiZhang
 *
 */
public class StrUtil {

	/**
	 * ���ַ����ж�ȡ���ݵ��б��У� �б��е�һ��Ԫ�ر�ʾһ������
	 * 
	 * @param string
	 * @return list
	 */
	public static List<String> str2List(String str) {
		
		List<String> list = new ArrayList<>();
		
		String []strArray=str.split("\n");
		for(String strLine:strArray){
			list.add(strLine.replaceAll("\r",""));
		}
		return list;
	}

	/**
	 * ���ַ����ж�ȡ���ݵ�Hashset������
	 * 
	 * @param string
	 * @return
	 */
	public static HashSet<String> str2Set(String str) {
		HashSet<String> set = new HashSet<>();
		
		String []strArray=str.split("\n");
		for(String strLine:strArray){
			set.add(strLine);
		}
		
		return set;
	}

	public static HashMap<String, Double> str2Map(String str){
		HashMap<String, Double> map = new HashMap<>();
		
		String []strArray=str.split("\n");
		for(String strLine:strArray){
            String[] arr = strLine.split("\t|\r");
            double val = Double.parseDouble(arr[1]);
            map.put(arr[0],val);
        }
	
		return map;
	}

	/**
	 * ���б��е�����д�뵽�����ļ���
	 * 
	 * @param list
	 * @param path
	 */
	public static String list2String(List<String> list) {
		
		String str="";
		
		for (String strLine : list) {
			str+=strLine+"\n";
		}
		
		return str;
			
	}

	public static void write2File(HashSet<String> set, String path) {
		
		File file = new File(path);
        if (file.exists()) {
            System.out.println("�ļ��Ѵ���");
        } else {
            try {
                File fileParent = file.getParentFile();
                if (fileParent != null) {
                    if (!fileParent.exists()) {
                        fileParent.mkdirs();
                    }
                }
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
		try {
			PrintWriter writer = new PrintWriter(path);
			for (String str : set) {
				writer.println(str);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

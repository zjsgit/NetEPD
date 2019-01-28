package cn.edu.bioinformatics.netepd.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;
import java.util.Map.Entry;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;

/**
 * 文件操作
 * 
 * @author lwk
 * @version Mar 21, 2017 10:01:18 AM
 */
public class FileUtil {

	/**
	 * 从本地文件中读取数据到列表中， 列表中的一个元素表示一行数据
	 * 
	 * @param path
	 * @return list
	 */
	public static List<String> readFile2List(String path) {
		List<String> list = new ArrayList<>();
		try {
			Scanner scanner = new Scanner(Paths.get(path));
			while (scanner.hasNextLine()) {
				list.add(scanner.nextLine());
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	
	
	
	/**
	 * 从本地文件中读取数据到Hashset集合中
	 * 
	 * @param path
	 * @return
	 */
	public static HashSet<String> readFile2Set(String path) {
		HashSet<String> set = new HashSet<>();
		try {
			Scanner scanner = new Scanner(Paths.get(path));
			while (scanner.hasNextLine()) {
				String str = scanner.nextLine().toUpperCase();
				boolean flag = set.add(str);
				if (!flag) {
					//System.out.println(str);
				}
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return set;
	}

	public static HashMap<String, Double> readFile2Map(String path){
		HashMap<String, Double> map = new HashMap<>();

		try {
			Scanner scanner = new Scanner(Paths.get(path));
			while (scanner.hasNextLine()){
                String[] arr = scanner.nextLine().split("\t");
                double val = Double.parseDouble(arr[1]);
                map.put(arr[0],val);
            }
		} catch (IOException e) {
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * 将列表中的数据写入到本地文件中
	 * 
	 * @param list
	 * @param path
	 */
	public static void write2File(List<String> list, String path) {
		
		File file = new File(path);
        if (file.exists()) {
        	System.out.println("文件已存在");
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
			for (String str : list) {
				writer.println(str);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void write2File(HashSet<String> set, String path) {
		
		File file = new File(path);
        if (file.exists()) {
        	System.out.println("文件已存在");
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
	
	
	
	public static void writeMap2File(Map<String,Double> map,String path){
		
		File file = new File(path);
        if (file.exists()) {
            System.out.println("文件已存在");
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
			for(Entry<String, Double> entry : map.entrySet()){
				writer.println(entry.getKey());
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void writeMap2File(HashMap<String,String> map,String path){
		
		File file = new File(path);
        if (file.exists()) {
            System.out.println("文件已存在");
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
			for(Entry<String, String> entry :  map.entrySet()){
				writer.println(entry.getKey()+"\t"+entry.getValue());
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void writeList2File(List<Entry<String, Double>> list,String path){
		
		File file = new File(path);
        if (file.exists()) {
            System.out.println("文件已存在");
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
			for (Entry<String, Double> entry: list) {
				writer.println(entry.getKey());
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}

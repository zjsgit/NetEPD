package cn.edu.bioinformatics.netepd.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

/**
 * 网络操作
 * @author lwk
 * @version Mar 27, 2017 4:48:54 PM
 */
public class NetUtil {
	
	/**
	 * 对静态网络筛选活性蛋白质间的相互作用网络
	 * @param actives 活性蛋白质集合
	 * @param net 原始蛋白质相互作用网络
	 * @return
	 */
	public static Vector<String[]> getTempralNet(HashSet<String> actives, Vector<String []> net){
		Vector<String []> list = new Vector<>();
		for (String[] edge : net) {
			if (actives.contains(edge[0]) && actives.contains(edge[1])) {
				list.add(edge);
			}
		}
		return list;
	}
	
	
	
	
	
	
	/**
	 * 从网络文件中获取所有节点
	 * @param net
	 * @return
	 */
	public static HashSet<String> getNodes4Net(List<String> net){
		HashSet<String> set = new HashSet<>();
		for(String str:net){
			String[] arr = str.split("\t|\r");
			set.add(arr[0]);
			set.add(arr[1]);
		}
		return set;
	}
	
	public static int[] getNodeEege4Net(List<String> net){
		int []netNodeEdge = new int[2];
		netNodeEdge[0]=net.size();
		HashSet<String> set = new HashSet<>();
		for(String str:net){
			String[] arr = str.split("\t|\r");
			set.add(arr[0]);
			set.add(arr[1]);
		}
		netNodeEdge[1]=set.size();
		return netNodeEdge;
	}
	
	/**
	 * 将网络中自循环的边去掉
	 * @param net
	 * @return after filter net
	 */
	public static List<String> filterLoops(List<String> net){
		
		List <String> newNet=new ArrayList<String>();
		
		int []check=new int [net.size()];
		for(int i=0;i<net.size();i++){
			String[] arr1 = net.get(i).split("\t|\r");
			for(int j=i+1;j<net.size();j++){
				if(check[j]==0){
					String[] arr2 = net.get(j).split("\t|\r");
					
					if(arr1[0].equals(arr2[1])&&arr1[1].equals(arr2[0])){
						check[j]=1;
						
					}
				}//end if
			}
		}
		
		for(int i=0;i<check.length;i++){
			if(check[i]==0){
				newNet.add(net.get(i));
			}
			
		}
		
		
		
		
		return newNet;
	}
	
	/**
	 * 将网络中重复的边去掉
	 * @param net
	 * @return after filter net
	 */
	public static List<String> filterOverlap(List<String> net){
		List <String> newNet=new ArrayList<String>();
		
		int []check=new int [net.size()];
		for(int i=0;i<net.size();i++){
			String[] arr1 = net.get(i).split("\t|\r");
			for(int j=i+1;j<net.size();j++){
				if(check[j]==0){
					String[] arr2 = net.get(j).split("\t|\r");
					if(arr1[0].equals(arr2[0])&&arr1[1].equals(arr2[1])){
						check[j]=1;
						
					}
				}//end if
			}
		}
		
		for(int i=0;i<check.length;i++){
			if(check[i]==0){
				newNet.add(net.get(i));
			}
			
		}
		
		return newNet;
	}
	
	/**
	 * 将网络中重复的边和自循环的边都去掉
	 * @param net
	 * @return after filter net
	 */
	public static List<String> filterNet(List<String> net){
		List <String> newNet=new ArrayList<String>();
		
		int []check=new int [net.size()];
		for(int i=0;i<net.size();i++){
			String[] arr1 = net.get(i).split("\t|\r");
			for(int j=i+1;j<net.size();j++){
				if(check[j]==0){
					String[] arr2 = net.get(j).split("\t|\r");
					if(arr1[0].equals(arr2[0])&&arr1[1].equals(arr2[1])){
						check[j]=1;//标记重复的边
					}
					if(arr1[0].equals(arr2[1])&&arr1[1].equals(arr2[0])){
						check[j]=1;	//标记自循环的边
					}
				}//end if
			}
		}
		
		for(int i=0;i<check.length;i++){
			if(check[i]==0){
				newNet.add(net.get(i));
			}
		}
		
		return newNet;
	}
	
}

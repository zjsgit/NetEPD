package cn.edu.bioinformatics.netepd.algorithm;

import java.util.Vector;

import cn.edu.bioinformatics.netepd.bean.ProteinNode;
import cn.edu.bioinformatics.netepd.util.AlgoNode;

public abstract class AlgoObject{
	
	/**
	 * make no weight matrix;
	 * @param vertex
	 * @param edges
	 * @return
	 */
	public int[][] makeMatrix(Vector<ProteinNode> vertex, Vector<String[]> edges){
		
		int len = vertex.size();
		int eLen = edges.size();
		int[][] data = new int[len][len];
		String[] edge;
		int m,n;
		try {
			for (m = 0; m < len; m++) {
				for (n = 0; n < len; n++) {
					data[m][n] = 0;
				}
			}
			for (int i = 0; i < eLen; i++) {
				edge = edges.get(i);
				for (m = 0; m < len; m++) {
					if (vertex.get(m).getName().equals(edge[0])) {
						break;
					}
				}
				for (n = 0; n < len; n++) {
					if (vertex.get(n).getName().equals(edge[1])) {
						break;
					}
				}
				if (n<len) {
					data[n][m] = 1;
					data[m][n] = 1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	/**
	 * make the weight matrix;
	 * @param vertex
	 * @param edges
	 * @return
	 */
	public int[][] makeWeiMatrix(Vector<ProteinNode> vertex, Vector<String[]> edges){
		
		int len = vertex.size();
		int eLen = edges.size();
		int[][] data = new int[len][len];
		String[] edge;
		int m,n;
		for (m = 0; m < len; m++) {
			for (n = 0; n < len; n++) {
				data[m][n] = 0;
			}
		}
		for (int i = 0; i < eLen; i++) {
			edge = edges.get(i);
			for (m = 0; m < len; m++) {
				if (vertex.get(m).getName().equals(edge[0])) {
					break;
				}
			}
			for (n = 0; n < len; n++) {
				if (vertex.get(n).getName().equals(edge[1])) {
					break;
				}
			}
			if (n<len) {
				data[n][m] = Integer.parseInt(edge[2]);
				data[m][n] = Integer.parseInt(edge[2]);
			}
		}
		
		return data;
	}
	
	public void processNode(Vector<String[]> edges, Vector<ProteinNode> vertex, int param, AlgoNode algoInfo){}
	
}

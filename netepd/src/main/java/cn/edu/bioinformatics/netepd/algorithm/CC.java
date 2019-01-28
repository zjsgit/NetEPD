package cn.edu.bioinformatics.netepd.algorithm;

import java.util.Vector;

import cn.edu.bioinformatics.netepd.bean.ProteinNode;
import cn.edu.bioinformatics.netepd.util.AlgoNode;

public class CC extends AlgoObject {

	@Override
	public void processNode(Vector<String[]> edges, Vector<ProteinNode> vertex, int param, AlgoNode algoInfo) {
		switch (param) {
		case 0:
			if (algoInfo.getAdj_matrix()==null) {
				algoInfo.setAdj_matrix(makeMatrix(vertex,edges));
			}
			figure(algoInfo.getAdj_matrix(),vertex);
			break;
		case 1:
			if (algoInfo.getAdj_matrix()==null) {
				algoInfo.setAdj_matrix(makeWeiMatrix(vertex, edges));
			}
			figure(algoInfo.getAdj_matrix(),vertex);
			break;
		default:
			break;
		}
	}
	
	/**
	 * Calculate by essential protein prediction algorithms;
	 * @param data
	 * @param vertex
	 */
	private void figure(int[][] data, Vector<ProteinNode> vertex) {
		
		//将不直接相连的两个节点的距离设置为最大值
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				if (data[i][j] == 0) {
					data[i][j] = Integer.MAX_VALUE;
				}
				
			}
		}
		
		int k=0, i, j, m;
		double score;
		int min;
		boolean flag;
		int len = vertex.size();
		int[] dist = new int[len];
		int[] set = new int[len];
		for (i = 0; i < len; i++) {
			for (j = 0; j < len; j++) {
				dist[j] = data[i][j];
				set[j] = 0;
			}
			set[i] = 1;
			for (m = 0; m < len; m++) {
				flag = false;
				min = Integer.MAX_VALUE;
				k = 0;
				for (j = 0; j < len; j++) {
					if (set[j] == 0 && dist[j] != Integer.MAX_VALUE && dist[j] < min) {
						min = dist[j];
						k = j;
						flag = true;
					}
				}
				if (flag) {
					set[k] = 1;
					for (j = 0; j < len; j++) {
						if (data[k][j] != Integer.MAX_VALUE && set[j] == 0 && dist[k]+data[k][j] < dist[j]) {
							dist[j] = dist[k] + data[k][j];
						}
					}
				}else {
					break;
				}
			}
			score = 0;
			for(j = 0; j < len; j++) {
				if (j != i) {
					if (dist[j] == Integer.MAX_VALUE) {
						score += len;
					}else {
						score += dist[j];
					}
				}
			}
			vertex.get(i).setParam((double)(len-1)/score);
		}
		
	}

}

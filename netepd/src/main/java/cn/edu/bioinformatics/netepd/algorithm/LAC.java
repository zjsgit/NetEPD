package cn.edu.bioinformatics.netepd.algorithm;

import java.util.Vector;

import cn.edu.bioinformatics.netepd.bean.ProteinNode;
import cn.edu.bioinformatics.netepd.util.AlgoNode;

public class LAC extends AlgoObject {

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
		
		int i, j, k, num;
		double score;
		for (i = 0; i < data.length; i++) {
			score = 0;
			num = 0;
			for (j = 0; j < data.length; j++) {
				if (data[i][j] != Integer.MAX_VALUE) {
					num++;
					for (k = 0; k < data.length; k++) {
						if (data[j][k] != Integer.MAX_VALUE && data[i][k] != Integer.MAX_VALUE) {
							score++;
						}
					}
				}
			}
			vertex.get(i).setParam(score/num);
		}
		
	}

}

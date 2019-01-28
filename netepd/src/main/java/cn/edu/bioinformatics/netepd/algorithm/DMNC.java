package cn.edu.bioinformatics.netepd.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import cn.edu.bioinformatics.netepd.bean.ProteinNode;
import cn.edu.bioinformatics.netepd.util.AlgoNode;
import cn.edu.bioinformatics.netepd.util.ConnectedCompent;

public class DMNC extends AlgoObject {

	@Override
	public void processNode(Vector<String[]> edges, Vector<ProteinNode> vertex, int param, AlgoNode algoNode) {
		
		switch (param) {
		case 0:
			algoNode.setAdj_matrix(makeMatrix(vertex, edges));
			figure(algoNode.getAdj_matrix(), vertex);
			break;
		case 1:
			algoNode.setAdj_matrix(makeWeiMatrix(vertex, edges));
			figure(algoNode.getAdj_matrix(), vertex);
			break;
		default:
			break;
		}
	}

	private void figure(int[][] adjMatrix, Vector<ProteinNode> vertex) {

		for (int i = 0; i < vertex.size(); i++) {

			// 节点i的邻居节点
			List<Integer> neighbors = new ArrayList<>();
			for (int j = 0; j < vertex.size(); j++) {
				if (adjMatrix[i][j] != 0) {
					// neighbors.put(j, vertex.get(j));
					neighbors.add(j);
				}
			}
			if (neighbors.size() > 0) {
				// 由邻居节点构成的子图
				int[][] subAdm = new int[neighbors.size()][neighbors.size()];
				subgraph(subAdm, neighbors, adjMatrix);
				// 求子图的最大连通分量
				ConnectedCompent cc = new ConnectedCompent(subAdm);
				double score = cc.getMaxCCEdge() / Math.pow(cc.getMaxCCVertex(), 1.7);
				vertex.get(i).setParam(score);
			} else {
				vertex.get(i).setParam(0);
			}
		}
	}

	/**
	 * 邻居节点的邻接矩阵
	 */
	public void subgraph(int[][] subAdm, List<Integer> neighbors, int[][] adm) {

		for (int i = 0; i < neighbors.size(); i++) {
			for (int j = i + 1; j < neighbors.size(); j++) {
				if (adm[neighbors.get(i)][neighbors.get(j)] != 0) {
					subAdm[i][j] = 1;
					subAdm[j][i] = 1;
				}
			}
		}
	}
}

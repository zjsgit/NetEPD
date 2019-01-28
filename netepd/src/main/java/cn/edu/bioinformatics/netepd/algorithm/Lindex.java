package cn.edu.bioinformatics.netepd.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import cn.edu.bioinformatics.netepd.bean.ProteinNode;
import cn.edu.bioinformatics.netepd.util.AlgoNode;

public class Lindex extends AlgoObject {
	
	
	/**
	 * 获得网络邻接矩阵
	 * @param edges
	 * @param vertex
	 * @param param
	 */
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
	
	/**
	 * 节点分数计算方法
	 * @param adjMatrix
	 * @param vertex
	 */
	public void figure(int[][] adjMatrix, Vector<ProteinNode> vertex) {
		
		int[] nodeStrength = new int[vertex.size()];
        //计算节点的node strength
        for (int i = 0; i < adjMatrix.length; i++) {
            int sum = 0;
            for (int j = 0; j < nodeStrength.length; j++) {
                if (adjMatrix[i][j] != 0) {
                    sum += adjMatrix[i][j];
                }
            }
            nodeStrength[i] = sum;
        }
        //获取节点的邻居节点
		for (int i = 0; i < vertex.size(); i++) {
			List<LNode> neighbors = new ArrayList<>();
			for (int j = 0; j < vertex.size(); j++) {
				if (adjMatrix[i][j] != 0) {
					LNode node = new LNode();
					node.vertex = vertex.get(j);
					node.nodeStrength = nodeStrength[j];
					neighbors.add(node);
				}
			}
			Collections.sort(neighbors, new Comparator<LNode>() {
				@Override
				public int compare(LNode n1, LNode n2) {
					return Integer.compare(n2.nodeStrength, n1.nodeStrength);
				}
			});
			
			int k = 0;
			for (int j = 0; j < neighbors.size(); j++) {
				k = k + 1;
				if (neighbors.get(j).nodeStrength < k) {
					k = k-1;
					break;
				}
			}
			vertex.get(i).setParam(k);
		}
	}
	
	class LNode{
		ProteinNode vertex;
		int nodeStrength;

		public LNode() {
			this.vertex = new ProteinNode();
			this.nodeStrength = 0;
		}
	}

}

package cn.edu.bioinformatics.netepd.algorithm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.Vector;

import cn.edu.bioinformatics.netepd.bean.ProteinNode;
import cn.edu.bioinformatics.netepd.util.AlgoNode;

public class BC extends AlgoObject {

	Map<ProteinNode, BSData> decorator = new HashMap<>();

	@Override
	public void processNode(Vector<String[]> edges, Vector<ProteinNode> vertex, int param, AlgoNode algoInfo) {
		switch (param) {
		case 0:
			algoInfo.setAdj_matrix(makeMatrix(vertex, edges));
			computeBetweenness(algoInfo.getAdj_matrix(), vertex);
			break;
		case 1:
			algoInfo.setAdj_matrix(makeWeiMatrix(vertex, edges));
			computeBetweennessWithWeight(algoInfo.getAdj_matrix(), vertex);
			break;
		default:
			break;
		}

	}

	public void computeBetweenness(int[][] data, Vector<ProteinNode> vertexs) {
		
		//重置节点的计算结果
		for (ProteinNode pNode : vertexs) {
			pNode.setParam(0.0);
			pNode.setRanking(0);
		}
		
		for (ProteinNode s : vertexs) {
			
			// 相关参数初始化
			for (ProteinNode vertex : vertexs) {
				decorator.put(vertex, new BSData());
			}

			decorator.get(s).numSPs = 1.0D;
			decorator.get(s).distance = 0.0D;

			Stack<ProteinNode> stack = new Stack<>();
			Queue<ProteinNode> queue = new ArrayDeque<>();
			queue.add(s);

			while (!queue.isEmpty()) {
				ProteinNode v = queue.remove();
				stack.push(v);
				for (ProteinNode w : vertexs) {
					// neighbor w of v?
					if (data[vertexs.indexOf(v)][vertexs.indexOf(w)] != 0) {
						// w found for the first time?
						if (decorator.get(w).distance < 0.0D) {
							queue.add(w);
							decorator.get(w).distance = decorator.get(v).distance + 1.0D;
						}
						// shortest path to w via v?
						if (decorator.get(w).distance == (decorator.get(v).distance + 1.0D)) {
							decorator.get(w).numSPs += decorator.get(v).numSPs;
							decorator.get(w).predecessors.add(v);
						}
					}
				}

			}

			while (!stack.isEmpty()) {
				ProteinNode w = stack.pop();
				for (ProteinNode v : decorator.get(w).predecessors) {
					double partialDependency = decorator.get(v).numSPs / decorator.get(w).numSPs;
					partialDependency *= (1.0D + decorator.get(w).dependency);
					decorator.get(v).dependency += partialDependency;
				}
				if (w != s) {
					double bcValue = w.getParam();
					bcValue += decorator.get(w).dependency;
					w.setParam(bcValue);
				}
			}
		}
	}

	public void computeBetweennessWithWeight(int[][] data, Vector<ProteinNode> vertexs) {

		for (ProteinNode s : vertexs) {

			// 相关参数初始化
			for (ProteinNode vertex : vertexs) {
				decorator.put(vertex, new BSData());
			}

			decorator.get(s).numSPs = 1.0D;
			decorator.get(s).distance = 0.0D;

			Stack<ProteinNode> stack = new Stack<>();
			Queue<ProteinNode> queue = new ArrayDeque<>();
			queue.add(s);

			while (!queue.isEmpty()) {
				ProteinNode v = queue.remove();
				stack.push(v);
				for (ProteinNode w : vertexs) {
					int wei = data[vertexs.indexOf(v)][vertexs.indexOf(w)];
					// neighbor w of v?
					if (wei != 0) {
						double dis = 1.0D / wei;
						// w found for the first time?
						if (decorator.get(w).distance < 0.0D) {
							queue.add(w);
							decorator.get(w).distance = decorator.get(v).distance + dis;
						}
						// shortest path to w via v?
						if (decorator.get(w).distance == (decorator.get(v).distance + dis)) {
							decorator.get(w).numSPs += decorator.get(v).numSPs;
							decorator.get(w).predecessors.add(v);
						}
					}
				}

			}

			while (!stack.isEmpty()) {
				ProteinNode w = stack.pop();
				for (ProteinNode v : decorator.get(w).predecessors) {
					double partialDependency = decorator.get(v).numSPs / decorator.get(w).numSPs;
					partialDependency *= (1.0D + decorator.get(w).dependency);
					decorator.get(v).dependency += partialDependency;
				}
				if (w != s) {
					double bcValue = w.getParam();
					bcValue += decorator.get(w).dependency;
					w.setParam(bcValue);
				}
			}

		}
	}

	class BSData {
		/**
		 * 即d[v]
		 */
		double distance;
		/**
		 * 即σ[v]
		 */
		double numSPs;
		/**
		 * 即P[v]
		 */
		List<ProteinNode> predecessors;
		/**
		 * 即δ[v]
		 */
		double dependency;

		BSData() {
			this.distance = -1.0D;
			this.numSPs = 0.0D;
			this.predecessors = new ArrayList<>();
			this.dependency = 0.0D;
		}
	}
}

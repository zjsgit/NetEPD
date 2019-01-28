package cn.edu.bioinformatics.netepd.algorithm;

import java.util.Vector;

import cn.edu.bioinformatics.netepd.bean.ProteinNode;
import cn.edu.bioinformatics.netepd.util.AlgoNode;
import javaalgorithm.algorithm.Matrix;

public class PageRank extends AlgoObject{
	
	
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
	
	public void figure(int[][] adjMatrix, Vector<ProteinNode> vertex) {
		
		double alpha = 0.85;
		Matrix tm = transitionMatrix(adjMatrix);
		tm = tm.multiply(alpha);
		
		Matrix p0 = new Matrix(vertex.size(),1);
		//转移到随机节点的概率，即初始状态矩阵
		for (int i = 0; i < vertex.size(); i++) {
			p0.setElement(i, 0, (double)1.0/vertex.size());
		}
		Matrix pr = p0;
		p0 = p0.multiply(1-alpha);
		
		int step = 1;
		double error = 10000;
		double error_threshold = 0.0001;
		//迭代计算
		while (error > error_threshold) {
			Matrix temp = pr;
			if (tm.getNumColumns() == pr.getNumRows()) {
				pr = p0.add(tm.multiply(pr));
			}else {
				System.out.println("Matrix Error!");
				return;
			}
			error = getErrorValue(temp, pr);
			step = step + 1;
		}
		
		System.out.println(step);
		System.out.println(pr.getNumRows()+"\t"+pr.getNumColumns());
		for (int i = 0; i < vertex.size(); i++) {
			vertex.get(i).setParam(pr.getElement(i, 0));
		}
	}
	
	/**
	 * 获取误差值
	 */
	public double getErrorValue(Matrix temp,Matrix pr){
		
		Matrix res = pr.subtract(temp);
		double max = res.getElement(0, 0);
		for (int i = 0; i < res.getNumRows(); i++) {
			double curValue = res.getElement(i, 0);
			if (max < curValue) {
				max = curValue;
			}
		}
		return max;
	}
	
	/**
	 * 由邻接矩阵得到状态转移矩阵
	 */
	private Matrix transitionMatrix(int[][] adjMatrix) {
		
		Matrix tm = new Matrix(adjMatrix.length, adjMatrix.length);
		
		for (int i = 0; i < tm.getNumRows(); i++) {
			int outDegree = 0;
			for (int j = 0; j < adjMatrix.length; j++) {
				int weiValue = adjMatrix[i][j];
				if (weiValue != 0) {
					outDegree += weiValue;
				}
			}
			for (int j = 0; j < tm.getNumColumns(); j++) {
				int weiValue = adjMatrix[i][j];
				if (weiValue != 0) {
					tm.setElement(j, i, (double)weiValue/outDegree);
				}
			}
		}
		
		return tm;
	}
	
}

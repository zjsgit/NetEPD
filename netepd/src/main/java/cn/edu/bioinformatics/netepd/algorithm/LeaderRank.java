package cn.edu.bioinformatics.netepd.algorithm;

import java.util.Vector;

import cn.edu.bioinformatics.netepd.bean.ProteinNode;
import cn.edu.bioinformatics.netepd.util.AlgoNode;
import javaalgorithm.algorithm.Matrix;

public class LeaderRank extends AlgoObject{
	
	@Override
	public void processNode(Vector<String[]> edges, Vector<ProteinNode> vertex, int param, AlgoNode algoNode) {
		switch (param) {
		case 0:
			algoNode.setAdj_matrix(makeMatrix(vertex, edges));
			figure(addGroundNode2Matrix(algoNode.getAdj_matrix()),vertex);
			break;
		case 1:
			algoNode.setAdj_matrix(makeWeiMatrix(vertex, edges));
			figure(addGroundNode2Matrix(algoNode.getAdj_matrix()), vertex);
			break;
		default:
			break;
		}
	}
	
	private void figure(int[][] adjMatrix, Vector<ProteinNode> vertex) {
		
		Matrix tm = transitionMatrix(adjMatrix);
		//初始分数
		Matrix god = new Matrix(vertex.size()+1,1);
		for (int i = 0; i < vertex.size(); i++) {
			god.setElement(i, 0, 1);
		}
		//迭代计算
		double error = 10000;
		double error_threshold = 0.00002;
		while (error > error_threshold) {
			Matrix m = god;
			if (tm.getNumColumns() == god.getNumRows()) {
				god = tm.multiply(god);
			}else {
				System.out.println("Matrix Error!");
				return;
			}
			error = getErrorValue(m, god);
		}
		
		//System.out.println(god.getNumRows()+"\t"+god.getNumColumns());
		for (int i = 0; i < vertex.size(); i++) {
			vertex.get(i).setParam(god.getElement(i, 0));
		}
	}
	
	public int[][] addGroundNode2Matrix(int[][] adm){
		
		int[][] admG = new int[adm.length+1][adm.length+1];
		for (int i = 0; i < adm.length; i++) {
			for (int j = 0; j < adm[i].length; j++) {
				admG[i][j] = adm[i][j];
			}
		}
		for (int i = 0; i < adm.length; i++) {
			admG[i][adm.length] = 1;
			admG[adm.length][i] = 1;
		}
		
		return admG;
	}

	/**
	 * 由邻接矩阵得到状态转移矩阵
	 */
	public Matrix transitionMatrix(int[][] admG){
		
		Matrix tm = new Matrix(admG.length, admG.length);
		
		for (int i = 0; i < tm.getNumRows(); i++) {
			int outDegree = 0;
			for (int j = 0; j < tm.getNumColumns(); j++) {
				int weiValue = admG[i][j];
				if (weiValue != 0) {
					outDegree += weiValue;
				}
			}
			for (int j = 0; j < tm.getNumColumns(); j++) {
				int weiValue = admG[i][j];
				if (weiValue != 0) {
					tm.setElement(j, i, (double)weiValue/outDegree);
				}
			}
		}
		return tm;
	}
	
	/**
	 * 获取误差值
	 */
	public double getErrorValue(Matrix m,Matrix god){
		double error = 0.0;
		Matrix res = god.subtract(m);
		double sum = 0;
		for (int i = 0; i < res.getNumRows(); i++) {
			sum += Math.abs(res.getElement(i, 0))/m.getElement(i, 0);
		}
		error = sum/m.getNumRows();
		//System.out.println("\t"+error);
		
		return error;
	}
}

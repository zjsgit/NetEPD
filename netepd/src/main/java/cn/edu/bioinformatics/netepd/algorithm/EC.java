package cn.edu.bioinformatics.netepd.algorithm;

import java.util.Vector;

import cn.edu.bioinformatics.netepd.bean.ProteinNode;
import cn.edu.bioinformatics.netepd.util.AlgoNode;
import javaalgorithm.algorithm.Matrix;

public class EC extends AlgoObject {

	@Override
	public void processNode(Vector<String[]> edges, Vector<ProteinNode> vertex, int param, AlgoNode algoInfo) {
		switch (param) {
		case 0:
			if (algoInfo.getAdj_matrix() == null) {
				algoInfo.setAdj_matrix(makeMatrix(vertex, edges));
			}
			figure(algoInfo.getAdj_matrix(), vertex, algoInfo);
			break;
		case 1:
			if (algoInfo.getAdj_matrix() == null) {
				algoInfo.setAdj_matrix(makeWeiMatrix(vertex, edges));
			}
			figure(algoInfo.getAdj_matrix(), vertex, algoInfo);
			break;
		default:
			break;
		}
	}

	private void figure(int[][] data, Vector<ProteinNode> vertex, AlgoNode algoInfo) {
		
		//将不直接相连的两个节点的距离设置为最大值
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				if (data[i][j] == 0) {
					data[i][j] = Integer.MAX_VALUE;
				}
				
			}
		}

		int d_len = data.length;
		double[] tempData = new double[d_len * d_len];
		int i = 0;
		if (algoInfo.getEigenvalues() == null || algoInfo.getEigenvector() == null) {
			for (i = 0; i < d_len * d_len; i++) {
				if (data[i / d_len][i % d_len] == Integer.MAX_VALUE) {
					tempData[i] = 0.0;
				} else {
					tempData[i] = (double) data[i / d_len][i % d_len];
				}
			}
			Matrix matx = new Matrix(d_len, tempData);
			Matrix mtxQ2 = new Matrix();
			Matrix mtxT2 = new Matrix();
			double[] bArray2 = new double[matx.getNumColumns()];
			double[] cArray2 = new double[matx.getNumColumns()];
			if (matx.makeSymTri(mtxQ2, mtxT2, bArray2, cArray2)) {
				// 2: compute eigenvalues and eigenvectors
				if (matx.computeEvSymTri(bArray2, cArray2, mtxQ2, 60, 0.0001)) {
					algoInfo.setEigenvalues(bArray2);
					algoInfo.setEigenvector(mtxQ2);
					setMaxVector(vertex, mtxQ2, bArray2);
				} else {
					System.out.println("失败");
				}
			} else {
				System.out.println("失败");
			}
		} else {
			setMaxVector(vertex, algoInfo.getEigenvector(), algoInfo.getEigenvalues());

		}

	}
	
	private void setMaxVector(Vector<ProteinNode> vertex, Matrix matrix, double[] value) {
		double max = -Double.MIN_VALUE;
		int i = 0, j = 0;
		for (i = 0; i < value.length; i++) {
			if (value[i] > max) {
				max = value[i];
				j = i;
			}
		}
		for (i = 0; i < matrix.getNumRows(); i++) {
			double score = matrix.getElement(i, j)>0?matrix.getElement(i, j):-matrix.getElement(i, j);
			vertex.get(i).setParam(score);
		}
	}

}

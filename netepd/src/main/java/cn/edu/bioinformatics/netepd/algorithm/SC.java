package cn.edu.bioinformatics.netepd.algorithm;

import java.util.Vector;

import cn.edu.bioinformatics.netepd.bean.ProteinNode;
import cn.edu.bioinformatics.netepd.util.AlgoNode;
import javaalgorithm.algorithm.Matrix;

public class SC extends AlgoObject {

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
		
		double[] tempData = new double[data.length * data.length];
		int i = 0;
		if (algoInfo.getEigenvalues() == null || algoInfo.getEigenvector() == null) {
			for (i = 0; i < data.length * data.length; i++) {
				if (data[i / data.length][i % data.length] == Integer.MAX_VALUE) {
					tempData[i] = 0.0;
				} else {
					tempData[i] = data[i / data.length][i % data.length];
				}
			}
			Matrix matx = new Matrix(data.length, tempData);
			Matrix mtxQ2 = new Matrix();
			Matrix mtxT2 = new Matrix();
			double[] bArray2 = new double[matx.getNumColumns()];
			double[] cArray2 = new double[matx.getNumColumns()];
			System.err.println("run");
			if (matx.makeSymTri(mtxQ2, mtxT2, bArray2, cArray2)) {
				// 2: compute eigenvalues and eigenvectors
				if (matx.computeEvSymTri(bArray2, cArray2, mtxQ2, 60, 0.0001)) {
					algoInfo.setEigenvalues(bArray2);
					algoInfo.setEigenvector(mtxQ2);
					setResult(vertex, mtxQ2, bArray2);
				} else {
					System.out.println("失败");
				}
			} else {
				System.out.println("失败");
			}
		} else {
			setResult(vertex, algoInfo.getEigenvector(), algoInfo.getEigenvalues());

		}
	}

	private void setResult(Vector<ProteinNode> vertex, Matrix matrix, double[] value) {
		boolean[] flag = new boolean[value.length];
		int i = 0, j = 0;
		double result = 0;
		double temp = 0;
		for (i = 0; i < matrix.getNumColumns(); i++) {
			flag[i] = true;
			for (j = 0; j < i - 1; j++) {
				if (value[j] == value[i]) {
					flag[j] = false;
					break;
				}
			}
		}
		for (i = 0; i < matrix.getNumRows(); i++) {
			result = 0;
			temp = 0;
			for (j = 0; j < matrix.getNumColumns(); j++) {
				temp = matrix.getElement(i, j);
				result += temp * Math.exp(value[j]) * temp;
			}
			vertex.get(i).setParam(result);
		}
	}
}

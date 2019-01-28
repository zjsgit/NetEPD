package cn.edu.bioinformatics.netepd.algorithm;

import java.util.Vector;

import cn.edu.bioinformatics.netepd.bean.ProteinNode;
import cn.edu.bioinformatics.netepd.util.AlgoNode;
import javaalgorithm.algorithm.Matrix;

public class IC extends AlgoObject {

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
	 * 代码实现同CytoNCA，但可能存在问题待解决
	 * @param adjMatrix 即A是网络的邻接矩阵
	 * @param vertexs 顶点集合
	 */
	public void figure(int[][] adjMatrix, Vector<ProteinNode> vertexs) {
		int vNum = vertexs.size();
		Matrix mtxQ = new Matrix(vNum);
		for (int i = 0; i < vNum; i++) {
			int degree = 0;
			for (int j = 0; j < vNum; j++) {
				degree += adjMatrix[i][j];
			}
			mtxQ.setElement(i, i, degree+1);
			for (int j = 0; j < vNum; j++) {
				if (adjMatrix[i][j]==1) {
					mtxQ.setElement(i, j, 0);
					mtxQ.setElement(j, i, 0);
				}
			}
		}
		if (mtxQ.invertGaussJordan()) {
			for (int i = 0; i < vNum; i++) {
				double sum = 0;
				for (int j = 0; j < vNum; j++) {
					if (i!=j) {
						sum += mtxQ.getElement(i, i)+mtxQ.getElement(j, j)-2.0*mtxQ.getElement(i, j);
					}
				}
				vertexs.get(i).setParam(1.0*vNum/sum);
			}
		}
	}
}

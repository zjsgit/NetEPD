package cn.edu.bioinformatics.netepd.algorithm;

import java.util.Vector;

import cn.edu.bioinformatics.netepd.util.AlgoNode;
import cn.edu.bioinformatics.netepd.bean.ProteinNode;

public class KShell extends AlgoObject {

	@Override
	public void processNode(Vector<String[]> edges, Vector<ProteinNode> vertex, int param, AlgoNode algoInfo) {
		switch (param) {
		case 0:
			if (algoInfo.getAdj_matrix() == null) {
				algoInfo.setAdj_matrix(makeMatrix(vertex, edges));
			}
			figure(algoInfo.getAdj_matrix(), vertex);
			break;
		case 1:
			if (algoInfo.getAdj_matrix() == null) {
				algoInfo.setAdj_matrix(makeWeiMatrix(vertex, edges));
			}
			figure(algoInfo.getAdj_matrix(), vertex);
			break;

		default:
			break;
		}
	}

	private void figure(int[][] data, Vector<ProteinNode> vertex) {
		
		double []shell=new double[vertex.size()];//表示节点的最终壳数
		double []nodeDC=new double[vertex.size()];//表示去壳后节点的度数
		int assignmentNumber=0;//表示已经求出壳数的节点的数目
		int shellNumber=1;//表示壳数
		
		while(assignmentNumber<vertex.size()){
			
			boolean changeShell = true;
			for(int i=0;i<data.length;i++){
				nodeDC[i]=GetDC(data, i);	
				if(nodeDC[i]==shellNumber) {
					changeShell = false;
				}
			}
			if(changeShell) {
				shellNumber++;
			}
			for(int i=0;i<data.length;i++){
				if(nodeDC[i]!=0&& shellNumber == nodeDC[i]){
					shell[i]=shellNumber;
					assignmentNumber++;
					for(int j=0;j<data[i].length;j++) {
						if(data[i][j]==1) {
							data[i][j]=0;
							data[j][i]=0;
						}
					}
				}
			}
		}
		
		for(int q=0;q<shell.length;q++){
			vertex.get(q).setParam( shell[q] );
		}
		
	}//end figure
	
	private static int GetDC(int [][] matrix, int i) {
		
		int dc =0;
		for(int j=0;j<matrix[i].length;j++) {
			if(matrix[i][j]==1) {
				dc++;
			}
		}
		return dc;
		
	}
	
	

}//end KShell

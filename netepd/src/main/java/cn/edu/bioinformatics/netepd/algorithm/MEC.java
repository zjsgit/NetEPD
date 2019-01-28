package cn.edu.bioinformatics.netepd.algorithm;

import java.util.Vector;

import cn.edu.bioinformatics.netepd.util.AlgoNode;
import cn.edu.bioinformatics.netepd.bean.ProteinNode;

public class MEC extends AlgoObject {

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
		
		double proteinsDeg[]=new double [vertex.size()];//表示蛋白质节点的度数
		
		//通过第一次遍历，求取并保存蛋白质节点的度数
		double sum;
		for(int i=0;i< data.length;i++){
			sum=0;
			for(int j=0;j< data[i].length;j++){
				sum+= data[i][j];
			}
			proteinsDeg[i]=sum;
		}
		
		//通过遍历节点的邻接点，求出映射熵
		for(int i=0;i< data.length;i++){
			sum=0;
			for(int j=0;j< data[i].length;j++){
				if( data[i][j]==1){
					sum+=Math.log(proteinsDeg[j]);
				}
			}
			
			double MappingEntropyCentrality=proteinsDeg[i]*sum;
			vertex.get(i).setParam( MappingEntropyCentrality);
			
		}
	}//end figure

}//end MEC

package cn.edu.bioinformatics.netepd.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import cn.edu.bioinformatics.netepd.util.AlgoNode;
import cn.edu.bioinformatics.netepd.bean.ProteinNode;

public class NeiC extends AlgoObject {

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
		
		for(int i=0;i< data.length;i++){
			List<Integer> neighbours=new ArrayList<Integer>();//表示与蛋白质节点直接相连的节点
			for(int j=0;j< data[i].length;j++){
				if( data[i][j]==1){
					neighbours.add(j);
				}
			}
			
			//获取到节点所有邻居节点的度
			double sum=0.0;
			for(int m=0;m<neighbours.size();m++){
				int row = neighbours.get(m);
				for (int n=0; n<data[m].length; n++) {
					sum += data[row][m];
				}
			}
			vertex.get(i).setParam( sum/neighbours.size() );
			neighbours.clear();
		}
		
		
	}
}

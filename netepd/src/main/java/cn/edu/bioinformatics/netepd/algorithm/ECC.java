package cn.edu.bioinformatics.netepd.algorithm;

import java.util.Vector;

import cn.edu.bioinformatics.netepd.util.AlgoNode;
import cn.edu.bioinformatics.netepd.bean.ProteinNode;

public class ECC extends AlgoObject {

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
		
		//将不直接相连的两个节点的距离设置为最大值
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				if (i != j && data[i][j] == 0) {
					data[i][j] = Integer.MAX_VALUE;
				}
				
			}
		}
		
		//用floyd算法计算两个蛋白质之间的最短距离
		for(int k=0;k<data.length;k++){
            for(int i=0;i<data[k].length;i++){
                for(int j=0;j<data[k].length;j++){
                    if(data[i][k]+data[k][j]<data[i][j]){
                    	data[i][j]=data[i][k]+data[k][j];
                    }
                }
            }
        }
		
		double max_distance;
		for(int i=0;i<data.length;i++){
			max_distance=0;
			for(int j=0;j<data[i].length;j++){
				//两个蛋白质是不相连的，蛋白质自身距离为0
				
				if(data[i][j]!= Integer.MAX_VALUE&&
						data[i][j]!=0&&data[i][j]>max_distance){
					max_distance=data[i][j];
				}
			}
			vertex.get(i).setParam(max_distance);
		}
		
	}

}


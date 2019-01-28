package cn.edu.bioinformatics.netepd.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import cn.edu.bioinformatics.netepd.util.AlgoNode;
import cn.edu.bioinformatics.netepd.bean.ProteinNode;

public class SLC extends AlgoObject {

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
		
		//根据邻接矩阵求出蛋白质节点的一步邻居节点和和二步邻居节点
		int N_proteinArray[]=new int [vertex.size()];
		for(int i=0;i<  data.length;i++){
			
			List <Integer> neighbourProteins=new ArrayList<Integer>();
			neighbourProteins.add(0, i);
			for(int j=0;j< data[i].length;j++){
				if(  data[i][j]==1&&!neighbourProteins.contains(j)){
					neighbourProteins.add(j);//将一步邻居节点加入到集合中
				}
			}	
			
			int nearestNeighbours=neighbourProteins.size();//表示一步邻居节点的数量
			for(int m=0;m<nearestNeighbours;m++){
				int row=neighbourProteins.get(m);
				for(int k=0; k < data[row].length; k++){
					if( data[row][k]==1&&!neighbourProteins.contains(k)){
						neighbourProteins.add(k);//在一步邻居节点的基础上将二步邻居节点加入到集合中
					}
				}
			}
			
			neighbourProteins.remove(0);
			N_proteinArray[i]=neighbourProteins.size();//将蛋白质节点的一步邻居节点和和二步邻居节点放入到数组中
			neighbourProteins.clear();//将蛋白质节点的邻居集合清理，否则会影响其他邻居节点
		}
		
		//在一步邻居节点和二步邻居节点的基础上求得三步邻居节点
		int Q_proteinArray[]=new int [vertex.size()];
		for(int i=0;i< data.length;i++){
			int sum=0;
			for(int j=0; j<data[i].length; j++){
				if(data[i][j]==1){
					sum+=N_proteinArray[j];
				}
			}	
			Q_proteinArray[i]=sum;
		}
		
		//在三步邻居节点的基础上求得四步邻居节点
		double sum;
		for(int i=0;i<data.length;i++){
			sum=0;
			for(int j=0;j<data[i].length;j++){
				if(data[i][j]==1){
					sum+=Q_proteinArray[j];
					vertex.get(i).setParam(sum);
				}
			}
		}
	}//end figure		
	
}//end SLC

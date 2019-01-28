package cn.edu.bioinformatics.netepd.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import cn.edu.bioinformatics.netepd.util.AlgoNode;
import cn.edu.bioinformatics.netepd.bean.ProteinNode;

public class LCCDC extends AlgoObject {

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
		
		int proteinDC[]=new int[vertex.size()];//表示单个蛋白质节点的度中心性
		double actualLinks[]=new double[vertex.size()];//表示单个蛋白质节点的邻居之间的实际链接数
		double maxPossibleLinks[]=new double[vertex.size()];//表示单个蛋白质节点的邻居之间最大可能的联系数量
		
		//求取单个蛋白质节点的度中心性
		for(int i=0;i< data.length;i++){
			for(int j=0;j<data[i].length;j++) {
				proteinDC[i] += data[i][j];
			}
		}//end for
		
		//求取单个蛋白质节点的邻居之间最大可能的联系数量
		for(int i=0;i<vertex.size();i++){
			if(proteinDC[i]==1){
				maxPossibleLinks[i]=1;
			}else{
				maxPossibleLinks[i]=proteinDC[i]*(proteinDC[i]-1)/2;		
			}
		}
		
		//求取单个蛋白质节点的邻居之间的实际链接数
		for(int i=0;i<data.length;i++){
			List <Integer> neighbours=new ArrayList<Integer>();
			for(int j=0;j<data[i].length;j++){
				if(data[i][j]==1){
					neighbours.add(j);
				}
			}
			int ActualLinks=0;
			for(int m=0;m<neighbours.size();m++){
				for(int n=m+1;n<neighbours.size();n++){
					if(data[neighbours.get(m)][neighbours.get(n)]==1){
						++ActualLinks;
					}
				}
			}//end for
			actualLinks[i]=ActualLinks;
			neighbours.clear();
		}//end for
		
		for(int i=0;i<vertex.size();i++){
			vertex.get(i).setParam( proteinDC[i]*(1-actualLinks[i]/maxPossibleLinks[i]) );
			
		}//end for
		
	}//end figure

}//end 


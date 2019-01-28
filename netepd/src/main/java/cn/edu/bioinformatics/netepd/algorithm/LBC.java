package cn.edu.bioinformatics.netepd.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import cn.edu.bioinformatics.netepd.util.AlgoNode;
import cn.edu.bioinformatics.netepd.bean.ProteinNode;

public class LBC extends AlgoObject {

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
		double Beita[]=new double[vertex.size()];//表示单个蛋白质节点的BridgingCoefficient
		int CEgo[]=new int[vertex.size()];//表示单个蛋白质节点的EgocentricBetweennessCentrality
		
		//求取单个蛋白质节点的度中心性
		for(int i=0;i<proteinDC.length;i++){
			int sum=0;
			for(int j=0; j <data[i].length;j++) {
				sum += data[i][j];
			}
			proteinDC[i] = sum;
		}//end for
		
		//求取单个蛋白质节点的BridgingCoefficient
		for(int i=0;i<data.length;i++){
			double reciprocalDC=1.0/proteinDC[i];//自身度中心性的倒数
			double sumReciprocalDC=0;
			for(int j=0;j<data[i].length;j++){
				if(data[i][j]==1){
					sumReciprocalDC+=1.0/proteinDC[j];//节点邻居的度中心性倒数之和
				}
			}
			Beita[i]=reciprocalDC/sumReciprocalDC;
		}
		
		//求取单个蛋白质节点的EgocentricBetweennessCentrality
		for(int i=0;i<data.length;i++){
			List <Integer> neighbours=new ArrayList<Integer>();
			for(int j=0;j<data[i].length;j++){
				if(data[i][j]==1){
					neighbours.add(j);
				}
			}
			int ebc=neighbours.size()*(neighbours.size()-1)/2;
			for(int m=0;m<neighbours.size();m++){
				for(int n=m+1;n<neighbours.size();n++){
					if(data[neighbours.get(m)][neighbours.get(n)]==1){
						--ebc;
					}
				}
			}//end for
			CEgo[i]=ebc;
			neighbours.clear();
		}//end for
		
		for(int i=0;i<vertex.size();i++){
			vertex.get(i).setParam( CEgo[i]* Beita[i] );
			
		}//end for
	
	}
}

package cn.edu.bioinformatics.netepd.algorithm;

import java.util.Vector;

import cn.edu.bioinformatics.netepd.util.AlgoNode;
import cn.edu.bioinformatics.netepd.bean.ProteinNode;

public class DSP extends AlgoObject {

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
		
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				if (i!=j && data[i][j] == 0) {
					data[i][j] = Integer.MAX_VALUE;
				}
				
			}
		}
		
		//复制蛋白质邻接矩阵
		int temp_proteinMatrix[][]=new int[vertex.size()][vertex.size()];
		for(int i=0;i<temp_proteinMatrix.length;i++){
			for(int j=0;j<temp_proteinMatrix[i].length;j++){
				temp_proteinMatrix[i][j]=data[i][j];
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
        }//end floyd algorithm
	
		for(int i=0;i<data.length;i++){//表示第i个蛋白质被删除
			
			//计算被删除蛋白质的直接损失
			double DLOS=0.0;
			for(int m=0;m<data[i].length;m++){
				if(data[i][m]!=Integer.MAX_VALUE&&m!=i){//不计算：不连接的蛋白质和自身
					DLOS+=1.0/data[i][m];
				}
			}//end for
			
			//构造删除第i个蛋白质后的邻接矩阵
			int afterDeleteProtein[][]=new int[data.length][data.length];
			for(int m=0;m<afterDeleteProtein.length;m++){
				if(m!=i){
					for(int n=0;n<afterDeleteProtein[m].length;n++){
						afterDeleteProtein[m][n]=temp_proteinMatrix[m][n];
					}
				}else{
					for(int n=0;n<afterDeleteProtein[m].length;n++){
						afterDeleteProtein[m][n]= Integer.MAX_VALUE;
					}
				}//end else
			}//end for
			
			//用floyd算法计算删除一个蛋白之后网络中任意两个蛋白质之间的最短距离
			for(int s=0;s<afterDeleteProtein.length;s++){
	            for(int p=0;p<afterDeleteProtein[s].length;p++){
	                for(int q=0;q<afterDeleteProtein[s].length;q++){
	                    if(afterDeleteProtein[p][s]+afterDeleteProtein[s][q]<afterDeleteProtein[p][q]){
	                    	afterDeleteProtein[p][q]=afterDeleteProtein[p][s]+afterDeleteProtein[s][q];
	                    }
	                }
	            }
	        }//end floyd algorithm
			
			double ILOS=0;//表示删除蛋白质后的间接损失
			
			for(int m=0;m<afterDeleteProtein.length;m++){
				for(int j=m+1;j<afterDeleteProtein[m].length;j++){
					if(afterDeleteProtein[m][j]== Integer.MAX_VALUE && data[m][j]!= Integer.MAX_VALUE && m!=i){
						ILOS+=1.0/data[m][j];
					}
				}
			}//end for
			
			vertex.get(i).setParam( DLOS+ILOS );
			
		}//end for
		
		
		
	}//end figure

}

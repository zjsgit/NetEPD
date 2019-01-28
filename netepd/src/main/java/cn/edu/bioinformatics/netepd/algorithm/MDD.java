package cn.edu.bioinformatics.netepd.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import cn.edu.bioinformatics.netepd.util.AlgoNode;
import cn.edu.bioinformatics.netepd.bean.ProteinNode;

public class MDD extends AlgoObject {

	public static List <Integer> completedShell;//表示壳数已经确定的蛋白质
	
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
		
		double lambd=0.7;//表示参数
		double kShell[]=new double[vertex.size()];//表示蛋白质划分的壳数
		int tempProteinMatrix[][]=new int[vertex.size()][vertex.size()];//表示删除节点后的蛋白质矩阵
		
		completedShell=new ArrayList<Integer>();
		
		for(int i=0;i<tempProteinMatrix.length;i++){
			for(int j=0;j<tempProteinMatrix[i].length;j++){
				tempProteinMatrix[i][j]= data[i][j];//对变化的蛋白质邻接矩阵进行初始化
			}
		}
				
		for(int i=0;i< data.length;i++){
			double sum=0;
			for(int j=0;j< data[i].length;j++){
				if( data[i][j]==1){
					sum++;
				}
			}
			kShell[i]=sum;//对蛋白质壳数进行初始化，等于度中心性
		}//end for

		double residualDegree=0;
		List<Integer>sameShellNumber;
		
			
		while(completedShell.size()< vertex.size()){
			double minShellNumber=getMinValueOfArray(kShell);//经过排序后获取蛋白质最小的壳数
			sameShellNumber=new ArrayList<Integer>();
			
			for(int i=0;i<kShell.length;i++){
				if(kShell[i]==minShellNumber){
					
					sameShellNumber.add(i);
					completedShell.add(i);//将壳数确定的蛋白质下标加入蛋白质壳数确定的集合
					//删除蛋白质节点并对蛋白质的邻接矩阵进行变更
					for(int j=0;j<tempProteinMatrix[i].length;j++){
						tempProteinMatrix[i][j]=0;
						tempProteinMatrix[j][i]=0;
					}
					
				}//end if
			}//end for

			double exhaustedDegree=0;
			boolean checkChangeRow=false;
			//通过比对变化后的邻接矩阵和原邻接矩阵，计算剩余蛋白质的壳数
			for(int m=0;m<data.length;m++){
				exhaustedDegree=0;
				checkChangeRow=false;//
				for(int n=0;n<data[m].length;n++){
					if(data[m][n]!=tempProteinMatrix[m][n]&&
							!sameShellNumber.contains(m)&&!completedShell.contains(m)){
						exhaustedDegree+=1.0;
						checkChangeRow=true;
					}//end if
				}//end for
				
				if(checkChangeRow==true){
					residualDegree=GetDC(m,tempProteinMatrix);
					kShell[m]=lambd*exhaustedDegree+residualDegree;//计算删除节点后与该删除节点相关的蛋白质的壳数			
				}//end if
				
			}//end for
			
			sameShellNumber.clear();
			
		}//end while
		
		for(int q=0;q<kShell.length;q++){
			vertex.get(q).setParam(kShell[q]);
		}
		
	}//end figure

	
	//求出未确定壳数中的蛋白质壳数的最小值
	public static double getMinValueOfArray(double array[]){
		double minValue=10000;
		for (int i = 0; i < array.length; i++) {
			if(array[i]<minValue&&!completedShell.contains(i)){
				minValue=array[i];
			}
		}
		return minValue;
	}
	
	//按行对一个矩阵进行取值
	public static int GetDC(int row,int matrix[][]) {

		int sum = 0;
		for (int i = 0; i < matrix[row].length; i++) {
			sum += matrix[row][i];
		}
		return sum;

	}
	
}//end

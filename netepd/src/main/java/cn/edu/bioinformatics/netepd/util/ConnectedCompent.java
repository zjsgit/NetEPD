package cn.edu.bioinformatics.netepd.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ConnectedCompent {
	/**
	 * edge:二维数组，存储节点之间边的信息的列表
	 */
	public int[][] edge;
	/**
	 * visited:布尔数组，标记当前节点是否已经访问过
	 */
	public boolean[] visited;
	/**
	 * 当前进行深度优先遍历的连通图顶点集合
	 */
	public List<Integer> cc;
	/**
	 * 存放所有的连通分量
	 */
	public Vector<List<Integer>> ccAll;
	/**
	 * 最大连通分量索引位置
	 */
	public int maxLoc;

	public ConnectedCompent(int[][] edge) {
		this.edge = edge;
		con();
	}

	/**
	 * 求该图的连通分量
	 */
	private void con() {
		maxLoc = 0;
		ccAll = new Vector<>();
		visited = new boolean[edge.length];

		for (int i = 0; i < edge.length; i++) {
			if (!visited[i]) {
				cc = new ArrayList<>();
				dfs(i);
				ccAll.add(cc);
				if (ccAll.get(maxLoc).size() < cc.size()) {
					maxLoc = ccAll.size()-1;
				}
			}
		}
	}

	/**
	 * 图的深度优先递归算法
	 */
	private void dfs(int i) {
		visited[i] = true;
		cc.add(i);
		for (int j = 0; j < edge.length; j++) {
			if (visited[j] == false && edge[i][j] == 1) {
				dfs(j);
			}
		}
	}

	/**
	 * 最大连通分量的顶点数量
	 */
	public int getMaxCCVertex(){
		
		return ccAll.get(maxLoc).size();
	}
	/**
	 * 最大连通分量的边的数量
	 */
	public int getMaxCCEdge(){
		
		int num = 0;
		List<Integer> maxCC = ccAll.get(maxLoc);
		for (int i = 0; i < maxCC.size(); i++) {
			for (int j = i+1; j < maxCC.size(); j++) {
				if (edge[i][j] != 0) {
					num = num + 1;
				}
			}
		}
		
		return num;
	}
}

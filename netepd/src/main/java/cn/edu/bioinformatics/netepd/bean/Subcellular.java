package cn.edu.bioinformatics.netepd.bean;

import java.util.List;

/**
 * 亚细胞区间，实验中构建了11中亚细胞区间蛋白质相互作用网络：
 * "CYTOSKELETON","CYTOSOL","ENDOPLASMIC","ENDOSOME","EXTRACELLULAR","GOLGI",
 * "LYSOSOME","MITOCHONDRION","NUCLEUS","PEROXISOME","PLASMA";
 * 细胞骨架，细胞质基质，内质网，核内体，细胞外基质，高尔基体，溶酶体，线粒体，细胞核，过氧化物酶体和细胞质。
 * 
 * @author lwk
 * @version May 9, 2017 3:51:33 PM
 */
public class Subcellular {

	private String name;//区间的名称
	
	private List<String> nodes;//区间中所包含的蛋白质节点以及在子网中的分数
	
	private List<String[]> edges;//亚细胞区间内的蛋白质网络
	
	private double reliability;//区间的可信度值

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getNodes() {
		return nodes;
	}

	public void setNodes(List<String> nodes) {
		this.nodes = nodes;
	}

	public List<String[]> getEdges() {
		return edges;
	}

	public void setEdges(List<String[]> edges) {
		this.edges = edges;
	}

	public double getReliability() {
		return reliability;
	}

	public void setReliability(double reliability) {
		this.reliability = reliability;
	}

	public Subcellular() {
	}

	public Subcellular(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Subcellular [name=" + name + ", nodes=" + nodes.size() + ", edges=" + edges.size() + ", reliability="
				+ reliability + "]";
	}

}




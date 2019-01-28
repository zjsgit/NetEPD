package cn.edu.bioinformatics.netepd.bean;

/**
 * 用来存储蛋白质节点数据
 * @author lwk
 *
 */
public class ProteinNode {
	
	/**
	 * 节点名称
	 */
	private String name;
	/**
	 * 节点的计算结果
	 */
	private double param;
	/**
	 * 节点的排序位置
	 */
	private int ranking;

	public ProteinNode() {}

	public ProteinNode(String name, double param) {
		super();
		this.name = name;
		this.param = param;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getParam() {
		return param;
	}

	public void setParam(double param) {
		this.param = param;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
}

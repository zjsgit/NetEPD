package cn.edu.bioinformatics.netepd.dao;

import java.util.List;

import cn.edu.bioinformatics.netepd.entity.DipNetwork;

public interface DipNetworkDao extends NetworkDao{
	/**
	 * 选择对应物种的数据集网络
	 * @param taxid
	 * @return List<Object>
	 */
	public List<DipNetwork> getNetworkByOrganism(String taxid);
	
	
}

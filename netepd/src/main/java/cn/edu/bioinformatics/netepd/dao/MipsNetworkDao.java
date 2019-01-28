package cn.edu.bioinformatics.netepd.dao;

import java.util.List;

import cn.edu.bioinformatics.netepd.entity.MipsNetwork;

public interface MipsNetworkDao extends NetworkDao{
	/**
	 * 选择对应物种的数据集网络
	 * @param taxid
	 * @return List<Object>
	 */
	public List<MipsNetwork> getNetworkByOrganism(String taxid);
	
}

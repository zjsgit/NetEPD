package cn.edu.bioinformatics.netepd.dao;

import java.util.List;

import cn.edu.bioinformatics.netepd.entity.MintMap;

public interface MintMapDao {
	/**
	 * 依据要映射的list集合进行mapping并返回可以匹配到的数据集
	 * @param list
	 * @return List<Object>
	 */
	public List<MintMap> getMappedResult(List<String> list);
}

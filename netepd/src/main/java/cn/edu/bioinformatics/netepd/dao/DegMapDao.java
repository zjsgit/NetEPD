package cn.edu.bioinformatics.netepd.dao;

import java.util.List;

import cn.edu.bioinformatics.netepd.entity.DegMap;

public interface DegMapDao {
	/**
	 * 依据要映射的list集合进行mapping并返回可以匹配到的数据集
	 * @param list
	 * @return List<Object>
	 */
	public List<DegMap> getMappedResult(List<String> list);
	/**
	 * 获取关键蛋白数据集
	 * @return
	 */
	public List<DegMap> getEPSet();
}

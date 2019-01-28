package cn.edu.bioinformatics.netepd.dao;

import java.util.List;

import cn.edu.bioinformatics.netepd.entity.LocationMap;

public interface LocationMapDao {

	/**
	 * 依据物种id进行mapping并返回可以匹配到的数据集
	 * @param list
	 * @return List<Object>
	 */
	public List<LocationMap> getMappedResult(List<String> list);
	
}

package cn.edu.bioinformatics.netepd.dao;

import java.util.List;

import cn.edu.bioinformatics.netepd.entity.ExpressionMap;

public interface ExpressionMapDao {

	/**
	 * 依据要映射的list集合进行mapping并返回可以匹配到的数据集
	 * @param list
	 * @return List<Object>
	 */
	public List<ExpressionMap> getMappedResult(List<String> list);
}

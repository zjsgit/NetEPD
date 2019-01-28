package cn.edu.bioinformatics.netepd.dao;

import java.util.List;

import cn.edu.bioinformatics.netepd.entity.ExpressionData;

public interface ExpressionDataDao {

	/**
	 * 选择对应物种的基因表达数据集
	 * @param taxid
	 * @return List<DyProtein>
	 */
	public List<ExpressionData> getExpressionByOrganism(String taxid);
	
	public List<String> getUniqueIdByOrganism(String taxid);
}

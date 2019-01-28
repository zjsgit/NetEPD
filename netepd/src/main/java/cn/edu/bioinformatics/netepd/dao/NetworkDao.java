package cn.edu.bioinformatics.netepd.dao;

import java.util.List;

/**
 * 数据集持久化层所具有的共同的方法接口
 * @author Dell
 *
 */
public interface NetworkDao {
	/**
	 * 获取对应物种的不重复ID
	 * @param taxid
	 * @return 返回ID的集合列表
	 */
	public List<String> getUniqueIdByOrganism(String taxid);
	
	/**
	 * 检查数据集是否包含指定的物种
	 * @param taxid
	 * @return 若包含该物种返回ture,否则返回false
	 */
	//public boolean containOrganism(String taxid);
}

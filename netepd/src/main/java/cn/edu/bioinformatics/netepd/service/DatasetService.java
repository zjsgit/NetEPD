package cn.edu.bioinformatics.netepd.service;

import java.util.HashMap;
import java.util.List;

public interface DatasetService {
	
	/**
	 * 选择对应物种的PPI网络数据集
	 * 返回的是边的集合
	 * @param taxid
	 * @return
	 */
	public List<String> getNetworkByTaxid(String taxid);
	
	/**
	 * 获取数据集的InteractorKB映射到InteractorID的集合
	 * @param taxid
	 * @return
	 */
	public HashMap<String, String> getMappingData(String taxid);
}

package cn.edu.bioinformatics.netepd.service;

import java.util.HashMap;
import java.util.List;

public interface ExpressionService {
	
	/**
	 * 选择对应物种的基因表达数据集
	 * 返回的是List<DyProtein>
	 * @param taxid
	 * @return
	 */
	public List<String> getExpressionByTaxid(String taxid, HashMap<String,String> ppiMapingData);
	
	public int getExpressionMapSizeByTaxid(String taxid);
	
}

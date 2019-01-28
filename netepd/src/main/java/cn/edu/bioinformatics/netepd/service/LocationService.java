package cn.edu.bioinformatics.netepd.service;

import java.util.HashMap;
import java.util.List;

public interface LocationService {

	/**
	 * 选择对应物种的亚细胞位置数据集
	 * 返回的是List<Subcellular>
	 * @param taxid
	 * @return
	 */
	public List<String> getLocationByTaxid(String taxid, HashMap<String,String> ppiMapingData);
	
	
	public int getLocationMapSizeByTaxid(String taxid);
	
}

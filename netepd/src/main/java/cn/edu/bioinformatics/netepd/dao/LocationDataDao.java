package cn.edu.bioinformatics.netepd.dao;

import java.util.List;

import cn.edu.bioinformatics.netepd.entity.LocationData;

public interface LocationDataDao {

	/**
	 * 选择对应物种的亚细胞数据集
	 * @param taxid
	 * @return List<Subcellular>
	 */
	public List<LocationData> getLocationByOrganism(String taxid);
	
	
	public List<String> getUniqueIdByOrganism(String taxid);
}

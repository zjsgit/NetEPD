package cn.edu.bioinformatics.netepd.dao;

import cn.edu.bioinformatics.netepd.entity.DegEukaryote;

public interface DegEukaryoteDao {
	/**
	 * 依据DEG_AC值获取对应的一条记录
	 * @param ac
	 * @return
	 */
	public DegEukaryote getByAC(String ac);
}

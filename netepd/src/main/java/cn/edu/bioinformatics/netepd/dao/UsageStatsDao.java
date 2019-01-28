package cn.edu.bioinformatics.netepd.dao;

import java.util.List;

import cn.edu.bioinformatics.netepd.entity.UsageStats;

public interface UsageStatsDao {
	/**
	 * 添加用户使用信息	 
	 * @param usage
	 * @return ID
	 */
	public String addUsage(UsageStats usage);
	
	/**
	 * 获取所有的使用信息
	 * @return 
	 */
	public List<UsageStats> getAllUsage();
}

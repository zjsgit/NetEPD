package cn.edu.bioinformatics.netepd.service;

import java.util.List;

import cn.edu.bioinformatics.netepd.entity.UsageStats;

public interface UsageStatsService {
	
	public void addUsage(String ip, String email);
	
	public List<UsageStats> getAllUsage();
}

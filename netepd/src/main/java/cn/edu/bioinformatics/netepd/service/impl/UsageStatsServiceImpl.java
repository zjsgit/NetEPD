package cn.edu.bioinformatics.netepd.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bioinformatics.netepd.dao.Ip2nationDao;
import cn.edu.bioinformatics.netepd.dao.UsageStatsDao;
import cn.edu.bioinformatics.netepd.entity.UsageStats;
import cn.edu.bioinformatics.netepd.service.UsageStatsService;

@Service("usageStatsService")
public class UsageStatsServiceImpl implements UsageStatsService {
	
	@Autowired
	private Ip2nationDao ip2nationDao;
	@Autowired
	private UsageStatsDao usageStatsDao;

	@Override
	public void addUsage(String ip, String email) {
		
		String[] nation = ip2nationDao.getCountryByIp(ip);
		//System.out.println(nation[0]+","+nation[1]);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String curDate = sdf.format(new Date());
		//System.out.println(curDate);
		
		UsageStats usage = new UsageStats(ip, email, nation[0], nation[1], curDate);
		usageStatsDao.addUsage(usage);
	}

	@Override
	public List<UsageStats> getAllUsage() {
		return usageStatsDao.getAllUsage();
	}

}

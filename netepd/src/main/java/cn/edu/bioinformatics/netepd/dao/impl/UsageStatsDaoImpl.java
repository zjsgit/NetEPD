package cn.edu.bioinformatics.netepd.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.edu.bioinformatics.netepd.dao.UsageStatsDao;
import cn.edu.bioinformatics.netepd.entity.UsageStats;

@Repository("usageStatsDao")
public class UsageStatsDaoImpl implements UsageStatsDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	@Override
	public String addUsage(UsageStats usage) {
		
		String id = getSession().save(usage).toString();
		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UsageStats> getAllUsage() {
		
		String hql = "FROM UsageStats";
		Query query = getSession().createQuery(hql);
		List<UsageStats> list = query.list();
		
		return list;
	}

}

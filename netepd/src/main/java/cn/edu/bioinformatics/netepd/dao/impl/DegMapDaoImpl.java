package cn.edu.bioinformatics.netepd.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.edu.bioinformatics.netepd.dao.DegMapDao;
import cn.edu.bioinformatics.netepd.entity.DegMap;

@Repository("degMapDao")
public class DegMapDaoImpl implements DegMapDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<DegMap> getMappedResult(List<String> list) {
		
		String hql="FROM DegMap degMap WHERE degMap.uniprotkb IN (:alist)";  
		Query query = getSession().createQuery(hql)
				.setParameterList("alist", list);
		List<DegMap> mappedList = query.list();
		
		return mappedList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DegMap> getEPSet() {
		String hql = "FROM DegMap";
		Query query = getSession().createQuery(hql);
		List<DegMap> list = query.list();
		
		return list;
	}

}

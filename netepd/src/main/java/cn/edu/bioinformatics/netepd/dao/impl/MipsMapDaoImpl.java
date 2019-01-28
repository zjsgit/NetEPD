package cn.edu.bioinformatics.netepd.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.edu.bioinformatics.netepd.dao.MipsMapDao;
import cn.edu.bioinformatics.netepd.entity.MipsMap;

@Repository("mipsMapDao")
public class MipsMapDaoImpl implements MipsMapDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<MipsMap> getMappedResult(List<String> list) {
		String hql="FROM MipsMap map WHERE map.mipsId IN (:alist)";  
		Query query = getSession().createQuery(hql)
				.setParameterList("alist", list);
		List<MipsMap> mappedList = query.list();
		
		return mappedList;
	}

}

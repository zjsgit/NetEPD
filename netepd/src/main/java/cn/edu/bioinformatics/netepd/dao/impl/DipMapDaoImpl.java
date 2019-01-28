package cn.edu.bioinformatics.netepd.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.edu.bioinformatics.netepd.dao.DipMapDao;
import cn.edu.bioinformatics.netepd.entity.DipMap;

@Repository("dipMapDao")
public class DipMapDaoImpl implements DipMapDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DipMap> getMappedResult(List<String> list) {
		
		String hql="FROM DipMap map WHERE map.dipId IN (:alist)";  
		Query query = getSession().createQuery(hql)
				.setParameterList("alist", list);
		List<DipMap> mappedList = query.list();
		
		return mappedList;
	}

	
	
}

package cn.edu.bioinformatics.netepd.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.edu.bioinformatics.netepd.dao.BiogridMapDao;
import cn.edu.bioinformatics.netepd.entity.BiogridMap;

@Repository("biogridMapDao")
public class BiogridMapDaoImpl implements BiogridMapDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BiogridMap> getMappedResult(List<String> list) {
		
		String hql="FROM BiogridMap map WHERE map.biogridId IN (:alist)";  
		Query query = getSession().createQuery(hql)
				.setParameterList("alist", list);
		List<BiogridMap> mappedList = query.list();
		
		return mappedList;
	}

}

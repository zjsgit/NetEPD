package cn.edu.bioinformatics.netepd.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.edu.bioinformatics.netepd.dao.LocationMapDao;
import cn.edu.bioinformatics.netepd.entity.LocationMap;

@Repository("locationMapDao")
public class LocationMapDaoImpl implements LocationMapDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<LocationMap> getMappedResult(List<String> list) {
		// TODO Auto-generated method stub
		
		String hql="FROM LocationMap map WHERE map.locationId IN (:alist)";  
		Query query = getSession().createQuery(hql)
				.setParameterList("alist", list);
		List<LocationMap> mappedList = query.list();
		
		return mappedList;
	}

}

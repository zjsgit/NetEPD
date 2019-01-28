package cn.edu.bioinformatics.netepd.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.edu.bioinformatics.netepd.dao.LocationDataDao;
import cn.edu.bioinformatics.netepd.entity.DipNetwork;
import cn.edu.bioinformatics.netepd.entity.LocationData;

@Repository("locationDataDao")
public class LocationDataDaoImpl implements LocationDataDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<LocationData> getLocationByOrganism(String taxid) {
		// TODO Auto-generated method stub
		
		String hql = "FROM LocationData location WHERE location.organismId = :org";

		Query query = getSession().createQuery(hql).setParameter("org", taxid);
		List<LocationData> list = query.list();
		
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> getUniqueIdByOrganism(String taxid) {
		// TODO Auto-generated method stub
		
		String hql = "SELECT PROTEIN_NAME FROM locationdata WHERE ORGANISM_Id = :org";
		Query query = getSession().createSQLQuery(hql).setParameter("org", taxid);
		List<String> list = query.list();
		
		return list;
	}

	
}

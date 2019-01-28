package cn.edu.bioinformatics.netepd.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.edu.bioinformatics.netepd.dao.BiogridNetworkDao;
import cn.edu.bioinformatics.netepd.entity.BiogridNetwork;

@Repository("biogridNetworkDao")
public class BiogridNetworkDaoImpl implements BiogridNetworkDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<BiogridNetwork> getNetworkByOrganism(String taxid) {
		
		String hql = "FROM BiogridNetwork net WHERE net.organismAid = :org OR net.organismBid = :org";
		
		Query query = getSession().createQuery(hql)
				.setParameter("org", taxid);
		List<BiogridNetwork> list = query.list();
		
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> getUniqueIdByOrganism(String taxid) {
		
		String sql = "SELECT BIOGRID_ID_A FROM biogridnetwork WHERE ORGANISM_ID_A = :org OR ORGANISM_ID_B = :org "
				+ "UNION SELECT BIOGRID_ID_B FROM biogridnetwork WHERE ORGANISM_ID_A = :org OR ORGANISM_ID_B = :org";
		
		Query query = getSession().createSQLQuery(sql)
				.setParameter("org", taxid);
		List<String> list = query.list();
		
		return list;
	}
	
}

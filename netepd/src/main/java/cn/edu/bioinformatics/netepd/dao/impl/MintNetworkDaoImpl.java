package cn.edu.bioinformatics.netepd.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.edu.bioinformatics.netepd.dao.MintNetworkDao;
import cn.edu.bioinformatics.netepd.entity.MintNetwork;

@Repository("mintNetworkDao")
public class MintNetworkDaoImpl implements MintNetworkDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<MintNetwork> getNetworkByOrganism(String taxid) {
		
		String hql = "FROM MintNetwork net WHERE net.hostId = :org";

		Query query = getSession().createQuery(hql).setParameter("org", taxid);
		List<MintNetwork> list = query.list();

		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> getUniqueIdByOrganism(String taxid) {
		String hql = "SELECT INTERACTOR_ID_A FROM mintnetwork WHERE HOST_ORGANISM_ID = :org "
				+ "UNION SELECT INTERACTOR_ID_B FROM mintnetwork WHERE HOST_ORGANISM_ID = :org";
		
		Query query = getSession().createSQLQuery(hql)
				.setParameter("org", taxid);
		List<String> list = query.list();
		return list;
	}

}

package cn.edu.bioinformatics.netepd.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.edu.bioinformatics.netepd.dao.DipNetworkDao;
import cn.edu.bioinformatics.netepd.entity.DipNetwork;

@Repository("dipNetworkDao")
public class DipNetworkDaoImpl implements DipNetworkDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DipNetwork> getNetworkByOrganism(String taxid) {
		String hql = "FROM DipNetwork net WHERE net.organismAid = :org OR net.organismBid = :org";

		Query query = getSession().createQuery(hql).setParameter("org", taxid);
		List<DipNetwork> list = query.list();

		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> getUniqueIdByOrganism(String taxid) {
		String hql = "SELECT DIP_ID_A FROM dipnetwork WHERE ORGANISM_ID_A = :org OR ORGANISM_ID_B = :org "
				+ "UNION SELECT DIP_ID_B FROM dipnetwork WHERE ORGANISM_ID_A = :org OR ORGANISM_ID_B = :org";

		Query query = getSession().createSQLQuery(hql).setParameter("org", taxid);
		List<String> list = query.list();
		return list;
	}

}

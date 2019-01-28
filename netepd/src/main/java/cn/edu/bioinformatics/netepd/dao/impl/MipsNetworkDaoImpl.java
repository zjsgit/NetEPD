package cn.edu.bioinformatics.netepd.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.edu.bioinformatics.netepd.dao.MipsNetworkDao;
import cn.edu.bioinformatics.netepd.entity.MipsNetwork;

@Repository("mipsNetworkDao")
public class MipsNetworkDaoImpl implements MipsNetworkDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<MipsNetwork> getNetworkByOrganism(String taxid) {
		
		String hql = "FROM MipsNetwork net WHERE net.organismAid = :org OR net.organismBid = :org";
		Query query = getSession().createQuery(hql)
				.setParameter("org", taxid);
		List<MipsNetwork> list = query.list();
		
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> getUniqueIdByOrganism(String taxid) {
		
		String sql = "SELECT MIPS_ID_A FROM mipsnetwork WHERE ORGANISM_ID_A = :org OR ORGANISM_ID_B = :org "
				+ "UNION SELECT MIPS_ID_B FROM mipsnetwork WHERE ORGANISM_ID_A = :org OR ORGANISM_ID_B = :org";
		
		Query query = getSession().createSQLQuery(sql)
				.setParameter("org", taxid);
		List<String> list = query.list();
		
		return list;
	}

}

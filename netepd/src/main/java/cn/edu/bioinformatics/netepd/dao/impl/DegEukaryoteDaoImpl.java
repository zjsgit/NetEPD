package cn.edu.bioinformatics.netepd.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.edu.bioinformatics.netepd.dao.DegEukaryoteDao;
import cn.edu.bioinformatics.netepd.entity.DegEukaryote;

@Repository("degEukaryoteDao")
public class DegEukaryoteDaoImpl implements DegEukaryoteDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public DegEukaryote getByAC(String ac) {
		
		String hql = "FROM DegEukaryote deg WHERE deg.degAC = ?";
		
		Query query = getSession().createQuery(hql)
				.setString(0, ac);
		
		return (DegEukaryote) query.uniqueResult();
	}

}

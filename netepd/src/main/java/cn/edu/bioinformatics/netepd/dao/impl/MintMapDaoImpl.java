package cn.edu.bioinformatics.netepd.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.edu.bioinformatics.netepd.dao.MintMapDao;
import cn.edu.bioinformatics.netepd.entity.MintMap;

@Repository("mintMapDao")
public class MintMapDaoImpl implements MintMapDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<MintMap> getMappedResult(List<String> list) {

		String hql="FROM MintMap map WHERE map.geneId IN (:alist)";  
		Query query = getSession().createQuery(hql)
				.setParameterList("alist", list);
		List<MintMap> mappedList = query.list();
		
		return mappedList;
	}

}

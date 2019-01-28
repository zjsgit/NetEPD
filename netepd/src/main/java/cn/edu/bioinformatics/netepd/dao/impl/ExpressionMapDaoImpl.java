package cn.edu.bioinformatics.netepd.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.edu.bioinformatics.netepd.dao.ExpressionMapDao;
import cn.edu.bioinformatics.netepd.entity.ExpressionMap;

@Repository("expressionMapDao")
public class ExpressionMapDaoImpl implements ExpressionMapDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ExpressionMap> getMappedResult(List<String> list) {
		// TODO Auto-generated method stub
		
		String hql="FROM ExpressionMap map WHERE map.expressionId IN (:alist)";  
		Query query = getSession().createQuery(hql)
				.setParameterList("alist", list);
		List<ExpressionMap> mappedList = query.list();
		
		return mappedList;
	}

}

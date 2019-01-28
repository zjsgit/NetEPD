package cn.edu.bioinformatics.netepd.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.edu.bioinformatics.netepd.dao.ExpressionDataDao;
import cn.edu.bioinformatics.netepd.entity.ExpressionData;
import cn.edu.bioinformatics.netepd.entity.LocationData;

@Repository("expressionDataDao")
public class ExpressionDataDaoImpl implements ExpressionDataDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * 
	 * @param 物种ID
	 * @return 对应物种的基因表达值
	 * @author JiashuaiZhang
	 */
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ExpressionData> getExpressionByOrganism(String taxid) {
		// TODO Auto-generated method stub
		
		String hql = "FROM ExpressionData expression WHERE expression.organismId = :org";

		Query query = getSession().createQuery(hql).setParameter("org", taxid);
		List<ExpressionData> list = query.list();
		
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> getUniqueIdByOrganism(String taxid) {
		// TODO Auto-generated method stub
		
		String hql = "SELECT GENE_NAME FROM expressiondata WHERE ORGANISM_Id = :org";
		Query query = getSession().createSQLQuery(hql).setParameter("org", taxid);
		List<String> list = query.list();
		
		return list;
	}

}

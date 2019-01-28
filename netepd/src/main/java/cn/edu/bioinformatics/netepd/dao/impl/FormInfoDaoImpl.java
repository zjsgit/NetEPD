package cn.edu.bioinformatics.netepd.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.edu.bioinformatics.netepd.dao.FormInfoDao;
import cn.edu.bioinformatics.netepd.entity.FormInfo;

@Repository("formInfoDao")
public class FormInfoDaoImpl implements FormInfoDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public String saveFormInfo(FormInfo info) {
		String id = getSession().save(info).toString();
		//System.out.println(id);
		return id;
	}

	@Override
	public FormInfo queryById(int id) {
		String hql = "FROM FormInfo f WHERE f.id = ?";
		Query query = getSession().createQuery(hql)
						.setInteger(0, id);
		FormInfo info = (FormInfo) query.uniqueResult();
		return info;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<FormInfo> queryByJobId(int jobId) {
		String hql = "FROM FormInfo f WHERE f.jobId = ?";
		Query query = getSession().createQuery(hql)
						.setString(0, hql);
		List<FormInfo> infos = query.list();
		return infos;
	}

}

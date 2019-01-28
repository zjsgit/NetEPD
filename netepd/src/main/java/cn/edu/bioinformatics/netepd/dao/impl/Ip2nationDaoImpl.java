package cn.edu.bioinformatics.netepd.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.edu.bioinformatics.netepd.dao.Ip2nationDao;

@Repository("ip2nationDao")
public class Ip2nationDaoImpl implements Ip2nationDao {
	
	@Autowired
	private SessionFactory sesssionFactory;
	public Session getSession(){
		return sesssionFactory.getCurrentSession();
	}

	@Override
	public String[] getCountryByIp(String ip) {
		
		String sql = "SELECT c.code,c.country FROM ip2nationCountries c,"
                + "ip2nation i WHERE i.ip < INET_ATON('"+ip+"') "
                + "AND c.code = i.country ORDER BY i.ip DESC LIMIT 0,1";
		
		Query query = getSession().createSQLQuery(sql);
		Object[] obj = (Object[]) query.uniqueResult();
		
		String nation[] = new String[2];
		nation[0] = (String) obj[0];
		nation[1] = (String) obj[1];
		
		return nation;
	}

}

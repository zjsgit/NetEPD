package cn.edu.bioinformatics.netepd.test;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EpFinderTestCon {
	
	private ApplicationContext ctx = null;
	private SessionFactory sessionFactory = null;
	private Session session = null;
	private Transaction transaction = null;
	
	@Before
	public void init() {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//		System.out.println(ctx);
		
		//测试SessionFactory
		sessionFactory = ctx.getBean(SessionFactory.class);
//		System.out.println("sessionFactory:"+sessionFactory);
		//测试操作数据库表
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		
	}
	
	@After
	public void destory() {
		transaction.commit();
		session.close();
		sessionFactory.close();
	}
	
	@Test
	public void testDataSource() throws SQLException {
		DataSource dataSource = ctx.getBean(DataSource.class);
		System.out.println("datasource:"+dataSource.getConnection());
		/*
		List<String> mapped = new ArrayList<>();
		mapped.add("P34111");
		mapped.add("P10962");
		mapped.add("P28005");
		mapped.add("P00549");
		mapped.add("Q01329");
		mapped.add("123");
		
		
		String hql="FROM DegMap map WHERE map.uniprotkb IN (:alist)";
		Query query = session.createQuery(hql).setParameterList("alist", mapped);
		List<DegMap> list = query.list();
		
		for (DegMap degMap : list) {
			System.out.println(degMap.getDegAC()+"\t"+degMap.getUniprotkb());
		}
		
		String hql = "SELECT BIOGRID_ID_A FROM biogridnetwork WHERE ORGANISM_ID_A = :org OR ORGANISM_ID_B = :org "
				+ "UNION SELECT BIOGRID_ID_B FROM biogridnetwork WHERE ORGANISM_ID_A = :org OR ORGANISM_ID_B = :org";
		
		Query query = session.createSQLQuery(hql).setParameter("org", "559292");
		List<String> list = query.list();
		System.out.println(list.size());
		*/
		System.out.println("----END----");
	}

}

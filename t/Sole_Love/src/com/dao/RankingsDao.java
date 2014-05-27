package com.dao;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import javax.persistence.Entity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.model.Rankings;

@Repository
public class RankingsDao{

	@Resource
	SessionFactory sessionFactory;

	/**
	 * ʹ�����а�
	 * 
	 * @return
	 */
	@Transactional
	public List<Rankings> findRankingInfo() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Rankings ORDER BY saleroom DESC");
		List<Rankings> list = query.list();
		if (list.size() != 0) {
			return list;
		}
		return null;
	}
	/**
	 * ����ʹ�����а� 1.�õ����۶�������۶���ȷ������ 2.�õ��û��������С�ʱ��
	 */
	@Transactional
	@Scheduled(cron = "59 59 23 * * ?")     //ÿ��23��59��59��������а�
	public void updateRankingInfo() {
		System.out.println("=======================>");
		/*Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createSQLQuery("SELECT SUM(price),u.nickname,u.city FROM orders o,orderdetails od,users u WHERE u.id=o.uid AND o.id=od.oid  AND paied =0 AND returned=0 GROUP BY u.id");
		List list = query.list();
		Iterator i = list.iterator();
		while (i.hasNext()) {
			Object[] obj = (Object[]) i.next();// ����ͨ����ǿ��ת����Object���飬������ÿ���Ӧ�����ÿ��

			
			 * System.out.println(obj[0] + " price-----------------");
			 * System.out.println(obj[1] + " nickname-----------------");
			 * System.out.println(obj[2] + " city-----------------");
			 
			Rankings r = new Rankings();
			r.setSaleroom(Integer.parseInt(obj[0].toString()));
			r.setName(obj[1].toString());
			r.setCity(obj[2].toString());
			r.setUpdatetime(new Timestamp(System.currentTimeMillis()));
			session.update(r);
		}*/
	}
}

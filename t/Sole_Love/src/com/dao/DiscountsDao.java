package com.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import javax.persistence.Entity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.model.Discounts;
import com.model.Orders;
import com.model.Salers;
import com.model.Users;
import com.util.FirstAndLastDate;

@Repository
public class DiscountsDao {

	@Resource
	SessionFactory sessionFactory;

	/**
	 * ��ǰ�û�ÿ�·����¼
	 * 
	 * @param salerid
	 * @param month
	 * @return discounts
	 */
	@Transactional
	public List<Discounts> findMonthDisInfo(int salerid,String month) {
		SimpleDateFormat sf=new SimpleDateFormat("yyyy");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Discounts where sid=? AND creationtime LIKE '%"+sf.format(new Date())+"-0"+month+"%'");
		query.setInteger(0, salerid);
		List<Discounts> list = query.list();
		if (list.size() != 0) {
			return list;
		}
		return null;
	}
	
	/**
	 * ��ǰ�û�ÿ���ܷ����¼
	 * 
	 * @param salerid
	 * @param year
	 * @return discounts
	 */
	@Transactional
	public List<Discounts> findYearDisInfo(int salerid,String year) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Discounts where sid=? AND creationtime LIKE '%"+year+"%'");
		query.setInteger(0, salerid);
		List<Discounts> list = query.list();
		if (list.size() != 0) {
			return list;
		}
		return null;
	}
	
	/**
	 * ���·����¼
	 * 1.��õ��µ����۶�
	 * 2.���ݵ��µ����۶���·����¼
	 */
	@Transactional
	@Scheduled(cron = "59 59 23 * * ?")     //ÿ��23��59��59����·����¼
	public void updateDisInfo(){
		Session session = sessionFactory.getCurrentSession();
		Query query=null;
		StringBuilder sb=new StringBuilder();
		sb.append("SELECT s.uid,SUM(price)");
		sb.append(" FROM orders o,orderdetails od,users u");
		sb.append(" WHERE u.id=o.uid");
		sb.append(" AND o.id=od.oid");
		sb.append(" AND u.id=s.uid");
		sb.append(" AND o.paied =0");
		sb.append(" AND o.returned=0");
		sb.append(" AND o.creationtime BETWEEN DATE_FORMAT(?,'%Y-%m-%d')  AND DATE_FORMAT(?,'%Y-%m-%d')");
		sb.append(" GROUP BY u.id");
		query=session.createSQLQuery(sb.toString());
		FirstAndLastDate fd=new FirstAndLastDate();
		query.setString(0, fd.firstDate());
		query.setString(1, fd.lastDate());
		List list = query.list();
		Iterator i = list.iterator();
		while (i.hasNext()) {
			Object[] obj = (Object[]) i.next();
			Discounts discounts=new Discounts();
			discounts.setSid(Integer.parseInt(obj[0].toString()));
			discounts.setAmount((Float)obj[1]);
			discounts.setUpdatetime(new Timestamp(System.currentTimeMillis()));
			session.update(discounts);
		}
	}
	/**
	 * ÿ�µ�һ�촴�����µķ����¼
	 */
	@Transactional
	@Scheduled(cron = "0 0 0 1 * ?")     //ÿ��1��0��0�봴���·����¼
	public void createDisInfo() {
		Session session = sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Salers");
		List<Salers> salerid=query.list();
		for (int i = 0; i < salerid.size(); i++) {
			Discounts discounts = new Discounts();
			discounts.setSid(salerid.get(i).getUid());
			discounts.setAmount(0f);
			discounts
					.setCreationtime(new Timestamp(System.currentTimeMillis()));
			discounts.setUpdatetime(new Timestamp(System.currentTimeMillis()));
			Serializable sb1 = session.save(discounts);
			if (sb1 == null) {
				System.out.println("���������¼ʧ��");
			}
		}
	}
	@Transactional
	public List findMonthOrders(String year,String month){
		Session session = sessionFactory.getCurrentSession();
		StringBuilder sb=new StringBuilder();
		sb.append("SELECT o.id,u.nickname,SUM(os.price),o.city");
		sb.append(" FROM orders o,users u,orderdetails os");
		sb.append(" WHERE o.uid=u.id ");
		sb.append(" AND o.id=os.oid");
		sb.append(" AND o.creationtime BETWEEN DATE_FORMAT(?,'%Y-%m-%d')  AND DATE_FORMAT(?,'%Y-%m-%d')");
		sb.append(" GROUP BY o.id");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// �������ڸ�ʽ
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.MONTH, Integer.parseInt(month));
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date lastDate = cal.getTime();//ĳ��ĳ�����һ��

		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date firstDate = cal.getTime();//ĳ��ĳ�µ�һ��
		
		Query query=session.createSQLQuery(sb.toString());
		query.setString(0, df.format(firstDate));
		query.setString(1, df.format(lastDate));
		List list=query.list();
		if (list.size()!=0) {
			return list;
		}
		return null;
	}
}

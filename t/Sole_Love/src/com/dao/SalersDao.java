package com.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import javax.persistence.Entity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.model.Discounts;
import com.model.Invcodes;
import com.model.Rankings;
import com.model.Salers;
import com.model.Users;

@Repository
public class SalersDao {
	/**
	 * �����û�ID��ѯ�ײ�ʹ����Ϣ
	 */
	@Resource
	SessionFactory sessionFactory;

	/**
	 * �ײ�ʹ����Ϣ
	 * 
	 * @param userid
	 * @return
	 */
	@Transactional
	public Salers findSalersInfo(int userid) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Salers where uid=?");
		query.setInteger(0, userid);
		Salers salers = (Salers) query.uniqueResult();
		if (salers != null)
			return salers;
		else
			return null;
	}

	/**
	 * ע���ײ�ʹ��
	 * 
	 * �������а�
	 */
	@Transactional
	public Salers addSalersInfo(int userid, String idcode, String invcodes) {
		Session session = sessionFactory.getCurrentSession();
		Salers s = new Salers();
		s.setUid(userid);
		// s.setCode(code); ��̨�����Żݴ���
		s.setIdcode(idcode);
		s.setCreationtime(new Timestamp(System.currentTimeMillis()));
		s.setUpdatetime(new Timestamp(System.currentTimeMillis()));
		// �����ײ�ʹ����Ϣ
		Serializable sb = session.save(s);
		joinRanking(sb, userid);// �������а�
		joinDiscounts(userid);// ���������¼
		if (sb != null) {
			Query query = session.createQuery("from Salers where uid=?");
			query.setInteger(0, userid);
			s = (Salers) query.uniqueResult();
			// �޸�������ʹ����
			query = session.createQuery("from Invcodes where code=?");
			query.setString(0, invcodes);
			Invcodes i = new Invcodes();
			i.setSid(s.getId());
			session.update(i);
			return s;
		}
		return null;
	}

	/**
	 * �������а�
	 */
	public void joinRanking(Serializable sb, int userid) {
		Session session = sessionFactory.getCurrentSession();
		if (sb != null) {
			Query q = session.createQuery("from Users where id=?");
			q.setInteger(0, userid);
			Users u = (Users) q.uniqueResult();
			Rankings r = new Rankings();
			r.setSaleroom(0.0);
			r.setName(u.getNickname());
			r.setCity(u.getCity());
			r.setCreationtime(new Timestamp(System.currentTimeMillis()));
			r.setUpdatetime(new Timestamp(System.currentTimeMillis()));
			Serializable sb1 = session.save(r);
			if (sb1 == null) {
				System.out.println("�������а�ʧ��");
			}
		}
	}

	/**
	 * ���������¼
	 */
	public void joinDiscounts(int userid) {
		Session session = sessionFactory.getCurrentSession();
		if (userid != 0) {
			Discounts discounts = new Discounts();
			discounts.setSid(userid);
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

	/**
	 * �������Ƿ����
	 */
	@Transactional
	public boolean findInvcodesInfo(String invcodes) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Invcodes where code=? and sid=0");
		query.setString(0, invcodes);
		Invcodes i = (Invcodes) query.uniqueResult();
		if (i != null)
			return true;
		else
			return false;
	}
}

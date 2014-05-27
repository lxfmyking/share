package com.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import javax.persistence.Entity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.model.Messages;

@Repository
public class MessagesDao {

	@Resource
	SessionFactory sessionFactory;

	/**
	 * վ����
	 * 
	 * @param userid
	 * @return
	 */
	@Transactional
	public List<Messages> findMsgInfo(int userid) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Messages where uid=?");
		query.setInteger(0, userid);
		List<Messages> list = query.list();
		return list;
	}

	/**
	 * �Ѷ�վ���Ÿı��Ѷ�����
	 * 
	 * @param userid
	 * @return
	 */
	@Transactional
	public List<Messages> readMesInfo(int userid, int id) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Messages where id=? and uid=?");
		query.setInteger(0, id);
		query.setInteger(1, userid);
		Messages msg = (Messages) query.uniqueResult();
		msg.setDelFlag(0);
		session.update(msg);
		Query query1 = session.createQuery("from Messages where uid=?");
		query.setInteger(0, userid);
		List<Messages> list = query.list();
		return list;
	}

	/**
	 * �½�վ����
	 * 
	 * @return
	 */
	@Transactional
	public boolean addMsgInfo(Messages messages) {
		Session session = sessionFactory.getCurrentSession();
		Serializable sb = session.save(messages);
		if (sb != null) {
			return true;
		}
		return false;
	}

	/**
	 * ɾ��վ����
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public List<Messages> delMsgInfo(int[] id, int userid) {
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		for (int i = 0; i < id.length; i++) {
			query = session.createQuery("from Messages where id=? and uid=?");
			query.setInteger(0, id[i]);
			query.setInteger(1, userid);
			Messages msg = (Messages) query.uniqueResult();
			session.delete(msg);
		}
		query = session.createQuery("from Messages where uid=?");
		query.setInteger(0, userid);
		List<Messages> list = query.list();
		return list;
	}
}

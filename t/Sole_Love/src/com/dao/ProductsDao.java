package com.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import javax.persistence.Entity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.model.Orderdetails;
import com.model.Products;

@Repository
public class ProductsDao {
	@Resource
	SessionFactory sessionFactory;

	/**
	 * ���в�Ʒ�б�
	 * 
	 * @return
	 */
	@Transactional
	public List<Products> findProductsInfo() {
		Session session = sessionFactory.getCurrentSession();// ���session
		Query query = session.createQuery("from Products");// ��ѯProducts����
		List<Products> list = query.list();// ���list����
		return list;
	}

	/**
	 * ��ǰ�û������������Ʒ
	 */
	@Transactional
	public List<Products> findProductsInfo(List<Orderdetails> detailsList) {
		Session session = sessionFactory.getCurrentSession();// ���session
		List<Products> list = new ArrayList<Products>();
		Query query = null;
		for (int i = 0; i < detailsList.size(); i++) {
			query = session.createQuery("from Products where id=?");// ��ѯProducts����
			query.setInteger(0, detailsList.get(i).getId());
			list.add((Products) query.uniqueResult());
		}
		if (list.size() != 0) {
			return list;
		}
		return list;
	}
}

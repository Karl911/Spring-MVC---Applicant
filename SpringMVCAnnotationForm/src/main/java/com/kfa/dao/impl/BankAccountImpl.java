package com.kfa.dao.impl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import com.kfa.dao.BankAccountDAO;
import com.kfa.entity.Applicant;
import com.kfa.entity.BankAccount;
import com.kfa.model.ApplicantModel;
import com.kfa.model.BankAccountModel;

public class BankAccountImpl implements BankAccountDAO {
	
	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public BankAccount findBankAccount(Long id) {
		Session session = sessionFactory.getCurrentSession();
		
		return session.find(BankAccount.class, id);
		
		/*
		 Criteria crit = session.createCriteria(BankAccount.class);
		 crit.add(Restrictions.eq("id", id));
		 return (BankAccount) crit.uniqueResult();
		 */
	}
	
	/*
	public Applicant findApplicant(String id) {
		 Session session = sessionFactory.getCurrentSession();
		 Criteria crit = session.createCriteria(Applicant.class);
		 crit.add(Restrictions.eq("id", id));
		 return (Applicant) crit.uniqueResult();
	}
	*/

	@Override
	public BankAccountModel findBankAccountModel(Long id) {
		
		BankAccountModel bankAccountModel = null;
		BankAccount bankAccount = findBankAccount(id);
		
		if (null != bankAccount)
		{
			bankAccountModel = new BankAccountModel();
			bankAccountModel.setId(id);
			bankAccountModel.setFullName(bankAccount.getFullName());
			bankAccountModel.setBalance(bankAccount.getBalance());
		}
		return bankAccountModel;
	}

	@Override
	public List<BankAccountModel> listBankAccountsModels() {
		
		String sql = "Select new "+BankAccountModel.class.getName()+
		"(e.id, e.fullName, e.balance) from "+BankAccount.class.getName()+" e ";
		
		Session session = sessionFactory.getCurrentSession();
		// 1ère méthode (basique)
		//Query query = session.createQuery(sql);
		
		// 2nd méthode
		Query<BankAccountModel> query = session.createQuery(sql, BankAccountModel.class);
		List<BankAccountModel> list = query.getResultList();
		
		/*
		List<BankAccountModel> sortedList = list.stream().sorted(new java.util.Comparator<BankAccountModel>() {
			public int compare(BankAccountModel bam1, BankAccountModel bam2)
			{
				int id1 = bam1.getId().intValue();
				int id2 = bam2.getId().intValue();
				return id2-id1;
			}
		}).collect(Collectors.toList());
		*/
		
		return list;
		
		/*
		 String sql = "Select new " + ApplicantModel.class.getName()//
				 + "(a.id, a.name, a.email, a.gender, a.position, a.skills) "//
				 + " from " + Applicant.class.getName() + " a ";
		 Session session = sessionFactory.getCurrentSession();
		 Query query = session.createQuery(sql);
		 return query.list();
		 */
	}

	@Override
	public void saveAccount(BankAccountModel bankAccountModel) {
		
		Long id = bankAccountModel.getId();
		BankAccount bankAccount = null;
		boolean isNew = false;
		
		if (id != null)
		{
			bankAccount = findBankAccount(id);
		}
		else {
			// id = null
			isNew = true;
			// for id type String not auto increment use : 
			//bankAccount.setId(UUID.randomUUID().toString());
			bankAccount = new BankAccount();
		}
		bankAccount.setFullName(bankAccountModel.getFullName());
		bankAccount.setBalance(bankAccountModel.getBalance());
		
		if (isNew)
		{
			Session session = this.sessionFactory.getCurrentSession();
			session.persist(bankAccount);
		}
	}

	@Override
	public void delete(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		BankAccount bankAccount = findBankAccount(id);
		session.delete(bankAccount);
	}

}

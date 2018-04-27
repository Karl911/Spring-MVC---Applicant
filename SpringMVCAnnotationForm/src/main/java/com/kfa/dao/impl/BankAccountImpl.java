package com.kfa.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import com.kfa.dao.BankAccountDAO;
import com.kfa.entity.BankAccount;
import com.kfa.model.BankAccountModel;

public class BankAccountImpl implements BankAccountDAO {
	
	@Autowired
    private SessionFactory sessionFactory;
	
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

	public void delete(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		BankAccount bankAccount = findBankAccount(id);
		session.delete(bankAccount);
	}

	public void transfertMoney(double amount, Long sourceAccountId, Long targetAccountId) {
	
		// TODO : débiter du montant "amount" le compte sourceAccountId
		
		// TODO : créditer du montant "amount" le compte targetAccountId
		addAmount(sourceAccountId, -amount);
		addAmount(targetAccountId, amount);
		
	}
	private void addAmount(Long accountId, double amount)
	{
		Session session = sessionFactory.getCurrentSession();
		
		//BankAccountModel bankAccountModel = findBankAccountModel(accountId);
		
		BankAccount bankAccount = findBankAccount(accountId);
		
		double balance = bankAccount.getBalance();
		balance += amount;
		bankAccount.setBalance(balance);
		//session.save(bankAccountModel);
		session.persist(bankAccount);
	}

	public void generateAccounts(int nbAccounts) {
		
		Session session = sessionFactory.getCurrentSession();
		for (int i=0;i<nbAccounts;i++)
		{
			BankAccount bankAccount = new BankAccount();
			bankAccount.setFullName("A_"+i);
			session.persist(bankAccount);
		}
		
	}
	
	 // MANDATORY: Transaction must be created before.
	/*
    @Transactional(propagation = Propagation.MANDATORY )
    public void addAmount(Long id, double amount) throws BankTransactionException {
        BankAccount account = this.findById(id);
        if (account == null) {
            throw new BankTransactionException("Account not found " + id);
        }
        double newBalance = account.getBalance() + amount;
        if (account.getBalance() + amount < 0) {
            throw new BankTransactionException(
                    "The money in the account '" + id + "' is not enough (" + account.getBalance() + ")");
        }
        account.setBalance(newBalance);
    }
    */

}

package com.kfa.demo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kfa.demo.entity.BankAccount;
import com.kfa.demo.exception.BankTransactionException;
import com.kfa.demo.model.BankAccountInfo;

@Repository
@Transactional
public class BankAccountDAO {
	
	@Autowired
	//private EntityManager entityManager;
	private SessionFactory sessionFactory;
	
	public BankAccountDAO () {
		
	}
	
	public BankAccount findById(Long id)
	{
		Session session = this.sessionFactory.getCurrentSession();
		//return this.entityManager.find(BankAccount.class, id);
		return session.get(BankAccount.class, id);
	}
	
	//@SuppressWarnings("unchecked")
	/*
	public List<BankAccountInfo> listBankAccountInfo()
	{
		String sql = "Select new "+ BankAccountInfo.class.getName() //
				+ "(e.id, e.fullName, e.balance)"//
				+ " from "+BankAccountInfo.class.getName()+ " e";	
		Query query = entityManager.createQuery(sql, BankAccountInfo.class);
        return query.getResultList();
	}
	*/
	
	 public List<BankAccountInfo> listBankAccountInfo() {
	        String sql = "Select new " + BankAccountInfo.class.getName() //
	        		+ "(e.id,e.fullName,e.balance) " //
	                + " from " + BankAccount.class.getName() + " As e ";
	        Session session = this.sessionFactory.getCurrentSession();
	        //Query query = entityManager.createQuery(sql, BankAccountInfo.class);
	        Query<BankAccountInfo> query = session.createQuery(sql, BankAccountInfo.class);
	        
	        return query.getResultList();
	       
	 }
	
	 // MANDATORY: Transaction must be created before.
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
 
    // Do not catch BankTransactionException in this method.
    @Transactional(propagation = Propagation.REQUIRES_NEW, 
                        rollbackFor = BankTransactionException.class)
    public void sendMoney(Long fromAccountId, Long toAccountId, double amount) throws BankTransactionException {
 
        addAmount(toAccountId, amount);
        addAmount(fromAccountId, -amount);
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW, 
            rollbackFor = BankTransactionException.class)
    public void createAccount(String fullname, double balance) throws BankTransactionException {
    	
		// create new account
		BankAccount account = new BankAccount();
		account.setFullName(fullname);
		account.setBalance(balance);

    		//Session session = this.sessionFactory.openSession();
    		Session sess = this.sessionFactory.getCurrentSession();
    		
    		sess.save(account);

    		// INSERT INTO `bank`.`BANK_ACCOUNT` (`ID`, `FULL_NAME`, `BALANCE`) VALUES (NULL, 'Karl', '5000');
	     //   String sql = "INSERT INTO  " + BankAccountInfo.class.getName() //
	       // 		+ "(e.id,e.fullName,e.balance) VALUES (NULL,'"+fullname+"',"+balance+"')" + BankAccount.class.getName() + " As e ";
	        //Query query = entityManager.createQuery(sql, BankAccountInfo.class);
    }

}

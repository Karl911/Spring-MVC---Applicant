package com.kfa.dao;

import java.util.List;

import com.kfa.entity.BankAccount;
import com.kfa.model.BankAccountModel;

public interface BankAccountDAO {
	
		public BankAccount findBankAccount(Long id);
		public BankAccountModel findBankAccountModel(Long id);
		public List<BankAccountModel> listBankAccountsModels();
		public void saveAccount(BankAccountModel bankAccountModel);
		public void delete(Long id);
		public void transfertMoney(double amount, Long sourceAccountId, Long targetAccountId);
		public void generateAccounts(int nbAccounts);

}

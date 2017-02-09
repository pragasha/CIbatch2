package com.capgemini.repository;

import java.util.ArrayList;

import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.model.Account;

public class AccountRepositoryImpl {
	
	ArrayList<Account> accounts =new ArrayList<Account>();
	
	
	boolean save(Account account){
		accounts.add(account);
		return true;
		
	}
	
	
	public Account searchAccount(int accountNumber)throws InvalidAccountNumberException
	{
		for(Account account:accounts)
		{
			if(account.getAccountNumber()==accountNumber)
			{
				return account;
			}
		}
		
		throw new InvalidAccountNumberException();
	}

}

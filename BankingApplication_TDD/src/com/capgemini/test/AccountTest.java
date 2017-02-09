package com.capgemini.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.model.Account;
import com.capgemini.repository.AccountRepository;
import com.capgemini.service.AccountService;
import com.capgemini.service.AccountServiceImpl;

public class AccountTest {

	@Mock
	AccountRepository accountRepository;
	
	AccountService accountService;
	
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		accountService = new AccountServiceImpl(accountRepository);
	}
	
	
	/*
	 * create account
	 * 1.when the amount is less than 500 system should throw exception
	 * 2.when the valid info is passed account should be created successfully
	 */

	@Test(expected=com.capgemini.exceptions.InsufficientInitialAmountException.class)
    public void whenTheAmountIsLessThanFiveHundredSystemShouldThrowException() throws InsufficientInitialAmountException
    {
		accountService.createAccount(101, 400);
    }
	
	@Test
	public void whenTheValidInfoIsPassedAccountShouldBeCreatedSuccessfully() throws InsufficientInitialAmountException
	{
		Account account =new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		
		when(accountRepository.save(account)).thenReturn(true);
		assertEquals(account,accountService.createAccount(101, 5000));
	}
	
	
	/*
	 * Deposit amount
	 * 1.when account is not found throw InvalidAccountNumberException
	 * 2.when accountNumber is present, amount is added successfully
	 */
	
	@Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void whenAccountIsNotPresentSystemShouldThrowInvalidAccountNumberException() throws InvalidAccountNumberException
	{
		
		when(accountRepository.searchAccount(101)).thenReturn(null);
		accountService.depositAmount(101, 211);
		
	}
	
	 @Test()
	 public void whenAccountNumberIsPresentAmountIsDepositedSuccessfully() throws InvalidAccountNumberException
	 {
		 ArrayList<Account> accounts=new ArrayList<Account>();
		 Account account =new Account();
		 account.setAccountNumber(101);
		 account.setAmount(2000);
		 accounts.add(account);
		 when(accountRepository.searchAccount(101)).thenReturn(account);
		 accountService.depositAmount(101, 211);
	 }
	 
	 
	 /*
	  * Withdraw amount
	  * 1.when account is not found throw InvalidAccountNumberException
	  * 2.when amount is not sufficient then throw InsufficientBalanceException
	  * 3.when the valid info is passed, amount is withdrawed successfully.
	  */
	 
	 @Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
		public void whenAccountIsNotPresentForWithdrawSystemShouldThrowInvalidAccountNumberException() throws InvalidAccountNumberException, InsufficientBalanceException
		{
			
			when(accountRepository.searchAccount(101)).thenReturn(null);
			accountService.withdrawAmount(101, 211);
			
		}
	 
	 
	 
	 @Test(expected=com.capgemini.exceptions.InsufficientBalanceException.class)
		public void whenInsufficientBalanceIsPresentForWithdrawSystemShouldThrowInSufficientBalanceException() throws InvalidAccountNumberException, InsufficientBalanceException
		{
			 ArrayList<Account> accounts=new ArrayList<Account>();
			 Account account =new Account();
			 account.setAccountNumber(101);
			 account.setAmount(600);
			 accounts.add(account);
			when(accountRepository.searchAccount(101)).thenReturn(account);
			accountService.withdrawAmount(101, 700);
			
		}	
	 
	 
	 
	
	 @Test()
	 public void whenSufficientAmountIsPresentAmountIsWithdrawnSuccessfully() throws InvalidAccountNumberException, InsufficientBalanceException
	 {
		 ArrayList<Account> accounts=new ArrayList<Account>();
		 Account account =new Account();
		 account.setAccountNumber(101);
		 account.setAmount(2000);
		 accounts.add(account);
		 when(accountRepository.searchAccount(101)).thenReturn(account);
		 accountService.withdrawAmount(101, 211);
	 }
	 
}

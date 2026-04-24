package com.bank.banking.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.banking.dto.TransferRequest;
import com.bank.banking.entity.Account;
import com.bank.banking.entity.Transaction;
import com.bank.banking.exception.AccountNotFoundException;
import com.bank.banking.exception.InsufficientBalanceException;
import com.bank.banking.exception.InvalidAmountException;
import com.bank.banking.repository.AccountRepository;
import com.bank.banking.repository.TransactionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

	@Autowired
	private final AccountRepository accountRepository;

	@Autowired
	private final TransactionRepository transactionRepository;


	public Account createAccount(Account account)
	{
		account.setBalance(BigDecimal.ZERO);
		return accountRepository.save(account);
	}

	public Account getAccount(Long id)
	{
		return accountRepository.findById(id)
				.orElseThrow(()->new AccountNotFoundException("Account not found"));
	}

	@Transactional
	public Account deposit(Long id, BigDecimal amount) {
		
	    log.info("Deposit request received for accountId: {}, amount: {}", id, amount);

	    if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
	        log.warn("Invalid deposit amount: {}", amount);
	        throw new InvalidAmountException("Amount must be greater than zero");
	    }

	    Account account = getAccount(id);

	    account.setBalance(account.getBalance().add(amount));
	    accountRepository.save(account);
	    
	    log.info("Deposit successful. New balance: {}", account.getBalance());

	    Transaction transaction = Transaction.builder()
	            .toAccountId(id)
	            .amount(amount)
	            .type("DEPOSIT")
	            .status("SUCCESS")
	            .timestamp(LocalDateTime.now())
	            .build();

	    transactionRepository.save(transaction);

	    return account;
	}

	@Transactional
	public Account withdraw(Long id, BigDecimal amount) {
		log.info("Withdraw request for accountId: {}, amount: {}", id, amount);

	    Account account = getAccount(id);

	    if (account.getBalance().compareTo(amount) < 0) {
	        log.error("Insufficient balance for accountId: {}", id);
	        throw new InsufficientBalanceException("Insufficient balance");
	    }

	    account.setBalance(account.getBalance().subtract(amount));
	    accountRepository.save(account);

	    transactionRepository.save(Transaction.builder()
	            .fromAccountId(id)
	            .amount(amount)
	            .type("WITHDRAW")
	            .status("SUCCESS")
	            .timestamp(LocalDateTime.now())
	            .build());

	    return account;
	}

	@Transactional
	public void transfer(TransferRequest request) {
		log.info("Transfer request: from {} to {} amount {}", 
		        request.getFromAccountId(), 
		        request.getToAccountId(), 
		        request.getAmount());
		
	    Account from = getAccount(request.getFromAccountId());
	    Account to = getAccount(request.getToAccountId());

	    if (from.getBalance().compareTo(request.getAmount()) < 0) {
	        throw new RuntimeException("Insufficient balance");
	    }

	    from.setBalance(from.getBalance().subtract(request.getAmount()));
	    to.setBalance(to.getBalance().add(request.getAmount()));

	    accountRepository.save(from);
	    accountRepository.save(to);

	    transactionRepository.save(Transaction.builder()
	            .fromAccountId(from.getId())
	            .toAccountId(to.getId())
	            .amount(request.getAmount())
	            .type("TRANSFER")
	            .status("SUCCESS")
	            .timestamp(LocalDateTime.now())
	            .build());
	}
	
	public List<Transaction> getTransactions(Long accountId) {
	    return transactionRepository
	            .findByFromAccountIdOrToAccountId(accountId, accountId);
	}
}

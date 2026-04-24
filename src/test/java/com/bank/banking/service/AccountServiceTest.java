package com.bank.banking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bank.banking.dto.TransferRequest;
import com.bank.banking.entity.Account;
import com.bank.banking.exception.InsufficientBalanceException;
import com.bank.banking.exception.InvalidAmountException;
import com.bank.banking.repository.AccountRepository;
import com.bank.banking.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

	@InjectMocks
	private AccountService accountService;

	@Mock
	private AccountRepository accountRepository;

	@Mock
	private TransactionRepository transactionRepository;

	@Test
	void testDeposit_success() {

		Account account = new Account(1L, "ACC123", "Nethaji", "test@mail.com", BigDecimal.valueOf(1000));

		when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
		when(accountRepository.save(any(Account.class))).thenReturn(account);

		Account result = accountService.deposit(1L, BigDecimal.valueOf(500));

		assertNotNull(result);
		assertEquals(BigDecimal.valueOf(1500), result.getBalance());

		verify(accountRepository, times(1)).save(any(Account.class));
		verify(transactionRepository, times(1)).save(any());
	}

	@Test
	void testDeposit_invalidAmount() {

		Exception exception = assertThrows(InvalidAmountException.class, () -> {
			accountService.deposit(1L, BigDecimal.ZERO);
		});

		assertEquals("Amount must be greater than zero", exception.getMessage());

		verify(accountRepository, never()).save(any());
	}
	
	@Test
	void testWithdraw_insufficientBalance() {

	    Account account = new Account(1L, "ACC123", "Nethaji", "test@mail.com", BigDecimal.valueOf(1000));

	    when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

	    Exception exception = assertThrows(InsufficientBalanceException.class, () -> {
	        accountService.withdraw(1L, BigDecimal.valueOf(2000));
	    });

	    assertEquals("Insufficient balance", exception.getMessage());

	    verify(accountRepository, never()).save(any());
	}
	
	@Test
	void testTransfer_success() {

	    Account from = new Account(1L, "ACC1", "User1", "u1@mail.com", BigDecimal.valueOf(5000));
	    Account to = new Account(2L, "ACC2", "User2", "u2@mail.com", BigDecimal.valueOf(1000));

	    when(accountRepository.findById(1L)).thenReturn(Optional.of(from));
	    when(accountRepository.findById(2L)).thenReturn(Optional.of(to));

	    accountService.transfer(new TransferRequest(1L, 2L, BigDecimal.valueOf(1000)));

	    assertEquals(BigDecimal.valueOf(4000), from.getBalance());
	    assertEquals(BigDecimal.valueOf(2000), to.getBalance());

	    verify(accountRepository, times(2)).save(any());
	    verify(transactionRepository, times(1)).save(any());
	}
}

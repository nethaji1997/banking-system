package com.bank.banking.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.banking.dto.AmountRequest;
import com.bank.banking.dto.TransferRequest;
import com.bank.banking.entity.Account;
import com.bank.banking.service.AccountService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

	private final AccountService accountService;

	@PostMapping("/create")
	public Account createAccount(@RequestBody Account account)
	{
		log.info("API called: Create Account");
		return accountService.createAccount(account);
	}

	@GetMapping("/{id}")
	public Account getAccount(@PathVariable Long id)
	{
		return accountService.getAccount(id);
	}

	@PostMapping("/{id}/deposit")
	public Account deposit(@PathVariable Long id,@RequestBody AmountRequest request) {
		log.info("API called: Deposit for accountId {}", id);
		return accountService.deposit(id, request.getAmount());
	}

	@PostMapping("/{id}/withdraw")
	public Account withdraw(@PathVariable Long id,
			@RequestBody AmountRequest request) {

		return accountService.withdraw(id, request.getAmount());
	}

	@PostMapping("/transfer")
	public String transfer(@RequestBody TransferRequest request)
	{
		accountService.transfer(request);
		return "Transfer successfull";
	}

	@GetMapping("/{id}/transactions")
	public List<com.bank.banking.entity.Transaction> getTransactions(@PathVariable Long id) {
		return accountService.getTransactions(id);
	}
}

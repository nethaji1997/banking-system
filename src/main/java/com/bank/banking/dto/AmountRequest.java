package com.bank.banking.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AmountRequest {
	private BigDecimal amount;
}

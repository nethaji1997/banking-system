package com.bank.banking.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferRequest {

	private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;
}

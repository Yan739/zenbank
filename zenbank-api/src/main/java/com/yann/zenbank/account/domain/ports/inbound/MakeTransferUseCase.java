package com.yann.zenbank.account.domain.ports.inbound;

import java.math.BigDecimal;

public interface MakeTransferUseCase {

    // Exécute un virement entre deux comptes bancaires
    void execute(String sourceIban, String targetIban, BigDecimal amount);
}
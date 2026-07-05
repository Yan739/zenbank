package com.yann.zenbank.account.domain.services;

import com.yann.zenbank.account.domain.models.Account;
import com.yann.zenbank.account.domain.ports.inbound.MakeTransferUseCase;
import com.yann.zenbank.account.domain.ports.outbound.AccountRepositoryPort;

import java.math.BigDecimal;

public class TransferService implements MakeTransferUseCase {

    private final AccountRepositoryPort accountRepositoryPort;

    public TransferService(AccountRepositoryPort accountRepositoryPort) {
        this.accountRepositoryPort = accountRepositoryPort;
    }

    @Override
    public void execute(String sourceIban, String targetIban, BigDecimal amount) {
        // Récupération des comptes via le port de sortie
        Account sourceAccount = accountRepositoryPort.findByIban(sourceIban)
                .orElseThrow(() -> new IllegalArgumentException("Compte émetteur introuvable."));

        Account targetAccount = accountRepositoryPort.findByIban(targetIban)
                .orElseThrow(() -> new IllegalArgumentException("Compte destinataire introuvable."));

        sourceAccount.debit(amount);
        targetAccount.credit(amount);

        // Sauvegarde
        accountRepositoryPort.save(sourceAccount);
        accountRepositoryPort.save(targetAccount);
    }
}
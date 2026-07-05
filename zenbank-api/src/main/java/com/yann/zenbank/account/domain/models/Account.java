package com.yann.zenbank.account.domain.models;

import java.math.BigDecimal;
import java.util.UUID;

public class Account {
    private final UUID id;
    private final String iban;
    private BigDecimal balance;
    private final String currency;
    private boolean isBlocked;

    // Constructeur
    public Account(UUID id, String iban, BigDecimal balance, String currency, boolean isBlocked) {
        this.id = id;
        this.iban = iban;
        this.balance = balance;
        this.currency = currency;
        this.isBlocked = isBlocked;
    }

    // ─── LES RÈGLES BANCAIRES (LOGIQUE MÉTIER) ───

    /**
     * Règle de débit (Retrait ou Virement sortant)
     */
    public void debit(BigDecimal amount) {
        if (this.isBlocked) {
            throw new IllegalStateException("Opération impossible : Le compte est bloqué.");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant à débiter doit être strictement supérieur à zéro.");
        }
        if (this.balance.compareTo(amount) < 0) {
            throw new IllegalStateException("Solde insuffisant pour effectuer cette opération.");
        }

        // Tout est OK, on soustrait
        this.balance = this.balance.subtract(amount);
    }

    /**
     * Règle de crédit (Dépôt ou Virement entrant)
     */
    public void credit(BigDecimal amount) {
        if (this.isBlocked) {
            throw new IllegalStateException("Opération impossible : Le compte destinataire est bloqué.");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant à créditer doit être strictement supérieur à zéro.");
        }

        // Tout est OK, on additionne
        this.balance = this.balance.add(amount);
    }

    // ─── GETTERS ───
    public UUID getId() { return id; }
    public String getIban() { return iban; }
    public BigDecimal getBalance() { return balance; }
    public String getCurrency() { return currency; }
    public boolean isBlocked() { return isBlocked; }
}
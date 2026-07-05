package com.yann.zenbank.account.infrastructure.web;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record TransferRequest(
        @NotBlank(message = "L'IBAN émetteur ne peut pas être vide.")
        String sourceIban,

        @NotBlank(message = "L'IBAN destinataire ne peut pas être vide.")
        String targetIban,

        @Positive(message = "Le montant du virement doit être supérieur à zéro.")
        BigDecimal amount
) {}
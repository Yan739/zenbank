package com.yann.zenbank.account.domain.services;

import com.yann.zenbank.account.domain.models.Account;
import com.yann.zenbank.account.domain.ports.outbound.AccountRepositoryPort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

    private AccountRepositoryPort accountRepositoryPort;
    private TransferService transferService;

    @BeforeEach
    void setUp() {
        accountRepositoryPort = mock(AccountRepositoryPort.class);
        transferService = new TransferService(accountRepositoryPort);
    }

    @Test
    void execute_ShouldSuccessfullyTransferMoney_WhenAccountsAreValid() {
        // Given
        Account source = new Account(UUID.randomUUID(), "FR76123", new BigDecimal("100.00"), "EUR", false);
        Account target = new Account(UUID.randomUUID(), "FR76456", new BigDecimal("50.00"), "EUR", false);

        when(accountRepositoryPort.findByIban("FR76123")).thenReturn(Optional.of(source));
        when(accountRepositoryPort.findByIban("FR76456")).thenReturn(Optional.of(target));

        // When
        transferService.execute("FR76123", "FR76456", new BigDecimal("30.00"));

        // Then
        assertThat(source.getBalance()).isEqualByComparingTo("70.00");
        assertThat(target.getBalance()).isEqualByComparingTo("80.00");

        verify(accountRepositoryPort, times(1)).save(source);
        verify(accountRepositoryPort, times(1)).save(target);
    }

    @Test
    void execute_ShouldThrowException_WhenSourceBalanceIsInsufficient() {
        // Given
        Account source = new Account(UUID.randomUUID(), "FR76123", new BigDecimal("10.00"), "EUR", false);
        Account target = new Account(UUID.randomUUID(), "FR76456", new BigDecimal("50.00"), "EUR", false);

        when(accountRepositoryPort.findByIban("FR76123")).thenReturn(Optional.of(source));
        when(accountRepositoryPort.findByIban("FR76456")).thenReturn(Optional.of(target));

        // When & Then
        assertThatThrownBy(() -> transferService.execute("FR76123", "FR76456", new BigDecimal("30.00")))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Solde insuffisant");

        verify(accountRepositoryPort, never()).save(any());
    }
}
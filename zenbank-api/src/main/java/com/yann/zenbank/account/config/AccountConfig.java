package com.yann.zenbank.account.config;

import com.yann.zenbank.account.domain.ports.inbound.MakeTransferUseCase;
import com.yann.zenbank.account.domain.ports.outbound.AccountRepositoryPort;
import com.yann.zenbank.account.domain.services.TransferService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfig {

    @Bean
    public MakeTransferUseCase makeTransferUseCase(AccountRepositoryPort accountRepositoryPort) {
        return new TransferService(accountRepositoryPort);
    }
}
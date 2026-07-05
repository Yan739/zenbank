package com.yann.zenbank.account.infrastructure.database;

import com.yann.zenbank.account.domain.models.Account;
import com.yann.zenbank.account.domain.ports.outbound.AccountRepositoryPort;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class AccountJpaAdapter implements AccountRepositoryPort {

    private final SpringDataAccountRepository repository;

    public AccountJpaAdapter(SpringDataAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Account> findByIban(String iban) {
        return repository.findByIban(iban)
                .map(entity -> new Account(
                        entity.getId(),
                        entity.getIban(),
                        entity.getBalance(),
                        entity.getCurrency(),
                        entity.isBlocked()
                ));
    }

    @Override
    public void save(Account account) {
        AccountEntity entity = new AccountEntity(
                account.getId(),
                account.getIban(),
                account.getBalance(),
                account.getCurrency(),
                account.isBlocked()
        );
        repository.save(entity);
    }
}
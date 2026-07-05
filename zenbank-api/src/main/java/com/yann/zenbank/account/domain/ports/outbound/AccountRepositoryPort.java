package com.yann.zenbank.account.domain.ports.outbound;

import com.yann.zenbank.account.domain.models.Account;
import java.util.Optional;

public interface AccountRepositoryPort {
    Optional<Account> findByIban(String iban);
    void save(Account account);
}
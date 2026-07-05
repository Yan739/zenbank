package com.yann.zenbank.account.infrastructure.database;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataAccountRepository extends JpaRepository<AccountEntity, UUID> {

    Optional<AccountEntity> findByIban(String iban);
}
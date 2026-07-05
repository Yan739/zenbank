package com.yann.zenbank.account.infrastructure.web;

import com.yann.zenbank.account.domain.ports.inbound.MakeTransferUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@CrossOrigin(origins = "http://localhost:4200")
public class AccountController {

    private final MakeTransferUseCase makeTransferUseCase;

    public AccountController(MakeTransferUseCase makeTransferUseCase) {
        this.makeTransferUseCase = makeTransferUseCase;
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@Valid @RequestBody TransferRequest request) {
        // On délègue l'action au port d'entrée de notre domaine
        makeTransferUseCase.execute(
                request.sourceIban(),
                request.targetIban(),
                request.amount()
        );
        return ResponseEntity.ok("Virement effectué avec succès !");
    }
}
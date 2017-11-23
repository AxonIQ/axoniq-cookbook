package io.axoniq.axonbank.run;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/accounts")
    public ResponseEntity createBankAccount(@RequestBody String name) {
        log.info("Request to create account for: {}", name);

        UUID accountId = accountService.createBankAccount(name);

        return new ResponseEntity<>(accountId, HttpStatus.CREATED);
    }

    @PutMapping("/accounts/{accountId}/deposit/{amount}")
    public ResponseEntity depositMoney(@PathVariable UUID accountId, @PathVariable Double amount) {
        log.info("Request to withdraw {} dollar from account {} ", amount, accountId);

        accountService.depositMoney(accountId, amount);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/accounts/{accountId}/withdraw/{amount}")
    public ResponseEntity withdrawMoney(@PathVariable UUID accountId, @PathVariable Double amount) {
        log.info("Request to withdraw {} dollar from account {} ", amount, accountId);

        accountService.withdrawMoney(accountId, amount);

        return new ResponseEntity(HttpStatus.OK);
    }

}
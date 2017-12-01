package io.axoniq.axonbank.run;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final CommandGateway commandGateway;

    @Autowired
    public AccountController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/accounts")
    public CompletableFuture<Object> createBankAccount(@RequestBody String name) {
        log.info("Request to create account for: {}", name);

        assertNotNull(name, "The name of the account holder should not be null");

        UUID accountId = UUID.randomUUID();

        CreateAccountCommand createAccountCommand = new CreateAccountCommand(accountId, name);

        return commandGateway.send(createAccountCommand);
    }

    @PutMapping("/accounts/{accountId}/deposit/{amount}")
    public CompletableFuture<Object> depositMoney(@PathVariable UUID accountId, @PathVariable Double amount) {
        log.info("Request to withdraw {} dollar from account {} ", amount, accountId);

        return commandGateway.send(new DepositMoneyCommand(accountId, amount));
    }

    @PutMapping("/accounts/{accountId}/withdraw/{amount}")
    public CompletableFuture<Object> withdrawMoney(@PathVariable UUID accountId, @PathVariable Double amount) {
        log.info("Request to withdraw {} dollar from account {} ", amount, accountId);

        return commandGateway.send(new DepositMoneyCommand(accountId, amount));
    }

}
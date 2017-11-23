package io.axoniq.axonbank.run;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final CommandGateway commandGateway;

    @Autowired
    public AccountService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    public UUID createBankAccount(String name) {
        assertNotNull(name, "The name of the account holder should not be null");

        UUID accountId = UUID.randomUUID();

        CreateAccountCommand createAccountCommand = new CreateAccountCommand(accountId, name);

        commandGateway.send(createAccountCommand);

        return accountId;
    }

    public void depositMoney(UUID accountId, Double amount) {
        commandGateway.send(new DepositMoneyCommand(accountId, amount));
    }

    public void withdrawMoney(UUID accountId, Double amount) {
        commandGateway.send(new WithdrawMoneyCommand(accountId, amount));
    }

}

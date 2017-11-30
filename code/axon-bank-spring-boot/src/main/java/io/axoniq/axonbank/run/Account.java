package io.axoniq.axonbank.run;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class Account {

    @AggregateIdentifier
    private UUID accountId;

    private Double balance;

    // Required for Axon to re-create the aggregate
    public Account() {
    }

    @CommandHandler
    public Account(CreateAccountCommand command) {
        apply(new AccountCreatedEvent(command.getAccountId(), command.getName()));
    }

    @CommandHandler
    public void handle(DepositMoneyCommand command) {
        apply(new MoneyDepositedEvent(command.getAccountId(), command.getAmount()));
    }

    @CommandHandler
    public void handle(WithdrawMoneyCommand command) {
        if (balance - command.getAmount() >= 0) {
            apply(new MoneyWithdrawnEvent(command.getAccountId(), command.getAmount()));
        } else {
            throw new IllegalArgumentException("Amount to withdraw is bigger than current balance on account");
        }
    }

    @EventSourcingHandler
    protected void on(AccountCreatedEvent event) {
        this.accountId = event.getAccountId();
        this.balance = 0.0;
    }

    @EventSourcingHandler
    protected void on(MoneyDepositedEvent event) {
        this.balance = balance + event.getAmount();
    }

    @EventSourcingHandler
    protected void on(MoneyWithdrawnEvent event) {
        this.balance = balance - event.getAmount();
    }

}

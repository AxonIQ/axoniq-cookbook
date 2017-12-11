package io.axoniq.axonbank.run;

import java.util.UUID;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("Account")
public class AccountProjector {

    private final AccountRepository repository;

    @Autowired
    public AccountProjector(AccountRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(AccountCreatedEvent event) {
        AccountView accountView =
                AccountView.builder()
                           .accountId(event.getAccountId())
                           .name(event.getName())
                           .build();

        repository.save(accountView);
    }

    @EventHandler
    public void on(MoneyDepositedEvent event) {
        UUID accountId = event.getAccountId();
        AccountView accountView = repository.findOne(accountId);

        double newBalance = accountView.getBalance() + event.getAmount();

        AccountView updatedView = AccountView.builder()
                                             .copyOf(accountView)
                                             .balance(newBalance)
                                             .build();

        repository.save(updatedView);
    }

    @EventHandler
    public void on(MoneyWithdrawnEvent event) {
        UUID accountId = event.getAccountId();
        AccountView accountView = repository.findOne(accountId);

        double newBalance = accountView.getBalance() - event.getAmount();

        AccountView updatedView = AccountView.builder()
                                             .copyOf(accountView)
                                             .balance(newBalance)
                                             .build();

        repository.save(updatedView);
    }

}

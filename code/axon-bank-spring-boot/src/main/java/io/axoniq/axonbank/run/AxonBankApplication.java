package io.axoniq.axonbank.run;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class AxonBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(AxonBankApplication.class, args);
    }

    @RestController
    public static class AccountController {

        private static final Logger log = LoggerFactory.getLogger(AccountController.class);

        private final AccountService accountService;

        @Autowired
        public AccountController(AccountService accountService) {
            this.accountService = accountService;
        }

        @PostMapping("/account")
        public ResponseEntity createBankAccount(@RequestBody String name) {
            log.info("Request to create account for: {}", name);

            UUID accountId = accountService.createBankAccount(name);

            return new ResponseEntity<>(accountId, HttpStatus.CREATED);
        }

        @PutMapping("/account/{accountId}/deposit/{amount}")
        public ResponseEntity depositMoney(@PathVariable UUID accountId, @PathVariable Double amount) {
            log.info("Request to withdraw {} dollar from account {} ", amount, accountId);

            accountService.depositMoney(accountId, amount);

            return new ResponseEntity(HttpStatus.OK);
        }

        @PutMapping("/account/{accountId}/withdraw/{amount}")
        public ResponseEntity withdrawMoney(@PathVariable UUID accountId, @PathVariable Double amount) {
            log.info("Request to withdraw {} dollar from account {} ", amount, accountId);

            accountService.withdrawMoney(accountId, amount);

            return new ResponseEntity(HttpStatus.OK);
        }

    }

    @Service
    public static class AccountService {

        private final CommandGateway commandGateway;

        @Autowired
        public AccountService(CommandGateway commandGateway) {
            this.commandGateway = commandGateway;
        }

        public UUID createBankAccount(String name) {
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

    @Aggregate
    public static class Account {

        @AggregateIdentifier
        private UUID accountId;

        private Double balance;

        // Required for Axon to create the aggregate
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
                apply(new MoneyDepositedEvent(command.getAccountId(), command.getAmount()));
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

    @Service
    @ProcessingGroup("Account")
    public static class AccountProjector {

        private final AccountRepository repository;

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
            String accountId = event.getAccountId().toString();
            AccountView accountView = repository.getOne(accountId);

            double newBalance = accountView.getBalance() + event.getAmount();

            AccountView updatedView = AccountView.builder()
                                                 .copyOf(accountView)
                                                 .balance(newBalance)
                                                 .build();

            repository.save(updatedView);
        }

        @EventHandler
        public void on(MoneyWithdrawnEvent event) {
            String accountId = event.getAccountId().toString();
            AccountView accountView = repository.getOne(accountId);

            double newBalance = accountView.getBalance() - event.getAmount();

            AccountView updatedView = AccountView.builder()
                                                 .copyOf(accountView)
                                                 .balance(newBalance)
                                                 .build();

            repository.save(updatedView);
        }

    }



}

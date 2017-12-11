package io.axoniq.axonbank.run;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountViewController {

    private static final Logger log = LoggerFactory.getLogger(AccountViewController.class);

    private final AccountDataService accountDataService;

    @Autowired
    public AccountViewController(AccountDataService accountDataService) {
        this.accountDataService = accountDataService;
    }

    @GetMapping("/accounts/{accountId}")
    public AccountView getAccountById(@PathVariable UUID accountId) {
        log.info("Request Account with id: {}", accountId);

        return accountDataService.getAccountById(accountId);
    }

    @GetMapping("/accounts")
    public List<AccountView> getAllAccounts() {
        log.info("Request all Accounts");

        return accountDataService.getAllAccounts();
    }

}
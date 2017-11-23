package io.axoniq.axonbank.run;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountDataService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountDataService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountView getAccountById(UUID accountId) {
        return accountRepository.findOne(accountId);
    }

    public List<AccountView> getAllAccounts() {
        return accountRepository.findAll();
    }

}
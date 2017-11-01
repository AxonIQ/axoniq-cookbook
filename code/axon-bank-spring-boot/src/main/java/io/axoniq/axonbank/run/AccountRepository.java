package io.axoniq.axonbank.run;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountView, String> {
}
package io.axoniq.axonbank.run

import org.axonframework.commandhandling.TargetAggregateIdentifier
import java.util.*

// Commands
data class CreateAccountCommand(
        @TargetAggregateIdentifier val accountId: UUID,
        val name: String?
)

data class DepositMoneyCommand(
        @TargetAggregateIdentifier val accountId: UUID,
        val amount: Double
)

data class WithdrawMoneyCommand(
        @TargetAggregateIdentifier val accountId: UUID,
        val amount: Double
)

// Events
data class AccountCreatedEvent(
        val accountId: UUID,
        val name: String?
)

data class MoneyDepositedEvent(
        val accountId: UUID,
        val amount: Double
)

data class MoneyWithdrawnEvent(
        val accountId: UUID,
        val amount: Double
)
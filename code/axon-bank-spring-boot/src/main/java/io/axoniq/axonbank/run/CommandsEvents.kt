package io.axoniq.axonbank.run

import org.axonframework.commandhandling.TargetAggregateIdentifier
import org.springframework.data.annotation.Id
import java.util.*
import javax.persistence.Entity
import javax.persistence.Table

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

// Value objects
@Entity(name = "account")
@Table(name = "account")
data class AccountView(
        @Id val accountId: UUID,
        val name: String?,
        val balance: Double
) {
    class Builder {
        private lateinit var accountId: UUID
        private var name: String? = null
        private var balance: Double = 0.0

        fun accountId(v: UUID) = apply { accountId = v }
        fun name(v: String?) = apply { name = v }
        fun balance(v: Double) = apply { balance = v }

        fun copyOf(v: AccountView) = apply {
            accountId = v.accountId
            name = v.name
            balance = v.balance
        }

        fun build() = AccountView(accountId, name, balance)
    }

    companion object {
        @JvmStatic fun builder() = Builder()
    }
}
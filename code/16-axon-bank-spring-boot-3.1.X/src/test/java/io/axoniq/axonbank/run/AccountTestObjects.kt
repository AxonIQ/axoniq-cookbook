package io.axoniq.axonbank.run

import java.util.*

// Constants

val ACCOUNT_ID: UUID = UUID.fromString("ca2b8135-003b-48b9-a521-1ce64059a5ad")
val NAME: String = "A. Recipe"
val DEPOSIT_AMOUNT: Double = 10.0
val WITHDRAW_AMOUNT: Double = 3.0

// Commands

fun createAccountCommand(
        accountId: UUID = ACCOUNT_ID,
        name: String = NAME
) = CreateAccountCommand(accountId, name)

fun depositMoneyCommand(
        accountId: UUID = ACCOUNT_ID,
        amount: Double = DEPOSIT_AMOUNT
) = DepositMoneyCommand(accountId, amount)

fun withdrawMoneyCommand(
        accountId: UUID = ACCOUNT_ID,
        amount: Double = WITHDRAW_AMOUNT
) = WithdrawMoneyCommand(accountId, amount)

// Events

fun accountCreatedEvent(
        accountId: UUID = ACCOUNT_ID,
        name: String = NAME
) = AccountCreatedEvent(accountId, name)

fun moneyDepositedEvent(
        accountId: UUID = ACCOUNT_ID,
        amount: Double = DEPOSIT_AMOUNT
) = MoneyDepositedEvent(accountId, amount)

fun moneyWithdrawnEvent(
        accountId: UUID = ACCOUNT_ID,
        amount: Double = WITHDRAW_AMOUNT
) = MoneyWithdrawnEvent(accountId, amount)
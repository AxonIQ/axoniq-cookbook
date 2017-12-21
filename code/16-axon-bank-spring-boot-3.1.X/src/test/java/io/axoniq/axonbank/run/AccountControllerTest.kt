package io.axoniq.axonbank.run

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.axonframework.commandhandling.gateway.CommandGateway
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*
import java.util.concurrent.CompletableFuture

/*
* Test for issue #14 - How to write tests for a simple Axon application
 */
class AccountControllerTest {

    private val commandGateway: CommandGateway = mock<CommandGateway>()

    private val accountController: AccountController = AccountController(commandGateway)

    private val accountId: UUID = UUID.fromString("ca2b8135-003b-48b9-a521-1ce64059a5ad");
    private val name: String = "A. Recipe"
    private val depositAmount: Double = 10.0
    private val withdrawAmount: Double = 3.0
    private val delta: Double = 0.0

    @Test
    fun `Create bank account should send CreateAccountCommand`() {
        val createAccountCommand = CreateAccountCommand(accountId, name)

        whenever(commandGateway.send<CreateAccountCommand>(any()))
                .thenReturn(CompletableFuture.completedFuture(createAccountCommand))

        val result = accountController.createBankAccount(name).get() as CreateAccountCommand;

        assertEquals(accountId, result.accountId);
        assertEquals(name, result.name);
    }

    @Test
    fun `Deposit money should send DepositMoneyCommand`() {
        val depositMoneyCommand = DepositMoneyCommand(accountId, depositAmount)

        whenever(commandGateway.send<DepositMoneyCommand>(any()))
                .thenReturn(CompletableFuture.completedFuture(depositMoneyCommand))

        val result = accountController.depositMoney(accountId, depositAmount).get() as DepositMoneyCommand;

        assertEquals(accountId, result.accountId);
        assertEquals(depositAmount, result.amount, delta);
    }

    @Test
    fun `Withdraw money should send WithdrawMoneyCommand`() {
        val depositMoneyCommand = DepositMoneyCommand(accountId, withdrawAmount)

        whenever(commandGateway.send<DepositMoneyCommand>(any()))
                .thenReturn(CompletableFuture.completedFuture(depositMoneyCommand))

        val result = accountController.depositMoney(accountId, withdrawAmount).get() as DepositMoneyCommand;

        assertEquals(accountId, result.accountId);
        assertEquals(withdrawAmount, result.amount, delta);
    }

}
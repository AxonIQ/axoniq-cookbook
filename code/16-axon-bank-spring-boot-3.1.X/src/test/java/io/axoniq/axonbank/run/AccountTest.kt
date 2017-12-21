package io.axoniq.axonbank.run

import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.Before
import org.junit.Test

class AccountTest {

    private lateinit var fixture: FixtureConfiguration<Account>

    @Before
    fun setUp() {
        fixture = AggregateTestFixture(Account::class.java)
    }

    @Test
    fun `Handle CreateAccountCommand should apply AccountCreatedEvent`() {
        fixture.given()
                .`when`(createAccountCommand())
                .expectEvents(accountCreatedEvent())
    }

    @Test
    fun `DepositMoneyCommand should apply MoneyDepositedEvent`(){
        fixture.given(accountCreatedEvent())
                .`when`(depositMoneyCommand())
                .expectEvents(moneyDepositedEvent())
    }

    @Test
    fun `WithdrawMoneyCommand should not apply MoneyWithdrawnEvent because of current balance`(){
        fixture.given(accountCreatedEvent())
                .`when`(withdrawMoneyCommand())
                .expectNoEvents()
    }

    @Test
    fun `WithdrawMoneyCommand should apply MoneyWithdrawnEvent`(){
        fixture.given(accountCreatedEvent(), moneyDepositedEvent())
                .`when`(withdrawMoneyCommand())
                .expectEvents(moneyWithdrawnEvent())
    }

}
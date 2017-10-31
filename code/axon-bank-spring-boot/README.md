# axon-bank

Simple Spring Boot application with Axon Framework

Used for:
- Recipe: Axon Framework in a Spring Boot application [#11]

Contains:
- 1 Account aggregate
- 3 commands
    - CreateAccountCommand
    - DepositMoneyCommand
    - WithdrawMoneyCommand
- 3 events
    - AccountCreatedEvent
    - MoneyDepositedEvent
    - MoneyWithdrawnEvent
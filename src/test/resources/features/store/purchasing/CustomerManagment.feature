Feature: Customer management

Scenario: Creating a customer
When a customer "John" asks to be created
Then there is a customer "John"
And the customer "John" has no purchase

Scenario: A customer purchases a product
Given A customer "John"
When the customer "John" purchases a "MacBook Pro" for $2000
Then the customer "John" has a purchase of "MacBook Pro" for $2000
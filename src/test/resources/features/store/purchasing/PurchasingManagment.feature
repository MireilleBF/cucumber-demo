Feature: Purchasing items

  Scenario: A customer purchases an item in a given store
    Given customer "john" is already registered
    Given the store "MCPol" contains a stock containing 10 ingredients of name "chocolate"
    When customer completes the purchases of 2 "cookies" of "chocolate" for 3 dollars
    Then customer can see that item in purchase history
    Then there is 8 ingredients of name "chocolate"  in the stock
    Then the revenue of the store "MCPol" is 3 dollars


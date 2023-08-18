Feature: Purchasing items in stores

  Scenario: A customer purchases  items in  given stores
    Given customer "john" is already registered in ecosystem
    Given the store "MCPol" is already registered in ecosystem
    Given the store "KFCPol" is already registered in ecosystem
    When customer "john" completes the purchases of 1 "mc" for 6 dollars in "MCPol"
    And  customer "john" completes the purchases of 2 "Chicken Portion" for 8 dollars in "KFCPol"
    Then customer "john" can see that  1 "mc" in its purchase history
    And  customer "john" can see that  2 "Chicken Portion" in its purchase history
    And  the revenue of the store "MCPol" is 6 dollars in ecosystem
    And  the revenue of the store "KFCPol" is 8 dollars in ecosystem

  Scenario: two customers purchases  items in  given stores
    Given customer "john" is already registered in ecosystem
    Given customer "jane" is already registered in ecosystem
    Given the store "MCPol" is already registered in ecosystem
    Given the store "KFCPol" is already registered in ecosystem
    When customer "john" completes the purchases of 1 "mc" for 6 dollars in "MCPol"
    And  customer "jane" completes the purchases of 2 "Chicken Portion" for 8 dollars in "KFCPol"
    And  customer "jane" completes the purchases of 2 "Fries Portion" for 4 dollars in "KFCPol"
    Then customer "john" can see that  1 "mc" in its purchase history
    And  customer "jane" can see that  2 "Chicken Portion" in its purchase history
    And  customer "jane" can see that  2 "Fries Portion" in its purchase history
    And  the revenue of the store "MCPol" is 6 dollars in ecosystem
    And  the revenue of the store "KFCPol" is 12 dollars in ecosystem

  Scenario: an Unknown customer purchases  item in  a given store
    When customer "john" completes the purchases of 1 "mc" for 6 dollars in "MCPol"
    Then an "UnknownCustomerException" should be thrown

  Scenario: an customer purchases  item in  an unknown store
    Given customer "john" is already registered in ecosystem
    When customer "john" completes the purchases of 1 "mc" for 6 dollars in "MCPol"
    Then an "UnknownStoreException" should be thrown
    And customer "john" cannot see that  1 "mc" in "MCPol" in its purchase history
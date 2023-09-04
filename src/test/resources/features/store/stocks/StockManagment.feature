Feature: Stock managment
  We only manage the stock of chocolates

  Scenario: adding ingredients to a stock
    Given the stock already contains 30 doses of chocolates
    When the manager adds 15 doses of chocolates
    Then the stock should contain 45 doses of chocolates


  Scenario: removing too many ingredients from a stock
    Given the stock already contains 30 doses of chocolates
    When the manager asks to remove  35 doses of chocolates
    Then the withdrawal is refused
    Then the stock should contain 30 doses of chocolates


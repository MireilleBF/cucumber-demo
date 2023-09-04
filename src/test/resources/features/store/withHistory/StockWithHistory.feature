Feature: Stock managment with History

Scenario: removing ingredients to a stock and checking the history
Given the stock already contains 30 doses of "chocolates"
    When the manager "John" adds 15 doses of "chocolates"
    Then the stock should contain 45 doses of "chocolates"
    Then the last entry in the history should be "John modifies stock : 15"

  Scenario: removing ingredients to a stock and checking the history
    Given the stock already contains 30 doses of "chocolates"
    When the manager "John" removes 10 doses of "chocolates"
    Then the stock should contain 20 doses of "chocolates"
    Then the last entry in the history should be "John modifies stock : -10"

  Scenario Outline: adding ingredients to a stock and checking the history
    Given the stock already contains 30 doses of <ingredient_name>
      When the manager <name> adds <amount_delta> doses of <ingredient_name>
    Then the stock should contain <final_amount> doses of <ingredient_name>
    And the last entry in the history should be <log_message>
    Examples:
      | name   | amount_delta | final_amount | log_message | ingredient_name |
      | "john" | 15           | 45           | "john modifies stock : 15" | "chocolates" |
      | "paul" | 0            | 30            | "paul modifies stock : 0" | "chocolates" |
      | "paul" | -10          | 20            | "paul modifies stock : -10" | "chocolates" |
      | "paul" | -40          | 30            | "paul tries to modify stock : -40 but it is refused" | "chocolates" |
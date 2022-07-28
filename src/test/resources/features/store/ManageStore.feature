Feature: Manage Stocks of Stores

  Background:
    Given a store of name "Stonks Store"


  Scenario Outline: Add ingredient in empty Store
    Given an empty store
    When the manager adds <amount_delta> ingredients of name chocolate
    Then there is <final_amount> ingredients of name chocolate  in the stock

    Examples:
   | amount_delta | final_amount|
   | 6            | 6         |
   | 0            | 0          |



  Scenario Outline: Add ingredients
    Given the store contains a stock containing <amount_base> ingredients of name chocolate
    When the manager adds <amount_delta> ingredients of name chocolate
    Then there is <final_amount> ingredients of name chocolate  in the stock

    Examples:
      |amount_base| amount_delta | final_amount|
      |10         | 6            | 16          |
      |0          |3             |3           |

  Scenario Outline: Add  ingredients in one store stock
    Given the store contains a stock containing <amount_chocolate> ingredients of name chocolate and <amount_strawberry> ingredients of name strawberry
    When the manager adds <amount_delta> ingredients of name chocolate
    Then there is <final_amount> ingredients of name chocolate  in the stock
    Then there is <amount_strawberry> ingredients of name strawberry  in the stock

    Examples:
      |amount_chocolate| amount_delta | final_amount|amount_strawberry|
      |100              | 20            | 120          |10               |
      |8                |3             |11           |2                |


  Scenario Outline: Update Stocks
    Given the store stock contains <chocolate_topping> chocolate topping, <strawberry_flavour> strawberry flavour, <almond_dough> almond dough
    And a list of items of <amount> chocolate, strawberry and almond
    When the manager asks store update with this list of items
    Then the stocks have been updated
    And the store contains <chocolate_flavour_after> chocolate topping, <strawberry_topping_after> strawberry flavour, <almond_dough_after> almond dough
    Examples:
      | chocolate_topping | strawberry_flavour | almond_dough | amount | chocolate_flavour_after | strawberry_topping_after | almond_dough_after |
      |10                 |7                   |6             | 1      |9                        |6                         |5                   |
      |5                  |2                   |14            | 2      |3                        |0                         |12                  |
      |5                  |10                  |15            | 5      |0                        |5                         |10                  |

  Scenario Outline: Impossible Update Stocks
    Given the store stock contains <chocolate_topping> chocolate topping, <strawberry_flavour> strawberry flavour, <almond_dough> almond dough
    And a list of items of <amount> chocolate, strawberry and almond
    When the manager asks store update with this list of items
    Then the updates return false
    And the store contains <chocolate_topping> chocolate topping, <strawberry_flavour> strawberry flavour, <almond_dough> almond dough


    Examples:
      | chocolate_topping | strawberry_flavour | almond_dough | amount |
      |10                 |7                   |6             | 7      |
      |5                  |2                   |14            | 3      |
      |5                  |10                  |15            | 6      |


 Scenario Outline: check items
    Given the store contains a stock containing <amount_base> ingredients of name chocolate
    When user asks the <amount_delta> of ingredients of name chocolate
    Then we expect the system to answer <response>

    Examples:
      |amount_base| amount_delta | response    |
      |10         | 6            | true        |
      |8          |10            | false        |



  Scenario Outline: refuse to update stock with negative ingredients
    Given the store contains a stock containing <amount_base> ingredients of name chocolate
    When the manager adds <chocolateAmount> ingredients of name chocolate
    Then an error Amount <chocolateAmount> is less than 0 is raised
    Then there is <amount_base> ingredients of name chocolate  in the stock

    Examples:
      |amount_base | chocolateAmount |
      |10               | -2              |

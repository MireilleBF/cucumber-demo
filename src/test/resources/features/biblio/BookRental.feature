Feature: Book rental

  Background:
    Given a student of name "Marcel" and with student id 123456
    And a book of title "UML pour les nuls"

  Scenario: No rental by default
    When "Marcel" requests his number of rentals
    Then There is 0 in his number of rentals

  Scenario: a book rental
    When "Marcel" rents the book "UML pour les nuls"
    Then There is 1 in his number of rentals
      And The book "UML pour les nuls" is in a rental in the list of rentals
      And The book "UML pour les nuls" is unavailable


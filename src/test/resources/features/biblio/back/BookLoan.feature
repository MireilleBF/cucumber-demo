Feature: Book loan

  Background:
    Given a student of name "Marcel" and with student id 123456
    And a book of title "UML pour les nuls"

  Scenario: No loan by default
    When "Marcel" requests his number of loans
    Then There is 0 in his number of loans

  Scenario: a book loan
    When "Marcel" rents the book "UML pour les nuls"
    Then There is 1 in his number of loans
    And The book "UML pour les nuls" is in a loan in the list of loans
    And The book "UML pour les nuls" is unavailable

  Scenario: a book return
    Given "Marcel" has rent the book "UML pour les nuls"
    When "Marcel" returns the book "UML pour les nuls"
    Then There is 0 in his number of loans
    And The book "UML pour les nuls" is available

Feature: Testing a REST API to deal with books library
  Description: the purpose of these tests are to cover End to End flows for Users (not necessarily Library members)

  Background:
    Given the API test servers are configured and started
    Given 3 books are at least already registered in the library
    Given 2 students are at least already registered by the scolarship service

  #un bibliothécaire ajoute des livres
  Scenario: The librarian adds a book
    When the librarian adds a book with the title "Never Let Me Go", the author "Kazuo Ishiguro", isbn "2-84893-019-5"
    Then the server should return a success status
    Then a book of title "Never Let Me Go" is registered in the library
    Then the library contains at least 4 books

    # un utilisateur demande la liste des livres
  Scenario: User is able to list all the books
    When a user asks to get the list of registered books
    Then the requested list is returned in json format
    And the list contains at least 3 books

  Scenario: Scolarity has added a student, the student books a book
    Given a registered student named "Paul" with student number 699
    Given a book of title "Never Let Me Go" with id "NLMG-0" has been registered and is available
    When the student with id 699 books the book with id "NLMG-0"
    Then the server should return a success status
    And there is one more loan for the student with the student number 699
    And the book with id "NLMG-0" is no longer available

  # une tentative de réservation d'un livre inexistant
  Scenario: A student tries to book a non existing book
    Given a registered student named "Paul" with student number 680
    Given a book of title "Never Let Me Go" with id "NLMG-2" has not been registered
    When the student with id 680 books the book with id "NLMG-2"
    Then the server should return a failure status
    And the server should return a message "{\"error\": \"Book not found\"}"
    And the number of loans has not changed for the student with the student number 680

  # une tentative de réservation d'un livre non disponible
  Scenario: A student tries to book a non available book
    Given a registered student named "Paul" with student number 679
    Given a book of title "Never Let Me Go" with id "NLMG-0" has been registered and is not available
    When the student with id 679 books the book with id "NLMG-0"
    Then the server should return a failure status
    And the server should return a message "{\"error\": \"Book NLMG-0 already borrowed\"}"
    And the number of loans has not changed for the student with the student number 679

   # une tentative de réservation d'un second exemplaire du même livre par le même étudiant
  Scenario: A student tries to book a second copy of the same book
    Given a registered student named "Paul" with student number 678
    Given a book of title "Never Let Me Go" with id "NLMG-0" has been registered and is available
    Given a book of title "Never Let Me Go" with id "NLMG-1" has been registered and is available
    When the student with id 678 books the book with id "NLMG-0"
    And the student with id 678 books the book with id "NLMG-1"
    Then the server should return a failure status
    And the server should return a message "{\"error\": \"Same book NLMG-0 already borrowed\"}"
    And there is one more loan for the student with the student number 678
    And the book with id "NLMG-1" is still available

  # Une tentative de réservation d'un livre pour un étudiant inexistant
  Scenario: A non existing student tries to book a book
    Given a book of title "Never Let Me Go" with id "NLMG-0" has been registered and is available
    Given a student of name "Paulin" and with student id 690 has not been registered
    When the student with id 690 books the book with id "NLMG-0"
    Then the server should return a failure status
    And the server should return a message "{\"error\": \"This student does not exist\"}"
    And the book with id "NLMG-0" is still available
#Inspired from https://github.com/karatelabs/karate/blob/master/karate-demo/src/test/java/demo/cats/cats.feature
Feature: Library API Testing with Karate Approach

  Background:
    Given the Karate setup is done, servers are configured and started

Scenario: add and retrieve a book

# Add a book
Given url url4library
Given request { title: "Guernica", author: [ "Dave Boling"], isbn: "2-84893-019-5", identifiant: "G-0" }
When method post
Then status 201
And match response == { "Book created" }

# get book by id
Given url url4library+ '/G-0'
When method get
Then status 200
And match response == {titre:"Guernica",auteurs:["Dave Boling"],isbn: "2-84893-019-5",identifiant:"G-0"}

# get all books
Given url url4library
When method get
Then status 200
And match response contains {titre:"Guernica",auteurs:["Dave Boling"],isbn: "2-84893-019-5",identifiant:"G-0"}
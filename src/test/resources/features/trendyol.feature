Feature: Trendyol Critical Functionalities

  Scenario: Search product
    Given I open Trendyol homepage
    When I search for "kablosuz kulaklik"
    Then I should see search results containing "kulaklik"

  Scenario: Select product and add to cart
    Given I searched for "kablosuz kulaklik"
    When I select a product from search results
    And I add the product to the cart
    Then The product should be visible in the cart

  Scenario: Validate cart price
    Given I have multiple products in the cart
    Then The total price should equal the sum of product prices

  Scenario: Remove item from cart
    Given I have a product in the cart
    When I remove the product
    Then The cart should be empty

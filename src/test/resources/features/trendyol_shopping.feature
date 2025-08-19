Feature: Trendyol Shopping Functionality
  As a user
  I want to search for products, view details, and manage my cart
  So that I can complete my shopping experience

  Background:
    Given I am on the Trendyol homepage
    And I accept cookies if present

  @search
  Scenario: Search for wireless headphones
    When I search for "kablosuz kulaklik"
    Then search results should be displayed
    And results should contain "kablosuz kulaklik"

  @product_details
  Scenario: View product details
    When I search for "kablosuz kulaklik"
    And I click on the first product from search results
    Then product details page should be displayed
    And product name should be visible
    And product price should be visible
    And product availability status should be visible

  @add_to_cart
  Scenario: Add product to cart
    When I search for "kablosuz kulaklik"
    And I click on the first product from search results
    And I add the product to cart
    Then product should be added to cart successfully
    And cart should display correct product details

  @cart_validation
  Scenario: Validate cart total price
    When I add multiple products to cart
    Then total price should match sum of individual product prices

  @remove_from_cart
  Scenario: Remove item from cart
    When I remove an item from cart
    Then item should be removed from cart
    And total price should be updated correctly

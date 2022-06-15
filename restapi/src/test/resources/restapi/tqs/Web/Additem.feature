Feature: Legoliveries Add Item

    @positive
    Scenario: Successful
        Given I am in "http://localhost:3000"
        When I click on the Lego "<name>"
        And I select the quantity "<qtty>" for the lego "<name>"
        And I click Add item to cart
        Then I should see the Cart with "<num>" items
        Examples:
            | name                              | qtty | cart |
            | Ford Mustang Shelby GT500 - 42138 |  2   |  1   |
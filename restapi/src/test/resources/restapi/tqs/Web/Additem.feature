Feature: Legoliveries Add Item

    @positive
    Scenario: Successful
        Given I am in "http://127.0.0.1:3000"
        When I click on the Lego "<name>"
        And I select the quantity "<qtty>" for the lego "<name>"
        And I click Add item to cart
        Then I should see the Cart with "<num>" items
        Examples:
            | name      | qtty | cart | num |
            | Lego Test |  2   |  1   |  1  |
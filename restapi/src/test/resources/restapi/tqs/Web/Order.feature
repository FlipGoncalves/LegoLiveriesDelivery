Feature: Legoliveries Order

    @positive
    Scenario: Successful
        Given I am in "http://localhost:3000"
        And I add an item to the cart
        When I click on the cart
        And I input the street: "<street>", the city: "<city>", the country: "<country>", the postal code: "<code>", the latitude: "<latit>" and the longitude: "<longit>"
        And I click Order
        Then I should see the Cart with "<num>" items
        Examples:
            | street  | city     | country    | code     | num | latit   | longit |
            | Rua 1   | Aveiro   | Portugal   | 3800-510 |  0  | 82.0499 | 107.35 |

    @positive
    Scenario: Successful with Time
        Given I am in "http://localhost:3000"
        And I add an item to the cart
        When I click on the cart
        And I input the street: "<street>", the city: "<city>", the country: "<country>", the postal code: "<code>", the latitude: "<latit>" and the longitude: "<longit>"
        And I choose "<time>"
        And I click Order
        Then I should see the Cart with "<num>" items
        Examples:
            | street  | city     | country    | code     | num | time     | latit   | longit |
            | Rua 1   | Aveiro   | Portugal   | 3800-510 |  0  | Tomorrow | 82.0499 | 107.35 | 

    @negative
    Scenario: Error
        Given I am in "http://localhost:3000"
        When I click on the cart
        And I click Order
        Then I should see an error message
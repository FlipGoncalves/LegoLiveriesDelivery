Feature: Legoliveries Order

    @positive
    Scenario: Successful
        Given I am in "http://172.20.202.3:3000"
        And I add an item to the cart
        When I click on the cart
        And I input my "<street>", "<city>", "<country>" and "<code>"
        And I click Order
        Then I should see the Cart with "<num>" items
        Examples:
            | street  | city     | country    | code     | num |
            | Rua 1   | Aveiro   | Portugal   | 3800-510 |  0  |

    @positive
    Scenario: Successful with Time
        Given I am in "http://172.20.202.3:3000"
        And I add an item to the cart
        When I click on the cart
        And I input my "<street>", "<city>", "<country>" and "<code>"
        And I choose "<time>"
        And I click Order
        Then I should see the Cart with "<num>" items
        Examples:
            | street  | city     | country    | code     | num | time     |
            | Rua 1   | Aveiro   | Portugal   | 3800-510 |  0  | Tomorrow |

    @negative
    Scenario: Error
        Given I am in "http://172.20.202.3:3000"
        When I click on the cart
        And I click Order
        Then I should see an error message
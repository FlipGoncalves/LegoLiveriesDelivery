Feature: Legoliveries Search Item By Name / Price

    @positive
    Scenario: Successful by Name
        Given I am in "http://127.0.0.1:3000"
        When I choose "<type>" on the search bar
        And I search for "<lego>"
        And I click Search
        Then I should see the item "<lego>" in my screen
        Examples:
            | lego       |   type   |
            | Lego Test  |   name   |

    @positive
    Scenario: Successful by Price
        Given I am in "http://127.0.0.1:3000"
        When I choose "<type>" on the search bar
        And I search for "<lego>"
        And I click Search
        Then I should see the item "<lego>" in my screen
        Examples:
            | lego       |   type    |
            | 9.99       |   price   |
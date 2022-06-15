Feature: Legoliveries Search Item By Name / Price

    @positive
    Scenario: Successful by Name
        Given I am in "http://localhost:3000"
        When I choose "<type>" on the search bar
        And I search for "<lego>"
        And I click Search
        Then I should see the item "<lego>" in my screen
        Examples:
            | lego       |   type   |
            | Lego Name  |   name   |

    @positive
    Scenario: Successful by Price
        Given I am in "http://localhost:3000"
        When I click on the search bar
        And I choose "<type>" on the dropdown
        And I search for "<lego>"
        And I click Search
        Then I should see the item "<lego>" in my screen
        Examples:
            | lego       |   type    |
            | Lego Price |   price   |
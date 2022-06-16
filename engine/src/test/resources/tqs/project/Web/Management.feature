Feature: Engine Management

    @positive
    Scenario: Successful View
        Given I am in "http://localhost:3001/management"
        When I look at the "<table>" table
        Then I can see there are more than "<num>" "<table>"
        Examples:
            |  table   |    num   |
            |  orders  |     0    |
            |  riders  |     1    |

    @positive
    Scenario: Successful Add Rider
        Given I am in "http://localhost:3001/management"
        When I input my "<name>", "<email>" and "<password>"
        And I click Add a Rider
        Then I can see the rider "<name>" was added
        Examples:
            |   name   |     email     |  password  |
            |  Filipe  | filipeg@ua.pt |   filipe   |
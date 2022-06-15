Feature: Legoliveries Login

    @positive
    Scenario: Successful
        Given I am in "http://localhost:3000/login"
        When I login with "<username>" and "<password>"
        And I click Submit
        Then I should "be logged in"
        Examples:
            | username | password |
            |  filipe  |  filipe  |

    @negative
    Scenario: Error
        Given I am in "http://localhost:3000/login"
        When I login with "<username>" and "<password>"
        And I click Submit
        Then I should "not be logged in"
        Examples:
            | username | password |
            |  filipe  |  admin   |
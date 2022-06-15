Feature: Legoliveries Login

    @positive
    Scenario: Successful
        Given I am in "http://172.20.202.3:3000/login"
        When I login with "<username>" and "<password>"
        And I click Login
        Then I should be logged in
        Examples:
            | username | password |
            |  filipe  |  filipe  |

    @negative
    Scenario: Error
        Given I am in "http://172.20.202.3:3000/login"
        When I login with "<username>" and "<password>"
        And I click Login
        Then I should not be logged in
        Examples:
            | username | password |
            |  filipe  |  admin   |

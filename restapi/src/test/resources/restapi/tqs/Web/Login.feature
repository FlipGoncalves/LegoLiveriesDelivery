Feature: Legoliveries Login

    @positive
    Scenario: Successful
        Given I am in "http://localhost:3000/login"
        When I login with "<email>" and "<password>"
        And I click Login
        Then I should be logged in
        Examples:
            |    email          |  password   |
            |  userA@gmail.com  |  userApass  |

    @negative
    Scenario: Error
        Given I am in "http://localhost:3000/login"
        When I login with "<email>" and "<password>"
        And I click Login
        Then I should not be logged in
        Examples:
            |  email         | password |
            |  filipe@ua.pt  |  admin   |

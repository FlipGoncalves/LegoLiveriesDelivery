Feature: Engine Login

    @positive
    Scenario: Successful
        Given I am in "http://localhost:3000/sign-in"
        When I login with "<email>" and "<password>"
        And I click Submit
        Then I should "be logged in"
        Examples:
            |  email         | password |
            |  filipe@ua.pt  |  filipe  |

    @negative
    Scenario: Error
        Given I am in "http://localhost:3000/sign-in"
        When I login with "<email>" and "<password>"
        And I click Submit
        Then I should "not be logged in"
        Examples:
            |  email         | password |
            |  filipe@ua.pt  |  admin   |

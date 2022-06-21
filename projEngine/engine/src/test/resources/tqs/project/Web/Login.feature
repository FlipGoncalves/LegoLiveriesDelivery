Feature: Engine Login

    # @positive
    # Scenario: Successful
    #     Given I am in "http://localhost:3001/sign-in"
    #     When I login with "<email>" and "<password>"
    #     And I click Login
    #     Then I should be logged in
    #     Examples:
    #         |  email            |  password   |
    #         |  admin@ua.com     |  admin      |

    @negative
    Scenario: Error
        Given I am in "http://localhost:3001/sign-in"
        When I login with "<email>" and "<password>"
        And I click Login
        Then I should not be logged in
        Examples:
            |  email         | password |
            |  filipe@ua.pt  |  admin   |

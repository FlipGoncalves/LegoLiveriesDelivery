Feature: Engine Register

    @positive
    Scenario: Successful
        Given I am in "http://localhost:3001/sign-up"
        When I register with "<username>", "<email>" and "<password>"
        And I click Register
        Then I should be registered
        Examples:
            | username | email         | password |
            |  filipe  | filipeg@ua.pt |  filipe  |

    # @negative
    # Scenario: Error
    #     Given I am in "http://localhost:3001/sign-up"
    #     When I register with "<username>", "<email>" and "<password>"
    #     And I click Register
    #     Then I should not be registered
    #     Examples:
    #         | username | email         | password |
    #         |  admin   | admin@ua.pt   |  admin   |

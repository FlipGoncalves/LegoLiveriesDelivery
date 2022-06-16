Feature: Legoliveries Register

    @positive
    Scenario: Successful
        Given I am in "http://localhost:3000/register"
        When I register with "<username>", "<email>", "<password>" and "<rep_password>"
        And I click Register
        Then I should be registered
        Examples:
            | username | password | rep_password | email         |
            |  filipe  |  filipe  |    filipe    | filipeg@ua.pt |

    # @negative
    # Scenario: Error
    #     Given I am in "http://localhost:3000/register"
    #     When I register with "<username>", "<email>", "<password>" and "<rep_password>"
    #     And I click Register
    #     Then I should not be registered
    #     Examples:
    #         | username | password | rep_password | email         |
    #         |  filipe  |  admin   |    admin     | filipeg@ua.pt |
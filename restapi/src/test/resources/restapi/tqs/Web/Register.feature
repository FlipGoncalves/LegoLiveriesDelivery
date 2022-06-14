Feature: Legoliveries Register

    @positive
    Scenario: Successful
        Given I am in "http://localhost:3000/register"
        When I register with "<username>" and "<password>" and "<rep_password>"
        And I click Submit
        Then I should "be registered"
        Examples:
            | username | password | rep_password |
            |  filipe  |  filipe  |    filipe    |

    @negative
    Scenario: Error
        Given I am in "http://localhost:3000/register"
        When I register with "<username>" and "<password>" and "<rep_password>"
        And I click Submit
        Then I should "not be registered"
        Examples:
            | username | password | rep_password |
            |  filipe  |  admin   |    admin     |
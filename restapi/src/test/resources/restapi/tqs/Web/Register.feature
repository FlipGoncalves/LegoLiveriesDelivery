Feature: Legoliveries Register

    @positive
    Scenario: Successful
        Given I am in "http://172.20.202.3:3000/register"
        When I register with "<username>" and "<password>" and "<rep_password>"
        And I click Register
        Then I should be registered
        Examples:
            | username | password | rep_password |
            |  filipe  |  filipe  |    filipe    |

    @negative
    Scenario: Error
        Given I am in "http://172.20.202.3:3000/register"
        When I register with "<username>" and "<password>" and "<rep_password>"
        And I click Register
        Then I should not be registered
        Examples:
            | username | password | rep_password |
            |  filipe  |  admin   |    admin     |
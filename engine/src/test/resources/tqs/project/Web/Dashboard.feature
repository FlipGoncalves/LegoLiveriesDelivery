Feature: Engine Dashboard

    @positive
    Scenario: Successful Normal Statistic
        Given I am in "http://localhost:3001"
        When I look at the number of "<type>" 
        Then I can see it is not "<num>"
        Examples:
            |  type    |    num   |
            |  orders  |     1    |
            |  riders  |     1    |
            |  compl   |     1    |


    # should be zero
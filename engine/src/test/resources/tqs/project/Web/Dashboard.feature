Feature: Engine Dashboard

    @positive
    Scenario: Successful Normal Statistic
        Given I am in "http://localhost:3001"
        When I look at the number of "<type>" 
        Then I can see it is not "<num>"
        Examples:
            |  type    |    num   |
            |  sales   |     0    |
            |  orders  |     0    |
            |  riders  |     0    |
            |  compl   |     0    |

    @negative
    Scenario: Error Normal Statistic
        Given I am in "http://localhost:3001"
        When I look at the number of "<type>" 
        Then I can see it is "<num>"
        Examples:
            |  type    |    num   |
            |  sales   |     0    |
            |  orders  |     0    |
            |  riders  |     0    |
            |  compl   |     0    |
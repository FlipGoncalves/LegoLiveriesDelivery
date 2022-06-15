Feature: Engine Dashboard

    @positive
    Scenario: Successful Normal Statistic
        Given I am in "http://172.20.200.3:3001"
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
        Given I am in "http://172.20.200.3:3001"
        When I look at the number of "<type>" 
        Then I can see it is "<num>"
        Examples:
            |  type    |    num   |
            |  sales   |     0    |
            |  orders  |     0    |
            |  riders  |     0    |
            |  compl   |     0    |

    
    # @positive
    # Scenario: Successful Graphs
    #     Given I am in "http://localhost:3000"
    #     When I look at the the graph of "<type>" 
    #     Then I can see that "<num>"
    #     Examples:
    #         |  type    |    num   |
    #         |  sales   |     0    |
    #         |  orders  |     0    |
    #         |  riders  |     0    |
    #         |  compl   |     0    |

    # @negative
    # Scenario: Error Graphs
    #     Given I am in "http://localhost:3000"
    #     When I look at the number of "<type>" 
    #     Then I can see it is "<num>"
    #     Examples:
    #         |  type    |    num   |
    #         |  sales   |     0    |
    #         |  orders  |     0    |
    #         |  riders  |     0    |
    #         |  compl   |     0    |

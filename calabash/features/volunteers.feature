Feature: Volunteers list, details

    Scenario: As valid user I can show list of volunteers and see its details
        When I login into app

        Then I swipe left
        * I wait for a second
        Then I touch the "Lista wolontariuszy" text
        Then I see the text "Paweł Nowak"
        * I wait for a second
        Then I touch the "Paweł Nowak" text

        * I wait for a second
        Then I scroll recyclerview down
        Then I scroll recyclerview down
        Then I see the text "Uszczęśliw seniora"
        Then I touch the "Uszczęśliw seniora" text


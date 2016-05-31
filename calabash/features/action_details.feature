Feature: Action details feature

    Scenario: As valid user I can see action details
        When I login into app

        Then I scroll until I see the "Oferta 5" text
        Then I see the text "Oferta 5"
        Then I touch the "Oferta 5" text

        * I wait for a second
        * I go back

        Then I see the text "Oferta 1"
        Then I touch the "Oferta 1" text


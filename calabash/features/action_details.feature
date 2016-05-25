Feature: Action details feature

    Scenario: As valid user I can see action details
        When I see "ZALOGUJ"
        Then I enter text "test" into field with id "edit_text_email"
        Then I enter text "test" into field with id "edit_text_password"
        Then I press button number 1
        And I wait up to 10 seconds for the "MainHostActivity" screen to appear

        Then I scroll recyclerview down
        Then I see the text "Oferta 5"
        Then I touch the "Oferta 5" text

        * I wait for a second
        * I go back

        Then I see the text "Oferta 1"
        Then I touch the "Oferta 1" text


Feature: Action details feature

    Scenario: As valid user I can see action details
        When I login into app

				Then I see the text "Hokus Pokus"
        Then I touch the "Hokus Pokus" text

        * I wait for a second
        * I go back

        Then I scroll until I see the "Uszczęśliw seniora :)" text
        Then I see the text "Uszczęśliw seniora :)"
        Then I touch the "Uszczęśliw seniora :)" text



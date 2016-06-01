Feature: Volunteers list, details

    Scenario: As valid user I can show list of volunteers and see its details
        When I login into app

        Then I swipe left
        Then I touch the "Lista wolontariuszy" text
        Then I see the text "Paweł Nowak"
        Then I touch the "Paweł Nowak" text

        Then I scroll until I see the "Uszczęśliw seniora" text
        Then I see the text "Uszczęśliw seniora"
		* I go back

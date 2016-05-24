Feature: Volunteers list feature

  Scenario: As a valid user I can view list of volunteers
    When I see "ZALOGUJ"
	Then I enter text "test@test.tst" into field with id "edit_text_email"
	Then I enter text "test" into field with id "edit_text_password"
	Then I press button number 1
    And I wait up to 40 seconds for the "MainHostActivity" screen to appear

    Then I swipe left
    And I touch the "Lista wolontariuszy" text
    Then I scroll recyclerview down
    Then I see "jan.kowalski@test.pl"
    Then I touch the "jan.kowalski@test.pl" text
    And I wait up to 40 seconds for the "VolunteerDetailsActivity" screen to appear


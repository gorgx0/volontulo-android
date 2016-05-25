Feature: Volunteers list feature

  Scenario: As a valid user I can view list of volunteers
    When I see "ZALOGUJ"
	Then I enter text "test@test.tst" into field with id "edit_text_email"
	Then I enter text "test" into field with id "edit_text_password"
	Then I press button number 1
    And I wait to see "Niepoprawny użytkownik lub hasło"

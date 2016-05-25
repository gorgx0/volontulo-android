Feature: Login feature

  Scenario: As a valid user I can log into application
    When I see "ZALOGUJ"
	Then I enter text "test" into field with id "edit_text_email"
	Then I enter text "test" into field with id "edit_text_password"
	Then I press button number 1
    And I wait up to 5 seconds for the "MainHostActivity" screen to appear

  Scenario: As a invalid user I cannot log in into application
    When I see "ZALOGUJ"
	Then I enter text "test@test.tst" into field with id "edit_text_email"
	Then I enter text "test" into field with id "edit_text_password"
	Then I press button number 1
    And I wait to see "Niepoprawny użytkownik lub hasło"


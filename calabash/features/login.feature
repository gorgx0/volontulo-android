Feature: Login feature

  Scenario: As a valid user I can log into application
	* I wait for 1 seconds
	* I wait for the "SplashActivity" screen to appear
	* I wait for 1 seconds
    When I see "ZALOGUJ"
	Then I enter text "test@test.com" into field with id "edit_text_email"
	Then I enter text "test123" into field with id "edit_text_password"
	Then I press button number 1
    And I wait up to 5 seconds for the "MainHostActivity" screen to appear

  Scenario: As a invalid user I cannot log in into application
	* I swipe left
  Then I touch the "Wyloguj" text
	* I wait for 1 seconds
    When I see "ZALOGUJ"
	Then I enter text "test@qa.pl" into field with id "edit_text_email"
	Then I enter text "test" into field with id "edit_text_password"
	Then I press button number 1
    And I wait to see "Niepoprawny użytkownik lub hasło"


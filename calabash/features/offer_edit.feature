Feature: Volunteers list feature

  Scenario: As a valid user I can edit an offer
    When I see "ZALOGUJ"
		Then I enter text "january.blefuje@supermaczo.pl" into field with id "edit_text_email"
		Then I enter text "test123" into field with id "edit_text_password"
		Then I press button number 1
    And I wait up to 3 seconds for the "MainHostActivity" screen to appear

    When I see "Hokus Pokus"
    Then I press "Hokus Pokus"

    * I wait up to 3 seconds for the "OfferDetailsActivity" screen to appear
		Then I tap on menu item with id "action_offer_edit" at toolbar
		* I wait for 1 seconds
		Then I clear input field with id "offer_name"
		* I hide the keyboard
		Then I press button number 1
		Then I should see "Określ, jak oferta będzie nazywać się w systemie, staraj się być dokładnym."
		* I wait for 5 seconds
		
		Then I clear input field with id "offer_description"	
		* I hide the keyboard
		Then I press button number 1
		* I wait for 1 seconds
		Then I should see "Postaraj się dokładnie opisać akcję."

		Then I clear input field with id "offer_time_requirement"	
		* I hide the keyboard
		Then I press button number 1
		* I wait for 1 seconds
		Then I should see "Określ wymagania czasowe - ile godzin pracy w ciągu dnia wymaga dana akcja."

		Then I clear input field with id "offer_benefits"	
		* I hide the keyboard
		Then I press button number 1
		* I wait for 1 seconds
		Then I should see "Określ korzyści płynące z udziału w akcji."

		* I scroll up
		* I scroll up
		* I wait for 1 seconds

		* I enter text "Hokus Pokus" into field with id "offer_name"
		* I hide the keyboard
		* I wait for 1 seconds
		* I enter text "Czary Mary" into field with id "offer_description"
		* I hide the keyboard
		* I wait for 1 seconds
		* I enter text "Twoja Stara" into field with id "offer_time_requirement"
		* I hide the keyboard
		* I wait for 1 seconds
		* I enter text "To Twoj Stary" into field with id "offer_benefits"
		* I hide the keyboard
		* I wait for 1 seconds
	
		Then I press button number 1
		And I wait up to 3 seconds for the "MainHostActivity" screen to appear
	

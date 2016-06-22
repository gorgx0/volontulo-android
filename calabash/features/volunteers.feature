Feature: Volunteers list, details

    Scenario: As valid user I can show list of volunteers and see its details
        When I login into app

        Then I swipe left
				* I wait for 1 seconds
        Then I touch the "Lista wolontariuszy" text
        Then I scroll until I see the "Radosław Drewa" text
        Then I press "Radosław Drewa"

        Then I scroll until I see the "Zbiórka materiałów szkolnych" text
				* I wait up to 3 seconds for the "VolunteerDetailsActivity" screen to appear
        Then I see the text "Zbiórka materiałów szkolnych"
				* I wait for 3 seconds
				* I go back

	     	Then I swipe left
				* I wait for 1 seconds
        Then I touch the "Wyloguj" text
				* I wait for 1 seconds
				Then I enter text "zw65623@st.amu.edu.pl" into field with id "edit_text_email"
				Then I enter text "test123" into field with id "edit_text_password"
				* I wait for 1 seconds
				Then I press button number 1
    		And I wait up to 5 seconds for the "MainHostActivity" screen to appear



#Then I press button number 1
#    And I wait up to 5 seconds for the "MainHostActivity" screen to appear

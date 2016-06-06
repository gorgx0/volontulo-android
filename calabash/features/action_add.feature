Feature: Add action feature

    Scenario: Empty form add
        When I login into app
        Then I tap on menu item with id "action_add_offer" at toolbar
        Then I press the "ZAPISZ" button 
        Then I see "Określ, jak oferta będzie nazywać się w systemie, staraj się być dokładnym"
        And I see "Określ, w jakim miejscu odbywać się będzie wolontariat"
        And I see "Postaraj się dokładnie opisać akcję"
				* I scroll down
        Then I see "Określ wymagania czasowe - ile godzin pracy w ciągu dnia wymaga dana akcja"
        And I see "Określ korzyści płynące z udziału w akcji"
        Then I go back


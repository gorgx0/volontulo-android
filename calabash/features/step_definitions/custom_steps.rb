Then /^I scroll recyclerview down$/ do
    scroll('recyclerView', :down)
end

Then /^I hide the keyboard$/ do
    hide_soft_keyboard()
end

Then /^I login into app$/ do
    wait_for_text("ZALOGUJ", timeout: 10)
    enter_text("android.widget.EditText id:'edit_text_email'", "test")
    enter_text("android.widget.EditText id:'edit_text_password'", "test")
    tap_when_element_exists("android.widget.Button index:0")
    wait_for_activity("MainHostActivity", timeout:5)
end

Then /^I tap on menu item with id "([^\"]*)" at toolbar$/ do |text|
    tap_when_element_exists("android.support.v7.widget.Toolbar android.widget.TextView id:'#{text}'")
end

Then /^I scroll until I see the "([^\"]*)" text$/ do |text|
    q = query("android.widget.TextView text:'#{text}'")
    while q.empty?
        scroll('recyclerView', :down)
        q = query("android.widget.TextView text:'#{text}' timeout:10")
		if element_exists("android.widget.TextView text:'#{text}'")
			break
		end
    end
end

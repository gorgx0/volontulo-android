package com.stxnext.volontulo.ui.im;

import android.support.design.widget.FloatingActionButton;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseFragment;
import com.stxnext.volontulo.api.User;
import com.stxnext.volontulo.logic.im.Conversation;
import com.stxnext.volontulo.logic.im.config.ImConfigFactory;
import com.stxnext.volontulo.logic.im.config.ImConfiguration;
import com.stxnext.volontulo.ui.utils.SimpleItemDivider;
import com.stxnext.volontulo.utils.realm.RealmString;

import org.parceler.Parcels;

import butterknife.Bind;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ConversationListFragment extends VolontuloBaseFragment {
    private static final int REQUEST_CHOOSE_VOLUNTEER = 1000;
    private static final String TAG = "Volontulo-Im";

    @Bind(R.id.list)
    protected RecyclerView conversationList;
    private Realm realm;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_list;
    }

    @Override
    protected void onPostCreateView(View root) {
        super.onPostCreateView(root);
        realm = Realm.getDefaultInstance();
        requestFloatingActionButton();
        setToolbarTitle(R.string.im_conversation_list_title);
        conversationList.setLayoutManager(new LinearLayoutManager(getActivity()));
        final String preferencesFileName = ImConfigFactory.create().getPreferencesFileName();
        final SharedPreferences preferences = getActivity().getSharedPreferences(preferencesFileName, Context.MODE_PRIVATE);
        final String currentUser = preferences.getString("user", "");

        final RealmResults<Conversation> conversations = realm.where(Conversation.class)
            .equalTo(Conversation.FIELD_CREATOR_ID, currentUser)
            .or().equalTo(String.format("%s.%s", Conversation.FIELD_RECIPIENTS_IDS, RealmString.FIELD_VALUE), currentUser).findAll();
        final ConversationsAdapter adapter = new ConversationsAdapter(getActivity(), conversations);
        conversationList.setAdapter(adapter);
        conversationList.addItemDecoration(new SimpleItemDivider(getActivity()));
        conversationList.setHasFixedSize(true);
    }

    @Override
    protected void onFabClick(FloatingActionButton button) {
        final RecipientChooserDialog chooserDialog = new RecipientChooserDialog();
        chooserDialog.setTargetFragment(this, REQUEST_CHOOSE_VOLUNTEER);
        chooserDialog.show(getFragmentManager(), RecipientChooserDialog.EXTRA_KEY_USER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHOOSE_VOLUNTEER && resultCode == Activity.RESULT_OK) {
            final User grabbedUser = Parcels.unwrap(data.getParcelableExtra(RecipientChooserDialog.EXTRA_KEY_USER));
            realm = Realm.getDefaultInstance();
            Conversation result;
            final String userEmail = grabbedUser.getEmail();
            result = realm.where(Conversation.class)
                .equalTo(Conversation.FIELD_CREATOR_ID, userEmail)
                .or().equalTo(String.format("%s.%s", Conversation.FIELD_RECIPIENTS_IDS, RealmString.FIELD_VALUE), userEmail)
                .findFirst();
            if (result == null) {
                Log.d(TAG, "No conversation found, so we create new one");
                result = Conversation.create(retrieveCurrentUser(), new RealmList<>(new RealmString(userEmail)));
            }
            Log.i(TAG, String.format("Conversation: %s", result));
            final Intent starter = new Intent(getActivity(), MessagingActivity.class);
            starter.putExtra(MessagesListFragment.KEY_PARTICIPANTS, Parcels.wrap(result));
            startActivity(starter);
        }
    }

    private String retrieveCurrentUser() {
        final ImConfiguration configuration = ImConfigFactory.create();
        final SharedPreferences preferences = getActivity().getSharedPreferences(configuration.getPreferencesFileName(), Context.MODE_PRIVATE);
        return preferences.getString("user", "");
    }
}

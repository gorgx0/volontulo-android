package com.stxnext.volontulo.ui.im;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseFragment;
import com.stxnext.volontulo.logic.im.Conversation;
import com.stxnext.volontulo.logic.im.config.ImConfigFactory;
import com.stxnext.volontulo.logic.im.config.ImConfiguration;
import com.stxnext.volontulo.ui.utils.SimpleItemDivider;
import com.stxnext.volontulo.utils.realm.RealmString;

import java.util.UUID;

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
        setToolbarTitle(R.string.im_conversaion_list_title);
        conversationList.setLayoutManager(new LinearLayoutManager(getActivity()));
        final ConversationsAdapter adapter = new ConversationsAdapter(getActivity());
        conversationList.setAdapter(adapter);
        conversationList.addItemDecoration(new SimpleItemDivider(getActivity()));
        conversationList.setHasFixedSize(true);
        final RealmResults<Conversation> conversations = realm.where(Conversation.class).findAll();
        adapter.updateList(conversations);
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
            //TODO grab data from intent and create conversation and switch to new conversation or update current conversation
            Toast.makeText(getActivity(), String.format("Grabbed: %s", data.getStringExtra("user")), Toast.LENGTH_LONG).show();
            final String grabbedUser = data.getStringExtra(RecipientChooserDialog.EXTRA_KEY_USER);
            realm = Realm.getDefaultInstance();
            Conversation result;
            result = realm.where(Conversation.class).equalTo("creatorId", grabbedUser).or().equalTo("recipientsIds.value", grabbedUser).findFirst();
            if (result == null) {//no found conversation
                Log.d(TAG, "No conversation found, so we create new one");
                final String uuidConversation = UUID.randomUUID().toString();
                final RealmString[] recipientStrings = new RealmString[] {
                    new RealmString(grabbedUser)
                };
                result = Conversation.create(uuidConversation, retrieveCurrentUser(), new RealmList<>(recipientStrings));
                Log.i(TAG, String.format("Conversation: %s", result));
                realm.beginTransaction();
                realm.copyToRealm(result);
                realm.commitTransaction();
            } else {//go with this one
                Log.d(TAG, "Conversation currently is existing, so we reused this one");
            }
        }
    }

    private String retrieveCurrentUser() {
        final ImConfiguration configuration = ImConfigFactory.create();
        final SharedPreferences preferences = getActivity().getSharedPreferences(configuration.getPreferencesFileName(), Context.MODE_PRIVATE);
        return preferences.getString("user", "");
    }
}

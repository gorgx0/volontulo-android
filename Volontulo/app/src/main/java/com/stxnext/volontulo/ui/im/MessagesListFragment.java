package com.stxnext.volontulo.ui.im;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseFragment;

import butterknife.Bind;

public class MessagesListFragment extends VolontuloBaseFragment {
    public static final String KEY_PARTICIPANTS = "participants";

    public interface InstantMessagingViewCallback {
        void onMessageComposed(String recipient, String body);
    }

    @Bind(R.id.list)
    protected RecyclerView messagesList;

    @Bind(R.id.message)
    protected EditText message;

//    @Bind(R.id.send)
//    protected ImageButton send;

    private InstantMessagingViewCallback viewCallback;
    private String participantName;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_message_list;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InstantMessagingViewCallback) {
            viewCallback = (InstantMessagingViewCallback) context;
        }
    }

//    @OnClick(R.id.send)
//    void onSendClicked() {
//        final String messageText = message.getText().toString();
//        if (viewCallback != null && !TextUtils.isEmpty(participantName) && !TextUtils.isEmpty(messageText)) {
//            viewCallback.onMessageComposed(participantName, messageText);
//        }
//    }

    @Override
    protected void onPostCreateView(View root) {
        super.onPostCreateView(root);
        final Bundle args = getArguments();
        participantName = args.getString(KEY_PARTICIPANTS);
        setToolbarTitle(getResources().getString(R.string.im_conversation_with_title, participantName));
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setStackFromEnd(true);
        messagesList.setLayoutManager(layoutManager);
        messagesList.setAdapter(new MessagesAdapter(getActivity()));

//        setBackgroundTintCompatibleOnSendButton();
    }
//
//    private void setBackgroundTintCompatibleOnSendButton() {
//        final Drawable wrapped = DrawableCompat.wrap(send.getBackground());
//        DrawableCompat.setTint(wrapped, ContextCompat.getColor(getActivity(), R.color.colorPrimary));
//        setBackgroundCompatible(send, wrapped);
//    }
//
//    public void setBackgroundCompatible(final View view, final Drawable backgroundDrawable) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            view.setBackground(backgroundDrawable);
//        } else {
//            view.setBackgroundDrawable(backgroundDrawable);
//        }
//    }
}

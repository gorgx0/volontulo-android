package com.stxnext.volontulo.ui.im;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.stxnext.volontulo.BuildConfig;
import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloApp;
import com.stxnext.volontulo.api.Image;
import com.stxnext.volontulo.api.User;
import com.stxnext.volontulo.api.UserProfile;
import com.stxnext.volontulo.ui.login.LoginFragment;
import com.stxnext.volontulo.ui.utils.SimpleItemDivider;
import com.stxnext.volontulo.ui.volunteers.UserProfileAdapter;

import org.parceler.Parcels;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipientChooserDialog extends DialogFragment {
    public static final String EXTRA_KEY_USER = "user";
    private RecyclerView volunteers;
    private UserProfile selected;
    private UserProfileAdapter userProfileAdapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View root = getActivity().getLayoutInflater().inflate(R.layout.fragment_list, null);
        volunteers = (RecyclerView) root.findViewById(R.id.list);
        obtainData();
        builder.setView(root);
        builder.setTitle(R.string.chooser_volunteer_title);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.chooser_volunteer_select, null);
        builder.setNegativeButton(R.string.chooser_volunteer_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, new Intent());
                dialog.dismiss();
            }
        });
        volunteers.setLayoutManager(new LinearLayoutManager(getActivity()));
        volunteers.addItemDecoration(new SimpleItemDivider(getActivity()));
        volunteers.setHasFixedSize(true);
        userProfileAdapter = new UserProfileAdapter(getActivity(), new UserProfileAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View clicked, int position, UserProfile item) {
                selected = item;
                userProfileAdapter.setSelected(position);
            }
        }) {
            @Override
            protected int getLayoutResource(int viewType) {
                return R.layout.item_volunteer_chooser;
            }
        };
        volunteers.setAdapter(userProfileAdapter);
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        final AlertDialog dialog = (AlertDialog) getDialog();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected != null) {
                    final Intent result = new Intent();
                    result.putExtra(EXTRA_KEY_USER, Parcels.wrap(selected.getUser()));
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, result);
                    dialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), R.string.chooser_select_one, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void obtainData() {
        final Call<List<UserProfile>> call = VolontuloApp.cachedApi.listVolunteers();
        call.enqueue(new Callback<List<UserProfile>>() {
            @Override
            public void onResponse(Call<List<UserProfile>> call, Response<List<UserProfile>> response) {
                final List<UserProfile> userProfileList = response.body();
                if (BuildConfig.DEBUG) {
                    Snackbar.make(
                            volunteers.getRootView(),
                            String.format(Locale.getDefault(), "%d profiles returned", userProfileList.size()),
                            Snackbar.LENGTH_LONG
                    ).show();
                    UserProfile[] mockProfiles = new UserProfile[LoginFragment.MOCK_USER_TABLE.length];
                    int index = 0;
                    for (User user : LoginFragment.MOCK_USER_TABLE) {
                        final UserProfile profile = new UserProfile();
                        profile.setUser(user);
                        profile.setImages(new RealmList<Image>());
                        mockProfiles[index++] = profile;
                    }
                    userProfileList.addAll(Arrays.asList(mockProfiles));
                }
                if (userProfileList.size() > 0) {
                    userProfileAdapter.swap(userProfileList);
                }
            }

            @Override
            public void onFailure(Call<List<UserProfile>> call, Throwable t) {
                Snackbar.make(volunteers.getRootView(), "Error", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}

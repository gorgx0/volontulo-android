package com.stxnext.volontulo.ui.im;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloApp;
import com.stxnext.volontulo.api.UserProfile;
import com.stxnext.volontulo.ui.utils.SimpleItemDivider;
import com.stxnext.volontulo.ui.volunteers.UserProfileAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipientChooserDialog extends DialogFragment {
    private RecyclerView volunteers;
    private UserProfile selected;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View root = getActivity().getLayoutInflater().inflate(R.layout.fragment_list, null);
        volunteers = (RecyclerView) root.findViewById(R.id.list);
        obtainData();
        builder.setView(root);
        builder.setTitle("Choose One");
        builder.setPositiveButton("Sel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (selected != null) {
                    final Intent result = new Intent();
                    result.putExtra("user", selected.getUser().getUsername());
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, result);
                    dialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "Select one user", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("CanSel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, new Intent());
                dialog.dismiss();
            }
        });
        volunteers.setLayoutManager(new LinearLayoutManager(getActivity()));
        volunteers.addItemDecoration(new SimpleItemDivider(getActivity()));
        volunteers.setHasFixedSize(true);
        return builder.create();
    }

    private void obtainData() {
        final Call<List<UserProfile>> call = VolontuloApp.api.listVolunteers();
        call.enqueue(new Callback<List<UserProfile>>() {
            @Override
            public void onResponse(Call<List<UserProfile>> call, Response<List<UserProfile>> response) {
                final List<UserProfile> userProfileList = response.body();
                final UserProfileAdapter adapter = new UserProfileAdapter(getActivity(), userProfileList, new UserProfileAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View clicked, UserProfile item) {
                        selected = item;
                        clicked.setSelected(true);
                    }
                });
                volunteers.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<UserProfile>> call, Throwable t) {
            }
        });
    }
}

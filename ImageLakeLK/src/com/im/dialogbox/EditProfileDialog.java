package com.im.dialogbox;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;

import com.im.ImageLakeLK.EditActivity;
import com.im.ImageLakeLK.EditProfileActivity;
import com.im.ImageLakeLK.R;
import com.im.sync.SyncThread;

/**
 * Created by RLN on 4/13/2015.
 */
public class EditProfileDialog extends DialogFragment{

    EditActivity ea;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirmation");
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        ea = (EditActivity)getActivity();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.edit_profile_dialog, null))

                // Add action buttons
                .setPositiveButton(R.string.edit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //NavUtils.navigateUpFromSameTask(getActivity());
                       // getActivity().finish();
                        SyncThread st = new SyncThread(ea);
                        st.execute();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditProfileDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();



    }
}

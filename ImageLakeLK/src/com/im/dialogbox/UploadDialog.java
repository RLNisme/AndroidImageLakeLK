package com.im.dialogbox;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.im.ImageLakeLK.MyUploadsActivity;
import com.im.ImageLakeLK.R;
import com.im.ImageLakeLK.UploadActivity;
import com.im.sync.SyncThread;

/**
 * Created by RLN on 4/22/2015.
 */
public class UploadDialog extends DialogFragment {

    UploadActivity ua;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirmation");
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        ua = (UploadActivity)getActivity();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.upload_dialog, null))

                // Add action buttons
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //NavUtils.navigateUpFromSameTask(getActivity());
                        // getActivity().finish();
                        // request = 1;
                        ua.addListData();

                        SyncThread st = new SyncThread(ua);
                        st.execute();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UploadDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();



    }
}

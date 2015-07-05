package com.im.dialogbox;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.im.ImageLakeLK.CartActivity;
import com.im.ImageLakeLK.R;
import com.im.sync.SyncThread;

/**
 * Created by RLN on 6/2/2015.
 */
public class DownloadDialog extends DialogFragment {
    CartActivity crt;
    SyncThread st;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirmation");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        crt = (CartActivity)getActivity();
        builder.setView(inflater.inflate(R.layout.download_dialog, null))

                // Add action buttons
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //NavUtils.navigateUpFromSameTask(getActivity());
                        // getActivity().finish();
                        crt.type = crt.ADD_LIGHTBOX;
                        st = new SyncThread(crt);
                        st.execute();
                        DownloadDialog.this.getDialog().cancel();

                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        DownloadDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();

    }
}

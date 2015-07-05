package com.im.dialogbox;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.im.ImageLakeLK.EditActivity;
import com.im.ImageLakeLK.LightBoxActivity;
import com.im.ImageLakeLK.MyUploadsActivity;
import com.im.ImageLakeLK.R;
import com.im.adapter.LightBoxAdapter;
import com.im.sync.SyncThread;

/**
 * Created by RLN on 4/20/2015.
 */
public class MyUploadDialog extends DialogFragment{

    MyUploadsActivity mua;
    public int request ;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirmation");
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        mua = (MyUploadsActivity)getActivity();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.myupload_dialog, null))

                // Add action buttons
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //NavUtils.navigateUpFromSameTask(getActivity());
                        // getActivity().finish();
                       // request = 1;
                        mua.addListData();
                        SyncThread st = new SyncThread(mua);
                        st.execute();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MyUploadDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();



    }

}

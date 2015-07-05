package com.im.dialogbox;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.im.ImageLakeLK.LightBoxActivity;
import com.im.ImageLakeLK.R;
import com.im.adapter.LightBoxAdapter;

/**
 * Created by RLN on 4/11/2015.
 */
public class LightBoxDialog extends DialogFragment{


    LightBoxActivity lbac;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.sort_lightbox)
               .setItems(R.array.sort_lightbox_by, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {
                       // The 'which' argument contains the index position
                       // of the selected item

                       lbac =(LightBoxActivity)getActivity();

                       if(which == 0){

                           lbac.lbad = new LightBoxAdapter(lbac,lbac.user_id,2,lbac);
                           lbac.lv.setAdapter(lbac.lbad);
                           lbac.lv.setOnItemClickListener(lbac);

                       }
                   }
               });
        return builder.create();

    }
}

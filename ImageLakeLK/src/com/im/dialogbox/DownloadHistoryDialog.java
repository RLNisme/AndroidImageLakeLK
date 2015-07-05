package com.im.dialogbox;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by RLN on 4/12/2015.
 */


import com.im.ImageLakeLK.DownloadHistoryActivity;
import com.im.ImageLakeLK.R;
import com.im.adapter.DownloadHistoryAdapter;
import com.im.adapter.LightBoxAdapter;

public class DownloadHistoryDialog extends DialogFragment {
    DownloadHistoryActivity dha;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.sort_downloadhistory)
               .setItems(R.array.sort_downloadhistory_by,new DialogInterface.OnClickListener(){
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dha =(DownloadHistoryActivity)getActivity();

                       if(which == 0){

                           dha.dhaa = new DownloadHistoryAdapter(dha,dha.user_id,2,dha);
                           dha.lv.setAdapter(dha.dhaa);
                           dha.lv.setOnItemClickListener(dha);

                       }else{
                           dha.dhaa = new DownloadHistoryAdapter(dha,dha.user_id,3,dha);
                           dha.lv.setAdapter(dha.dhaa);
                           dha.lv.setOnItemClickListener(dha);
                       }
                   }
               })  ;
        return builder.create();
    }
}

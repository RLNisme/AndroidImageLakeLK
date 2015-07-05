package com.im.dialogbox;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.im.ImageLakeLK.EarningManagementActivity;
import com.im.ImageLakeLK.R;
import com.im.sync.SMS;
import com.im.sync.SyncThread;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by RLN on 4/26/2015.
 */
public class EarningsettleDialog extends DialogFragment {

    EarningManagementActivity ema;
    EarningsettleDialog esd;
    public String type;
    public int idd;
    public int ruid;
    public int user_id;
    public double amount;
    public int state;

    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String date1 = sdf.format(d);


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Sort by");
        esd = this;
        LayoutInflater inflater = getActivity().getLayoutInflater();

        ema = (EarningManagementActivity)getActivity();
        idd = ema.idd;
        ruid = ema.ruid;
        user_id = ema.user_id;
        amount = ema.amount;


        builder.setView(inflater.inflate(R.layout.earningconfirmation_dialog, null))

                .setPositiveButton(R.string.settle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                            type = "settle";
                            state = 2;
                        SyncThread st = new SyncThread(esd);
                        st.execute();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        type = "cancel";
                        state = 3;
                        SyncThread st = new SyncThread(esd);
                        st.execute();

                        EarningsettleDialog.this.getDialog().cancel();
                    }
                });

        return builder.create();


    }

    public void stringErrorMsg(String s) {
        ema.errorMsg(s);
    }

    public void JSONResponse(String s) {

    }

    public void stringResponse(String s) {

        if(s.charAt(0)=='x'){

            String m[] = s.split("-");
            boolean b = SMS.send_SMS(m[1],"ImageLake has confirmed you earning request in "+date1);
            if(b) {
                Toast.makeText(ema, "Successfully Confirmed.", Toast.LENGTH_LONG).show();
                ema.loadEarningsDetails();
            }else{
                Toast.makeText(ema, "SMS error.", Toast.LENGTH_LONG).show();
            }
        }else{
            ema.msgResponse(s);
        }
    }
}

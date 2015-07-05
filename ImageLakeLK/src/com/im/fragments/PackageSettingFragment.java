package com.im.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.im.ImageLakeLK.CreditPackageActivity;
import com.im.ImageLakeLK.CreditSizeActivity;
import com.im.ImageLakeLK.R;
import com.im.ImageLakeLK.SubscriptionPackageActivity;
import com.im.sync.SyncThread;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by RLN on 5/4/2015.
 */
public class PackageSettingFragment extends Fragment implements View.OnClickListener{

    private final static String DAILY_LOAD_ALL ="Daily";
    private final static String DAILY_UPDATE ="DailyUpdate";
    private final static String UNLIMIT_LOAD_ALL ="Unlimit";
    private final static String UNLIMIT_UPDATE ="UnlimitUpdate";
    private final static String ADMIN_SPLIT_PERCENT_LOAD_ALL ="AdminSplitPercent";
    private final static String ADMIN_SPLIT_MIN_LOAD_ALL ="AdminSplitMin";
    private final static String ADMIN_SPLIT_UPDATE ="AdminSplitUpdate";
    private final static String ADMIN_MIN_UPDATE ="AdminMinUpdate";
    private final static String ADMIN_ADD_DWN ="AddDownloads";


    PackageSettingFragment pksf;
    Button btn_daily,btn_unlimited,btn_crdpack,btn_subpack,btn_crdsiz,btn_spitpack,btn_minpack;
    EditText daily_day,daily_down,adsplit_percent,adsplit_min;
    Button btn_up_daily,btn_up_daily_cancel,btn_up_adsplit_cancel,btn_up_adsplit,btn_dwn;
    SyncThread st;
    TextView split_title;

    public String type = "";


    public int daily_days = 0;
    public int daily_downloads = 0;

    public int unlimit_days = 0;
    public int unlimit_downloads = 0;

    public int adsplit_percents =0;
    public double adsplit_mins = 0;

    public int dwn_count =0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.packagesetting_layout,container,false);

        pksf = this;

        btn_daily =(Button)v.findViewById(R.id.btn_daily);
        btn_daily.setOnClickListener(this);

        btn_unlimited =(Button)v.findViewById(R.id.btn_unlimted);
        btn_unlimited.setOnClickListener(this);

        btn_crdpack =(Button)v.findViewById(R.id.btn_crdpack);
        btn_crdpack.setOnClickListener(this);

        btn_subpack =(Button)v.findViewById(R.id.btn_subpack);
        btn_subpack.setOnClickListener(this);

        btn_crdsiz =(Button)v.findViewById(R.id.btn_crdsize);
        btn_crdsiz.setOnClickListener(this);

        btn_spitpack =(Button)v.findViewById(R.id.btn_spitpack);
        btn_spitpack.setOnClickListener(this);

        btn_minpack =(Button)v.findViewById(R.id.btn_minpack);
        btn_minpack.setOnClickListener(this);

        btn_dwn =(Button)v.findViewById(R.id.btn_dwn);
        btn_dwn.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_daily){

            type = DAILY_LOAD_ALL;
            st = new SyncThread(pksf);
            st.execute();

        }else if(v.getId() == R.id.btn_unlimted){
            type = UNLIMIT_LOAD_ALL;
            st = new SyncThread(pksf);
            st.execute();
        }else if(v.getId() == R.id.btn_crdpack){
            Intent i = new Intent(getActivity(), CreditPackageActivity.class);
            startActivity(i);
        }else if(v.getId() == R.id.btn_subpack){
            Intent i = new Intent(getActivity(),SubscriptionPackageActivity.class);
            startActivity(i);
        }else if(v.getId() == R.id.btn_crdsize){
            Intent i = new Intent(getActivity(), CreditSizeActivity.class);
            startActivity(i);
        }else if(v.getId() == R.id.btn_spitpack){
            type = ADMIN_SPLIT_PERCENT_LOAD_ALL;
            st = new SyncThread(pksf);
            st.execute();
        }else if(v.getId() == R.id.btn_minpack){
            type = ADMIN_SPLIT_MIN_LOAD_ALL;
            st = new SyncThread(pksf);
            st.execute();
        }else if(v.getId() == R.id.btn_dwn){

            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.adminsplit_dialog);
            dialog.setTitle("Download Count");


            split_title =(TextView)dialog.findViewById(R.id.admin_title);
            split_title.setText(getActivity().getResources().getString(R.string.admin_min_earning));

            adsplit_min = (EditText) dialog.findViewById(R.id.adsplit_percent);
            adsplit_min.setHint("Download Count");
            adsplit_min.setInputType(0x00000002);


            btn_up_adsplit = (Button) dialog.findViewById(R.id.btn_up_adsplit);
            btn_up_adsplit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (adsplit_min.getText().toString().trim().equals("")) {
                        dwn_count = 0;
                    } else {
                        dwn_count = Integer.parseInt(adsplit_min.getText().toString());
                    }


                    if (dwn_count == 0) {
                        Toast.makeText(getActivity(), "Please check details you have entered.", Toast.LENGTH_LONG).show();
                    } else {
                        type = ADMIN_ADD_DWN;
                        st = new SyncThread(pksf);
                        st.execute();
                        dialog.dismiss();
                    }
                }
            });

            btn_up_adsplit_cancel = (Button) dialog.findViewById(R.id.btn_up_adsplit_cancel);
            btn_up_adsplit_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();


        }
    }

    public void stringErrorMsg(String s) {
        Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
    }

    public void JSONResponse(String s) {

        try {

            JSONArray ja = new JSONArray(s);
            JSONObject jo;

            if(type.equals(DAILY_LOAD_ALL)){

                if(ja.length()>0) {

                    jo = ja.getJSONObject(0);



                    final Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.dailypackage_dialog);
                    dialog.setTitle("Daily Package");

                    daily_day = (EditText) dialog.findViewById(R.id.daily_day);
                    daily_day.setText(jo.getString("days"));

                    daily_down = (EditText) dialog.findViewById(R.id.daily_down);
                    daily_down.setText(jo.getString("downloads"));

                    btn_up_daily = (Button) dialog.findViewById(R.id.btn_up_daily);
                    btn_up_daily.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(daily_day.getText().toString().trim().equals("")){
                                daily_days = 0;
                            }else{
                                daily_days = Integer.parseInt(daily_day.getText().toString());
                            }

                            if(daily_down.getText().toString().trim().equals("")){
                                daily_downloads = 0;
                            }else{
                                daily_downloads =Integer.parseInt(daily_down.getText().toString());
                            }


                            if(daily_days == 0 || daily_downloads == 0) {
                                Toast.makeText(getActivity(), "Please check details you have entered.", Toast.LENGTH_LONG).show();
                            }else{
                                type = DAILY_UPDATE;
                                st = new SyncThread(pksf);
                                st.execute();
                                dialog.dismiss();
                            }
                        }
                    });

                    btn_up_daily_cancel = (Button) dialog.findViewById(R.id.btn_up_daily_cancel);
                    btn_up_daily_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            }else if(type.equals(UNLIMIT_LOAD_ALL)){
                if(ja.length()>0) {

                    jo = ja.getJSONObject(0);



                    final Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.dailypackage_dialog);
                    dialog.setTitle("Unlimited Package");

                    daily_day = (EditText) dialog.findViewById(R.id.daily_day);
                    daily_day.setText(jo.getString("days"));

                    daily_down = (EditText) dialog.findViewById(R.id.daily_down);
                    daily_down.setText(jo.getString("downloads"));

                    btn_up_daily = (Button) dialog.findViewById(R.id.btn_up_daily);
                    btn_up_daily.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(daily_day.getText().toString().trim().equals("")){
                                unlimit_days = 0;
                            }else{
                                unlimit_days = Integer.parseInt(daily_day.getText().toString());
                            }

                            if(daily_down.getText().toString().trim().equals("")){
                                unlimit_downloads = 0;
                            }else{
                                unlimit_downloads =Integer.parseInt(daily_down.getText().toString());
                            }


                            if(unlimit_days == 0 || unlimit_downloads == 0) {
                                Toast.makeText(getActivity(), "Please check details you have entered.", Toast.LENGTH_LONG).show();
                            }else{
                                type = UNLIMIT_UPDATE;
                                st = new SyncThread(pksf);
                                st.execute();
                                dialog.dismiss();
                            }
                        }
                    });

                    btn_up_daily_cancel = (Button) dialog.findViewById(R.id.btn_up_daily_cancel);
                    btn_up_daily_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            }else if(type.equals(ADMIN_SPLIT_PERCENT_LOAD_ALL)){
                if(ja.length()>0) {

                    jo = ja.getJSONObject(0);



                    final Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.adminsplit_dialog);
                    dialog.setTitle("Split Percentage");


                    split_title =(TextView)dialog.findViewById(R.id.admin_title);
                    split_title.setText(getActivity().getResources().getString(R.string.admin_split_percent));

                    adsplit_percent = (EditText) dialog.findViewById(R.id.adsplit_percent);
                    adsplit_percent.setHint("Percentage");
                    adsplit_percent.setText(jo.getString("percent"));

                    btn_up_adsplit = (Button) dialog.findViewById(R.id.btn_up_adsplit);
                    btn_up_adsplit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            if (adsplit_percent.getText().toString().trim().equals("")) {
                                adsplit_percents = 0;
                            } else {
                                adsplit_percents = Integer.parseInt(adsplit_percent.getText().toString());
                            }


                            if (adsplit_percents == 0) {
                                Toast.makeText(getActivity(), "Please check details you have entered.", Toast.LENGTH_LONG).show();
                            } else {
                                type = ADMIN_SPLIT_UPDATE;
                                Log.d("--MSG--",adsplit_percents+"");
                                st = new SyncThread(pksf);
                                st.execute();
                                dialog.dismiss();
                            }
                        }
                    });

                    btn_up_adsplit_cancel = (Button) dialog.findViewById(R.id.btn_up_adsplit_cancel);
                    btn_up_adsplit_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            }else if(type.equals(ADMIN_SPLIT_MIN_LOAD_ALL)){
                if(ja.length()>0) {

                    jo = ja.getJSONObject(0);



                    final Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.adminsplit_dialog);
                    dialog.setTitle("Min Earnings");


                    split_title =(TextView)dialog.findViewById(R.id.admin_title);
                    split_title.setText(getActivity().getResources().getString(R.string.admin_min_earning));

                    adsplit_min = (EditText) dialog.findViewById(R.id.adsplit_percent);
                    adsplit_min.setHint("Min Earning");
                    adsplit_min.setText(jo.getString("min"));

                    btn_up_adsplit = (Button) dialog.findViewById(R.id.btn_up_adsplit);
                    btn_up_adsplit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            if (adsplit_min.getText().toString().trim().equals("")) {
                                adsplit_mins = 0.0;
                            } else {
                                adsplit_mins = Double.parseDouble(adsplit_min.getText().toString());
                            }


                            if (adsplit_mins == 0) {
                                Toast.makeText(getActivity(), "Please check details you have entered.", Toast.LENGTH_LONG).show();
                            } else {
                                type = ADMIN_MIN_UPDATE;
                                st = new SyncThread(pksf);
                                st.execute();
                                dialog.dismiss();
                            }
                        }
                    });

                    btn_up_adsplit_cancel = (Button) dialog.findViewById(R.id.btn_up_adsplit_cancel);
                    btn_up_adsplit_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void stringResponse(String s) {
        if(s.equals("Daily_Update_Successful...")){
            Toast.makeText(getActivity(), "Daily free package updated.", Toast.LENGTH_LONG).show();
        }else if(s.equals("Unlimit_Update_Successful...")){
            Toast.makeText(getActivity(), "Unlimited free package updated.", Toast.LENGTH_LONG).show();
        }else if(s.equals("Percentage_Update")){
            Toast.makeText(getActivity(), "Percentage Updated.", Toast.LENGTH_LONG).show();
        }else if(s.equals("Earning_Update")){
            Toast.makeText(getActivity(), "Earning Update.", Toast.LENGTH_LONG).show();
        }else if(s.equals("Add_Download")){
            Toast.makeText(getActivity(), "Added Successfully.", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
        }
    }
}

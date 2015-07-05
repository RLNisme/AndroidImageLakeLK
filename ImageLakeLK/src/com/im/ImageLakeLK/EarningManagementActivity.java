package com.im.ImageLakeLK;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.im.adapter.EarningManagementAdapter;
import com.im.dialogbox.EarningsettleDialog;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by RLN on 4/24/2015.
 */
public class EarningManagementActivity extends Activity implements AdapterView.OnItemClickListener,View.OnClickListener{

    Intent i;
    EarningManagementActivity emaa;
    EarningManagementAdapter ema;
    public ArrayList<String> earn_li;//lightbox list
    public int user_id;
    public String type;
    public ListView earning_list;
    Button refresh;
    ProgressBar pl;
    public ImageButton sort;
    public LinearLayout earning_content,earning_msg_content,earning_loading_content;
    public TextView ern_msg;
    public int requestType;

    public EditText ens_uid,ens_date;
    public CheckBox ens_cr,ens_nr;
    Button ens_cancel,ens_sort;
    public String uid="";
    public String date ="";
    public boolean nr,cr;

    public int idd;
    public int ruid;
    public double amount;

    DateFormat df = DateFormat.getDateInstance();
    Calendar cl = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earning_management_layout);

        emaa = this;

        type = "new";

        i=getIntent();
        user_id = i.getIntExtra("id",-1);

        earning_content =(LinearLayout)findViewById(R.id.earning_list_content);
        earning_content.setVisibility(View.GONE);
        earning_list =(ListView)findViewById(R.id.earning_list);

        earning_msg_content =(LinearLayout)findViewById(R.id.earning_msg_content);
        earning_msg_content.setVisibility(View.GONE);

        earning_loading_content =(LinearLayout)findViewById(R.id.earnings_load_content);
        earning_loading_content.setVisibility(View.GONE);

        ern_msg =(TextView)findViewById(R.id.earning_msg);

        sort =(ImageButton)findViewById(R.id.earning_sort);
        sort.setOnClickListener(this);

        //earn_li = new ArrayList<String>();
        refresh =(Button)findViewById(R.id.earning_btn_refresh);
        refresh.setOnClickListener(this);

        loadEarningsDetails();
    }

    public void loadEarningsDetails(){
        requestType = 0;
        earn_li = new ArrayList<String>();
        ema = new EarningManagementAdapter(this,user_id,type,emaa);
        earning_list.setAdapter(ema);
        earning_list.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String index =earn_li.get(position);
        Log.d("--MSG--","Index :"+index);
        String n_index[] = index.split("-");
        Log.d("--MSG--","N_Index :"+n_index[0]);
        Log.d("--MSG--","N_Index :"+n_index[1]);
        Log.d("--MSG--","N_Index :"+n_index[6]);
        if(Integer.parseInt(n_index[7])== 1) {
            idd = Integer.parseInt(n_index[0]);
            ruid = Integer.parseInt(n_index[1]);
            amount = Double.parseDouble(n_index[6]);
            FragmentManager fm = getFragmentManager();
            DialogFragment newFragment = new EarningsettleDialog();
            newFragment.show(fm, "EarningSettle");
        }else if(Integer.parseInt(n_index[7])== 2){
            Context context = this;
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.earning_settled_dialog);
            dialog.setTitle("Message");
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
        }else if(Integer.parseInt(n_index[7])== 3){
            Context context = this;
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.earning_canceled_dialog);
            dialog.setTitle("Message");
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
        }

    }

    public void setDate(){
        new DatePickerDialog(this,d,cl.get(Calendar.YEAR),cl.get(Calendar.MONTH),cl.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            cl.set(Calendar.YEAR,year);
            cl.set(Calendar.MONTH,monthOfYear);
            cl.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            updateDate();
        }
    };

    public void errorMsg(String s) {
        earning_content.setVisibility(View.GONE);
        earning_msg_content.setVisibility(View.VISIBLE);
        earning_loading_content.setVisibility(View.GONE);
        ern_msg.setText("No item found.");
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    public void msgResponse(String s) {
        earning_content.setVisibility(View.GONE);
        earning_msg_content.setVisibility(View.VISIBLE);
        earning_loading_content.setVisibility(View.GONE);
        ern_msg.setText(s);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.earning_sort){

            Context context = this;
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.earningsort_dialog);
            dialog.setTitle("Sort By");

            ens_uid =(EditText)dialog.findViewById(R.id.ens_uid);
            ens_uid.setText(uid);

            ens_date =(EditText)dialog.findViewById(R.id.ens_date);
            ens_date.setText(date);
            ens_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setDate();
                }
            });
            ens_cr =(CheckBox)dialog.findViewById(R.id.ens_cr);
            if(nr == true){
                ens_nr.setChecked(true);
            }
            if(cr == true){
                ens_cr.setChecked(true);
            }
            ens_nr =(CheckBox)dialog.findViewById(R.id.ens_nr);


            ens_cancel =(Button)dialog.findViewById(R.id.ens_cancel);
            ens_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            ens_sort =(Button)dialog.findViewById(R.id.ens_sort);
            ens_sort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        uid = ens_uid.getText().toString();
                        date = ens_date.getText().toString();
                        if(ens_nr.isChecked()){
                            nr = true;
                        }else{
                            nr = false;
                        }
                        if(ens_cr.isChecked()){
                            cr = true;
                        }else{
                            cr = false;
                        }
                    sort();
                    dialog.dismiss();
                }
            });


            dialog.show();

        }else if(v.getId() == R.id.earning_btn_refresh){
            if(type.equals("new")) {
                loadEarningsDetails();
            }else if(type.equals("sort")){
                sort();
            }

        }
    }


    public void sort(){
        requestType = 0;
        type ="sort";
        earn_li = new ArrayList<String>();
        ema = new EarningManagementAdapter(this,user_id,type,emaa,uid,date,nr,cr);
        earning_list.setAdapter(ema);
        earning_list.setOnItemClickListener(this);
    }

    public void updateDate(){
        date = df.format(cl.getTime());
        String day[] = date.split(", ");
        String nano_day[] = day[0].split(" ");
        Log.d("--MSG--",date);
        Log.d("--MSG--",day[0]);
        Log.d("--MSG--",day[1]);

        Log.d("--MSG--",nano_day[0]);
        Log.d("--MSG--",nano_day[1]);

        if(nano_day[0].equals("Jan")){
            nano_day[0] = "01";
        }else if(nano_day[0].equals("Feb")){
            nano_day[0] = "02";
        }else if(nano_day[0].equals("Mar")){
            nano_day[0] = "03";
        }else if(nano_day[0].equals("Apr")){
            nano_day[0] = "04";
        }else if(nano_day[0].equals("May")){
            nano_day[0] = "05";
        }else if(nano_day[0].equals("Jun")){
            nano_day[0] = "06";
        }else if(nano_day[0].equals("Jul")){
            nano_day[0] = "07";
        }else if(nano_day[0].equals("Aug")){
            nano_day[0] = "08";
        }else if(nano_day[0].equals("Sep")){
            nano_day[0] = "09";
        }else if(nano_day[0].equals("Oct")){
            nano_day[0] = "10";
        }else if(nano_day[0].equals("Nov")){
            nano_day[0] = "11";
        }else if(nano_day[0].equals("Dec")){
            nano_day[0] = "12";
        }
        date = nano_day[1]+"-"+nano_day[0]+"-"+day[1];
        ens_date.setText(date);
    }
}

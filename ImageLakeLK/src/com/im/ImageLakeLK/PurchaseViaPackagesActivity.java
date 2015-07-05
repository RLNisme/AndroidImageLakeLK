package com.im.ImageLakeLK;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.im.adapter.PurchaseViaPackagesAdapter;
import com.im.adapter.UserManagementAdapter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by RLN on 4/29/2015.
 */
public class PurchaseViaPackagesActivity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener,AdapterView.OnItemSelectedListener{

    PurchaseViaPackagesActivity pvpa;
    PurchaseViaPackagesAdapter pvpaa;
    public LinearLayout viapac_content,viapac_msg_content,viapac_load_content;
    ListView viapur_list;
    Button viapac_btn_refresh;
    ImageButton viapac_sort;
    ProgressBar pb;
    TextView viapur_msg;
    public ArrayList<String> iv_li ;//lightbox list
    public int user_id;
    public String type;
    public int request_type;
    EditText pur_date,exp_date,pac_uid;
    Spinner packages;
    String[] pack={"Select a Package Type","Subscription","Credit"};

    Button viapacs_cancel,viapacs_sort;
    Date org_purchaseDate,org_expireDate;
    public String uid="";
    public String date_pur ="";
    public String date_exp ="";
    public int package_id = 0;
    int pkID = 0;
    ArrayAdapter<String> adapter;

    DateFormat df = DateFormat.getDateInstance();
    Calendar cl = Calendar.getInstance();

    DatePickerDialog dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchaseviapackage_layout);

        pvpa = this;

        viapac_content =(LinearLayout)findViewById(R.id.viapac_content);
        viapac_content.setVisibility(View.GONE);

        viapur_list =(ListView)findViewById(R.id.viapac_list);

        viapac_msg_content =(LinearLayout)findViewById(R.id.viapac_msg_content);
        viapac_msg_content.setVisibility(View.GONE);

        viapac_load_content =(LinearLayout)findViewById(R.id.viapac_load_content);
        viapac_load_content.setVisibility(View.GONE);

        viapac_btn_refresh =(Button)findViewById(R.id.viapac_btn_refresh);
        viapac_btn_refresh.setOnClickListener(this);

        viapac_sort =(ImageButton)findViewById(R.id.viapac_sort);
        viapac_sort.setOnClickListener(this);

        viapur_msg =(TextView)findViewById(R.id.viapac_msg);

        pb =(ProgressBar)findViewById(R.id.viapac_loading);

        loadPurchaseData();

    }


    public void loadPurchaseData(){
        iv_li = new ArrayList<String>();
        type ="all";
        viapac_content.setVisibility(View.GONE);
        viapac_msg_content.setVisibility(View.GONE);
        viapac_load_content.setVisibility(View.VISIBLE);

        pvpaa = new PurchaseViaPackagesAdapter(this,user_id,type,pvpa);
        viapur_list.setAdapter(pvpaa);
        viapur_list.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.viapac_btn_refresh){
            loadPurchaseData();
        }else if(v.getId() == R.id.viapac_sort){

            Context context = this;
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.purchasemanagement_sort_dialog);
            dialog.setTitle("Sort by");

            pur_date =(EditText)dialog.findViewById(R.id.viapac_pucdate);
            //pur_date.setText(date_pur);
            pur_date.setFocusable(false);
            pur_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setPurchaseDate();
                }
            });

            exp_date =(EditText)dialog.findViewById(R.id.viapac_expdate);
            //exp_date.setText(date_exp);
            exp_date.setFocusable(false);
            exp_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setExpireDate();
                }
            });

            pac_uid =(EditText)dialog.findViewById(R.id.viapac_uid);
            pac_uid.setText(uid);


            packages =(Spinner)dialog.findViewById(R.id.viapac_packages);
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,pack);
            packages.setAdapter(adapter);
            packages.setSelection(pkID);
            packages.setOnItemSelectedListener(this);

            viapacs_cancel =(Button)dialog.findViewById(R.id.viapacs_cancel);
            viapacs_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            viapacs_sort =(Button)dialog.findViewById(R.id.viapacs_sort);
            viapacs_sort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uid = pac_uid.getText().toString().trim();

                    sort();
                    dialog.dismiss();
                }
            });
            dialog.show();

        }
    }

    private void setPurchaseDate() {
        dp = new DatePickerDialog(this,d,cl.get(Calendar.YEAR),cl.get(Calendar.MONTH),cl.get(Calendar.DAY_OF_MONTH));

        dp.show();

    }

    private void setExpireDate(){
        dp = new DatePickerDialog(this,dd,cl.get(Calendar.YEAR),cl.get(Calendar.MONTH),cl.get(Calendar.DAY_OF_MONTH));
        if(org_purchaseDate != null) {

            dp.getDatePicker().setMinDate(org_purchaseDate.getTime());
        }
        dp.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


    }

    public void stringResponse(String s) {
        if(s.equals("Successful...")){
            //sort();
        }else{
            viapac_content.setVisibility(View.GONE);
            viapac_msg_content.setVisibility(View.VISIBLE);
            viapac_load_content.setVisibility(View.GONE);
            viapur_msg.setText("No item found.");
            Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        }
    }

    public void stringErrorMsg(String s) {
        viapac_content.setVisibility(View.GONE);
        viapac_msg_content.setVisibility(View.VISIBLE);
        viapac_load_content.setVisibility(View.GONE);
        viapur_msg.setText("No item found.");
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d("--MSG--",position+"");
        if(position == 1){
            package_id = 3;
            pkID = 1;
        }else if(position == 2){
            package_id = 4;
            pkID = 2;
        }else if(position == 0){
            package_id = 0;
            pkID = 0;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




    private void updatePurchaseDate() {

        date_pur = df.format(cl.getTime());
        org_purchaseDate = cl.getTime();
        String day[] = date_pur.split(", ");
        String nano_day[] = day[0].split(" ");
        Log.d("--MSG--", date_pur);
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
        date_pur = nano_day[1]+"-"+nano_day[0]+"-"+day[1];
        pur_date.setText(date_pur);

    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            cl.set(Calendar.YEAR,year);
            cl.set(Calendar.MONTH,monthOfYear);
            cl.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            updatePurchaseDate();

        }
    };

    DatePickerDialog.OnDateSetListener dd = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            cl.set(Calendar.YEAR,year);
            cl.set(Calendar.MONTH,monthOfYear);
            cl.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            updateExpireDate();

        }
    };

    private void updateExpireDate() {
        date_exp = df.format(cl.getTime());
        org_expireDate = cl.getTime();
        String day[] = date_exp.split(", ");
        String nano_day[] = day[0].split(" ");
        Log.d("--MSG--", date_exp);
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
        date_exp = nano_day[1]+"-"+nano_day[0]+"-"+day[1];
        exp_date.setText(date_exp);

    }



    private void sort() {
        request_type = 0;
        type ="sort";
        int count = 0;
        if(org_purchaseDate != null && org_expireDate != null) {
            count = org_expireDate.compareTo(org_purchaseDate);
            if(count == 0 || count>0){
                iv_li = new ArrayList<String>();
                pvpaa = new PurchaseViaPackagesAdapter(this,user_id,type,pvpa,uid,date_pur,date_exp,package_id);
                viapur_list.setAdapter(pvpaa);
                viapur_list.setOnItemClickListener(this);
            }else{
                Toast.makeText(this,"Select a valid date period.",Toast.LENGTH_LONG).show();
            }
        }else if(org_purchaseDate == null && org_expireDate == null){
            iv_li = new ArrayList<String>();
            pvpaa = new PurchaseViaPackagesAdapter(this,user_id,type,pvpa,uid,date_pur,date_exp,package_id);
            viapur_list.setAdapter(pvpaa);
            viapur_list.setOnItemClickListener(this);
        }
        else if(org_purchaseDate == null ){
            if(org_expireDate != null){
                Toast.makeText(this,"Select a 'from' date.",Toast.LENGTH_LONG).show();
            }
        }else if(org_purchaseDate != null){
            if(org_expireDate == null){
                Toast.makeText(this,"Select a 'to' date.",Toast.LENGTH_LONG).show();
            }
        }


    }


}

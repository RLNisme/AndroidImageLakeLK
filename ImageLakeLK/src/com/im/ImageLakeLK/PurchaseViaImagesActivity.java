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

import com.im.adapter.PurchaseViaImagesAdapter;
import com.im.adapter.PurchaseViaPackagesAdapter;
import com.im.sync.SyncThread;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by RLN on 4/29/2015.
 */
public class PurchaseViaImagesActivity extends Activity implements AdapterView.OnItemClickListener,View.OnClickListener{

    PurchaseViaImagesActivity pvia;
    PurchaseViaImagesAdapter pviaa;
    public LinearLayout viaimg_content,viaimg_msg_content,viaimg_load_content;
    ListView viapur_list;
    Button viaimg_btn_refresh;
    ImageButton viaimg_sort;
    ProgressBar pb;
    TextView viaimg_msg;


    public ArrayList<String> iv_li ;//lightbox list
    public ArrayList<String> categories = new ArrayList<String>();
    public ArrayList<String> categories_show = new ArrayList<String>();

    public ArrayList<String> buyers = new ArrayList<String>();
    public ArrayList<String> buyers_show = new ArrayList<String>();

    public ArrayList<String> sellers = new ArrayList<String>();
    public ArrayList<String> sellers_show = new ArrayList<String>();

    public int user_id;
    public String type;
    public int request_type;



    Spinner cat_spin,buy_spin,sel_spin;
    Button viaimgs_cancel,viaimgs_sort;
    Date org_purchaseDate,org_expireDate;
    EditText pur_date,exp_date,pac_uid;
    public String uid="";
    public String date_pur ="";
    public String date_exp ="";
    public int cat_id = 0;
    int ca=0,b=0,s=0;
    public int buy_id = 0;
    public int sel_id = 0;
    ArrayAdapter<String> adapter;

    DateFormat df = DateFormat.getDateInstance();
    Calendar cl = Calendar.getInstance();

    DatePickerDialog dp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchaseviaimages_layout);

        pvia = this;

        viaimg_content =(LinearLayout)findViewById(R.id.viaimg_content);
        viaimg_content.setVisibility(View.GONE);

        viapur_list =(ListView)findViewById(R.id.viaimg_list);

        viaimg_msg_content =(LinearLayout)findViewById(R.id.viaimg_msg_content);
        viaimg_msg_content.setVisibility(View.GONE);

        viaimg_load_content =(LinearLayout)findViewById(R.id.viaimg_load_content);
        viaimg_load_content.setVisibility(View.GONE);

        viaimg_btn_refresh =(Button)findViewById(R.id.viaimg_btn_refresh);
        viaimg_btn_refresh.setOnClickListener(this);

        viaimg_sort =(ImageButton)findViewById(R.id.viaimg_sort);
        viaimg_sort.setOnClickListener(this);

        viaimg_msg =(TextView)findViewById(R.id.viaimg_msg);

        pb =(ProgressBar)findViewById(R.id.viapac_loading);
        loadPurchaseData();
        loadSortComponents();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.viaimg_btn_refresh){
            loadPurchaseData();
        }else if(v.getId() == R.id.viaimg_sort){

            Context context = this;
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.purchasemanagementvia_image_sort_dialog);
            dialog.setTitle("Sort by");

            pur_date =(EditText)dialog.findViewById(R.id.viaimg_pucdate);
            //pur_date.setText(date_pur);
            pur_date.setFocusable(false);
            pur_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setPurchaseDate();
                }
            });

            exp_date =(EditText)dialog.findViewById(R.id.viaimg_expdate);
            //exp_date.setText(date_exp);
            exp_date.setFocusable(false);
            exp_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setExpireDate();
                }
            });

            pac_uid =(EditText)dialog.findViewById(R.id.viaimg_uid);
            pac_uid.setText(uid);


            cat_spin =(Spinner)dialog.findViewById(R.id.viaimg_cat);
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categories_show);
            cat_spin.setAdapter(adapter);
            cat_spin.setSelection(ca);
            cat_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ca = position;
                    String c = categories.get(position);
                    String[] cc = c.split("/");
                    Log.d("--MSG--",cc[0]);
                    cat_id = Integer.parseInt(cc[0]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            buy_spin =(Spinner)dialog.findViewById(R.id.viaimg_buy);
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,buyers_show);
            buy_spin.setAdapter(adapter);
            buy_spin.setSelection(b);
            buy_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    b = position;
                    String c = buyers.get(position);
                    String[] cc = c.split("/");
                    Log.d("--MSG--",cc[0]);
                    buy_id = Integer.parseInt(cc[0]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            sel_spin =(Spinner)dialog.findViewById(R.id.viaimg_sel);
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,sellers_show);
            sel_spin.setAdapter(adapter);
            sel_spin.setSelection(s);
            sel_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    s = position;
                    String c = sellers.get(position);
                    String[] cc = c.split("/");
                    Log.d("--MSG--",cc[0]);
                    sel_id = Integer.parseInt(cc[0]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            viaimgs_cancel =(Button)dialog.findViewById(R.id.viaimgs_cancel);
            viaimgs_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            viaimgs_sort =(Button)dialog.findViewById(R.id.viaimgs_sort);
            viaimgs_sort.setOnClickListener(new View.OnClickListener() {
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

    public void loadPurchaseData(){
        iv_li = new ArrayList<String>();
        type ="all";
        viaimg_content.setVisibility(View.GONE);
        viaimg_msg_content.setVisibility(View.GONE);
        viaimg_load_content.setVisibility(View.VISIBLE);

        pviaa = new PurchaseViaImagesAdapter(this,user_id,type,pvia);
        viapur_list.setAdapter(pviaa);
        viapur_list.setOnItemClickListener(this);

    }








    public void stringResponse(String s) {
        if(s.equals("Successful...")){
            //sort();
        }else{
            viaimg_content.setVisibility(View.GONE);
            viaimg_msg_content.setVisibility(View.VISIBLE);
            viaimg_load_content.setVisibility(View.GONE);
            viaimg_msg.setText("No item found.");
            Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        }
    }

    public void stringErrorMsg(String s) {
        viaimg_content.setVisibility(View.GONE);
        viaimg_msg_content.setVisibility(View.VISIBLE);
        viaimg_load_content.setVisibility(View.GONE);
        viaimg_msg.setText("No item found.");
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
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
                pviaa = new PurchaseViaImagesAdapter(this,user_id,type,pvia,uid,date_pur,date_exp,cat_id,buy_id,sel_id);
                viapur_list.setAdapter(pviaa);
                viapur_list.setOnItemClickListener(this);
            }else{
                Toast.makeText(this,"Select a valid date period.",Toast.LENGTH_LONG).show();
            }
        }else if(org_purchaseDate == null && org_expireDate == null){
            iv_li = new ArrayList<String>();
            pviaa = new PurchaseViaImagesAdapter(this,user_id,type,pvia,uid,date_pur,date_exp,cat_id,buy_id,sel_id);
            viapur_list.setAdapter(pviaa);
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



        Log.d("--MSG--",type);
    }

    public void loadSortComponents(){
        type = "all_sort";
        SyncThread st = new SyncThread(pvia);
        st.execute();
    }

    public void JSONResponse(String s) {

        try {
            JSONArray ar = new JSONArray(s);
            JSONArray catArr,buyArr,selArr;
            JSONObject jo;


            if(ar.length()>0) {
                for (int i = 0; i < ar.length(); i++) {
                    if(i == 0) {
                        catArr = ar.getJSONArray(0);
                        String box_row;
                        categories.add("0/Select a Category");
                        categories_show.add("Select a Category");
                        for(int j = 0;j<catArr.length();j++){
                            jo = catArr.getJSONObject(j);
                            box_row = jo.getString("id") //0
                                    + "/" + jo.getString("category");//1


                            categories.add(box_row);
                            categories_show.add(jo.getString("category"));
                            Log.d("--Category--", box_row);
                        }
                    }else if(i == 1){
                        buyArr = ar.getJSONArray(1);
                        String box_row;
                        buyers.add("0/Select a Buyer");
                        buyers_show.add("Select a Buyer");
                        for(int j = 0;j<buyArr.length();j++){
                            jo = buyArr.getJSONObject(j);
                            box_row = jo.getString("bid") //0
                                    + "/" + jo.getString("bun");//1


                            buyers.add(box_row);
                            buyers_show.add(jo.getString("bun"));
                            Log.d("--Buyer--", box_row);
                        }
                    }else if(i == 2){
                        selArr = ar.getJSONArray(2);
                        String box_row;
                        sellers.add("0/Select a Seller");
                        sellers_show.add("Select a Seller");
                        for(int j = 0;j<selArr.length();j++){
                            jo = selArr.getJSONObject(j);
                            box_row = jo.getString("sid") //0
                                    + "/" + jo.getString("sun");//1


                            sellers.add(box_row);
                            sellers_show.add(jo.getString("sun"));
                            Log.d("--Seller--", box_row);
                        }
                    }

                }




            }else{

                stringResponse("No item found.");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

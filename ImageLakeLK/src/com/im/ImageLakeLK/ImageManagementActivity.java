package com.im.ImageLakeLK;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.im.adapter.ImageManagementAdapter;
import com.im.adapter.PurchaseViaImagesAdapter;
import com.im.adapter.UploadManagementAdapter;
import com.im.sync.SyncThread;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by RLN on 5/4/2015.
 */
public class ImageManagementActivity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener{

    ImageManagementActivity ima;
    ImageManagementAdapter imaa;
    Intent i;
    public LinearLayout im_list_content,im_msg_content,im_loading_content;
    TextView msg;
    Button refresh;
    ImageButton sort_btn;
    public ListView lv;
    public int user_id;
    public ArrayList<String> uploads_li = new ArrayList<String>();//lightbox list

    public ArrayList<String> categories = new ArrayList<String>();
    public ArrayList<String> categories_show = new ArrayList<String>();

    public ArrayList<String> sellers = new ArrayList<String>();
    public ArrayList<String> sellers_show = new ArrayList<String>();

    public int request;
    public String type ="";


    Spinner cat_spin,sel_spin;
    Button im_cancel,im_sort;
    Date org_purchaseDate,org_expireDate;
    EditText pur_date,exp_date,key_word;
    public String uid="";
    public String date_pur ="";
    public String date_exp ="";
    public String key ="";
    public int cat_id = 0;
    int ca=0,b=0,s=0;
    public int sel_id = 0;
    ArrayAdapter<String> adapter;

    DateFormat df = DateFormat.getDateInstance();
    Calendar cl = Calendar.getInstance();
    DatePickerDialog dp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_management_layout);

        ima = this;

        i = getIntent();
        user_id = i.getIntExtra("id",-1);

        getActionBar().setHomeButtonEnabled(true);// this is goin to enable the home button
        getActionBar().setDisplayHomeAsUpEnabled(true);// this will show home screen of your app
        getActionBar().setTitle("Image Management");

        im_list_content =(LinearLayout)findViewById(R.id.im_content);
        im_list_content.setVisibility(View.GONE);

        im_msg_content =(LinearLayout)findViewById(R.id.im_msg_content);
        im_msg_content.setVisibility(View.GONE);

        im_loading_content =(LinearLayout)findViewById(R.id.im_load_content);
        im_loading_content.setVisibility(View.GONE);

        lv =(ListView)findViewById(R.id.im_list);

        msg =(TextView)findViewById(R.id.im_msg);

        refresh = (Button)findViewById(R.id.im_btn_refresh);
        refresh.setOnClickListener(this);

        sort_btn =(ImageButton)findViewById(R.id.im_btn_sort);
        sort_btn.setOnClickListener(this);

        request = 0;

        getImagesList();
       loadSortComponents();
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
    public void onClick(View v) {
        if(v.getId() == R.id.im_btn_refresh){
            Log.d("--MSG--", "Clicked");
            getImagesList();
        }if(v.getId() == R.id.im_btn_sort){

            Context context = this;
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.image_management_sort_dialog);
            dialog.setTitle("Sort by");

            pur_date =(EditText)dialog.findViewById(R.id.im_pucdate);
            //pur_date.setText(date_pur);
            date_pur ="";
            pur_date.setFocusable(false);
            pur_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setPurchaseDate();
                }
            });

            exp_date =(EditText)dialog.findViewById(R.id.im_expdate);
            //exp_date.setText(date_exp);
            date_exp ="";
            exp_date.setFocusable(false);
            exp_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setExpireDate();
                }
            });



            cat_spin =(Spinner)dialog.findViewById(R.id.im_cat);
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



            sel_spin =(Spinner)dialog.findViewById(R.id.im_sel);
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

            key_word =(EditText)dialog.findViewById(R.id.im_key);
            key_word.setText(key);

            im_cancel =(Button)dialog.findViewById(R.id.im_cancel);
            im_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                }
            });

            im_sort =(Button)dialog.findViewById(R.id.im_sort);
            im_sort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    key = key_word.getText().toString();

                    sort();
                    dialog.dismiss();
                }
            });
            dialog.show();



        }
    }

    public void getImagesList(){

        im_list_content.setVisibility(View.GONE);
        im_msg_content.setVisibility(View.GONE);

        im_loading_content.setVisibility(View.VISIBLE);
        imaa = new ImageManagementAdapter(this,user_id,ima);
        lv.setAdapter(imaa);
        lv.setOnItemClickListener(this);
    }

    public void loadSortComponents(){
        type = "all_sort";
        SyncThread st = new SyncThread(ima);
        st.execute();
    }


    private void sort() {
        //request_type = 0;
        type ="sort";

        int count = 0;
        if(org_purchaseDate != null && org_expireDate != null) {
            count = org_expireDate.compareTo(org_purchaseDate);
            if(count == 0 || count>0){
                uploads_li = new ArrayList<String>();
                imaa = new ImageManagementAdapter(this,user_id,type,ima,date_pur,date_exp,cat_id,sel_id,key);
                lv.setAdapter(imaa);
                lv.setOnItemClickListener(this);
            }else{
                Toast.makeText(this,"Select a valid date period.",Toast.LENGTH_LONG).show();
            }
        }else if(org_purchaseDate == null && org_expireDate == null){
            uploads_li = new ArrayList<String>();
            imaa = new ImageManagementAdapter(this,user_id,type,ima,date_pur,date_exp,cat_id,sel_id,key);
            lv.setAdapter(imaa);
            lv.setOnItemClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String k = uploads_li.get(position);
        String kk[] = k.split("%");

        Intent i = new Intent(ImageManagementActivity.this,UploadManagementSingleActivity.class);
        i.putExtra("id",user_id);
        i.putExtra("imgid",Integer.parseInt(kk[0]));
        i.putExtra("req",2);
        i.putExtra("state",Integer.parseInt(kk[6]));
        startActivity(i);
    }

    public void stringErrorMsg(String s) {
        im_list_content.setVisibility(View.GONE);
        im_loading_content.setVisibility(View.GONE);
        im_msg_content.setVisibility(View.VISIBLE);
        msg.setText("No item found.");
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }



    public void stringResponse(String s) {
        im_list_content.setVisibility(View.GONE);
        im_loading_content.setVisibility(View.GONE);
        im_msg_content.setVisibility(View.VISIBLE);
        msg.setText(s);
    }


    public void JSONResponse(String s) {

        try {
            JSONArray ar = new JSONArray(s);
            JSONArray catArr,selArr;
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
                    }/*else if(i == 1){
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
                    }*/else if(i == 2){
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

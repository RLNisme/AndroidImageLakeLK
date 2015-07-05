package com.im.ImageLakeLK;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.im.adapter.CreditPackageAdapter;
import com.im.adapter.CreditSizeAdapter;
import com.im.sync.SyncThread;

import java.util.ArrayList;

/**
 * Created by RLN on 5/14/2015.
 */
public class CreditPackageActivity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private static final String LOAD_ALL ="AllCreditPackages";
    private static final String ADD_CREDPACKAGE ="AddCreditPackage";
    private static final String UPDATE_CREDPACKAGE ="UpdateCreditPackages";

    CreditPackageActivity cpa;
    CreditPackageAdapter cpaa;
    SyncThread st;
    public LinearLayout crdsize_list_content,crdsize_msg_content,crdsize_loading_content;
    Button crdsize_btn_refresh;
    ImageButton crdsize_sort;
    ListView crdsize_list;
    public ProgressBar pb;
    TextView msg;
    public String type ="";
    public ArrayList<String> us_li ;//lightbox list
    ArrayAdapter<String> adapter;

    TextView title1,title2,title3,title4,title5;
    EditText edit1,edit2,edit3,edit4;
    Button btn1,btn2;
    Spinner spin5;
    //--------------
    public int credit =1;
    public double unit =0.70;
    public int month = 1;
    public int disco = 0;

    //---------
    //--------------
    public int upid = 0;
    public int upcredit =1;
    public double upunit =0.0;
    public int upmonth = 1;
    public int updisco = 0;
    public int upstate = 0;
    //---------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creditsize_activity);

        cpa = this;

        getActionBar().setTitle("Credit Packages");

        crdsize_list_content = (LinearLayout)findViewById(R.id.crdsize_list_content);
        crdsize_list_content.setVisibility(View.GONE);

        crdsize_list =(ListView)findViewById(R.id.crdsize_list);

        crdsize_msg_content =(LinearLayout)findViewById(R.id.crdsize_msg_content);
        crdsize_msg_content.setVisibility(View.GONE);

        msg =(TextView)findViewById(R.id.crdsize_msg);
        crdsize_btn_refresh =(Button)findViewById(R.id.crdsize_btn_refresh);

        crdsize_loading_content =(LinearLayout)findViewById(R.id.crdsize_load_content);
        crdsize_loading_content.setVisibility(View.GONE);

        pb =(ProgressBar)findViewById(R.id.crdsize_loading);

        crdsize_sort =(ImageButton)findViewById(R.id.crdsize_sort);
        crdsize_sort.setOnClickListener(this);
        loadCredSizeList();
    }

    public void loadCredSizeList(){
        type = LOAD_ALL;

        us_li = new ArrayList<String>();
        crdsize_list_content.setVisibility(View.GONE);
        crdsize_msg_content.setVisibility(View.GONE);
        crdsize_loading_content.setVisibility(View.VISIBLE);

        cpaa = new CreditPackageAdapter(this,type,cpa) ;
        crdsize_list.setAdapter(cpaa);
        crdsize_list.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.crdsize_sort){
            Context context = this;
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.creditandcreditpack);
            dialog.setTitle("New Credit Package");

            title1 = (TextView)dialog.findViewById(R.id.title1);
            title1.setText("Duration(Month)");

            title2 = (TextView)dialog.findViewById(R.id.title2);
            title2.setText("Credit unit price($)");

            title3 = (TextView)dialog.findViewById(R.id.title3);
            title3.setText("Credit count");

            title4 = (TextView)dialog.findViewById(R.id.title4);
            title4.setText("Discount(%)");

            title5 =(TextView)dialog.findViewById(R.id.title5);
            title5.setVisibility(View.GONE);

            edit1 =(EditText)dialog.findViewById(R.id.edit1);
            edit1.setInputType(0x00000002);
            edit1.setText(month+"");

            edit2 =(EditText)dialog.findViewById(R.id.edit2);
            edit2.setInputType(0x00002002);
            edit2.setText(unit+"");

            edit3 =(EditText)dialog.findViewById(R.id.edit3);
            edit3.setInputType(0x00000002);
            edit3.setText(credit+"");

            edit4 =(EditText)dialog.findViewById(R.id.edit4);
            edit4.setInputType(0x00000002);
            edit4.setText(disco+"");

            spin5 =(Spinner)dialog.findViewById(R.id.spin5);
            spin5.setVisibility(View.GONE);

            btn1 =(Button)dialog.findViewById(R.id.btn1);
            btn1.setText("Cancel");
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            btn2 =(Button)dialog.findViewById(R.id.btn2);
            btn2.setText("Add");
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    if(edit1.getText().toString().trim().equals("") || edit2.getText().toString().trim().equals("") ||
                            edit3.getText().toString().trim().equals("") ||edit4.getText().toString().trim().equals("")){
                        Toast.makeText(CreditPackageActivity.this, "Please check details", Toast.LENGTH_LONG).show();
                    }else{
                        month = Integer.parseInt(edit1.getText().toString());
                        unit =  Double.parseDouble(edit2.getText().toString().trim().toString());
                        credit = Integer.parseInt(edit3.getText().toString());
                        disco = Integer.parseInt(edit4.getText().toString());

                        if(credit <= 0){
                            Toast.makeText(CreditPackageActivity.this,"Credit must be at least 1.",Toast.LENGTH_LONG).show();
                        }else if(unit == 0.0){
                            Toast.makeText(CreditPackageActivity.this,"unit price must be more than $0.00.",Toast.LENGTH_LONG).show();
                        }else if(month <1){
                            Toast.makeText(CreditPackageActivity.this,"Months must be at least 1.",Toast.LENGTH_LONG).show();
                        }else if(disco <0){
                            Toast.makeText(CreditPackageActivity.this,"Discount msut be 0 or more than 0.",Toast.LENGTH_LONG).show();
                        }else{

                            type = ADD_CREDPACKAGE;
                            st = new SyncThread(cpa);
                            st.execute();
                            dialog.dismiss();
                        }
                    }
                }
            });
            dialog.show();
        }
    }

    public void stringErrorMsg(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    public void JSONResponse(String s) {
    }

    public void stringResponse(String s) {
        if(s.equals("Add_Credit_Package")) {
            credit = 1;
            month = 1;
            unit = 0.0;
            disco = 0;
            loadCredSizeList();
            Toast.makeText(this, "Successfully added.", Toast.LENGTH_LONG).show();
        }else if(s.equals("Update_Credit_Package")){
            upcredit = 1;
            upunit =0.0;
            upmonth = 1;
            updisco = 0;
            upstate = 0;
            loadCredSizeList();
            Toast.makeText(this, "Successfully updated.", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String g = us_li.get(position);
        String gg[]=g.split("/");

        upid = Integer.parseInt(gg[0]);
        upcredit = Integer.parseInt(gg[1]);
        upunit = Double.parseDouble(gg[3]);
        upmonth = Integer.parseInt(gg[2]);
        updisco = Integer.parseInt(gg[4]);
        upstate = Integer.parseInt(gg[5]);

        Context context = this;
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.creditandcreditpack);
        dialog.setTitle("Update credit package");

        title1 = (TextView)dialog.findViewById(R.id.title1);
        title1.setText("Duration(Month)");

        title2 = (TextView)dialog.findViewById(R.id.title2);
        title2.setText("Credit unit price($)");

        title3 = (TextView)dialog.findViewById(R.id.title3);
        title3.setText("Credit count");

        title4 = (TextView)dialog.findViewById(R.id.title4);
        title4.setText("Discount(%)");

        title5 =(TextView)dialog.findViewById(R.id.title5);
        title5.setVisibility(View.VISIBLE);
        title5.setText("State");


        edit1 =(EditText)dialog.findViewById(R.id.edit1);
        edit1.setInputType(0x00000002);
        edit1.setText(upmonth+"");

        edit2 =(EditText)dialog.findViewById(R.id.edit2);
        edit2.setInputType(0x00002002);
        edit2.setText(upunit+"");

        edit3 =(EditText)dialog.findViewById(R.id.edit3);
        edit3.setInputType(0x00000002);
        edit3.setText(upcredit+"");

        edit4 =(EditText)dialog.findViewById(R.id.edit4);
        edit4.setInputType(0x00000002);
        edit4.setText(updisco+"");

        spin5 =(Spinner)dialog.findViewById(R.id.spin5);
        spin5.setVisibility(View.VISIBLE);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.state));
        spin5.setAdapter(adapter);
        Log.d("--MSG--",upstate+"");
        if(upstate == 1) {
            spin5.setSelection(0);
        }else if(upstate == 2){
            spin5.setSelection(0);
        }else if(upstate == 3){
            spin5.setSelection(1);
        }
        spin5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0){
                    upstate = 1;
                }else if(position == 1){
                    upstate = 3;
                }
                //Toast.makeText(CreditSizeActivity.this,upstate+"",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn1 =(Button)dialog.findViewById(R.id.btn1);
        btn1.setText("Cancel");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn2 =(Button)dialog.findViewById(R.id.btn2);
        btn2.setText("Add");
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(edit1.getText().toString().trim().equals("") || edit2.getText().toString().trim().equals("") ||
                        edit3.getText().toString().trim().equals("") ||edit4.getText().toString().trim().equals("")){
                    Toast.makeText(CreditPackageActivity.this,"Please check details",Toast.LENGTH_LONG).show();
                }else{
                    upmonth = Integer.parseInt(edit1.getText().toString());
                    upunit =  Double.parseDouble(edit2.getText().toString().trim().toString());
                    upcredit = Integer.parseInt(edit3.getText().toString());
                    updisco = Integer.parseInt(edit4.getText().toString());

                    if(upcredit <= 0){
                        Toast.makeText(CreditPackageActivity.this,"Credit must be at least 1.",Toast.LENGTH_LONG).show();
                    }else if(upunit == 0.0){
                        Toast.makeText(CreditPackageActivity.this,"unit price must be more than $0.00.",Toast.LENGTH_LONG).show();
                    }else if(upmonth <1){
                        Toast.makeText(CreditPackageActivity.this,"Months must be at least 1.",Toast.LENGTH_LONG).show();
                    }else if(updisco <0){
                        Toast.makeText(CreditPackageActivity.this,"Discount msut be 0 or more than 0.",Toast.LENGTH_LONG).show();
                    }else{

                        type = UPDATE_CREDPACKAGE;
                        st = new SyncThread(cpa);
                        st.execute();
                        dialog.dismiss();
                    }
                }
            }
        });
        dialog.show();
    }

}

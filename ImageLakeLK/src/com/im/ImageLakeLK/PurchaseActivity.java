package com.im.ImageLakeLK;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.im.sync.SyncThread;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RLN on 6/5/2015.
 */
public class PurchaseActivity extends Activity implements View.OnClickListener{

    TextView t1,t2;
    EditText fn,ln,ccn,cvv;
    Spinner mon,yer,con;
    Button bpur;

    Intent i;
    SyncThread st;
    PurchaseActivity pac;

    public int pck_id;
    public int uid;
    public int user_type;
    public String pfn,pln,pccn,pcvv,pmon,pyer,pcon,type;
    public int ptd,pdwn;
    public double ptot;


    public List<String> yer_li;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_activity);

        pac = this;

        i = getIntent();
        pck_id = i.getIntExtra("pck_id", -1);
        uid = i.getIntExtra("uid",-1);
        user_type = i.getIntExtra("ut",-1);
        ptd = i.getIntExtra("ptd",-1);
        pdwn = i.getIntExtra("pdwn",-1);
        ptot = i.getDoubleExtra("ptot",-1);
        type = i.getIntExtra("type",-1)+"";

        t1 = (TextView)findViewById(R.id.t1);
        t1.setText(i.getStringExtra("t1"));
        t2 = (TextView)findViewById(R.id.t2);
        t2.setText(i.getStringExtra("t2"));

        Log.d("PackID",pck_id+"");
        Log.d("Uid",uid+"");
        Log.d("User_tupe",user_type+"");
        Log.d("Ptd",ptd+"");
        Log.d("Pdwn",pdwn+"");
        Log.d("ptot",ptot+"");
        Log.d("type",type+"");
        Log.d("t1",i.getStringExtra("t1"));
        Log.d("t2",i.getStringExtra("t2"));

        fn = (EditText)findViewById(R.id.fn);
        ln = (EditText)findViewById(R.id.ln);
        ccn = (EditText)findViewById(R.id.ccn);
        cvv = (EditText)findViewById(R.id.cvv);

        mon =(Spinner)findViewById(R.id.mon);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.months));
        mon.setAdapter(adapter);
        mon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pmon = (position + 1)+"";
                Log.d("Month",pmon);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        yer =(Spinner)findViewById(R.id.yer);
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.yeas));
        yer.setAdapter(adapter2);
        yer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               pyer = adapter2.getItem(position);
                Log.d("Month",pyer);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        con =(Spinner)findViewById(R.id.con);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.countrys));
        con.setAdapter(adapter3);
        con.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pcon = (position + 1)+"";
                Log.d("Month",pcon);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bpur =(Button)findViewById(R.id.bpur);
        bpur.setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.bpur){
            pfn = fn.getText().toString();
            pln = ln.getText().toString();
            pccn = ccn.getText().toString();
            pcvv = cvv.getText().toString();



            st = new SyncThread(pac);
            st.execute();

        }
    }


    public void stringErrorMsg(String s) {
        Toast.makeText(pac,s,Toast.LENGTH_LONG).show();
    }

    public void JSONResponse(String s) {
    }

    public void stringResponse(String s) {
        if(s.equals("Successfuly_added")){
            Toast.makeText(pac, "Successfully Activated.", Toast.LENGTH_LONG).show();
            pac.finish();
            Intent i = new Intent(pac,AccountActivity.class);
            i.putExtra("id",uid);
            i.putExtra("user_type",user_type);
            startActivity(i);
        }else {
            Toast.makeText(pac, s, Toast.LENGTH_LONG).show();
        }
    }
}

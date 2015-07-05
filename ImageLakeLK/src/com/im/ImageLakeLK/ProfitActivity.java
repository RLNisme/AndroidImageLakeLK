package com.im.ImageLakeLK;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.im.sync.SyncThread;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RLN on 4/19/2015.
 */
public class ProfitActivity extends Activity implements View.OnClickListener{

    ProfitActivity pa;
    public final int GET_EARNINGS = 1;
    public final int REQUEST_EARNINGS = 2;
    public final int PAYPAL = 1;
    public final int PAYSA = 2;
    public int requestType;
    Intent i;
    EditText paypal_txt,paysa_txt;
    Button paypal_btn,paysa_btn;
    TextView pen_earn,acc_bal,net_earn;
    public int user_id;
    public int acc_type;
    public String acc_em;
    public List<NameValuePair> li;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profit_activity);

        getActionBar().setHomeButtonEnabled(true);// this is goin to enable the home button
        getActionBar().setDisplayHomeAsUpEnabled(true);// this will show home screen of your app
        getActionBar().setTitle("Profit");

        pa = this;

        i = getIntent();
        user_id = i.getIntExtra("id",-1);

        pen_earn =(TextView)findViewById(R.id.pending);
        acc_bal =(TextView)findViewById(R.id.account);
        net_earn =(TextView)findViewById(R.id.net);

        requestType = 0;
        getEarningDetails();

        paypal_txt =(EditText)findViewById(R.id.em_paypal);
        paypal_btn =(Button)findViewById(R.id.btn_paypal);
        paypal_btn.setOnClickListener(this);

        paysa_txt =(EditText)findViewById(R.id.em_paysa);
        paysa_btn =(Button)findViewById(R.id.btn_paysa);
        paysa_btn.setOnClickListener(this);
    }

    public void getEarningDetails(){
        SyncThread st = new SyncThread(pa);
        st.execute();
    }

    public void stringErrorMsg(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    public void JSONResponse(String s) {

       try {
           JSONObject jo = new JSONObject(s);
           pen_earn.setText("$ "+jo.getString("pe"));
           acc_bal.setText("$ "+jo.getString("ab"));
           net_earn.setText("$ "+jo.getString("ne"));
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    public void stringResponse(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_paypal){
            requestType = 1;
            acc_type = PAYPAL;
            acc_em = paypal_txt.getText().toString();
            addRequestDetails();
            SyncThread st = new SyncThread(pa);
            st.execute();
        }else if(v.getId() == R.id.btn_paysa){
            requestType = 1;
            acc_type = PAYSA;
            acc_em = paysa_txt.getText().toString();
            addRequestDetails();
            SyncThread st = new SyncThread(pa);
            st.execute();
        }
    }

    public void addRequestDetails(){
        li = new ArrayList<NameValuePair>();
        li.add(new BasicNameValuePair("uid",user_id+""));
        li.add(new BasicNameValuePair("acc",acc_type+""));
        li.add(new BasicNameValuePair("accem",acc_em));
    }
}

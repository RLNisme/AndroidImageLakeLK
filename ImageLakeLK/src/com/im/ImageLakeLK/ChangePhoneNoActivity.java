package com.im.ImageLakeLK;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.im.sync.SyncThread;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RLN on 6/8/2015.
 */
public class ChangePhoneNoActivity extends Activity implements View.OnClickListener{

    public static final String LOAD_PHN = "load_phone";
    public static final String UPDATE_PHN = "update_phone";

    Intent i;
    SyncThread st;
    public int user_id;
    public String phn;
    public String type;
    ChangePhoneNoActivity chna;
    EditText chp_phn;
    Button phn_btn;
    public List<NameValuePair> li;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changephone_no);

        chna = this;

        i = getIntent();
        user_id = i.getIntExtra("id",-1);


        chp_phn =(EditText)findViewById(R.id.chp_phn);
        phn_btn =(Button)findViewById(R.id.phn_btn);
        phn_btn.setOnClickListener(this);


        loadPhon();
    }

    private void loadPhon(){
        type = LOAD_PHN;

        li = new ArrayList<NameValuePair>();
        li.add(new BasicNameValuePair("uid",user_id+""));
        li.add(new BasicNameValuePair("type",type));

        st = new SyncThread(chna);
        st.execute();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.phn_btn){
            type = UPDATE_PHN;
            phn = chp_phn.getText().toString();

            if(phn.length() < 0){

                stringErrorMsg("Please enter phone no.");
            }else {

                li = new ArrayList<NameValuePair>();
                li.add(new BasicNameValuePair("uid", user_id + ""));
                li.add(new BasicNameValuePair("type", type));
                li.add(new BasicNameValuePair("phn", phn));
                st = new SyncThread(chna);
                st.execute();
            }
        }
    }


    public void stringErrorMsg(String s) {
        Toast.makeText(chna,s,Toast.LENGTH_LONG).show();
    }

    public void JSONResponse(String s) {

        try {
            JSONObject jo = new JSONObject(s);
            chp_phn.setText(jo.getString("phn"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stringResponse(String s) {

        Toast.makeText(chna,s,Toast.LENGTH_LONG).show();
    }
}

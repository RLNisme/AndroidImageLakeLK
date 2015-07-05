package com.im.ImageLakeLK;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.im.dialogbox.ChangePasswordDialogbox;
import com.im.dialogbox.EditProfileDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RLN on 4/17/2015.
 */
public class ChangePasswordActivity extends Activity implements View.OnClickListener{

    Button chpw_btn;
    EditText currPw,newPw,reyPw;
    Intent i;
    public int user_id;
    public List<NameValuePair> li;
    String curpw = "",pw = "",rtypw = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword_activity);

        i=getIntent();
        user_id = i.getIntExtra("id",-1);

        getActionBar().setTitle("Change Password");
        getActionBar().setHomeButtonEnabled(true);// this is goin to enable the home button
        getActionBar().setDisplayHomeAsUpEnabled(true);// this will show home screen of your app

        chpw_btn =(Button)findViewById(R.id.changing_btn);
        currPw =(EditText)findViewById(R.id.chp_crrp);
        newPw =(EditText)findViewById(R.id.chp_pw);
        reyPw =(EditText)findViewById(R.id.chp_cpw);

        chpw_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.changing_btn){

            curpw = currPw.getText().toString().trim();
            pw  = newPw.getText().toString().trim();
            rtypw = reyPw.getText().toString().trim();

            li = new ArrayList<NameValuePair>();
            li.add(new BasicNameValuePair("uid",user_id+""));
            li.add(new BasicNameValuePair("old-psw",curpw));
            li.add(new BasicNameValuePair("new-psw",pw));
            li.add(new BasicNameValuePair("rty-psw",rtypw));



            FragmentManager fm = getFragmentManager();
            DialogFragment newFragment = new ChangePasswordDialogbox();
            newFragment.show(fm, "ChangePassword");
        }
    }


    public void stringResponse(String s){
        if(s.equals("Successful...")){
            back();
        }else{
            Toast.makeText(this,s,Toast.LENGTH_LONG).show();
        }
    }

    public void back(){
        Intent i = new Intent();
        i.putExtra("pw",pw);
        setResult(Activity.RESULT_OK,i);
        finish();
    }
}

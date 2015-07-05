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

import com.im.dialogbox.EditProfileDialog;
import com.im.dialogbox.LightBoxDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RLN on 4/13/2015.
 */
public class EditActivity extends Activity implements View.OnClickListener{



    Intent i;
    public int user_id;
    public String un;
    public String fn;
    public String ln;

    public String em;
    public EditText ed_un,ed_fn,ed_ln,ed_em;
    Button edit_btn;

    public String e_un="",e_fn="",e_ln="",e_em="";

    public List<NameValuePair> li;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);

        getActionBar().setHomeButtonEnabled(true);// this is goin to enable the home button
        getActionBar().setDisplayHomeAsUpEnabled(true);// this will show home screen of your app
        getActionBar().setTitle("Back");

        i = getIntent();
        user_id = i.getIntExtra("id", -1);
        un = i.getStringExtra("un");
        fn = i.getStringExtra("fn");
        ln = i.getStringExtra("ln");

        em = i.getStringExtra("em");

        ed_un =(EditText)findViewById(R.id.editing_un);
        ed_un.setText(un);

        ed_em =(EditText)findViewById(R.id.editing_em);
        ed_em.setText(em);

        ed_fn =(EditText)findViewById(R.id.editing_fn);
        ed_fn.setText(fn);

        ed_ln =(EditText)findViewById(R.id.editing_ln);
        ed_ln.setText(ln);

        edit_btn =(Button)findViewById(R.id.editing_btn);
        edit_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.editing_btn){

            e_un = ed_un.getText().toString().trim();
            e_fn = ed_fn.getText().toString().trim();
            e_ln = ed_ln.getText().toString().trim();
            e_em = ed_em.getText().toString().trim();

            if(!e_un.equals("") && !e_em.equals("")){

                int email_length = e_em.length();
                int atPose = e_em.indexOf('@');
                int dotePos = e_em.indexOf('.');

                if(atPose<1 || dotePos < atPose+2 || dotePos+2 >=email_length){

                    Toast.makeText(this,"Incorrect email address",Toast.LENGTH_LONG).show();

                }else{

                    li = new ArrayList<NameValuePair>();
                    li.add(new BasicNameValuePair("uid",user_id+""));
                    li.add(new BasicNameValuePair("un",e_un));
                    li.add(new BasicNameValuePair("fn",e_fn));
                    li.add(new BasicNameValuePair("ln",e_ln));
                    li.add(new BasicNameValuePair("em",e_em));

                    FragmentManager fm = getFragmentManager();
                    DialogFragment newFragment = new EditProfileDialog();
                    newFragment.show(fm, "edit");
                }

            }else{

                Toast.makeText(this, "Please check user details", Toast.LENGTH_LONG).show();
            }



        }
    }

    public void stringResponse(String s){
        if(s.equals("Successful...")){
            back();
        }else{
            Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        }
    }


    public void back(){
        Intent i = new Intent();
        i.putExtra("un",e_un);
        i.putExtra("fn",e_fn);
        i.putExtra("ln",e_ln);
        i.putExtra("em",e_em);

        setResult(Activity.RESULT_OK,i);
        finish();
    }

    public void stringErrorMsg(String s){
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }


}

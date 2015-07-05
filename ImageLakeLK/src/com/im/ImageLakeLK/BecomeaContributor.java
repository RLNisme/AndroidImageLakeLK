package com.im.ImageLakeLK;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.im.dialogbox.ContributorDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RLN on 4/17/2015.
 */
public class BecomeaContributor extends Activity implements View.OnClickListener{

    public SQLiteDatabase sqlitDB;
    //user login DB
    public final String DATABASE_TABLE ="signin";
    public final String KEY_ID = "id";
    public final String KEY_USER_ID = "uid";
    public final String KEY_USER_TYPE = "utype";
    public final String KEY_USER_NAME = "un";
    public final String KEY_PASSWORD = "pw";
    public final String KEY_FN ="fn";
    public final String KEY_LN ="ln";
    public final String KEY_EM ="em";
    public final String KEY_STATE = "state";

    Intent i;
    EditText fn,ln;
    Button btn_bec;
    public int user_id;
    public long row_id;
    public List<NameValuePair> li;
    public String firstName = "",lastName = "";
    public int user_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.becomeacontributor);

        getActionBar().setHomeButtonEnabled(true);// this is goin to enable the home button
        getActionBar().setDisplayHomeAsUpEnabled(true);// this will show home screen of your app
        getActionBar().setTitle("Become a Contributor");

        i = getIntent();
        user_id = i.getIntExtra("id",-1);
        row_id = i.getLongExtra("row_id",-1);
        user_type = i.getIntExtra("user_type",-1);

        fn =(EditText)findViewById(R.id.bec_fn);
        ln =(EditText)findViewById(R.id.bec_ln);

        btn_bec =(Button)findViewById(R.id.bec_btn);
        btn_bec.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.bec_btn){
            firstName = fn.getText().toString();
            lastName = ln.getText().toString();

            li = new ArrayList<NameValuePair>();
            li.add(new BasicNameValuePair("uid",user_id+""));
            li.add(new BasicNameValuePair("fn",firstName));
            li.add(new BasicNameValuePair("ln",lastName));

            FragmentManager fm = getFragmentManager();
            DialogFragment df = new ContributorDialog();
            df.show(fm,"Contributor");
        }
    }


    public void stringErrorMsg(String s){
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    public void stringResponse(String s){
        if(s.equals("Successful...")){
            user_type = 2;
            DBOpen();
            updateUserType(DATABASE_TABLE,row_id,user_type,firstName,lastName);
            DBClose();
            Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        }
    }

    public void DBOpen(){
        sqlitDB = openOrCreateDatabase("ImageLKDB",MODE_PRIVATE,null);
    }

    public void DBClose(){
        sqlitDB.close();
    }

    public void updateUserType(String tableName,long row,int user_type,String first_Name,String last_Name){
        ContentValues cv = new ContentValues();
        cv.put(KEY_USER_TYPE,user_type);
        cv.put(KEY_FN,first_Name);
        cv.put(KEY_LN,last_Name);


        sqlitDB.update(tableName,cv,KEY_ID +"="+row,null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(this,AccountActivity.class);
        i.putExtra("id",user_id);
        i.putExtra("user_type",user_type);
        startActivity(i);
        return super.onOptionsItemSelected(item);


    }
}

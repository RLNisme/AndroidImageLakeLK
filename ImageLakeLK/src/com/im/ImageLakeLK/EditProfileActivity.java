package com.im.ImageLakeLK;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.im.dialogbox.EditProfileDialog;
import com.im.dialogbox.LightBoxDialog;

/**
 * Created by RLN on 4/13/2015.
 */
public class EditProfileActivity extends Activity implements View.OnClickListener{

    private final int REUEST_EDIT = 2;
    private final int REUEST_PW = 1;

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
    public long row_id;
    public int user_id;
    public String un;
    public String fn;
    public String ln;
    public String pw;
    public String em;
    public int user_type;

    TextView txt_fn,txt_un,txt_ln,txt_em;
    Button edit_profile,change_pw,change_phn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);

        getActionBar().setHomeButtonEnabled(true);// this is goin to enable the home button
        //getActionBar().setDisplayHomeAsUpEnabled(true);// this will show home screen of your app
        getActionBar().setTitle("Edit Profile");

        i = getIntent();
        row_id = i.getLongExtra("row_id",-1);
        user_id = i.getIntExtra("id", -1);
        un = i.getStringExtra("un");
        fn = i.getStringExtra("fn");
        ln = i.getStringExtra("ln");
        pw = i.getStringExtra("pw");
        em = i.getStringExtra("em");
        user_type = i.getIntExtra("type",-1);

        txt_un =(TextView)findViewById(R.id.edit_un);
        txt_un.setText(un);

        txt_fn =(TextView)findViewById(R.id.edit_fn);
        txt_fn.setText(fn);

        txt_ln =(TextView)findViewById(R.id.edit_ln);
        txt_ln.setText(ln);

        txt_em =(TextView)findViewById(R.id.edit_em);
        txt_em.setText(em);

        edit_profile =(Button)findViewById(R.id.edit_profile_btn);
        edit_profile.setOnClickListener(this);

        change_pw =(Button)findViewById(R.id.change_password_btn);
        change_pw.setOnClickListener(this);

        change_phn =(Button)findViewById(R.id.change_phone_btn);
        change_phn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.edit_profile_btn){
           Intent i = new Intent(this,EditActivity.class);
            i.putExtra("id",user_id);
            i.putExtra("un",un);
            i.putExtra("fn",fn);
            i.putExtra("ln",ln);
            i.putExtra("em",em);
            startActivityForResult(i,REUEST_EDIT);
        }else if(v.getId() == R.id.change_password_btn){

            Intent i = new Intent(this,ChangePasswordActivity.class);
            i.putExtra("id",user_id);

            startActivityForResult(i,REUEST_PW);

        }else if(v.getId() == R.id.change_phone_btn){

            Intent i = new Intent(this,ChangePhoneNoActivity.class);
            i.putExtra("id",user_id);

            startActivityForResult(i,REUEST_PW);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REUEST_EDIT){
            if(resultCode == RESULT_OK){
                txt_un.setText(data.getStringExtra("un"));
                txt_fn.setText(data.getStringExtra("fn"));
                txt_ln.setText(data.getStringExtra("ln"));
                txt_em.setText(data.getStringExtra("em"));

                DBOpen();
                updateUserDetails(DATABASE_TABLE,row_id,data.getStringExtra("un"),data.getStringExtra("fn"),data.getStringExtra("ln"),data.getStringExtra("em"));
                DBClose();
            }
        }else if(requestCode == REUEST_PW){
            if(resultCode == RESULT_OK){
                DBOpen();
                updateUserPassword(DATABASE_TABLE,row_id,data.getStringExtra("pw"));
                DBClose();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    public void DBOpen(){
        sqlitDB = openOrCreateDatabase("ImageLKDB",MODE_PRIVATE,null);
    }

    public void DBClose(){

        sqlitDB.close();
    }

    public void updateUserDetails(String tableName,long row,String un,String fn,String ln,String em){
        ContentValues cv = new ContentValues();
        cv.put(KEY_USER_NAME,un);
        cv.put(KEY_FN,fn);
        cv.put(KEY_LN,ln);
        cv.put(KEY_EM,em);

        sqlitDB.update(tableName,cv,KEY_ID +"="+row,null);
    }

    public void updateUserPassword(String tableName,long row,String pw){
        ContentValues cv = new ContentValues();
        cv.put(KEY_PASSWORD,pw);
        sqlitDB.update(tableName,cv,KEY_ID + "="+row,null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
               // NavUtils.navigateUpFromSameTask(this);
                //this.finish();
                Log.d("--MSG--","WOW");
               // return true;
                Intent i = new Intent(this,AccountActivity.class);
                i.putExtra("id",user_id);
                i.putExtra("user_type",user_type);
                startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}

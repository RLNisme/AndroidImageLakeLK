package com.im.ImageLakeLK;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.im.sync.SyncThread;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RLN on 4/1/2015.
 */
public class SignupActivity extends Activity implements View.OnClickListener{


    EditText un,em,pw,cpw,phn;
    Button btn_signup;
    String user_name = "",email = "",password= "",confirm_password = "",phon = "";

    SignupActivity sa;

    public SQLiteDatabase sqlitDB;
    public final String DATABASE_TABLE = "signup";
    public final String KEY_ID = "id";
    public final String KEY_USER_NAME = "un";
    public final String KEY_EMAIL = "em";
    public final String KEY_PASSWORD = "pw";
    public final String KEY_PHONE = "phn";
    public List<NameValuePair> nameValuePairList;

    public int requestType = 1;  // Method type

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        sa = this;

        un =(EditText)findViewById(R.id.sup_un);
        em =(EditText)findViewById(R.id.sup_em);
        pw =(EditText)findViewById(R.id.sup_pw);
        cpw =(EditText)findViewById(R.id.sup_cpw);
        phn =(EditText)findViewById(R.id.sup_phn);

        btn_signup =(Button)findViewById(R.id.sup_btn_sup);
        btn_signup.setOnClickListener(this);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.sup_btn_sup){

            user_name = un.getText().toString().trim();
            email = em.getText().toString().trim();
            password = pw.getText().toString().trim();
            confirm_password = cpw.getText().toString().trim();
            phon = phn.getText().toString().trim();

            if( !user_name.equals("") && !email.equals("") && !password.equals("") && !confirm_password.equals("") && !phon.equals("")){

               // Toast.makeText(this,"OK",Toast.LENGTH_LONG).show();
                int email_length = email.length();
                int atPose = email.indexOf('@');
                int dotePos = email.indexOf('.');

                if(atPose<1 || dotePos < atPose+2 || dotePos+2 >=email_length){
                    Toast.makeText(this,"Incorrect email address",Toast.LENGTH_LONG).show();
                }else{
                   // Toast.makeText(this,"Correct email address",Toast.LENGTH_LONG).show();
                    if(password.length()>=6) {
                        if (!password.equals(confirm_password)) {
                            Toast.makeText(this, "Password not matching", Toast.LENGTH_LONG).show();
                        } else {
                            if(phon.length() > 0){
                                DBOpen();
                                DBUpgrad(DATABASE_TABLE);
                                createDBTable(DATABASE_TABLE);
                                long i = insertData(user_name,email,password,phon);


                                if(i > 0){
                                    SyncThread st = new SyncThread(sa);
                                    st.execute();
                                }
                            }else{
                                Toast.makeText(this, "Please enter the phone no", Toast.LENGTH_LONG).show();
                            }



                        }
                    }else{
                        Toast.makeText(this, "Password must have at least 6 characters", Toast.LENGTH_LONG).show();
                    }
                }

            }else{
                Toast.makeText(this,"Please check user details",Toast.LENGTH_LONG).show();
            }


        }
    }



    public void serverResultAsString(String result){

        Log.d("--MSG--",result);
        if(result.equals("Successful...")){
            this.finish();
        }else {
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }

    }

    public void DBOpen(){
        sqlitDB = openOrCreateDatabase("ImageLKDB",MODE_PRIVATE,null);
    }

    public void createDBTable(String tableName){
        sqlitDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableName + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USER_NAME + " VARCHAR," + KEY_EMAIL + " VARCHAR," + KEY_PASSWORD + " VARCHAR,"+KEY_PHONE+" VARCHAR);");//create signup table

    }


    public void DBUpgrad(String tableName){
        sqlitDB.execSQL("DROP TABLE IF EXISTS "+tableName);
        createDBTable(tableName);
    }

    public void DBClose(){
        sqlitDB.close();
    }

    public long insertData(String userName,String email,String password,String phon){
        ContentValues cv = new ContentValues();
        cv.put(KEY_USER_NAME,userName);
        cv.put(KEY_EMAIL,email);
        cv.put(KEY_PASSWORD,password);
        cv.put(KEY_PHONE,phon);
        return sqlitDB.insert(DATABASE_TABLE,null,cv);

    }

    public List<NameValuePair> getData(String tableName){
        String[] colums = new String[]{KEY_ID,KEY_USER_NAME,KEY_EMAIL,KEY_PASSWORD,KEY_PHONE};
        Cursor c = sqlitDB.query(tableName,colums,null,null,null,null,null);

        int cid = c.getColumnIndex(KEY_ID);
        int cun = c.getColumnIndex(KEY_USER_NAME);
        int cem = c.getColumnIndex(KEY_EMAIL);
        int cpw = c.getColumnIndex(KEY_PASSWORD);
        int phne = c.getColumnIndex(KEY_PHONE);
        nameValuePairList = new ArrayList<NameValuePair>();

        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            nameValuePairList.add(new BasicNameValuePair("un",c.getString(cun)));
            nameValuePairList.add(new BasicNameValuePair("em",c.getString(cem)));
            nameValuePairList.add(new BasicNameValuePair("pw",c.getString(cpw)));
            nameValuePairList.add(new BasicNameValuePair("phn",c.getString(phne)));
        }
        //DBClose();
        return nameValuePairList;
    }



}

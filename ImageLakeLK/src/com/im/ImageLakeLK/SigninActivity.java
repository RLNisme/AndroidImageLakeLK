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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.im.sync.SyncThread;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RLN on 4/2/2015.
 */
public class SigninActivity extends Activity implements View.OnClickListener{

    EditText un,pw;
    Button sin_btn_sin,sin_btn_sup;
    public SigninActivity sa;
    String userName= "",password="";

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

    //packages DB
    public final String DATABASE_PACKAGE_TABLE = "packages";
    public final String KEY_TYPE ="type";
    public final String KEY_TITLE ="title";
    public final String KEY_P1 = "p1";
    public final String KEY_P2 = "p2";
    public final String KEY_P3 = "p3";
    public final String KEY_ISNULL = "is_null";

    //admin profile DB
    public final String DATABASE_ADMIN_DETAILS_TABLE = "admin";
    public final String KEY_INCOME = "income";
    public final String KEY_USERS = "users";
    public final String KEY_IMGCOUNT = "imgcount";



    public int requestType = 1;

    public List<NameValuePair> nameValuePairList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_layout);

        sa = this;

       un = (EditText)findViewById(R.id.sin_un);
       pw = (EditText)findViewById(R.id.sin_pw);

       sin_btn_sin = (Button)findViewById(R.id.sin_btn_sin);
       sin_btn_sin.setOnClickListener(this);

       sin_btn_sup = (Button)findViewById(R.id.sin_btn_sup);
       sin_btn_sup.setOnClickListener(this);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onClick(View v) {
       if(v.getId() == R.id.sin_btn_sin) {
           userName = un.getText().toString().trim();
           password = pw.getText().toString().trim();

           if (!userName.equals("") && !password.equals("")) {

                   SyncThread st = new SyncThread(sa);
                   st.execute();

           } else {

               Toast.makeText(this, "Please check user details", Toast.LENGTH_LONG).show();
           }


       }else if(v.getId()==R.id.sin_btn_sup){
               Intent i = new Intent(this,SignupActivity.class);
               startActivity(i);

       }
    }

    public void responseString(String s){
        Log.d("--MSG--", "Response String");
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();

    }

    public void responseJson(String s){
        Log.d("--MSG--","response JSON");

        try {
            JSONObject jo = new JSONObject(s);
            String id = jo.getString("id");
            String user_name = jo.getString("un");
            String password =jo.getString("pw");
            String first_name = jo.getString("fn");
            String last_name = jo.getString("ln");
            String email =jo.getString("em");
            String user_type = jo.getString("user_type");
            String state = jo.getString("state");


            Log.d("--MSG--", id + "/" + user_name + "/" + user_type+"/"+state);



            DBOpen();//open DB
            DBUpgrad(DATABASE_TABLE);// upgrade signin table if it exists
            createDBTable(DATABASE_TABLE);//create new signin table
            long l = insertData(DATABASE_TABLE,Integer.parseInt(id), userName, password,first_name,last_name,email,Integer.parseInt(user_type),Integer.parseInt(state));//insert data to signin table



            DBClose();

            Intent i = new Intent(this,AccountActivity.class);
            i.putExtra("id",Integer.parseInt(id));
            i.putExtra("user_type",Integer.parseInt(user_type));
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            this.finish();
            startActivity(i);

        }catch (Exception e){
            Log.d("--Error--","Exception");
            e.printStackTrace();
            Toast.makeText(this,"Internal error,please try again later.",Toast.LENGTH_LONG).show();
        }

    }


    public void DBOpen(){
        sqlitDB = openOrCreateDatabase("ImageLKDB",MODE_PRIVATE,null);
    }

    public void DBClose(){
        sqlitDB.close();
    }

    public void DBUpgrad(String tableName){
        sqlitDB.execSQL("DROP TABLE IF EXISTS " + tableName);

    }

    public void createDBTable(String tableName){
        sqlitDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableName + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+KEY_USER_ID+" INTEGER," + KEY_USER_NAME + " VARCHAR," + KEY_PASSWORD + " VARCHAR,"+KEY_FN+" VARCHAR,"+KEY_LN+" VARCHAR,"+KEY_EM+" VARCHAR,"+KEY_USER_TYPE+" INTEGER,"+KEY_STATE+" INTEGER);");

    }

    public long insertData(String tableName,int userId,String userName,String password,String fn,String ln,String em,int userType,int state){
        ContentValues cv = new ContentValues();
        cv.put(KEY_USER_ID,userId);
        cv.put(KEY_USER_NAME,userName);
        cv.put(KEY_PASSWORD, password);
        cv.put(KEY_FN,fn);
        cv.put(KEY_LN,ln);
        cv.put(KEY_EM,em);
        cv.put(KEY_USER_TYPE,userType);
        cv.put(KEY_STATE,state);
        return sqlitDB.insert(tableName,null,cv);
    }

    //create packages Db
    public void createPackageDBTable(String tableName){
        Log.d("--msg--","inside the packagesDBTable");
        sqlitDB.execSQL("CREATE TABLE IF NOT EXISTS "+tableName+"("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+KEY_TYPE+" INTEGER,"+KEY_TITLE+" VARCHAR,"+KEY_P1+" VARCHAR,"+KEY_P2+" VARCHAR,"+KEY_P3+" VARCHAR,"+KEY_ISNULL+" INTEGER);");
    }

    public long inserPackageData(String tableName,String title,String p1,String p2,String p3,int type,int isNull){
        Log.d("--MSG--","insertPackageData");
        ContentValues cv = new ContentValues();
        cv.put(KEY_TYPE,type);
        cv.put(KEY_TITLE,title);
        cv.put(KEY_P1,p1);
        cv.put(KEY_P2,p2);
        cv.put(KEY_P3,p3);
        cv.put(KEY_ISNULL,isNull);

        return sqlitDB.insert(tableName,null,cv);
    }

    //create Admin Details DB
    public void createAdminDBTable(String tableName){
        sqlitDB.execSQL("CREATE TABLE IF NOT EXISTS "+tableName+"("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+KEY_INCOME+" DOUBLE,"+KEY_USERS+" INTEGER,"+KEY_IMGCOUNT+" INTEGER);");
    }

    public long insertAdminData(String tablName,double income,int users,int imgcount){
        ContentValues cv = new ContentValues();
        cv.put(KEY_INCOME,income);
        cv.put(KEY_USERS,users);
        cv.put(KEY_IMGCOUNT,imgcount);

        return sqlitDB.insert(tablName,null,cv);
    }


    /*public List<NameValuePair> getData(String tableName){
        String[] columsName = new String[]{KEY_ID,KEY_USER_NAME,KEY_PASSWORD};
        Cursor c = sqlitDB.query(tableName,columsName,null,null,null,null,null);

        int cid = c.getColumnIndex(KEY_ID);
        int cun = c.getColumnIndex(KEY_USER_NAME);
        int cpw = c.getColumnIndex(KEY_PASSWORD);

        nameValuePairList = new ArrayList<NameValuePair>();

        for (c.moveToFirst(); !c.isAfterLast();c.moveToNext()){
            Log.d("--MSG--",c.getString(cun)+"/"+c.getString(cpw));
            nameValuePairList.add(new BasicNameValuePair("un",c.getString(cun)));
            nameValuePairList.add(new BasicNameValuePair("pw",c.getString(cpw)));
        }
        return nameValuePairList;
    }*/

    public List<NameValuePair> getData(String tableName){

        nameValuePairList = new ArrayList<NameValuePair>();
        nameValuePairList.add(new BasicNameValuePair("un",un.getText().toString()));
        nameValuePairList.add(new BasicNameValuePair("pw",pw.getText().toString()));
        return nameValuePairList;
    }

}

package com.im.ImageLakeLK;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.im.sync.SyncThread;

public class MainActivity extends Activity{
    /**
     * Called when the activity is first created.
     */

    MainActivity ma;

    public ProgressBar loading;

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



    public int requestType = 0;
    public int user_id;
    public int user_type;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ma = this;

        loading = (ProgressBar)findViewById(R.id.load_network);
        loading.setVisibility(ProgressBar.INVISIBLE);

       checkSignin();

    }


    public void responseString(String s){
        if(s.equals("OK")){
            Intent i = new Intent(this,ImageSearchActivity.class);
            this.finish();
            startActivity(i);
        }else {
            loading.setVisibility(ProgressBar.INVISIBLE);
            Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        }
    }

    public void checkSignin(){
        DBOpen();
         getData();
    }

    private void DBOpen(){
        sqlitDB = openOrCreateDatabase("ImageLKDB",MODE_PRIVATE,null);
    }
    private void getData(){
        String columns[] = {KEY_USER_ID,KEY_USER_NAME,KEY_PASSWORD,KEY_FN,KEY_LN,KEY_EM,KEY_USER_TYPE,KEY_STATE};
        Cursor c = sqlitDB.query(DATABASE_TABLE,columns,null,null,null,null,null);

        int count = c.getCount();
        if(count > 0) {
            int iid = c.getColumnIndex(KEY_USER_ID);

            int iut = c.getColumnIndex(KEY_USER_TYPE);


            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                user_id = c.getInt(iid);
                user_type = c.getInt(iut);
                Log.d("id", user_id + "");
                Log.d("ut",user_type+"");
            }
            Log.d("id", user_id + "");
            Log.d("ut",user_type+"");

            Intent i = new Intent(ma,AccountActivity.class);
            i.putExtra("id",user_id);
            i.putExtra("user_type",user_type);
            this.finish();
            startActivity(i);

        }else{
            SyncThread st = new SyncThread(ma);
            st.execute();
        }
    }

}

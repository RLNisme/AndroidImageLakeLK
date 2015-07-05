package com.im.ImageLakeLK;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.im.sync.SyncThread;

import org.json.JSONObject;

/**
 * Created by RLN on 6/4/2015.
 */
public class PackagesActivity extends Activity implements View.OnClickListener{

    public static final String LOAD_ALL= "Load_all";
    public static final String LOAD_FT= "free_trial";
    public static final String LOAD_UT= "Ultimate_free_trial";

    Intent i;
    SyncThread st;
    PackagesActivity pga;
    public int user_id,user_type;

    Button ftb,utb,cb,sb;
    TextView ft1,ft2,ut1;

    public String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.packages_activity);

        pga = this;

        i = getIntent();
        user_id = i.getIntExtra("uid",-1);
        user_type = i.getIntExtra("ut",-1);

        ftb = (Button)findViewById(R.id.ftb);
        ftb.setOnClickListener(this);

        utb = (Button)findViewById(R.id.utb);
        utb.setOnClickListener(this);

        cb = (Button)findViewById(R.id.cb);
        cb.setOnClickListener(this);

        sb = (Button)findViewById(R.id.sb);
        sb.setOnClickListener(this);

        ft1 = (TextView)findViewById(R.id.ft1);
        ft2 = (TextView)findViewById(R.id.ft2);
        ut1 = (TextView)findViewById(R.id.ut1);

        loadAll();
    }

    public void loadAll(){
        type = LOAD_ALL;
        st = new SyncThread(pga);
        st.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*MenuInflater mif2 = getMenuInflater();
        mif2.inflate(R.menu.package_menu,menu);
        if(user_id > -1 && user_id > 0){
            if(user_type == 2 || user_type == 3){
                menu.findItem(R.id.next).setVisible(true);
                menu.findItem(R.id.cart).setVisible(true);
                menu.findItem(R.id.profil).setVisible(true);
            }else{
                menu.findItem(R.id.next).setVisible(true);
                menu.findItem(R.id.cart).setVisible(false);
                menu.findItem(R.id.profil).setVisible(true);
            }
        }else{
            menu.findItem(R.id.next).setVisible(true);
            menu.findItem(R.id.cart).setVisible(false);
            menu.findItem(R.id.profil).setVisible(false);
        }*/
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        /*if(item.getTitle().equals("Next")){
            Intent i = new Intent(PackagesActivity.this,ImageSearchActivity.class);
            i.putExtra("uid",user_id);
            i.putExtra("ut",user_type);
            pga.finish();
            startActivity(i);

        }else if(item.getTitle().equals("Cart")){
            Intent i = new Intent(PackagesActivity.this,CartActivity.class);
            i.putExtra("uid",user_id);
            i.putExtra("ut",user_type);
            pga.finish();
            startActivity(i);
        }else if(item.getTitle().equals("Pro")){
            Intent i = new Intent(PackagesActivity.this,AccountActivity.class);
            i.putExtra("id",user_id);
            i.putExtra("user_type",user_type);
            pga.finish();
            startActivity(i);
        }*/
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ftb){

            if(user_id == -1 || user_id == 0){
                Intent i = new Intent(pga,SigninActivity.class);
                startActivity(i);
            }else {
                if(user_type == 2 || user_type == 3){
                    type = LOAD_FT;
                    st = new SyncThread(pga);
                    st.execute();
                }else{
                    Toast.makeText(pga,"Packages are only to contributors and buyers.",Toast.LENGTH_LONG).show();
                }
            }
        }else if(v.getId() == R.id.utb){
            if(user_id == -1 || user_id == 0){
                Intent i = new Intent(pga,SigninActivity.class);
                startActivity(i);
            }else {
                if(user_type == 2 || user_type == 3){
                    type = LOAD_UT;
                    st = new SyncThread(pga);
                    st.execute();
                }else{
                    Toast.makeText(pga,"Packages are only to contributors and buyers.",Toast.LENGTH_LONG).show();
                }
            }
        }else if(v.getId() == R.id.sb){
            Intent i = new Intent(pga,SubscriptionActivity.class);
            i.putExtra("uid",user_id);
            i.putExtra("ut",user_type);
            startActivity(i);
        }else if(v.getId() == R.id.cb){
            Intent i = new Intent(pga,CreditActivity.class);
            i.putExtra("uid",user_id);
            i.putExtra("ut",user_type);
            startActivity(i);
        }
    }

    public void stringErrorMsg(String s) {
        Toast.makeText(pga,s,Toast.LENGTH_LONG).show();
    }

    public void JSONResponse(String s) {
        try {
            JSONObject jo = new JSONObject(s);
            ft1.setText("Start a "+jo.getString("ft_days")+"-day Free Trial");
            ft2.setText(jo.getString("ft_dwn")+" Downloads. Every day. Any Image.No Strings Attached.");
            ut1.setText(jo.getString("ft2_dwn")+" free image per "+jo.getString("ft2_days")+" days.Every "+jo.getString("ft2_days")+" days. No commitment. Upgrade whenever you're ready.");
            utb.setText("Start My "+jo.getString("ft2_dwn")+" Image "+jo.getString("ft2_days")+" days Unlimited Free Trial");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stringResponse(String s) {


            Toast.makeText(pga,s,Toast.LENGTH_LONG).show();

    }
}

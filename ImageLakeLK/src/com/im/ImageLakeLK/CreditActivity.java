package com.im.ImageLakeLK;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.im.adapter.CreditAdapter;
import com.im.adapter.SubscriptionAdapter;

import java.util.ArrayList;

/**
 * Created by RLN on 6/5/2015.
 */
public class CreditActivity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener{

    public static final String GET_CREDIT= "load_crd";

    CreditActivity ca;
    CreditAdapter caa;
    //Intent i;

    Intent i;
    ListView splist;
    public LinearLayout sp_list_content,sp_msg_content,sp_loading_content;
    Button sp_btn_refresh;
    TextView msg;
    ProgressBar pb;
    String type;

    public int uid;
    public int user_type;
    public ArrayList<String> us_li ;//lightbox list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_package_activity);

        ca = this;

        i = getIntent();
        uid = i.getIntExtra("uid",-1);
        user_type =i.getIntExtra("ut",-1);

        getActionBar().setTitle("Credit Packages");

        splist =(ListView)findViewById(R.id.splist);

        sp_list_content = (LinearLayout)findViewById(R.id.sp_list_content);
        sp_list_content.setVisibility(View.GONE);



        sp_msg_content =(LinearLayout)findViewById(R.id.sp_msg_content);
        sp_msg_content.setVisibility(View.GONE);

        msg =(TextView)findViewById(R.id.sp_msg);
        sp_btn_refresh =(Button)findViewById(R.id.sp_btn_refresh);
        sp_btn_refresh.setOnClickListener(this);

        sp_loading_content =(LinearLayout)findViewById(R.id.sp_load_content);
        sp_loading_content.setVisibility(View.GONE);

        pb =(ProgressBar)findViewById(R.id.sp_loading);
        loadPackages();
    }

    void loadPackages(){
        type = GET_CREDIT;

        us_li = new ArrayList<String>();
        sp_list_content.setVisibility(View.GONE);
        sp_msg_content.setVisibility(View.GONE);
        sp_loading_content.setVisibility(View.VISIBLE);

        caa = new CreditAdapter(this,type,ca);
        splist.setAdapter(caa);
        splist.setOnItemClickListener(this);
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(uid > -1 || uid > 0){
            if(user_type == 2 || user_type == 3) {
                String s = us_li.get(position);
                String ss[] = s.split("/");

                Intent i = new Intent(ca, PurchaseActivity.class);
                i.putExtra("pck_id", Integer.parseInt(ss[0]));
                i.putExtra("uid", uid);
                i.putExtra("ut", user_type);
                i.putExtra("ptd",Integer.parseInt(ss[1]) * 30 );
                i.putExtra("pdwn",Integer.parseInt(ss[2]));
                i.putExtra("ptot",Double.parseDouble(ss[4]));
                i.putExtra("type",4);
                i.putExtra("t1", "Start your " + Integer.parseInt(ss[1]) * 30 + " days Credit Package");
                i.putExtra("t2", "Any " + Integer.parseInt(ss[2]) + " credits. Any size. Every day." +
                        "Automatic renewal. Cancel anytime. Fuss-free all the way. ");
                startActivity(i);
            }else{
                Toast.makeText(ca,"Only Buyers & Contributors can purchase packages.",Toast.LENGTH_LONG).show();
            }
        }else{
            Intent i = new Intent(ca,SigninActivity.class);
            startActivity(i);

        }


    }

    public void stringErrorMsg(String s) {
        sp_list_content.setVisibility(View.GONE);
        sp_msg_content.setVisibility(View.VISIBLE);
        sp_loading_content.setVisibility(View.GONE);

        msg.setText(s);

        Toast.makeText(ca, s, Toast.LENGTH_LONG).show();
    }

    public void stringResponse(String s) {
        Toast.makeText(ca,s,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.sp_btn_refresh){
            loadPackages();
        }
    }
}

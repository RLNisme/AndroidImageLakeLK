package com.im.ImageLakeLK;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.im.adapter.MyUploadsAdapter;
import com.im.adapter.UploadManagementAdapter;

import java.util.ArrayList;

/**
 * Created by RLN on 4/30/2015.
 */
public class UploadManagementActivity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener{

    UploadManagementActivity upma;
    UploadManagementAdapter upmaa;
    Intent i;
    public LinearLayout upm_list_content,upm_msg_content,upm_loading_content;
    TextView msg;
    Button refresh;
    public ListView lv;
    public int user_id;
    public ArrayList<String> uploads_li = new ArrayList<String>();//lightbox list
    public int request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploadmanagement_layout);

        upma = this;

        i = getIntent();
        user_id = i.getIntExtra("id",-1);

        getActionBar().setHomeButtonEnabled(true);// this is goin to enable the home button
        getActionBar().setDisplayHomeAsUpEnabled(true);// this will show home screen of your app
        getActionBar().setTitle("Upload Management");

        upm_list_content =(LinearLayout)findViewById(R.id.upm_content);
        upm_list_content.setVisibility(View.GONE);

        upm_msg_content =(LinearLayout)findViewById(R.id.upm_msg_content);
        upm_msg_content.setVisibility(View.GONE);

        upm_loading_content =(LinearLayout)findViewById(R.id.upm_load_content);
        upm_loading_content.setVisibility(View.GONE);

        lv =(ListView)findViewById(R.id.upm_list);

        msg =(TextView)findViewById(R.id.upm_msg);

        refresh = (Button)findViewById(R.id.upm_btn_refresh);
        refresh.setOnClickListener(this);

        request = 0;
        getUploadsList();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.upm_btn_refresh){
            Log.d("--MSG--", "Clicked");
            getUploadsList();
        }
    }

    public void getUploadsList(){

        upm_list_content.setVisibility(View.GONE);
        upm_msg_content.setVisibility(View.GONE);

        upm_loading_content.setVisibility(View.VISIBLE);
        upmaa = new UploadManagementAdapter(this,user_id,upma);
        lv.setAdapter(upmaa);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String k = uploads_li.get(position);
        String kk[] = k.split("%");

        Intent i = new Intent(UploadManagementActivity.this,UploadManagementSingleActivity.class);
        i.putExtra("id",user_id);
        i.putExtra("imgid",Integer.parseInt(kk[0]));
        i.putExtra("req",1);
        NavUtils.navigateUpFromSameTask(this);
        this.finish();
        startActivity(i);
    }

    public void stringErrorMsg(String s) {
        upm_list_content.setVisibility(View.GONE);
        upm_loading_content.setVisibility(View.GONE);
        upm_msg_content.setVisibility(View.VISIBLE);
        msg.setText("No item found.");
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }



    public void stringResponse(String s) {
        upm_list_content.setVisibility(View.GONE);
        upm_loading_content.setVisibility(View.GONE);
        upm_msg_content.setVisibility(View.VISIBLE);
        msg.setText(s);
    }


    public void ErrorMsg(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    public void Response(String s) {
        if(s.equals("Successful...")){
           // Toast.makeText(this,s,Toast.LENGTH_LONG).show();
            //getUploadsList();

        }else{
            Toast.makeText(this,s,Toast.LENGTH_LONG).show();
        }
    }
}

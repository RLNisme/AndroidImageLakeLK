package com.im.ImageLakeLK;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.im.adapter.MyUploadsAdapter;
import com.im.dialogbox.LightBoxDialog;
import com.im.dialogbox.MyUploadDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RLN on 4/19/2015.
 */
public class MyUploadsActivity extends Activity implements AdapterView.OnItemClickListener,View.OnClickListener{

    MyUploadsActivity mua;
    MyUploadsAdapter mupa;
    Intent i;
    public LinearLayout myup_list_content,myup_msg_content,myup_loading_content;
    TextView msg;
    Button refresh;
    public ListView lv;
    public int user_id;
    public int img_id;

    public int request;
    public ArrayList<String> myuploads_li = new ArrayList<String>();//lightbox list
    public List<NameValuePair> li;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myuploads_layout);

        mua = this;
        i = getIntent();
        user_id = i.getIntExtra("id",-1);

        getActionBar().setHomeButtonEnabled(true);// this is goin to enable the home button
        getActionBar().setDisplayHomeAsUpEnabled(true);// this will show home screen of your app
        getActionBar().setTitle("My Uploads");

        myup_list_content =(LinearLayout)findViewById(R.id.myup_list_content);
        myup_list_content.setVisibility(View.GONE);

        myup_msg_content =(LinearLayout)findViewById(R.id.myup_msg_content);
        myup_msg_content.setVisibility(View.GONE);

        myup_loading_content =(LinearLayout)findViewById(R.id.myup_load_content);
        myup_loading_content.setVisibility(View.GONE);

        lv =(ListView)findViewById(R.id.myup_list);

        msg =(TextView)findViewById(R.id.myup_msg);

        refresh = (Button)findViewById(R.id.myup_btn_refresh);
        refresh.setOnClickListener(this);

        request = 0;
        getMyUploadsList();

    }


    public void getMyUploadsList(){

        myup_list_content.setVisibility(View.GONE);
        myup_msg_content.setVisibility(View.GONE);

        myup_loading_content.setVisibility(View.VISIBLE);
        mupa = new MyUploadsAdapter(this,user_id,mua);
        lv.setAdapter(mupa);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("--MSG--",position+"");

        String item = myuploads_li.get(position);
        String nano_items[] = item.split("%");

        img_id = Integer.parseInt(nano_items[0]);
        FragmentManager fm = getFragmentManager();
        DialogFragment newFragment = new MyUploadDialog();
        newFragment.show(fm, "MyUploads");
        //request = 1;


    }


    public void stringErrorMsg(String s) {
        myup_list_content.setVisibility(View.GONE);
        myup_loading_content.setVisibility(View.GONE);
        myup_msg_content.setVisibility(View.VISIBLE);
        msg.setText("No item found.");
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }



    public void stringResponse(String s) {
        myup_list_content.setVisibility(View.GONE);
        myup_loading_content.setVisibility(View.GONE);
        myup_msg_content.setVisibility(View.VISIBLE);
        msg.setText(s);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.myup_btn_refresh){
            Log.d("--MSG--","Clicked");
            getMyUploadsList();
        }
    }

    public void addListData(){
        li = new ArrayList<NameValuePair>();
        li.add(new BasicNameValuePair("uid",user_id+""));
        li.add(new BasicNameValuePair("imgid",img_id+""));

    }

    public void ErrorMsg(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    public void Response(String s) {
        if(s.equals("Successful...")){
            Toast.makeText(this,s,Toast.LENGTH_LONG).show();
            getMyUploadsList();

        }else{
            Toast.makeText(this,s,Toast.LENGTH_LONG).show();
        }
    }
}

package com.im.ImageLakeLK;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.*;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.PopupMenu;
import com.im.adapter.LightBoxAdapter;
import com.im.dialogbox.LightBoxDialog;

import java.util.ArrayList;

/**
 * Created by RLN on 4/9/2015.
 */
public class LightBoxActivity extends Activity implements AdapterView.OnItemClickListener,View.OnClickListener{

    private static final int ERORR = 1;
    private static final int INFO = 2;
    private static final int WARNING = 3;
    private static final int SUCCESS = 4;
    private static final int JSON = 5;


    public LightBoxActivity lbac;
    public LightBoxAdapter lbad;
    public int type;
    public ListView lv;
    public LinearLayout ll,ll_loading;
    public TextView tv;
    public ProgressBar pb;
    public Button refresh;
    public ArrayList<String> lightbox_li = new ArrayList<String>();//lightbox list
    public Intent i;
    public int user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lightbox_activity);

        getActionBar().setHomeButtonEnabled(true);// this is goin to enable the home button
        getActionBar().setDisplayHomeAsUpEnabled(true);// this will show home screen of your app
        getActionBar().setTitle("LightBox");
        lbac = this;

        type = 1;

        i = getIntent();
        user_id = i.getIntExtra("id", -1);

        ll =(LinearLayout)findViewById(R.id.light_box_msg_content);//no item found content
        tv =(TextView)findViewById(R.id.light_box_msg);
        refresh = (Button)findViewById(R.id.light_box_btn_refresh);
        ll_loading =(LinearLayout)findViewById(R.id.light_box_load_content);//progress bar content
        pb =(ProgressBar)findViewById(R.id.light_box_loading);
        lv = (ListView)findViewById(R.id.box_list);//list view in light box



        loadLightBoxDetails();

        refresh.setOnClickListener(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mif = getMenuInflater();
        mif.inflate(R.menu.menu_styles, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("--MSG--", item.getItemId() + "");

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                //this.finish();

                return true;
            default:
               // Log.d("--MSG--",item.getItemId()+"");
               showDialog();
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("--MSG--","iiiiiiiiii "+position);
    }

    public void msgResponse(String s){
        ll_loading.setVisibility(View.GONE);
        lv.setVisibility(View.GONE);
        ll.setVisibility(View.VISIBLE);
        tv.setText(s);
    }


    public void errorResponse(String s){
        ll_loading.setVisibility(View.GONE);
        lv.setVisibility(View.GONE);
        ll.setVisibility(View.VISIBLE);
        tv.setText("No item found.");

        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.light_box_btn_refresh){
            loadLightBoxDetails();
        }
    }

    public void loadLightBoxDetails(){


        ll.setVisibility(View.GONE);//setting lightbox list gone

        ll_loading.setVisibility(View.GONE);//setting progress layout gone


        lv.setVisibility(View.GONE);
        lbad = new LightBoxAdapter(this,user_id,type,lbac);
        lv.setAdapter(lbad);
        lv.setOnItemClickListener(this);
    }

    public void showDialog(){
        Log.d("--MSG--","inside the showDialog");
        FragmentManager fm = getFragmentManager();
        DialogFragment newFragment = new LightBoxDialog();
        newFragment.show(fm, "missiles");

    }

}

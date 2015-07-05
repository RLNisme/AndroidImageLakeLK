package com.im.ImageLakeLK;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.im.adapter.DownloadHistoryAdapter;
import com.im.dialogbox.DownloadHistoryDialog;
import com.im.dialogbox.LightBoxDialog;

import java.util.ArrayList;

/**
 * Created by RLN on 4/12/2015.
 */



public class DownloadHistoryActivity extends Activity implements AdapterView.OnItemClickListener{

    DownloadHistoryActivity dha;
    public DownloadHistoryAdapter dhaa;
    public ListView lv;
    public TextView tv;
    public LinearLayout dmc,dlc;
    public ProgressBar pb;
    public Button refresh;
    public ArrayList<String> downloadHistory_li =new ArrayList<String>();

    public int user_id;
    public int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.downloadhistory_activity);

        dha = this;

        getActionBar().setHomeButtonEnabled(true);// this is goin to enable the home button
        getActionBar().setDisplayHomeAsUpEnabled(true);// this will show home screen of your app
        getActionBar().setTitle("Download History");

        Intent i =getIntent();
        user_id = i.getIntExtra("id",-1);

        type = 1;

        lv =(ListView)findViewById(R.id.download_list);
        lv.setVisibility(View.GONE);

        dmc =(LinearLayout)findViewById(R.id.downloadhistory_msg_content);
        dmc.setVisibility(View.GONE);
        tv =(TextView)findViewById(R.id.downloadhistory_msg);

        dlc =(LinearLayout)findViewById(R.id.downloadhistory_load_content);
        dlc.setVisibility(View.GONE);

        pb =(ProgressBar)findViewById(R.id.downloadhistory_loading);
        refresh =(Button)findViewById(R.id.downloadhistory_btn_refresh);

        loadDownloadHidtoryDetails();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_styles,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                //this.finish();

                return true;
            default:
                showDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    public void msgResponse(String s){
        dlc.setVisibility(View.GONE);
        lv.setVisibility(View.GONE);
        dmc.setVisibility(View.VISIBLE);
        tv.setText(s);
    }

    public void errorResponse(String s){
        dlc.setVisibility(View.GONE);
        lv.setVisibility(View.GONE);
        dmc.setVisibility(View.VISIBLE);
        tv.setText("No item found.");

        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    public void loadDownloadHidtoryDetails(){
        dmc.setVisibility(View.GONE);
        dlc.setVisibility(View.GONE);
        lv.setVisibility(View.GONE);

        dhaa = new DownloadHistoryAdapter(this,user_id,type,dha);
        lv.setAdapter(dhaa);
        lv.setOnItemClickListener(this);
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public void showDialog(){
        Log.d("--MSG--", "inside the showDialog");
        FragmentManager fm = getFragmentManager();
        DialogFragment newFragment = new DownloadHistoryDialog();
        newFragment.show(fm, "history");
    }
}

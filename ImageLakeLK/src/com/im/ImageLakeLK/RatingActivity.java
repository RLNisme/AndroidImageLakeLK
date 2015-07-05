package com.im.ImageLakeLK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.im.adapter.ImageManagementViewHolder;
import com.im.adapter.RatingAdapter;
import com.im.sync.BitmapAsync;
import com.im.sync.SyncThread;

import java.util.ArrayList;

/**
 * Created by RLN on 6/8/2015.
 */
public class RatingActivity extends Activity implements AdapterView.OnItemClickListener{

    RatingActivity rta;
    RatingAdapter rtaa;

    public ListView li;
    Button b1;
    Intent i;
    public ArrayList<String> vb_li ;//lightbox list




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_activity);

        rta = this;

        li =(ListView)findViewById(R.id.li1);
        b1 =(Button)findViewById(R.id.ok);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rta.finish();
            }
        });
        i = getIntent();

        vb_li = i.getStringArrayListExtra("rate");
        if(vb_li.isEmpty()){
            Toast.makeText(this,"Error.",Toast.LENGTH_LONG).show();
            this.finish();
        }else{

            rtaa = new RatingAdapter(this,rta);
            li.setAdapter(rtaa);
            li.setOnItemClickListener(this);
        }

    }



    public void stringErrorMsg(String s) {
        Toast.makeText(rta,s,Toast.LENGTH_SHORT).show();
    }

    public void JSONResponse(String s) {
    }

    public void stringResponse(String s) {
        Toast.makeText(rta,s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

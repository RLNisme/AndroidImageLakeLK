package com.im.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.im.ImageLakeLK.R;
import com.im.ImageLakeLK.RatingActivity;
import com.im.sync.BitmapAsync;
import com.im.sync.SyncThread;

/**
 * Created by RLN on 6/8/2015.
 */
public class RatingAdapter extends BaseAdapter {

    RatingActivity rta;
    RatingAdapter rtaa;
    SyncThread st;
    Context context;
    public RatingBar rb;
    public ImageView iv;
    public int chid;
    public float x;

    public RatingAdapter(Context context,RatingActivity rta){
        this.rtaa = this;
        this.rta = rta;
        this.context= context;
    }

    @Override
    public int getCount() {
        return rta.vb_li.size();
    }

    @Override
    public Object getItem(int position) {
        return rta.vb_li.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = null;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)rta.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.rating_row,parent,false);

            iv =(ImageView)row.findViewById(R.id.upm_img);

            rb =(RatingBar)row.findViewById(R.id.upm_rb);

        }else{
            row = convertView;

        }

        String list_items = rta.vb_li.get(position);
        String[] list_item = list_items.split("%");

        Log.d("--MSG--", "{" + list_items + "}");


        chid = Integer.parseInt(list_item[7]);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                x = rating;
                Log.d("rate",x+"");
                Log.d("chid",chid+"");
                st = new SyncThread(rtaa);

                st.execute();
            }
        });

        BitmapAsync ba = new BitmapAsync(iv,rta);
        ba.execute(list_item[5]);



        return row;
    }


    public void stringErrorMsg(String s) {
       rta.stringErrorMsg(s);
    }

    public void JSONResponse(String s) {
    }

    public void stringResponse(String s) {
        rta.stringResponse(s);
    }


}

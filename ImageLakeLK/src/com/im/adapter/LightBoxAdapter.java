package com.im.adapter;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.ImageButton;
import android.widget.TextView;
import com.im.ImageLakeLK.LightBoxActivity;
import com.im.ImageLakeLK.R;
import com.im.sync.SyncThread;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by RLN on 4/10/2015.
 */
public class LightBoxAdapter extends BaseAdapter {

    private Context context;

    public String[] lightbox;
    private LightBoxAdapter lbad;
    private LightBoxActivity lbac;

    public int requestType = 0;

    public int user_id;
    public int type;

    public ArrayList<String> li = new ArrayList<String>();

    public LightBoxAdapter(Context context,int user_id,int type,LightBoxActivity lbac){
        Log.d("--MSG--","user id "+user_id);
        Log.d("--MSG--","type "+type);
        this.context = context;
        this.user_id = user_id;
        this.type = type;
        this.lbac = lbac;
        getLightBoxData();
    }

    @Override
    public int getCount() {
        Log.d("--MSG--", li.size() + " getCount");
        return li.size();
    }

    @Override
    public Object getItem(int position) {
        Log.d("--MSG--", lightbox[position] + " getItem");
        return lightbox[position];
    }

    @Override
    public long getItemId(int position) {
        Log.d("--MSG--", position + " getItemID");
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d("--MSG--", "Inside the LightBoxAdapter_" + position);
        View row = null;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.light_box_row,parent,false);
        }else{
            row = convertView;
        }

        String list_items = li.get(position);
        String[] list_item = list_items.split("-");

        lbac.lightbox_li = li;
        TextView title = (TextView)row.findViewById(R.id.light_box_img_title);
        title.setText(list_item[1]);

        TextView date =(TextView)row.findViewById(R.id.light_box_img_date);
        date.setText(list_item[2] + "-" + list_item[3] + "-" + list_item[4]);



        return row;
    }

    public void getLightBoxData(){
        lbad = this;
        SyncThread st = new SyncThread(lbad);
        st.execute();
    }


    public void JSONResponse(String s){

       try {
           JSONArray ar = new JSONArray(s);
           JSONObject jo;
           String box_row;

           for(int i = 0;i<ar.length();i++){
               jo = ar.getJSONObject(i);
               box_row = jo.getString("cart_id")+"-"+jo.getString("title")+"-"+jo.getString("date")+"-"+jo.getString("state");
               li.add(box_row);
               Log.d("--MSG--",box_row);
           }

           lbac.ll.setVisibility(View.GONE);
           lbac.lv.setVisibility(View.VISIBLE);

       }catch (Exception e){
            e.printStackTrace();
       }

    }


    public void stringResponse(String s){
        lbac.msgResponse(s);
    }

    public void stringErrorMsg(String s){
        lbac.errorResponse(s);
    }

    public void setProgressBarVisible(){
        lbac.ll.setVisibility(View.GONE);
        lbac.lv.setVisibility(View.GONE);
        lbac.ll_loading.setVisibility(View.VISIBLE);
    }
    public void setProgressBarGone(){
        lbac.ll_loading.setVisibility(View.GONE);
    }
}

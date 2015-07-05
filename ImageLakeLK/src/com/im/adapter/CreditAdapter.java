package com.im.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.im.ImageLakeLK.CreditActivity;
import com.im.ImageLakeLK.R;
import com.im.ImageLakeLK.SubscriptionActivity;
import com.im.sync.SyncThread;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by RLN on 6/5/2015.
 */
public class CreditAdapter extends BaseAdapter {

    public String type;
    Context context;
    SyncThread st;
    CreditActivity ca;
    CreditAdapter caa;

    public ArrayList<String> li = new ArrayList<String>();

    public CreditAdapter(Context context,String type,CreditActivity ca){
        this.context = context;
        this.type = type;
        this.ca = ca;
        loadPackages();
    }

    void loadPackages(){

        caa = this;

        st = new SyncThread(caa);
        st.execute();
    }

    @Override
    public int getCount() {
        return li.size();
    }

    @Override
    public Object getItem(int position) {
        return li.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = null;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.credit_package_row,parent,false);
        }else{

            row = convertView;
        }

        String list_items = li.get(position);

        ca.us_li = li;

        String[] list_item = list_items.split("/");

        TextView du,dwn,pimg,prc,state,disc;


        du =(TextView)row.findViewById(R.id.du);
        du.setText(list_item[1]+" Month");

        dwn =(TextView)row.findViewById(R.id.dwn);
        dwn.setText(list_item[2]+ "");

        pimg =(TextView)row.findViewById(R.id.pimg);
        pimg.setText("$ "+list_item[3]);

        prc =(TextView)row.findViewById(R.id.prc);
        prc.setText("$ "+list_item[4]);

        disc =(TextView)row.findViewById(R.id.disc);
        Log.d("Disco",list_item[5]);
        if(Double.parseDouble(list_item[5])== 0.0){
            disc.setVisibility(View.GONE);
        }else{
            disc.setVisibility(View.VISIBLE);
            disc.setText("SAVE "+Double.parseDouble(list_item[5])+"%");
        }

        state =(TextView)row.findViewById(R.id.state);
        state.setVisibility(View.GONE);
        if(Integer.parseInt((list_item[6]))== 2){
            state.setVisibility(View.VISIBLE);
            state.setText("New Package");
            state.setBackgroundColor(ca.getResources().getColor(R.color.md_green_600));
            state.setTextColor(ca.getResources().getColor(R.color.md_text_white));
        }else{
            state.setVisibility(View.VISIBLE);
            state.setText(list_item[2]+ " Credits");
            state.setBackgroundColor(ca.getResources().getColor(R.color.md_blue_700));
            state.setTextColor(ca.getResources().getColor(R.color.md_text_white));
        }


        return row;
    }

    public void stringErrorMsg(String s) {
        ca.stringErrorMsg(s);
    }

    public void JSONResponse(String s) {
        try {
            JSONArray ar = new JSONArray(s);
            JSONObject jo;
            String box_row;

            if(ar.length()>0) {
                for (int i = 0; i < ar.length(); i++) {
                    jo = ar.getJSONObject(i);
                    box_row = jo.getString("id") //0
                            + "/" + jo.getString("du")//1
                            + "/" + jo.getString("dwn") //2
                            + "/" + jo.getString("pimg") //3
                            + "/" + jo.getString("prc")//4
                            + "/" + jo.getString("disc") //5
                            + "/" + jo.getString("state") //6

                    ;
                    li.add(box_row);
                    Log.d("--MSG--", box_row);
                }

                ca.sp_list_content.setVisibility(View.VISIBLE);
                ca.sp_msg_content.setVisibility(View.GONE);

            }else{

                stringErrorMsg("No item found.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stringResponse(String s) {
        ca.stringResponse(s);
    }

    public void setProgressBarVisible(){
        ca.sp_msg_content.setVisibility(View.GONE);
        ca.sp_list_content.setVisibility(View.GONE);
        ca.sp_loading_content.setVisibility(View.VISIBLE);
    }
    public void setProgressBarGone(){
        ca.sp_loading_content.setVisibility(View.GONE);
    }
}

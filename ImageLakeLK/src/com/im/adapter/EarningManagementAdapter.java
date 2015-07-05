package com.im.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.im.ImageLakeLK.EarningManagementActivity;
import com.im.ImageLakeLK.R;
import com.im.sync.SyncThread;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by RLN on 4/24/2015.
 */
public class EarningManagementAdapter extends BaseAdapter{

    private Context context;

    public EarningManagementAdapter ema;
    public EarningManagementActivity emaa;
    public int user_id;
    public String type;
    public int requestType;
    public ArrayList<String> li;

    public String uid="";
    public String date = "";
    public boolean nr,cr;

    public EarningManagementAdapter(Context context,int user_id,String type,EarningManagementActivity emaa){
        this.context = context;
        this.user_id = user_id;
        this.type = type;
        this.emaa = emaa;
        li = new ArrayList<String>();
        getEarningData();
    }

    public EarningManagementAdapter(Context context,int user_id,String type,EarningManagementActivity emaa,String uid,String date,boolean nr,boolean cr){
        this.context = context;
        this.user_id = user_id;
        this.type = type;
        this.emaa = emaa;
        this.uid = uid;
        this.date = date;
        this.nr = nr;
        this.cr = cr;
        li = new ArrayList<String>();
        getSortDate();
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
            row = inflater.inflate(R.layout.earning_management_row,parent,false);
        }else{

            row = convertView;
        }
        String list_items = li.get(position);
        String[] list_item = list_items.split("-");

        emaa.earn_li = li;

        TextView req_em,req_uid,req_date,req_acc,req_state,req_amount;

        req_em =(TextView)row.findViewById(R.id.req_em);
        req_em.setText(list_item[8]);

        req_uid =(TextView)row.findViewById(R.id.req_uid);
        req_uid.setText(list_item[1]);

        req_date =(TextView)row.findViewById(R.id.req_date);
        req_em.setText(list_item[2]+"-"+list_item[3]+"-"+list_item[4]);

        req_acc =(TextView)row.findViewById(R.id.req_acc);
        req_acc.setText(list_item[5]);

        req_state =(TextView)row.findViewById(R.id.req_state);
        if(Integer.parseInt(list_item[7])== 1){
            req_state.setText("New Request");
            req_state.setBackgroundColor(emaa.getResources().getColor(R.color.md_blue_700));
            req_state.setTextColor(emaa.getResources().getColor(R.color.md_text_white));
        }else if(Integer.parseInt(list_item[7])== 2){
            req_state.setText("Success");
            req_state.setBackgroundColor(emaa.getResources().getColor(R.color.md_green_600));
            req_state.setTextColor(emaa.getResources().getColor(R.color.md_text_white));

        }else if(Integer.parseInt(list_item[7])== 3){
            req_state.setText("Canceled");
            req_state.setBackgroundColor(emaa.getResources().getColor(R.color.md_red_300));
            req_state.setTextColor(emaa.getResources().getColor(R.color.md_text_white));

        }
        req_amount =(TextView)row.findViewById(R.id.req_amount);
        req_amount.setText("$ "+Double.parseDouble(list_item[6]));

        return row;
    }

    public void getEarningData(){
        requestType =0;
        ema = this;
        SyncThread st = new SyncThread(ema);
        st.execute();
    }

    public void getSortDate(){
        requestType =0;
        ema = this;
        SyncThread st = new SyncThread(ema);
        st.execute();
    }


    public void JSONResponse(String s){

        try {
            JSONArray ar = new JSONArray(s);
            JSONObject jo;
            String box_row;

            if(ar.length()>0) {
                for (int i = 0; i < ar.length(); i++) {
                    jo = ar.getJSONObject(i);
                    box_row = jo.getString("id") + "-" + jo.getString("userid") + "-" + jo.getString("reqdate") + "-" + jo.getString("acctype") + "-" + jo.getString("amount")
                            + "-" + jo.getString("state") + "-" + jo.getString("email") + "-" + jo.getString("no");
                    li.add(box_row);
                    Log.d("--MSG--", box_row);
                }

                emaa.earning_content.setVisibility(View.VISIBLE);
                emaa.earning_msg_content.setVisibility(View.GONE);

            }else{

                stringResponse("No item found.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void setProgressBarVisible(){
        emaa.earning_msg_content.setVisibility(View.GONE);
        emaa.earning_content.setVisibility(View.GONE);
        emaa.earning_loading_content.setVisibility(View.VISIBLE);
    }
    public void setProgressBarGone(){
        emaa.earning_loading_content.setVisibility(View.GONE);
    }

    public void stringErrorMsg(String s) {
        emaa.errorMsg(s);
    }

    public void stringResponse(String s) {
        emaa.msgResponse(s);
    }
}

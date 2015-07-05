package com.im.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.im.ImageLakeLK.DownloadHistoryActivity;
import com.im.ImageLakeLK.R;
import com.im.sync.SyncThread;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by RLN on 4/12/2015.
 */
public class DownloadHistoryAdapter extends BaseAdapter{

    private Context context;
    public DownloadHistoryActivity dhaa;
    public DownloadHistoryAdapter dha;
    public int user_id;
    public int type;
    public String[] downloadHistory;
    public ArrayList<String> li =new ArrayList<String>();

    public DownloadHistoryAdapter(Context context,int user_id,int type,DownloadHistoryActivity dhaa){
        this.context = context;
        this.user_id = user_id;
        this.type = type;
        this.dhaa = dhaa;
        getDownloadHistoryData();
    }

    @Override
    public int getCount() {
        return li.size();
    }

    @Override
    public Object getItem(int position) {
        Log.d("--MSG--", downloadHistory[position] + " getItem");
        return downloadHistory[position];
    }

    @Override
    public long getItemId(int position) {
        Log.d("--MSG--", position + " getItemID");
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = null;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.downloadhistory_row,parent,false);
        }else{
            row = convertView;
        }

        String list_items = li.get(position);
        String[] list_item = list_items.split("-");

        dhaa.downloadHistory_li = li;
        TextView title = (TextView)row.findViewById(R.id.downloadhistory_img_title);
        title.setText(list_item[1]);

        TextView date =(TextView)row.findViewById(R.id.downloadhistory_img_date);
        date.setText(list_item[2] + "-" + list_item[3] + "-" + list_item[4]);

        TextView size =(TextView)row.findViewById(R.id.downloadhistory_size);
        size.setText(list_item[7]);

        return row;
    }

    public void getDownloadHistoryData(){
        dha = this;
        SyncThread st = new SyncThread(dha);
        st.execute();
    }

    public void JSONResponse(String s){

        try {
            JSONArray ar = new JSONArray(s);
            JSONObject jo;
            String box_row;

            for(int i = 0;i<ar.length();i++){
                jo = ar.getJSONObject(i);
                box_row = jo.getString("cart_id")+"-"+jo.getString("title")+"-"+jo.getString("date")+"-"+jo.getString("state")+"-"+jo.getString("sub_id")+"-"+jo.getString("credits");
                li.add(box_row);
                Log.d("--MSG--",box_row);
            }

            dhaa.dmc.setVisibility(View.GONE);
            dhaa.lv.setVisibility(View.VISIBLE);

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void stringResponse(String s){
        dhaa.msgResponse(s);
    }

    public void stringErrorMsg(String s){
        dhaa.errorResponse(s);
    }

    public void setProgressBarVisible(){
        dhaa.lv.setVisibility(View.GONE);
        dhaa.dmc.setVisibility(View.GONE);
        dhaa.dlc.setVisibility(View.VISIBLE);
    }

    public void setProgressBarGone(){
        dhaa.dlc.setVisibility(View.GONE);
    }

}

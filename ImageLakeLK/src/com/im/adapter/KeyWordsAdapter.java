package com.im.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.im.ImageLakeLK.R;
import com.im.ImageLakeLK.UploadManagementSingleActivity;
import com.im.sync.SyncThread;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RLN on 5/1/2015.
 */
public class KeyWordsAdapter extends BaseAdapter {

    UploadManagementSingleActivity umsa;
    KeyWordsAdapter kwa;
    Context c;
    public String type;
    public int imgid;

    List<String> li = new ArrayList<String>();

    public KeyWordsAdapter(Context c,int imgid,String type,UploadManagementSingleActivity umsa){
        this.c = c;
        this.imgid = imgid;
        this.type = type;
        this.umsa = umsa;
        getKeyWords();
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
            LayoutInflater inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.keyword_row,parent,false);
        }else{

            row = convertView;
        }

        String list_items = li.get(position);
        String[] list_item = list_items.split("%");

        TextView keyword;

        keyword =(TextView)row.findViewById(R.id.keyword);

        umsa.up_li = li;

        keyword.setText(list_item[1]);
        return row;
    }

    public void getKeyWords(){
        kwa = this;
        SyncThread st = new SyncThread(kwa);
        st.execute();

    }


    public void stringErrorMsg(String s) {
        umsa.stringErrorMsg(s);
    }

    public void JSONResponse(String s) {

        try {
            JSONArray ar = new JSONArray(s);
            JSONObject jo;
            String box_row;

            for(int i = 0;i<ar.length();i++){
                jo = ar.getJSONObject(i);
                box_row = jo.getString("kid")+"%"+jo.getString("key");
                Log.d("--MSG--",box_row);
                li.add(box_row);
                Log.d("--MSG--", box_row);
            }



        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void stringResponse(String s) {
        umsa.stringResponse(s);
    }
}

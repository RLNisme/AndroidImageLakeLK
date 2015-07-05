package com.im.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.im.ImageLakeLK.R;
import com.im.fragments.ImageSettingFragment;
import com.im.sync.SyncThread;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by RLN on 5/11/2015.
 */
public class CategoryAdapter extends BaseAdapter {
    CategoryAdapter ca;
    Context context;
    ImageSettingFragment isf;
    public String type ="";
    TextView imgset_cat_title;
    public ArrayList<String> li = new ArrayList<String>();

    public CategoryAdapter(Context context,String type,ImageSettingFragment isf){
        this.context = context;
        this.type = type;
        this.isf = isf;
        getAllCatList();
    }

    private void getAllCatList() {

        ca = this;
        SyncThread st = new SyncThread(ca);
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
            row = inflater.inflate(R.layout.categorylist_row,parent,false);
        }else{

            row = convertView;
        }

        String list_items = li.get(position);

       // isf.slice_show = li;

        String[] list_item = list_items.split("/");

        imgset_cat_title =(TextView)row.findViewById(R.id.imgset_cat_title);
        imgset_cat_title.setText(list_item[1]);

        return row;
    }

    public void stringErrorMsg(String s) {
        isf.stringErrorMsg(s);
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
                            + "/" + jo.getString("cat");//1


                    li.add(box_row);
                    Log.d("--MSG--", box_row);
                }


            }else{

                stringResponse("No item found.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void stringResponse(String s) {
        isf.stringResponse(s);
    }


}

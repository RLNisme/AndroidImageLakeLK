package com.im.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.im.ImageLakeLK.R;
import com.im.ImageLakeLK.UserManagementActivity;
import com.im.fragments.ImageSettingFragment;
import com.im.sync.SyncThread;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by RLN on 5/10/2015.
 */
public class ImageSettingAdapter extends BaseAdapter {

    ImageSettingAdapter isa;
    Context context;
    ImageSettingFragment isf;
    public String type ="";
    TextView imgset_slice_size,imgset_slice_width_height;
    public ArrayList<String> li = new ArrayList<String>();
    
    public ImageSettingAdapter(Context context,String type,ImageSettingFragment isf){
        this.context = context;       
        this.type = type;
        this.isf = isf;
        getAllSliceList();
    }

    private void getAllSliceList() {

        isa = this;
        SyncThread st = new SyncThread(isa);
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
            row = inflater.inflate(R.layout.slicelist_row,parent,false);
        }else{

            row = convertView;
        }

        String list_items = li.get(position);

        isf.slice_show = li;

        String[] list_item = list_items.split("/");

        imgset_slice_size =(TextView)row.findViewById(R.id.imgset_slice_size);
        imgset_slice_size.setText(list_item[2]);

        imgset_slice_width_height =(TextView)row.findViewById(R.id.imgset_slice_width_height);
        imgset_slice_width_height.setText(list_item[3]+" X "+list_item[4]);

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
                            + "/" + jo.getString("credits")//1
                            + "/" + jo.getString("size") //2
                            + "/" + jo.getString("width") //3
                            + "/" + jo.getString("height");//4

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

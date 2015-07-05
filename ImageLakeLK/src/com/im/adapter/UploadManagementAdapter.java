package com.im.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.im.ImageLakeLK.MyUploadsActivity;
import com.im.ImageLakeLK.R;
import com.im.ImageLakeLK.UploadManagementActivity;
import com.im.sync.BitmapAsync;
import com.im.sync.SyncThread;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by RLN on 4/30/2015.
 */
public class UploadManagementAdapter extends BaseAdapter {

    UploadManagementActivity upma;
    public UploadManagementAdapter upmaa;
    private Context context;
    public String[] uplods;
    public int user_id;
    public int request;
    public String type = "";
    public ArrayList<String> li = new ArrayList<String>();


    public UploadManagementAdapter(Context context,int uid,UploadManagementActivity upma){
        this.upma = upma;
        this.user_id = uid;
        this.context = context;
        getUploads();
    }

    private void getUploads() {
        request = 0;
        upmaa = this;
        type = "all";
        SyncThread st = new SyncThread(upmaa);
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
        UploadManagementViewHolder mvh = null;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.uploadmanagement_row,parent,false);

            mvh = new UploadManagementViewHolder();

            mvh.upm_title = (TextView)row.findViewById(R.id.upm_title);


            mvh.upm_date = (TextView)row.findViewById(R.id.upm_date);

            mvh.upm_img = (ImageView)row.findViewById(R.id.upm_img);

            mvh.upm_cat = (TextView)row.findViewById(R.id.upm_cat);

            mvh.upm_sel = (TextView)row.findViewById(R.id.upm_seller);

            row.setTag(mvh);

        }else{
            row = convertView;
            mvh = (UploadManagementViewHolder)row.getTag();
        }

        String list_items = li.get(position);
        String[] list_item = list_items.split("%");

        Log.d("--MSG--","{"+list_items+"}");
        upma.uploads_li = li;

        mvh.upm_title.setText(list_item[1]);
        mvh.upm_date.setText(list_item[2]);
        mvh.upm_cat.setText(list_item[4]);
        mvh.upm_sel.setText(list_item[5]);

        BitmapAsync ba = new BitmapAsync(mvh.upm_img,upma);
        ba.execute(list_item[3]);

        return row;
    }

    public void stringErrorMsg(String s) {
        upma.stringErrorMsg(s);
    }

    public void JSONResponse(String s) {

        try {
            JSONArray ar = new JSONArray(s);
            JSONObject jo;
            String box_row;

            if(ar.length() > 0) {

                for (int i = 0; i < ar.length(); i++) {
                    jo = ar.getJSONObject(i);
                    box_row = jo.getString("img_id") + "%" + jo.getString("title") + "%" + jo.getString("date") + "%" + jo.getString("url") + "%" + jo.getString("cat") + "%" + jo.getString("sel");
                    li.add(box_row);
                    Log.d("--MSG--", box_row);
                }

                upma.upm_msg_content.setVisibility(View.GONE);

                upma.upm_list_content.setVisibility(View.VISIBLE);
            }else{

                stringResponse("No New Uploads Found.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void stringResponse(String s) {
        upma.stringResponse(s);
    }

    public void setProgressBarVisible(){
        upma.upm_list_content.setVisibility(View.GONE);
        upma.upm_msg_content.setVisibility(View.GONE);
        upma.upm_loading_content.setVisibility(View.VISIBLE);
    }
    public void setProgressBarGone(){
        upma.upm_loading_content.setVisibility(View.GONE);
    }
}

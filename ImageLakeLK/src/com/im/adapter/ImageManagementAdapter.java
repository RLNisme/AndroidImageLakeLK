package com.im.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.im.ImageLakeLK.ImageManagementActivity;
import com.im.ImageLakeLK.R;
import com.im.ImageLakeLK.UploadManagementActivity;
import com.im.sync.BitmapAsync;
import com.im.sync.SyncThread;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by RLN on 5/4/2015.
 */
public class ImageManagementAdapter extends BaseAdapter {

    ImageManagementActivity ima;
    public ImageManagementAdapter imaa;
    private Context context;
    public String[] uplods;
    public int user_id;
    public String date_pur ="";
    public String date_exp ="";

    public String key="";

    public int request;
    public String type = "";
    public ArrayList<String> li = new ArrayList<String>();




    public int cat_id =0;
    public int sel_id =0;

    public ImageManagementAdapter(Context context,int uid,ImageManagementActivity ima){
        this.ima = ima;
        this.user_id = uid;
        this.context = context;
        getImageData();
    }

    public ImageManagementAdapter(Context context,int uid,String type,ImageManagementActivity ima,String date_pur,String date_exp,int cat_id,int sel_id,String key){
        this.ima = ima;
        this.user_id = uid;
        this.context = context;
        this.type = type;
        this.date_pur = date_pur;
        this.date_exp = date_exp;
        this.cat_id = cat_id;
        this.sel_id = sel_id;
        this.key = key;
        getSortDate();
    }

    private void getImageData() {
        request = 0;
        imaa = this;
        type = "sort";
        SyncThread st = new SyncThread(imaa);
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
        ima.uploads_li = li;

        mvh.upm_title.setText(list_item[1]);
        mvh.upm_date.setText(list_item[3]);
        mvh.upm_cat.setText(list_item[4]);
        mvh.upm_sel.setText(list_item[5]);

        BitmapAsync ba = new BitmapAsync(mvh.upm_img,ima);
        ba.execute(list_item[2]);

        return row;
    }


    public void setProgressBarVisible(){
        ima.im_list_content.setVisibility(View.GONE);
        ima.im_msg_content.setVisibility(View.GONE);
        ima.im_loading_content.setVisibility(View.VISIBLE);
    }
    public void setProgressBarGone(){
        ima.im_loading_content.setVisibility(View.GONE);
    }

    public void stringErrorMsg(String s) {
        ima.stringErrorMsg(s);
    }

    public void JSONResponse(String s) {

        try {
            JSONArray ar = new JSONArray(s);
            JSONObject jo;
            String box_row;

            if(ar.length()>0){

                for (int i = 0; i < ar.length(); i++) {
                    jo = ar.getJSONObject(i);
                    box_row = jo.getString("id") + "%" + jo.getString("title") + "%" + jo.getString("url") + "%" + jo.getString("date") + "%" + jo.getString("cat") + "%" + jo.getString("sel")+"%"+jo.getString("state");
                    li.add(box_row);
                    Log.d("--MSG--", box_row);
                }

                ima.im_msg_content.setVisibility(View.GONE);

                ima.im_list_content.setVisibility(View.VISIBLE);


            }else{

                stringResponse("No item found.");
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void stringResponse(String s) {
        ima.stringResponse(s);
    }

    public void getSortDate() {
        //requestType =0;
        imaa = this;
        SyncThread st = new SyncThread(imaa);
        st.execute();
    }
}

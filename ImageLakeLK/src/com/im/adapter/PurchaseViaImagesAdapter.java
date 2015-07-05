package com.im.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.im.ImageLakeLK.PurchaseViaImagesActivity;
import com.im.ImageLakeLK.PurchaseViaPackagesActivity;
import com.im.ImageLakeLK.R;
import com.im.sync.SyncThread;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by RLN on 4/30/2015.
 */
public class PurchaseViaImagesAdapter extends BaseAdapter {

    private Context context;
    public String[] lists;

    PurchaseViaImagesActivity pvia;
    PurchaseViaImagesAdapter pviaa;

    public int requestType = 0;
    public int user_id;
    public String type;
    public ArrayList<String> li = new ArrayList<String>();


    public String uid="";
    public String date_pur ="";
    public String date_exp ="";
    public int cat_id;
    public int buy_id;
    public int sel_id;


    public PurchaseViaImagesAdapter(Context context,int user_id,String type,PurchaseViaImagesActivity pvia){
        this.context = context;
        this.user_id = user_id;
        this.type = type;
        this.pvia = pvia;
        getViaData();
    }

    public PurchaseViaImagesAdapter(Context context,int user_id,String type,PurchaseViaImagesActivity pvia,String uid,String date_pur,String date_exp,int cat_id,int buy_id,int sel_id){
        this.context = context;
        this.user_id = user_id;
        this.type = type;
        this.uid =uid;
        this.pvia = pvia;
        this.date_pur = date_pur;
        this.date_exp = date_exp;
        this.cat_id = cat_id;
        this.buy_id = buy_id;
        this.sel_id = sel_id;
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
            row = inflater.inflate(R.layout.purchasemanagementimage_row,parent,false);
        }else{

            row = convertView;
        }

        String list_items = li.get(position);
        pvia.iv_li = li;

        String[] list_item = list_items.split("/");

        TextView viaimg_title,viaimg_cate,viaimg_date,viaimg_crd,viaimg_buy,viaimg_sel;

        viaimg_title =(TextView)row.findViewById(R.id.viaimg_title);
        viaimg_title.setText(list_item[1]);

        viaimg_crd =(TextView)row.findViewById(R.id.viaimg_crd);
        viaimg_crd.setText(list_item[2]);

        viaimg_cate =(TextView)row.findViewById(R.id.viaimg_cat);
        viaimg_cate.setText(list_item[3]);

        viaimg_date =(TextView)row.findViewById(R.id.viaimg_date);
        viaimg_date.setText(list_item[4]);

        viaimg_buy =(TextView)row.findViewById(R.id.viaimg_buy);
        viaimg_buy.setText(list_item[5]);

        viaimg_sel =(TextView)row.findViewById(R.id.viaimg_sel);
        viaimg_sel.setText(list_item[6]);

        return row;
    }

    public void getViaData(){
        requestType =0;
        pviaa = this;

        SyncThread st = new SyncThread(pviaa);
        st.execute();

    }


    public void setProgressBarVisible(){
        pvia.viaimg_content.setVisibility(View.GONE);
        pvia.viaimg_msg_content.setVisibility(View.GONE);
        pvia.viaimg_load_content.setVisibility(View.VISIBLE);
    }
    public void setProgressBarGone(){
        pvia.viaimg_load_content.setVisibility(View.GONE);
    }

    public void stringErrorMsg(String s) {
        pvia.stringErrorMsg(s);
    }

    public void JSONResponse(String s) {

        try {
            JSONArray ar = new JSONArray(s);
            JSONObject jo;
            String box_row;

            if(ar.length()>0) {
                for (int i = 0; i < ar.length(); i++) {
                    jo = ar.getJSONObject(i);
                    box_row = jo.getString("cid") //0
                            + "/" + jo.getString("title")//1
                            + "/" + jo.getString("credit") //2
                            + "/" + jo.getString("category") //3
                            + "/" + jo.getString("date")//4
                            + "/" + jo.getString("buyer")//5
                            + "/" + jo.getString("seller");//6

                    li.add(box_row);
                    Log.d("--MSG--", box_row);
                }

                pvia.viaimg_content.setVisibility(View.VISIBLE);
                pvia.viaimg_msg_content.setVisibility(View.GONE);

            }else{

                stringResponse("No item found.");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void stringResponse(String s) {
        pvia.stringResponse(s);
    }

    public void getSortDate() {
        requestType =0;
        pviaa = this;
        SyncThread st = new SyncThread(pviaa);
        st.execute();
    }
}

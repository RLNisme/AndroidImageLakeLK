package com.im.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.im.ImageLakeLK.CreditSizeActivity;
import com.im.ImageLakeLK.R;
import com.im.sync.SyncThread;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by RLN on 5/13/2015.
 */
public class CreditSizeAdapter extends BaseAdapter {
    Context context;
    CreditSizeActivity csa;
    CreditSizeAdapter csaa;
    SyncThread st;
    public String type;
    public String[] users;
    public ArrayList<String> li = new ArrayList<String>();

    public CreditSizeAdapter(Context context,String type,CreditSizeActivity csa){
        this.context = context;
        this.type = type;
        this.csa = csa;
        loadCreditSize();
    }

    public void loadCreditSize(){
        csaa = this;
        st = new SyncThread(csaa);
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
            row = inflater.inflate(R.layout.crdsize_row,parent,false);
        }else{

            row = convertView;
        }

        String list_items = li.get(position);

        csa.us_li = li;

        String[] list_item = list_items.split("/");

        TextView user_em,user_fn,user_ln,user_id,user_state;
        ImageView ico =(ImageView)row.findViewById(R.id.users_ico);

        user_em =(TextView)row.findViewById(R.id.user_em);
        user_em.setText(list_item[2]);

        user_id =(TextView)row.findViewById(R.id.user_id);
        user_id.setText(list_item[1]);

        user_fn =(TextView)row.findViewById(R.id.user_fn);
        user_fn.setText(list_item[3]);

        user_ln =(TextView)row.findViewById(R.id.user_ln);
        user_ln.setText(list_item[4]);

        user_state =(TextView)row.findViewById(R.id.user_state);
        if(Integer.parseInt((list_item[5]))== 1){
            user_state.setText("Active");
            user_state.setBackgroundColor(csa.getResources().getColor(R.color.md_green_600));
            user_state.setTextColor(csa.getResources().getColor(R.color.md_text_white));
        }else if(Integer.parseInt((list_item[5]))== 2){
            user_state.setText("Inactive");
            user_state.setBackgroundColor(csa.getResources().getColor(R.color.md_red_300));
            user_state.setTextColor(csa.getResources().getColor(R.color.md_text_white));
        }


        return row;
    }

    public void stringErrorMsg(String s) {
        csa.stringErrorMsg(s);
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
                            + "/" + jo.getString("height")//4
                            + "/" + jo.getString("state") //5
                            ;
                    li.add(box_row);
                    Log.d("--MSG--", box_row);
                }

                csa.crdsize_list_content.setVisibility(View.VISIBLE);
                csa.crdsize_msg_content.setVisibility(View.GONE);

            }else{

                stringResponse("No item found.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stringResponse(String s) {
        csa.stringResponse(s);
    }

    public void setProgressBarVisible(){
        csa.crdsize_msg_content.setVisibility(View.GONE);
        csa.crdsize_list_content.setVisibility(View.GONE);
        csa.crdsize_loading_content.setVisibility(View.VISIBLE);
    }
    public void setProgressBarGone(){
        csa.crdsize_loading_content.setVisibility(View.GONE);
    }
}

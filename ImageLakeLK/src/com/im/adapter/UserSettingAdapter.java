package com.im.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.im.ImageLakeLK.R;
import com.im.fragments.UserSettingFragment;
import com.im.sync.SyncThread;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by RLN on 5/4/2015.
 */
public class UserSettingAdapter extends BaseAdapter {
    private Context context;
    UserSettingAdapter ustaa;
    UserSettingFragment usf;
    public int requestType = 0;
    public int user_id;
    public String type;
    public int state;
    public String uid="";
    public String date = "";
    public boolean cus,con;
    LayoutInflater in;
    public ArrayList<String> li = new ArrayList<String>();

    public UserSettingAdapter(Context context,int user_id,String type,UserSettingFragment usf,LayoutInflater in){
        this.context = context;
        this.user_id = user_id;
        this.type = type;
        this.usf = usf;
        this.in = in;
        getUserList();
    }

    public void getUserList(){
        requestType =0;
        ustaa = this;
        SyncThread st = new SyncThread(ustaa);
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
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = null;

        if(convertView == null){
            //LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = in.inflate(R.layout.usersetting_row,parent,false);
        }else{

            row = convertView;
        }

        String list_items = li.get(position);

        usf.us_li = li;

        String[] list_item = list_items.split("/");

        TextView user_em,user_type,user_un,user_state;
        ImageView ico =(ImageView)row.findViewById(R.id.uset_ico);

        user_em =(TextView)row.findViewById(R.id.uset_em);
        user_em.setText(list_item[2]);

        user_un =(TextView)row.findViewById(R.id.uset_un);
        user_un.setText(list_item[1]);

        user_type =(TextView)row.findViewById(R.id.uset_type);
        user_type.setText(list_item[5]);



        user_state =(TextView)row.findViewById(R.id.uset_state);
        if(Integer.parseInt((list_item[4]))== 1){
            user_state.setText("Active");
            user_state.setBackgroundColor(usf.getResources().getColor(R.color.md_green_600));
            user_state.setTextColor(usf.getResources().getColor(R.color.md_text_white));
        }else if(Integer.parseInt((list_item[4]))== 2){
            user_state.setText("Block");
            user_state.setBackgroundColor(usf.getResources().getColor(R.color.md_red_300));
            user_state.setTextColor(usf.getResources().getColor(R.color.md_text_white));
        }


        return row;
    }

    public void stringErrorMsg(String s) {
        usf.stringErrorMsg(s);
    }

    public void JSONResponse(String s) {

        try {
            JSONArray ar = new JSONArray(s);
            JSONObject jo;
            JSONArray us,pg;
            String box_row;


            if(ar.length()>0) {
                for (int i = 0; i < ar.length(); i++) {

                   if(i ==0){
                       us = ar.getJSONArray(i);

                       if(us.length()>0){
                           for (int j=0;j<us.length();j++){
                               jo = us.getJSONObject(j);
                               box_row = jo.getString("id") //0
                                       + "/" + jo.getString("user_name")//1
                                       + "/" + jo.getString("em")//2
                                       + "/" + jo.getString("phn") //3
                                       + "/" + jo.getString("state")//4
                                       +"/"+jo.getString("type")//5
                                       +"/"+jo.getString("type_id");//6
                               li.add(box_row);
                               Log.d("--MSG--", box_row);
                           }
                       }
                   }else if(i == 1){
                       jo=ar.getJSONObject(i);
                       if(Integer.parseInt(jo.getString("regstate"))== 2){
                           usf.uset_btn_addsub.setBackground(usf.getResources().getDrawable(R.drawable.green_buttons));
                           usf.uset_btn_addsub.setText(usf.getResources().getString(R.string.opn_add_subadmin));
                           usf.addSub = 2;
                       }else{
                           usf.uset_btn_addsub.setBackground(usf.getResources().getDrawable(R.drawable.red_buttons));
                           usf.uset_btn_addsub.setText(usf.getResources().getString(R.string.cls_add_subadmin));
                           usf.addSub = 1;
                       }

                   }else if(i == 2){
                       jo=ar.getJSONObject(i);
                       if(Integer.parseInt(jo.getString("upstate"))== 2){
                           usf.uset_btn_upsub.setBackground(usf.getResources().getDrawable(R.drawable.green_buttons));
                           usf.uset_btn_upsub.setText(usf.getResources().getString(R.string.opn_update_subadmin));
                           usf.updateSub = 2;
                       }else{
                           usf.uset_btn_upsub.setBackground(usf.getResources().getDrawable(R.drawable.red_buttons));
                           usf.uset_btn_upsub.setText(usf.getResources().getString(R.string.cls_update_subadmin));
                           usf.updateSub = 1;
                       }
                   }


                }



                usf.uset_list.setVisibility(View.VISIBLE);
                usf.uset_msg_content.setVisibility(View.GONE);

            }else{

                stringResponse("No item found.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stringResponse(String s) {
        usf.stringResponse(s);
    }

    public void setProgressBarVisible(){
        usf.uset_msg_content.setVisibility(View.GONE);
        usf.uset_list.setVisibility(View.GONE);
        usf.uset_load_content.setVisibility(View.VISIBLE);
    }
    public void setProgressBarGone(){
        usf.uset_load_content.setVisibility(View.GONE);
    }


}

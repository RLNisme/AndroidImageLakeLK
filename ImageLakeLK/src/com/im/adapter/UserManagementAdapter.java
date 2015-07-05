package com.im.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.im.ImageLakeLK.EarningManagementActivity;
import com.im.ImageLakeLK.R;
import com.im.ImageLakeLK.UserManagementActivity;
import com.im.sync.SyncThread;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by RLN on 4/27/2015.
 */
public class UserManagementAdapter extends BaseAdapter {

    private Context context;
    public String[] users;
    UserManagementActivity uma;
    UserManagementAdapter umaa;

    public int requestType = 0;

    public int user_id;
    public String type;
    public int state;
    public String uid="";
    public String date = "";
    public boolean cus,con;

    public ArrayList<String> li = new ArrayList<String>();

    public UserManagementAdapter(Context context,int user_id,String type,UserManagementActivity uma){
        this.context = context;
        this.user_id = user_id;
        this.type = type;
        this.uma = uma;
        getUserList();
    }

    public UserManagementAdapter(Context context,int user_id,String type,UserManagementActivity uma,String uid,String date,boolean cus,boolean con){
        this.context = context;
        this.user_id = user_id;
        this.type = type;
        this.uma = uma;
        this.uid = uid;
        this.date = date;
        this.cus = cus;
        this.con = con;
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

    public void getUserList(){
        requestType =0;
        umaa = this;
        SyncThread st = new SyncThread(umaa);
        st.execute();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = null;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.user_management_row,parent,false);
        }else{

            row = convertView;
        }

        String list_items = li.get(position);

        uma.us_li = li;

        String[] list_item = list_items.split("/");

        TextView user_em,user_fn,user_ln,user_id,user_phn,user_state;
        ImageView ico =(ImageView)row.findViewById(R.id.users_ico);

        user_em =(TextView)row.findViewById(R.id.user_em);
        user_em.setText(list_item[4]);

        user_id =(TextView)row.findViewById(R.id.user_id);
        user_id.setText(list_item[0]);

        user_fn =(TextView)row.findViewById(R.id.user_fn);
        user_fn.setText(list_item[2]);

        user_ln =(TextView)row.findViewById(R.id.user_ln);
        user_ln.setText(list_item[3]);

        user_phn =(TextView)row.findViewById(R.id.user_phn);


        user_state =(TextView)row.findViewById(R.id.user_state);
        if(Integer.parseInt((list_item[6]))== 1){
            user_state.setText("Active");
            user_state.setBackgroundColor(uma.getResources().getColor(R.color.md_green_600));
            user_state.setTextColor(uma.getResources().getColor(R.color.md_text_white));
        }else if(Integer.parseInt((list_item[6]))== 2){
            user_state.setText("Inactive");
            user_state.setBackgroundColor(uma.getResources().getColor(R.color.md_red_300));
            user_state.setTextColor(uma.getResources().getColor(R.color.md_text_white));
        }
        if(Integer.parseInt((list_item[7])) == 2) {

            ico.setBackgroundColor(uma.getResources().getColor(R.color.md_green_A100));
            user_phn.setText("Contributor");
        }else if(Integer.parseInt((list_item[7])) == 3){

            ico.setBackgroundColor(uma.getResources().getColor(R.color.md_pink_100));
            user_phn.setText("Customers");

        }

        return row;
    }

    public void stringErrorMsg(String s) {
        uma.stringErrorMsg(s);
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
                            + "/" + jo.getString("user_name")//1
                            + "/" + jo.getString("f_name") //2
                            + "/" + jo.getString("l_name") //3
                            + "/" + jo.getString("em")//4
                            + "/" + jo.getString("phn") //5
                            + "/" + jo.getString("state")//6
                            +"/"+jo.getString("type");//7
                    li.add(box_row);
                    Log.d("--MSG--", box_row);
                }

                uma.users_list_content.setVisibility(View.VISIBLE);
                uma.users_msg_content.setVisibility(View.GONE);

            }else{

                stringResponse("No item found.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stringResponse(String s) {
        uma.stringResponse(s);
    }

    public void setProgressBarVisible(){
        uma.users_msg_content.setVisibility(View.GONE);
        uma.users_list_content.setVisibility(View.GONE);
        uma.users_loading_content.setVisibility(View.VISIBLE);
    }
    public void setProgressBarGone(){
        uma.users_loading_content.setVisibility(View.GONE);
    }

    public void getSortDate() {
        requestType =0;
        umaa = this;
        SyncThread st = new SyncThread(umaa);
        st.execute();
    }
}

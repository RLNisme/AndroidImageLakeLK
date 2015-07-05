package com.im.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.im.ImageLakeLK.AccountActivity;
import com.im.ImageLakeLK.LightBoxActivity;
import com.im.ImageLakeLK.R;
import com.im.sync.SyncThread;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RLN on 4/3/2015.
 */
public class MyNavigationAdapter extends BaseAdapter {

    public Context context;

    public String[] privilages;
    public int[] imgID = {0,R.drawable.ic_action_view_as_grid,R.drawable.ic_action_labels,R.drawable.ic_action_edit,
                          R.drawable.ic_action_go_to_today,R.drawable.ic_action_new_account,R.drawable.ic_action_picture,
                          R.drawable.ic_action_new_picture,R.drawable.ic_action_cc_bcc,R.drawable.ic_action_paste,
                          R.drawable.ic_action_add_to_queue,R.drawable.ic_action_settings,R.drawable.ic_action_location_searching,R.drawable.ic_action_import_export};


    private MyNavigationAdapter mna;
    private AccountActivity aa;

    public int requestType = 0;
    public int user_type;


    public ArrayList<String> li = new ArrayList<String>();


    public MyNavigationAdapter(Context context){
        this.context=context;

        //privilages = context.getResources().getStringArray(R.array.planets);

    }

    public MyNavigationAdapter(Context context,int user_type,AccountActivity aa){
        this.context=context;
        this.aa = aa;
        this.user_type = user_type;
        Log.d("--MSG--","Inside the contructor");
        getPrivilages();


       // privilages = context.getResources().getStringArray(R.array.planets);


    }


    @Override
    public int getCount() {
        Log.d("--MSG--","Get Item");
        Log.d("--MSG--","GetCount_"+li.size());
        return li.size();
    }

    @Override
    public Object getItem(int position) {

        Log.d("--MSG--","GetItem_"+position);
        return privilages[position];
    }

    @Override
    public long getItemId(int position) {
        Log.d("--MSG--", "GetItemID_P " + position);

        String[] privilage = li.get(position).split("-");


        Log.d("--MSG--",privilage[0]);



        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("--MSG--","Inside the MyNavigationAdapter_"+position);
        View row = null;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.navigation_row,parent,false);
        }else{
            row = convertView;
        }

                String lit_item = li.get(position);
                String[] img_name = lit_item.split("-");

                //adding activity names to the main list
                aa.privilages_li.add(img_name[0]);

                TextView titleTextView = (TextView) row.findViewById(R.id.nav_title);
                titleTextView.setText(img_name[0]);


                ImageView imageView = (ImageView) row.findViewById(R.id.nav_draw_img);
                imageView.setImageResource(imgID[Integer.parseInt(img_name[1])]);

                Log.d("--MSG--",img_name[0]+"/"+img_name[1]);


        return row;
    }

    public void getPrivilages(){
        mna = this;

        SyncThread st = new SyncThread(mna);
        st.execute();


    }


    public void responseJson(String s){
        try {
            String[] result = s.split("/");

            JSONArray ja = new JSONArray(result[0]);
            JSONObject jo ;
            String sObject ;

           // li = new ArrayList<String>();
            for (int i =0;i<ja.length();i++){

                jo = ja.getJSONObject(i);
                sObject = jo.getString("interface")+"-"+jo.getInt("imgId");
                li.add(sObject);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

package com.im.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.im.ImageLakeLK.PurchaseViaPackagesActivity;
import com.im.ImageLakeLK.R;
import com.im.ImageLakeLK.UserManagementActivity;
import com.im.sync.SyncThread;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by RLN on 4/29/2015.
 */
public class PurchaseViaPackagesAdapter extends BaseAdapter {

    private Context context;
    public String[] lists;

    PurchaseViaPackagesActivity pvpa;
    PurchaseViaPackagesAdapter pvpaa;

    public int requestType = 0;
    public int user_id;
    public String type;
    public ArrayList<String> li = new ArrayList<String>();


    public String uid="";
    public String date_pur ="";
    public String date_exp ="";
    public int package_id;


    public PurchaseViaPackagesAdapter(Context context,int user_id,String type,PurchaseViaPackagesActivity pvpa){
        this.context = context;
        this.user_id = user_id;
        this.type = type;
        this.pvpa = pvpa;
        getViaData();
    }

    public PurchaseViaPackagesAdapter(Context context,int user_id,String type,PurchaseViaPackagesActivity pvpa,String uid,String date_pur,String date_exp,int package_id){
        this.context = context;
        this.user_id = user_id;
        this.type = type;
        this.pvpa = pvpa;
        this.uid = uid;
        this.date_pur = date_pur;
        this.date_exp = date_exp;
        this.package_id = package_id;
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
            row = inflater.inflate(R.layout.purchasemanagementpackage_row,parent,false);
        }else{

            row = convertView;
        }

        String list_items = li.get(position);
        pvpa.iv_li = li;

        String[] list_item = list_items.split("/");

        TextView viapac_pur,viapac_exp,viapac_state,viapac_income,viapac_un;

        viapac_pur =(TextView)row.findViewById(R.id.viapac_pur);
        viapac_pur.setText(list_item[0]);

        viapac_exp =(TextView)row.findViewById(R.id.viapac_exp);
        viapac_exp.setText(list_item[1]);

        viapac_un =(TextView)row.findViewById(R.id.viapac_em);
        viapac_un.setText(list_item[2]);

        viapac_state = (TextView)row.findViewById(R.id.viapac_state);
        if(Integer.parseInt(list_item[3])== 1){
            viapac_state.setText("Active");
            viapac_state.setBackgroundColor(pvpa.getResources().getColor(R.color.md_green_600));
            viapac_state.setTextColor(pvpa.getResources().getColor(R.color.md_text_white));
        }else if(Integer.parseInt(list_item[3])== 2){
            viapac_state.setText("Inactive");
            viapac_state.setBackgroundColor(pvpa.getResources().getColor(R.color.md_red_300));
            viapac_state.setTextColor(pvpa.getResources().getColor(R.color.md_text_white));
        }

        viapac_income =(TextView)row.findViewById(R.id.viapac_income);

        viapac_income.setText("$"+list_item[4]);

        return row;

    }


    public void getViaData(){
        requestType =0;
        pvpaa = this;

        SyncThread st = new SyncThread(pvpaa);
        st.execute();

    }

    public void setProgressBarVisible(){
        pvpa.viapac_content.setVisibility(View.GONE);
        pvpa.viapac_msg_content.setVisibility(View.GONE);
        pvpa.viapac_load_content.setVisibility(View.VISIBLE);
    }
    public void setProgressBarGone(){
        pvpa.viapac_load_content.setVisibility(View.GONE);
    }

    public void stringErrorMsg(String s) {
        pvpa.stringErrorMsg(s);
    }

    public void JSONResponse(String s) {

        try {
            JSONArray ar = new JSONArray(s);
            JSONObject jo;
            String box_row;

            if(ar.length()>0) {
                for (int i = 0; i < ar.length(); i++) {
                    jo = ar.getJSONObject(i);
                    box_row = jo.getString("pur_date") //0
                            + "/" + jo.getString("exp_date")//1
                            + "/" + jo.getString("un") //2
                            + "/" + jo.getString("state") //3
                            + "/" + jo.getString("income");//4

                    li.add(box_row);
                    Log.d("--MSG--", box_row);
                }

                pvpa.viapac_content.setVisibility(View.VISIBLE);
                pvpa.viapac_msg_content.setVisibility(View.GONE);

            }else{

                stringResponse("No item found.");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void stringResponse(String s) {
        pvpa.stringResponse(s);
    }

    public void getSortDate() {
        requestType =0;
        pvpaa = this;
        SyncThread st = new SyncThread(pvpaa);
        st.execute();
    }

}

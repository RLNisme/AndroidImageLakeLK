package com.im.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.im.ImageLakeLK.R;
import com.im.ImageLakeLK.SettingActivity;
import com.im.adapter.UserManagementAdapter;
import com.im.adapter.UserSettingAdapter;
import com.im.sync.SyncThread;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by RLN on 5/4/2015.
 */
public class UserSettingFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener{

    public Button uset_btn_addsub,uset_btn_upsub;
    public ListView uset_list;
    public RelativeLayout uset_load_content,uset_msg_content;
    ProgressBar uset_loading;
    TextView uset_msg;
    UserSettingFragment usf;
    UserSettingAdapter ustaa;
    LayoutInflater in;
    public ArrayList<String> us_li ;//lightbox list
    public String type = "";
    int user_id;
    Spinner uset_type;
    Button uset_can,uset_up;
    RadioButton uset_ac,uset_bk;
    RadioGroup rg;
    public ArrayList<String> types = new ArrayList<String>();
    public ArrayList<String> types_show = new ArrayList<String>();
    public int addSub,updateSub;

    int old_type ;
    int old_state;

    int new_type;
    public int new_state;
    public int newType;
    public int uid =0;

    ArrayAdapter<String> adapter;

    SettingActivity sa = new SettingActivity();
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.usersetting_layout,container,false);

        usf = this;
        in = inflater;

        uset_load_content =(RelativeLayout)v.findViewById(R.id.uset_load_content);
        uset_load_content.setVisibility(View.GONE);

        uset_msg_content =(RelativeLayout)v.findViewById(R.id.uset_msg_content);
        uset_msg_content.setVisibility(View.GONE);

        uset_loading =(ProgressBar)v.findViewById(R.id.uset_loading);

        uset_msg =(TextView)v.findViewById(R.id.uset_msg);

        uset_btn_addsub =(Button)v.findViewById(R.id.uset_btn_newsub);
        uset_btn_addsub.setOnClickListener(this);
        uset_btn_upsub =(Button)v.findViewById(R.id.uset_btn_upsub);
        uset_btn_upsub.setOnClickListener(this);

        uset_list =(ListView)v.findViewById(R.id.uset_list);
        uset_list.setVisibility(View.GONE);

        loadSubAdmins();

        loadUserType();

        return v;


    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.uset_btn_newsub){
            SyncThread st = null ;
            if(addSub>0) {
                if (addSub == 1) {
                    type = "closenewsub";
                    st = new SyncThread(usf);

                } else {
                    type = "addnewsub";
                    st = new SyncThread(usf);
                }
                st.execute();
            }else{
                Toast.makeText(getActivity(),"Error",Toast.LENGTH_LONG).show();
            }
        }else if(v.getId()==R.id.uset_btn_upsub){
            SyncThread st = null ;
            if(updateSub>0) {
                if (updateSub == 1) {
                    type = "closeeditsub";
                    st = new SyncThread(usf);

                } else {
                    type = "editsub";
                    st = new SyncThread(usf);
                }
                st.execute();
            }else{
                Toast.makeText(getActivity(),"Error",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void loadSubAdmins(){
        //request_type = 0;
        type ="all";
        us_li = new ArrayList<String>();
        uset_list.setVisibility(View.GONE);
        uset_msg_content.setVisibility(View.GONE);
        uset_load_content.setVisibility(View.VISIBLE);

        ustaa = new UserSettingAdapter(sa,user_id,type,usf,in);
        uset_list.setAdapter(ustaa);
        uset_list.setOnItemClickListener(this);

    }

    public void loadUserType(){
        type ="user_type_all";
        SyncThread st = new SyncThread(usf);
        st.execute();
    }

    public void stringErrorMsg(String s) {
        Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
    }

    public void stringResponse(String s) {
        if(s.equals("Us_Successful...")){
            loadSubAdmins();
        }else if(s.equals("addnewsub_Successful...")){
            uset_btn_addsub.setBackground(getResources().getDrawable(R.drawable.red_buttons));
            uset_btn_addsub.setText(getResources().getString(R.string.cls_add_subadmin));
            addSub = 1;
        }else if(s.equals("closenewsub_Successful...")){
            uset_btn_addsub.setBackground(getResources().getDrawable(R.drawable.green_buttons));
            uset_btn_addsub.setText(getResources().getString(R.string.opn_add_subadmin));
            addSub = 2;
        }else if(s.equals("editsub_Successful...")){
            uset_btn_upsub.setBackground(getResources().getDrawable(R.drawable.red_buttons));
            uset_btn_upsub.setText(getResources().getString(R.string.cls_update_subadmin));
            updateSub = 1;
        }else if(s.equals("closeeditsub_Successful...")){
            uset_btn_upsub.setBackground(getResources().getDrawable(R.drawable.green_buttons));
            uset_btn_upsub.setText(getResources().getString(R.string.opn_update_subadmin));
            updateSub = 2;
        }
        else{
            Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
        }
    }

    public void JSONResponse(String s) {

        try {
            JSONArray ar = new JSONArray(s);

            JSONObject jo;


            if(ar.length()>0) {

                for (int i = 0; i < ar.length(); i++) {



                        jo = ar.getJSONObject(i);
                        String box_row;
                        box_row = jo.getString("tid") //0
                                    + "/" + jo.getString("ttype");//1
                        types.add(box_row);
                        types_show.add(jo.getString("ttype"));
                        Log.d("--Category--", box_row);



                }




            }else{

                stringResponse("No item found.");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String user = us_li.get(position);
        String user_ar[] = user.split("/");

        Log.d("--MSG--",us_li.size()+"");
        Log.d("--MSG--",user);

        uid = Integer.parseInt(user_ar[0]);

        //Context context = sa;
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.usersetting_dialog);
        dialog.setTitle("Update");

        uset_type =(Spinner)dialog.findViewById(R.id.uset_admin_type);
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,types_show);
        uset_type.setAdapter(adapter);
        for (int i =0;i<types.size();i++){
            String typeall = types.get(i);
            String type_one[] = typeall.split("/");
            if(Integer.parseInt(user_ar[6])== Integer.parseInt(type_one[0])){
                uset_type.setSelection(i);
                old_type = i;
                new_type = i;
            }
        }
        uset_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new_type = position;
                String ty = types.get(position);
                String typ[] = ty.split("/");
                newType = Integer.parseInt(typ[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        rg =(RadioGroup)dialog.findViewById(R.id.uset_state_group);
        uset_ac =(RadioButton)dialog.findViewById(R.id.uset_active);
        uset_bk =(RadioButton)dialog.findViewById(R.id.uset_block);
        if(Integer.parseInt(user_ar[4])== 1){

            uset_ac.setChecked(true);
            old_state = 1;
            new_state = 1;
        }else if(Integer.parseInt(user_ar[4])== 2){
            uset_bk.setChecked(true);
            old_state = 2;
            new_state =2;
        }



        uset_can =(Button)dialog.findViewById(R.id.uset_cancel);
        uset_can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        uset_up =(Button)dialog.findViewById(R.id.uset_update);
        uset_up.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                if(uset_ac.isChecked()){
                    new_state = 1;
                }else if(uset_bk.isChecked()){
                    new_state = 2;
                }

                if(old_state == new_state && old_type == new_type){
                    Toast.makeText(getActivity(),"Same details.",Toast.LENGTH_LONG).show();
                }else{
                    Log.d("--MSG--","OLD_STATE:"+old_state);
                    Log.d("--MSG--","NEW_STATE:"+new_state);
                    Log.d("--MSG--","OLD_TYPE:"+old_type);
                    Log.d("--MSG--","NEW_TYPE:"+new_type);
                    type = "update";
                    SyncThread st = new SyncThread(usf);
                    st.execute();

                    dialog.dismiss();
                }

            }
        });

        dialog.show();

    }
}

package com.im.ImageLakeLK;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.im.adapter.EarningManagementAdapter;
import com.im.adapter.UserManagementAdapter;
import com.im.sync.SyncThread;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by RLN on 4/27/2015.
 */
public class UserManagementActivity extends Activity implements AdapterView.OnItemClickListener,View.OnClickListener{

    public UserManagementAdapter umaa;
    public UserManagementActivity uma;
    public LinearLayout users_list_content,users_msg_content,users_loading_content;
    Button users_btn_refresh;
    ImageButton uers_sort;
    ListView users_list;
    public ProgressBar pb;
    TextView msg;
    EditText urs_date,usr_uid;
    CheckBox usr_cus,usr_con;
    public ArrayList<String> us_li ;//lightbox list
    public int user_id;
    public int request_type;
    public String type ="";

    Button usr_cancel,usr_sort;
    public String uid="";
    public String date ="";
    public boolean cus = true,con = true;

    public String uuid="";
    public int state;
    RadioGroup rg;
    RadioButton ac,inac;
    Button um_cancel,um_update;

    DateFormat df = DateFormat.getDateInstance();
    Calendar cl = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_management_activity);

        uma = this;

        users_list_content = (LinearLayout)findViewById(R.id.users_list_content);
        users_list_content.setVisibility(View.GONE);

        users_list =(ListView)findViewById(R.id.users_list);

        users_msg_content =(LinearLayout)findViewById(R.id.users_msg_content);
        users_msg_content.setVisibility(View.GONE);

        msg =(TextView)findViewById(R.id.users_msg);
        users_btn_refresh =(Button)findViewById(R.id.users_btn_refresh);

        users_loading_content =(LinearLayout)findViewById(R.id.users_load_content);
        users_loading_content.setVisibility(View.GONE);

        pb =(ProgressBar)findViewById(R.id.users_loading);

        uers_sort =(ImageButton)findViewById(R.id.users_sort);
        uers_sort.setOnClickListener(this);



        loadUsers();


    }

    public void loadUsers(){
        request_type = 0;
        type ="all";
        us_li = new ArrayList<String>();
        users_list_content.setVisibility(View.GONE);
        users_msg_content.setVisibility(View.GONE);
        users_loading_content.setVisibility(View.VISIBLE);

        umaa = new UserManagementAdapter(this,user_id,type,uma);
        users_list.setAdapter(umaa);
        users_list.setOnItemClickListener(this);

    }

    public void stringErrorMsg(String s) {
        users_list_content.setVisibility(View.GONE);
        users_msg_content.setVisibility(View.VISIBLE);
        users_loading_content.setVisibility(View.GONE);
        msg.setText(s);
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }


    public void stringResponse(String s) {
        if(s.equals("Successful...")){
            sort();
        }else{
            users_list_content.setVisibility(View.GONE);
            users_msg_content.setVisibility(View.VISIBLE);
            users_loading_content.setVisibility(View.GONE);
            msg.setText(s);
            Toast.makeText(this,s,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String user = us_li.get(position);
        Log.d("--MSG--",user);
        String details[] = user.split("/");

        uuid = details[0];
        Log.d("--MSG--",uuid+"");

        state = Integer.parseInt(details[6]);
        Log.d("--MSG--",state+"");

        Context context = this;
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.user_management_state_change_dialog);
        dialog.setTitle("Update user");

        rg =(RadioGroup)dialog.findViewById(R.id.um_state);

        ac =(RadioButton)dialog.findViewById(R.id.um_ac);

        inac =(RadioButton)dialog.findViewById(R.id.um_inac);

        if(state == 1){
            ac.setChecked(true);
        }else if(state == 2){
            inac.setChecked(true);
        }

        um_cancel =(Button)dialog.findViewById(R.id.um_cancel);
        um_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        um_update =(Button)dialog.findViewById(R.id.um_update);
        um_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                type = "update";

                if(ac.isChecked()){
                    state = 1;
                }
                else if(inac.isChecked()){
                    state = 2 ;
                }
                SyncThread st = new SyncThread(uma);
                st.execute();

                dialog.dismiss();
            }
        });

        dialog.show();

    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            cl.set(Calendar.YEAR,year);
            cl.set(Calendar.MONTH,monthOfYear);
            cl.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            updateDate();
        }
    };

    private void updateDate() {

        date = df.format(cl.getTime());
        String day[] = date.split(", ");
        String nano_day[] = day[0].split(" ");
        Log.d("--MSG--", date);
        Log.d("--MSG--",day[0]);
        Log.d("--MSG--",day[1]);

        Log.d("--MSG--",nano_day[0]);
        Log.d("--MSG--",nano_day[1]);

        if(nano_day[0].equals("Jan")){
            nano_day[0] = "01";
        }else if(nano_day[0].equals("Feb")){
            nano_day[0] = "02";
        }else if(nano_day[0].equals("Mar")){
            nano_day[0] = "03";
        }else if(nano_day[0].equals("Apr")){
            nano_day[0] = "04";
        }else if(nano_day[0].equals("May")){
            nano_day[0] = "05";
        }else if(nano_day[0].equals("Jun")){
            nano_day[0] = "06";
        }else if(nano_day[0].equals("Jul")){
            nano_day[0] = "07";
        }else if(nano_day[0].equals("Aug")){
            nano_day[0] = "08";
        }else if(nano_day[0].equals("Sep")){
            nano_day[0] = "09";
        }else if(nano_day[0].equals("Oct")){
            nano_day[0] = "10";
        }else if(nano_day[0].equals("Nov")){
            nano_day[0] = "11";
        }else if(nano_day[0].equals("Dec")){
            nano_day[0] = "12";
        }
        date = nano_day[1]+"-"+nano_day[0]+"-"+day[1];
        urs_date.setText(date);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.users_sort){
            Context context = this;
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.usermanagement_dialog);
            dialog.setTitle("Sort By");

            usr_uid =(EditText)dialog.findViewById(R.id.usr_uid);
            usr_uid.setText(uid);

            urs_date =(EditText)dialog.findViewById(R.id.usr_date);
            urs_date.setText(date);
            urs_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setDate();
                }
            });

            usr_cus =(CheckBox)dialog.findViewById(R.id.usr_cus);
            if(cus == true){
                usr_cus.setChecked(true);
            }
            usr_con =(CheckBox)dialog.findViewById(R.id.usr_con);
            if(con == true){
                usr_con.setChecked(true);
            }



            usr_cancel =(Button)dialog.findViewById(R.id.usr_cancel);
            usr_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            usr_sort =(Button)dialog.findViewById(R.id.usr_sort);
            usr_sort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uid = usr_uid.getText().toString();
                    date = urs_date.getText().toString();
                    if(usr_cus.isChecked()){
                        cus = true;
                    }else{
                        cus = false;
                    }
                    if(usr_con.isChecked()){
                        con = true;
                    }else{
                        con = false;
                    }
                    sort();
                    dialog.dismiss();
                }
            });


            dialog.show();
        }
    }

    private void sort() {
        request_type = 0;
        type ="sort";
        us_li = new ArrayList<String>();
        umaa = new UserManagementAdapter(this,user_id,type,uma,uid,date,cus,con);
        users_list.setAdapter(umaa);
        users_list.setOnItemClickListener(this);
    }

    private void setDate() {
        new DatePickerDialog(this,d,cl.get(Calendar.YEAR),cl.get(Calendar.MONTH),cl.get(Calendar.DAY_OF_MONTH)).show();
    }
}

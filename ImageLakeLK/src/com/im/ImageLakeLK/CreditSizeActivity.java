package com.im.ImageLakeLK;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.im.adapter.CreditSizeAdapter;
import com.im.adapter.UserManagementAdapter;
import com.im.sync.SyncThread;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by RLN on 5/13/2015.
 */
public class CreditSizeActivity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private static final String LOAD_ALL ="AllCreditSize";
    private static final String ADD_CREDSIZE ="AddCreditSize";
    private static final String UPDATE_CREDSIZE ="UpdateCreditSize";

    CreditSizeActivity csa;
    CreditSizeAdapter csaa;
    SyncThread st;
    public LinearLayout crdsize_list_content,crdsize_msg_content,crdsize_loading_content;
    Button crdsize_btn_refresh;
    ImageButton crdsize_sort;
    ListView crdsize_list;
    public ProgressBar pb;
    TextView msg;
    public String type ="";
    public ArrayList<String> us_li ;//lightbox list
    ArrayAdapter<String> adapter;

    TextView title1,title2,title3,title4,title5,ti28;
    EditText edit1,edit2,edit3,edit4;
    Button btn1,btn2;
    Spinner spin5;
    //--------------
    public int credit =1;
    public String size ="";
    public int width = 960;
    public int height = 540;

    //---------
    //--------------
    public int upid = 0;
    public int upcredit =1;
    public String upsize ="";
    public int upwidth = 960;
    public int upheight = 540;
    public int upstate = 0;
    //---------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creditsize_activity);

        csa = this;

        getActionBar().setTitle("Credit & Size");

        crdsize_list_content = (LinearLayout)findViewById(R.id.crdsize_list_content);
        crdsize_list_content.setVisibility(View.GONE);

        crdsize_list =(ListView)findViewById(R.id.crdsize_list);

        crdsize_msg_content =(LinearLayout)findViewById(R.id.crdsize_msg_content);
        crdsize_msg_content.setVisibility(View.GONE);

        msg =(TextView)findViewById(R.id.crdsize_msg);
        crdsize_btn_refresh =(Button)findViewById(R.id.crdsize_btn_refresh);

        crdsize_loading_content =(LinearLayout)findViewById(R.id.crdsize_load_content);
        crdsize_loading_content.setVisibility(View.GONE);

        pb =(ProgressBar)findViewById(R.id.crdsize_loading);

        crdsize_sort =(ImageButton)findViewById(R.id.crdsize_sort);
        crdsize_sort.setOnClickListener(this);
        loadCredSizeList();
    }

    public void loadCredSizeList(){
        type = LOAD_ALL;

        us_li = new ArrayList<String>();
        crdsize_list_content.setVisibility(View.GONE);
        crdsize_msg_content.setVisibility(View.GONE);
        crdsize_loading_content.setVisibility(View.VISIBLE);

        csaa = new CreditSizeAdapter(this,type,csa);
        crdsize_list.setAdapter(csaa);
        crdsize_list.setOnItemClickListener(this);

    }
    
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.crdsize_sort){
            Context context = this;
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.creditandcreditpack);
            dialog.setTitle("New Credit");

            title1 = (TextView)dialog.findViewById(R.id.title1);
            title1.setText("Credits");

            title2 = (TextView)dialog.findViewById(R.id.title2);
            title2.setText("Size");

            title3 = (TextView)dialog.findViewById(R.id.title3);
            title3.setText("Width");

            title4 = (TextView)dialog.findViewById(R.id.title4);
            title4.setText("Height");

            title5 =(TextView)dialog.findViewById(R.id.title5);
            title5.setVisibility(View.GONE);

            edit1 =(EditText)dialog.findViewById(R.id.edit1);
            edit1.setInputType(0x00000002);
            edit1.setText(credit+"");

            edit2 =(EditText)dialog.findViewById(R.id.edit2);
            edit2.setInputType(0x00000001);
            edit2.setText(size);

            edit3 =(EditText)dialog.findViewById(R.id.edit3);
            edit3.setInputType(0x00000002);
            edit3.setText(width+"");

            edit4 =(EditText)dialog.findViewById(R.id.edit4);
            edit4.setInputType(0x00000002);
            edit4.setText(height+"");

            spin5 =(Spinner)dialog.findViewById(R.id.spin5);
            spin5.setVisibility(View.GONE);

            btn1 =(Button)dialog.findViewById(R.id.btn1);
            btn1.setText("Cancel");
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            btn2 =(Button)dialog.findViewById(R.id.btn2);
            btn2.setText("Add");
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    if(edit1.getText().toString().trim().equals("") || edit2.getText().toString().trim().equals("") ||
                            edit3.getText().toString().trim().equals("") ||edit4.getText().toString().trim().equals("")){
                        Toast.makeText(CreditSizeActivity.this,"Please check details",Toast.LENGTH_LONG).show();
                    }else{
                        credit = Integer.parseInt(edit1.getText().toString());
                        size =  edit2.getText().toString().trim();
                        width = Integer.parseInt(edit3.getText().toString());
                        height = Integer.parseInt(edit4.getText().toString());

                        if(credit <= 0){
                            Toast.makeText(CreditSizeActivity.this,"Credit must be at least 1",Toast.LENGTH_LONG).show();
                        }else if(size.equals("")){
                            Toast.makeText(CreditSizeActivity.this,"Size name can not be null",Toast.LENGTH_LONG).show();
                        }else if(width <960){
                            Toast.makeText(CreditSizeActivity.this,"Width must be more than 960px",Toast.LENGTH_LONG).show();
                        }else if(height <540){
                            Toast.makeText(CreditSizeActivity.this,"Height must be more than 540px",Toast.LENGTH_LONG).show();
                        }else{

                            type = ADD_CREDSIZE;
                            st = new SyncThread(csa);
                            st.execute();
                            dialog.dismiss();
                        }
                    }
                }
            });
            dialog.show();
        }
    }

    public void stringErrorMsg(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    public void JSONResponse(String s) {
    }

    public void stringResponse(String s) {
        if(s.equals("CrdSize_Successfully...")) {
            credit = 1;
            size = "";
            width = 960;
            height = 540;
            loadCredSizeList();
            Toast.makeText(this, "Successfully added.", Toast.LENGTH_LONG).show();
        }else if(s.equals("Update_CrdSize_Successfully...")){
            upcredit = 1;
            upsize ="";
            upheight = 540;
            upwidth = 960;
            upstate = 0;
            loadCredSizeList();
            Toast.makeText(this, "Successfully updated.", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String g = us_li.get(position);
        String gg[]=g.split("/");

        upid = Integer.parseInt(gg[0]);
        upcredit = Integer.parseInt(gg[1]);
        upsize = gg[2];
        upwidth = Integer.parseInt(gg[3]);
        upheight = Integer.parseInt(gg[4]);
        upstate = Integer.parseInt(gg[5]);

        Context context = this;
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.creditandcreditpack);
        dialog.setTitle("Update credit & size");

        title1 = (TextView)dialog.findViewById(R.id.title1);
        title1.setText("Credits");

        title2 = (TextView)dialog.findViewById(R.id.title2);
        title2.setText("Size");

        title3 = (TextView)dialog.findViewById(R.id.title3);
        title3.setText("Width");

        title4 = (TextView)dialog.findViewById(R.id.title4);
        title4.setText("Height");

        title5 =(TextView)dialog.findViewById(R.id.title5);
        title5.setVisibility(View.VISIBLE);
        title5.setText("State");

        edit1 =(EditText)dialog.findViewById(R.id.edit1);
        edit1.setInputType(0x00000002);
        edit1.setText(upcredit+"");

        edit2 =(EditText)dialog.findViewById(R.id.edit2);
        edit2.setInputType(0x00000001);
        edit2.setText(upsize);

        edit3 =(EditText)dialog.findViewById(R.id.edit3);
        edit3.setInputType(0x00000002);
        edit3.setText(upwidth+"");

        edit4 =(EditText)dialog.findViewById(R.id.edit4);
        edit4.setInputType(0x00000002);
        edit4.setText(upheight+"");

        spin5 =(Spinner)dialog.findViewById(R.id.spin5);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.state));
        spin5.setAdapter(adapter);
        if(upstate == 1) {
            spin5.setSelection(0);
        }else if(upstate == 2){
            spin5.setSelection(1);
        }
        spin5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0){
                    upstate = 1;
                }else if(position == 1){
                    upstate = 2;
                }
                //Toast.makeText(CreditSizeActivity.this,upstate+"",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn1 =(Button)dialog.findViewById(R.id.btn1);
        btn1.setText("Cancel");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn2 =(Button)dialog.findViewById(R.id.btn2);
        btn2.setText("Add");
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(edit1.getText().toString().trim().equals("") || edit2.getText().toString().trim().equals("") ||
                        edit3.getText().toString().trim().equals("") ||edit4.getText().toString().trim().equals("")){
                    Toast.makeText(CreditSizeActivity.this,"Please check details",Toast.LENGTH_LONG).show();
                }else{
                    upcredit = Integer.parseInt(edit1.getText().toString());
                    upsize =  edit2.getText().toString().trim();
                    upwidth = Integer.parseInt(edit3.getText().toString());
                    upheight = Integer.parseInt(edit4.getText().toString());

                    if(upcredit <= 0){
                        Toast.makeText(CreditSizeActivity.this,"Credit must be at least 1",Toast.LENGTH_LONG).show();
                    }else if(upsize.equals("")){
                        Toast.makeText(CreditSizeActivity.this,"Size name can not be null",Toast.LENGTH_LONG).show();
                    }else if(upwidth <960){
                        Toast.makeText(CreditSizeActivity.this,"Width must be more than 960px",Toast.LENGTH_LONG).show();
                    }else if(upheight <540){
                        Toast.makeText(CreditSizeActivity.this,"Height must be more than 540px",Toast.LENGTH_LONG).show();
                    }else{

                        type = UPDATE_CREDSIZE;
                        st = new SyncThread(csa);
                        st.execute();
                        dialog.dismiss();
                    }
                }
            }
        });
        dialog.show();
    }
}

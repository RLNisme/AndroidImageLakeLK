package com.im.ImageLakeLK;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.im.adapter.ImageSearchAdapter;
import com.im.adapter.UserManagementAdapter;
import com.im.sync.SyncThread;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by RLN on 5/16/2015.
 */
public class ImageSearchActivity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener{

    public static final String LOAD_ALL = "AllImages";
    public static final String SEARCH_IMAGE = "SearchImages";


    Intent i;
    ImageSearchActivity imsa;
    ImageSearchAdapter imsaa;
    SyncThread st;
    public LinearLayout is_list_content,is_msg_content,is_loading_content,is_box;
    Button is_btn_refresh;
    public ImageButton is_left,is_right;
    public ListView is_list;
    public ProgressBar pb;
    public TextView msg,pageno;
    public AutoCompleteTextView is_key;
    Button is_sch,btn_cancel,btn_sort;
    Spinner spin_cat,spin_sel,spin_siz;
    EditText cfrom,cto;

    public int user_id;
    public int user_type;

    public String type ="";
    public String key ="";
    public int catid = 0;
    public int sid = 0;
    public String sizid = "";
    public int from =0;
    public int to = 0;

    String g[];

    int cid = 0,ssid=0,szid=0;

    public ArrayList<String> us_li ;//lightbox list
    public ArrayAdapter<String> adapter;


    public ArrayList<String> kli;
    public ArrayList<String> kli_show;

    public ArrayList<String> cli;
    public ArrayList<String> cli_show;

    public ArrayList<String> sli;
    public ArrayList<String> sli_show;

    public ArrayList<String> szli;
    public ArrayList<String> szli_show;

    public int noOfPages = 0,currentPage = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagesearch_activity);

        imsa = this;

        i = getIntent();
        user_id = i.getIntExtra("uid",-1);
        user_type = i.getIntExtra("ut",-1);

        is_list_content = (LinearLayout)findViewById(R.id.is_list_content);
        is_list_content.setVisibility(View.GONE);

        is_box = (LinearLayout)findViewById(R.id.is_box);
        is_box.setVisibility(View.VISIBLE);

        is_key =(AutoCompleteTextView)findViewById(R.id.is_key);
        is_sch =(Button)findViewById(R.id.is_search);
        is_sch.setOnClickListener(this);

        is_list =(ListView)findViewById(R.id.is_list);

        is_msg_content =(LinearLayout)findViewById(R.id.is_msg_content);
        is_msg_content.setVisibility(View.GONE);

        msg =(TextView)findViewById(R.id.is_msg);
        is_btn_refresh =(Button)findViewById(R.id.is_btn_refresh);

        is_loading_content =(LinearLayout)findViewById(R.id.is_load_content);
        is_loading_content.setVisibility(View.GONE);

        pb =(ProgressBar)findViewById(R.id.is_loading);

        is_left =(ImageButton)findViewById(R.id.is_left);
        is_left.setVisibility(View.GONE);
        is_left.setOnClickListener(this);

        is_right =(ImageButton)findViewById(R.id.is_right);
        is_right.setVisibility(View.GONE);
        is_right.setOnClickListener(this);

        pageno = (TextView)findViewById(R.id.pageno);
        pageno.setText(currentPage+"");

        loadImages();
        loadSearchs();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("--MSG--",position+"");
        //  Intent i = new Intent(ImageSearchActivity.this,ImageDetailActivity.class);
        // startActivity(i);
    }

    public void loadImages(){
        type = LOAD_ALL;

        us_li = new ArrayList<String>();
        is_list_content.setVisibility(View.GONE);
        is_msg_content.setVisibility(View.GONE);
        is_loading_content.setVisibility(View.VISIBLE);

        imsaa = new ImageSearchAdapter(this,type,imsa);
        is_list.setAdapter(imsaa);

            is_list.setOnItemClickListener(this);



    }

    public void loadSearchs(){
        st = new SyncThread(imsa);
        st.execute();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.is_right){
            currentPage = currentPage + 1;
            us_li = new ArrayList<String>();
            is_list_content.setVisibility(View.GONE);
            is_msg_content.setVisibility(View.GONE);
            is_loading_content.setVisibility(View.VISIBLE);

            imsaa = new ImageSearchAdapter(this,type,imsa,currentPage);
            is_list.setAdapter(imsaa);
            is_list.setOnItemClickListener(this);
        }else  if(v.getId() == R.id.is_left){
            currentPage = currentPage - 1;
            us_li = new ArrayList<String>();
            is_list_content.setVisibility(View.GONE);
            is_msg_content.setVisibility(View.GONE);
            is_loading_content.setVisibility(View.VISIBLE);

            imsaa = new ImageSearchAdapter(this,type,imsa,currentPage);
            is_list.setAdapter(imsaa);
            is_list.setOnItemClickListener(this);
        }else if(v.getId() == R.id.is_search){
            type = SEARCH_IMAGE;
            currentPage = 1;

            g = is_key.getText().toString().trim().split(" ");
            key = "";
            for(int i =0 ; i<g.length;i++){
                if(i == (g.length -1)) {
                    key += g[i];
                }else{
                    key += g[i]+"-";
                }
            }

            Log.d("--MSG--",key);
            us_li = new ArrayList<String>();
            is_list_content.setVisibility(View.GONE);
            is_msg_content.setVisibility(View.GONE);
            is_loading_content.setVisibility(View.VISIBLE);

            imsaa = new ImageSearchAdapter(this,type,catid,sid,sizid,from,to,key,imsa,currentPage);
            is_list.setAdapter(imsaa);
            is_list.setOnItemClickListener(this);
        }else if(v.getId() == R.id.is_btn_refresh){
            loadImages();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        myMenu(menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void myMenu(Menu menu){
        MenuInflater mif2 = getMenuInflater();
        mif2.inflate(R.menu.search_menu,menu);
        menu.findItem(R.id.next_activity1).setVisible(true);
        if(user_id > -1 && user_id > 0) {
            menu.findItem(R.id.sign).setVisible(false);
            menu.findItem(R.id.profil).setVisible(false);
            menu.findItem(R.id.out).setVisible(true);
        }else{
            menu.findItem(R.id.sign).setVisible(true);
            menu.findItem(R.id.profil).setVisible(false);
            menu.findItem(R.id.out).setVisible(false);

        }
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        Log.d("--MSG--", featureId + "" + item.getTitle());
        if(item.getTitle().equals("sort")){
            final Dialog dialog = new Dialog(ImageSearchActivity.this);
            dialog.setContentView(R.layout.imagesearch_dialog);
            dialog.setTitle("Advance Search");

            spin_cat =(Spinner)dialog.findViewById(R.id.spin_cat);
            adapter = new ArrayAdapter<String>(ImageSearchActivity.this,android.R.layout.simple_dropdown_item_1line,cli_show);
            spin_cat.setAdapter(adapter);
            spin_cat.setSelection(cid);
            spin_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String c=cli.get(position);
                    String cc[] = c.split("/");
                    catid = Integer.parseInt(cc[0]);
                    cid = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spin_sel =(Spinner)dialog.findViewById(R.id.spin_sel);
            adapter = new ArrayAdapter<String>(ImageSearchActivity.this,android.R.layout.simple_dropdown_item_1line,sli_show);
            spin_sel.setAdapter(adapter);
            spin_sel.setSelection(ssid);
            spin_sel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String c=sli.get(position);
                    String cc[] = c.split("/");
                    sid = Integer.parseInt(cc[0]);
                    ssid = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spin_siz =(Spinner)dialog.findViewById(R.id.spin_siz);
            adapter = new ArrayAdapter<String>(ImageSearchActivity.this,android.R.layout.simple_dropdown_item_1line,szli_show);
            spin_siz.setAdapter(adapter);
            spin_siz.setSelection(szid);
            spin_siz.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String c=szli.get(position);
                    String cc[] = c.split("/");
                    sizid = cc[0];
                    szid = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            cfrom =(EditText)dialog.findViewById(R.id.cfrom);
            if(from!=0) {
                cfrom.setText(from + "");
            }

            cto =(EditText)dialog.findViewById(R.id.cto);
            if(to!=0) {
                cto.setText(to + "");
            }

            btn_cancel =(Button)dialog.findViewById(R.id.btn_cancel);
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            btn_sort =(Button)dialog.findViewById(R.id.btn_sort);
            btn_sort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   if(cfrom.getText().toString().trim().equals("")||cfrom.getText().toString()== null){
                       from = 0;
                   }else{
                       from = Integer.parseInt(cfrom.getText().toString().trim());
                   }
                   if(cto.getText().toString().trim().equals("")||cto.getText().toString()== null){
                        to = 0;
                   }else{
                       to = Integer.parseInt(cto.getText().toString().trim());
                   }

                   if(from <= to){
                       type = SEARCH_IMAGE;
                       currentPage = 1;
                       //key = is_key.getText().toString().trim();
                      // Log.d("--MSG--",key);
                       us_li = new ArrayList<String>();
                       is_list_content.setVisibility(View.GONE);
                       is_msg_content.setVisibility(View.GONE);
                       is_loading_content.setVisibility(View.VISIBLE);

                       imsaa = new ImageSearchAdapter(ImageSearchActivity.this,type,catid,sid,sizid,from,to,key,imsa,currentPage);
                       is_list.setAdapter(imsaa);
                       is_list.setOnItemClickListener(ImageSearchActivity.this);
                   }else{
                       Toast.makeText(ImageSearchActivity.this,"Check credit.",Toast.LENGTH_LONG).show();
                   }
                    dialog.dismiss();
                }
            });
            dialog.show();
        }else if(item.getTitle().equals("Si")){
            Intent i = new Intent(imsa,SigninActivity.class);
            startActivity(i);
        }else if(item.getTitle().equals("Pro")){
            Intent i = new Intent(ImageSearchActivity.this,AccountActivity.class);
            i.putExtra("uid",user_id);
            i.putExtra("user_type",user_type);
            ImageSearchActivity.this.finish();
            startActivity(i);
        }else if(item.getTitle().equals("Pack")){
            Intent i = new Intent(ImageSearchActivity.this,PackagesActivity.class);
            i.putExtra("uid",user_id);
            i.putExtra("ut",user_type);

            startActivity(i);
        }
        return super.onMenuItemSelected(featureId, item);
    }

    public void stringErrorMsg(String s) {
        is_msg_content.setVisibility(View.GONE);
        msg.setText(s);
        is_list_content.setVisibility(View.GONE);
        is_loading_content.setVisibility(View.VISIBLE);
        Log.d("--ErrorMSG--",s);
    }

    public void stringResponse(String s) {
        Toast.makeText(ImageSearchActivity.this,s,Toast.LENGTH_LONG).show();
    }

    public void JSONResponse(String s) {

        try {
            JSONArray ja = new JSONArray(s);
            JSONObject jo;
            JSONArray kja,szja,sja,cja;

            for(int i = 0 ;i<ja.length();i++){
                if(i == 0){
                    kli = new ArrayList<String>();
                    kli_show = new ArrayList<String>();

                    kja = ja.getJSONArray(i);

                    for (int j =0;j<kja.length();j++) {
                        jo = kja.getJSONObject(j);
                        kli.add(jo.getString("id") + "/" + jo.getString("kw"));
                        kli_show.add(jo.getString("kw"));
                    }
                }else if(i == 1){
                    cli = new ArrayList<String>();
                    cli_show = new ArrayList<String>();
                    cja = ja.getJSONArray(i);
                    cli.add("0/Select a Category");
                    cli_show.add("Select a Category");
                    for (int j =0;j<cja.length();j++) {
                        jo = cja.getJSONObject(j);
                        cli.add(jo.getString("id") + "/" + jo.getString("cat"));
                        cli_show.add(jo.getString("cat"));
                    }
                }else if(i == 2){
                    sli = new ArrayList<String>();
                    sli_show = new ArrayList<String>();
                    sja = ja.getJSONArray(i);
                    sli.add("0/Select a Seller name");
                    sli_show.add("Select a Seller name");
                    for (int j =0;j<sja.length();j++) {
                        jo = sja.getJSONObject(j);
                        sli.add(jo.getString("id") + "/" + jo.getString("nm"));
                        sli_show.add(jo.getString("nm"));
                    }
                }else if(i == 3){
                    szli = new ArrayList<String>();
                    szli_show = new ArrayList<String>();
                    szja = ja.getJSONArray(i);
                    szli.add("null/Select a Size");
                    szli_show.add("Select a Size");
                    for (int j =0;j<szja.length();j++) {
                        jo = szja.getJSONObject(j);
                        szli.add(jo.getString("width") + "x" + jo.getString("height")+"/"+jo.getString("width") + " x " + jo.getString("height"));
                        szli_show.add(jo.getString("width") + " x " + jo.getString("height"));
                    }
                }
            }

            adapter = new ArrayAdapter<String>(ImageSearchActivity.this,android.R.layout.simple_dropdown_item_1line,kli_show);
            is_key.setAdapter(adapter);



        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void startIt(int id){
        Log.d("--MSG--",id+"");
        Intent i = new Intent(imsa, ImageDetailActivity.class);
        i.putExtra("imgid",id);
        i.putExtra("uid",user_id);
        i.putExtra("ut",user_type);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Restart","jjjj");
       // checkSignin();
    }




}

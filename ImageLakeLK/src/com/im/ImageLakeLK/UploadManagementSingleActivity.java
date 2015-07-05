package com.im.ImageLakeLK;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.im.adapter.KeyWordsAdapter;
import com.im.sync.BitmapAsync;
import com.im.sync.SyncThread;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RLN on 4/30/2015.
 */
public class UploadManagementSingleActivity extends Activity implements AdapterView.OnItemClickListener,View.OnClickListener{

    private static final int SINGLE_REQUEST = 1;
    private static final int IMAGE_REQUEST = 2;

    Intent i;
    UploadManagementSingleActivity umsa;
    KeyWordsAdapter kwa;
    public int user_id;
    public int img_id;
    public int request_type;
    public String type ="";
    public int state =0;

    public ScrollView up_img_scroll;
    public LinearLayout up_load_content,up_img_single_layout,up_img_state_layout;
    public ProgressBar pb;
    public ListView up_img_keylist;
    public ImageView up_img_img;
    public TextView up_img_title;
    public EditText up_img_key;
    public TableLayout up_img_sub_table;
    public Button up_img_add_btn,up_img_can,up_img_con,up_img_inactive,up_img_active;

    public String keyword="";

    public List<String> up_li = new ArrayList<String>();
    public List<String> li = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploadmanagement_single_layout);

        umsa = this;

        getActionBar().setHomeButtonEnabled(true);// this is goin to enable the home button
        getActionBar().setDisplayHomeAsUpEnabled(true);// this will show home screen of your app
        getActionBar().setTitle("Image Details");

        i = getIntent();
        user_id = i.getIntExtra("id",-1);
        img_id = i.getIntExtra("imgid",-1);

        up_load_content =(LinearLayout)findViewById(R.id.upm_load_content);
        up_load_content.setVisibility(View.GONE);

        up_img_scroll =(ScrollView)findViewById(R.id.upm_scroll);
        up_img_scroll.setVisibility(View.GONE);

        pb =(ProgressBar)findViewById(R.id.up_progress);

        up_img_img =(ImageView)findViewById(R.id.up_img_img);

        up_img_title =(TextView)findViewById(R.id.upm_img_title);

        up_img_key =(EditText)findViewById(R.id.up_img_key);

        up_img_add_btn =(Button)findViewById(R.id.up_img_add_btn);
        up_img_add_btn.setOnClickListener(this);

        up_img_sub_table =(TableLayout)findViewById(R.id.up_img_sub_table);

        up_img_single_layout =(LinearLayout)findViewById(R.id.up_img_single_layout);
        up_img_state_layout =(LinearLayout)findViewById(R.id.up_img_state_layout);

        if(i.getIntExtra("req",-1)== SINGLE_REQUEST) {


            up_img_single_layout.setVisibility(View.VISIBLE);
            up_img_state_layout.setVisibility(View.GONE);


            up_img_can = (Button) findViewById(R.id.up_img_can);
            up_img_can.setOnClickListener(this);


            up_img_con = (Button) findViewById(R.id.up_img_con);
            up_img_con.setOnClickListener(this);
        }else if(i.getIntExtra("req",-1)== IMAGE_REQUEST){

            up_img_single_layout.setVisibility(View.GONE);
            up_img_state_layout.setVisibility(View.VISIBLE);

            up_img_inactive = (Button) findViewById(R.id.up_img_inactive);
            up_img_inactive.setVisibility(View.GONE);

            up_img_active = (Button) findViewById(R.id.up_img_active);
            up_img_active.setVisibility(View.GONE);

            if(i.getIntExtra("state",-1)== 1){

                up_img_inactive.setVisibility(View.VISIBLE);
                up_img_inactive.setOnClickListener(this);
            }else if(i.getIntExtra("state",-1)==5){


                up_img_active.setVisibility(View.VISIBLE);
                up_img_active.setOnClickListener(this);

            }





        }
        up_img_sub_table =(TableLayout)findViewById(R.id.up_img_sub_table);

        //loadKeyWords();
        loadImageDetails();
    }


    public void loadKeyWords(){


        request_type = 0;
        type = "keywords";
        Log.d("--MSG--","Inside loadKeyWords");
        kwa =new KeyWordsAdapter(this,img_id,type,umsa);
        up_img_keylist.setAdapter(kwa);
        up_img_keylist.setOnItemClickListener(this);
    }

    public void reloadKeyWord(){
        request_type = 0;
        type = "keywords";
        Log.d("--MSG--","Inside loadKeyWords");
        kwa =new KeyWordsAdapter(this,img_id,type,umsa);
    }

    public void loadImageDetails(){
        request_type = 0;
        type = "single_details";

        SyncThread st = new SyncThread(umsa);
        st.execute();

        up_load_content.setVisibility(View.VISIBLE);
        up_img_scroll.setVisibility(View.GONE);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("--MSG--",position+"");

    }

    public void stringErrorMsg(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    public void stringResponse(String s) {

        if(s.equals("Key_Successful...")){
            Toast.makeText(this, "Keyword Successfuly add.", Toast.LENGTH_LONG).show();
        }else if(s.equals("Con_Successful...")){

            Toast.makeText(this, "Successfuly Confiremed.", Toast.LENGTH_LONG).show();

            NavUtils.navigateUpFromSameTask(this);
            this.finish();

            Intent i = new Intent(this,UploadManagementActivity.class);
            i.putExtra("id",user_id);
            startActivity(i);
        }else if(s.equals("Can_Successful...")){
            Toast.makeText(this, "Successfuly Removed.", Toast.LENGTH_LONG).show();

            NavUtils.navigateUpFromSameTask(this);
            this.finish();

            Intent i = new Intent(this,UploadManagementActivity.class);
            i.putExtra("id",user_id);
            startActivity(i);
        }else if(s.equals("Sta_Successful...")){
            Toast.makeText(this, "Successfuly State Updated.", Toast.LENGTH_LONG).show();

            NavUtils.navigateUpFromSameTask(this);
            this.finish();

            //return true;

           // Intent i = new Intent(this,ImageManagementActivity.class);
            //i.putExtra("id",user_id);
            //startActivity(i);
        }
        else {
            Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.up_img_add_btn){
          keyword = up_img_key.getText().toString();

            if(keyword.trim().equals("")){


                Toast.makeText(this,"Please check what you have enter.",Toast.LENGTH_LONG);

            }else{

                type ="add_keywords";

                String xm[] = keyword.trim().split(" ");
                String nkey ="";
                for(int i =0;i<xm.length;i++){
                    if(i ==(xm.length - 1)) {
                        nkey += xm[i];
                    }else{
                        nkey += xm[i]+",";
                    }
                }
                Log.d("--MSG--",nkey);
                keyword = nkey;
                Log.d("--MSG--",keyword);

                SyncThread st = new SyncThread(umsa);
                st.execute();
            }
        }else if(v.getId() == R.id.up_img_con){
            type ="confirem";

            SyncThread st = new SyncThread(umsa);
            st.execute();
        }else if(v.getId() == R.id.up_img_can){
            type ="cancel";

            SyncThread st = new SyncThread(umsa);
            st.execute();
        }else if(v.getId() == R.id.up_img_inactive){
            type ="state";
            state = 5;
            SyncThread st = new SyncThread(umsa);
            st.execute();
        }else if(v.getId() == R.id.up_img_active){
            type ="state";
            state = 1;
            SyncThread st = new SyncThread(umsa);
            st.execute();
        }
    }

    public void JSONResponse(String s) {

        try {

            JSONArray ar = new JSONArray(s);
            JSONArray sub;
            JSONArray sam;

            JSONObject jo;
            String box_row;

            if(ar.length()>0) {
                for (int i = 0; i < ar.length(); i++) {
                    jo = ar.getJSONObject(i);
                   // box_row = jo.getString("imgid") //0
                        //    + "/" + jo.getString("title")//1
                         //   + "/" + jo.getString("f_name") //2
                         //   + "/" + jo.getString("l_name") //3
                         //   + "/" + jo.getString("em")//4
                         //   + "/" + jo.getString("phn") //5
                          //  + "/" + jo.getString("state")//6
                          //  +"/"+jo.getString("type");//7
                    Log.d("--MSG--",jo.getString("title"));
                    up_img_title.setText(jo.getString("title"));

                    BitmapAsync ba = new BitmapAsync(up_img_img,umsa);
                    ba.execute(jo.getString("url"));


                    sub = new JSONArray(jo.getString("subs"));

                    up_img_sub_table.setStretchAllColumns(true);
                    up_img_sub_table.setShrinkAllColumns(true);

                    TableRow title_Row = new TableRow(umsa);

                    title_Row.setPadding(5,5,5,5);

                    TextView dimention_text = new TextView(umsa);
                    TextView size_text = new TextView(umsa);
                    TextView crdit_text = new TextView(umsa);

                    dimention_text.setText("Dimention");
                    size_text.setText("Size");
                    crdit_text.setText("Credits");

                    //dimention_text.setGravity(Gravity.LEFT);
                    dimention_text.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
                    //size_text.setGravity(Gravity.LEFT);
                    size_text.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
                    //crdit_text.setGravity(Gravity.LEFT);
                    crdit_text.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);

                    TableRow.LayoutParams params = new TableRow.LayoutParams();
                    params.span = 3;

                    title_Row.addView(dimention_text, params);

                    title_Row.addView(size_text, params);
                    title_Row.addView(crdit_text, params);

                    up_img_sub_table.addView(title_Row);


                    if(sub.length()>0){
                        for (int j =0;j<sub.length();j++){
                            JSONObject jb = sub.getJSONObject(j);
                            TableRow title_Row2 = new TableRow(umsa);

                            title_Row2.setPadding(5,5,5,5);
                            TextView dimention_value = new TextView(umsa);
                            TextView size_value = new TextView(umsa);
                            TextView crdit_value = new TextView(umsa);

                            dimention_value.setText(jb.getString("dimention"));
                            size_value.setText(jb.getString("size"));
                            crdit_value.setText(jb.getString("crd"));

                            dimention_value.setTextColor(getResources().getColor(R.color.md_red_500));
                            //dimention_text.setGravity(Gravity.LEFT);
                            dimention_text.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
                            //size_text.setGravity(Gravity.LEFT);
                            size_text.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
                            //crdit_text.setGravity(Gravity.LEFT);
                            crdit_text.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);



                            title_Row2.addView(dimention_value, params);

                            title_Row2.addView(size_value, params);
                            title_Row2.addView(crdit_value, params);

                            up_img_sub_table.addView(title_Row2);


                        }
                    }else{
                        TableRow title_Row2 = new TableRow(umsa);

                        TableRow.LayoutParams params2 = new TableRow.LayoutParams();
                        params2.span = 3;


                        TextView no_item = new TextView(umsa);
                        no_item.setText("No item found.");

                        title_Row2.addView(no_item,params2);
                        up_img_sub_table.addView(title_Row2);

                    }


//-----------------------

                    sam = new JSONArray(jo.getString("equal"));
                    TableRow img_row =null;

                    TableRow.LayoutParams img_params = new TableRow.LayoutParams();
                    img_params.setMargins(5,5,5,5);
                    img_params.span = 3;



                    if(sam.length() >0){
                        LinearLayout.LayoutParams img_layout_params = new LinearLayout.LayoutParams(200, 200);
                        Log.d("--LENGTH--",sam.length()+"");
                        for (int j =0;j<sam.length();j++){
                            JSONObject jbs = sam.getJSONObject(j);
                            if(j%3 == 0){
                                img_row = new TableRow(umsa);
                                img_row.setPadding(5,5,5,5);


                                //--------------------------------------
                                LinearLayout img_layout = new LinearLayout(umsa);
                                img_layout.setOrientation(LinearLayout.VERTICAL);
                                img_layout.setBackground(getResources().getDrawable(R.drawable.white_panel));
                                img_layout.setPadding(2, 2, 2, 2);
                                img_layout.setGravity(Gravity.CENTER);

                                ImageView img_view = new ImageView(umsa);
                                img_view.setMaxHeight(100);
                                img_view.setMaxWidth(100);
                                img_view.setLayoutParams(img_layout_params);

                                Log.d("--URL--",jbs.getString("equalurl"));
                                BitmapAsync bac = new BitmapAsync(img_view,umsa);
                                bac.execute(jbs.getString("equalurl"));

                                img_layout.addView(img_view);

                                TextView seller_text = new TextView(umsa);
                                seller_text.setText(jbs.getString("equalseller"));
                                seller_text.setGravity(Gravity.CENTER);
                                seller_text.setMaxLines(1);
                                img_layout.addView(seller_text);

                                TextView state_text = new TextView(umsa);
                                state_text.setGravity(Gravity.CENTER);
                                state_text.setMaxLines(1);
                                if(Integer.parseInt(jbs.getString("equalstate"))==1) {
                                    state_text.setText("Active");
                                    state_text.setBackgroundColor(getResources().getColor(R.color.md_green_500));
                                    state_text.setTextColor(getResources().getColor(R.color.md_text_white));
                                }else if(Integer.parseInt(jbs.getString("equalstate"))==5){
                                    state_text.setText("Inactive");
                                    state_text.setBackgroundColor(getResources().getColor(R.color.md_red_700));
                                    state_text.setTextColor(getResources().getColor(R.color.md_text_white));
                                }else if(Integer.parseInt(jbs.getString("equalstate"))==7){
                                    state_text.setText("Removed");
                                    state_text.setBackgroundColor(getResources().getColor(R.color.md_falcon_500_75));
                                    state_text.setTextColor(getResources().getColor(R.color.md_text_white));
                                }
                                img_layout.addView(state_text);
                                img_row.addView(img_layout,img_params);
                                up_img_sub_table.addView(img_row);


                            }else{

                                //--------------------------------------
                                LinearLayout img_layout = new LinearLayout(umsa);
                                img_layout.setOrientation(LinearLayout.VERTICAL);
                                img_layout.setBackground(getResources().getDrawable(R.drawable.white_panel));
                                img_layout.setPadding(2, 2, 2, 2);
                                img_layout.setGravity(Gravity.CENTER);

                                ImageView img_view = new ImageView(umsa);
                                img_view.setMaxHeight(100);
                                img_view.setMaxWidth(100);
                                img_view.setLayoutParams(img_layout_params);

                                Log.d("--URL--",jbs.getString("equalurl"));
                                BitmapAsync bac = new BitmapAsync(img_view,umsa);
                                bac.execute(jbs.getString("equalurl"));

                                img_layout.addView(img_view);

                                TextView seller_text = new TextView(umsa);
                                seller_text.setText(jbs.getString("equalseller"));
                                seller_text.setGravity(Gravity.CENTER);
                                seller_text.setMaxLines(1);
                                img_layout.addView(seller_text);

                                TextView state_text = new TextView(umsa);
                                state_text.setGravity(Gravity.CENTER);
                                state_text.setMaxLines(1);
                                if(Integer.parseInt(jbs.getString("equalstate"))==1) {
                                    state_text.setText("Active");
                                    state_text.setBackgroundColor(getResources().getColor(R.color.md_green_500));
                                    state_text.setTextColor(getResources().getColor(R.color.md_text_white));
                                }else if(Integer.parseInt(jbs.getString("equalstate"))==5){
                                    state_text.setText("Inactive");
                                    state_text.setBackgroundColor(getResources().getColor(R.color.md_red_600));
                                    state_text.setTextColor(getResources().getColor(R.color.md_text_white));
                                }else if(Integer.parseInt(jbs.getString("equalstate"))==7){
                                    state_text.setText("Removed");
                                    state_text.setBackgroundColor(getResources().getColor(R.color.md_falcon_500_75));
                                    state_text.setTextColor(getResources().getColor(R.color.md_text_white));
                                }
                                img_layout.addView(state_text);
                                img_row.addView(img_layout,img_params);



                            }




                        }
                    }else{

                        img_row = new TableRow(umsa);
                        img_row.setPadding(5,5,5,5);

                        LinearLayout.LayoutParams img_layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100);

                        LinearLayout img_layout = new LinearLayout(umsa);
                        img_layout.setOrientation(LinearLayout.VERTICAL);
                        img_layout.setBackground(getResources().getDrawable(R.drawable.white_panel));
                        img_layout.setPadding(2, 2, 2, 2);
                        img_layout.setGravity(Gravity.CENTER);

                        TextView seller_text = new TextView(umsa);
                        seller_text.setText("No matching image found.");
                        seller_text.setGravity(Gravity.CENTER);
                        seller_text.setMaxLines(1);
                        seller_text.setLayoutParams(img_layout_params);
                        img_layout.addView(seller_text);
                        img_row.addView(img_layout,img_params);
                        up_img_sub_table.addView(img_row);
                    }

                    //--------------------------------------




                    up_load_content.setVisibility(View.GONE);
                    up_img_scroll.setVisibility(View.VISIBLE);

                   // li.add(box_row);
                   // Log.d("--MSG--", box_row);
                }


            }else{

                stringResponse("No item found.");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

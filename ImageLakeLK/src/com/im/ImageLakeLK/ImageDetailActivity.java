package com.im.ImageLakeLK;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.im.sync.BitmapAsync;
import com.im.sync.SyncThread;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by RLN on 5/19/2015.
 */
public class ImageDetailActivity extends Activity {

    public static final String INFO= "info";
    public static final String ADD_LIGHTBOX= "lightbox";
    public static final String ADD_CART= "cart";

    ImageDetailActivity imda;
    Intent i;
    SyncThread st ;
    public int imgid;
    public int ssid;
    public int uid;
    public int user_type;
    public String type;

    LinearLayout imd_inlight;
    TextView imd_title,imd_sel,imd_view,imd_dwn;
    ImageView imd_img;
    Button imd_btn_libox;
    TableLayout imd_dwncon,imd_keylist,imd_rellist;

    ArrayList<String> sub_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagedetail_activity);

        getActionBar().setHomeButtonEnabled(true);// this is goin to enable the home button
        getActionBar().setDisplayHomeAsUpEnabled(true);// this will show home screen of your app
        getActionBar().setTitle("Image Detail");

        imda = this;

        i = getIntent();
        imgid = i.getIntExtra("imgid",-1);
        Log.d("img_id",imgid+"");
        uid = i.getIntExtra("uid",-1);
        Log.d("us_id",uid+"");
        user_type = i.getIntExtra("ut",-1);
        Log.d("us_id",user_type+"");

        imd_title =(TextView)findViewById(R.id.imd_title);
        imd_sel =(TextView)findViewById(R.id.imd_sel);
        imd_view =(TextView)findViewById(R.id.imd_view);
        imd_dwn =(TextView)findViewById(R.id.imd_dwn);
        imd_img = (ImageView)findViewById(R.id.imd_img);
        imd_inlight = (LinearLayout)findViewById(R.id.imd_inlight);
        imd_inlight.setVisibility(View.GONE);

        imd_btn_libox =(Button)findViewById(R.id.imd_btn_libox);
        imd_btn_libox.setVisibility(View.GONE);
        imd_btn_libox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(uid > -1 && uid > 0){
                    if(user_type == 2 || user_type == 3){
                        type = ADD_LIGHTBOX;
                        st = new SyncThread(imda);
                        st.execute();
                    }else{
                        Toast.makeText(imda,"You must be a contributor or buyer.",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Intent i = new Intent(imda,SigninActivity.class);
                    startActivity(i);
                }

            }
        });
        imd_dwncon =(TableLayout)findViewById(R.id.imd_dwncon);
        imd_keylist =(TableLayout)findViewById(R.id.imd_keylist);
        imd_rellist =(TableLayout)findViewById(R.id.imd_rellist);
        loadImageDetails();

    }

    void loadImageDetails(){
        type = INFO;
        st = new SyncThread(imda);
        st.execute();
    }

    public void stringErrorMsg(String s) {
        Toast.makeText(ImageDetailActivity.this,s,Toast.LENGTH_LONG).show();
    }

    public void JSONResponse(String s) {
        try {
            JSONArray ja;
            JSONObject jo = new JSONObject(s);
            String box_list= "";
            imd_title.setText(jo.getString("title"));
            imd_sel.setText(jo.getString("uName"));

            BitmapAsync ba = new BitmapAsync(imda.imd_img,imda);
            ba.execute(jo.getString("url"));

            imd_view.setText(jo.getString("view"));
            imd_dwn.setText(jo.getString("dwn"));

            imd_dwncon.setStretchAllColumns(true);
            imd_dwncon.setShrinkAllColumns(true);

            TableRow.LayoutParams params = new TableRow.LayoutParams();
            params.span = 1;

            LinearLayout.LayoutParams img_layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 30);
            LinearLayout.LayoutParams img_layout_params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            LinearLayout.LayoutParams img_btn_params = new LinearLayout.LayoutParams(16, 16);
            img_btn_params.setMargins(0,0,0,0);
            img_btn_params.height = 16;
            img_btn_params.width = 16;
            imd_dwncon.removeAllViewsInLayout();


            sub_list = new ArrayList<String>();

            ja = new JSONArray(jo.getString("subList"));
            for (int i = 0;i<ja.length();i++){

                JSONObject jjo = ja.getJSONObject(i);
                box_list = jjo.getString("sid") + "/" + jjo.getString("size")+ "/" + jjo.getString("dim")+"/"+jjo.getString("crd");
                sub_list.add(box_list);

                TableRow title_Row = new TableRow(ImageDetailActivity.this);
                title_Row.setLayoutParams(img_layout_params);

                title_Row.setBackground(ImageDetailActivity.this.getResources().getDrawable(R.drawable.darkblue_panel));



                TextView slice_size = new TextView(ImageDetailActivity.this);
                slice_size.setText(jjo.getString("size"));
                slice_size.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
                slice_size.setLayoutParams(img_layout_params2);
                slice_size.setPadding(10,40,0,0);
                slice_size.setTextColor(ImageDetailActivity.this.getResources().getColor(R.color.md_text_white));

                TextView slice_dim = new TextView(ImageDetailActivity.this);
                slice_dim.setText(jjo.getString("dim"));
                slice_dim.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
                slice_dim.setLayoutParams(img_layout_params2);
                slice_dim.setPadding(0,40,0,0);
                slice_dim.setTextColor(ImageDetailActivity.this.getResources().getColor(R.color.md_text_white));

                TextView slice_crd = new TextView(ImageDetailActivity.this);
                slice_crd.setText(jjo.getString("crd"));
                slice_crd.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
                slice_crd.setLayoutParams(img_layout_params2);
                slice_crd.setPadding(0,40,0,0);
                slice_crd.setTextColor(ImageDetailActivity.this.getResources().getColor(R.color.md_text_white));

                ImageButton slice_btn = new ImageButton(ImageDetailActivity.this);
                slice_btn.setImageDrawable(ImageDetailActivity.this.getResources().getDrawable(R.drawable.download_img));
                slice_btn.setLayoutParams(img_btn_params);
                slice_btn.setMaxHeight(16);
                slice_btn.setMaxWidth(16);
                slice_btn.setBackground(ImageDetailActivity.this.getResources().getDrawable(R.drawable.blue_button));

                final int sid = Integer.parseInt(jjo.getString("sid"));

                slice_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        type = "Add_cart";
                        ssid = sid;
                        if(uid != -1){

                            st = new SyncThread(imda);
                            st.execute();

                        }else{
                            Intent i = new Intent(imda,SigninActivity.class);
                            imda.finish();
                            startActivity(i);

                        }


                    }
                });

                final int x = Integer.parseInt(jjo.getString("sid"));

                title_Row.addView(slice_size, params);
                title_Row.addView(slice_dim, params);
                title_Row.addView(slice_crd, params);
                title_Row.addView(slice_btn, params);

                imd_dwncon.addView(title_Row);



            }

            imd_keylist.setStretchAllColumns(true);
            imd_keylist.setShrinkAllColumns(true);
            imd_keylist.removeAllViewsInLayout();

            ja = new JSONArray(jo.getString("keyList"));
            img_layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            for(int i =0;i<ja.length();i++){

                JSONObject jjo = ja.getJSONObject(i);
                TableRow title_Row = new TableRow(ImageDetailActivity.this);
                title_Row.setLayoutParams(img_layout_params);

                title_Row.setBackground(ImageDetailActivity.this.getResources().getDrawable(R.drawable.warning_panel));

                TextView slice_size = new TextView(ImageDetailActivity.this);
                slice_size.setText(jjo.getString("key"));
                slice_size.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
                slice_size.setLayoutParams(img_layout_params);


                title_Row.addView(slice_size, params);
                imd_keylist.addView(title_Row);

            }

            imd_rellist.setStretchAllColumns(true);
            imd_rellist.setShrinkAllColumns(true);
            imd_rellist.removeAllViewsInLayout();
            ja = new JSONArray(jo.getString("imgList"));

            TableRow img_row =null;

            TableRow.LayoutParams img_params = new TableRow.LayoutParams();
            img_params.setMargins(5,5,5,5);
            img_params.span = 1;

            LinearLayout.LayoutParams imd_params = new LinearLayout.LayoutParams(350, 300);

            if(ja.length()>0 && ja.length()>1) {

            for(int i = 0 ; i < ja.length();i++){
                Log.d("--LENGTH--",ja.length()+"");

                    JSONObject jjo = ja.getJSONObject(i);
                    if (i % 3 == 0) {
                        Log.d("--LENGTH--",i+"");

                        img_row = new TableRow(ImageDetailActivity.this);
                        img_row.setPadding(5, 5, 5, 5);

                        LinearLayout img_layout = new LinearLayout(ImageDetailActivity.this);
                        img_layout.setOrientation(LinearLayout.VERTICAL);
                        img_layout.setBackground(getResources().getDrawable(R.drawable.white_panel));
                        img_layout.setPadding(2, 2, 2, 2);
                        img_layout.setGravity(Gravity.CENTER);

                        ImageView img_view = new ImageView(ImageDetailActivity.this);
                        img_view.setMaxHeight(150);
                        img_view.setMaxWidth(180);
                        img_view.setLayoutParams(imd_params);

                        Log.d("--URL--", jjo.getString("url"));
                        BitmapAsync bac = new BitmapAsync(img_view, ImageDetailActivity.this);
                        bac.execute(jjo.getString("url"));

                        img_layout.addView(img_view);
                        final int id = Integer.parseInt(jjo.getString("simid"));

                        img_row.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(ImageDetailActivity.this,ImageDetailActivity.class);
                                ImageDetailActivity.this.finish();
                                i.putExtra("imgid",id);
                                i.putExtra("uid",uid);
                                startActivity(i);
                            }
                        });
                        imd_rellist.addView(img_row);
                    } else {

                        Log.d("--LENGTH--",i+" else");

                        LinearLayout img_layout = new LinearLayout(ImageDetailActivity.this);
                        img_layout.setOrientation(LinearLayout.VERTICAL);
                        img_layout.setBackground(getResources().getDrawable(R.drawable.white_panel));
                        img_layout.setPadding(2, 2, 2, 2);
                        img_layout.setGravity(Gravity.CENTER);

                        ImageView img_view = new ImageView(ImageDetailActivity.this);
                        img_view.setMaxHeight(150);
                        img_view.setMaxWidth(180);
                        img_view.setLayoutParams(imd_params);

                        Log.d("--URL--", jjo.getString("url"));
                        BitmapAsync bac = new BitmapAsync(img_view, ImageDetailActivity.this);
                        bac.execute(jjo.getString("url"));

                        img_layout.addView(img_view);
                        final int idd = Integer.parseInt(jjo.getString("simid"));
                        img_row.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(ImageDetailActivity.this,ImageDetailActivity.class);
                                ImageDetailActivity.this.finish();
                                i.putExtra("imgid",idd);
                                i.putExtra("uid",uid);
                                startActivity(i);
                            }
                        });
                        img_row.addView(img_layout, img_params);

                    }
                }
            }else{
                Log.d("--LENGTH--",ja.length()+" 123");
                img_row = new TableRow(ImageDetailActivity.this);
                img_row.setPadding(5,5,5,5);

                imd_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100);

                LinearLayout img_layout = new LinearLayout(ImageDetailActivity.this);
                img_layout.setOrientation(LinearLayout.VERTICAL);
                img_layout.setBackground(getResources().getDrawable(R.drawable.white_panel));
                img_layout.setPadding(2, 2, 2, 2);
                img_layout.setGravity(Gravity.CENTER);

                TextView seller_text = new TextView(ImageDetailActivity.this);
                seller_text.setText("No matching image found.");
                seller_text.setGravity(Gravity.CENTER);
                seller_text.setMaxLines(1);
                seller_text.setLayoutParams(imd_params);
                img_layout.addView(seller_text);
                img_row.addView(img_layout,img_params);
                imd_rellist.addView(img_row);
            }

            int inlight = Integer.parseInt(jo.getString("lightbox"));
            if(inlight == 0){
                imd_btn_libox.setVisibility(View.VISIBLE);
                imd_inlight.setVisibility(View.GONE);
            }else{
                imd_btn_libox.setVisibility(View.GONE);
                imd_inlight.setVisibility(View.VISIBLE);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stringResponse(String s) {
        if(s.equals("Successfully_add_lightbox")){
            Toast.makeText(ImageDetailActivity.this,"Successfully add to Lightbox.",Toast.LENGTH_LONG).show();
            imd_btn_libox.setVisibility(View.GONE);
            imd_inlight.setVisibility(View.VISIBLE);
        }else{
            Toast.makeText(ImageDetailActivity.this,s,Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       /* MenuInflater mif2 = getMenuInflater();
        mif2.inflate(R.menu.search_menu,menu);

        menu.findItem(R.id.next_activity1).setVisible(false);
        if(uid > -1 && uid > 0) {
            menu.findItem(R.id.sign).setVisible(false);
            menu.findItem(R.id.profil).setVisible(true);
            menu.findItem(R.id.out).setVisible(true);
        }else{
            menu.findItem(R.id.sign).setVisible(true);
            menu.findItem(R.id.profil).setVisible(false);
            menu.findItem(R.id.out).setVisible(false);
        }*/
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        Log.d("--MSG--",featureId+""+item.getTitle());
        if(item.getTitle().equals("Pack")){
            Intent i = new Intent(ImageDetailActivity.this,PackagesActivity.class);
            i.putExtra("uid",uid);
            i.putExtra("ut",user_type);
            startActivity(i);

        }else if(item.getTitle().equals("Pro")){
            Intent i = new Intent(ImageDetailActivity.this,AccountActivity.class);
            i.putExtra("id",uid);
            i.putExtra("user_type",user_type);
            startActivity(i);
        }else if(item.getTitle().equals("Si")){
            Intent i = new Intent(ImageDetailActivity.this,SigninActivity.class);
            startActivity(i);
        }
        return super.onMenuItemSelected(featureId, item);
    }

}

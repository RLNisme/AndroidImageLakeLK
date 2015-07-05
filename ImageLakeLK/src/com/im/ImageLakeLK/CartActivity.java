package com.im.ImageLakeLK;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.DownloadManager;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.im.adapter.CartAdapter;
import com.im.adapter.ImageSearchAdapter;
import com.im.dialogbox.CartItemDialog;
import com.im.dialogbox.EditProfileDialog;
import com.im.sync.BitmapAsync;
import com.im.sync.BitmapCart;
import com.im.sync.DownloadTask;
import com.im.sync.SyncThread;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by RLN on 6/1/2015.
 */
public class CartActivity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener{

    public static final String LOAD_ALL = "All_Cart_Items";
    public static final String REMOVE_DB = "Remove_DB";
    public static final String ADD_LIGHTBOX = "Add_Light";
    public static final String DOWNLOAD = "Download";

    Intent i;
    CartActivity crt;
    CartAdapter crta;
    SyncThread st;
    public LinearLayout crt_list_content,crt_msg_content,crt_loading_content;
    Button crt_btn_refresh;
    public ListView crt_list;
    public TextView msg,crt_total;
    public ProgressBar pb;
    public Button crt_dwn;
    public int user_id;
    public int user_type;
    public int total;


    public String type ="";
    public int cart_item ;
    public int crd_count;


    public ArrayList<String> us_li ;//lightbox list
    //public ArrayList<String> vb_li ;//lightbox list
    public ArrayAdapter<String> adapter;

    Date dat = new Date();
    SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sd2 = new SimpleDateFormat("HH:mm:ss");
    String day = sd1.format(dat);
    String time = sd2.format(dat);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);

        crt = this;

        i = getIntent();
        user_id = i.getIntExtra("uid",-1);
        user_type = i.getIntExtra("ut",-1);

        crt_list_content = (LinearLayout)findViewById(R.id.crt_list_content);
        crt_list_content.setVisibility(View.GONE);


        crt_list =(ListView)findViewById(R.id.crt_list);

        Log.d("DATE/TIME",day+"/"+time);

        crt_msg_content =(LinearLayout)findViewById(R.id.crt_msg_content);
        crt_msg_content.setVisibility(View.VISIBLE);

        msg =(TextView)findViewById(R.id.crt_msg);
        crt_btn_refresh =(Button)findViewById(R.id.crt_btn_refresh);
        crt_btn_refresh.setOnClickListener(this);

        crt_loading_content =(LinearLayout)findViewById(R.id.crt_load_content);
        crt_loading_content.setVisibility(View.GONE);

        pb =(ProgressBar)findViewById(R.id.crt_loading);

        crt_dwn = (Button)findViewById(R.id.crt_dwn);
        crt_dwn.setVisibility(View.GONE);
        crt_dwn.setOnClickListener(this);
        crt_total =(TextView)findViewById(R.id.crt_total);
        crt_total.setVisibility(View.GONE);
        loadImages();

    }

    public void loadImages(){
        type = LOAD_ALL;

        us_li = new ArrayList<String>();
        crt_list_content.setVisibility(View.GONE);
        crt_msg_content.setVisibility(View.GONE);
        crt_loading_content.setVisibility(View.VISIBLE);

        crta = new CartAdapter(this,type,user_id,crt);
        crt_list.setAdapter(crta);

        crt_list.setOnItemClickListener(this);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       /* MenuInflater mif2 = getMenuInflater();
        mif2.inflate(R.menu.account_menu,menu);
        menu.findItem(R.id.next_activity2).setVisible(false);
        menu.findItem(R.id.profil).setVisible(true)*/
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        /*if(item.getTitle().equals("Next")){
            Intent i = new Intent(CartActivity.this,ImageSearchActivity.class);
            i.putExtra("uid",user_id);
            i.putExtra("ut",user_type);
            startActivity(i);

        }else if(item.getTitle().equals("Cart")){
            Intent i = new Intent(CartActivity.this,CartActivity.class);
            i.putExtra("uid",user_id);
            i.putExtra("ut",user_type);
            startActivity(i);
        }else if(item.getTitle().equals("Pro")){
            Intent i = new Intent(CartActivity.this,AccountActivity.class);
            i.putExtra("id",user_id);
            i.putExtra("user_type",user_type);
            CartActivity.this.finish();
            startActivity(i);
        }*/
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.crt_dwn){
            type = DOWNLOAD;
            st = new SyncThread(crt);
            st.execute();
        }else if(v.getId()== R.id.crt_btn_refresh){
            loadImages();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String g = us_li.get(position);
        String gg[] = g.split("%");

        cart_item = Integer.parseInt(gg[7]);
        Log.d("Click id",cart_item+"");
        crd_count = Integer.parseInt(gg[4]);

        FragmentManager fm = getFragmentManager();
        DialogFragment newFragment = new CartItemDialog();
        newFragment.show(fm, "CartItem");
    }

    public void stringErrorMsg(String s) {
            crt_msg_content.setVisibility(View.VISIBLE);
            crt_list_content.setVisibility(View.GONE);
            crt_dwn.setVisibility(View.GONE);
            crt_total.setVisibility(View.GONE);
            msg.setText(s);
            Toast.makeText(crt, s, Toast.LENGTH_LONG).show();

    }

    public void stringResponse(String s) {
        if(s.equals("Successfuly_Remove") || s.equals("Successfully_Add_Lightbox")){
            loadImages();
        }else if(s.equals("Dwnloaded.")){

            try {
                    for (String sc:us_li) {
                        String n[] = sc.split("%");
                        String url = "http://10.0.3.2:8084/AndroidImageLK/";
                        url += "uploads/" + n[8];

                       // BitmapCart ba = new BitmapCart(n[3],url,crt);
                       // ba.execute("uploads/" + n[8]);

                        //url += n[5];
                        Log.d("LLLLLLLLLLLLLLLLLLLLL", url);
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                        request.setTitle(n[3]);
                        request.setDescription("File is being downloaded...");
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);


                        String nameOfFile = URLUtil.guessFileName(url, null, MimeTypeMap.getFileExtensionFromUrl(url));

                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nameOfFile);

                        DownloadManager dm = (DownloadManager) crt.getSystemService(Context.DOWNLOAD_SERVICE);
                        dm.enqueue(request);

                       // DownloadTask dt = new DownloadTask(url,crt);
                       // dt.execute();
                    }



               // vb_li = us_li;
                //loadImages();
                Intent i = new Intent(crt,RatingActivity.class);
                i.putExtra("rate",us_li);
                this.finish();
                startActivity(i);
                Toast.makeText(crt,"Download complete.",Toast.LENGTH_LONG).show();
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(crt,"Download Error.",Toast.LENGTH_LONG).show();
            }


        }else {
            Toast.makeText(crt, s, Toast.LENGTH_LONG).show();
        }
    }

    public void JSONResponse(String s) {
    }
}

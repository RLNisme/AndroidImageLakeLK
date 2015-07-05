package com.im.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.im.ImageLakeLK.CartActivity;
import com.im.ImageLakeLK.ImageDetailActivity;
import com.im.ImageLakeLK.ImageManagementActivity;
import com.im.ImageLakeLK.ImageSearchActivity;
import com.im.ImageLakeLK.R;
import com.im.ImageLakeLK.UserManagementActivity;
import com.im.sync.BitmapAsync;
import com.im.sync.SyncThread;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by RLN on 5/17/2015.
 */
public class ImageSearchAdapter extends BaseAdapter{

    private Context context;
    SyncThread st;
    public String[] users;
    ImageSearchAdapter imsaa;
    ImageSearchActivity imsa;
    RatingBar rb;
    public String type,key;
    public int catid = 0;
    public int sid = 0;
    public String sizid = "";
    public int from =0;
    public int to = 0;

    public ArrayList<String> li = new ArrayList<String>();

    public int currentPage = 1;

    public ImageSearchAdapter(Context context,String type,ImageSearchActivity imsa){
        this.context = context;
        this.type = type;
        this.imsa = imsa;
        getImages();
    }

    public ImageSearchAdapter(Context context,String type,ImageSearchActivity imsa,int currentPage){
        this.context = context;
        this.type = type;
        this.imsa = imsa;
        this.currentPage = currentPage;
        getImages();
    }

    public ImageSearchAdapter(Context context,String type,int catid,int sid,String sizid,int from,int to,String key,ImageSearchActivity imsa,int currentPage){
        this.context = context;
        this.type = type;
        this.catid = catid;
        this.sid = sid;
        this.sizid = sizid;
        this.from = from;
        this.to = to;
        this.key = key;
        this.imsa = imsa;
        this.currentPage = currentPage;
        getImages();
    }




    void getImages(){
        imsaa = this;
        st = new SyncThread(imsaa);
        st.execute();
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
        ImageManagementViewHolder mvh = null;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.imagesearch_row,parent,false);

            mvh = new ImageManagementViewHolder();

            mvh.upm_title = (TextView)row.findViewById(R.id.upm_title);

            mvh.upm_siz = (TextView)row.findViewById(R.id.upm_siz);

            mvh.upm_rb = (RatingBar)row.findViewById(R.id.upm_rb);

            mvh.upm_img = (ImageView)row.findViewById(R.id.upm_img);
            //mvh.upm_img.setOnClickListener(this);

            mvh.upm_cat = (TextView)row.findViewById(R.id.upm_cat);

            mvh.upm_sel = (TextView)row.findViewById(R.id.upm_sel);

            row.setTag(mvh);

        }else{
            row = convertView;
            mvh = (ImageManagementViewHolder)row.getTag();
        }

        String list_items = li.get(position);
        String[] list_item = list_items.split("%");

        Log.d("--MSG--","{"+list_items+"}");
        imsa.us_li = li;

        mvh.upm_title.setText(list_item[2]);
        mvh.upm_siz.setText(list_item[5]);
        mvh.upm_rb.setRating(Float.parseFloat(list_item[6]));
        final float rt = Float.parseFloat(list_item[6]);
        mvh.upm_rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
               ratingBar.setRating(rt);
            }
        });
        mvh.upm_cat.setText(list_item[1]);
        mvh.upm_sel.setText(list_item[3]);

        BitmapAsync ba = new BitmapAsync(mvh.upm_img,imsa);
        ba.execute(list_item[4]);
        final int id = Integer.parseInt(list_item[0]);
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imsa.startIt(id);
            }
        });

        return row;
    }

    public void stringErrorMsg(String s) {
        imsa.stringErrorMsg(s);
    }

    public void JSONResponse(String s) {

        try {
            JSONArray ar = new JSONArray(s);
            JSONArray ar2;
            JSONObject jo,jo2;
            String box_row;

            if(ar.length()>0) {
                for (int i = 0; i < ar.length(); i++) {
                    if(i ==0) {
                        ar2 = ar.getJSONArray(i);
                        Log.d("--LENGTH--",ar2.length()+"");
                        if(ar2.length()>0) {
                            for (int j = 0; j < ar2.length(); j++) {
                                jo = ar2.getJSONObject(j);
                                box_row = jo.getString("id") //0
                                        + "%" + jo.getString("cat")//1
                                        + "%" + jo.getString("title") //2
                                        + "%" + jo.getString("seller") //3
                                        + "%" + jo.getString("url")//4
                                        + "%" + jo.getString("dimention") //5
                                        + "%" + jo.getString("rate")//6
                                ;//7
                                li.add(box_row);
                                Log.d("--MSG--", box_row);

                            }
                        }else{
                            imsa.stringErrorMsg("No item found.");
                        }
                    }else if(i==1){
                            jo2=ar.getJSONObject(i);
                            imsa.noOfPages = Integer.parseInt(jo2.getString("noOfPages"));
                            imsa.currentPage = Integer.parseInt(jo2.getString("currentPage"));

                            Log.d("--MSG--",imsa.noOfPages+"");
                        Log.d("--MSG--",imsa.currentPage+"");

                            if(imsa.currentPage != 1){
                                imsa.is_left.setVisibility(View.VISIBLE);
                            }else{
                                imsa.is_left.setVisibility(View.GONE);
                            }
                            if(imsa.currentPage < imsa.noOfPages){
                                imsa.is_right.setVisibility(View.VISIBLE);
                            }else{
                                imsa.is_right.setVisibility(View.GONE);
                            }
                            imsa.pageno.setText(imsa.currentPage+"");
                    }
                }

                imsa.is_list_content.setVisibility(View.VISIBLE);
                imsa.is_msg_content.setVisibility(View.GONE);

            }else{

                stringErrorMsg("No item found.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stringResponse(String s) {
        imsa.stringResponse(s);
    }

    public void setProgressBarVisible(){
        imsa.is_msg_content.setVisibility(View.GONE);
        imsa.is_list_content.setVisibility(View.GONE);
        imsa.is_loading_content.setVisibility(View.VISIBLE);
    }
    public void setProgressBarGone(){
        imsa.is_loading_content.setVisibility(View.GONE);
    }


}

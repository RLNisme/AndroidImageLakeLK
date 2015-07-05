package com.im.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.im.ImageLakeLK.CartActivity;
import com.im.ImageLakeLK.ImageSearchActivity;
import com.im.ImageLakeLK.R;
import com.im.sync.BitmapAsync;
import com.im.sync.SyncThread;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by RLN on 6/1/2015.
 */
public class CartAdapter extends BaseAdapter {

    private Context context;
    SyncThread st;
    public String[] users;
    CartAdapter crta;
    CartActivity crt;
    public String type;
    public int uid;

    public ArrayList<String> li = new ArrayList<String>();

    public CartAdapter(Context context,String type,int uid,CartActivity crt){
        this.context = context;
        this.type = type;
        this.uid = uid;
        this.crt = crt;
        getImages();
    }

    void getImages(){
        crta = this;
        st = new SyncThread(crta);
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
        CartViewHolder mvh = null;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.cartrow,parent,false);

            mvh = new CartViewHolder();

            mvh.crt_title = (TextView)row.findViewById(R.id.crt_title);

            mvh.crt_img = (ImageView)row.findViewById(R.id.crt_img);
            //mvh.upm_img.setOnClickListener(this);

            mvh.crt_dim = (TextView)row.findViewById(R.id.crt_dim);

            mvh.crt_cd = (TextView)row.findViewById(R.id.crt_cd);

            mvh.crd_state = (TextView)row.findViewById(R.id.crd_state);

            row.setTag(mvh);

        }else{
            row = convertView;
            mvh = (CartViewHolder)row.getTag();
        }

        String list_items = li.get(position);
        String[] list_item = list_items.split("%");

        Log.d("--MSG--","{"+list_items+"}");
        crt.us_li = li;

        mvh.crt_title.setText(list_item[3]);
        Log.d("Title",list_item[3]);
        mvh.crt_dim.setText(list_item[2]);
        Log.d("Dimention",list_item[2]);
        mvh.crt_cd.setText(list_item[4]);
        Log.d("Credits",list_item[4]);

        if(Integer.parseInt(list_item[6])== 1){
            mvh.crd_state.setText("Active");
            mvh.crd_state.setBackgroundColor(crt.getResources().getColor(R.color.md_green_500));

        }else if(Integer.parseInt(list_item[6])== 7){
            mvh.crd_state.setText("Inactive");
            mvh.crd_state.setBackgroundColor(crt.getResources().getColor(R.color.md_red_500));
        }
        Log.d("State",list_item[6]);
        Log.d("url",list_item[5]);
        BitmapAsync ba = new BitmapAsync(mvh.crt_img,crta);
        ba.execute(list_item[5]);

        crt.crt_total.setVisibility(View.VISIBLE);
        crt.crt_dwn.setVisibility(View.VISIBLE);

        return row;
    }

    public void stringErrorMsg(String s) {
        crt.stringErrorMsg(s);
    }

    public void JSONResponse(String s) {

        try {
            JSONObject ar = new JSONObject(s);
            JSONArray ar2;
            JSONObject jo;
            String box_row;



                        ar2 = ar.getJSONArray("cartList");
                        Log.d("-MSG-",ar2+"");

                        Log.d("--LENGTH--", ar2.length() + "");
                        if(ar2.length()>0) {
                            for (int j = 0; j < ar2.length(); j++) {
                                jo = ar2.getJSONObject(j);
                                box_row = jo.getString("imgid") //0
                                        + "%" + jo.getString("sid")//1
                                        + "%" + jo.getString("dim") //2
                                        + "%" + jo.getString("title") //3
                                        + "%" + jo.getString("crd")//4
                                        + "%" + jo.getString("url") //5
                                        + "%" + jo.getString("state")//6
                                        + "%" + jo.getString("chid")//7
                                        + "%" + jo.getString("surl")//8
                                ;
                                li.add(box_row);
                                Log.d("--MSG--", box_row);

                            }

                            crt.crt_list_content.setVisibility(View.VISIBLE);
                            crt.crt_msg_content.setVisibility(View.GONE);
                            crt.total = Integer.parseInt(ar.getString("total"));
                            crt.crt_total.setText("Total Credits:"+ar.getString("total"));
                        }else{
                            crt.stringErrorMsg("No item found.");
                        }





        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stringResponse(String s) {
        crt.stringResponse(s);
    }

    public void setProgressBarVisible(){
        crt.crt_msg_content.setVisibility(View.GONE);
       // crt.crt_list_content.setVisibility(View.GONE);
        crt.crt_loading_content.setVisibility(View.VISIBLE);
    }
    public void setProgressBarGone(){
        crt.crt_loading_content.setVisibility(View.GONE);
    }
}

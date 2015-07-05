package com.im.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.im.ImageLakeLK.MyUploadsActivity;
import com.im.ImageLakeLK.R;
import com.im.sync.BitmapAsync;
import com.im.sync.SyncThread;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by RLN on 4/20/2015.
 */
public class MyUploadsAdapter extends BaseAdapter {

    MyUploadsActivity mua;
    public MyUploadsAdapter mupa;
    private Context context;
    public String[] myuploads;
    public int user_id;
    public int request;
    public ArrayList<String> li = new ArrayList<String>();

    public MyUploadsAdapter(Context context,int uid,MyUploadsActivity mua){
        this.mua = mua;
        this.user_id = uid;
        this.context = context;
        getMyUploads();
    }

    @Override
    public int getCount() {
        Log.d("--MSG--", li.size() + " getCount");

        return li.size();
    }

    @Override
    public Object getItem(int position) {
        Log.d("--MSG--", myuploads[position] + " getItem");
        return myuploads[position];
    }

    @Override
    public long getItemId(int position) {
        Log.d("--MSG--", position + " getItemID");
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d("--MSG--", "Inside the MyuploadsAdapter_" + position);
        View row = null;
        MyUploadsViewHolder mvh = null;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.myuploads_row,parent,false);

            mvh = new MyUploadsViewHolder();

            mvh.title = (TextView)row.findViewById(R.id.myup_title);


            mvh.id = (TextView)row.findViewById(R.id.myup_img_id);


            mvh.state = (TextView)row.findViewById(R.id.myup_state);


            mvh.img = (ImageView)row.findViewById(R.id.myup_img);

            row.setTag(mvh);

        }else{
            row = convertView;
            mvh = (MyUploadsViewHolder)row.getTag();
        }

        String list_items = li.get(position);
        String[] list_item = list_items.split("%");

        Log.d("--MSG--","{"+list_items+"}");
        mua.myuploads_li = li;

        mvh.title.setText(list_item[1]);
        mvh.id.setText(list_item[0]);

        if(list_item[2].equals("1")) {
            mvh.state.setBackgroundColor(mua.getResources().getColor(R.color.md_green_600));
            mvh.state.setTextColor(mua.getResources().getColor(R.color.md_text_white));
            mvh.state.setText("Active");
        }else{

            mvh.state.setBackgroundColor(mua.getResources().getColor(R.color.md_red_300));
            mvh.state.setTextColor(mua.getResources().getColor(R.color.md_text_white));
            mvh.state.setText("Inactive");
        }
        BitmapAsync ba = new BitmapAsync(mvh.img,mua);
        ba.execute(list_item[3]);
        //convertView.setTag(row);

        return row;
    }

    public void getMyUploads(){
        request = 0;
        mupa = this;
        SyncThread st = new SyncThread(mupa);
        st.execute();
    }

    public void stringErrorMsg(String s) {
        mua.stringErrorMsg(s);
    }

    public void JSONResponse(String s) {

        try {
            JSONArray ar = new JSONArray(s);
            JSONObject jo;
            String box_row;

            for(int i = 0;i<ar.length();i++){
                jo = ar.getJSONObject(i);
                box_row = jo.getInt("img_id")+"%"+jo.getString("title")+"%"+jo.getInt("state")+"%"+jo.getString("url");
                li.add(box_row);
                Log.d("--MSG--", box_row);
            }

            mua.myup_msg_content.setVisibility(View.GONE);

            mua.myup_list_content.setVisibility(View.VISIBLE);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void stringResponse(String s) {
        mua.stringResponse(s);
    }

    public void setProgressBarVisible(){
        mua.myup_list_content.setVisibility(View.GONE);
        mua.myup_msg_content.setVisibility(View.GONE);
        mua.myup_loading_content.setVisibility(View.VISIBLE);
    }
    public void setProgressBarGone(){
        mua.myup_loading_content.setVisibility(View.GONE);
    }
}

package com.im.fragments;

import android.app.Dialog;
import android.graphics.Typeface;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.im.ImageLakeLK.R;
import com.im.ImageLakeLK.SettingActivity;
import com.im.adapter.CategoryAdapter;
import com.im.adapter.ImageSettingAdapter;
import com.im.adapter.UserManagementAdapter;
import com.im.sync.SyncThread;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RLN on 5/4/2015.
 */
public class ImageSettingFragment extends Fragment implements View.OnClickListener{

    public static final String STYLE_TYPE ="SliceTypes";
    public static final String STYLE_REMOVE ="removesliceimage";
    public static final String STYLE_ALL ="allimageSlices";
    public static final String STYLE_ADD ="addsliceimage";
    public static final String ALL_CATEGORY ="AllCategories";
    public static final String ADD_CATEGORY ="newCategories";

    ImageSettingFragment isf;
    SyncThread st;
    ImageSettingAdapter isa;
    CategoryAdapter ca;

    EditText imgset_cat;
    Button imgset_add,imgset_show,imgset_add_slice,imgset_slice_cancel,imgset_slice_remove,imgset_all_cancel;
    ListView imgset_slice_all,imgset_cat_all;
    Spinner imgset_slice;
    TableLayout imgset_slice_size;
    ArrayAdapter<String> adapter;

    public String category ="";

    public int slice_type =0;
    public String type ="";
    public int slice_id =0;
    List<String> slice_all;
    public List<String> slice_show;
    public int cred_id =0;
    SettingActivity sa = new SettingActivity();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.imagesetting_layout,container,false);

        isf = this;

        imgset_cat =(EditText)v.findViewById(R.id.imgset_cat);

        imgset_add =(Button)v.findViewById(R.id.imgset_add);
        imgset_add.setOnClickListener(this);
        imgset_show =(Button)v.findViewById(R.id.imgset_show);
        imgset_show.setOnClickListener(this);

        imgset_add_slice =(Button)v.findViewById(R.id.imgset_add_slice);
        imgset_add_slice.setOnClickListener(this);
        imgset_add_slice.setVisibility(View.GONE);

        imgset_slice_size =(TableLayout)v.findViewById(R.id.imgset_slice_size);

        imgset_slice =(Spinner)v.findViewById(R.id.imgset_slice);
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,getActivity().getResources().getStringArray(R.array.slice));
        imgset_slice.setAdapter(adapter);
        imgset_slice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0){

                    slice_type = position;
                    loadSlices();

                }else if(position == 0){

                   imgset_slice_size.removeAllViewsInLayout();
                   imgset_add_slice.setVisibility(View.GONE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });






        return v;
    }

    public void stringErrorMsg(String s) {
        Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
    }

    public void JSONResponse(String s) {
        try {
            if(type.equals(STYLE_TYPE)) {
                JSONArray ja = new JSONArray(s);
                JSONObject jo;
                String box_list;
                slice_all = new ArrayList<String>();

                imgset_slice_size.setStretchAllColumns(true);
                imgset_slice_size.setShrinkAllColumns(true);


                TableRow.LayoutParams params = new TableRow.LayoutParams();
                params.span = 1;

                LinearLayout.LayoutParams img_layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                imgset_slice_size.removeAllViewsInLayout();

                if (ja.length() != 0) {
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        box_list = jo.getString("cid") + "/" + jo.getString("csize") + "/" + jo.getString("sid");
                        slice_all.add(box_list);
                        Log.d("--MSG--", box_list);

                        final int x = Integer.parseInt(jo.getString("sid"));
                        TableRow title_Row = new TableRow(getActivity());
                        title_Row.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final Dialog dialog = new Dialog(getActivity());
                                dialog.setContentView(R.layout.imageslice_dialog);
                                dialog.setTitle("Remove");

                                imgset_slice_cancel = (Button) dialog.findViewById(R.id.imgset_slice_cancel);
                                imgset_slice_cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                imgset_slice_remove = (Button) dialog.findViewById(R.id.imgset_slice_remove);
                                imgset_slice_remove.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        slice_id = x;
                                        type = STYLE_REMOVE;

                                        st = new SyncThread(isf);
                                        st.execute();
                                    }
                                });

                                dialog.show();
                            }
                        });


                        TextView slice_size = new TextView(getActivity());
                        slice_size.setText(jo.getString("csize"));
                        slice_size.setBackground(getActivity().getResources().getDrawable(R.drawable.warning_panel));
                        slice_size.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
                        slice_size.setLayoutParams(img_layout_params);

                        title_Row.addView(slice_size, params);

                        imgset_slice_size.addView(title_Row);

                    }
                }
                imgset_add_slice.setVisibility(View.VISIBLE);
            }else if(type.equals(STYLE_ALL)){


            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stringResponse(String s) {
        if(s.equals("Remove_Slice_Successful...")){
            loadSlices();
            Toast.makeText(getActivity(), "Successfully Removed.", Toast.LENGTH_LONG).show();

        }else if(s.equals("Add_Slice_Successful...")){
            loadSlices();
            Toast.makeText(getActivity(), "Successfully Added.", Toast.LENGTH_LONG).show();
        }else if(s.equals("Add_Category_Successful...")){
            loadSlices();
            Toast.makeText(getActivity(), "Successfully Added.", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.imgset_add_slice){
            slice_show =new ArrayList<String>();

            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.sliceall_dialog);
            dialog.setTitle("Add Slice");

            type = STYLE_ALL;

            imgset_slice_all =(ListView)dialog.findViewById(R.id.imgset_slice_all);
            isa = new ImageSettingAdapter(getActivity(),type,isf);
            imgset_slice_all.setAdapter(isa);
            imgset_slice_all.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String ll =slice_show.get(position);
                    String lll[] = ll.split("/");
                    cred_id = Integer.parseInt(lll[0]);

                    type = STYLE_ADD;
                    st = new SyncThread(isf);
                    st.execute();
                    dialog.dismiss();

                }
            });

            imgset_all_cancel =(Button)dialog.findViewById(R.id.imgset_all_cancel);
            imgset_all_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();

        }else if(v.getId() == R.id.imgset_add){
            type = ADD_CATEGORY;
            category = imgset_cat.getText().toString();
            category = category.trim();
            st = new SyncThread(isf);
            st.execute();
        }else if(v.getId() == R.id.imgset_show){
            type = ALL_CATEGORY;
            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.categoryall_dialog);
            dialog.setTitle("Show Categories");

            imgset_cat_all =(ListView)dialog.findViewById(R.id.imgset_cat_all);
            ca = new CategoryAdapter(getActivity(),type,isf);
            imgset_cat_all.setAdapter(ca);
            dialog.show();
        }
    }

    public void loadSlices(){

        type = STYLE_TYPE;
        st = new SyncThread(isf);
        st.execute();


    }
}

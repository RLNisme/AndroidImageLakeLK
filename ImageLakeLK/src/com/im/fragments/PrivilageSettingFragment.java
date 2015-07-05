package com.im.fragments;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.im.adapter.ImageSettingAdapter;
import com.im.adapter.PrivilegeSettingAdapter;
import com.im.sync.SyncThread;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RLN on 5/6/2015.
 */
public class PrivilageSettingFragment extends Fragment implements View.OnClickListener{

    public static final String USER_TYPE ="AllUserTypes";
    public static final String USER_ADD ="AddUserTypes";
    public static final String USER_TYPE_PRIVILEGE ="TypePrivilege";
    public static final String USER_REMOVE ="remvinf";
    public static final String USER_ALL_INTERFACE ="AllInterface";
    public static final String USER_ADD_INTERFACE ="addinf";

    PrivilageSettingFragment psf;
    SyncThread st;
    PrivilegeSettingAdapter psa;

    EditText priset_type;
    Button priset_add,priset_add_privilege,imgset_slice_cancel,imgset_slice_remove,priset_cancel;
    ListView priset_all;
    Spinner priset_privi;
    TableLayout priviset_prvi_selec;
    ArrayAdapter<String> adapter;

    ArrayList<String> privilege = new ArrayList<String>();
    ArrayList<String> privilege_all;

    ArrayList<String> privilege_show = new ArrayList<String>();
    ArrayList<String> privilege_single;

    public List<String> slice_show;

    public String type ="";
    public String new_user_type ="";
    public int user_type_id =0;
    public int privi_id = 0;
    public int interface_id =0;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.privilagesetting_layout,container,false);

        psf = this;

        priset_type =(EditText)v.findViewById(R.id.priset_type);

        priset_add =(Button)v.findViewById(R.id.priset_add);
        priset_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_user_type = priset_type.getText().toString();
                if(new_user_type.trim().equals("")){
                    Toast.makeText(getActivity(),"Please enter user type.",Toast.LENGTH_LONG).show();
                }else{
                    type = USER_ADD;
                    st = new SyncThread(psf);
                    st.execute();
                }
            }
        });

        priset_add_privilege =(Button)v.findViewById(R.id.priset_add_privilege);
        priset_add_privilege.setVisibility(View.GONE);
        priset_add_privilege.setOnClickListener(this);

        priset_privi =(Spinner)v.findViewById(R.id.priset_privi);


        priviset_prvi_selec =(TableLayout)v.findViewById(R.id.priset_privi_selec);

        loadUserTypes();

        return v;
    }

    public void loadUserTypes(){
        type = USER_TYPE;
        new_user_type = null;
        st = new SyncThread(psf);
        st.execute();
    }

    public void stringErrorMsg(String s) {
        Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
    }

    public void JSONResponse(String s) {

        try {
            if(type.equals(USER_TYPE)) {
                JSONArray ja = new JSONArray(s);
                JSONObject jo;
                String box_list;
                privilege_all = new ArrayList<String>();
                if (ja.length() > 0) {
                    privilege.add("0/Select a User Type");
                    privilege_all.add("Select a User Type");
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        box_list = jo.getString("id") + "/" + jo.getString("type");
                        privilege.add(box_list);
                        privilege_all.add(jo.getString("type"));
                    }
                    adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, privilege_all);
                    priset_privi.setAdapter(adapter);
                    priset_privi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position > 0) {

                                String sl = privilege.get(position);
                                String sll[] = sl.split("/");

                                user_type_id = Integer.parseInt(sll[0]);
                               loadSinglePrivileges();

                            } else {
                                priviset_prvi_selec.removeAllViewsInLayout();
                                priset_add_privilege.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else {
                    stringResponse("No item found.");
                }
            }else if(type.equals(USER_TYPE_PRIVILEGE)) {
                JSONArray ja = new JSONArray(s);
                JSONObject jo;
                String box_list;

                priviset_prvi_selec.setStretchAllColumns(true);
                priviset_prvi_selec.setShrinkAllColumns(true);

                TableRow.LayoutParams params = new TableRow.LayoutParams();
                params.span = 1;

                LinearLayout.LayoutParams img_layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                priviset_prvi_selec.removeAllViewsInLayout();

                privilege_single = new ArrayList<String>();
                if (ja.length() > 0) {
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        box_list = jo.getString("id") + "/" + jo.getString("name")+ "/" + jo.getString("state");
                        privilege_single.add(box_list);

                        TableRow title_Row = new TableRow(getActivity());
                        TextView slice_size = new TextView(getActivity());
                        slice_size.setText(jo.getString("name"));
                        slice_size.setBackground(getActivity().getResources().getDrawable(R.drawable.info_panel));
                        slice_size.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
                        slice_size.setLayoutParams(img_layout_params);

                        final int x = Integer.parseInt(jo.getString("id"));

                        title_Row.addView(slice_size, params);
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

                                        privi_id = x;
                                        type = USER_REMOVE;

                                        st = new SyncThread(psf);
                                        st.execute();
                                        dialog.dismiss();
                                    }
                                });

                                dialog.show();
                            }
                        });
                        priviset_prvi_selec.addView(title_Row);

                    }
                    priset_add_privilege.setVisibility(View.VISIBLE);
                }else{
                    priset_add_privilege.setVisibility(View.VISIBLE);
                    priviset_prvi_selec.removeAllViewsInLayout();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadSinglePrivileges(){
        type = USER_TYPE_PRIVILEGE;
        st = new SyncThread(psf);
        st.execute();
    }

    public void stringResponse(String s) {
        if(s.equals("Add_User_Type_Successfully...")){
            loadUserTypes();
            Toast.makeText(getActivity(), "Successfully added...", Toast.LENGTH_LONG).show();
        }else if(s.equals("Remve_Successfully...")){
            loadSinglePrivileges();
            Toast.makeText(getActivity(), "Successfully removed...", Toast.LENGTH_LONG).show();
        }else if(s.equals("Int_Add_Successfully...")){
            loadSinglePrivileges();
            Toast.makeText(getActivity(), "Successfully added...", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.priset_add_privilege){
            slice_show =new ArrayList<String>();

            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.privilageadd_dialog);
            dialog.setTitle("Add Privilege");

            type = USER_ALL_INTERFACE;

            priset_all =(ListView)dialog.findViewById(R.id.priset_all);
            psa = new PrivilegeSettingAdapter(getActivity(),type,psf);
            priset_all.setAdapter(psa);
            priset_all.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String ll =slice_show.get(position);
                    String lll[] = ll.split("/");
                    interface_id = Integer.parseInt(lll[0]);

                    type = USER_ADD_INTERFACE;
                    st = new SyncThread(psf);
                    st.execute();
                    dialog.dismiss();

                }
            });

            priset_cancel =(Button)dialog.findViewById(R.id.priset_cancel);
            priset_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
}

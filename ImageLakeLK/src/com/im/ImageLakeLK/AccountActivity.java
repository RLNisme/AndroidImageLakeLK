package com.im.ImageLakeLK;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.*;
import com.im.adapter.MyNavigationAdapter;
import com.im.sync.SyncThread;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * Created by RLN on 4/2/2015.
 */
public class AccountActivity extends Activity implements AdapterView.OnItemClickListener,DrawerLayout.DrawerListener,View.OnClickListener{

    private final int REQUEST_EDIT_PROFILE = 1;

    public SQLiteDatabase sqlitDB;
    //user login DB
    public final String DATABASE_TABLE ="signin";
    public final String KEY_ID = "id";
    public final String KEY_USER_ID = "uid";
    public final String KEY_USER_TYPE = "utype";
    public final String KEY_USER_NAME = "un";
    public final String KEY_PASSWORD = "pw";
    public final String KEY_FN ="fn";
    public final String KEY_LN ="ln";
    public final String KEY_EM ="em";
    public final String KEY_STATE = "state";

    //packages DB
    public final String DATABASE_PACKAGE_TABLE = "packages";
    public final String KEY_TYPE ="type";
    public final String KEY_TITLE ="title";
    public final String KEY_P1 = "p1";
    public final String KEY_P2 = "p2";
    public final String KEY_P3 = "p3";
    public final String KEY_ISNULL = "is_null";

    //admin profile DB
    public final String DATABASE_ADMIN_DETAILS_TABLE = "admin";
    public final String KEY_INCOME = "income";
    public final String KEY_USERS = "users";
    public final String KEY_IMGCOUNT = "imgcount";


    private DrawerLayout drawerLayout;
    private ListView listView;

    public ArrayList<String> privilages_li = new ArrayList<String>();//privilages list

    private MyNavigationAdapter myAdapter;
    AccountActivity aa;
    private ActionBarDrawerToggle drawerListner;

    //Layout management
    private LinearLayout adminContent,userContent,subActivePackage,subInactivePackage,crdActivePackage,crdInactivePackage;

    private TextView adminIncomeValue,adminUsersValue,adminImageValue;

    private TextView activeSubTitle,inactiveSubTitle,activeSubPurchDay,activeSubExpirDay,activeSubMsg,inactiveSubMsg,
                     activeCrdTitle,inactiveCrdTitle,activeCrdPurchDay,activeCrdExpirDay,activeCrdMsg,inactiveCrdMsg;

    public SyncThread st;
    public Intent i;
    private TextView userName_text;

    public long row_id;
    public int user_id;
    public String user_name;
    public String fn;
    public String ln;
    public String pw;
    public String em;
    public int user_type;
    public int user_state;
    //Admin users
    public double income;
    public int users;
    public int images;
    //normal users
    //Subscription package
    public int issNull;
    public String subscription;
    public String ps1,ps2,ps3;
    //Credit packages
    public int iscNull;
    public String credits;
    public String pc1,pc2,pc3;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity);

        aa = this;

        i = getIntent();
        user_id = i.getIntExtra("id",-1);
       // user_name = i.getStringExtra("un");
        user_type = i.getIntExtra("user_type", -1);
        Log.d("ACCCCCCCCCCC",user_type+"");
        Log.d("ACCCCCCCCCCC",user_id+"");
       // user_state = i.getIntExtra("state",-1);
       // fn = i.getStringExtra("fn");
       // ln = i.getStringExtra("ln");
       // pw =i.getStringExtra("pw");
       // em =i.getStringExtra("em");

        //if(user_type != 2 && user_type != 3){

           /* income = i.getDoubleExtra("income", -1);
            users = i.getIntExtra("users",-1);
            images = i.getIntExtra("imgcount",-1);*/
            //DBOpen();
            //getAdminDetails(DATABASE_ADMIN_DETAILS_TABLE);

        //}else{

            /*issNull = i.getStringExtra("issNull");
            if(issNull.equals("yes")){

                subscription = i.getStringExtra("subscription");
                ps1 = i.getStringExtra("ps1");

            }else{
                subscription = i.getStringExtra("subscription");
                ps1 = i.getStringExtra("ps1");
                ps2 = i.getStringExtra("ps2");
                ps3 = i.getStringExtra("ps3");
            }

            iscNull = i.getStringExtra("iscNull");
            if(iscNull.equals("yes")){

                credits = i.getStringExtra("credits");
                pc1 = i.getStringExtra("pc1");

            }else{
                credits = i.getStringExtra("credits");
                pc1 = i.getStringExtra("pc1");
                pc2 = i.getStringExtra("pc2");
                pc3 = i.getStringExtra("pc3");
            }*/

        //}

        drawerLayout = (DrawerLayout)findViewById(R.id.menu_layout);
        drawerListner = new ActionBarDrawerToggle(this,drawerLayout,R.drawable.ic_action_sort_by_size,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.setDrawerListener(drawerListner);

        getActionBar().setHomeButtonEnabled(true);// this is goin to enable the home button
        getActionBar().setDisplayHomeAsUpEnabled(true);// this will show home screen of your app

        //planets = getResources().getStringArray(R.array.planets);
        listView = (ListView)findViewById(R.id.drawerlist);
       // listView.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,planets));

       // myAdapter = new MyNavigationAdapter(this);


        Log.d("--MSG--","before GetUserDetails");
        DBOpen();
        getUserDetails(DATABASE_TABLE);//--------------------------------------------
        //getPackagesDetais(DATABASE_PACKAGE_TABLE); //--------------------------------------------


        userName_text = (TextView)findViewById(R.id.nav_username);
        userName_text.setText(user_name);

        //layout management
        adminContent = (LinearLayout)findViewById(R.id.admin_content);//admin main content
        adminContent.setVisibility(View.GONE);
        adminIncomeValue = (TextView)findViewById(R.id.admin_income_value);
        adminUsersValue = (TextView)findViewById(R.id.admin_users_value);
        adminImageValue = (TextView)findViewById(R.id.admin_images_value);

        userContent = (LinearLayout)findViewById(R.id.users_content);//user main content
        userContent.setVisibility(View.GONE);

        subActivePackage =(LinearLayout)findViewById(R.id.active_sub_pack);//Active subscription package
        subActivePackage.setVisibility(View.GONE);

        activeSubTitle = (TextView)findViewById(R.id.active_sub_pack_title);//active
        activeSubPurchDay = (TextView)findViewById(R.id.active_sub_purch_day);// components
        activeSubExpirDay = (TextView)findViewById(R.id.active_sub_expi_day);//
        activeSubMsg = (TextView)findViewById(R.id.active_sub_msg);//


        subInactivePackage = (LinearLayout)findViewById(R.id.inactive_sub);//Inactive subscription package
        subInactivePackage.setVisibility(View.GONE);

        inactiveSubTitle = (TextView)findViewById(R.id.inactive_sub_title);//inactive
        inactiveSubMsg = (TextView)findViewById(R.id.inactive_sub_msg);// components


        crdActivePackage =(LinearLayout)findViewById(R.id.active_crd_pack);//Active credit pack
        crdActivePackage.setVisibility(View.GONE);

        activeCrdTitle = (TextView)findViewById(R.id.active_crd_pack_title);//active
        activeCrdPurchDay = (TextView)findViewById(R.id.active_crd_purch_day);//components
        activeCrdExpirDay = (TextView)findViewById(R.id.active_crd_expi_day);//
        activeCrdMsg = (TextView)findViewById(R.id.active_crd_msg);//

        crdInactivePackage =(LinearLayout)findViewById(R.id.inactive_crd);// Inactive credit pack
        crdInactivePackage.setVisibility(View.GONE);

        inactiveCrdTitle = (TextView)findViewById(R.id.inactive_crd_title);
        inactiveCrdMsg = (TextView)findViewById(R.id.inactive_crd_msg);

        myAdapter = new MyNavigationAdapter(this,user_type,aa);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(this);

        getDetails();


    }

    public void getDetails(){
        st = new SyncThread(aa);
        st.execute();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerListner.onConfigurationChanged(newConfig);
        Log.d("--MSG--","On Configuration Change");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(drawerListner.onOptionsItemSelected(item)){
            return true;
        }
        Log.d("--MSG--","On Option Item select");
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerListner.syncState();
        Log.d("--MSG--", "On Post Create");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(this,planets[position]+" was selected",Toast.LENGTH_LONG).show();
        selectItem(position);
        Log.d("--MSG--", "On Item Click");
    }

    public void selectItem(int position){
        //listView.setItemChecked(position,true);

        String privilage = privilages_li.get(position);



        if(privilage.equals("Light Box")) {

            Log.d("--MSG--", privilage);
            Intent i = new Intent(this, LightBoxActivity.class);
            i.putExtra("id",user_id);
            startActivity(i);

        }else if(privilage.equals("Download History")){

            Log.d("--MSG--", privilage);
            Intent i = new Intent(this,DownloadHistoryActivity.class);
            i.putExtra("id",user_id);
            startActivity(i);

        }else if(privilage.equals("Edit Profile")){

            Log.d("--MSG--", privilage);
            Intent i = new Intent(this,EditProfileActivity.class);
            i.putExtra("row_id",row_id);
            i.putExtra("id",user_id);
            i.putExtra("un",user_name);
            i.putExtra("fn",fn);
            i.putExtra("ln",ln);
            i.putExtra("pw",pw);
            i.putExtra("em",em);
            i.putExtra("type",user_type);
            startActivity(i);

        }else if(privilage.equals("Upload")){
            Log.d("--MSG--", privilage);
            Intent i = new Intent(this,UploadActivity.class);
            i.putExtra("id",user_id);

            startActivity(i);
        }else if(privilage.equals("My Uploads")){
            Intent i = new Intent(this,MyUploadsActivity.class);
            i.putExtra("id",user_id);
            startActivity(i);
            Log.d("--MSG--", privilage);
        }else if(privilage.equals("Profit")){
            Log.d("--MSG--", privilage);
            Intent i = new Intent(this,ProfitActivity.class);
            i.putExtra("id",user_id);
            startActivity(i);
        }else if(privilage.equals("Become a contributor")){
            Log.d("--MSG--", privilage);
            Intent i = new Intent(this,BecomeaContributor.class);
            i.putExtra("row_id",row_id);
            i.putExtra("user_type",user_type);
            i.putExtra("id",user_id);
            startActivity(i);
        }else if(privilage.equals("Purchase Details")){
            Log.d("--MSG--", privilage);

            Context context = this;
            final Dialog dialog = new Dialog(context);
            dialog.setTitle("Choose");
            dialog.setContentView(R.layout.purchasemanagement_main_dialog);

            ImageButton vimg,vpac;
            vimg =(ImageButton)dialog.findViewById(R.id.via_img);
            vimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplication(),PurchaseViaImagesActivity.class);
                    startActivity(i);
                }
            });
            vpac =(ImageButton)dialog.findViewById(R.id.via_pac);
            vpac.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplication(),PurchaseViaPackagesActivity.class);
                    startActivity(i);
                }
            });
            dialog.show();

        }else if(privilage.equals("User Management")){

            Log.d("--MSG--", privilage);
            Intent i=new Intent(this,UserManagementActivity.class);
            i.putExtra("id",user_id);
            startActivity(i);

        }else if(privilage.equals("Upload Management")){

            Log.d("--MSG--", privilage);
            Intent i = new Intent(this,UploadManagementActivity.class);
            i.putExtra("id",user_id);
            startActivity(i);

        }else if(privilage.equals("Image Management")){
            Log.d("--MSG--", privilage);
            Intent i = new Intent(this,ImageManagementActivity.class);
            i.putExtra("id",user_id);
            startActivity(i);
        }else if(privilage.equals("Setting")){
            Intent i = new Intent(this,SettingActivity.class);
            i.putExtra("id",user_id);
            startActivity(i);
            Log.d("--MSG--", privilage);
        }else if(privilage.equals("Earning Orders")){

            Log.d("--MSG--", privilage);
            Intent i = new Intent(this,EarningManagementActivity.class);
            i.putExtra("id",user_id);
            startActivity(i);

        }else if(privilage.equals("Upload Images")){

            Log.d("--MSG--", privilage);
            Intent i = new Intent(this,UploadActivity.class);
            i.putExtra("id",user_id);

            startActivity(i);
        }


    }



    @Override
    public void onDrawerSlide(View view, float v) {

    }

    @Override
    public void onDrawerOpened(View view) {
       // Toast.makeText(this,"Opened",Toast.LENGTH_LONG).show();
        Log.d("--MSG--", "Inside the Open");
    }

    @Override
    public void onDrawerClosed(View view) {
        Toast.makeText(this,"Closed",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDrawerStateChanged(int i) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mif2 = getMenuInflater();
        mif2.inflate(R.menu.account_menu,menu);
        menu.findItem(R.id.profil).setVisible(false);
        if(user_type != 2 && user_type!= 3) {
            menu.findItem(R.id.next_activity1).setVisible(false);
            menu.findItem(R.id.next_activity2).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        Log.d("--MSG--",featureId+""+item.getTitle());
        if(item.getTitle().equals("Next")){
            Intent i = new Intent(AccountActivity.this,ImageSearchActivity.class);
            i.putExtra("uid",user_id);
            i.putExtra("ut",user_type);
            startActivity(i);

        }else if(item.getTitle().equals("Cart")){
            Intent i = new Intent(AccountActivity.this,CartActivity.class);
            i.putExtra("uid",user_id);
            i.putExtra("ut",user_type);
            startActivity(i);
        }else if(item.getTitle().equals("Pack")){
            Intent i = new Intent(AccountActivity.this,PackagesActivity.class);
            i.putExtra("uid",user_id);
            i.putExtra("ut",user_type);
            startActivity(i);
        }else if(item.getTitle().equals("Signout")){
            signOut();
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onClick(View v) {
        Log.d("--MSG--","Home");
        if(v.getId() == R.id.admin_income){

        }
    }

    public void getUserDetails(String tableName){
        String[] columsName = new String[]{KEY_ID,KEY_USER_ID,KEY_USER_NAME,KEY_PASSWORD,KEY_FN,KEY_LN,KEY_EM,KEY_USER_TYPE,KEY_STATE};
        Cursor c = sqlitDB.query(tableName,columsName,null,null,null,null,null);

        int cid = c.getColumnIndex(KEY_ID);
        int cuid = c.getColumnIndex(KEY_USER_ID);
        int cun = c.getColumnIndex(KEY_USER_NAME);
        int cpw = c.getColumnIndex(KEY_PASSWORD);
        int cfn = c.getColumnIndex(KEY_FN);
        int cln = c.getColumnIndex(KEY_LN);
        int cem = c.getColumnIndex(KEY_EM);
        int cut = c.getColumnIndex(KEY_USER_TYPE);
        int cst = c.getColumnIndex(KEY_STATE);

        for (c.moveToFirst(); !c.isAfterLast();c.moveToNext()){
            row_id = c.getLong(cid);
            user_name = c.getString(cun);
            fn = c.getString(cfn);
            ln = c.getString(cln);
            pw = c.getString(cpw);
            em = c.getString(cem);
            user_state = c.getInt(cst);


        }
    }

    public void getAdminDetails(String tableName){
        String[] columsName = new String[]{KEY_ID,KEY_INCOME,KEY_USERS,KEY_IMGCOUNT};
        Cursor c = sqlitDB.query(tableName,columsName,null,null,null,null,null);

        int cid = c.getColumnIndex(KEY_ID);
        int cinc = c.getColumnIndex(KEY_INCOME);
        int curs = c.getColumnIndex(KEY_USERS);
        int cimg = c.getColumnIndex(KEY_IMGCOUNT);
        DecimalFormat df = new DecimalFormat("#.00");
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){

            income = c.getDouble(cinc);
            users = c.getInt(curs);
            images = c.getInt(cimg);
        }
    }


    public void getPackagesDetais(String tableName){
        String[] columsName = new String[]{KEY_ID,KEY_TYPE,KEY_TITLE,KEY_P1,KEY_P2,KEY_P3,KEY_ISNULL};
        Cursor c = sqlitDB.query(tableName,columsName,null,null,null,null,null);

        int cid = c.getColumnIndex(KEY_ID);
        int cty = c.getColumnIndex(KEY_TYPE);
        int cti = c.getColumnIndex(KEY_TITLE);
        int cp1 = c.getColumnIndex(KEY_P1);
        int cp2 = c.getColumnIndex(KEY_P2);
        int cp3 = c.getColumnIndex(KEY_P3);
        int cnul = c.getColumnIndex(KEY_ISNULL);


        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){

            if(c.getInt(cty)== 1){

                if(c.getInt(cnul)== 1){
                    subscription = c.getString(cti);
                    ps1 = c.getString(cp1);
                    issNull = 1;
                }else{
                    subscription = c.getString(cti);
                    ps1 = c.getString(cp1);
                    ps2 = c.getString(cp2);
                    ps3 = c.getString(cp3);
                    issNull = 0;
                }

            }else{

                if(c.getInt(cnul)== 1){
                    credits = c.getString(cti);
                    pc1 = c.getString(cp1);
                    iscNull = 1;

                }else{
                    credits = c.getString(cti);
                    pc1 = c.getString(cp1);
                    pc2 = c.getString(cp2);
                    pc3 = c.getString(cp3);
                    iscNull = 0;
                }

            }

        }
    }


    public void DBOpen(){
        sqlitDB = openOrCreateDatabase("ImageLKDB",MODE_PRIVATE,null);
    }

    public void DBClose(){

        sqlitDB.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {



        super.onActivityResult(requestCode, resultCode, data);
    }

    public void stringErrorMsg(String s) {
        Toast.makeText(aa,s,Toast.LENGTH_LONG).show();
    }

    public void JSONResponse(String s) {
        try {
            JSONObject jo = new JSONObject(s);
            if(user_type != 2 && user_type != 3){
            adminContent.setVisibility(View.VISIBLE);
            adminIncomeValue.setText("$ " + jo.getString("income") + "");
            adminUsersValue.setText(jo.getString("users")+"");
            adminImageValue.setText(jo.getString("imgcount")+"");
            }else{
                userContent.setVisibility(View.VISIBLE);
                if(jo.getString("issNull").equals("yes")){
                    subInactivePackage.setVisibility(View.VISIBLE);
                    inactiveSubTitle.setText(jo.getString("subscription"));
                    inactiveSubMsg.setText(jo.getString("ps1"));
                }else{
                    subActivePackage.setVisibility(View.VISIBLE);
                    activeSubTitle.setText(jo.getString("subscription"));
                    activeSubPurchDay.setText(jo.getString("ps1"));
                    activeSubExpirDay.setText(jo.getString("ps2"));
                    activeSubMsg.setText(jo.getString("ps3"));
                }

                if(jo.getString("iscNull").equals("yes")){
                    crdInactivePackage.setVisibility(View.VISIBLE);
                    inactiveCrdTitle.setText(jo.getString("credits"));
                    inactiveCrdMsg.setText(jo.getString("pc1"));
                }else{
                    crdActivePackage.setVisibility(View.VISIBLE);
                    activeCrdTitle.setText(jo.getString("credits"));
                    activeCrdPurchDay.setText(jo.getString("pc1"));
                    activeCrdExpirDay.setText(jo.getString("pc2"));
                    activeCrdMsg.setText(jo.getString("pc3"));
                }
            }



        }catch (Exception e){
            e.printStackTrace();

        }
    }

    public void stringResponse(String s) {
        Toast.makeText(aa,s,Toast.LENGTH_LONG).show();
    }

    private void signOut(){
        deletEntity();
        Intent i = new Intent(aa,MainActivity.class);
        //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        this.finish();
        startActivity(i);
    }

    private void deletEntity(){

        sqlitDB.delete(DATABASE_TABLE,KEY_USER_ID+"="+user_id,null);
    }
}

package com.im.ImageLakeLK;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.im.Model.Category;
import com.im.dialogbox.MyUploadDialog;
import com.im.dialogbox.UploadDialog;
import com.im.sync.RealPathUtil;
import com.im.sync.SyncThread;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RLN on 4/20/2015.
 */
public class UploadActivity extends Activity implements AdapterView.OnItemSelectedListener,View.OnClickListener{

    Intent i;
    Spinner cate;
    public static final int SELECT_IMAGE = 4;
    public LinearLayout upload_content,upload_msg_content,upload_progress,upload_load;
    public ProgressBar pl,con_load;
    ImageView picture;
    EditText tit;
    TextView upload_msg,img_dimention,img_size;
    Button refresh,upload,selectImage;
    UploadActivity ua;
    public int requestType;

    public List<String> li;
    public List<String> categories;
    public List<NameValuePair> nv_li;

    public int user_id = 0;
    public String title ="";
    public String img_name = "";
    public int category_id = 0;
    public String dimention = "";
    public double size_b = 0.0;
    public String image = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_layout);

        ua = this;

        i=getIntent();
        user_id = i.getIntExtra("id",-1);

        img_dimention =(TextView)findViewById(R.id.img_dimention);
        img_dimention.setVisibility(View.GONE);

        tit =(EditText)findViewById(R.id.up_img_title);

        img_size =(TextView)findViewById(R.id.img_size);
        img_size.setVisibility(View.GONE);

        cate =(Spinner)findViewById(R.id.up_cat);
        upload_content = (LinearLayout)findViewById(R.id.upload_content);
        upload_content.setVisibility(View.GONE);

        upload_msg_content =(LinearLayout)findViewById(R.id.upload_msg_content);
        upload_msg_content.setVisibility(View.GONE);
        upload_msg =(TextView)findViewById(R.id.upload_msg);


        upload_progress =(LinearLayout)findViewById(R.id.upload_load_content);
        upload_progress.setVisibility(View.GONE);


        upload_load =(LinearLayout)findViewById(R.id.img_up_load);
        upload_load.setVisibility(View.GONE);

        pl =(ProgressBar)findViewById(R.id.up_progress);
        pl.setProgress(0);
        con_load=(ProgressBar)findViewById(R.id.upload_loading);

        refresh =(Button)findViewById(R.id.upload_btn_refresh);
        refresh.setOnClickListener(this);

        selectImage =(Button)findViewById(R.id.up_selectimage);
        selectImage.setOnClickListener(this);

        upload =(Button)findViewById(R.id.up_btn_upload);
        upload.setOnClickListener(this);

        picture =(ImageView)findViewById(R.id.up_image);

        loadCategories();


    }


    public void loadCategories(){
        requestType = 0;

        upload_content.setVisibility(View.GONE);
        upload_msg_content.setVisibility(View.GONE);
        upload_load.setVisibility(View.GONE);
        upload_progress.setVisibility(View.VISIBLE);

        SyncThread st = new SyncThread(ua);
        st.execute();

    }

    public void stringErrorMsg(String s) {

        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
        upload_content.setVisibility(View.GONE);
        upload_load.setVisibility(View.GONE);
        upload_msg_content.setVisibility(View.VISIBLE);
        upload_progress.setVisibility(View.GONE);

        upload_msg.setText(s);
    }

    public void JSONResponse(String s) {

        li = new ArrayList<String>();
        categories = new ArrayList<String>();
        String c;
        String cat;
        try {
            JSONArray ja = new JSONArray(s);
            JSONObject jo;
            c = 0 + "%"+ "Select an image category";
            cat = "Select an image category";
            li.add(c);
            categories.add(cat);
            for (int i =0;i<ja.length();i++){
                jo = ja.getJSONObject(i);
                 c = jo.getInt("id")+"%"+jo.getString("cat");
                 cat = jo.getString("cat");
                li.add(c);
                categories.add(cat);
            }

            upload_content.setVisibility(View.VISIBLE);
            upload_msg_content.setVisibility(View.GONE);
            upload_progress.setVisibility(View.GONE);
            upload_load.setVisibility(View.GONE);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(categories != null){



            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categories);
            cate.setAdapter(adapter);
            cate.setOnItemSelectedListener(this);
        }

    }

    public void stringResponse(String s) {

        if(s.equals("Successful...")){
            Toast.makeText(this,s,Toast.LENGTH_LONG).show();
            upload_content.setVisibility(View.VISIBLE);
            tit.setText("");
            picture.setImageResource(R.drawable.ic_action_picture);

            dimention = "";
            category_id = 0;
            image = null;
            img_name = "";
            size_b = 0.0;

            img_dimention.setVisibility(View.GONE);
            img_size.setVisibility(View.GONE);

            upload_msg_content.setVisibility(View.GONE);
            upload_progress.setVisibility(View.GONE);
            upload_load.setVisibility(View.GONE);
        }else{
            Toast.makeText(this,s,Toast.LENGTH_LONG).show();
            upload_content.setVisibility(View.VISIBLE);

            upload_msg_content.setVisibility(View.GONE);
            upload_progress.setVisibility(View.GONE);
            upload_load.setVisibility(View.GONE);
        }


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d("--MSG--",position+"");
        String s = li.get(position);
        String category_object[]=s.split("%");

        category_id = Integer.parseInt(category_object[0]);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.upload_btn_refresh){
            Log.d("--MSG--","inside the refresh");
            loadCategories();

        }else if(v.getId()== R.id.up_selectimage){

            Intent i= new Intent();
            i.setType("image/*");
            i.setAction(i.ACTION_GET_CONTENT);
            startActivityForResult(i.createChooser(i,"Select a image"),SELECT_IMAGE);

        }else if(v.getId() == R.id.up_btn_upload){
            requestType = 1;
            title = tit.getText().toString();
            if(title.trim().equals("")){
                Toast.makeText(this,"Please enter image title.",Toast.LENGTH_LONG).show();
            }else{
                if(category_id == 0){
                    Toast.makeText(this,"Please select a category.",Toast.LENGTH_LONG).show();
                }else{
                    if(image == null){
                        Toast.makeText(this,"Please select an image.",Toast.LENGTH_LONG).show();
                    }else{
                        String imgDimention[] = dimention.split(" x ");

                        int width = Integer.parseInt(imgDimention[0]);
                        int height = Integer.parseInt(imgDimention[1]);

                        if(width < 1024 && height < 576){

                            Toast.makeText(this,"Image is too small",Toast.LENGTH_LONG).show();

                        }else{

                            if(!img_name.equals("")){

                                int dotposition= img_name.lastIndexOf(".");
                                String format = img_name.substring(dotposition + 1, img_name.length());

                                Log.d("--FORMAT--",format.equals("jpg")+"");

                                if(format.equals("jpg")) {

                                    FragmentManager fm = getFragmentManager();
                                    DialogFragment newFragment = new UploadDialog();
                                    newFragment.show(fm, "Upload");
                                }else{
                                    Toast.makeText(this,"Incompatible image type,Select an JPG image.",Toast.LENGTH_LONG).show();
                                }

                            }else{
                                Toast.makeText(this,"Please select an image",Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String realPath;
        if(resultCode == RESULT_OK){
            if(requestCode == SELECT_IMAGE){
                Uri selectedImageUri = data.getData();
                if(Build.VERSION.SDK_INT < 19){
                    String selectedImagePath = getPath(selectedImageUri);

                    Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);

                    realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
                    Log.d("--MSG--","path = "+realPath);

                    String path[] = realPath.split("/");
                    for (int i =0;i<path.length;i++){
                        if(i == (path.length -1)){
                            img_name = path[i];
                        }
                    }
                    setImage(bitmap);

                }else{
                    ParcelFileDescriptor parcelFileDescriptor;
                    try {
                        parcelFileDescriptor = getContentResolver().openFileDescriptor(selectedImageUri,"r");
                        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                        parcelFileDescriptor.close();

                        realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
                        Log.d("--MSG--","path = "+realPath);

                        String path[] = realPath.split("/");
                        for (int i =0;i<path.length;i++){
                            if(i == (path.length -1)){
                                img_name = path[i];
                            }
                        }

                        setImage(bitmap);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void setImage(Bitmap bitmap) {
        if(bitmap != null) {
            img_dimention.setVisibility(View.VISIBLE);
            img_size.setVisibility(View.VISIBLE);

            picture.setImageBitmap(bitmap);
            image = encodeToBase64(bitmap);
            Log.d("--MSG--","BASE64 = "+image);

            img_dimention.setText("Dimention : " + bitmap.getWidth() + " x " + bitmap.getHeight());

            dimention = bitmap.getWidth() + " x " + bitmap.getHeight();
            double bytes = bitmap.getRowBytes();
            double hight = bitmap.getHeight();
            double size = (bytes * hight) / 1024;
            size_b = size;

            img_size.setText("Size : " + size + " KB");
        }else{
            img_dimention.setVisibility(View.GONE);
            img_size.setVisibility(View.GONE);

            dimention = "";
            size_b = 0.0;

            picture.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_picture));
        }
    }

    private String getPath(Uri uri) {
        if(uri == null){
            return null;
        }
        String[] projection ={MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri,projection,null,null,null);
        if(cursor != null){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();


            //int columnIndex = cursor.getColumnIndex(projection[0]);
            //String picturePath = cursor.getString(columnIndex);
            //String picturePath = cursor.getString(column_index);
            //Log.d("--MSG--","picturePath : "+picturePath);
            return  cursor.getString(column_index);
        }

        return uri.getPath();
    }


    public static String encodeToBase64(Bitmap image){

        if(image == null){
            return null;
        }else{
            Bitmap imagex = image;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imagex.compress(Bitmap.CompressFormat.JPEG,100,baos);
            byte[] b = baos.toByteArray();

            String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
            return imageEncoded;
        }

    }


    public void addListData(){
        nv_li = new ArrayList<NameValuePair>();
        nv_li.add(new BasicNameValuePair("uid",user_id+""));
        nv_li.add(new BasicNameValuePair("title",title));
        nv_li.add(new BasicNameValuePair("category",category_id+""));
        nv_li.add(new BasicNameValuePair("dimention",dimention));
        nv_li.add(new BasicNameValuePair("imgname",img_name));
        nv_li.add(new BasicNameValuePair("img",image));
    }

    public void setProgressBarVisible(){
        upload_content.setVisibility(View.GONE);
        upload_msg_content.setVisibility(View.GONE);
        upload_progress.setVisibility(View.GONE);
        upload_load.setVisibility(View.VISIBLE);

    }

    public void  setProgressBarGone(){
        upload_load.setVisibility(View.GONE);
    }
}

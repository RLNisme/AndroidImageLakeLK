package com.im.sync;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.im.ImageLakeLK.ImageDetailActivity;
import com.im.ImageLakeLK.ImageManagementActivity;
import com.im.ImageLakeLK.ImageSearchActivity;
import com.im.ImageLakeLK.MyUploadsActivity;
import com.im.ImageLakeLK.R;
import com.im.ImageLakeLK.RatingActivity;
import com.im.ImageLakeLK.UploadManagementActivity;
import com.im.ImageLakeLK.UploadManagementSingleActivity;
import com.im.adapter.CartAdapter;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by RLN on 4/20/2015.
 */
public class BitmapAsync extends AsyncTask<String,String,Bitmap> {

    private ImageView bitmap;
    private MyUploadsActivity ma;
    private UploadManagementActivity upma;
    private UploadManagementSingleActivity umsa;
    private ImageManagementActivity ima;
    private ImageSearchActivity imsa;
    private ImageDetailActivity imda;
    private CartAdapter crta;
    private RatingActivity rta;

    public BitmapAsync(ImageView bitmap,MyUploadsActivity mua){
        this.bitmap = bitmap;
        this.ma = mua;
    }


    public BitmapAsync(ImageView bitmap,UploadManagementActivity upma){
        this.bitmap = bitmap;
        this.upma = upma;
    }

    public BitmapAsync(ImageView bitmap,UploadManagementSingleActivity umsa){
        this.bitmap = bitmap;
        this.umsa = umsa;
    }

    public BitmapAsync(ImageView bitmap,ImageManagementActivity ima){
        this.bitmap = bitmap;
        this.ima = ima;
    }

    public BitmapAsync(ImageView bitmap,ImageSearchActivity imsa){
        this.bitmap = bitmap;
        this.imsa = imsa;
    }

    public BitmapAsync(ImageView bitmap,ImageDetailActivity imda){
        this.bitmap = bitmap;
        this.imda = imda;
    }

    public BitmapAsync(ImageView bitmap,CartAdapter crta){
        this.bitmap = bitmap;
        this.crta = crta;
    }

    public BitmapAsync(ImageView bitmap,RatingActivity rta){
        this.bitmap = bitmap;
        this.rta = rta;
    }

    @Override
    protected Bitmap doInBackground(String[] params) {

        Bitmap myImag = null;
        try {
            InputStream in = new URL("http://10.0.3.2:8084/AndroidImageLK/"+params[0]).openStream();
            myImag = BitmapFactory.decodeStream(in);

        }catch (Exception e){
            Log.d("--MSG--", e.getMessage());
            e.printStackTrace();
        }

        return myImag;
    }

    @Override
    protected void onPostExecute(Bitmap o) {
        super.onPostExecute(o);
        Log.d("--MSG--",o+"");
        if(o != null) {
            bitmap.setImageBitmap(o);
        }else{
            bitmap.setImageResource(R.drawable.ic_action_picture);
            if(ma != null) {
                bitmap.setBackgroundColor(ma.getResources().getColor(R.color.md_blue_grey_500_75));
            }else if(upma != null){
                bitmap.setBackgroundColor(upma.getResources().getColor(R.color.md_blue_grey_500_75));
            }else if(umsa != null){
                bitmap.setBackgroundColor(umsa.getResources().getColor(R.color.md_blue_grey_500_75));
            }else if(ima != null){
                bitmap.setBackgroundColor(ima.getResources().getColor(R.color.md_text_white));
            }else if(imsa != null){
                bitmap.setBackgroundColor(ima.getResources().getColor(R.color.md_text_white));
            }

        }
    }
}

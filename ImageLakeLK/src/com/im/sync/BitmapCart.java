package com.im.sync;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;

import com.im.ImageLakeLK.CartActivity;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by RLN on 6/3/2015.
 */
public class BitmapCart extends AsyncTask<String,String,Bitmap> {

    private CartActivity crt;
    private String url;
    private String title;

    public BitmapCart(String url,String title,CartActivity crt){
        this.url = url;
        this.title = title;
        this.crt = crt;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap myImag = null;
        try {
            Log.d("Bitmap=",params[0]+"");
            InputStream in = new URL("http://10.0.3.2:8084/AndroidImageLK/"+params[0]).openStream();
            myImag = BitmapFactory.decodeStream(in);

        }catch (Exception e){
            Log.d("--MSG--", e.getMessage());
            e.printStackTrace();
        }

        return myImag;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        Log.d("Path",url);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(title);
        request.setDescription("File is being downloaded...");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);


        String nameOfFile = URLUtil.guessFileName(url, null, MimeTypeMap.getFileExtensionFromUrl(url));

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nameOfFile);

        DownloadManager dm = (DownloadManager) crt.getSystemService(Context.DOWNLOAD_SERVICE);
        dm.enqueue(request);

    }
}

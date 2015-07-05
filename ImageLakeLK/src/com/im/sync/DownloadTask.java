package com.im.sync;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by RLN on 6/3/2015.
 */
public class DownloadTask extends AsyncTask {
    private String url = null;
    private Activity ac;
    public DownloadTask(String url,Activity ac){
        this.url = url;
        this.ac = ac;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        try {
            URL imgUrl = new URL(url);
            HttpURLConnection con = (HttpURLConnection)imgUrl.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.connect();

            File rootDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"ImageLake");
            if(!rootDirectory.exists()){
                rootDirectory.mkdir();
            }
            String name = URLUtil.guessFileName(url,null, MimeTypeMap.getFileExtensionFromUrl(url));

            File file = new File(rootDirectory,name);
            file.createNewFile();
            InputStream is = con.getInputStream();
            OutputStream os = new FileOutputStream(file);

            byte[] buf = new byte[1024*4];
            int bytCount = 0;
            while ((bytCount =is.read(buf))>0){
                os.write(buf,0,bytCount);
            }
            os.close();

            Intent i =new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            i.setData(Uri.fromFile(file));
            ac.sendBroadcast(i);

        }catch (MalformedURLException e){


        }catch (IOException e){

        }


        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        Toast.makeText(ac,"Completed One",Toast.LENGTH_SHORT).show();
        super.onPostExecute(o);
    }
}

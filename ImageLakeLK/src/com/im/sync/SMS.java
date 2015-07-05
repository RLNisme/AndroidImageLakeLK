package com.im.sync;

import android.telephony.SmsManager;

/**
 * Created by RLN on 6/8/2015.
 */
public class SMS {

    public static synchronized boolean send_SMS(String no,String msg){

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(no,null,msg,null,null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
}

package com.im.sync;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.im.ImageLakeLK.*;
import com.im.adapter.CartAdapter;
import com.im.adapter.CategoryAdapter;
import com.im.adapter.CreditAdapter;
import com.im.adapter.CreditPackageAdapter;
import com.im.adapter.CreditSizeAdapter;
import com.im.adapter.DownloadHistoryAdapter;
import com.im.adapter.EarningManagementAdapter;
import com.im.adapter.ImageManagementAdapter;
import com.im.adapter.ImageSearchAdapter;
import com.im.adapter.ImageSettingAdapter;
import com.im.adapter.KeyWordsAdapter;
import com.im.adapter.LightBoxAdapter;
import com.im.adapter.MyNavigationAdapter;
import com.im.adapter.MyUploadsAdapter;
import com.im.adapter.PrivilegeSettingAdapter;
import com.im.adapter.PurchaseViaImagesAdapter;
import com.im.adapter.PurchaseViaPackagesAdapter;
import com.im.adapter.RatingAdapter;
import com.im.adapter.SubscriptionAdapter;
import com.im.adapter.SubscriptionPackageAdapter;
import com.im.adapter.UploadManagementAdapter;
import com.im.adapter.UserManagementAdapter;
import com.im.adapter.UserSettingAdapter;
import com.im.dialogbox.EarningsettleDialog;
import com.im.fragments.ImageSettingFragment;
import com.im.fragments.PackageSettingFragment;
import com.im.fragments.PrivilageSettingFragment;
import com.im.fragments.UserSettingFragment;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RLN on 4/3/2015.
 */
public class SyncThread extends AsyncTask {

    private int DELAY = 50;
    private static final int GET = 0;
    private static final int POST= 1;
    private int loaded_value = 0;


    private MainActivity ma;
    private SignupActivity supa;
    private SigninActivity sina;
    private AccountActivity aa;
    private MyNavigationAdapter mna;
    private LightBoxActivity lba;
    private LightBoxAdapter lbad;
    private DownloadHistoryAdapter dha;
    private EditActivity ea;
    private ChangePasswordActivity chp;
    private ChangePhoneNoActivity chna;
    private BecomeaContributor bc;
    private ProfitActivity pa;
    private MyUploadsAdapter mupa;
    private MyUploadsActivity mua;
    private UploadActivity ua;
    private EarningManagementAdapter ema;
    private EarningsettleDialog esd;
    private UserManagementAdapter umaa;
    private UserManagementActivity uma;
    private PurchaseViaPackagesAdapter pvpaa;
    private PurchaseViaImagesAdapter pviaa;
    private PurchaseViaImagesActivity pvia;
    private UploadManagementAdapter upmaa;
    private KeyWordsAdapter kwa;
    private UploadManagementSingleActivity upsa;
    private ImageManagementActivity ima;
    private ImageManagementAdapter imaa;
    private UserSettingFragment usf;
    private UserSettingAdapter ustaa;
    private ImageSettingFragment isf;
    private ImageSettingAdapter isa;
    private CategoryAdapter ca;
    private PrivilageSettingFragment psf;
    private PrivilegeSettingAdapter psa;
    private PackageSettingFragment pksf;
    private CreditSizeActivity csa;
    private CreditSizeAdapter csaa;
    private CreditPackageActivity cpa;
    private CreditPackageAdapter cpaa;
    private SubscriptionPackageActivity spa;
    private SubscriptionPackageAdapter spaa;
    private ImageSearchActivity imsa;
    private ImageSearchAdapter imsaa;
    private ImageDetailActivity imda;
    private CartAdapter crta;
    private CartActivity crt;
    private PackagesActivity pga;
    private SubscriptionActivity sa;
    private SubscriptionAdapter saa;
    private CreditAdapter caa;
    private PurchaseActivity pac;
    private RatingAdapter rtaa;
    //private String imgURL;
    public String[] message;

    public SyncThread(SignupActivity supa){// this is for SIGN UP
        this.supa = supa;
    }

    public SyncThread(SigninActivity sina){
        this.sina = sina;
    }

    public SyncThread(AccountActivity aa){
        this.aa = aa;
    }

    public SyncThread(MyNavigationAdapter mna){
        this.mna = mna;
    }

    public SyncThread(MainActivity ma){
        this.ma = ma;
    }

    public SyncThread(LightBoxActivity lba){
        this.lba = lba;
    }

    public SyncThread(LightBoxAdapter lbad){
        this.lbad = lbad;
    }

    public SyncThread(DownloadHistoryAdapter dha){
        this.dha = dha;
    }

    public SyncThread(EditActivity ea){
        this.ea = ea;
    }

    public SyncThread(ChangePasswordActivity chp){
        this.chp = chp;
    }

    public SyncThread(ChangePhoneNoActivity chna){

            this.chna = chna;
    }

    public SyncThread(BecomeaContributor bc){
        this.bc = bc;
    }

    public SyncThread(ProfitActivity pa){
        this.pa = pa;
    }

    public SyncThread(MyUploadsAdapter mupa){
        this.mupa = mupa;
    }

    public SyncThread(MyUploadsActivity mua){
        this.mua = mua;
    }

    public SyncThread(UploadActivity ua){
        this.ua = ua;
    }

    public SyncThread(EarningManagementAdapter ema){
        this.ema = ema;
    }

    public SyncThread(EarningsettleDialog esd){
        this.esd = esd;
    }

    public SyncThread(UserManagementAdapter umaa){
        this.umaa = umaa;
    }

    public SyncThread(UserManagementActivity uma){
        this.uma = uma;
    }

    public SyncThread(PurchaseViaPackagesAdapter pvpaa){
        this.pvpaa = pvpaa;
    }

    public SyncThread(PurchaseViaImagesAdapter pviaa){
        this.pviaa = pviaa;
    }

    public SyncThread(PurchaseViaImagesActivity pvia){
        this.pvia = pvia;
    }

    public SyncThread(UploadManagementAdapter upmaa){
        this.upmaa = upmaa;
    }

    public SyncThread(KeyWordsAdapter kwa){
        this.kwa = kwa;
    }

    public SyncThread(UploadManagementSingleActivity upsa){
        this.upsa = upsa;
    }

    public SyncThread(ImageManagementActivity ima){
        this.ima = ima;
    }

    public SyncThread(ImageManagementAdapter imaa){
        this.imaa = imaa;
    }

    public SyncThread(UserSettingFragment usf){
        this.usf = usf;
    }

    public SyncThread(UserSettingAdapter ustaa){
        this.ustaa = ustaa;
    }

    public SyncThread(ImageSettingFragment isf){
        this.isf = isf;
    }

    public SyncThread(ImageSettingAdapter isa){
        this.isa = isa;
    }

    public SyncThread(CategoryAdapter ca){
        this.ca = ca;
    }

    public SyncThread(PrivilageSettingFragment psf){
        this.psf = psf;
    }

    public SyncThread(PrivilegeSettingAdapter psa){
        this.psa = psa;
    }

    public SyncThread(PackageSettingFragment pksf){
        this.pksf = pksf;
    }

    public SyncThread(CreditSizeActivity csa){
        this.csa = csa;
    }

    public SyncThread(CreditSizeAdapter csaa){
        this.csaa = csaa;
    }

    public SyncThread(CreditPackageActivity cpa){
        this.cpa = cpa;
    }

    public SyncThread(CreditPackageAdapter cpaa){
        this.cpaa = cpaa;
    }

    public SyncThread(SubscriptionPackageActivity spa){
        this.spa = spa;
    }

    public SyncThread(SubscriptionPackageAdapter spaa){
        this.spaa = spaa;
    }

    public SyncThread(ImageSearchActivity imsa){
        this.imsa = imsa;
    }

    public SyncThread(ImageSearchAdapter imsaa){
        this.imsaa = imsaa;
    }

    public SyncThread(ImageDetailActivity imda){
        this.imda = imda;
    }

    public SyncThread(CartAdapter crta){
        this.crta = crta;
    }

    public SyncThread(CartActivity crt){
        this.crt = crt;
    }

    public SyncThread(PackagesActivity pga){
        this.pga = pga;
    }

    public SyncThread(SubscriptionActivity sa){
        this.sa = sa;
    }

    public SyncThread(SubscriptionAdapter saa){
        this.saa = saa;
    }

    public SyncThread(CreditAdapter caa){
        this.caa = caa;
    }

    public SyncThread(PurchaseActivity pac){
        this.pac = pac;
    }

    public SyncThread(RatingAdapter rtaa){
        this.rtaa = rtaa;
    }

    String host ="10.0.3.2:8084";
    @Override
    protected Object doInBackground(Object[] params) {
        String result = "";
        if(supa != null) {
            if (supa.requestType == POST) {
                String url = "http://"+host+"/AndroidImageLK/Servlet_signup";
                result = postRequest(url, supa.getData(supa.DATABASE_TABLE));
            }
        }else if(sina != null) {
            if (sina.requestType == POST) {
                String url = "http://"+host+"/AndroidImageLK/Servlet_signin";
                Log.d("---------------","Request from signin");
                result = postRequest(url, sina.getData(sina.DATABASE_TABLE));
            }
        }else if(aa != null) {

                String url = "http://"+host+"/AndroidImageLK/Servlet_Account?uid="+aa.user_id;
                result = getRequest(url);

        }else if(mna != null){

            if(mna.requestType == GET){
                String url = "http://"+host+"/AndroidImageLK/Servlet_privilages?user_type="+mna.user_type;
                result = getRequest(url);
            }
        }else if(ma != null){
            if(ma.requestType == GET){
                String url = "http://"+host+"/AndroidImageLK/index.jsp";
                result = getRequest(url);

            }
        }else if(lbad != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_LightBox?type="+lbad.type+"&user_id="+lbad.user_id;
            result = getRequest(url);
        }else if(dha != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_downloadhistory?type="+dha.type+"&user_id="+dha.user_id;
            result = getRequest(url);
        }else if(ea != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_editprofile";
            result = postRequest(url,ea.li);
        }else if(chp != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_ChangePassword";
            result = postRequest(url,chp.li);
        }else if(chna != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_ChangePhoneno";
            result = postRequest(url,chna.li);
        }else if(bc != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_BeContributor";
            result = postRequest(url,bc.li);
        }else if(pa != null){
            if(pa.requestType == GET){
                String url = "http://"+host+"/AndroidImageLK/Servlet_EarningsValues?uid="+pa.user_id;
                result = getRequest(url);
            }else{
                String url = "http://"+host+"/AndroidImageLK/Servlet_Earnings";
                result = postRequest(url,pa.li);
            }
        }else if(mupa != null){
            if(mupa.request == GET){
                String url = "http://"+host+"/AndroidImageLK/Servlet_MyImages?uid="+mupa.user_id;
                result = getRequest(url);
            }else{

            }
        }else if(mua !=null){

                String url = "http://"+host+"/AndroidImageLK/Servlet_UpdateMyImages";
                result = postRequest(url,mua.li);

        }else if(ua != null){
            if(ua.requestType == GET){
                String url = "http://"+host+"/AndroidImageLK/Servlet_categories";
                result = getRequest(url);
            }else if(ua.requestType == POST){

                String url = "http://"+host+"/AndroidImageLK/Servlet_Uploads";
                result = postRequest(url,ua.nv_li);
            }
        }else if(ema != null){
            if(ema.requestType == GET){
                if(ema.type.equals("new")) {
                    String url = "http://"+host+"/AndroidImageLK/Servlet_AdminEarnings?id=" + ema.user_id + "&type=" + ema.type;
                    result = getRequest(url);
                }else if(ema.type.equals("sort")){
                    String url = "http://"+host+"/AndroidImageLK/Servlet_AdminEarnings?id=" + ema.user_id + "&type=" + ema.type
                            +"&uid="+ema.uid+"&date="+ema.date+"&conr="+ema.cr+"&newr="+ema.nr;
                    result = getRequest(url);
                }
            }
        }else if(esd != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_AdminEarnings?idd=" + esd.idd + "&type=" + esd.type
                    +"&uid="+esd.ruid+"&id="+esd.user_id+"&amount="+esd.amount+"&state="+esd.state;
            result = getRequest(url);
        }else if(umaa != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_UserManagement?type="+umaa.type+"&uid="+umaa.uid+"&date="+umaa.date+"&buy="+umaa.cus+"&sell="+umaa.con+"&id="+umaa.user_id;
            result = getRequest(url);
        }else if(uma != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_UserManagement?type="+uma.type+"&id="+uma.user_id+"&uid="+uma.uuid+"&state="+uma.state;
            result = getRequest(url);
        }else if(pvpaa != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_purchaseVisePackages?type="+pvpaa.type+"&date="+pvpaa.date_pur+"&date2="+pvpaa.date_exp+"&cat="+pvpaa.package_id+"&buy="+pvpaa.uid;
            result = getRequest(url);
        }else if(pviaa != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_purchaseViseImages?type="+pviaa.type+"&imid="+pviaa.uid+"&date="+pviaa.date_pur+"&date2="+pviaa.date_exp+"&cat="+pviaa.cat_id+"&buy="+pviaa.buy_id+"&sell="+pviaa.sel_id;
            result = getRequest(url);
        }else if(pvia != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_purchaseViseImages?type="+pvia.type;
            result = getRequest(url);
        }else if(upmaa != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_UploadManagement?type="+upmaa.type;
            result = getRequest(url);
        }else if(kwa != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_UploadManagement?type="+kwa.type+"&imgid="+kwa.imgid;
            result = getRequest(url);
        }else if(upsa != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_UploadManagement?type="+upsa.type+"&imgid="+upsa.img_id+"&key="+upsa.keyword+"&userid="+upsa.user_id+"&state="+upsa.state;
            result = getRequest(url);
        }else if(imaa != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_ImageManagement?type="+imaa.type+"&date="+imaa.date_pur+"&date2="+imaa.date_exp+"&usnm="+imaa.sel_id+"&key="+imaa.key+"&cat="+imaa.cat_id;
            result = getRequest(url);
        }else if(ima != null){
            if(ima.type.equals("all_sort")) {
                String url = "http://"+host+"/AndroidImageLK/Servlet_purchaseViseImages?type="+ima.type;
                result = getRequest(url);
            }
        }else if(ustaa != null){
            if(ustaa.type.equals("all")) {
                String url = "http://"+host+"/AndroidImageLK/Servlet_UserSetting?type="+ustaa.type;
                result = getRequest(url);
            }
        }else if(usf != null){

                String url = "http://"+host+"/AndroidImageLK/Servlet_UserSetting?type="+usf.type+"&id="+usf.uid+"&admintype="+usf.newType+"&state="+usf.new_state;
                result = getRequest(url);

        }else if(isf != null){

            String url = "http://"+host+"/AndroidImageLK/Servlet_ImageSetting?type="+isf.type+"&sliceType="+isf.slice_type+"&sliceId="+isf.slice_id+"&creditId="+isf.cred_id+"&category="+isf.category;
            result = getRequest(url);

        }else if(isa != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_ImageSetting?type="+isa.type;
            result = getRequest(url);
        }else if(ca != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_ImageSetting?type="+ca.type;
            result = getRequest(url);
        }else if(psf != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_PrivilegeSetting?type="+psf.type+"&ustype="+psf.new_user_type+"&userType="+psf.user_type_id+"&infid="+psf.privi_id+"&intfid="+psf.interface_id;
            result = getRequest(url);
        }else if(psa != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_PrivilegeSetting?type="+psa.type;
            result = getRequest(url);
        }else if(pksf != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_PackageSetting?type="+pksf.type+"&freeday="+pksf.daily_days+"&freecnt="+pksf.daily_downloads+"&freeunday="+pksf.unlimit_days+"&freeuncnt="+pksf.unlimit_downloads+"&percent="+pksf.adsplit_percents+"&earn="+pksf.adsplit_mins+"&dwnloads="+pksf.dwn_count;
            result = getRequest(url);
        }else if(csa != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_PackageSetting?type="+csa.type+"&addcredits="+csa.credit+"&addsize="+csa.size+"&addwidth="+csa.width+"&addheight="+csa.height
                    +"&upid="+csa.upid+"&upcredit="+csa.upcredit+"&upstate="+csa.upstate+"&upwidth="+csa.upwidth+"&upheight="+csa.upheight+"&upsize="+csa.upsize;
            result = getRequest(url);
        }else if(csaa != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_PackageSetting?type="+csaa.type;
            result = getRequest(url);
        }else if(cpa != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_PackageSetting?type="+cpa.type+"&addcount="+cpa.credit+"&adddico="+cpa.disco+"&addunitprice="+cpa.unit+"&addduration="+cpa.month
                    +"&upid="+cpa.upid+"&upcount="+cpa.upcredit+"&upstate="+cpa.upstate+"&updisco="+cpa.updisco+"&upduration="+cpa.upmonth+"&upunitprice="+cpa.upunit;
            result = getRequest(url);
        }else if(cpaa != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_PackageSetting?type="+cpaa.type;
            result = getRequest(url);
        }else if(spa != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_PackageSetting?type="+spa.type+"&addcount="+spa.count+"&adddico="+spa.disco+"&addunitprice="+spa.unit+"&addduration="+spa.month+"&addpacktype="+spa.typ+
                    "&upid="+spa.upid+"&upcount="+spa.upcount+"&upstate="+spa.upstate+"&updico="+spa.updisco+"&upduration="+spa.upmonth+"&upunitprice="+spa.upunit;
            result = getRequest(url);
        }else if(spaa != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_PackageSetting?type="+spaa.type;
            result = getRequest(url);
        }else if(imsa != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_Components";
            result = getRequest(url);
        }else if(imsaa != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_Search?type="+imsaa.type+"&page="+imsaa.currentPage+"&cat="+imsaa.catid
                    +"&key="+imsaa.key+"&from="+imsaa.to+"&to="+imsaa.from+"&sell="+imsaa.sid+"&size="+imsaa.sizid;
            result = getRequest(url);
        }else if(imda != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_ImageDetails?type="+imda.type+"&imgid="+imda.imgid+"&uid="+imda.uid+"&sid="+imda.ssid;
            result = getRequest(url);
        }else if(crt != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_Download?type="+crt.type+"&uid="+crt.user_id+"&chiID="+crt.cart_item+"&crec="+crt.crd_count;
            result = getRequest(url);
        }else if(crta != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_Download?type="+crta.type+"&uid="+crta.uid;
            result = getRequest(url);
        }else if(pga != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_Packages?type="+pga.type+"&uid="+pga.user_id;
            result = getRequest(url);
        }else if(saa != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_Packages?type="+saa.type;
            result = getRequest(url);
        }else if(caa != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_Packages?type="+caa.type;
            result = getRequest(url);
        }else if(pac != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_Purch?fn="+pac.pfn+"&ln="+pac.pln+"&type="+pac.type+"&card="+pac.pccn+"&cv="+pac.pcvv+"&em="+pac.pmon+"&ey="+pac.pyer+"&ec="+pac.pcon+"&totalDays="+pac.ptd+"&pkId="+pac.pck_id+"&usid="+pac.uid+"&dwnCount="+pac.pdwn+"&total="+pac.ptot;
            result = getRequest(url);
        }else if(rtaa != null){
            String url = "http://"+host+"/AndroidImageLK/Servlet_Rating?chid="+rtaa.chid+"&x="+rtaa.x;
            result = getRequest(url);
        }


        Log.d("--MSG--", "Inside the doInBackground");
        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if(ma != null){
            ma.loading.setVisibility(ProgressBar.VISIBLE);

        }else if(lbad != null){
            lbad.setProgressBarVisible();
        }else if(dha != null){
            dha.setProgressBarVisible();
        }else if(mupa != null){
            mupa.setProgressBarVisible();
        }else if(ua != null){
            ua.setProgressBarVisible();
            for (int i = 1;i<26;i++){
                publishProgress(i);
                sleep();
            }
        }else if(ema != null){
            ema.setProgressBarVisible();
        }else if(umaa != null){
            umaa.setProgressBarVisible();
        }else if(pvpaa != null){
            pvpaa.setProgressBarVisible();
        }else if(pviaa != null){
            pviaa.setProgressBarVisible();
        }else if(upmaa != null){
            upmaa.setProgressBarVisible();
        }else if(imaa != null){
            imaa.setProgressBarVisible();
        }else if(ustaa != null){
           ustaa.setProgressBarVisible();
        }else if(csaa != null){
            csaa.setProgressBarVisible();
        }else if(cpaa != null){
            cpaa.setProgressBarVisible();
        }else if(spaa != null){
            spaa.setProgressBarVisible();
        }else if(imsaa != null){
            imsaa.setProgressBarVisible();
        }else if(crta != null){
            crta.setProgressBarVisible();
        }else if(saa != null){
            saa.setProgressBarVisible();
        }else if(caa != null){
            caa.setProgressBarVisible();
        }

        Log.d("--MSG--", "inside the preexecute");
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        Log.d("--MSG--", "inside the postExecute" + o);
        if(supa != null){
            supa.serverResultAsString(o+"");
        }
        else if(sina != null){
            if(message == null){
                sina.responseString(o + "");
            }else if(message[0].equals("json")){
                sina.responseJson(o+"");
            }else{
                sina.responseString(o+"");
            }
        }
        else if(mna !=null){
            if(message == null){
               Log.d("--MSG--","Error in message");
            }else if(message[0].equals("json")){
                mna.responseJson(o+"");//
            }else{
                Log.d("--MSG--", "Error in message");
            }
        }
        else if(ma != null){
            ma.responseString(o+"");
        }
        else if(lbad != null){
            lbad.setProgressBarGone();
            if(message == null){
                lbad.stringErrorMsg(o+"");
            }
            else if(message[0].equals("json")) {
                lbad.JSONResponse(o + "");
            }else if(message[0].equals("msg")){
                lbad.stringResponse(o+"");
            }
        }else if(dha !=null){
            dha.setProgressBarGone();
            if(message == null){
                dha.stringErrorMsg(o+"");
            } else if(message[0].equals("json")) {
                dha.JSONResponse(o + "");
            }else if(message[0].equals("msg")){
                dha.stringResponse(o+"");
            }
        }else if(ea != null){
            if(message == null){
                ea.stringResponse(o+"");
            }else if(message[0].equals("msg")){
                ea.stringResponse(o+"");
            }
        }else if(chp != null){
            if(message == null){
                chp.stringResponse(o+"");
            }else if(message[0].equals("msg")){
                chp.stringResponse(o+"");
            }
        }else if(bc != null){
            if(message == null) {
                bc.stringErrorMsg(o+"");
            }else if(message[0].equals("msg")){
                bc.stringResponse(o+"");
            }
        }else if(pa != null){
            if(message == null){
                pa.stringErrorMsg(o + "");
            } else if(message[0].equals("json")) {
                pa.JSONResponse(o + "");
            }else if(message[0].equals("msg")){
                pa.stringResponse(o + "");
            }
        }else if(mupa != null){
            mupa.setProgressBarGone();
            if(message == null){
                mupa.stringErrorMsg(o + "");
            } else if(message[0].equals("json")) {
                mupa.JSONResponse(o + "");
            }else if(message[0].equals("msg")){
                mupa.stringResponse(o + "");
            }
        }else if(mua != null){
            if(message == null){
                mua.ErrorMsg(o + "");
            } else if(message[0].equals("msg")){
                mua.Response(o + "");
            }
        }else if(ua != null){
                if(ua.requestType == GET) {
                    ua.upload_progress.setVisibility(View.GONE);
                    if (message == null) {
                        ua.stringErrorMsg(o + "");
                    } else if (message[0].equals("json")) {
                        ua.JSONResponse(o + "");
                    } else if (message[0].equals("msg")) {
                        ua.stringResponse(o + "");
                    }
                }else{
                    for (int i = 72;i<101;i+=5){
                        publishProgress(i);
                        if(i == 100){
                            ua.setProgressBarGone();
                        }
                        sleep();
                    }


                    if (message == null) {
                        ua.stringErrorMsg(o + "");
                    } else if (message[0].equals("json")) {
                        ua.JSONResponse(o + "");
                    } else if (message[0].equals("msg")) {
                        ua.stringResponse(o + "");
                    }
                }
        }else if(ema != null){
            ema.setProgressBarGone();
            if (message == null) {
                ema.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                ema.JSONResponse(o + "");
            } else if (message[0].equals("msg")) {
                ema.stringResponse(o + "");
            }
        }else if(esd != null){
            if (message == null) {
                esd.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                esd.JSONResponse(o + "");
            } else if (message[0].equals("msg")) {
                esd.stringResponse(o + "");
            }
        }else if(umaa != null){
            umaa.setProgressBarGone();
            if (message == null) {
                umaa.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                umaa.JSONResponse(o + "");
            } else if (message[0].equals("msg")) {
                umaa.stringResponse(o + "");
            }
        }else if(uma != null){

            if (message == null) {
                uma.stringErrorMsg(o + "");
            }  else if (message[0].equals("msg")) {
                uma.stringResponse(o + "");
            }
        }else if(pvpaa != null){
            pvpaa.setProgressBarGone();
            if (message == null) {
                pvpaa.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                pvpaa.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                pvpaa.stringResponse(o + "");
            }
        }else if(pviaa != null){
            pviaa.setProgressBarGone();
            if (message == null) {
                pviaa.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                pviaa.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                pviaa.stringResponse(o + "");
            }
        }else if(pvia != null){

            if (message == null) {
                pvia.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                pvia.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                pvia.stringResponse(o + "");
            }
        }else if(upmaa != null){
            upmaa.setProgressBarGone();
            if (message == null) {
                upmaa.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                upmaa.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                upmaa.stringResponse(o + "");
            }
        }else if(kwa != null){

            if (message == null) {
                kwa.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                kwa.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                kwa.stringResponse(o + "");
            }
        }else if(upsa != null){
            upsa.up_load_content.setVisibility(View.GONE);
            if (message == null) {
                upsa.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                upsa.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                upsa.stringResponse(o + "");
            }
        }else if(imaa != null){
            imaa.setProgressBarGone();
            if (message == null) {
                imaa.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                imaa.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                imaa.stringResponse(o + "");
            }
        }else if(ima != null){
           // ima.setProgressBarGone();
            if (message == null) {
                ima.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                ima.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                ima.stringResponse(o + "");
            }
        }else if(ustaa != null){
             ustaa.setProgressBarGone();
            if (message == null) {
                ustaa.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                ustaa.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                ustaa.stringResponse(o + "");
            }
        }else if(usf != null){

            if (message == null) {
                usf.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                usf.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                usf.stringResponse(o + "");
            }
        }else if(isf != null){

            if (message == null) {
                isf.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                isf.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                isf.stringResponse(o + "");
            }
        }else if(isa != null){

            if (message == null) {
                isa.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                isa.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                isa.stringResponse(o + "");
            }
        }else if(ca != null){

            if (message == null) {
                ca.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                ca.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                ca.stringResponse(o + "");
            }
        }else if(psf != null){

            if (message == null) {
                psf.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                psf.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                psf.stringResponse(o + "");
            }
        }else if(psa != null){

            if (message == null) {
                psa.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                psa.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                psa.stringResponse(o + "");
            }
        }else if(pksf != null){

            if (message == null) {
                pksf.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                pksf.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                pksf.stringResponse(o + "");
            }
        }else if(csa != null){

            if (message == null) {
                csa.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                csa.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                csa.stringResponse(o + "");
            }
        }else if(csaa != null){
            csaa.setProgressBarGone();
            if (message == null) {
                csaa.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                csaa.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                csaa.stringResponse(o + "");
            }
        }else if(cpa != null){

            if (message == null) {
                cpa.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                cpa.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                cpa.stringResponse(o + "");
            }
        }else if(cpaa != null){
            cpaa.setProgressBarGone();
            if (message == null) {
                cpaa.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                cpaa.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                cpaa.stringResponse(o + "");
            }
        }else if(spa != null){

            if (message == null) {
                spa.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                spa.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                spa.stringResponse(o + "");
            }
        }else if(spaa != null){
           spaa.setProgressBarGone();
            if (message == null) {
                spaa.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                spaa.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                spaa.stringResponse(o + "");
            }
        }else if(imsaa != null){
            imsaa.setProgressBarGone();
            if (message == null) {
                imsaa.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                imsaa.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                imsaa.stringResponse(o + "");
            }
        }else if(imsa != null){

            if (message == null) {
                imsa.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                imsa.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                imsa.stringResponse(o + "");
            }
        }else if(imda != null){

            if (message == null) {
                imda.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                imda.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                imda.stringResponse(o + "");
            }
        }else if(crta != null){
            crta.setProgressBarGone();
            if (message == null) {
                crta.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                crta.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                crta.stringResponse(o + "");
            }
        }else if(crt != null){

            if (message == null) {
                crt.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                crt.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                crt.stringResponse(o + "");
            }
        }else if(aa != null){

            if (message == null) {
                aa.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                aa.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                aa.stringResponse(o + "");
            }
        }else if(pga != null){

            if (message == null) {
                pga.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                pga.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                pga.stringResponse(o + "");
            }
        }else if(saa != null){
            saa.setProgressBarGone();
            if (message == null) {
                saa.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                saa.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                saa.stringResponse(o + "");
            }
        }else if(caa != null){
            caa.setProgressBarGone();
            if (message == null) {
                caa.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                caa.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                caa.stringResponse(o + "");
            }
        }else if(pac != null){

            if (message == null) {
                pac.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                pac.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                pac.stringResponse(o + "");
            }
        }else if(chna != null){

            if (message == null) {
                chna.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                chna.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                chna.stringResponse(o + "");
            }
        }else if(rtaa != null){
            if (message == null) {
                rtaa.stringErrorMsg(o + "");
            } else if (message[0].equals("json")) {
                rtaa.JSONResponse(o + "");
            }  else if (message[0].equals("msg")) {
                rtaa.stringResponse(o + "");
            }
        }
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
        if(ma != null) {
            ma.loading.setProgress(Integer.parseInt(values[0] + ""));
        }else if(ua != null){
            ua.pl.setProgress(Integer.parseInt(values[0]+""));
        }
    }

    public String getRequest(String url){

        String s = "";
        int status_id;
        System.out.print(url);
        try {
            HttpClient hc = new DefaultHttpClient();
            HttpGet getRequest = new HttpGet(url);
            HttpResponse response = hc.execute(getRequest);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
            status_id = response.getStatusLine().getStatusCode();
            Log.d("Status",status_id+"");
            if(status_id == 200) {
                String thisLine = "";
                while ((thisLine = reader.readLine()) != null) {
                    Log.d("Response",thisLine);
                    if(ma != null) {
                        for (int i = 0;i<21;i++){
                            sleep();
                            publishProgress(i * 5);

                        }
                        s = "OK";
                    }else{
                        message = thisLine.split("=");
                        s = message[1];
                        Log.d("--MSG--",s);
                    }
                }
                Log.d("--MSG--",s);
                if(ma == null) {
                    if (message[0].equals("msg")) {


                    } else if (message[0].equals("json")) {

                    }
                }

            }else{
                s ="Internal server error,Please try again later.";
            }



        }catch (Exception e) {
            e.printStackTrace();
            Log.d("--Error--","Exception");
            s ="Internal error,Please try again later.";
        }
        return s;
    }


    public String postRequest(String url,List<NameValuePair> nameValuePairsList){
        String s = "";
        int status_id;
        Log.d("--MSG--","Post Request");
        try {
            HttpClient hc = new DefaultHttpClient();
            Log.d("--MSG--","HttpClient");
            HttpPost postRequest = new HttpPost(url);
            Log.d("--MSG--","HttpPost request");
            postRequest.setEntity(new UrlEncodedFormEntity(nameValuePairsList));
            Log.d("--MSG--", "Url encodingFormEntity");
            HttpResponse response = hc.execute(postRequest);
            Log.d("--MSG--","Httpresponse");
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
            Log.d("--MSG--","Buffer reader");
            status_id = response.getStatusLine().getStatusCode();
            Log.d("--MSG--",status_id+"");
            if(status_id == 200) {
                String thisLine;

                while ((thisLine = reader.readLine()) != null) {
                    message = thisLine.split("=");

                    s = message[1];

                }

                if(message[0].equals("msg")) {




                }
                else if(message[0].equals("json")){

                }
            }else{
                s = "Network Error.";
            }


        }catch (Exception e){
            e.printStackTrace();
            Log.d("--Error--", "Exception");
            s = "Internal error,please try again later.";
        }

        if(ua != null){
            for (int i = 26;i<72;i++){
                publishProgress(i);
                sleep();
            }
        }

        Log.d("--MSG--",s);
        return s;
    }

    private void sleep(){
        try {
            Thread.sleep(DELAY);
        }catch (InterruptedException e){
            e.printStackTrace();
            Log.d("--Error--","Error");
        }
    }



    public String uploadImageToServer(){
        return  null;
    }


}

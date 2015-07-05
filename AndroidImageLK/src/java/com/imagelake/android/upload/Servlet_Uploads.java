/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagelake.android.upload;

import com.imagelake.control.AdminHasNotificationDAOImp;
import com.imagelake.control.AdminNotificationDAOImp;
import com.imagelake.control.UserDAOImp;
import com.imagelake.model.AdminNotification;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 *
 * @author RLN
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB 
        maxFileSize = 1024 * 1024 * 50, // 50 MB
        maxRequestSize = 1024 * 1024 * 100)
public class Servlet_Uploads extends HttpServlet {

    private static final String UPLOAD_DIR = "uploads";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        PrintWriter out = response.getWriter();

        Date d = new Date();
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
        String date = sm.format(d);

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        UserDAOImp udi=new UserDAOImp();
        
        String uid, title, category,imgname,dimention,image;
        
        uid = request.getParameter("uid");
        title = request.getParameter("title");
        imgname=request.getParameter("imgname");
        category = request.getParameter("category");
        dimention = request.getParameter("dimention");
        image = request.getParameter("img");
        
        long size = 0;
        InputStream inputStream = null;

        if (uid.trim().equals("") || title.trim().equals("") || category.trim().equals("")) {

            out.write("msg=Please check details you have entered.");

        } else {

            System.out.println("img="+image);
            System.out.println("uid ="+uid);
            System.out.println("title ="+title);
            System.out.println("imgname ="+imgname);
            System.out.println("category ="+category);
            System.out.println("dimention ="+dimention);
            
            try {
                byte[] btDataFile = new sun.misc.BASE64Decoder().decodeBuffer(image);
                
                
                String fileName = null;
                try {
                    
                    // gets absolute path of the web application
                                    String applicationPath = request.getServletContext().getRealPath("");
                                    // constructs path of the directory to save uploaded file
                                    String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

                                    // creates the save directory if it does not exists
                                    File fileSaveDir = new File(uploadFilePath);
                                    if (!fileSaveDir.exists()) {
                                        fileSaveDir.mkdirs();
                                    }
                                    System.out.println("Upload File Directory="+fileSaveDir.getAbsolutePath());
                                    System.out.println("Upload File Directory2="+fileSaveDir.getAbsolutePath()+"\\"+imgname);

                                    //Get all the parts from request and write it to the file on server
                                    File of = new File(fileSaveDir.getAbsolutePath()+"\\"+imgname);
                                    FileOutputStream osf = new FileOutputStream(of);
                                    osf.write(btDataFile);
                                    osf.flush();
                                    
                                    
                                    
                                        
                                    boolean sliceImage=new CreateImages().sliceImages("","","","","","","","","","",imgname,dimention,title,category,uid,date);
                                        out.write("msg=Successful...");
                                    if(sliceImage){
                                    }else{
                                        out.write("msg=Unable to complete the action,Please try again later.");
                                    }
                    
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            }catch(Exception e){
                e.printStackTrace();
            }
            
        }

    }
    
    
     private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        System.out.println("content-disposition header= "+contentDisp);
        System.out.println(contentDisp);
        String[] tokens = contentDisp.split(";");
        
        for (String token : tokens) {
            
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length()-1);
            }
        }
        
        return "";
    }
    
    
}

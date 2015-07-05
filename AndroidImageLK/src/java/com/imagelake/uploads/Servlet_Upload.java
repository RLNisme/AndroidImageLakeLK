/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.uploads;

import com.imagelake.control.AdminHasNotificationDAOImp;
import com.imagelake.control.AdminNotificationDAOImp;
import com.imagelake.control.ImagesDAOImp;
import com.imagelake.control.UserDAOImp;
import com.imagelake.model.AdminNotification;
import com.imagelake.model.Images;
import com.imagelake.model.ImagesSub;
import com.imagelake.model.User;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Lakmal
 */

@MultipartConfig(fileSizeThreshold=1024*1024*10,    // 10 MB 
                 maxFileSize=1024*1024*50,          // 50 MB
                 maxRequestSize=1024*1024*100) 
public class Servlet_Upload extends HttpServlet {
    
    private static final String UPLOAD_DIR = "uploads";

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        Date d=new Date();
        SimpleDateFormat sm=new SimpleDateFormat("yyyy-MM-dd");
        String date=sm.format(d);
        
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        UserDAOImp udi=new UserDAOImp();
        // gets values of text fields
      PrintWriter out=response.getWriter();
        
        String cat,imgtit,imgname,dominate,price,dimention,col1,col2,col3,col4,col5,col6,col7,col8,col9,size_string,uid;
        System.out.println("server name===="+request.getServerName());
        
        uid=request.getParameter("uid");
        imgname=request.getParameter("imgname");
        imgtit=request.getParameter("tit");
        cat=request.getParameter("cat");      
        dimention=request.getParameter("dimention");
        dominate=request.getParameter("dominate");
        col1=request.getParameter("col-1");
        col2=request.getParameter("col-2");
        col3=request.getParameter("col-3");
        col4=request.getParameter("col-4");
        col5=request.getParameter("col-5");
        col6=request.getParameter("col-6");
        col7=request.getParameter("col-7");
        col8=request.getParameter("col-8");
        col9=request.getParameter("col-9");
        size_string=request.getParameter("size");
        long size = 0;
        
        System.out.println(cat+" "+imgname+" "+col1+" "+col2+" "+col3+" "+col4+" "+col5+" "+col6+" "+col7+" "+col8+" "+col9+" //"+dominate+" "+size_string);
        System.out.println(request.getParameter("tit")+"bbbbb"+request.getParameter("cat"));
        InputStream inputStream = null; // input stream of the upload file
        
        System.out.println("woooooooooo"+imgtit.trim()+"hooooooooo");
        
            if(imgtit.equals("") || imgtit.equals(null) || cat.equals("") || cat.equals(null)||cat.equals("0") ||dimention.equals("")|| dimention.equals(null) && dominate.equals("")|| dominate.equals(null) && 
                 col1.equals("")|| col1.equals(null)&& col2.equals("")||col2.equals(null) && col3.equals("")||col3.equals(null) && col4.equals("")||col4.equals(null) && col5.equals("")&&
                col5.equals(null) && col6.equals("")||col6.equals(null) && col7.equals("")|| col7.equals(null) && col8.equals("")|| col8.equals(null)&&
                col9.equals("")|| col9.equals(null)){
                            System.out.println("error");
                            
                            out.write("error");
                            }else{
                
                
                
                         // obtains the upload file part in this multipart request 
                        try {
                            Part filePart = request.getPart("photo");


                        if (filePart != null) {
                            // prints out some information for debugging
                            System.out.println("NAME:"+filePart.getName());
                            System.out.println(filePart.getSize());
                            size=filePart.getSize();
                            System.out.println(filePart.getContentType());

                            // obtains input stream of the upload file
                            inputStream = filePart.getInputStream();
                            System.out.println(inputStream);
                        }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Connection conn = null; // connection to the database
                        String message = null;  // message will be sent back to client
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
                                    System.out.println("Upload File Directory2="+fileSaveDir.getAbsolutePath()+"/"+imgname);

                                    //Get all the parts from request and write it to the file on server
                                    for (Part part : request.getParts()) {
                                        fileName = getFileName(part);
                                        if(!fileName.equals(null)|| !fileName.equals("")){
                                            try{
                                            part.write(uploadFilePath + File.separator + fileName);
                                            }catch(Exception e){
                                                break;
                                            }
                                        }

                                    }
                                    
                                    
                                    //GlassFish File Upload
                                    
                                    System.out.println(inputStream);
                                        
                                    
                                  if (inputStream != null) {
                                      
                                     // int id=new ImagesDAOImp().checkImagesId(dominate, col1, col2, col3, col4, col5, col6, col7, col8, col9);
                                         //   if(id==0){
                                                boolean sliceImage=new CreateImages().sliceImages(col1,col2,col3,col4,col5,col6,col7,col8,col9,dominate,imgname,dimention,imgtit,cat,uid,date);
                                                if(sliceImage){
                                                    AdminNotification a=new AdminNotification();
                                                    a.setUser_id(Integer.parseInt(uid));
                                                    a.setDate(timeStamp);
                                                    
                                                    a.setShow(1);
                                                    a.setType(1);
                                                    String not="New Image has uploaded by "+udi.getUn(Integer.parseInt(uid));
                                                    a.setNotification(not);
                                                    
                                                    int an=new AdminNotificationDAOImp().insertNotificaation(a);
                                                    System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPP "+an);
                                                    
                                                    boolean kl=new AdminHasNotificationDAOImp().insertNotification(udi.listAdminsIDs(),an, 1);
                                                    if(kl){
                                                        out.write("ok");
                                                    }else{
                                                        System.out.println("error in sliceimage");
                                                out.write("error");
                                                    }
                                            }else{
                                                    System.out.println("error in sliceimage");
                                                out.write("error");
                                            }
                                     // }
                                  /*else{
                                                System.out.println("error in id");
                                          out.write("error");
                                      }*/
                                      

                                      
                                      
                                  }

                            // sends the statement to the database server

                        } catch (Exception ex) {
                            message = "ERROR: " + ex.getMessage();
                            ex.printStackTrace();
                        } finally {
                            if (conn != null) {
                                // closes the database connection
                                try {
                                    conn.close();
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }
                            }

                        }

                         
            
                
                
                
                
                
                
            }         
                       
                        //out.write("ok");
              //else code          
            
                        
                        
        
    }

    
    /**
     * Utility method to get file name from HTTP header content-disposition
     */
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

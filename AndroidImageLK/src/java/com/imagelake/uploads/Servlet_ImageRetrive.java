/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.uploads;

import com.imagelake.control.DBFactory;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.out;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lakmal
 */
public class Servlet_ImageRetrive extends HttpServlet {

    protected void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
           
   
    String url = "jdbc:mysql://localhost:3306/lkdbsql5";
Connection con = null;
Statement stmt = null;
ResultSet rs = null;

    String id=request.getParameter("id");
try {
    Class.forName("com.mysql.jdbc.Driver").newInstance();
    con = DriverManager.getConnection(url,"root","123");
    stmt = con.createStatement();
    rs = stmt.executeQuery("SELECT img_blob FROM images where images_id='"+Integer.parseInt(id)+"'");
    int i = 1;
     while(rs.next()) {
        Blob len1 = rs.getBlob("img_blob");
        int len = (int)len1.length();
        byte[] b = new byte[len];
        InputStream readImg = rs.getBinaryStream(i);
        int index = readImg.read(b, 0, len);
        System.out.println("index" +index);
        stmt.close();
        response.reset();
        response.setContentType("image/jpg");
        
        
        
     
        response.getOutputStream().write(b,0,len);
        response.getOutputStream().flush();
        i+=5;
    }
} catch(Exception ex) {
    out.println(ex);
} 
 
    }
}

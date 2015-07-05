/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Lakmal
 */
public class DBFactory {
    static Connection Connection=null;
    static String userName="root";
    static String password="123";
    static String url="jdbc:mysql://localhost:3306/lkdbsql6";
    static String driver="com.mysql.jdbc.Driver";
    
    public static Connection getConnection()throws Exception{
        if (Connection == null) {
            Class.forName(driver);
            Connection=DriverManager.getConnection(url,userName, password);
        }
        return Connection;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.User;
import java.util.List;
import org.json.simple.JSONArray;

/**
 *
 * @author Lakmal
 */
public interface UserDAO {
    public boolean searchUserName(String un);
    public boolean serachEmail(String em);
    
    public User searchEmailWithUserId(String em);
    public User searchUserNameWithUserID(String un);
    public User searchPassword(String pw,int uid);
    
    public boolean insertUser(User u);
    public User searchSignIn(String un,String pw);
    
    public boolean updatePublicData(User user);
    public boolean updatePrivateData(User user);//this method is to be a contributor
    public boolean updatePassword(String pw,int uid);
    public boolean updatePhoneNo(int uid,String phn);
    
    public User getUser(int user_id);
    public boolean searchUpdatinUserName(String un,int uid);
    public boolean searchUpateEmail(String em,int uid);
    public boolean updateFnLn(String fn,String ln,int uid);
    
    public List<User> getUsers(int state);
    public List<User> listSellersBuyers();
    //public List<User> listBuyers();
    public List<User> listSellers();
    public String getUserRate();
    public List<User> listAll();
    
    public boolean updateUserState(int uid,int state);
    public String listSubAdmins();
    
    public boolean updateUserType(int uid,int type);
    public boolean deleteSubAdmin(int uid);
    
   public String getUsertype();
   
   public String getUn(int uid);
   public List<String> listAdminsIDs();
   public int allUsersCount();
   
   public String getSubadminList();
   public String getAdminUserType(int id);
   public String getJSONAllUserTypeList();
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.User;
import com.imagelake.model.Userlogin;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public interface UserloginDAO {
    public boolean insert(Userlogin ul);
    public boolean updateLogin(Userlogin ul);
    public boolean checkInvalidSession(int uid,int state);
    public String listCurrentLogedUsers(int state);
    
}

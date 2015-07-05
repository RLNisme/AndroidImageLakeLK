/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.Privilages;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public interface PrivilageDAO {
    public List<Privilages> listAll(int user_type_user_type_id);
    public boolean addPrivilage(int uid,int ifid,int state);
    public boolean updatePrivilage(int uid,int ifid,int state);
    public Privilages getFirstPrivilage(int uid);
    public boolean checkPrivilages(int uid,int infid);
}

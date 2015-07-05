/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.Interfaces;
import com.imagelake.model.InterfacesSub;
import com.imagelake.model.Privilages;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public interface InterfacesDAO {
    public List<Interfaces> listAll(ArrayList<Privilages> privilagelist);
    public Interfaces getInterface(String name);
    public boolean updateInteface(Interfaces inf);
    public String listInterfaces();
    public String listPrivilages();
}

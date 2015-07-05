/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.InterfacesSub;
import com.imagelake.model.SubInterfaces;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public interface SubInterfacesDAO {
    public List<SubInterfaces> listSub(ArrayList<InterfacesSub> interfacelist);
}

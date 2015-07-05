/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.CurrentCountry;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public interface CurCountryDAO {
   public List<CurrentCountry> listAll();
   public String countryName(int id);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.Categories;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public interface CategoriesDAO {
    public List<Categories> listAllCategories();
    public Categories getCategory(int cat_id);
    public String getImageCategory(int catid);
    public boolean insertCategory(String cat);
}

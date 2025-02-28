/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;
import enity.Category;
import java.util.Iterator;
import java.util.List;
/**
 *
 * @author Admin
 */
public class CategoryDAO extends GenericDAO<Category>{

    @Override
    public List<Category> findAll() {
       return queryGenericDAO(Category.class);
    }

    
    public static void main(String[] args) {
        for (Category category : new CategoryDAO().findAll()){
            System.out.println(category);
        }
        
    }
}

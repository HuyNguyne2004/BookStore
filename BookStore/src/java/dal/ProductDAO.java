/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import enity.Product;
import java.util.LinkedHashMap;
/**
 *
 * @author Admin
 */
import java.util.List;
import org.apache.catalina.util.ParameterMap;

/**
 *
 * @author Admin
 */
public class ProductDAO extends GenericDAO<Product> {

    public Product findById(Product product) {
        String sql = "SELECT [id]\n"
                + "      ,[name]\n"
                + "      ,[img]\n"
                + "      ,[quantity]\n"
                + "      ,[price]\n"
                + "      ,[description]\n"
                + "      ,[categoryId]\n"
                + "  FROM [dbo].[Product]\n"
                + "  WHERE id = ?";
        parameterMap = new LinkedHashMap<>();
        parameterMap.put("id", product.getId());
        List<Product> list = queryGenericDAO(Product.class, sql, parameterMap);
        // nếu như list mà empty => ko có sản phần => trả về null
        // ngược lại list mà ko empty => có sản phần => nằm ở vị trí đầu tiền => lấy về ở vị trí số 0
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Product> findAll() {
        return queryGenericDAO(Product.class);
    }

    @Override
    public int insert(Product t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Product> findByCategory(String categoryID) {
        String sql = "SELECT *\n"
                + "  FROM [Product]\n"
                + "  where categoryId = ?";
        parameterMap = new LinkedHashMap<>();
        parameterMap.put("categoryId", categoryID);
        return queryGenericDAO(Product.class, sql, parameterMap);
    }

    public List<Product> findByKeyWord(String keyword) {
        String sql = "SELECT *\n"
                + "FROM [Product]\n"
                + "WHERE name LIKE ?";
        parameterMap = new LinkedHashMap<>();
        parameterMap.put("name", "%" + keyword + "%");
        return queryGenericDAO(Product.class,sql,parameterMap);
    }

}

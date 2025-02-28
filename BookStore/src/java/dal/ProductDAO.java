/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import com.oracle.wls.shaded.org.apache.bcel.classfile.Code;
import enity.Product;
import java.util.LinkedHashMap;
import constant.CommonConst;
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

    public List<Product> findByCategory(String categoryID, int page) {
        String sql = "SELECT * \n"
                + "FROM [Product]\n"
                + "where categoryId = ?\n"
                + "ORDER BY id\n"
                + "OFFSET ? ROW\n" // (PAGE - 1) * Y
                + "FETCH NEXT ? ROW ONLY"; // NUMBER_RECORD_PER_PAGE
        parameterMap = new LinkedHashMap<>();
        parameterMap.put("categoryId", categoryID);
        parameterMap.put("offset", (page - 1) * CommonConst.RECORD_PER_PAGE);
        parameterMap.put("fetch", CommonConst.RECORD_PER_PAGE);
        return queryGenericDAO(Product.class, sql, parameterMap);
    }

    public List<Product> findByKeyWord(String keyword, int page) {
        String sql = "SELECT *\n"
                + "FROM [Product]\n"
                + "WHERE name LIKE ?";
        parameterMap = new LinkedHashMap<>();
        parameterMap.put("name", "%" + keyword + "%");
        return queryGenericDAO(Product.class, sql, parameterMap);
    }

    public int findTotalRecordByCategory(String categoryID) {
        String sql = "SELECT count (*)\n"
                + "FROM [Product]\n"
                + "where categoryId = ?";
        parameterMap = new LinkedHashMap<>();
        parameterMap.put("categoryId", categoryID);
        return findTotalRecordGenericDAO(Product.class, sql, parameterMap);
    }

    public int findTotalRecordByKeyword(String keyword) {
        String sql = "SELECT count (*)\n"
                + "FROM [Product]\n"
                + "where name like ?";
        parameterMap = new LinkedHashMap<>();
        parameterMap.put("name", keyword);
        return findTotalRecordGenericDAO(Product.class, sql, parameterMap);
    }

    public void insert(Product product) {
        insertGenericDAO(product);
    }

   

}

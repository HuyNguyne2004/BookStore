/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.homepage;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.ProductDAO;
import dal.CategoryDAO;
import enity.Product;
import enity.Category;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import enity.PageControl;
import constant.CommonConst;
/**
 *
 * @author Admin
 */
public class HomeController extends HttpServlet {

    ProductDAO productDAO = new ProductDAO();
    CategoryDAO categoryDAO = new CategoryDAO();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet HomeController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HomeController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PageControl pageControl = new PageControl();
        List<Product> listProduct = findProductDoGet(request,pageControl);
        // get list product dao
        // get list category
        List<Category> lissCategory = categoryDAO.findAll();
        // set vào bên trong session
        HttpSession session = request.getSession();
        session.setAttribute(CommonConst.SESION_PRODUCT, listProduct);
        session.setAttribute(CommonConst.SESION_CATEGORY, lissCategory);
        session.setAttribute("pageControl", pageControl);
        request.getRequestDispatcher("view/homepage/home.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private List<Product> findProductDoGet(HttpServletRequest request , PageControl pageControl) {
        List<Product> listProduct;
        // GET về page
        String pageRaw = request.getParameter("page");
        // GET về request URL
        String requestURL = request.getRequestURI().toString();
        //valid page 
        int page;
        int totalRecord = 0; 
        try {
            page = Integer.parseInt(pageRaw);
            if (page <= 0) {
                page = 1;
            }
        } catch (Exception e) {
            page = 1;
        }

        //get vể search
        String actionSearch = request.getParameter("search") == null ? "default"
                : request.getParameter("search");
        switch (actionSearch) {
            case "category":
                String categoryID = request.getParameter("categoryId");
                totalRecord = productDAO.findTotalRecordByCategory (categoryID);
                listProduct = productDAO.findByCategory(categoryID,page);
                pageControl.setUrlPattern(requestURL + "?search=category&categoryId" + categoryID + "&");
                break;
            case "searchByName":
                String keyword = request.getParameter("keyword");
                listProduct = productDAO.findByKeyWord(keyword,page);
                totalRecord = productDAO.findTotalRecordByKeyword (keyword);
                pageControl.setUrlPattern(requestURL + "?search=name&keyword" + keyword + "&");
                break;

            default:
                listProduct = productDAO.findAll();
                pageControl.setUrlPattern(requestURL + "?");
        }

        //total page
        int totalPage = (totalRecord % constant.CommonConst.RECORD_PER_PAGE) == 0
                        ? (totalRecord / constant.CommonConst.RECORD_PER_PAGE)
                        : (totalRecord / constant.CommonConst.RECORD_PER_PAGE) + 1;
        //set total record, total page, page vao pageControl
        pageControl.setPage(page);
        pageControl.setTotalPage(totalPage);
        pageControl.setTotalRecord(totalRecord);
        
        return listProduct;

    }

}

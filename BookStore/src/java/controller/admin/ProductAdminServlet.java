/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.ProductDAO;
import dal.CategoryDAO;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import enity.Product;
import jakarta.servlet.annotation.MultipartConfig;

/**
 *
 * @author Admin
 */
@MultipartConfig
public class ProductAdminServlet extends HttpServlet {
    ProductDAO productDao = new ProductDAO();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        // set enconding UTF-8
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        // get session
        HttpSession session = request.getSession();
        // get action
        String action = request.getParameter("action") == null
                ? ""
                : request.getParameter("action");
        switch (action) {
            case "add":
                addProduct(request, response);
                break;
            default:
                throw new AssertionError();
        }
        response.sendRedirect("dashboard");
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

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            // Get name
            String name = request.getParameter("name");

            // Get price
//            double price= Double.parseDouble(request.getParameter("price"));

            // Get quantity
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            // Get description
            String description = request.getParameter("description");

            // Get category ID
            int categoryId = Integer.parseInt(request.getParameter("category"));

            //image 
            Part part = request.getPart("image");

            // link to save img
            String path = request.getServletContext().getRealPath("/images");
            File dir = new File(path);
            // check the link exits
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File image = new File(dir, part.getSubmittedFileName());
            // write file to the link
            part.write(image.getAbsolutePath());
            //get the context path project
            String pathOfFile = request.getContextPath() + "/images/" + image.getName();

            Product product = Product.builder()
                    .name(name)
                    .quantity(quantity)
                    .categoryId(categoryId)
                    .description(description)
                    .image(pathOfFile)
                    .build();
           productDao.insert(product);
        } catch (NumberFormatException | IOException | ServletException e) {  // Sử dụng cùng một biến 'e'
            e.printStackTrace();
        }
    }

}

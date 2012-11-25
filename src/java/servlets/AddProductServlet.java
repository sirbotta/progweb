/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import db.DBManager;
import db.Category;
import helpers.PageHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author simone
 */
//servlet per la pagina che aggiunge i prodotti a db dal seller
public class AddProductServlet extends HttpServlet {

    private DBManager manager;

    @Override
    public void init() throws ServletException {
        // inizializza il DBManager dagli attributi di Application
        this.manager = (DBManager) super.getServletContext().getAttribute("dbmanager");
    }    
    
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        PageHelper page = new PageHelper("Add Product - Seller");
        List<Category> categorie = manager.getCategories();
        
        String body ="";
        body += "<div class='container-fluid' >\n";
        //body += "<div class='row-fluid'>\n";
        //body += "<div class='span12'></div>\n";
        //body += "</div>\n";
        body += "<div class='row-fluid'>\n";
        body += "<div class='span3 offset1'>\n";
        body += "<a href='"+ getServletContext().getContextPath()+"/Seller" +"'>Home</a>\n";
        body += "</div>\n";
        body += "<div class='span6'>\n";
        body += "<h2>Aggiungi Prodotto</h2>\n";
        body += "</div>\n";
        body += "<div class='span1'>\n";
        body += "<a href='"+ getServletContext().getContextPath()+"/Logout" +"'>Sign out</a>\n";
        body += "</div>\n";
        body += "</div>\n";
        body += "<div class='row-fluid'>\n";
        body += "<div class='span8 offset2' >\n";
        body += "<form action='"+ getServletContext().getContextPath()+"/InsertProduct" +"' method='POST' >\n";
        body += "<h5>Aggiungi: <input type='text' placeholder='Nome prodotto' name='product'/><h5>\n";
        body += "</div>\n";
        body += "</div>\n";
        body += "<!-- end of row-fluid -->\n";
        body += "<div class='row-fluid'>\n";
        body += "<div class='span2'></div>\n";
        body += "<div class='span10' >\n";
        body += "<p>\n";       
        body += "Categoria: <select name='cat_id'>";
        /*
         * Carico le categorie in cui è possibile aggiungere il prodotto
         */
        for( Category categoria:categorie ){
            body += "<option value='"+ categoria.getId() +"'>"+ categoria.getName() +"</option>";
        }
        body += "</select>";
        
        body += "<p>\n";
        body += "Quantit&agrave;: <input type='text' placeholder='0' class='span2' name='qnt'/>\n";
        body += "<input type='text' placeholder='Unit&agrave; di misura es. \"Kg\"' class='span2' name='um'/>\n";
        body += "</p>\n";
        body += "<p>\n";
        body += "Prezzo: <input type='text' placeholder='0.00' class='span2' name='price'/> &#8364;\n";
        body += "</p>\n";
        body += "<p>\n";
        body += "Aggiungi il nome dell'immagine del prodotto<br>\n";
        body += "<input type='text' placeholder='example.jpg' name='img_url'>\n";
        body += "</div>\n";
        body += "<!-- end of row-fluid -->\n";
        body += "<div class='row-fluid'>\n";
        body += "<div class='span2 offset2'>";
        body += "<button class='btn btn-mini' onClick='history.go(-1);return true;'>Annulla</button>";
        body += "</div>";
        body += "<div class='span2 offset2' >\n";
        body += "<button type='reset' class='btn btn-mini'>Resetta</button>\n";
        body += "</div>";
        body += "<div class='span2'>\n";
        body += "<button type='submit' class='btn btn-mini'>Conferma</button>\n";
        body += "</form>\n";
        body += "</div>\n";
        body += "<div class='span2'></div>\n";
        body += "</div>\n";
        body += "<!-- end of row-fluid -->\n";
        body += "</div>\n";
        body += "<!-- end of container-fluid -->\n";

        page.addContent(body);
        
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println(page.getFullPage());
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AddProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AddProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
}

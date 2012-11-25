/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import db.DBManager;
import db.Product;
import helpers.PageHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Samuele
 */
//pagina per confermare la creazione di un ordine da un buyer, inoltre crea il pdf con la ricevuta
public class ConfirmProductsServlet extends HttpServlet {
    
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
        
        Product prodotto = manager.getProductById(Integer.parseInt(request.getParameter("product")));        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        PageHelper page = new PageHelper("Products - buyer");
        
        String body = "";
        body += "<div class='container-fluid' >";
        //body += "<div class='row-fluid'>";
        //body += "<div class='span12'></div>";
        //body += "</div>";
        body += "<div class='row-fluid'>";
        body += "<div class='span3 offset1'>";
        body += "<a href='"+ getServletContext().getContextPath()+"/Buyer" +"'>Home</a>";
        body += "</div>";
        body += "<div class='span6'>";
        body += "<h1>Conferma Ordine</h1>";
        body += "</div>";
        body += "<div class='span1'>";
        body += "<a href='#'>Sign out</a>";
        body += "</div>";
        body += "</div>";
        body += "<div class='row-fluid'>";
        body += "<div class='span2'></div>";
        body += "<div class='span8' >";
        body += "<h5>"+ prodotto.getName() +"<h5>";
        body += "</div>";
        body += "<div class='span2'></div>";
        body += "</div>";
        body += "<!-- end of row-fluid -->";
        body += "<div class='row-fluid'>";
        body += "<div class='span3'></div>";
        body += "<div class='span6' >";
        body += "<p>Quantit&agrave;:"+ prodotto.getQuantity()+" "+ prodotto.getUm() +"</p>";
        body += "<p>Venditore: "+ prodotto.getUser() +"</p>";
        body += "<p>Prezzo totale: "+ prodotto.getPrice() +"&#8364;</p>";
        body += "</div>";
        body += "<div class='span3'></div>";
        body += "</div>";
        body += "<!-- end of row-fluid -->";
        body += "</div>";
        body += "<!-- end of container-fluid -->";
        body += "<div class='row-fluid'>";
        body += "<div class='span2 offset2'>";
        body += "<button class='btn btn-small' onClick='history.go(-1);return true;'>Annulla</button>";
        body += "</div>";
        body += "<div class='span2 offset4' >";
        body += "<a href='"+ getServletContext().getContextPath()+"/ProcessOrder?product_id="+prodotto.getId()+"'><button class='btn btn-small' >Compra</button></a>";
        body += "</div>";
        body += "<div class='span2'></div>";
        body += "</div>";
        body += "<!-- end of row-fluid -->";
        page.addContent(body);
        
        try {
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
            Logger.getLogger(ConfirmProductsServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ConfirmProductsServlet.class.getName()).log(Level.SEVERE, null, ex);
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

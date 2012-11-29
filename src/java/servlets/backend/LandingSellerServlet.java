/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.backend;

import db.DBManager;

import db.Product;
import db.Order;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author simone
 */
//pagine iniziale per un seller dopo essersi loggato
public class LandingSellerServlet extends HttpServlet {

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
        HttpSession session = request.getSession(false);
        PrintWriter out = response.getWriter();
        PageHelper page = new PageHelper("Landing page - Seller");
        List<Product> prodotti = manager.getProductsBySeller(Integer.parseInt(session.getAttribute("user_id").toString()));
        List<Order> ricevute = manager.getOrdersBySeller(Integer.parseInt(session.getAttribute("user_id").toString()));


        String body = "";

        body += "<div class='container-fluid' >\n";
        //body += "<div class='row-fluid'>\n";
        //body += "<div class='span12'></div>\n";
        //body += "</div>\n";
        body += "<div class='row-fluid'>\n";
        body += "<div class='span1'></div>\n";
        body += "<div class='span2'>\n";
        body += "<a href='"+ getServletContext().getContextPath()+"/Seller" +"'>Home</a>\n";
        body += "</div>\n";
        body += "<div class='span5'>\n";
        body += "<h2>"+ session.getAttribute("username") +"'s - Products</h2>\n";
        body += "</div>\n";
        body += "<div class='span4'>\n";
        body += "<a href='"+ getServletContext().getContextPath()+"/Logout" +"'>Sign out</a>\n";
        body += "</div>\n";
        body += "</div>\n";
        body += "<div class='row-fluid'>";
        body += "<div class='span1'></div>";
        body += "<div class='span10' id='scana'>";
        body += "<div class='tabbable tabs-left'>";
        body += "<ul class='nav nav-tabs'>";
        /*
         * inizio il ciclo per caricare l'ul dei prodotti messi in vendita
         * dall'utente
         * <li><a href='#tabN' data-toggle='tab'>Fasoli</a></li> 
         */
        int c = 1;
        for (Product prodotto : prodotti) {
            body += "<li><a href='#tab" + c + "' data-toggle='tab'>" + prodotto.getName() + "</a></li>\n";
            c++;
        }

        body += "<a href=" + getServletContext().getContextPath() + "/AddProduct"
                + ">\n";
        body += "<li><strong>Aggiungi Prodotto</strong></li></a>\n";
        body += "</ul>\n";
        body += "<div class='tab-content'>\n";
        c = 1;
        for (Product prodotto : prodotti) {
            body += "<div class='tab-pane' id='tab" + c + "'>";
            body += "<div class='span9'>";
            body += "<h5>Prezzo " + prodotto.getPrice() + "&#128;</h5>";
            body += "<p>";
            body += "Categoria " + prodotto.getCategory() + "<br>";
            body += "</p>";
            body += "<p>";
            if (prodotto.getQuantity() != 0) {
                body += "Disponibili " + prodotto.getQuantity() + " " + prodotto.getUm() + "<br>";
            } else {
                body += "Venduto <br>";
            }
            body += "</p>";
            body += "</div>";
            if (!prodotto.getUrlImage().isEmpty()) {
                body += "<img src='" + getServletContext().getContextPath() + "/img/" + prodotto.getUrlImage() + "' alt='' class='img-rounded span3'>";
            } else {
                body += "<img src='" + getServletContext().getContextPath() + "/img/na.gif' alt='' class='img-rounded span3'>";
            }
            body += "</div>";
            c++;
        }
        body += "</div>\n";
        body += "<div class='span1'></div>\n";
        body += "</div>\n";
        body += "<!-- end of row-fluid -->\n";
        body += "</div>\n";
        body += "<!-- end of container-fluid -->\n";
        body += "</div>\n";
        body += "<div class='row-fluid'>\n";
        body += "<div class='span2'></div>\n";
        body += "<div class='span2'>\n";
        body += "<h5>" + session.getAttribute("username") + " - Order List </h5>\n";
        body += "</div>\n";
        body += "<div class='span8'></div>\n";
        body += "</div>\n";
        body += "<!-- end of row-fluid -->\n";
        body += "<div class='row-fluid'>\n";
        body += "<div class='span10 offset1'>\n";
        body += "<table class='table table-striped'>\n";
        body += "<thead>\n";
        body += "<tr>\n";
        body += "<td>PRODOTTO</td><td>ACQUIRENTE</td><td>RECEIPT</td><td>PRICE</td><td>DATE</td></b>\n";
        body += "</tr>\n";
        body += "</thead>\n";
        body += "<tbody>\n";
        /*
         * Inizio il ciclo per caricare la lista delle ricevute
         * body += "<tr>\n";
         * body += "<td>Cipolline</td><td>gino</td><td><a href='#'>url receipt</a></td><td>55e</td><td>10/12/2012</td>\n";
         * body += "</tr>\n";
         */
        for (Order ordine : ricevute) {
            body += "<tr>\n";
            body += "<td>" + ordine.getProduct() + "</td>"
                    + "<td>" + ordine.getBuyer() + "</td><td><a href='" + getServletContext().getContextPath() + "/receipts/" + ordine.getUrlReceipt() + "'>Ricevuta</a></td>"
                    + "<td>" + ordine.getTotalPrice() + "&#128;</td><td>" + ordine.getDate() + "</td>\n";
            body += "</tr>\n";
        }
        body += "</tbody>\n";
        body += "</table>\n";
        body += "</div>\n";
        body += "</div>\n";
        body += "<!-- end of row-fluid -->\n";
        body += "</div>\n";


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
            Logger.getLogger(LandingSellerServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(LandingSellerServlet.class.getName()).log(Level.SEVERE, null, ex);
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

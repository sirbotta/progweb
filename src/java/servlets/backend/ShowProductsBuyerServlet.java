/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.backend;

import db.DBManager;
import db.Order;
import db.Product;
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
 * @author Samuele
 */
//servlet che mostra i prodotti da una categoria scelta dal buyer
public class ShowProductsBuyerServlet extends HttpServlet {
    
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
            throws SQLException,ServletException, IOException  {
        List<Product> prodotti = manager.getProductsByCategory(Integer.parseInt(request.getParameter("cat")));
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        PageHelper page = new PageHelper("Products - buyer");
        
        String body = "";
        body += "<div class=\"container-fluid\" >";
        body += "<div class='row-fluid'>\n";
        body += "<div class='span1'></div>\n";
        body += "<div class='span2'>\n";
        body += "<a href='"+ getServletContext().getContextPath()+"/Seller" +"'>Home</a>\n";
        body += "</div>\n";
        body += "<div class='span5'>\n";
        body += "<h2>"+ manager.getCategoryById(Integer.parseInt(request.getParameter("cat"))).getName() +"</h2>\n";
        body += "</div>\n";
        body += "<div class='span4'>\n";
        body += "<a href='"+ getServletContext().getContextPath()+"/Logout" +"'>Sign out</a>\n";
        body += "</div>\n";
        body += "</div>\n";
        body += "<div class=\"row-fluid\">\n";
        body += "<div class=\"span1\"></div>\n";
        body += "<div class=\"span10\" id=\"scana\">\n";
        body += "<div class=\"tabbable tabs-left\">\n";
        body += "<ul class=\"nav nav-tabs\">\n";
        /*
         * Il ciclo scorre i prodotti ottenuti dal db manager
         * e crea un <li><a href=\"#tabN\" data-toggle=\"tab\">NomeProdotto</a></li> 
         * per ogni prodotto trovato
         */
        int c=1;
        for(Product prodotto:prodotti){
            body += "<li><a href='#tab"+ c +"' data-toggle='tab'>"+ prodotto.getName() +"</a></li>\n";
            c++;
        }
        c=1;
        body += "</ul>\n";
        /*
         * Inizio il ciclo per le informazioni dei songoli prodotti
         *  body += "<div class=\"tab-pane\" id=\"tab1\">\n";
         *      body += "<div class=\"span9\">\n";
         *          body += "<h5>Prezzo</h5>\n";
         *          body += "<p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>\n";
         *         body += "</div>\n";
         *  body += "<img src=\"frutta.jpg\" alt=\"\" class=\"img span3\">\n";
         * 
         */
        body += "<div class=\"tab-content\">\n";
        for(Product prodotto:prodotti){
            body += "<div class=\"tab-pane\" id='tab"+ c +"'>\n";
            body += "<div class=\"span9\">\n";
            body += "<h5>Prezzo "+ prodotto.getPrice() +"&#128;</h5>\n";
            body += "<p>Disonibili "+ prodotto.getQuantity()+" "+ prodotto.getUm() +"<br>"
                    + "Venditore "
                    + prodotto.getUser()
                    +"<br><br>";
                    
            if( prodotto.getQuantity()==0 ){
                body += "PRODOTTO NON DISPONIBILE";
            }
            else{
                body += "<a href='"
                        + getServletContext().getContextPath()+
                        "/Confirm?product="+ prodotto.getId()
                        +"'><button class='btn btn-mini'>Compra</button></a>";                        
            }
            body += "</p></div>\n";
            if (!prodotto.getUrlImage().isEmpty()) {
                body += "<img src='" + getServletContext().getContextPath() + "/img/"+prodotto.getUrlImage() + "' alt='' class='img-rounded span3'>";
            } else {
                body += "<img src='" + getServletContext().getContextPath() + "/img/na.gif' alt='' class='img-rounded span3'>";
            }
            body += "</div>\n";
            c++;
        }       
  
        body += "</div>\n";                
        body += "<div class=\"span1\"></div>\n";
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
            Logger.getLogger(ShowProductsBuyerServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ShowProductsBuyerServlet.class.getName()).log(Level.SEVERE, null, ex);
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

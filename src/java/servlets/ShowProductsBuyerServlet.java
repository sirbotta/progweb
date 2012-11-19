/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import helpers.PageHelper;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Samuele
 */
//servlet che mostra i prodotti da una categoria scelta dal buyer
public class ShowProductsBuyerServlet extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        PageHelper page = new PageHelper("Products - buyer");
        String body = "";
        body += "<div class=\"container-fluid\" >";
        body += "<div class=\"row-fluid\">";
        body += "<div class=\"span12\"></div>";
        body += "</div>";
        body += "<div class=\"row-fluid\">";
        body += "<div class=\"span3\"></div>";
        body += "<div class=\"span6\">";
        body += "<h1>Category</h1>";
        body += "</div>";
        body += "<div class=\"span3\">\n";
        body += "<a href=\"#\">Sign out</a>\n";
        body += "</div>\n";
        body += "</div>";
        body += "<div class=\"row-fluid\">";
        body += "<div class=\"span2\"></div>";
        body += "<div class=\"span8\" id=\"scana\">";
        body += "<div class=\"tabbable tabs-left\" id=\"ciccio\">";
        body += "<ul class=\"nav nav-tabs\">";
        body += "<li><a href=\"#tab1\" data-toggle=\"tab\">Name 1</a></li>";
        body += "<li><a href=\"#tab2\" data-toggle=\"tab\">Name 2</a></li>";
        body += "</ul>";
        body += "<div class=\"tab-content\">";
        body += "<div class=\"tab-pane\" id=\"tab1\">";
        body += "<div class=\"span9\">";
        body += "<h5>Prezzo</h5>";
        body += "<p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>";
        body += "</div>";
        body += "<img src=\"frutta.jpg\" alt=\"\" class=\"img span3\">";
        body += "</div>";
        body += "<div class=\"tab-pane\" id=\"tab2\">";
        body += "<img src=\"verdure.jpg\" alt=\"\" class=\"img span3\">";
        body += "</div>";
        body += "</div>";
        body += "</div>";
        body += "</div>";
        body += "<div class=\"span2\"></div>";
        body += "</div>";
        body += "<!-- end of row-fluid -->";
        body += "</div>";
        body += "<!-- end of container-fluid -->";

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
        processRequest(request, response);
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
}

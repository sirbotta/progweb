/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import db.DBManager;
import db.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author simone
 */
//servlet che controlla il login utente e la sessione
public class LoginServlet extends HttpServlet {

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
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user;
        // controllo nel DB se esiste un utente con lo stesso username + password User user;
        try {
            user = manager.authenticate(username, password);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }

        if (user == null) {
            request.setAttribute("message", "Username/password non esistente !");
            RequestDispatcher rd = request.getRequestDispatcher("/errorelogin");
            rd.forward(request, response);
        } else {

            // imposto l'utente connesso come attributo di sessione
            // per adesso e' solo un oggetto String con il nome dell'utente,ma posso metterci anche un oggetto User
            // con, ad esempio, il timestamp di login
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            session.setAttribute("username", username);
            // mando un redirect alla servlet che carica i prodotti
            if ("buyer".equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/Buyer");
            } else {
                response.sendRedirect(request.getContextPath() + "/Seller");
            }
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

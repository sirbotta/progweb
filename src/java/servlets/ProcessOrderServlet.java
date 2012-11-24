/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import db.DBManager;
import db.Product;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ProcessOrderServlet extends HttpServlet {

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

        HttpSession session = request.getSession(false);
        int buyer_id = Integer.parseInt(session.getAttribute("user_id").toString());
        int product_id = Integer.parseInt(request.getParameter("product_id"));

        Product p = manager.getProductById(product_id);
        //@TODO check product quantity
        if (p.getQuantity() != 0) {

            //create pdf receipt
            String filename = hashWord(buyer_id+""+product_id+""+(int)Math.random()*10000)+".pdf";
            String path = getServletContext().getRealPath("/receipts/" + filename);
            // step 1: creation of a document-object
            Document document = new Document();
            try {
                // step 2:

                PdfWriter.getInstance(document, new FileOutputStream(path));



                // step 3: we open the document
                document.open();

                //write bla bla blah
                List list = new List(true, 20);
                list.add(new ListItem(""+product_id));
                list.add(new ListItem(""+buyer_id));
                list.add(new ListItem(""+p.getQuantity()));
                list.add(new ListItem(""+buyer_id));
                list.add(new ListItem(""+p.getPrice()));
                document.add(list);

            } catch (DocumentException de) {
                System.err.println(de.getMessage());
            } catch (IOException ioe) {
                System.err.println(ioe.getMessage());
            }

            // step 5: we close the document
            document.close();


            //creare l'ordine
            manager.createOrder(product_id, buyer_id, p.getQuantity(), p.getPrice(), "receipts/" + filename);

            //rimuovere il prodotto (metterlo a 0)

            manager.removeProduct(product_id);

            //redirect landing page
            
            
            //il forward sarebbe da evitare in questo caso anche se viene intercettata la casistica di refresh
            //request.setAttribute("message", "Hai acquistato "+p.getQuantity()+""+p.getUm()+" di "+p.getName());
            //request.setAttribute("message_type", "success");
            //RequestDispatcher rd = request.getRequestDispatcher("/Buyer");
            //rd.forward(request, response);
            
            
            response.sendRedirect(request.getContextPath() + "/Buyer");

        } else {
            //forward in caso di prodotto a zero
            request.setAttribute("message", "Il prodotto è esaurito");
            request.setAttribute("message_type", "warning");
            RequestDispatcher rd = request.getRequestDispatcher("/Buyer");
            rd.forward(request, response);
        }


    }
    
    private String hashWord(String word) {
        String hashstring = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(word.getBytes());
            BigInteger hash = new BigInteger(1, md5.digest());
            hashstring = hash.toString(16);
        } catch (NoSuchAlgorithmException nsae) {
        // ignore
        }
        return hashstring;
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
            Logger.getLogger(ProcessOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ProcessOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
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

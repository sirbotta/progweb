/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author simone
 */
//Classe che gestice tutte le query a db
public class DBManager implements Serializable {
    // transient == non viene serializzato

    private transient Connection con;
    
    //inizializza il db gestendo le eccezioni,  WebAppContextLIstener lo avvia per ogni sessione
    public DBManager(String dburl) throws SQLException {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver", true,
                    getClass().getClassLoader());
        } catch (Exception e) {
            throw new RuntimeException(e.toString(), e);
        }
        Connection con = DriverManager.getConnection(dburl);
        this.con = con;
    }
    
    //chiude il db dopo la sessione utente
    public static void shutdown(){
    try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).info(ex.getMessage());
        }
    }
    
    //controlla se esiste l'utente e restituisce l'oggetto user con i dati
    public User authenticate(String username, String password);
        
    //recupera la lista delle categorie dal DB
    public List<String> getCategory();
    
    //recupera la lista dei prodotti dal DB per categorie (solo buyer)
    public List<Product> getProductsByCategory(String category);
    
    //recupera la lista dei prodotti dal db per un seller
    public List<Product> getProductsBySeller(int seller_id);
    
    //recupera la lista degli ordini dal db per un seller
    public List<Product> getOrdersBySeller(int seller_id);
    
    //recupera la lista degli ordini dal db per un buyer
    public List<Product> getOrdersByBuyer(int buyer_id);
    
    //aggiunge un prodotto nel DB 
    public void addProductByBuyer(int buyer_id,String product_name,int quantity,double price, String Um);
    
    //crea un ordine a db e scala la quantità da products, se fallisce restituisce false
    public Boolean createOrder(int Seller_id,int quantity);
    
    
    
    
    
}

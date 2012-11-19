/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public static void shutdown() {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).info(ex.getMessage());
        }
    }

    //controlla se esiste l'utente e restituisce l'oggetto user con i dati
    public User authenticate(String username, String password) throws SQLException {
         PreparedStatement stm = con.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
        try {
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            try {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(username);
                    user.setRole(rs.getString("role"));
                    return user;
                } else {
                    return null;
                }
            } finally {
                // ricordarsi SEMPRE di chiudere i ResultSet in un blocco
                rs.close();
            }
        } finally { // ricordarsi SEMPRE di chiudere i PreparedStatement in un blocco finally
            stm.close();
        }
        
        
    }

    //recupera la lista delle categorie dal DB
    public List<Category> getCategories() throws SQLException {
        List<Category> categoryList = new ArrayList<Category>();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM category");
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Category c = new Category(); 
                    c.setId(rs.getInt("id"));
                    c.setName(rs.getString("name"));
                    categoryList.add(c);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return categoryList;       
    }

    //recupera la lista dei prodotti dal DB per categorie (solo buyer)
    public List<Product> getProductsByCategory(String category_id) throws SQLException {
         List<Product> products = new ArrayList<Product>();
        PreparedStatement stm = con.prepareStatement("SELECT products.*,category.name,users.username "
                + "FROM products,category,users "
                + "WHERE products.category_id = ? "
                + "AND category.id = products.category_id "
                + "AND user.id = seller.id");
        
        stm.setString(1, category_id);
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getInt("products.id"));
                    p.setName(rs.getString("products.name"));
                    p.setUser(rs.getString("user.username"));
                    p.setCategory(rs.getString("category.name"));
                    p.setQuantity(rs.getInt("products.quantity"));
                    p.setPrice(rs.getDouble("products.price"));
                    p.setUm(rs.getString("products.um"));
                    p.setUrlImage(rs.getString("products.url_image"));
                    p.setDate(rs.getDate("products.date_timeinsert"));
                    products.add(p);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return products;
    }

    //recupera la lista dei prodotti dal db per un seller
    public List<Product> getProductsBySeller(int seller_id) {
        return new ArrayList<Product>();
    }

    //recupera la lista degli ordini dal db per un seller
    public List<Product> getOrdersBySeller(int seller_id) {
        return new ArrayList<Product>();
    }

    //recupera la lista degli ordini dal db per un buyer
    public List<Product> getOrdersByBuyer(int buyer_id) {
        return new ArrayList<Product>();
    }

    //aggiunge un prodotto nel DB 
    public void addProductByBuyer(int buyer_id, String product_name, int quantity, double price, String Um) {
    }

    //crea un ordine a db e scala la quantità da products, se fallisce restituisce false
    public Boolean createOrder(int product_id, int quantity) {
        return true;
    }
}

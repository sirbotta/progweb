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
    
    public Product getProductById(int product_id) throws SQLException {
        Product p = new Product();
        PreparedStatement stm = con.prepareStatement(
                "SELECT products.*,category.name as category,users.USERNAME as seller "
                + "FROM products "
                + "INNER JOIN category on products.category_id=category.id "
                + "INNER JOIN users on products.SELLER_ID=users.id "
                + "WHERE products.id = ?");

        stm.setInt(1, product_id);
        try {
            ResultSet rs = stm.executeQuery();
            try {
                if (rs.next()) {
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setUser(rs.getString("seller"));
                    p.setCategory(rs.getString("category"));
                    p.setQuantity(rs.getInt("quantity"));
                    p.setPrice(rs.getDouble("price"));
                    p.setUm(rs.getString("um"));
                    p.setUrlImage(rs.getString("url_image"));
                    p.setDate(rs.getDate("date_timeinsert"));
                    return p;
                } else
                {
                    return null;
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
    }
    

    //recupera la lista dei prodotti dal DB per categorie (solo buyer)
    public List<Product> getProductsByCategory(int category_id) throws SQLException {
        List<Product> products = new ArrayList<Product>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT products.*,category.name as category,users.USERNAME as seller "
                + "FROM products "
                + "INNER JOIN category on products.category_id=category.id "
                + "INNER JOIN users on products.SELLER_ID=users.id "
                + "WHERE products.category_id = ?");

        stm.setInt(1, category_id);
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setUser(rs.getString("seller"));
                    p.setCategory(rs.getString("category"));
                    p.setQuantity(rs.getInt("quantity"));
                    p.setPrice(rs.getDouble("price"));
                    p.setUm(rs.getString("um"));
                    p.setUrlImage(rs.getString("url_image"));
                    p.setDate(rs.getDate("date_timeinsert"));
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
    public List<Product> getProductsBySeller(int seller_id) throws SQLException {
        List<Product> products = new ArrayList<Product>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT products.*,category.name as category,users.USERNAME as seller "
                + "FROM products "
                + "INNER JOIN category on products.category_id=category.id "
                + "INNER JOIN users on products.SELLER_ID=users.id "
                + "WHERE products.seller_id = ?");

        stm.setInt(1, seller_id);
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setUser(rs.getString("seller"));
                    p.setCategory(rs.getString("category"));
                    p.setQuantity(rs.getInt("quantity"));
                    p.setPrice(rs.getDouble("price"));
                    p.setUm(rs.getString("um"));
                    p.setUrlImage(rs.getString("url_image"));
                    p.setDate(rs.getDate("date_timeinsert"));
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

    //recupera la lista degli ordini dal db per un seller
    public List<Order> getOrdersBySeller(int seller_id) throws SQLException {

        List<Order> orders = new ArrayList<Order>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT products.*,orders.*,buyers.USERNAME as buyer , sellers.USERNAME as seller "
                + "FROM orders "
                + "INNER JOIN products on products.ID=orders.PRODUCT_ID "
                + "INNER JOIN users as buyers on orders.BUYER_ID=buyers.id "
                + "INNER JOIN users as sellers on products.SELLER_ID=sellers.id "
                + "WHERE products.SELLER_ID = ?");

        stm.setInt(1, seller_id);
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Order o = new Order();
                    o.setId(rs.getInt("id"));
                    o.setBuyer(rs.getString("buyer"));
                    o.setSeller(rs.getString("seller"));
                    o.setProduct(rs.getString("name"));
                    o.setQuantity(rs.getInt("quantity"));
                    o.setTotalPrice(rs.getDouble("price"));
                    o.setUrlReceipt(rs.getString("url_receipt"));
                    o.setUm(rs.getString("um"));
                    o.setDate(rs.getDate("date_timestamp"));
                    orders.add(o);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return orders;
    }

    //recupera la lista degli ordini dal db per un buyer
    public List<Order> getOrdersByBuyer(int buyer_id) throws SQLException {
        List<Order> orders = new ArrayList<Order>();
        PreparedStatement stm = con.prepareStatement(
                "SELECT products.*,orders.*,buyers.USERNAME as buyer , sellers.USERNAME as seller "
                + "FROM orders "
                + "INNER JOIN products on products.ID=orders.PRODUCT_ID "
                + "INNER JOIN users as buyers on orders.BUYER_ID=buyers.id "
                + "INNER JOIN users as sellers on products.SELLER_ID=sellers.id "
                + "WHERE orders.BUYER_ID = ?");

        stm.setInt(1, buyer_id);
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Order o = new Order();
                    o.setId(rs.getInt("id"));
                    o.setBuyer(rs.getString("buyer"));
                    o.setSeller(rs.getString("seller"));
                    o.setProduct(rs.getString("name"));
                    o.setQuantity(rs.getInt("quantity"));
                    o.setTotalPrice(rs.getDouble("price"));
                    o.setUrlReceipt(rs.getString("url_receipt"));
                    o.setUm(rs.getString("um"));
                    o.setDate(rs.getDate("date_timestamp"));
                    orders.add(o);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return orders;
    }

    //aggiunge un prodotto nel DB 
    public int addProduct(String product_name, int seller_id, int category_id, int quantity, String UM, double price, String url_image) throws SQLException {
        PreparedStatement stm = con.prepareStatement(
                "INSERT INTO APP.PRODUCTS (NAME, SELLER_ID, CATEGORY_ID, QUANTITY, UM, PRICE, DATE_TIMEINSERT, URL_IMAGE) "
                + "VALUES (?,?,?,?,?,?,?,?)");


        java.util.Date today = new java.util.Date();
        java.sql.Date sqlToday = new java.sql.Date(today.getTime());

        stm.setString(1, product_name);
        stm.setInt(2, seller_id);
        stm.setInt(3, category_id);
        stm.setInt(4, quantity);
        stm.setString(5, UM);
        stm.setDouble(6, price);
        stm.setDate(7, sqlToday);
        stm.setString(8, url_image);

        try {
            return stm.executeUpdate();
        } finally {
            stm.close();
        }
    }

    //crea un ordine a db 
    public int createOrder(int product_id, int buyer_id, int quantity, double price, String receipt_url) throws SQLException {
        PreparedStatement stm = con.prepareStatement(
                "INSERT INTO ORDERS (DATE_TIMESTAMP, PRODUCT_ID, BUYER_ID, QUANTITY, TOTAL_PRICE, URL_RECEIPT) "
                + "VALUES (?, ?, ?, ?, ?, ?)");

        java.util.Date today = new java.util.Date();
        java.sql.Date sqlToday = new java.sql.Date(today.getTime());
        stm.setDate(1, sqlToday);
        stm.setInt(2, product_id);
        stm.setInt(3, buyer_id);
        stm.setInt(4, quantity);
        stm.setDouble(5, price);
        stm.setString(6, receipt_url);

        try {
            return stm.executeUpdate();
        } finally {
            stm.close();
        }
    }

    //scala la quantità da products, se fallisce restituisce 0
    public int removeProduct(int product_id) throws SQLException {
        PreparedStatement stm = con.prepareStatement(
                "UPDATE products "
                + "SET quantity=0 "
                + "WHERE id= ?");
        stm.setInt(1, product_id);

        try {
            return stm.executeUpdate();
        } finally {
            stm.close();
        }
    }
}

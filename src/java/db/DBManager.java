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

/**
 *
 * @author simone
 */
public class DBManager implements Serializable {
    // transient == non viene serializzato

    private transient Connection con;

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
    
    public static void shutdown(){}
    
    public User authenticate(String username, String password);
        
    public List<String> getCategory();
    
    public List<Product> getProductsByCategory(String category);
    
    
    
    
    
    
}

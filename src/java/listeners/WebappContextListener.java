/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package listeners;

import db.DBManager;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author simone
 */
public class WebappContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String dburl =
                sce.getServletContext().getInitParameter("dburl");
        try {
            DBManager manager = new DBManager(dburl);
            sce.getServletContext().setAttribute("dbmanager", manager);//pubblico l'attributo per il context
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).severe(ex.toString());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Il database Derby deve essere "spento" tentando diconnettersi al database con shutdown=true
        DBManager.shutdown();
    }
}

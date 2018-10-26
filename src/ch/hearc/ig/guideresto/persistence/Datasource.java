/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.guideresto.persistence;

/**
 *
 * @author maxime.parret
 */
import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.pool.OracleDataSource;

public class Datasource {
    
    private static Connection cn;
    
    public void openSession() throws SQLException{
        cn = createConnection();
    }
    
    public void closeSession() throws SQLException{
        cn.close();
    }
    
    public void commitTransaction() throws SQLException{
        cn.commit();
    }
    
    public void rollbackTransaction() throws SQLException{
        cn.rollback();
    }
    
    // methode qui retourne une nouvelle connection
    private Connection createConnection() throws SQLException {
        OracleDataSource ds = new OracleDataSource();

        //L'implémentation du Datasource est propre à chaque constructeur
        //L'interface Datasource ne propose qu'une méthode (getConnection())
        //Il faut donc la caster pour utiliser les méthodes propres à l'implémentation
        ((OracleDataSource) ds).setDriverType("thin");
        ((OracleDataSource) ds).setServerName("db.ig.he-arc.ch");
        ((OracleDataSource) ds).setDatabaseName("ens2");
        ((OracleDataSource) ds).setPortNumber(1521);
        ((OracleDataSource) ds).setUser("maxime_parret");
        ((OracleDataSource) ds).setPassword("maxime_parret");
        
        Datasource.cn = ds.getConnection();
        Datasource.cn.setAutoCommit(false);
        return cn;
    }

    public static Connection getConnection() {
        return cn;
    }
    
   
}

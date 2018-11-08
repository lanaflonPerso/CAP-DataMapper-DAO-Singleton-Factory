/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.guideresto.persistence.oracleDAO;

/**
 *
 * @author maxime.parret
 */
import ch.hearc.ig.guideresto.persistence.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.pool.OracleDataSource;

public class OracleDatasource extends DataSource {

  private static Connection cn = null;

  private void createConnection() throws SQLException {
    try {
      OracleDataSource ds = new OracleDataSource();

      ds.setDriverType("thin");
      ds.setServerName("db.ig.he-arc.ch");
      ds.setDatabaseName("ens2");
      ds.setPortNumber(1521);
      ds.setUser("maxime_parret");
      ds.setPassword("maxime_parret");

      OracleDatasource.cn = ds.getConnection();
      OracleDatasource.cn.setAutoCommit(false);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Connection getConnection() {
    try {
      if (cn == null) {
        createConnection();
      }
      return cn;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void openSession() {
    getConnection();
  }

  @Override
  public void closeSession(){
    try {
      if(cn != null){
        cn.close();
      }
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }finally{
      cn = null;
    }
  }

  @Override
  public void commitTransaction() {
    try {
      cn.commit();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void rollbackTransaction(){
    try {
      cn.rollback();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }
}

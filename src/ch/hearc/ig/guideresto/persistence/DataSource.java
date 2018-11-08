package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.persistence.oracleDAO.OracleDatasource;
import java.sql.Connection;

public abstract class DataSource {
  private static DataSource INSTANCE;

  public static DataSource getInstance() {
    if (INSTANCE == null) {
      //Ajouter condition avec un H2Datasource pour avoir le choix
      INSTANCE = new OracleDatasource();
    }

    return INSTANCE;
  }
  public abstract Connection getConnection();
  public abstract void openSession();
  public abstract void closeSession();
  public abstract void commitTransaction();
  public abstract void rollbackTransaction();

}
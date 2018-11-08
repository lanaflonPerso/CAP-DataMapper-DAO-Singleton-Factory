package ch.hearc.ig.guideresto.persistence;

import java.sql.Connection;

public abstract class DAO {
  private final DataSource dataSource;

  public DAO(DataSource dataSource){
    this.dataSource = dataSource;
  }

  protected Connection getConnection() {
    return dataSource.getConnection();
  }

}

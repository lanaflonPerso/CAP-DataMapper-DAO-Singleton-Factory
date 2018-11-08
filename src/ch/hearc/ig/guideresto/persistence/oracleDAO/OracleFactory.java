package ch.hearc.ig.guideresto.persistence.oracleDAO;

import ch.hearc.ig.guideresto.persistence.AbstractFactory;
import ch.hearc.ig.guideresto.persistence.BasicEvaluationDAOInterface;
import ch.hearc.ig.guideresto.persistence.CityDAOInterface;
import ch.hearc.ig.guideresto.persistence.DataSource;
import ch.hearc.ig.guideresto.persistence.RestaurantDAOInterface;

public class OracleFactory extends AbstractFactory {

  @Override
  public DataSource getDatasource() {
    return OracleDatasource.getInstance();
  }

  @Override
  public BasicEvaluationDAOInterface getBasicEvaluationDAO() {
    return new OracleBasicEvaluationDAO(getDatasource());
  }

  @Override
  public CityDAOInterface getCityDAO() {
    return new OracleCityDAO(getDatasource());
  }

  @Override
  public RestaurantDAOInterface getRestaurantDAO() {
    return new OracleRestaurantDAO(getDatasource());
  }
}

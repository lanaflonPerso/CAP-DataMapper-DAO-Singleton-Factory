package ch.hearc.ig.guideresto.persistence.javaDB;

import ch.hearc.ig.guideresto.persistence.AbstractFactory;
import ch.hearc.ig.guideresto.persistence.DataSource;
import ch.hearc.ig.guideresto.persistence.RestaurantDAOInterface;
import ch.hearc.ig.guideresto.persistence.BasicEvaluationDAOInterface;
import ch.hearc.ig.guideresto.persistence.CityDAOInterface;

public class H2Factory extends AbstractFactory {

  @Override
  public DataSource getDatasource() {
    return null;
  }

  @Override
  public BasicEvaluationDAOInterface getBasicEvaluationDAO() {
    return null;
  }

  @Override
  public CityDAOInterface getCityDAO() {
    return null;
  }

  @Override
  public RestaurantDAOInterface getRestaurantDAO() {
    return null;
  }
}

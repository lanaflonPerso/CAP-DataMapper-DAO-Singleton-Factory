package ch.hearc.ig.guideresto.service;

import ch.hearc.ig.guideresto.business.City;
import ch.hearc.ig.guideresto.persistence.AbstractFactory;
import ch.hearc.ig.guideresto.persistence.DataSource;
import java.util.List;

public class CityService {

  private DataSource dataSource;
  private final AbstractFactory factory;

  public CityService(AbstractFactory factory) {
    this.factory = factory;
    this.dataSource = factory.getDatasource();
  }

  public void insertCity(City city){
    try{
      dataSource.openSession();
      factory.getCityDAO().insert(city);
      dataSource.commitTransaction();
    }finally {
      dataSource.closeSession();
    }
  }

  public void updateCity(City city){
    try{
      dataSource.openSession();
      factory.getCityDAO().update(city);
      dataSource.commitTransaction();
    } finally {
      dataSource.closeSession();
    }
  }

  public void deleteCity(City city){
    try{
      dataSource.openSession();
      factory.getCityDAO().delete(city);
      dataSource.commitTransaction();
    } finally {
      dataSource.closeSession();
    }
  }

  public List<City> findAllCity(){
    try{
      dataSource.openSession();
      return factory.getCityDAO().findAll();
    }finally {
      dataSource.closeSession();
    }
  }
}


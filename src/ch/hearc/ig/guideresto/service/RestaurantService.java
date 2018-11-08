package ch.hearc.ig.guideresto.service;

import ch.hearc.ig.guideresto.business.Restaurant;
import ch.hearc.ig.guideresto.persistence.AbstractFactory;
import ch.hearc.ig.guideresto.persistence.DataSource;
import java.util.List;

public class RestaurantService {
  private DataSource dataSource;
  private final AbstractFactory factory;

  public RestaurantService(AbstractFactory factory){
    this.factory = factory;
    this.dataSource = factory.getDatasource();

  }

  public void insertRestaurant(Restaurant restaurant){
    try {
      dataSource.openSession();
      factory.getRestaurantDAO().insert(restaurant);
      dataSource.commitTransaction();
    } finally {
      dataSource.closeSession();
    }
  }

  public void updateRestaurant(Restaurant restaurant){
    try{
      dataSource.openSession();
      factory.getRestaurantDAO().update(restaurant);
      dataSource.commitTransaction();
    } finally {
      dataSource.closeSession();
    }
  }

  public void deleteRestaurant(Restaurant restaurant){
    try{
      dataSource.openSession();
      factory.getRestaurantDAO().delete(restaurant);
      dataSource.commitTransaction();
    }finally {
      dataSource.closeSession();
    }
  }

  public List<Restaurant> findAllRestaurant(){
    try{
      dataSource.openSession();
      return  factory.getRestaurantDAO().findAll();
  } finally {
      dataSource.closeSession();
    }
    }
}

package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.business.Restaurant;
import java.util.List;

public interface RestaurantDAOInterface {

  void insert(Restaurant restaurant);

  void update(Restaurant restaurant);

  void delete(Restaurant restaurant);

  List<Restaurant> findAll();
}

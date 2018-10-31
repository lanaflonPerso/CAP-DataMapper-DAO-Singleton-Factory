package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.business.City;
import java.util.List;

public interface CityDAOInterface {

  void insert (City city);

  void update(City city);

  void delete(City city);

  List<City> findAll();
}

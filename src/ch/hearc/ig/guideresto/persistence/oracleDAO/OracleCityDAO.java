package ch.hearc.ig.guideresto.persistence.oracleDAO;

import ch.hearc.ig.guideresto.business.City;
import ch.hearc.ig.guideresto.persistence.CityDAOInterface;
import ch.hearc.ig.guideresto.persistence.DAO;
import ch.hearc.ig.guideresto.persistence.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OracleCityDAO extends DAO implements CityDAOInterface {

  public OracleCityDAO(DataSource dataSource){
    super(dataSource);
  }
  @Override
  public void insert(City city){

    String requeteSQL = "INSERT INTO VILLES (CODE_POSTAL, NOM_VILLE)VALUES(?,?)";

    try(PreparedStatement statement= getConnection().prepareStatement(requeteSQL)){
      statement.setString(1, city.getZipCode());
      statement.setString(2,city.getCityName() );
      statement.execute();
    }catch (SQLException e){
      throw new RuntimeException(e);
    }
  }

  @Override
  public void update(City city){

    String requeteSQL = "UPDATE VILLES SET (CODE_POSTAL = ?, NOM_VILLE = ?) WHERE NUMERO = ?";

    try(PreparedStatement statement = getConnection().prepareStatement(requeteSQL)){
      statement.setString(1,city.getZipCode());
      statement.setString(2,city.getCityName() );
      statement.setInt(3,city.getId());
      statement.execute();
    }catch(SQLException e){
      throw new RuntimeException(e);
    }
  }

  @Override
  public void delete(City city){

    String requeteSQL = "DELETE FROM VILLES WHERE NUMERO =?";

    try(PreparedStatement statement = getConnection().prepareStatement(requeteSQL)){
      statement.setInt(1,city.getId());
      statement.execute();
    } catch (SQLException e){
      throw new RuntimeException(e);
    }
  }
  @Override
  public List<City> findAll(){

    String requeteSQL ="SELECT CODE_POSTAL, NOM_VILLE FROM VILLES";
    List<City> cities = new ArrayList<>();

    try(PreparedStatement statement = getConnection().prepareStatement(requeteSQL);ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        City cityCourrant = new City(resultSet.getString(1), resultSet.getString(2));
        cities.add(cityCourrant);
      }
    }catch(SQLException e){
          throw new RuntimeException(e);
      }
      return cities;
    }
}

package ch.hearc.ig.guideresto.persistence.oracleDAO;

import ch.hearc.ig.guideresto.business.BasicEvaluation;
import ch.hearc.ig.guideresto.business.City;
import ch.hearc.ig.guideresto.business.Localisation;
import ch.hearc.ig.guideresto.business.Restaurant;
import ch.hearc.ig.guideresto.business.RestaurantType;
import ch.hearc.ig.guideresto.persistence.BasicEvaluationDAOInterface;
import ch.hearc.ig.guideresto.persistence.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class OracleBasicEvaluationDAO extends DAO implements BasicEvaluationDAOInterface {

  public OracleBasicEvaluationDAO(DataSource dataSource){
    super(dataSource);
  }

  @Override
  public void insert(BasicEvaluation basicEvaluation){

    String requeteSQL = "INSERT INTO LIKES(appreciation, date_eval, adresse_ip, fk_rest) VALUES(?,sysdate,?,?)";

    try(PreparedStatement statement = getConnection().prepareStatement(requeteSQL)){
      statement.setString(1,basicEvaluation.getLikeRestaurant() ? "T" : "F");
      statement.setString(2, basicEvaluation.getIpAddress());
      statement.setInt(3,basicEvaluation.getRestaurant().getId());
      statement.execute();
    }catch(SQLException e){
      throw new RuntimeException(e);
    }
  }

  @Override
  public void update(BasicEvaluation basicEvaluation){

    String requeteSQL = "UPDATE LIKES SET appreciation = ?, date_eval= sysdate, adress_ip = ?, fk_rest = ? WHERE NUMERO = ?";

    try(PreparedStatement statement = getConnection().prepareStatement(requeteSQL)){
      statement.setString(1, basicEvaluation.getLikeRestaurant() ? "T" : "F");
      statement.setString(2,basicEvaluation.getIpAddress());
      statement.setInt(3, basicEvaluation.getRestaurant().getId());
      statement.setInt(4,basicEvaluation.getId());
      statement.execute();
    }catch(SQLException e){
      throw new RuntimeException(e);
    }
  }

  @Override
  public void delete(BasicEvaluation basicEvaluation){

    String requeteSQL = "DELETE FROM LIKES WHERE NUMERO = ?";

    try(PreparedStatement statement = getConnection().prepareStatement(requeteSQL)){
      statement.setInt(1,basicEvaluation.getId() );
      statement.execute();
    } catch (SQLException e){
      throw new RuntimeException(e);
    }
  }
  @Override
  public Set<BasicEvaluation> findAll(){

    String requeteSQL = "SELECT l.date_eval, l.fk_rest, l.appreciation, l.adresse_id, r.numero AS restaurant_numero, r.nom, r.adresse, r.description, r.siteweb, " +
            "v.numero, v.code_postal, v.nom_ville, tg.numero as numero_type, tg.libelle,tg.description as description_type " +
            "FROM LIKES l " +
            "INNER JOIN RESTAURANTS r ON r.numero = l.fk_rest " +
            "INNER JOIN TYPES_GASTRONOMIQUES TG ON R.FK_TYPE = TG.NUMERO " +
            "INNER JOIN VILLES V ON R.FK_VILL = V.NUMERO " +
            "ORDER BY R.NOM";

    try (PreparedStatement statement = getConnection().prepareStatement(requeteSQL);
    ResultSet resultSet = statement.executeQuery()) {
      Map<Integer, Restaurant> restaurants = new HashMap<>();
      Map<Integer, RestaurantType> types = new HashMap<>();
      Map<Integer, City> cities = new HashMap<>();

      Set<BasicEvaluation> basicEvaluations = new LinkedHashSet<>();
      Integer numRestaurant;
      Integer numType;
      Integer numCity;

      while(resultSet.next()){
        BasicEvaluation basicEvaluation = new BasicEvaluation();

        //Vérification si le restaurant existe déjà :
        numRestaurant = resultSet.getInt("restaurant_numero");
        if(restaurants.containsKey(numRestaurant)){
          basicEvaluation.setRestaurant(restaurants.get(numRestaurant));
        }else{
          Restaurant restaurantTmp = new Restaurant();
          restaurantTmp.setId(numRestaurant);
          restaurantTmp.setName(resultSet.getString("nom"));
          restaurantTmp.setWebsite(resultSet.getString("siteweb"));

          // Vérification si le type est déjà créé
          numType = resultSet.getInt("numero_type");
          if(types.containsKey(numType)){
            restaurantTmp.setType(types.get(numType));
          }else{
            RestaurantType restaurantType = new RestaurantType(numType, resultSet.getString("libelle"), resultSet.getString("description_type"));
            types.put(restaurantType.getId(), restaurantType);
            restaurantTmp.setType(restaurantType);
          }

          // Vérification si la ville est déjà créé
          numCity = resultSet.getInt("VILLE_NUMERO");
          if(cities.containsKey(numCity)){
            restaurantTmp.setAddress(new Localisation(resultSet.getString("adresse"), cities.get(numCity)));
          }else{
            City cityTmp = new City(numCity, resultSet.getString("CODE_POSTAL"), resultSet.getString("NOM_VILLE"));
            cities.put(numCity, cityTmp);
            restaurantTmp.setAddress(new Localisation(resultSet.getString("ADRESSE"), cityTmp));
          }
          basicEvaluation.setRestaurant(restaurantTmp);
        }

        if(resultSet.getString("appreciation").equals("T")){
          basicEvaluation.setLikeRestaurant(true);
        }else{
          basicEvaluation.setLikeRestaurant(false);
        }
        basicEvaluation.setVisitDate(resultSet.getDate("date_eval"));
        basicEvaluation.setIpAddress(resultSet.getString("adresse_id"));

        basicEvaluations.add(basicEvaluation);
      }
      return basicEvaluations;

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}

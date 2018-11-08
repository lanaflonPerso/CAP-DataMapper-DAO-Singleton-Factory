package ch.hearc.ig.guideresto.persistence.oracleDAO;

import ch.hearc.ig.guideresto.business.Restaurant;
import ch.hearc.ig.guideresto.business.City;
import ch.hearc.ig.guideresto.business.Localisation;
import ch.hearc.ig.guideresto.business.RestaurantType;
import ch.hearc.ig.guideresto.persistence.DAO;
import ch.hearc.ig.guideresto.persistence.DataSource;
import ch.hearc.ig.guideresto.persistence.RestaurantDAOInterface;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OracleRestaurantDAO extends DAO implements RestaurantDAOInterface {

    //On va ici faire en sorte que quand on construit l'objet on doive choisir une datasource qui va être propre à tous les DAO
    public OracleRestaurantDAO(DataSource dataSource){
      super(dataSource);
    }

    @Override
    public void insert(Restaurant restaurant) {
        String requeteSQL = "INSERT INTO RESTAURANTS (NOM, ADRESSE, DESCRIPTION, SITE_WEB, FK_TYPE, FK_VILL) VALUES (?,?,?,?,?,?)";

        //On peut ici directement appeler la connexion selon la dataSource préselectionné
        try (PreparedStatement statement = getConnection().prepareStatement(requeteSQL);
        ){  statement.setString(1, restaurant.getName());
            statement.setString(2, restaurant.getAddress().getStreet());
            statement.setString(3, restaurant.getDescription());
            statement.setString(4, restaurant.getWebsite());
            statement.setInt(5, restaurant.getType().getId());
            statement.setInt(6, restaurant.getAddress().getCity().getId());
            statement.execute();
        } catch(SQLException e) {
            throw new RuntimeException(e);
       }
    }
    @Override
    public void update(Restaurant restaurant) {
        String requeteSQL = "UPDATE RESTAURANTS SET NOM = ?, ADRESSE = ?, DESCRIPTION = ?, SITE_WEB = ?, FK_TYPE = ?, FK_VILLE = ?) WHERE NUMERO = ?";

        try(PreparedStatement statement = getConnection().prepareStatement(requeteSQL)){
            statement.setString(1, restaurant.getName());
            statement.setString(2, restaurant.getAddress().getStreet());
            statement.setString(3, restaurant.getDescription());
            statement.setString(4, restaurant.getWebsite());
            statement.setInt(5, restaurant.getType().getId());
            statement.setInt(6, restaurant.getAddress().getCity().getId());
            statement.setInt(7, restaurant.getId());
            statement.execute();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void delete(Restaurant restaurant) {

            String requeteSQL = "DELETE FROM RESTAURANTS WHERE NUMERO = ?";

            try(PreparedStatement statement = getConnection().prepareStatement(requeteSQL);) {
            statement.setInt(1, restaurant.getId());
            statement.execute();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<Restaurant> findAll() {

            String requeteSQL = "SELECT R.NUMERO, R.NOM, R.ADRESSE, R.DESCRIPTION, R.SITE_WEB, V.NUMERO AS VILLE_NUMERO, V.CODE_POSTAL,"+
                "V.NOM_VILLE, T.NUMERO, T.LIBELLE, T.DESCRIPTION AS TYPE_DESCRIPTION "+
                "FROM RESTAURANTS R INNER JOIN VILLES V ON R.FK_VILL = V.NUMERO "+
                "INNER JOIN TYPES_GASTRONOMIQUES T ON R.FK_TYPE = T.NUMERO "+
                "ORDER BY R.NOM";

            try(PreparedStatement statement = getConnection().prepareStatement(requeteSQL);
                ResultSet resultSet = statement.executeQuery()){

            List<Restaurant> restaurants = new ArrayList<>();
            Integer numType;
            Integer numCity;
            Map<Integer, City> cities = new HashMap<>();
            Map<Integer, RestaurantType> types = new HashMap<>(); 
            
            while(resultSet.next()){
               Restaurant restaurantTmp = new Restaurant();
               
               //On vérifie si le type existe déjà si oui on le cherche dans l'hashmap et on l'ajoute à notre restau si non on le crée!
               numType = resultSet.getInt("NUMERO");
                if (types.containsKey(numType)) {
                    restaurantTmp.setType(types.get(numType));
                } else {
                    RestaurantType restaurantType = new RestaurantType(numType, resultSet.getString("LIBELLE"), resultSet.getString("TYPE_DESCRIPTION"));
                    types.put(restaurantType.getId(), restaurantType);
                    restaurantTmp.setType(restaurantType);
                }
                
                //Pareil que avant mais pour City
                numCity = resultSet.getInt("VILLE_NUMERO");
                if(cities.containsKey(numCity)){
                    restaurantTmp.setAddress(new Localisation(resultSet.getString("ADRESSE"),cities.get(numCity)));
                } else {
                    City cityTmp = new City(numCity,resultSet.getString("CODE_POSTAL"),resultSet.getString("NOM_VILLE"));
                    cities.put(numCity, cityTmp);
                    restaurantTmp.setAddress(new Localisation(resultSet.getString("ADRESSE"),cityTmp));
                }
                
                //Remplissage des attributs de restaurants restant (qui ne sont pas des objets)
                restaurantTmp.setId(resultSet.getInt("NUMERO"));
                restaurantTmp.setName(resultSet.getString("NOM"));
                restaurantTmp.setDescription(resultSet.getString("DESCRIPTION"));
                restaurantTmp.setWebsite(resultSet.getString("SITE_WEB"));
                
                restaurants.add(restaurantTmp);

            }
                statement.close();
                resultSet.close();
                return restaurants;
            } catch(SQLException e){
                throw new RuntimeException(e);
        }
    }
}
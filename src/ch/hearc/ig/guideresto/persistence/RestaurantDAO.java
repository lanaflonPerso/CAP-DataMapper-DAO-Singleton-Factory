package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.business.Restaurant;
import ch.hearc.ig.guideresto.business.City;
import ch.hearc.ig.guideresto.business.Localisation;
import ch.hearc.ig.guideresto.business.RestaurantType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantDAO {
    public void insert(Restaurant restaurant) {
        try {
            // Instanciation de datasource dans le main et reprise de la connexion grâce à la méthode static
            
            Connection connection = Datasource.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO RESTAURANTS (NOM, ADRESSE, DESCRIPTION, SITE_WEB, FK_TYPE, FK_VILL) VALUES (?,?,?,?,?,?)");
            statement.setString(1, restaurant.getName());
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
    public void update(Restaurant restaurant) {
        try{
            Connection connection = Datasource.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE RESTAURANTS SET NOM = ?, ADRESSE = ?, DESCRIPTION = ?, SITE_WEB = ?, FK_TYPE = ?, FK_VILLE = ?) WHERE NUMERO = ?");
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
    public void delete(Restaurant restaurant) {
            try {
            Connection connection = Datasource.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM RESTAURANTS WHERE NUMERO = ?");
            statement.setInt(1, restaurant.getId());
            statement.execute();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Restaurant> findAll() {

            
            try(Connection connection = Datasource.getConnection();){
            PreparedStatement statement = connection.prepareStatement("SELECT R.NUMERO, R.NOM, R.ADRESSE, R.DESCRIPTION, R.SITE_WEB, V.NUMERO AS VILLE_NUMERO, V.CODE_POSTAL,"+
                    "V.NOM_VILLE, T.NUMERO, T.LIBELLE, T.DESCRIPTION AS TYPE_DESCRIPTION "+
                    "FROM RESTAURANTS R INNER JOIN VILLES V ON R.FK_VILL = V.NUMERO "+
                    "INNER JOIN TYPES_GASTRONOMIQUES T ON R.FK_TYPE = T.NUMERO "+
                    "ORDER BY R.NOM");
            
            ResultSet resultSet = statement.executeQuery();
            
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
                return restaurants;
            } catch(SQLException e){
                throw new RuntimeException(e);
        }
          
    }
}
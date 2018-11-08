/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.guideresto.presentation;

import ch.hearc.ig.guideresto.business.City;
import ch.hearc.ig.guideresto.business.Localisation;
import ch.hearc.ig.guideresto.business.Restaurant;
import ch.hearc.ig.guideresto.business.RestaurantType;
import ch.hearc.ig.guideresto.persistence.AbstractFactory;
import ch.hearc.ig.guideresto.persistence.ChoixTypeDatasource;
import ch.hearc.ig.guideresto.persistence.oracleDAO.OracleCityDAO;
import ch.hearc.ig.guideresto.persistence.CityDAOInterface;
import ch.hearc.ig.guideresto.persistence.oracleDAO.OracleDatasource;
import ch.hearc.ig.guideresto.persistence.oracleDAO.OracleRestaurantDAO;
import ch.hearc.ig.guideresto.persistence.RestaurantDAOInterface;
import ch.hearc.ig.guideresto.service.CityService;
import ch.hearc.ig.guideresto.service.RestaurantService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sun.security.smartcardio.SunPCSC.Factory;

/**
 *
 * @author maxime.parret
 */
public class AppForDAO {
    public static void main(String[] args) throws SQLException{
        
        //Choix de la Datasource
        AbstractFactory factory = AbstractFactory.getDatabaseFactory(ChoixTypeDatasource.ORACLE);

        //Manipulation des données avec données tests
        City cityTest = new City(1,"ziptest","CityNameTest");
        Localisation localisationtest1 = new Localisation("Streettest",cityTest);
        RestaurantType restType1 = new RestaurantType(1,"labeltest","DescriptionTest");
        Restaurant restTest1 = new Restaurant(6,"testDAO1","DescriptionTest","WebsiteTest",localisationtest1, restType1);

        //On utilise les services par rapport au choix de la datasource d'avant afin de gérer les connexions pour chaque "manoeuve sur la BDD
        CityService cityService = new CityService(factory);
        cityService.insertCity(cityTest);

        RestaurantService restaurantService = new RestaurantService(factory);
        restaurantService.insertRestaurant(restTest1);

        //afficher la liste des restaurants
        List <Restaurant> restaurants = restaurantService.findAllRestaurant();
            String result;
        for(Restaurant currentRest : restaurants){
          result = "";
          result = "\"" + result + currentRest.getName() + "\" - " + currentRest.getAddress().getStreet() + " - ";
          result = result + currentRest.getAddress().getCity().getZipCode() + " " + currentRest.getAddress().getCity().getCityName();
          System.out.println(result);
        }

    }
}

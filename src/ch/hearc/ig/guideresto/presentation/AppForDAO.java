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
import ch.hearc.ig.guideresto.persistence.Datasource;
import ch.hearc.ig.guideresto.persistence.RestaurantDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author maxime.parret
 */
public class AppForDAO {
    public static void main(String[] args) throws SQLException{
        
        //Instanciation de la datasource et setter de la connexion static qui va être reprise dans les DAO
        Datasource datasource = new Datasource();
        datasource.openSession();
        
        //Manipulation des données avec données tests
        City cityTest = new City(1,"ziptest","CityNameTest");
        Localisation localisationtest1 = new Localisation("Streettest",cityTest);
        RestaurantType restType1 = new RestaurantType(1,"labeltest","DescriptionTest");
        Restaurant restTest1 = new Restaurant(6,"testDAO2","DescriptionTest","WebsiteTest",localisationtest1, restType1);
        
        insertRestaurant(restTest1);
        displayListRestaurants();
        
        //Commit + fermeture de la datasource
        datasource.commitTransaction();
        datasource.closeSession();
  
             
    }
    
    private static void insertRestaurant(Restaurant restaurant) throws SQLException{
        RestaurantDAO restaurantMapper = new RestaurantDAO();
        restaurantMapper.insert(restaurant);
    }
    
    private static void displayListRestaurants() throws SQLException {
        //afficher la liste des restaurants
        RestaurantDAO restaurantMapper = new RestaurantDAO();
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants = restaurantMapper.findAll();
        String result;
        
        for(Restaurant currentRest : restaurants){
            result = "";
            result = "\"" + result + currentRest.getName() + "\" - " + currentRest.getAddress().getStreet() + " - ";
            result = result + currentRest.getAddress().getCity().getZipCode() + " " + currentRest.getAddress().getCity().getCityName();
            System.out.println(result);
        }
    }
}

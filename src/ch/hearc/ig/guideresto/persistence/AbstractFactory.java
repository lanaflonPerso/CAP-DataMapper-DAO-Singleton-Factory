package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.persistence.javaDB.H2Factory;
import ch.hearc.ig.guideresto.persistence.oracleDAO.OracleFactory;

public abstract class AbstractFactory {

        public static AbstractFactory getDatabaseFactory(ChoixTypeDatasource typeDatasource){

          switch (typeDatasource){
            case ORACLE: return new OracleFactory();
            case JavaDB: return new H2Factory();
        }
          throw new IllegalArgumentException();
        }

        public abstract DataSource getDatasource();
        public abstract BasicEvaluationDAOInterface getBasicEvaluationDAO();
        public abstract CityDAOInterface getCityDAO();
        public abstract RestaurantDAOInterface getRestaurantDAO();
        /* Reste à implémenter..
        public abstract CompleteEvaluationDAO getCompleteEvaluationDAO();
        public abstract EvaluationCriteriaDAO getEvaluationCriteriaDAO();
        public abstract GradeDAO getGradeDAO();
        public abstract RestaurantTypeDAO getRestaurantTypeDAO();
        */

}
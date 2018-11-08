package ch.hearc.ig.guideresto.service;

import ch.hearc.ig.guideresto.business.BasicEvaluation;
import ch.hearc.ig.guideresto.persistence.AbstractFactory;
import ch.hearc.ig.guideresto.persistence.DataSource;

public class BasicEvaluationService {

    private DataSource dataSource;
    private final AbstractFactory factory;

  public BasicEvaluationService(AbstractFactory factory){
    this.factory = factory;
    this.dataSource = factory.getDatasource();
  }

  public void insertBasicEvaluation(BasicEvaluation basicEvaluation){
    try{
      dataSource.openSession();
      factory.getBasicEvaluationDAO().insert(basicEvaluation);
      dataSource.commitTransaction();
    }finally{
      dataSource.closeSession();
    }
  }

  public void updateBasicEvaluation(BasicEvaluation basicEvaluation){
    try{
      dataSource.openSession();
      factory.getBasicEvaluationDAO().update(basicEvaluation);
      dataSource.commitTransaction();
    }finally{
      dataSource.closeSession();
    }
  }

  public void deleteBasicEvaluation(BasicEvaluation basicEvaluation){
    try{
      dataSource.openSession();
      factory.getBasicEvaluationDAO().delete(basicEvaluation);
      dataSource.commitTransaction();
    } finally {
      dataSource.closeSession();
    }
  }
}

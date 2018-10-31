package ch.hearc.ig.guideresto.persistence;

import ch.hearc.ig.guideresto.business.BasicEvaluation;
import java.util.Set;

public interface BasicEvaluationDAOInterface {

  void insert(BasicEvaluation basicEvaluation);

  void update(BasicEvaluation basicEvaluation);

  void delete(BasicEvaluation basicEvaluation);

  Set<BasicEvaluation> findAll();
}

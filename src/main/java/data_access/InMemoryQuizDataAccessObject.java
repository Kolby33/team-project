package data_access;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import entity.Attempt;
import entity.Quiz;
import use_case.play_quiz.PlayQuizDataAccessInterface;

/**
 * Keeps quizzes and attempts in memory. Used by the Play Quiz tests and to run
 * the app until the JSON file storage is ready. The file-based DAO only needs to
 * implement PlayQuizDataAccessInterface to replace this one.
 */
public class InMemoryQuizDataAccessObject implements PlayQuizDataAccessInterface {
  private final Map<String, Quiz> quizzes = new HashMap<>();
  private final List<Attempt> attempts = new ArrayList<>();

  /**
   * Adds or replaces a saved quiz.
   * @param quiz the quiz to save
   */
  public void saveQuiz(Quiz quiz) {
    quizzes.put(quiz.getId(), quiz);
  }

  @Override
  public Optional<Quiz> findQuizById(String quizId) {
    return Optional.ofNullable(quizzes.get(quizId));
  }

  @Override
  public void saveAttempt(Attempt attempt) {
    attempts.add(attempt);
  }

  public List<Attempt> getSavedAttempts() {
    return Collections.unmodifiableList(attempts);
  }
}
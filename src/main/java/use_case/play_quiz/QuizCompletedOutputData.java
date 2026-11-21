package use_case.play_quiz;

/**
 * Output data for when the last question has been answered and the attempt is saved.
 */
public class QuizCompletedOutputData {
  private final String attemptId;
  private final String quizName;
  private final int score;
  private final int totalQuestions;

  public QuizCompletedOutputData(String attemptId, String quizName, int score, int totalQuestions) {
    this.attemptId = attemptId;
    this.quizName = quizName;
    this.score = score;
    this.totalQuestions = totalQuestions;
  }

  /**
   * Returns the id of the saved attempt, so the View Results use case can load it.
   * @return the attempt id
   */
  public String getAttemptId() {
    return attemptId;
  }

  public String getQuizName() {
    return quizName;
  }

  public int getScore() {
    return score;
  }

  public int getTotalQuestions() {
    return totalQuestions;
  }
}
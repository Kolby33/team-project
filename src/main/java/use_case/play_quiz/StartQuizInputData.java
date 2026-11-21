package use_case.play_quiz;

/**
 * Input data for starting a quiz.
 */
public class StartQuizInputData {
  private final String quizId;

  public StartQuizInputData(String quizId) {
    this.quizId = quizId;
  }

  public String getQuizId() {
    return quizId;
  }
}
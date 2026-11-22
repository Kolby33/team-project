package use_case.play_quiz;

/**
 * Output boundary for the Play Quiz use case.
 */
public interface PlayQuizOutputBoundary {

  /**
   * Prepares the view showing a question (the first one, or the next one).
   * @param outputData the question to show
   */
  void prepareQuestionView(QuestionOutputData outputData);

  /**
   * Prepares the view shown after the last question, once the attempt is saved.
   * @param outputData the saved attempt's id and score
   */
  void prepareQuizCompletedView(QuizCompletedOutputData outputData);

  /**
   * Prepares the failure view.
   * @param errorMessage what went wrong
   */
  void prepareFailView(String errorMessage);
}
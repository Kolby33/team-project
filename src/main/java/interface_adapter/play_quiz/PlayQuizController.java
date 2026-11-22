package interface_adapter.play_quiz;

import use_case.play_quiz.AnswerQuestionInputData;
import use_case.play_quiz.PlayQuizInputBoundary;
import use_case.play_quiz.StartQuizInputData;

/**
 * Controller for the Play Quiz use case.
 */
public class PlayQuizController {
  private final PlayQuizInputBoundary playQuizInteractor;

  public PlayQuizController(PlayQuizInputBoundary playQuizInteractor) {
    this.playQuizInteractor = playQuizInteractor;
  }

  /**
   * Starts the quiz with the given id.
   * @param quizId the id of the saved quiz
   */
  public void startQuiz(String quizId) {
    playQuizInteractor.startQuiz(new StartQuizInputData(quizId));
  }

  /**
   * Submits the player's answer to the current question.
   * @param choiceIndex the 0-based index of the chosen answer, or -1 to skip
   */
  public void answerQuestion(int choiceIndex) {
    playQuizInteractor.answerQuestion(new AnswerQuestionInputData(choiceIndex));
  }
}
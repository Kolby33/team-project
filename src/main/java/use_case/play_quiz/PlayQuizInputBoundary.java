package use_case.play_quiz;

/**
 * Input boundary for the Play Quiz use case.
 */
public interface PlayQuizInputBoundary {

  /**
   * Starts playing the requested quiz from its first question.
   * @param inputData the id of the quiz to play
   */
  void startQuiz(StartQuizInputData inputData);

  /**
   * Answers (or skips) the current question of the quiz being played.
   * @param inputData the chosen answer
   */
  void answerQuestion(AnswerQuestionInputData inputData);
}
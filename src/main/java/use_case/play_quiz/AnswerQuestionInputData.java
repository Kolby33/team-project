package use_case.play_quiz;

/**
 * Input data for answering the current question.
 */
public class AnswerQuestionInputData {
  private final int choiceIndex;

  /**
   * Creates the input data.
   * @param choiceIndex the 0-based index of the chosen answer, or -1 to skip the question
   */
  public AnswerQuestionInputData(int choiceIndex) {
    this.choiceIndex = choiceIndex;
  }

  public int getChoiceIndex() {
    return choiceIndex;
  }
}
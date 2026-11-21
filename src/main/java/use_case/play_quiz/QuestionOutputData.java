package use_case.play_quiz;

import java.util.List;

/**
 * Output data describing the question to show next.
 */
public class QuestionOutputData {
  private final String quizName;
  private final String questionText;
  private final List<String> choices;
  private final int questionNumber;
  private final int totalQuestions;

  public QuestionOutputData(String quizName, String questionText, List<String> choices,
                            int questionNumber, int totalQuestions) {
    this.quizName = quizName;
    this.questionText = questionText;
    this.choices = choices;
    this.questionNumber = questionNumber;
    this.totalQuestions = totalQuestions;
  }

  public String getQuizName() {
    return quizName;
  }

  public String getQuestionText() {
    return questionText;
  }

  public List<String> getChoices() {
    return choices;
  }

  /**
   * Returns the 1-based number of the question being shown.
   * @return the question number
   */
  public int getQuestionNumber() {
    return questionNumber;
  }

  public int getTotalQuestions() {
    return totalQuestions;
  }
}
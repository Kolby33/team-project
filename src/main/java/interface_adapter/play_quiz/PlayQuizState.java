package interface_adapter.play_quiz;

import java.util.ArrayList;
import java.util.List;

/**
 * Everything the Play Quiz view needs to draw itself. The presenter fills this
 * in; the view only reads it.
 */
public class PlayQuizState {
  private String quizName = "";
  private String questionText = "";
  private List<String> choices = new ArrayList<>();
  private String progressText = "";
  private String actionButtonText = "Next";
  private String errorMessage = "";
  private boolean finished;
  private String resultText = "";
  private String attemptId = "";

  public String getQuizName() {
    return quizName;
  }

  public void setQuizName(String quizName) {
    this.quizName = quizName;
  }

  public String getQuestionText() {
    return questionText;
  }

  public void setQuestionText(String questionText) {
    this.questionText = questionText;
  }

  public List<String> getChoices() {
    return choices;
  }

  public void setChoices(List<String> choices) {
    this.choices = choices;
  }

  public String getProgressText() {
    return progressText;
  }

  public void setProgressText(String progressText) {
    this.progressText = progressText;
  }

  public String getActionButtonText() {
    return actionButtonText;
  }

  public void setActionButtonText(String actionButtonText) {
    this.actionButtonText = actionButtonText;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public boolean isFinished() {
    return finished;
  }

  public void setFinished(boolean finished) {
    this.finished = finished;
  }

  public String getResultText() {
    return resultText;
  }

  public void setResultText(String resultText) {
    this.resultText = resultText;
  }

  /**
   * Returns the id of the attempt just saved (empty while playing), for the View Results screen.
   * @return the attempt id
   */
  public String getAttemptId() {
    return attemptId;
  }

  public void setAttemptId(String attemptId) {
    this.attemptId = attemptId;
  }
}
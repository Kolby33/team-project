package use_case.view_results;

/**
 * Input data for the View Results use case.
 */
public class ViewResultsInputData {
  private final String attemptId;

  public ViewResultsInputData(String attemptId) {
    this.attemptId = attemptId;
  }

  public String getAttemptId() {
    return attemptId;
  }
}
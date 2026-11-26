package use_case.view_results;

/**
 * Input boundary for the View Results use case.
 */
public interface ViewResultsInputBoundary {

  /**
   * Shows the results of a saved attempt.
   * @param inputData which attempt to show
   */
  void execute(ViewResultsInputData inputData);
}
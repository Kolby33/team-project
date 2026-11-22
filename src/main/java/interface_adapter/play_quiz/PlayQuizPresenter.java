package interface_adapter.play_quiz;

import interface_adapter.ViewManagerModel;
import use_case.play_quiz.PlayQuizOutputBoundary;
import use_case.play_quiz.QuestionOutputData;
import use_case.play_quiz.QuizCompletedOutputData;

/**
 * Presenter for the Play Quiz use case: turns output data into text for the view.
 */
public class PlayQuizPresenter implements PlayQuizOutputBoundary {
  private final PlayQuizViewModel playQuizViewModel;
  private final ViewManagerModel viewManagerModel;

  public PlayQuizPresenter(PlayQuizViewModel playQuizViewModel, ViewManagerModel viewManagerModel) {
    this.playQuizViewModel = playQuizViewModel;
    this.viewManagerModel = viewManagerModel;
  }

  @Override
  public void prepareQuestionView(QuestionOutputData outputData) {
    final int number = outputData.getQuestionNumber();
    final int total = outputData.getTotalQuestions();

    final PlayQuizState state = playQuizViewModel.getState();
    state.setQuizName(outputData.getQuizName());
    state.setQuestionText(outputData.getQuestionText());
    state.setChoices(outputData.getChoices());
    state.setProgressText("Question " + number + " of " + total);
    if (number == total) {
      state.setActionButtonText("Submit");
    }
    else {
      state.setActionButtonText("Next");
    }
    state.setErrorMessage("");
    state.setFinished(false);
    state.setResultText("");
    state.setAttemptId("");
    playQuizViewModel.firePropertyChanged();

    viewManagerModel.setState(playQuizViewModel.getViewName());
    viewManagerModel.firePropertyChanged();
  }

  @Override
  public void prepareQuizCompletedView(QuizCompletedOutputData outputData) {
    final PlayQuizState state = playQuizViewModel.getState();
    state.setFinished(true);
    state.setResultText("You scored " + outputData.getScore()
        + " out of " + outputData.getTotalQuestions() + ".");
    state.setAttemptId(outputData.getAttemptId());
    state.setErrorMessage("");
    playQuizViewModel.firePropertyChanged();
  }

  @Override
  public void prepareFailView(String errorMessage) {
    final PlayQuizState state = playQuizViewModel.getState();
    state.setErrorMessage(errorMessage);
    playQuizViewModel.firePropertyChanged();
  }
}
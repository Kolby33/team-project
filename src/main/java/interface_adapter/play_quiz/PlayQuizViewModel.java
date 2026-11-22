package interface_adapter.play_quiz;

import interface_adapter.ViewModel;

/**
 * The view model for the Play Quiz view.
 */
public class PlayQuizViewModel extends ViewModel<PlayQuizState> {
  public static final String VIEW_NAME = "play quiz";

  public PlayQuizViewModel() {
    super(VIEW_NAME);
    setState(new PlayQuizState());
  }
}
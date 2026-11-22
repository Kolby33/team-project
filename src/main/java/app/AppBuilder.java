package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import interface_adapter.ViewManagerModel;
import interface_adapter.play_quiz.PlayQuizController;
import interface_adapter.play_quiz.PlayQuizPresenter;
import interface_adapter.play_quiz.PlayQuizViewModel;
import use_case.play_quiz.PlayQuizDataAccessInterface;
import use_case.play_quiz.PlayQuizInputBoundary;
import use_case.play_quiz.PlayQuizInteractor;
import use_case.play_quiz.PlayQuizOutputBoundary;
import view.PlayQuizView;
import view.ViewManager;

/**
 * Builds the app: creates every object and connects them. This is the only
 * class that knows about all the layers. Each teammate adds their own
 * add...View() and add...UseCase() methods here.
 */
public class AppBuilder {
  private final JPanel cardPanel = new JPanel();
  private final CardLayout cardLayout = new CardLayout();
  private final ViewManagerModel viewManagerModel = new ViewManagerModel();
  private final PlayQuizDataAccessInterface playQuizDataAccessObject;

  private PlayQuizViewModel playQuizViewModel;
  private PlayQuizView playQuizView;

  public AppBuilder(PlayQuizDataAccessInterface playQuizDataAccessObject) {
    this.playQuizDataAccessObject = playQuizDataAccessObject;
    cardPanel.setLayout(cardLayout);
    new ViewManager(cardPanel, cardLayout, viewManagerModel);
  }

  /**
   * Adds the Play Quiz view.
   * @return this builder
   */
  public AppBuilder addPlayQuizView() {
    playQuizViewModel = new PlayQuizViewModel();
    playQuizView = new PlayQuizView(playQuizViewModel);
    cardPanel.add(playQuizView, playQuizView.getViewName());
    return this;
  }

  /**
   * Adds the Play Quiz use case. Call addPlayQuizView() first.
   * @return this builder
   */
  public AppBuilder addPlayQuizUseCase() {
    final PlayQuizOutputBoundary presenter = new PlayQuizPresenter(playQuizViewModel, viewManagerModel);
    final PlayQuizInputBoundary interactor = new PlayQuizInteractor(playQuizDataAccessObject, presenter);
    playQuizView.setPlayQuizController(new PlayQuizController(interactor));
    return this;
  }

  /**
   * Creates the window and shows the first view.
   * @return the application window
   */
  public JFrame build() {
    final JFrame application = new JFrame("Trivia Studio");
    application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    application.add(cardPanel);

    viewManagerModel.setState(playQuizView.getViewName());
    viewManagerModel.firePropertyChanged();
    return application;
  }
}
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import interface_adapter.play_quiz.PlayQuizController;
import interface_adapter.play_quiz.PlayQuizState;
import interface_adapter.play_quiz.PlayQuizViewModel;

/**
 * The screen where a player answers a quiz one question at a time.
 * It only displays the PlayQuizState and forwards button clicks to the controller.
 */
public class PlayQuizView extends JPanel implements PropertyChangeListener {
  private static final int NOTHING_SELECTED = -1;

  private final String viewName = PlayQuizViewModel.VIEW_NAME;
  private PlayQuizController playQuizController;

  // Temporary way to pick a quiz until the Browse screen (use case 4) calls
  // PlayQuizController.startQuiz itself. Delete this row once that is wired up.
  private final JTextField quizIdField = new JTextField(12);
  private final JButton startButton = new JButton("Start");

  private final JLabel nameLabel = new JLabel();
  private final JLabel progressLabel = new JLabel();
  private final JLabel questionLabel = new JLabel();
  private final JPanel choicesPanel = new JPanel();
  private final JLabel hintLabel = new JLabel("Leave every choice blank to skip this question.");
  private final JLabel errorLabel = new JLabel();
  private final JLabel resultLabel = new JLabel();
  private final JButton actionButton = new JButton("Next");

  private final List<JRadioButton> choiceButtons = new ArrayList<>();

  public PlayQuizView(PlayQuizViewModel playQuizViewModel) {
    playQuizViewModel.addPropertyChangeListener(this);

    final JPanel startRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
    startRow.add(new JLabel("Quiz ID:"));
    startRow.add(quizIdField);
    startRow.add(startButton);

    choicesPanel.setLayout(new BoxLayout(choicesPanel, BoxLayout.Y_AXIS));
    hintLabel.setForeground(Color.GRAY);
    errorLabel.setForeground(Color.RED);

    final JPanel center = new JPanel();
    center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
    center.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    center.add(nameLabel);
    center.add(progressLabel);
    center.add(questionLabel);
    center.add(choicesPanel);
    center.add(hintLabel);
    center.add(errorLabel);
    center.add(resultLabel);

    final JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    bottom.add(actionButton);

    setLayout(new BorderLayout());
    add(startRow, BorderLayout.NORTH);
    add(center, BorderLayout.CENTER);
    add(bottom, BorderLayout.SOUTH);

    startButton.addActionListener(evt -> playQuizController.startQuiz(quizIdField.getText().trim()));
    actionButton.addActionListener(evt -> playQuizController.answerQuestion(getSelectedChoiceIndex()));

    showState(playQuizViewModel.getState());
  }

  public String getViewName() {
    return viewName;
  }

  public void setPlayQuizController(PlayQuizController playQuizController) {
    this.playQuizController = playQuizController;
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    showState((PlayQuizState) evt.getNewValue());
  }

  private void showState(PlayQuizState state) {
    final boolean playing = !state.getQuestionText().isEmpty() && !state.isFinished();

    nameLabel.setText(state.getQuizName());
    progressLabel.setText(playing ? state.getProgressText() : "");
    questionLabel.setText(playing ? "<html><b>" + state.getQuestionText() + "</b></html>" : "");
    hintLabel.setVisible(playing);
    errorLabel.setText(state.getErrorMessage());
    resultLabel.setText(state.isFinished() ? state.getResultText() : "");
    actionButton.setText(state.getActionButtonText());
    actionButton.setVisible(playing);

    // An error means the same question is still on screen, so keep the player's selection.
    // Otherwise a new question (or none) is being shown, so rebuild the choices.
    if (state.getErrorMessage().isEmpty()) {
      if (playing) {
        showChoices(state.getChoices());
      }
      else {
        showChoices(new ArrayList<>());
      }
    }
    revalidate();
    repaint();
  }

  private void showChoices(List<String> choices) {
    choicesPanel.removeAll();
    choiceButtons.clear();
    final ButtonGroup group = new ButtonGroup();
    for (String choice : choices) {
      final JRadioButton button = new JRadioButton(choice);
      group.add(button);
      choiceButtons.add(button);
      choicesPanel.add(button);
    }
  }

  private int getSelectedChoiceIndex() {
    for (int i = 0; i < choiceButtons.size(); i++) {
      if (choiceButtons.get(i).isSelected()) {
        return i;
      }
    }
    return NOTHING_SELECTED;
  }
}
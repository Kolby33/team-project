package use_case.play_quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entity.Question;
import entity.Quiz;

/**
 * A quiz that is currently being played: which question the player is on and
 * the answers given so far. Once every question is answered, the interactor
 * turns this into an {@link entity.Attempt}.
 */
public class QuizSession {
  /** The answer index recorded when the player skips a question. */
  public static final int SKIPPED = -1;

  private final Quiz quiz;
  private final List<Integer> chosenIndexes = new ArrayList<>();

  /**
   * Starts a session for the given quiz.
   * @param quiz the quiz to play (must have at least one question)
   */
  public QuizSession(Quiz quiz) {
    this.quiz = quiz;
  }

  public Quiz getQuiz() {
    return quiz;
  }

  /**
   * Returns the 0-based index of the question the player is on.
   * @return the current question index
   */
  public int getCurrentQuestionIndex() {
    return chosenIndexes.size();
  }

  /**
   * Returns whether every question has been answered or skipped.
   * @return true if the session is finished
   */
  public boolean isFinished() {
    return chosenIndexes.size() == quiz.getQuestions().size();
  }

  /**
   * Returns the question the player is on.
   * @return the current question
   * @throws IllegalStateException if the session is finished
   */
  public Question getCurrentQuestion() {
    if (isFinished()) {
      throw new IllegalStateException("The quiz is already finished.");
    }
    return quiz.getQuestions().get(chosenIndexes.size());
  }

  /**
   * Returns whether the index is an allowed answer to the current question:
   * either {@link #SKIPPED} or the index of one of its choices.
   * @param choiceIndex the index to check
   * @return true if it can be recorded
   */
  public boolean isValidAnswer(int choiceIndex) {
    final int numberOfChoices = getCurrentQuestion().getChoices().size();
    return choiceIndex == SKIPPED || (choiceIndex >= 0 && choiceIndex < numberOfChoices);
  }

  /**
   * Records the answer to the current question and moves to the next one.
   * @param choiceIndex the chosen index, or {@link #SKIPPED}
   * @throws IllegalArgumentException if the answer is not valid
   */
  public void recordAnswer(int choiceIndex) {
    if (!isValidAnswer(choiceIndex)) {
      throw new IllegalArgumentException("Invalid answer: " + choiceIndex);
    }
    chosenIndexes.add(choiceIndex);
  }

  /**
   * Returns the answers so far, in question order ({@link #SKIPPED} for skipped questions).
   * @return an unmodifiable list of chosen indexes
   */
  public List<Integer> getChosenIndexes() {
    return Collections.unmodifiableList(chosenIndexes);
  }

  /**
   * Returns how many of the answers so far are correct.
   * @return the score
   */
  public int getScore() {
    int score = 0;
    for (int i = 0; i < chosenIndexes.size(); i++) {
      if (quiz.getQuestions().get(i).getCorrectIndex() == chosenIndexes.get(i)) {
        score++;
      }
    }
    return score;
  }
}
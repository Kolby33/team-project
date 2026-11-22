package use_case.play_quiz;

import java.util.Optional;
import java.util.UUID;

import entity.Attempt;
import entity.Question;
import entity.Quiz;

/**
 * The Play Quiz interactor: starts a quiz, records each answer, and saves an
 * Attempt when the last question has been answered.
 */
public class PlayQuizInteractor implements PlayQuizInputBoundary {
  private final PlayQuizDataAccessInterface quizDataAccessObject;
  private final PlayQuizOutputBoundary playQuizPresenter;

  /** The quiz being played, or null when no quiz is in progress. */
  private QuizSession session;

  public PlayQuizInteractor(PlayQuizDataAccessInterface quizDataAccessObject,
                            PlayQuizOutputBoundary playQuizPresenter) {
    this.quizDataAccessObject = quizDataAccessObject;
    this.playQuizPresenter = playQuizPresenter;
  }

  @Override
  public void startQuiz(StartQuizInputData inputData) {
    final String quizId = inputData.getQuizId();
    final Optional<Quiz> found = quizDataAccessObject.findQuizById(quizId);
    if (found.isEmpty()) {
      playQuizPresenter.prepareFailView("Quiz not found: " + quizId);
      return;
    }

    final Quiz quiz = found.get();
    if (quiz.getQuestions().isEmpty()) {
      playQuizPresenter.prepareFailView("This quiz has no questions.");
      return;
    }

    session = new QuizSession(quiz);
    playQuizPresenter.prepareQuestionView(currentQuestionOutput());
  }

  @Override
  public void answerQuestion(AnswerQuestionInputData inputData) {
    if (session == null) {
      playQuizPresenter.prepareFailView("No quiz is in progress.");
      return;
    }

    final int choiceIndex = inputData.getChoiceIndex();
    if (!session.isValidAnswer(choiceIndex)) {
      playQuizPresenter.prepareFailView("Invalid answer choice.");
      return;
    }

    session.recordAnswer(choiceIndex);
    if (session.isFinished()) {
      finishQuiz();
    }
    else {
      playQuizPresenter.prepareQuestionView(currentQuestionOutput());
    }
  }

  private void finishQuiz() {
    final Quiz quiz = session.getQuiz();
    final Attempt attempt = new Attempt(
        UUID.randomUUID().toString(),
        quiz.getId(),
        quiz.getName(),
        quiz.getQuestions(),
        session.getChosenIndexes(),
        session.getScore(),
        System.currentTimeMillis());
    quizDataAccessObject.saveAttempt(attempt);
    session = null;

    playQuizPresenter.prepareQuizCompletedView(new QuizCompletedOutputData(
        attempt.getId(), quiz.getName(), attempt.getScore(), quiz.getQuestions().size()));
  }

  private QuestionOutputData currentQuestionOutput() {
    final Quiz quiz = session.getQuiz();
    final Question question = session.getCurrentQuestion();
    return new QuestionOutputData(
        quiz.getName(),
        question.getText(),
        question.getChoices(),
        session.getCurrentQuestionIndex() + 1,
        quiz.getQuestions().size());
  }
}
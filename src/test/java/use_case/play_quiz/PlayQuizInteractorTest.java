package use_case.play_quiz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import data_access.InMemoryQuizDataAccessObject;
import entity.Attempt;
import entity.Question;
import entity.Quiz;

class PlayQuizInteractorTest {
  private InMemoryQuizDataAccessObject dataAccessObject;
  private RecordingPresenter presenter;
  private PlayQuizInteractor interactor;

  @BeforeEach
  void setUp() {
    dataAccessObject = new InMemoryQuizDataAccessObject();
    dataAccessObject.saveQuiz(new Quiz("q1", "Capitals", List.of(
        new Question("Capital of Canada?", List.of("Toronto", "Ottawa"), 1, "Geography", "easy"),
        new Question("Capital of France?", List.of("Paris", "Lyon", "Nice"), 0, "Geography", "easy")),
        0L));
    dataAccessObject.saveQuiz(new Quiz("empty", "Empty", List.of(), 0L));
    presenter = new RecordingPresenter();
    interactor = new PlayQuizInteractor(dataAccessObject, presenter);
  }

  /** A presenter that remembers what it was asked to show. */
  private static class RecordingPresenter implements PlayQuizOutputBoundary {
    private final List<QuestionOutputData> questions = new ArrayList<>();
    private QuizCompletedOutputData completed;
    private String error;

    @Override
    public void prepareQuestionView(QuestionOutputData outputData) {
      questions.add(outputData);
    }

    @Override
    public void prepareQuizCompletedView(QuizCompletedOutputData outputData) {
      completed = outputData;
    }

    @Override
    public void prepareFailView(String errorMessage) {
      error = errorMessage;
    }

    QuestionOutputData lastQuestion() {
      if (questions.isEmpty()) {
        fail("No question view was prepared.");
      }
      return questions.get(questions.size() - 1);
    }
  }

  @Test
  void startShowsFirstQuestion() {
    interactor.startQuiz(new StartQuizInputData("q1"));

    final QuestionOutputData shown = presenter.lastQuestion();
    assertEquals("Capitals", shown.getQuizName());
    assertEquals("Capital of Canada?", shown.getQuestionText());
    assertEquals(List.of("Toronto", "Ottawa"), shown.getChoices());
    assertEquals(1, shown.getQuestionNumber());
    assertEquals(2, shown.getTotalQuestions());
    assertNull(presenter.error);
  }

  @Test
  void startUnknownQuizFails() {
    interactor.startQuiz(new StartQuizInputData("nope"));

    assertEquals("Quiz not found: nope", presenter.error);
    assertTrue(presenter.questions.isEmpty());
  }

  @Test
  void startQuizWithNoQuestionsFails() {
    interactor.startQuiz(new StartQuizInputData("empty"));

    assertEquals("This quiz has no questions.", presenter.error);
    assertTrue(presenter.questions.isEmpty());
  }

  @Test
  void answeringFirstQuestionShowsSecond() {
    interactor.startQuiz(new StartQuizInputData("q1"));
    interactor.answerQuestion(new AnswerQuestionInputData(1));

    final QuestionOutputData shown = presenter.lastQuestion();
    assertEquals("Capital of France?", shown.getQuestionText());
    assertEquals(List.of("Paris", "Lyon", "Nice"), shown.getChoices());
    assertEquals(2, shown.getQuestionNumber());
    assertTrue(dataAccessObject.getSavedAttempts().isEmpty());
  }

  @Test
  void answeringLastQuestionSavesAttemptAndShowsScore() {
    interactor.startQuiz(new StartQuizInputData("q1"));
    interactor.answerQuestion(new AnswerQuestionInputData(1));                    // correct
    interactor.answerQuestion(new AnswerQuestionInputData(QuizSession.SKIPPED));  // skipped

    assertEquals(1, dataAccessObject.getSavedAttempts().size());
    final Attempt saved = dataAccessObject.getSavedAttempts().get(0);
    assertNotNull(saved.getId());
    assertEquals("q1", saved.getQuizId());
    assertEquals("Capitals", saved.getQuizName());
    assertEquals(2, saved.getQuestions().size());
    assertEquals(List.of(1, -1), saved.getChosenIndexes());
    assertEquals(1, saved.getScore());
    assertTrue(saved.getCompletedAt() > 0);

    final QuizCompletedOutputData completed = presenter.completed;
    assertNotNull(completed);
    assertEquals(saved.getId(), completed.getAttemptId());
    assertEquals("Capitals", completed.getQuizName());
    assertEquals(1, completed.getScore());
    assertEquals(2, completed.getTotalQuestions());
  }

  @Test
  void cannotAnswerAfterQuizIsFinished() {
    interactor.startQuiz(new StartQuizInputData("q1"));
    interactor.answerQuestion(new AnswerQuestionInputData(0));
    interactor.answerQuestion(new AnswerQuestionInputData(0));
    interactor.answerQuestion(new AnswerQuestionInputData(0));

    assertEquals("No quiz is in progress.", presenter.error);
    assertEquals(1, dataAccessObject.getSavedAttempts().size());
  }

  @Test
  void answeringWithoutStartingFails() {
    interactor.answerQuestion(new AnswerQuestionInputData(0));

    assertEquals("No quiz is in progress.", presenter.error);
  }

  @Test
  void invalidChoiceFailsAndDoesNotAdvance() {
    interactor.startQuiz(new StartQuizInputData("q1"));
    interactor.answerQuestion(new AnswerQuestionInputData(5));

    assertEquals("Invalid answer choice.", presenter.error);
    assertEquals(1, presenter.questions.size());
    assertNull(presenter.completed);
  }
}
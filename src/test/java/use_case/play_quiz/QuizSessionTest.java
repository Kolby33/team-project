package use_case.play_quiz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entity.Question;
import entity.Quiz;

class QuizSessionTest {
  private Question q1;
  private Question q2;
  private Quiz quiz;
  private QuizSession session;

  @BeforeEach
  void setUp() {
    q1 = new Question("Q1", List.of("a", "b", "c"), 2, "Cat", "easy");
    q2 = new Question("Q2", List.of("True", "False"), 0, "Cat", "easy");
    quiz = new Quiz("id", "Quiz", List.of(q1, q2), 0L);
    session = new QuizSession(quiz);
  }

  @Test
  void newSessionStartsAtFirstQuestion() {
    assertSame(quiz, session.getQuiz());
    assertEquals(0, session.getCurrentQuestionIndex());
    assertSame(q1, session.getCurrentQuestion());
    assertFalse(session.isFinished());
    assertEquals(0, session.getScore());
    assertTrue(session.getChosenIndexes().isEmpty());
  }

  @Test
  void recordAnswerMovesToNextQuestion() {
    session.recordAnswer(1);
    assertEquals(1, session.getCurrentQuestionIndex());
    assertSame(q2, session.getCurrentQuestion());
    assertEquals(List.of(1), session.getChosenIndexes());
  }

  @Test
  void isValidAnswerAcceptsSkipAndChoicesOnly() {
    assertTrue(session.isValidAnswer(QuizSession.SKIPPED));
    assertTrue(session.isValidAnswer(0));
    assertTrue(session.isValidAnswer(2));
    assertFalse(session.isValidAnswer(-2));
    assertFalse(session.isValidAnswer(3));
  }

  @Test
  void scoreCountsCorrectAnswersAndNotSkips() {
    session.recordAnswer(2);                    // correct
    session.recordAnswer(QuizSession.SKIPPED);  // skipped
    assertTrue(session.isFinished());
    assertEquals(1, session.getScore());
    assertEquals(List.of(2, -1), session.getChosenIndexes());
  }

  @Test
  void wrongAnswerScoresZero() {
    session.recordAnswer(0);
    session.recordAnswer(1);
    assertEquals(0, session.getScore());
  }

  @Test
  void invalidAnswerIsRejectedAndNotRecorded() {
    assertThrows(IllegalArgumentException.class, () -> session.recordAnswer(3));
    assertEquals(0, session.getCurrentQuestionIndex());
  }

  @Test
  void cannotContinueAfterFinishing() {
    session.recordAnswer(0);
    session.recordAnswer(0);
    assertThrows(IllegalStateException.class, () -> session.getCurrentQuestion());
    assertThrows(IllegalStateException.class, () -> session.recordAnswer(0));
  }

  @Test
  void chosenIndexesCannotBeModifiedFromOutside() {
    assertThrows(UnsupportedOperationException.class, () -> session.getChosenIndexes().add(0));
  }
}
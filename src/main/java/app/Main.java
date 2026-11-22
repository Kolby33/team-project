package app;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import data_access.InMemoryQuizDataAccessObject;
import entity.Question;
import entity.Quiz;

/**
 * Starts the app. Until the JSON file storage is ready, the sample quiz from
 * data/quizzes.json (id "quiz-001") is added in memory so Play Quiz can be demoed.
 */
public class Main {

  public static void main(String[] args) {
    final InMemoryQuizDataAccessObject quizDataAccessObject = new InMemoryQuizDataAccessObject();
    quizDataAccessObject.saveQuiz(sampleQuiz());

    SwingUtilities.invokeLater(() -> {
      final JFrame application = new AppBuilder(quizDataAccessObject)
          .addPlayQuizView()
          .addPlayQuizUseCase()
          .build();
      application.setSize(500, 350);
      application.setLocationRelativeTo(null);
      application.setVisible(true);
    });
  }

  private static Quiz sampleQuiz() {
    return new Quiz("quiz-001", "General Knowledge Warmup", List.of(
        new Question("What is the capital of France?",
            List.of("Berlin", "Madrid", "Paris", "Rome"), 2, "Geography", "easy"),
        new Question("Which planet is known as the Red Planet?",
            List.of("Venus", "Mars", "Jupiter", "Mercury"), 1, "Science", "easy"),
        new Question("How many sides does a hexagon have?",
            List.of("5", "6", "7", "8"), 1, "Mathematics", "medium")),
        1760000000000L);
  }
}
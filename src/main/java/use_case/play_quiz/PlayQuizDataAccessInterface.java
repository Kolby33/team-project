package use_case.play_quiz;

import entity.Attempt;
import entity.Quiz;

import java.util.Optional;

public interface PlayQuizDataAccessInterface {
    Optional<Quiz> findQuizById(String quizId);

    void saveAttempt(Attempt attempt);
}
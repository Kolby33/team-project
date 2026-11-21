package use_case.create_quiz;

import entity.Quiz;

public interface SaveQuizDataAccessInterface {
    void saveQuiz(Quiz quiz);
}
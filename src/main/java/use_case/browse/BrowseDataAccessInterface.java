package use_case.browse;

import entity.Attempt;
import entity.Quiz;

import java.util.List;

public interface BrowseDataAccessInterface {
    List<Quiz> loadAllQuizzes();

    List<Attempt> loadAllAttempts();
}
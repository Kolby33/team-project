package use_case.create_quiz;

import entity.Question;

import java.util.List;

public interface FetchQuestionsDataAccessInterface {
    /** Throws an unchecked exception if the request fails (no network, rate limit). */
    List<Question> fetchQuestions(String category, int amount);
}
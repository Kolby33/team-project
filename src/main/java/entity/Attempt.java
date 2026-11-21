package entity;

import java.util.List;

public class Attempt {
    private final String id;
    private final String quizId;
    private final String quizName;
    private final List<Question> questions;   // snapshot of the quiz at play time
    private final List<Integer> chosenIndexes; // -1 means the question was skipped
    private final int score;
    private final long completedAt;           // epoch millis

    public Attempt(String id, String quizId, String quizName, List<Question> questions,
                   List<Integer> chosenIndexes, int score, long completedAt) {
        this.id = id;
        this.quizId = quizId;
        this.quizName = quizName;
        this.questions = List.copyOf(questions);
        this.chosenIndexes = List.copyOf(chosenIndexes);
        this.score = score;
        this.completedAt = completedAt;
    }

    public String getId() { return id; }
    public String getQuizId() { return quizId; }
    public String getQuizName() { return quizName; }
    public List<Question> getQuestions() { return questions; }
    public List<Integer> getChosenIndexes() { return chosenIndexes; }
    public int getScore() { return score; }
    public long getCompletedAt() { return completedAt; }
}
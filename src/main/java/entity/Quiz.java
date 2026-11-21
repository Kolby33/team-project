package entity;

import java.util.List;

public class Quiz {
    private final String id;
    private final String name;
    private final List<Question> questions;
    private final long createdAt; 

    public Quiz(String id, String name, List<Question> questions, long createdAt) {
        this.id = id;
        this.name = name;
        this.questions = List.copyOf(questions);
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public List<Question> getQuestions() { return questions; }
    public long getCreatedAt() { return createdAt; }
}
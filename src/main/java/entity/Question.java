package entity;

import java.util.List;

public class Question {
    private final String text;
    private final List<String> choices;
    private final int correctIndex;
    private final String category;
    private final String difficulty;

    public Question(String text, List<String> choices, int correctIndex,
                    String category, String difficulty) {
        this.text = text;
        this.choices = List.copyOf(choices);
        this.correctIndex = correctIndex;
        this.category = category;
        this.difficulty = difficulty;
    }

    public String getText() { return text; }
    public List<String> getChoices() { return choices; }
    public int getCorrectIndex() { return correctIndex; }
    public String getCategory() { return category; }
    public String getDifficulty() { return difficulty; }
}
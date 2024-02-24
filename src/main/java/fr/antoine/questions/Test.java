package fr.antoine.questions;

import java.util.HashMap;

public class Test {

    private String name, statement;
    private HashMap<Integer, Question> questions;

    public Test(final String name, final String statement) {

        this.name = name;
        this.statement = statement;
        this.questions = new HashMap<>();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public HashMap<Integer, Question> getQuestions() {
        return questions;
    }

    public void setQuestions(HashMap<Integer, Question> questions) {
        this.questions = questions;
    }
}

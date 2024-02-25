package fr.antoine.questions.tests;

import fr.antoine.questions.Question;

import java.util.ArrayList;
import java.util.List;

public class Test {

    private String name, statement;
    private TestType type;
    private List<Question> questions;

    public Test(final String name, final TestType type, final String statement) {

        this.name = name;
        this.type = type;
        this.statement = statement;
        this.questions = new ArrayList<>();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TestType getType() {
        return type;
    }

    public void setType(TestType type) {
        this.type = type;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}

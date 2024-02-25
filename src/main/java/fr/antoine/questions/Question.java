package fr.antoine.questions;

import java.util.LinkedList;

public class Question {

    private final String statement, //The text before the proposition
                            generalFeedback;
    private final String[] statementImage, generalFbImage; //images in base64 code
    private final LinkedList<Proposition> propositions; //Propositions

    public Question(String statement, String generalFeedback, String[] statementImage, String[] generalFbImage) {
        this.statement = statement;
        this.generalFeedback = generalFeedback;
        this.statementImage = statementImage;
        this.generalFbImage = generalFbImage;
        this.propositions = new LinkedList<>();
    }

    /**
     * Register a new propositon
     * @param text - {@link String} : The text of the proposition
     * @param correct - {@link Boolean} : Is this proposition correct ?
     */
    public void registerProposition(final String text, final String feedback, final String[] propositionImage, final String[] feedbackImage, final boolean correct) {

        this.propositions.add(new Proposition(text, feedback, propositionImage, feedbackImage, correct));

    }

    public String getStatement() {
        return statement;
    }

    public LinkedList<Proposition> getPropositions() {
        return propositions;
    }

    public String getGeneralFeedback() {
        return generalFeedback;
    }

    public String[] getStatementImage() {
        return statementImage;
    }

    public String[] getGeneralFbImage() {
        return generalFbImage;
    }

    @Override
    public String toString() {
        return "Text : " + this.statement + " | " + this.propositions;
    }

    public record Proposition(String text, String feedback, String[] propositionImage, String[] feedbackImage,
                              boolean correct) {

        @Override
        public String toString() {
            return "Proposition{" +
                        "text='" + text + '\'' +
                        ", correct=" + correct +
                        '}';
        }
    }
}

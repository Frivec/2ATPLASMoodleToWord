package fr.antoine.questions;

import java.util.HashSet;
import java.util.LinkedList;

public class Question {

    private final String statement, //The text before the proposition
                            generalFeedback;
    private final LinkedList<Proposition> propositions; //Propositions

    public Question(final String statement, final String generalFeedback) {

        this.statement = statement;
        this.generalFeedback = generalFeedback;
        this.propositions = new LinkedList<>();

    }

    /**
     * Register a new propositon
     * @param text - {@link String} : The text of the proposition
     * @param correct - {@link Boolean} : Is this proposition correct ?
     */
    public void registerProposition(final String text, final String feedback, final boolean correct) {

        this.propositions.add(new Proposition(text, feedback, correct));

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

    @Override
    public String toString() {
        return "Text : " + this.statement + " | " + this.propositions;
    }

    public class Proposition {

        private final String text,
                        feedback;
        private final boolean correct;

        public Proposition(final String text, final String feedback, final boolean correct) {
            this.text = text;
            this.feedback = feedback;
            this.correct = correct;
        }

        @Override
        public String toString() {
            return "Proposition{" +
                    "text='" + text + '\'' +
                    ", correct=" + correct +
                    '}';
        }

        public String getText() {
            return text;
        }

        public String getFeedback() {
            return feedback;
        }

        public boolean isCorrect() {
            return correct;
        }
    }
}

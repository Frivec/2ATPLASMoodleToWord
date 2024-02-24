package fr.antoine.questions;

import java.util.HashSet;

public class Question {

    private final String statement; //The text before the proposition
    private HashSet<Proposition> propositions; //Propositions

    public Question(final String statement) {
        this.statement = statement;
    }

    /**
     * Register a new propositon
     * @param text - {@link String} : The text of the proposition
     * @param correct - {@link Boolean} : Is this proposition correct ?
     */
    public void registerProposition(final String text, final boolean correct) { this.propositions.add(new Proposition(text, correct)); }

    public class Proposition {

        private String text;
        private boolean correct;

        public Proposition(final String text, final boolean correct) {
            this.text = text;
            this.correct = correct;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public boolean isCorrect() {
            return correct;
        }

        public void setCorrect(boolean correct) {
            this.correct = correct;
        }
    }

}

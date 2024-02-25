package fr.antoine.questions.tests;

public enum TestType {

    ANATOMY("Anatomie"),
    BIOCHEMISTRY("Biochimie"),
    CELLULAR_BIOLOGY("Biologie Cellulaire"),
    CHEMISTRY("Chimie"),
    LAW_AND_SOCIAL_SCIENCES("Droit et Sciences Sociales"),
    MATHEMATICS("Mathématiques"),
    ODONTOLOGY("Odontologie"),
    PHYSIOLOGY("Physiologie"),
    PSYCHOLOGY("Psychologie"),
    THERAPEUTIC_SCIENCES("Sciences Thérapeutiques");

    private final String frenchName;

    TestType(String frenchName) {
        this.frenchName = frenchName;
    }

    public static TestType getTypeByName(final String frenchName) {

        for(TestType types : TestType.values())

            if(frenchName.equalsIgnoreCase(types.getFrenchName()))

                return types;

        return null;

    }

    public String getFrenchName() {
        return frenchName;
    }
}

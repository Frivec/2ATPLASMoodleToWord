package fr.antoine.questions.tests;

public enum TestType {

    ANATOMY("Anatomie", "moduleanatomie@asso2atp.fr"),
    BIOCHEMISTRY("Biochimie", "modulebiochimie@asso2atp.fr"),
    CELLULAR_BIOLOGY("Biologie Cellulaire", "modulebiologiecellulaire@asso2atp.fr"),
    CHEMISTRY("Chimie", "modulechimie@asso2atp.fr"),
    LAW_AND_SOCIAL_SCIENCES("Droit et Sciences Sociales", "moduledroitsciencessociales@asso2atp.fr"),
    MATHEMATICS("Mathématiques", "modulemathematiques@asso2atp.fr"),
    ODONTOLOGY("Odontologie", "moduleodontologie@asso2atp.fr"),
    PHYSIOLOGY("Physiologie", "modulephysiologiebiophysique@asso2atp.fr"),
    PSYCHOLOGY("Psychologie","modulepsychologie@asso2atp.fr"),
    THERAPEUTIC_SCIENCES("Sciences Thérapeutiques", "modulesciencestherapeutiques@asso2atp.fr");

    private final String frenchName,
                            mail;

    TestType(String frenchName, String mail) {
        this.frenchName = frenchName;
        this.mail = mail;
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

    public String getMail() {
        return mail;
    }
}

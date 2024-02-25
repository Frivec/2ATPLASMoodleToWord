package fr.antoine.word;

import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat;

public enum ListType {

    QCM("QCM %1.", STNumberFormat.DECIMAL),
    ITEM("%1.", STNumberFormat.UPPER_LETTER);

    private final String format;
    private final STNumberFormat.Enum numberFormat;

    ListType(String format, STNumberFormat.Enum stNumberFormat) {
        this.format = format;
        this.numberFormat = stNumberFormat;
    }

    public String getFormat() {
        return format;
    }

    public STNumberFormat.Enum getNumberFormat() {
        return numberFormat;
    }
}

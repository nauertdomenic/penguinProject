package projectanimal.whatever.jpa;

/**
 *
 * @author simon
 */
public enum TierartStatus {
    AUSGESTORBEN, BEDROHT, ENTDECKT, GESCHÜTZT, IN_FORSCHUNG, MYTHOS;

    /**
     * Bezeichnung ermitteln
     *
     * @return Bezeichnung
     */
    public String getLabel() {
        switch (this) {
            case AUSGESTORBEN:
                return "ausgestorben";
            case BEDROHT:
                return "bedroht";
            case ENTDECKT:
                return "entdeckt";
            case GESCHÜTZT:
                return "geschützt";
            case IN_FORSCHUNG:
                return "wird Untersucht";
            case MYTHOS:
                return "Mythos";
            default:
                return this.toString();
        }
    }

}

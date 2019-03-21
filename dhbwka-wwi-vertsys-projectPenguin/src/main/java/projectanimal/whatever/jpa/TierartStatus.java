package projectanimal.whatever.jpa;

/**
 *
 * @author phoenix
 */
public enum TierartStatus {
    ENTDECKT, IN_FORSCHUNG, GESCHÜTZT, BEDROHT, AUSGESTORBEN, MYTHOS;

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
                return "unter Artenschutz";
            case IN_FORSCHUNG:
                return "wird untersucht";
            case MYTHOS:
                return "Mythos";
            default:
                return this.toString();
        }
    }

}

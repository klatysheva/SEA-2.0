package de.telekom.sea2.lookup;

import java.util.Locale;

public enum SalutationOld {

    MR,
    MRS,
    MISS,
    OTHER;

    public static SalutationOld fromString(String str) {
        switch (str.toLowerCase(Locale.ROOT)) {
            case "mr":
            case "mr.":
            case "mister":
                return MR;
            case "mrs":
            case "mrs.":
            case "misses":
                return MRS;
            case "miss.":
            case "miss":
                return MISS;
            case "Other":
            case "other":
                return OTHER;
            default:
                throw new IllegalArgumentException("Unexpected value: " + str);
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case MRS:
                return "Mrs.";
            case MR:
                return "Mr.";
            case MISS:
                return "Miss.";
            case OTHER:
                return "Other";
            default:
                throw new IllegalArgumentException("Unexpected case!");
        }
    }

}

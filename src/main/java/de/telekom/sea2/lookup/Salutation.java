package de.telekom.sea2.lookup;

import java.util.Locale;

public enum Salutation {

    MR((byte) 0, new String[]{"mr.", "mr", "mister"}, "Mr."),
    MRS((byte) 1, new String[]{"mrs.", "mrs", "misses"}, "Mrs."),
    MISS((byte) 2, new String[]{"miss", "miss."}, "Miss"),
    OTHER((byte) 3, new String[]{"other"}, "Other");

    byte byteValue;
    String[] stringAllowedValues;
    String stringValue;

    private Salutation(byte byteValue, String[] stringAllowedValues, String stringValue) {
        this.byteValue = byteValue;
        this.stringAllowedValues = stringAllowedValues;
        this.stringValue = stringValue;
    }

    public byte getByteValue() {
        return byteValue;
    }

    public String[] getStringAllowedValues() {
        return stringAllowedValues;
    }

    public String getStringValue() {
        return stringValue;
    }

    public static Salutation fromString(String str) {
        Salutation[] salutations = Salutation.values();
        for (Salutation s : salutations) {
            String[] stringValues = s.getStringAllowedValues();
            for (String ss : stringValues) {
                if (ss.equals(str.toLowerCase(Locale.ROOT))) {
                    return s;
                }
            }
        }
        throw new IllegalArgumentException("Unexpected value: " + str);
    }

    public static Salutation fromByte (byte b) {
        Salutation[] salutations = Salutation.values();
        for (Salutation s : salutations) {
            if (s.byteValue == b) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unexpected value: " + b);
    }

    public static void showAllowedValues() {
        Salutation[] salutations = Salutation.values();
        for (Salutation s : salutations) {
            String[] stringValues = s.getStringAllowedValues();
            for (String ss : stringValues) {
                System.out.print(" " + ss);
            }
        }
        System.out.println();
    }

    public static boolean isSalutationStringValue(String string) {
        Salutation[] salutations = Salutation.values();
        for (Salutation s : salutations) {
            String[] stringValues = s.getStringAllowedValues();
            for (String ss : stringValues) {
                if (ss.equals(string.toLowerCase(Locale.ROOT))) {
                    return true;
                }
            }
        }
        return false;
    }

//    public static Salutation fromString(String str) {
//        switch (str.toLowerCase(Locale.ROOT)) {
//            case "mr":
//            case "mr.":
//            case "mister":
//                return MR;
//            case "mrs":
//            case "mrs.":
//            case "misses":
//                return MRS;
//            case "miss.":
//            case "miss":
//                return MISS;
//            case "other":
//                return OTHER;
//            default:
//                throw new IllegalArgumentException("Unexpected value: " + str);
//        }
//    }



//    public static Salutation fromByte(byte b) {
//        switch (b) {
//            case 0:
//                return MR;
//            case 1:
//                return MRS;
//            case 2:
//                return MISS;
//            case 3:
//                return OTHER;
//            default:
//                throw new IllegalArgumentException("Unexpected value: " + b);
//        }
//    }
}


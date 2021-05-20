package de.telekom.sea2.lookup;

import java.util.Locale;

public enum Salutation {

    MR((byte) 0, "Mr."),
    MRS((byte) 1, "Ms."),
    MISS((byte) 2, "Miss"),
    OTHER((byte) 3, "Miss");

    byte byteValue;
    String stringValue;

    private Salutation(byte b, String s) {
        this.byteValue = b;
        this.stringValue = s;
    }

    public byte getByteValue() {
        return byteValue;
    }

    public void setByteValue(byte byteValue) {
        this.byteValue = byteValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public static Salutation fromString(String str) {
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
            case "other":
                return OTHER;
            default:
                throw new IllegalArgumentException("Unexpected value: " + str);
        }
    }

    public static Salutation fromByte (byte b) {
        switch (b) {
            case 0:
                return MR;
            case 1:
                return MRS;
            case 2:
                return MISS;
            case 3:
                return OTHER;
            default:
                throw new IllegalArgumentException("Unexpected value: " + b);
        }
    }
}


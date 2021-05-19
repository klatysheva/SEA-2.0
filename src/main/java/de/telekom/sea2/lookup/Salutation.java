package de.telekom.sea2.lookup;

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
}


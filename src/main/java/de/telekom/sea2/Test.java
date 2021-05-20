package de.telekom.sea2;

import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        SeminarApp seminarApp = new SeminarApp();
        seminarApp.connectDb();
        seminarApp.run(args);
        seminarApp.disconnectDb();
    }
}

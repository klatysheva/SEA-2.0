package de.telekom.sea2;

import de.telekom.sea2.lookup.Salutation;

import java.sql.SQLException;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        try (SeminarApp seminarApp = new SeminarApp()) {
            seminarApp.connectDb();
            seminarApp.run(args);
            //seminarApp.disconnectDb();
        }


    }
}

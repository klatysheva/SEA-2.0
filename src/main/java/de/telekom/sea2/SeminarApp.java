package de.telekom.sea2;

import de.telekom.sea2.model.Person;
import de.telekom.sea2.persistence.PersonsRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static de.telekom.sea2.lookup.Salutation.*;

class SeminarApp {
    final String DRIVER = "com.mysql.cj.jdbc.Driver";
    final String URL = "jdbc:mysql://localhost:3306/seadb?user=seauser&password=seapass";
    Connection connection;

    public void connectDb () throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        connection = DriverManager.getConnection(URL);

    }

    public void disconnectDb() throws SQLException {
        connection.close();
    }

    public void run(String[] args) {
          PersonsRepository personsRepository = new PersonsRepository(connection);
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Please input firstname:");
//        String firstname = scanner.nextLine();
//        System.out.println("Please input lastname:");
//        String lastname = scanner.nextLine();
//        System.out.println("Please input salutation:");
//        Salutation salutation = Salutation.fromString(scanner.nextLine());
        Person person = new Person(96, MRS, "Adams", "Susi");
        personsRepository.create(person);
        personsRepository.update(person, "Mr.", "Smith", "Sam");
    }
}

package de.telekom.sea2;

import de.telekom.sea2.model.Person;
import de.telekom.sea2.persistence.PersonsRepository;
import de.telekom.sea2.ui.Menu;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static de.telekom.sea2.lookup.Salutation.*;

class SeminarApp implements Closeable {
    final String DRIVER = "com.mysql.cj.jdbc.Driver";
    final String URL = "jdbc:mysql://localhost:3306/seadb?user=seauser&password=seapass";
    Connection connection;

    public void run(String[] args) throws SQLException {
        PersonsRepository personsRepository = new PersonsRepository(connection);

        //for tests only:
        Person person = new Person(1, MRS, "Adams", "Susi");
        Person person1 = new Person(2, MR, "Adams", "Sam");
        Person person2= new Person(3, MRS, "Wilson", "Ann");
        try {
            personsRepository.create(person);
            personsRepository.create(person1);
            personsRepository.create(person2);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        //main part:
        try (Menu menu = new Menu(personsRepository)) {
            menu.showList();
            menu.selectOption();
        }
        // ####################


        //for tests only:
        try {
            personsRepository.deleteAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Please input firstname:");
//        String firstname = scanner.nextLine();
//        System.out.println("Please input lastname:");
//        String lastname = scanner.nextLine();
//        System.out.println("Please input salutation:");
//        Salutation salutation = Salutation.fromString(scanner.nextLine());

    }

    public void connectDb () throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        connection = DriverManager.getConnection(URL);

    }

    public void disconnectDb() throws SQLException {
        connection.close();
    }

    @Override
    public void close()  {
        try {
            connection.close();
            System.out.println("Connection is closed.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

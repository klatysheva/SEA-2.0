package de.telekom.sea2.persistence;

import de.telekom.sea2.model.Person;

import java.sql.*;
import java.util.List;

public class PersonsRepository {
    final String DRIVER = "com.mysql.cj.jdbc.Driver";
    final String URL = "jdbc:mysql://localhost:3306/seadb?user=seauser&password=seapass";

    public void create (Person person) {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection(URL);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT into persons (ID, SALUTATION, NAME, SURNAME) values (?,?,?,?)");
            preparedStatement.setLong(1, person.getId());
            preparedStatement.setByte(2, person.getSalutation().getByteValue());
            preparedStatement.setString(3, person.getFirstname());
            preparedStatement.setString(4, person.getLastname());
            preparedStatement.execute();

            preparedStatement.close();
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update (Person person) {
    }

    public void delete (long id) {
    }

    public void delete (Person person) {
    }

    public void deleteAll () {
    }

    public Person get(long id) {
        return null;
    }

    public List getAll() {
        return null;
    }

}

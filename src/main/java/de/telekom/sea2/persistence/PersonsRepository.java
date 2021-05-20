package de.telekom.sea2.persistence;

import de.telekom.sea2.lookup.Salutation;
import de.telekom.sea2.model.Person;

import java.sql.*;
import java.util.List;

public class PersonsRepository {
    private Connection connection;
    private String query;
    private ResultSet resultSet;

    public PersonsRepository(Connection connection) {
        this.connection = connection;
    }

    public void create (Person person) {
        query = "INSERT into persons (ID, SALUTATION, NAME, SURNAME) values (?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, person.getId());
            preparedStatement.setByte(2, person.getSalutation().getByteValue());
            preparedStatement.setString(3, person.getFirstname());
            preparedStatement.setString(4, person.getLastname());
            preparedStatement.execute();
            //preparedStatement.close(); redundant - will be closed automatically because of try-with-resources?

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void update (Person person, String salutation, String lastname, String firstname) {
        query = "UPDATE persons SET salutation = ?, surname = ?, name = ?  WHERE ID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setByte(1, Salutation.fromString(salutation).getByteValue());
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, firstname);
            preparedStatement.setLong(4, person.getId());
            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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

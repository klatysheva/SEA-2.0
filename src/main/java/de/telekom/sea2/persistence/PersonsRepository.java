package de.telekom.sea2.persistence;

import de.telekom.sea2.exceptions.MyException;
import de.telekom.sea2.lookup.Salutation;
import de.telekom.sea2.model.Person;

import java.sql.*;
import java.util.List;

public class PersonsRepository {
    private final Connection CONNECTION;
    private String query;

    public PersonsRepository(Connection connection) {
        this.CONNECTION = connection;
    }

    public int size () throws SQLException {
        query = "SELECT COUNT(*) FROM persons";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        int size = resultSet.getInt(1);
        preparedStatement.close();
        return size;
    }

    public boolean create(Person person) throws SQLException {
        query = "INSERT into persons (ID, SALUTATION, NAME, SURNAME) values (?,?,?,?)";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);
        preparedStatement.setLong(1, person.getId());
        preparedStatement.setByte(2, person.getSalutation().getByteValue());
        preparedStatement.setString(3, person.getFirstname());
        preparedStatement.setString(4, person.getLastname());
        preparedStatement.execute();
        preparedStatement.close();
        return true;

    }

    public boolean update(Person person, String salutation, String lastname, String firstname) throws SQLException {
        query = "UPDATE persons SET salutation = ?, surname = ?, name = ?  WHERE ID = ?";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);
        preparedStatement.setByte(1, Salutation.fromString(salutation).getByteValue());
        preparedStatement.setString(2, lastname);
        preparedStatement.setString(3, firstname);
        preparedStatement.setLong(4, person.getId());
        preparedStatement.execute();
        preparedStatement.close();
        return true;
    }

    public boolean delete(long id) throws SQLException {
        query = "DELETE FROM persons WHERE ID = ?";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        preparedStatement.close();
        return true;
    }

    public boolean delete(Person person) throws SQLException {
        query = "DELETE FROM persons WHERE name = ? AND surname = ?";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);
        preparedStatement.setString(1, person.getFirstname());
        preparedStatement.setString(2, person.getLastname());
        preparedStatement.execute();
        preparedStatement.close();
        return true;
    }

    public boolean deleteAll() throws SQLException {
        query = "DELETE FROM persons";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);
            preparedStatement.execute();
            preparedStatement.close();
            return true;

    }

    public Person get(long id) throws SQLException {
        query = "SELECT * FROM persons WHERE id = ?";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(query);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Person person = null;
        if (resultSet.next()) {
            person = new Person(Salutation.fromByte(resultSet.getByte(2)), resultSet.getString(4), resultSet.getString(3));
        }
        resultSet.close();
        preparedStatement.close();
        if (person != null) {
            return person;
        } else {
            throw new MyException("Nothing is found by id " + id);
        }

    }

    public List getAll() {

        return null;
    }



}

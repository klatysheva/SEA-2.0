package de.telekom.sea2.persistence;

import de.telekom.sea2.lookup.Salutation;
import de.telekom.sea2.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static de.telekom.sea2.ui.Color.ANSI_RED;
import static de.telekom.sea2.ui.Color.ANSI_RESET;

public class PersonsRepository {
    private final Connection CONNECTION;

    public PersonsRepository(Connection connection) {
        this.CONNECTION = connection;
    }

    public boolean create(Person person) throws SQLException {
        final String CREATEQUERY = "INSERT into persons (ID, SALUTATION, NAME, SURNAME) values (?,?,?,?)";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(CREATEQUERY);
        preparedStatement.setLong(1, person.getId());
        preparedStatement.setByte(2, person.getSalutation().getByteValue());
        preparedStatement.setString(3, person.getFirstname());
        preparedStatement.setString(4, person.getLastname());
        preparedStatement.execute();
        preparedStatement.close();
        return true;
    }

    public boolean updateLastname(long id, String lastname) throws SQLException {
        final String UPDATEQUERY = "UPDATE persons SET surname = ? WHERE ID = ?";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(UPDATEQUERY);
        preparedStatement.setString(1, lastname);
        preparedStatement.setLong(2, id);
        preparedStatement.execute();
        preparedStatement.close();
        return true;
    }

    public boolean updateFirstname(long id, String firstname) throws SQLException {
        final String UPDATEQUERY = "UPDATE persons SET name = ?  WHERE ID = ?";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(UPDATEQUERY);
        preparedStatement.setString(1, firstname);
        preparedStatement.setLong(2, id);
        preparedStatement.execute();
        preparedStatement.close();
        return true;
    }

    public boolean updateSalutation(long id, String salutation) throws SQLException {
        final String UPDATEQUERY = "UPDATE persons SET salutation = ? WHERE ID = ?";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(UPDATEQUERY);
        preparedStatement.setByte(1, Salutation.fromString(salutation).getByteValue());
        preparedStatement.setLong(2, id);
        preparedStatement.execute();
        preparedStatement.close();
        return true;
    }


    public boolean delete(long id) throws SQLException {
        if (isIdPresent(id)) {
            final String DELETEBYIDQUERY = "DELETE FROM persons WHERE ID = ?";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(DELETEBYIDQUERY);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            preparedStatement.close();
            return true;
        } else {
            System.out.println(ANSI_RED + "Person is not found." + ANSI_RESET);
            return false;
        }
    }

    public boolean delete(String name, String surname) throws SQLException {
        final String DELETEPERSONQUERY = "DELETE FROM persons WHERE name = ? AND surname = ?";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(DELETEPERSONQUERY);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);
        int count = preparedStatement.executeUpdate();
        System.out.println(count + " person(s) deleted.");
        preparedStatement.close();
        return true;
    }

    public boolean deleteAll() throws SQLException {
        final String DELETEALLQUERY = "DELETE FROM persons";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(DELETEALLQUERY);
        int count = preparedStatement.executeUpdate();
        System.out.println(count + " person(s) deleted. ");
        preparedStatement.close();
        return true;
    }

    public boolean isIdPresent(long id) throws SQLException {
        final String IDCOUNTQUERY = "SELECT COUNT(*) FROM persons WHERE id = ?";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(IDCOUNTQUERY);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);
        preparedStatement.close();
        if (count == 0) {
            return false;
        }
        return true;
    }

    public int size() throws SQLException {
        final String COUNTQUERY = "SELECT COUNT(*) FROM persons";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(COUNTQUERY);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int size = resultSet.getInt(1);
        preparedStatement.close();
        return size;
    }

    public Person get(long id) throws SQLException {
        final String GETQUERY = "SELECT * FROM persons WHERE id = ?";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(GETQUERY);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Person person;
        if (resultSet.next()) {
            person = new Person(resultSet.getLong(1), Salutation.fromByte(resultSet.getByte(2)), resultSet.getString(4), resultSet.getString(3));
            resultSet.close();
            preparedStatement.close();
        } else {
            System.out.println("Nothing is found by id " + id + ".");
            person = null;
        }
        return person;

    }

    public ArrayList<Person> getAll() throws SQLException {
        ArrayList<Person> list = new ArrayList<Person>();
        final String GETALLQUERY = "SELECT * FROM persons";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(GETALLQUERY);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Person person = new Person(resultSet.getLong(1), Salutation.fromByte(resultSet.getByte(2)), resultSet.getString(4), resultSet.getString(3));
            list.add(person);
        }
        return list;
    }


}

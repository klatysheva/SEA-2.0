package de.telekom.sea2.model;

import de.telekom.sea2.lookup.Salutation;
import de.telekom.sea2.persistence.PersonsRepository;

import java.sql.SQLException;
import java.util.Scanner;

import static de.telekom.sea2.ui.Color.ANSI_RED;
import static de.telekom.sea2.ui.Color.ANSI_RESET;

public class Person {
    private long id;
    private Salutation salutation;
    private String firstname;
    private String lastname;

    public Person() {
    }

    public Person(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Person(long id, Salutation salutation, String lastname, String firstname) {
        this.id = id;
        this.salutation = salutation;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public boolean setFirstname(Scanner scanner) {
        System.out.println("Enter name: ");
        String firstname = scanner.nextLine();
        if (firstname.equals("")) {
            System.out.println(ANSI_RED + "Firstname can't be set to an empty value." + ANSI_RESET);
            return false;
        }
        this.firstname = firstname;
        return true;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean setLastname(Scanner scanner) {
        System.out.println("Enter surname: ");
        String lastname = scanner.nextLine();
        if (lastname.equals("")) {
            System.out.println(ANSI_RED + "Lastname can't be set to an empty value." + ANSI_RESET);
            return false;
        }
        this.lastname = firstname;
        return true;
    }

    public void setSalutation(Salutation salutation) {
        this.salutation = salutation;
    }

    public boolean setSalutation(Scanner scanner) {
        Salutation salutation;
        System.out.println("Enter salutation: ");
        String salutationString = scanner.nextLine();
        if (Salutation.isSalutationStringValue(salutationString)) {
            salutation = Salutation.fromString(salutationString);
            this.salutation = salutation;
            return true;
        }
        else {
            System.out.println(ANSI_RED + "This value is not allowed for salutation.");
            System.out.print("Allowed values: ");
            Salutation.showAllowedValues();
            System.out.print(ANSI_RESET);
            return false;
        }
    }

    public void setId(long id) {
        this.id = id;
    }
    public boolean setId(PersonsRepository personsRepository, Scanner scanner) throws SQLException {
        long id = 0;
        System.out.println("Enter person's id (long): ");
        if (scanner.hasNextLong()) {
            if (personsRepository.isIdPresent(id)) {
                System.out.println(ANSI_RED + "Person with this id is already present." + ANSI_RESET);
                return false;
            }
            this.id = scanner.nextLong();
            scanner.nextLine();
            return true;
        }
        else {
            System.out.println(ANSI_RED + "This value can't be parsed to long." + ANSI_RESET);
            scanner.nextLine();
            return false;
        }
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Salutation getSalutation() {
        return salutation;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) // null check
        {
            return false;
        }
        if (this == obj) // self check
        {
            return true;
        }
        if (!(obj instanceof Person)) {// type check
            return false;
        }
        Person person = (Person) obj; // cast to Person
        return ((person.getId() == this.getId()) && person.getFirstname().equals(this.firstname) && person.getLastname().equals(this.lastname) && person.getSalutation().equals(this.salutation));
    }
}


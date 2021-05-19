package de.telekom.sea2.model;

import de.telekom.sea2.lookup.Salutation;

public class Person {
    private long id;
    private Salutation salutation;
    private String firstname;
    private String lastname;
    private static long personsCounter = 0;

    public Person(Salutation salutation, String lastname, String firstname) {
        this.id = ++personsCounter;
        this.salutation = salutation;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setSalutation(Salutation salutation) {
        this.salutation = salutation;
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
        return ((person.getId()==this.getId()) &&person.getFirstname().equals(this.firstname) && person.getLastname().equals(this.lastname) && person.getSalutation().equals(this.salutation));
    }
}


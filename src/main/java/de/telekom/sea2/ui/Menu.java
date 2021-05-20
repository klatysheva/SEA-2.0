package de.telekom.sea2.ui;

import de.telekom.sea2.lookup.Salutation;
import de.telekom.sea2.model.Person;
import de.telekom.sea2.persistence.PersonsRepository;

import java.io.Closeable;
import java.sql.SQLException;
import java.util.Scanner;

public class Menu implements Closeable {
    private PersonsRepository personsRepository;
    Scanner scanner = new Scanner(System.in);

    public Menu(PersonsRepository personsRepository) {
        this.personsRepository = personsRepository;
    }

    public void setRepo(PersonsRepository personsRepository) {
        this.personsRepository = personsRepository;
    }

    public void showMenu() {
        System.out.println(Color.ANSI_BLUE + "Select option:" + Color.ANSI_GREEN);
        System.out.println("1 - input person - OK");
        System.out.println("2 - remove person - TODO: id not found");
        System.out.println("3 - remove all persons - TODO: no persons");
        System.out.println("4 - show count of persons - NOK");
        System.out.println("5 - show data by person's id");
        System.out.println("6 - list all persons");
        System.out.println("0 - exit");
        System.out.println(Color.ANSI_RESET);
    }

    public String inputLine() {
        String result = "";
        result = scanner.nextLine();
        return result;
    }

    public void selectOption() {
        String result = "";
        do {
            showMenu();
            result = checkMenu();
        }
        while (!result.equals("0"));
    }

    private String checkMenu() {
        String result;
        result = inputLine();
        switch (result) {
            case "1":
                System.out.println("1. Input person.");
                try {
                    inputPerson();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            case "2":
                System.out.println("2. Remove person");
                try {
                    removePerson();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                break;
            case "3":
                System.out.println("3. Remove all.");
                try {
                    removeAll();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            case "4":
                System.out.println("4. Show size.");
                try {
                    showSize();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            case "5":
                System.out.println("5. Show person's data by person's id.");
                try {
                    showPersonsData();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            case "6":
                System.out.println("6. Show list of all persons.");
                showList();
                break;
            case "0":
                System.out.println("0. Exit.");
                break;
            default:
                System.out.println("Please chose an valid number. To exit input 0.");
        }
        return result;
    }


    private void showPersonsData() throws SQLException {
        Person person = getPerson();
        System.out.println("Id: " + person.getId());
        System.out.println("Salutation: " + person.getSalutation().getStringValue());
        System.out.println("Surname: " + person.getLastname());
        System.out.println("Name: " + person.getFirstname());
    }

    private Person getPerson() throws SQLException {
        System.out.println("Enter person's id: ");
        long id = Long.parseLong(scanner.nextLine());
        return personsRepository.get(id);
    }

    public void inputPerson() throws SQLException {
        System.out.println("Enter person's id: ");
        long id = Long.parseLong(scanner.nextLine());
        System.out.println("Enter name: ");
        String name = scanner.nextLine();
        System.out.println("Enter surname: ");
        String surname = scanner.nextLine();
        System.out.println("Enter salutation: ");
        Salutation salutation = Salutation.fromString(scanner.nextLine());
        Person person = new Person(id, salutation, surname, name);
        personsRepository.create(person);
    }

    @Override
    public void close() {
        scanner.close();
        System.out.println("Scanner is closed!");
    }

    public void removeAll() throws SQLException {
        personsRepository.deleteAll();
    }

    public void removePerson() throws SQLException {
        System.out.println("To delete person by element's id input '1', by name&surname - '2' ");
        String option = inputLine();
        switch (option) {
            case "1":
                System.out.println("Input person's id: ");
                long i = Long.parseLong(inputLine());
                personsRepository.delete(i);
                break;
            case "2":
                System.out.println("Input person's name: ");
                String name = inputLine();
                System.out.println("Input person's surname: ");
                String surname = inputLine();
                Person person = new Person(name, surname);
                personsRepository.delete(person);
                break;
            default:
                System.out.println("Invalid option. Returned to main menu.");
                break;
        }
    }

    public void showList() { //listAllPerson doesn't show last element
    }

    public void showSize() throws SQLException {
        System.out.println("Count of person's: " + personsRepository.size());

    }

}

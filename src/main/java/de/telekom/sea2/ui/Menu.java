package de.telekom.sea2.ui;

import de.telekom.sea2.lookup.Salutation;
import de.telekom.sea2.model.Person;
import de.telekom.sea2.persistence.PersonsRepository;

import java.io.Closeable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static de.telekom.sea2.ui.Color.*;

public class Menu implements Closeable {
    private PersonsRepository personsRepository;
    Scanner scanner = new Scanner(System.in);

    public Menu(PersonsRepository personsRepository) {
        this.personsRepository = personsRepository;
    }

    public void showMenu() throws SQLException {
        System.out.println();
        System.out.println(ANSI_BLUE + "Select option:" + ANSI_GREEN);
        System.out.println("1 - input person");
        System.out.println("2 - show count of persons");
        if (personsRepository.size() == 0) {
            System.out.print(ANSI_GREY);
            System.out.println("3 - (NOT ACTIVE) remove person");
            System.out.println("4 - (NOT ACTIVE) remove all persons");
            System.out.println("5 - (NOT ACTIVE) show data by person's id");
            System.out.println("6 - (NOT ACTIVE) update person");
            System.out.print(ANSI_GREEN);
        } else {
            System.out.println("3 - remove person");
            System.out.println("4 - remove all persons");
            System.out.println("5 - show data by person's id");
            System.out.println("6 - update person");
        }
        System.out.println("7 - list all persons");
        System.out.println("0 - exit");
        System.out.println(ANSI_RESET);
    }

    public String inputLine() {
        String result = "";
        result = scanner.nextLine();
        return result;
    }

    public void selectOption() {
        String result = "";
        do {
            try {
                showMenu();
                result = checkMenu();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        while (!result.equals("0"));
    }

    private String checkMenu() throws SQLException {
        String result;
        result = inputLine();
        switch (result) {
            case "1":
                System.out.println("1. Input person.");
                inputPerson();
                break;
            case "2":
                System.out.println("2. Show size.");
                showSize();
                break;
            case "3":
                if (personsRepository.size() == 0) {
                    System.out.println("Please chose an valid number. To exit input 0.");
                    break;
                }
                System.out.println("3. Remove person");
                removePerson();
                break;
            case "4":
                if (personsRepository.size() == 0) {
                    System.out.println("Please chose an valid number. To exit input 0.");
                    break;
                }
                System.out.println("4. Remove all.");
                removeAll();
                break;

            case "5":
                if (personsRepository.size() == 0) {
                    System.out.println("Please chose an valid number. To exit input 0.");
                    break;
                }
                System.out.println("5. Show person's data by person's id.");
                showPersonsData();
                break;
            case "6":
                if (personsRepository.size() == 0) {
                    System.out.println("Please chose an valid number. To exit input 0.");
                    break;
                }
                System.out.println("6. Update person");
                updatePerson();
                break;
            case "7":
                System.out.println("7. Show list of all persons.");
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

    public boolean inputPerson() throws SQLException {
        Person person = new Person();

//      how to join?
//        if (!(person.setId(personsRepository, scanner) ||
//                person.setFirstname(scanner)||
//                person.setLastname(scanner) ||
//                person.setSalutation(scanner)
//        )) {
//            System.out.println(ANSI_RED + "Returned to the main menu." + ANSI_RESET);
//            return false;
//        }

        if (!person.setId(personsRepository, scanner)) {
            System.out.println(ANSI_RED + "Returned to the main menu." + ANSI_RESET);
            return false;
        }

        if (!person.setFirstname(scanner)) {
            System.out.println(ANSI_RED + "Returned to the main menu." + ANSI_RESET);
            return false;
        }

        if (!person.setLastname(scanner)) {
            System.out.println(ANSI_RED + "Returned to the main menu." + ANSI_RESET);
            return false;
        }

        if (!person.setSalutation(scanner)) {
            System.out.println(ANSI_RED + "Returned to the main menu." + ANSI_RESET);
            return false;
        }

        personsRepository.create(person);
        return true;
    }


    private boolean updatePerson() throws SQLException {
        long id = inputId();
        if (personsRepository.isIdPresent(id)) {
            String option = "";
            do {
                showUpdatePersonMenu();
                option = scanner.nextLine();
                switch (option) {
                    case "1":
                        return updateSalutation(id);
                    case "2":
                        return updateFirstname(id);
                    case "3":
                        return updateLastname(id);
                    case "0":
                        System.out.println("Returned to the main menu.");
                        return false;
                    default:
                        System.out.println("Please chose 1, 2 or 3. Or 0 to return to the main menu.");
                }
            }
            while (!option.equals("0"));
        } else {
            System.out.println(ANSI_RED + "Person is not found." + ANSI_RESET);
        }
        return false;
    }

    private boolean updateLastname(long id) throws SQLException {
        System.out.println("Please input a new lastname:");
        String lastname = scanner.nextLine();
        if (lastname.equals("")) {
            System.out.println(ANSI_RED + "Lastname can't be set to an empty value." + ANSI_RESET);
            return false;
        }
        else {
            personsRepository.updateLastname(id, lastname);
            return true;
        }
    }

    private boolean updateFirstname(long id) throws SQLException {
        System.out.println("Please input a new firstname:");
        String firstname = scanner.nextLine();
        if (firstname.equals("")) {
            System.out.println(ANSI_RED + "Firstname can't be set to an empty value." + ANSI_RESET);
            return false;
        }
        else {
            personsRepository.updateFirstname(id, firstname);
            return true;
        }
    }

    private boolean updateSalutation(long id) throws SQLException {
        System.out.println("Please input a new salutation:");
        String salutation = scanner.nextLine();
        if (Salutation.isSalutationStringValue(salutation)) {
            personsRepository.updateSalutation(id, salutation);
            return true;
        }
        else {
            System.out.println(ANSI_RED + "This value is not allowed for salutation.");
            System.out.print("Allowed values: ");
            Salutation.showAllowedValues();
            System.out.println(ANSI_RESET);
            return false;
        }
    }

    private long inputId() {
        System.out.println("Input person's id: ");
        return Long.parseLong(inputLine());
    }

    private void showUpdatePersonMenu() {
        System.out.println(ANSI_BLUE + "Please chose what do you want to update:" + ANSI_RESET);
        System.out.println("1 - salutation;");
        System.out.println("2 - firstname;");
        System.out.println("3 - lastname;");
        System.out.println("0 - return to main menu;");
    }

    private Person getPerson(long id) throws SQLException {
        return personsRepository.get(id);
    }

    private void showPersonsData() throws SQLException {
        if (personsRepository.size() == 0) {
            System.out.println(ANSI_RED + "There is no records in the table." + ANSI_RESET);
            return;
        }
        long id = inputId();
        if (personsRepository.isIdPresent(id)) {
            Person person = getPerson(id);
            System.out.print("Id: " + person.getId() + "; ");
            System.out.print("Salutation: " + person.getSalutation().getStringValue() + "; ");
            System.out.print("Surname: " + person.getLastname() + "; ");
            System.out.println("Name: " + person.getFirstname() + ".");
        } else {
            System.out.println(ANSI_RED + "Person is not found." + ANSI_RESET);
        }
    }

    @Override
    public void close() {
        scanner.close();
        System.out.println("Scanner is closed!");
    }

    public boolean removeAll() throws SQLException {
        if (personsRepository.size() == 0) {
            System.out.println(ANSI_RED + "There is no records in the table. Nothing to delete." + ANSI_RESET);
            return false;
        }
        personsRepository.deleteAll();
        return true;
    }

    public boolean removePerson() throws SQLException {
        if (personsRepository.size() == 0) {
            System.out.println(ANSI_RED + "There is no records in the table. Nothing to delete." + ANSI_RESET);
            return false;
        }
        System.out.println("To delete person by element's id input '1', by name&surname - '2' ");
        String option = inputLine();
        switch (option) {
            case "1":
                long id = inputId();
                return (personsRepository.delete(id));
            case "2":
                System.out.println("Input person's name: ");
                String name = inputLine();
                System.out.println("Input person's surname: ");
                String surname = inputLine();
                return (personsRepository.delete(name, surname));
            default:
                System.out.println("Invalid option. Returned to main menu.");
                return false;
        }
    }

    public void showList() throws SQLException {
        ArrayList list = personsRepository.getAll();
        if (list.size() == 0) {
            System.out.println(ANSI_RED + "List is empty." + ANSI_RESET);
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            Person person = (Person) list.get(i);
            System.out.print("Id: " + person.getId() + "; ");
            System.out.print("Salutation: " + person.getSalutation().getStringValue() + "; ");
            System.out.print("Surname: " + person.getLastname() + "; ");
            System.out.println("Name: " + person.getFirstname() + ".");
        }

    }

    public void showSize() throws SQLException {
        System.out.println("Count of person's: " + personsRepository.size());
    }

}

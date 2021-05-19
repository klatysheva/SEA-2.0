package de.telekom.sea2;

import de.telekom.sea2.model.Person;
import de.telekom.sea2.persistence.PersonsRepository;
import static de.telekom.sea2.lookup.Salutation.*;

class SeminarApp {
    public void run(String[] args) {
        PersonsRepository personsRepository = new PersonsRepository();
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Please input firstname:");
//        String firstname = scanner.nextLine();
//        System.out.println("Please input lastname:");
//        String lastname = scanner.nextLine();
//        System.out.println("Please input salutation:");
//        Salutation salutation = Salutation.fromString(scanner.nextLine());
        Person person = new Person(MRS, "Adams", "Susi");
        personsRepository.create(person);
    }
}

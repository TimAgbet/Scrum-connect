package uk.co.huntersix.spring.rest.referencedata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import uk.co.huntersix.spring.rest.model.Person;


@Service
public class PersonDataService {

  private final List<Person> persons = new ArrayList<>();

  public PersonDataService() {
    persons.addAll(Arrays.asList(
            new Person("Mary", "Smith"),
            new Person("Brian", "Archer"),
            new Person("Collin", "Brown")));
  }

  public Optional<Person> findPerson(String lastName, String firstName) {
    return persons.stream()
            .filter(p -> p.getFirstName().equalsIgnoreCase(firstName)
                    && p.getLastName().equalsIgnoreCase(lastName))
            .findAny();
  }

  public List<Person> findPersonsByLastName(String lastName) {
    return persons.stream()
            .filter(p -> p.getLastName().equalsIgnoreCase(lastName))
            .collect(Collectors.toList());
  }

  public Person addPerson(String lastName, String firstName) {
    Person newPerson = new Person(lastName, firstName);
    if(!persons.contains(newPerson)) {
      persons.add(newPerson);
    }
    return newPerson;
  }

}

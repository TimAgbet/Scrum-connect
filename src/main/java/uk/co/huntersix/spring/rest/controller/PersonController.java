package uk.co.huntersix.spring.rest.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import uk.co.huntersix.spring.rest.model.Person;
import uk.co.huntersix.spring.rest.referencedata.PersonDataService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class PersonController {

  private final PersonDataService personDataService;

  public PersonController(@Autowired PersonDataService personDataService) {
    this.personDataService = personDataService;
  }

  @GetMapping("/person/{lastName}/{firstName}")
  public ResponseEntity<Person> getPerson(@PathVariable String lastName, @PathVariable String firstName) {
    return personDataService.findPerson(lastName, firstName)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found!"));
  }

  @GetMapping("/person/{lastName}")
  public ResponseEntity<List<Person>> getPersonsByLastName(@PathVariable String lastName) {
    List<Person> persons = personDataService.findPersonsByLastName(lastName);
    if(persons.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No persons found with last name " + lastName);
    }
    return ResponseEntity.ok(persons);
  }

  @PostMapping("/person")
  public ResponseEntity<Person> addPerson(@RequestParam String lastName, @RequestParam String firstName) {
    return ResponseEntity.ok(Objects.requireNonNull(personDataService.addPerson(lastName, firstName)));
  }
}
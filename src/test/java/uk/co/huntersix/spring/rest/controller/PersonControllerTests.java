package uk.co.huntersix.spring.rest.controller;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.huntersix.spring.rest.model.Person;
import uk.co.huntersix.spring.rest.referencedata.PersonDataService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PersonDataService personDataService;

  @Test
  public void getPerson_whenExists_returnsPerson() throws Exception {
    givenPersonReturningFromService("Mary", "Smith");

    mockMvc.perform(get("/person/smith/mary"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").value("Mary"));
  }

  @Test
  public void getPerson_whenNotExists_returnsNotFound() throws Exception {
    givenPersonReturningFromService(null, null);

    mockMvc.perform(get("/person/smith/john"))
            .andExpect(status().isNotFound());
  }

  private void givenPersonReturningFromService(String firstName, String lastName) {
    Person person = StringUtils.isNotBlank(firstName) ? new Person(firstName, lastName) : null;
    when(personDataService.findPerson(any(), any()))
            .thenReturn(Optional.ofNullable(person));
  }
}
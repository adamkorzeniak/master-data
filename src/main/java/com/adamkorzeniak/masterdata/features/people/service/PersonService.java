package com.adamkorzeniak.masterdata.features.people.service;

import com.adamkorzeniak.masterdata.features.people.model.Person;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PersonService {

    List<Person> searchPersons(Map<String, String> allRequestParams);

    Optional<Person> findPersonById(Long id);

    Person addPerson(Person person);

    Person updatePerson(Long id, Person person);

    void deletePerson(Long id);

    boolean isPersonExist(Long id);
}

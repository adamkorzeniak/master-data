package com.adamkorzeniak.masterdata.features.people.service;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.filter.FilterExpression;
import com.adamkorzeniak.masterdata.api.order.OrderExpression;
import com.adamkorzeniak.masterdata.features.people.model.Person;
import com.adamkorzeniak.masterdata.features.people.repository.PersonRepository;
import com.adamkorzeniak.masterdata.persistence.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final ApiQueryService apiQueryService;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository,
                            ApiQueryService apiQueryService) {
        this.personRepository = personRepository;
        this.apiQueryService = apiQueryService;
    }

    @Override
    public List<Person> searchPersons(Map<String, String> queryParams) {
        FilterExpression filterExpression = apiQueryService.buildFilterExpression(queryParams);
        OrderExpression orderExpression = apiQueryService.buildOrderExpression(queryParams);
        Specification<Person> spec = new GenericSpecification<>(filterExpression, orderExpression);
        return personRepository.findAll(spec);
    }

    @Override
    public Optional<Person> findPersonById(Long id) {
        return personRepository.findById(id);
    }

    @Override
    public Person addPerson(Person person) {
        person.setId(-1L);
        return personRepository.save(person);
    }

    @Override
    public Person updatePerson(Long id, Person person) {
        person.setId(id);
        return personRepository.save(person);
    }

    @Override
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public boolean isPersonExist(Long id) {
        return personRepository.existsById(id);
    }
}

package com.island.jpa_test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class JpaTestApplicationTests {
    @Autowired
    PersonRepository personRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testJPA() {
        Person person = new Person();
        person.setName("tommy");
        person.setPassword("9527");

        Set<Country> countrySet = new HashSet<>();
        Country country = new Country();
        country.setCountry_name("france");
        countrySet.add((country));
        person.setCountries(countrySet);


//        PersonInfo personInfo = new PersonInfo();
//        BeanUtils.copyProperties(person, personInfo);
//        System.out.println(personInfo.toString());
        personRepository.save(person);
    }

    @Test
    void repositoryTest() {
        personRepository.findByName("james thx").forEach(s -> System.out.println(s.getId()));
    }

    @Test
    void sqlTest() {
        personRepository.getPersonByName("james thx").forEach(s -> System.out.println(s.toString()));
    }

    @Transactional
    @Test
    void mutliTest() {
        personRepository.findAll().forEach(s -> System.out.println(s.getName()));
        personRepository.findAll().forEach(s -> s.getCountries().forEach(e -> System.out.println(e.getCountry_name())));
    }
}

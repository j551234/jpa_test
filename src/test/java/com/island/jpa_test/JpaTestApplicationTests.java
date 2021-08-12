package com.island.jpa_test;

import com.island.jpa_test.entity.Account;
import com.island.jpa_test.entity.Country;
import com.island.jpa_test.entity.Job;
import com.island.jpa_test.entity.Person;
import com.island.jpa_test.repository.AccountRepository;
import com.island.jpa_test.repository.CountryRepository;
import com.island.jpa_test.repository.JobRepository;
import com.island.jpa_test.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JpaTestApplicationTests {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    JobRepository jobRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void oneToOneSingleSaveTest() {
        Person tommy = new Person();
        tommy.setName("gimmy");
        tommy.setInfo("8591");


        Account account = new Account();
        account.setPassword("orm so ez ");
        account.setDescription("rrrrrrr");

        tommy.setAccount(account);
        personRepository.save(tommy);
    }

    @Test
    void AccountTest() {
        System.out.println(accountRepository.findById(0).get().getPerson().getName());
    }


    @Test
    void updateTest() {
        Person person = personRepository.findByName("gimmy");
        person.setInfo("8591");
        person.getAccount().setPassword("7777777");
        person.getAccount().setDescription("rrrrrrrrrrr");

        personRepository.save(person);
    }


    @Test
    void sqlTest() {
        personRepository.getPersonByName("james thx").forEach(s -> System.out.println(s.toString()));
    }

    @Test
    void countryTest() {
        Country country = new Country();
        country.setCountry_name("aa");
        countryRepository.save(country);
    }

    @Test
    void jobTest() {
        Job doctor = new Job();
        doctor.setInfo("heal people");
        doctor.setName("doctor");
        jobRepository.save(doctor);
    }

}

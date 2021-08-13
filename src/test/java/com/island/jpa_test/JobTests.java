package com.island.jpa_test;

import com.island.jpa_test.entity.Job;
import com.island.jpa_test.entity.Person;
import com.island.jpa_test.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class JobTests {
    @Autowired
    PersonRepository personRepository;

    @Test
    void jobTest() {
        Person james = new Person();
        james.setName("james");
        james.setInfo("im a engineer");

        List<Job> jobList = new ArrayList<>();
        Job engineer = new Job();
        engineer.setName("engineer");
        engineer.setInfo("coding ");
        jobList.add(engineer);

        Job doctor = new Job();
        doctor.setName("doctor");
        doctor.setInfo("heal ");
        jobList.add(doctor);

        james.setJobList(jobList);
        personRepository.save(james);
    }

    @Test
    void deleteJob(){
        personRepository.deleteById(62);
    }
}

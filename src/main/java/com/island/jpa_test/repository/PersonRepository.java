package com.island.jpa_test.repository;

import com.island.jpa_test.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByName(String name);

    @Query(value = "select * from person p where p.name = ?1", nativeQuery = true)
    List<Person> getPersonByName(String name);


}

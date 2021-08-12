package com.island.jpa_test.repository;

import com.island.jpa_test.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City,Integer> {
}

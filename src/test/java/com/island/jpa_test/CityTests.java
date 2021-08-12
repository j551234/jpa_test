package com.island.jpa_test;

import com.island.jpa_test.entity.City;
import com.island.jpa_test.entity.Country;
import com.island.jpa_test.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CityTests {
    @Autowired
    CountryRepository countryRepository;

    // if use mapped to new an object must to set your mapping by object into your many to one object
    @Test
    void countAddTest() {
        Country taiwan = new Country();
        taiwan.setCountry_name("taiwan");
        List<City> cityList = new ArrayList<>();
        City ha = new City();
        ha.setCity_name("ha");
        ha.setDescription("ahhahaa");
        //set the country and city relation
        ha.setCountry(taiwan);

        City hi = new City();
        hi.setCity_name("hi");
        hi.setDescription("hihihhi");
        //set the country and city relation
        hi.setCountry(taiwan);

        cityList.add(ha);
        cityList.add(hi);

        taiwan.setCities(cityList);
        countryRepository.save(taiwan);
    }

    @Test
    void useOwnFunctionToSet() {
        Country taiwan = new Country();
        taiwan.setCountry_name("taiwan");
        List<City> cityList = new ArrayList<>();
        City a = new City();
        a.setCity_name("a");
        a.setDescription("aaaaaaa");
        //set the country and city relation
        taiwan.addCity(a);


        City he = new City();
        he.setCity_name("he");
        he.setDescription("hehehehehehe");
        //set the country and city relation
        taiwan.addCity(he);

        taiwan.setCities(cityList);
        countryRepository.save(taiwan);
    }

}

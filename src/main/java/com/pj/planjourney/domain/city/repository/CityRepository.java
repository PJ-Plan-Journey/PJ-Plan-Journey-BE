package com.pj.planjourney.domain.city.repository;

import com.pj.planjourney.domain.city.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByName(String city);
}

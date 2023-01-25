package com.murat.taskback.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.murat.taskback.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
	List<City> findAllByNameContainingIgnoreCase(String name);
}

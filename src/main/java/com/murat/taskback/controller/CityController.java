package com.murat.taskback.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.murat.taskback.model.City;
import com.murat.taskback.repository.CityRepository;


@CrossOrigin(origins = "*") // to avoid browser's Cross-Origin Resource Sharing (CORS)
@RestController
public class CityController {

	@Autowired
	private CityRepository cityRepository;

	// returns all city entities in the cities table
	@GetMapping("/api/cities")
	public Page<City> getAllCities(Pageable pageable) {
		return cityRepository.findAll(pageable);
	}

	// returns cities from cities table which involve the search text
	@GetMapping("/api/search")
	public Page<City> searchCities(@RequestParam String name) {
		// find all cities in the repository that name of the city contains the search term, ignoring case
		List<City> citiList = cityRepository.findAllByNameContainingIgnoreCase(name);
		Pageable defaultPage = PageRequest.of(0, 10);
		return new PageImpl<City>(citiList, defaultPage, citiList.size());
	}

	// updates the city having id equals in put parameter id
	// secured by spring security ROLE_ALLOW_EDIT
	@CrossOrigin
	@PutMapping("/api/{id}")
	public City updateCity(@PathVariable int id, @RequestBody City city) {

		// retrieve the city to update from the repository
		City existingCity = cityRepository.findById(id).orElse(null);
		if (existingCity == null) {
			return null;
		} else {
			// update the city properties
			existingCity.setName(city.getName());
			existingCity.setPhoto(city.getPhoto());
			// save the updated city to the repository
			return cityRepository.save(existingCity);
		}
	}

}

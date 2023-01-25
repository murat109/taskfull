package com.murat.taskback.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.murat.taskback.model.City;
import com.murat.taskback.repository.CityRepository;

@Service
public class CityService {
	private final CityRepository cityRepository;
	
	public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public Page<City> getCities(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return cityRepository.findAll(pageable);
    }

}

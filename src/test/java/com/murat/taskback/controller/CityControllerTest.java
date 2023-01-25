/**
 * 
 */
package com.murat.taskback.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.murat.taskback.model.City;
import com.murat.taskback.repository.CityRepository;

/**
 * @author murat
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
class CityControllerTest {
	
	@Autowired
    private MockMvc mvc;
	

	
	@MockBean
    private CityRepository cityRepository;

	/**
	 * Test method for {@link com.murat.taskback.controller.CityController#getAllCities(org.springframework.data.domain.Pageable)}.
	 * @throws Exception 
	 */
	@Test
	void testGetAllCities() throws Exception {
//		Pageable pageable = PageRequest.of(0, 10);
//      Page<City> pageOfCities = new PageImpl<>(Arrays.asList(new City(1, "Ankara", "1.jpg"), new City(1, "Hatay", "2.jpg")));
//      when(cityRepository.findAll(pageable)).thenReturn(pageOfCities);
//      mvc.perform(MockMvcRequestBuilders
//	  			.get("/api/cities")
//	  			.accept(MediaType.ALL))
//					.andExpect(status().isOk());
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.murat.taskback.controller.CityController#searchCities(java.lang.String)}.
	 */
	@Test
	void testSearchCities() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.murat.taskback.controller.CityController#updateCity(int, com.murat.taskback.model.City)}.
	 */
	@Test
	void testUpdateCity() throws Exception {
		fail("Not yet implemented");
//		 	City city = new City(1, "Ankara", "1.jpg");
//	        city.setId(1);
//	        when(cityRepository.findById(1)).thenReturn(java.util.Optional.of(city));
//	        when(cityRepository.save(city)).thenReturn(city);
//
//	        ResultActions resultActions = mvc.perform(put("/api/1").contentType(MediaType.APPLICATION_JSON)
//	        		        .content("{\"username\": \"admin\", \"password\":\"editor\"}"));
//	        resultActions.andExpect(status().isOk());
	}

}

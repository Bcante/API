package org.miage.m2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.miage.m2.boundary.TacheRepresentation;
import org.miage.m2.boundary.TacheRessource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.RequestResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TacheServiceApplicationTests {

	@Autowired
	private MockMvc mvc;
	
	@Test
	public void getTaches() {
		try {
			mvc.perform(get("/taches"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$._embedded.taches[0].nom", is("coiffeur")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getTacheByEtatCourant() {
		try {
			mvc.perform(get("/taches?etat=nouveau"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$._embedded.taches[0].nom", is("assurance")))
		    .andExpect(jsonPath("$._embedded.taches", hasSize(2)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getTacheByUnknownEtatCourant() {
		try {
			mvc.perform(get("/taches?etat=unknown"))
			.andExpect(status().isOk());
			// TODO test el rendu
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getTacheById() {
		//http://localhost:8082/taches/de7d9052-4961-4b4f-938a-3cd12cbe1f82
		try {
			mvc.perform(get("/taches/de7d9052-4961-4b4f-938a-3cd12cbe1f82"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.nom", is("coiffeur")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void getTacheById404() {
		try {
			mvc.perform(get("/taches/zzde7d9052-4961-4b4f-938a-3cd12cbe1f82"))
			.andExpect(status().isNotFound());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void postTaches() {
		try {
			this.mvc.perform(post("/taches")
			        .contentType(MediaType.APPLICATION_JSON)
			        .content("{\"nom\":\"bob\"}"))
			        .andExpect(status().is2xxSuccessful());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void putTachesEtatNonAcheve() {
		
	}
	
	@Test
	public void putTachesEtatAcheve() {
		
	}
	
	@Test
	public void postTacheNvParticipant() {
		
	}
	
	@Test
	public void getTacheParticipants() {
		
	}
	
	@Test
	public void getTacheParticipant() {
		
	}
	
	@Test
	public void getTacheDeleteParticipant() {
		
	}
	
	@Test
	public void deleteTache() {
		
	}

}

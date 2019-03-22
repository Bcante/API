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
	
	/*@Test
	public void getTaches() {
		try {
			mvc.perform(get("/taches"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$._embedded.taches[0].nom", is("assurance")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
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
	public void getTacheByIdBadToken() {
		try {
			mvc.perform(get("/taches/de7d9052-4961-4b4f-938a-3cd12cbe1f82")
					.header("token", "ab"))
			.andExpect(status().isUnauthorized());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getTacheById() {
		//http://localhost:8082/taches/de7d9052-4961-4b4f-938a-3cd12cbe1f82
		try {
			mvc.perform(get("/taches/de7d9052-4961-4b4f-938a-3cd12cbe1f82")
					.header("token", "a"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.nom", is("conseil de classe")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void getTacheById404() {
		try {
			mvc.perform(get("/taches/geionjoqef-4961-4b4f-938a-3cd12cbe1f82"))
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
			        .andExpect(status().isCreated());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void putTachesBadToken() {
		try {
			mvc.perform(put("/taches/de7d9052-4961-4b4f-938a-3cd12cbe1f82")
					.header("token", "ab")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"nom\":\"bob\"}"))
			.andExpect(status().isUnauthorized());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void putTachesEtatNonAcheve() {
		try {
			mvc.perform(put("/taches/de7d9052-4961-4b4f-938a-3cd12cbe1f82")
					.header("token", "a")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"nom\":\"bob\"}"))
			.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void putTachesEtatAcheve() {
		try {
			mvc.perform(put("/taches/de7d9052-4961-4b4f-938a-3cd12cbe1f85")
					.header("token", "a")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"nom\":\"bob\"}"))
			.andExpect(status().isNotAcceptable());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void postTacheNvParticipant() {
		try {
			mvc.perform(post("/taches/de7d9052-4961-4b4f-938a-3cd12cbe1f83/de7d9052-4961-4b4f-938a-3cd12cbe1f81")
					.header("token", "a"))
			.andExpect(status().isCreated());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void postTacheNvParticipantEtatAchevee() {
		try {
			mvc.perform(post("/taches/de7d9052-4961-4b4f-938a-3cd12cbe1f85/de7d9052-4961-4b4f-938a-3cd12cbe1f81")
					.header("token", "a"))
			.andExpect(status().isNotAcceptable());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void postTacheNvParticipantBadToken() {
		try {
			mvc.perform(post("/taches/de7d9052-4961-4b4f-938a-3cd12cbe1f82/de7d9052-4961-4b4f-938a-3cd12cbe1f81")
					.header("token", "ab"))
			.andExpect(status().isUnauthorized());
		} catch (Exception e) {
			// T22ODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getTacheParticipants() {
		try {
			mvc.perform(post("/taches/de7d9052-4961-4b4f-938a-3cd12cbe1f83/de7d9052-4961-4b4f-938a-3cd12cbe1f81")
					.header("token", "a"));
			mvc.perform(get("/de7d9052-4961-4b4f-938a-3cd12cbe1f83/participants")
					.header("token", "a"))
			.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getTacheParticipantsBadToken() {
		try {
			mvc.perform(post("/taches/de7d9052-4961-4b4f-938a-3cd12cbe1f83/de7d9052-4961-4b4f-938a-3cd12cbe1f81")
					.header("token", "a"));
			mvc.perform(get("/de7d9052-4961-4b4f-938a-3cd12cbe1f83/participants")
					.header("token", "ab"))
			.andExpect(status().isUnauthorized());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void getTacheParticipant() {
		try {
			
			mvc.perform(get("/taches/participants")
					.header("token", "a"))
			.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getTacheParticipantBadToken() {
		
	}
	
	@Test
	public void deleteTache() {
		try {
			mvc.perform(delete("/taches/de7d9052-4961-4b4f-938a-3cd12cbe1f82")
					.header("token", "a"))
			.andExpect(status().isNoContent());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void deleteTacheBadToken() {
		try {
			mvc.perform(delete("/taches/de7d9052-4961-4b4f-938a-3cd12cbe1f82")
					.header("token", "ab"))
			.andExpect(status().isUnauthorized());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void getTacheDeleteParticipant() {
		try {
			mvc.perform(delete("/taches/de7d9052-4961-4b4f-938a-3cd12cbe1f82/participants/de7d9052-4961-4b4f-938a-3cd12cbe1f81")
					.header("token", "a"))
			.andExpect(status().isNoContent());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void getTacheDeleteParticipantBadToken() {
		try {
			mvc.perform(delete("/taches/de7d9052-4961-4b4f-938a-3cd12cbe1f82/participants/de7d9052-4961-4b4f-938a-3cd12cbe1f81")
					.header("token", "ab"))
			.andExpect(status().isUnauthorized());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}

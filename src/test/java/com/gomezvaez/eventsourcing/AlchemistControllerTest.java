package com.gomezvaez.eventsourcing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gomezvaez.eventsourcing.api.CreateAlchemistRequest;
import com.gomezvaez.eventsourcing.api.RegisterActivityRequest;
import com.gomezvaez.eventsourcing.api.RegisterExpenseRequest;
import com.gomezvaez.eventsourcing.eventstore.EventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AlchemistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void testHappyPath() throws Exception {
        // Créer un alchimiste
        CreateAlchemistRequest createAlchemistRequest = new CreateAlchemistRequest("Test Alchemist", "test@example.com");
        String alchemistId = mockMvc.perform(post("/alchemists").contentType("application/json").content(objectMapper.writeValueAsString(createAlchemistRequest))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        // Ajouter une activité +30
        RegisterActivityRequest registerActivityRequest = new RegisterActivityRequest(new Date(), "Test Activity", 30);
        String activityId = mockMvc.perform(post("/alchemists/" + alchemistId + "/activities").contentType("application/json").content(objectMapper.writeValueAsString(registerActivityRequest))).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // Le solde reste à 0, perles en cours de validatin à 30
        mockMvc.perform(get("/alchemists/" + alchemistId)).andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(0))
                .andExpect(jsonPath("$.upcoming-operations").value(30));


        // On valide l'activité
        mockMvc.perform(post("/alchemists/" + alchemistId + "/activities/" + activityId).contentType("application/json").content(objectMapper.writeValueAsString(registerActivityRequest))).andExpect(status().isOk());

        // Le solde passe à 30, les perles en cours de validation à 0
        mockMvc.perform(get("/alchemists/" + alchemistId)).andExpect(status().isOk())
                // Le solde reste à 0, perles en cours de validation à 30
                .andExpect(jsonPath("$.balance").value(30))
                .andExpect(jsonPath("$.upcoming-operations").value(0));

        // Ajouter une dépense de -20
        RegisterExpenseRequest registerExpenseRequest = new RegisterExpenseRequest(new Date(), "Test Spend Pearls", 20);
        mockMvc.perform(post("/alchemists/" + alchemistId + "/expenses").contentType("application/json").content(objectMapper.writeValueAsString(registerExpenseRequest))).andExpect(status().isOk());

        // Le solde reste à 00, perles en cours de validation à -20
        // On valide la dépense

        // Le solde passe à 10, les perles en cours de validation à 0
        mockMvc.perform(get("/alchemists/" + alchemistId)).andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(10));
    }

    @Test
    public void testCreationsAndListing() throws Exception {
        eventRepository.deleteAll();

        CreateAlchemistRequest createAlchemistRequest = new CreateAlchemistRequest("Test Alchemist", "test@example.com");
        String alchemistId = mockMvc.perform(post("/alchemists").contentType("application/json").content(objectMapper.writeValueAsString(createAlchemistRequest))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        RegisterActivityRequest registerActivityRequest = new RegisterActivityRequest(new Date(), "Test Activity", 30);
        String activityId = mockMvc.perform(post("/alchemists/" + alchemistId + "/activities").contentType("application/json").content(objectMapper.writeValueAsString(registerActivityRequest))).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        mockMvc.perform(get("/alchemists/" + alchemistId)).andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(30))
                .andExpect(jsonPath("$.activities[0].activityId").isNotEmpty());

        // Récupérer tous les Alchimistes en en ajoutant second
        mockMvc.perform(post("/alchemists").contentType("application/json").content(objectMapper.writeValueAsString(createAlchemistRequest))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        ResultActions alchemistListResult = mockMvc.perform(get("/alchemists")).andExpect(status().isOk());
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectMapper.readValue(alchemistListResult
                .andReturn().getResponse().getContentAsString(), Object.class)));
        alchemistListResult
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testCannotAddActivitiesNorExpensesOnUknownAlchimists() throws Exception {
        ResultActions alchemistResult = mockMvc.perform(get("/alchemists/" + "00000-00"));
        alchemistResult.andExpect(status().isNotFound());

        RegisterActivityRequest registerActivityRequest = new RegisterActivityRequest(new Date(), "Test Activity", 30);
        mockMvc.perform(post("/alchemists/" + "00000-00" + "/activities").contentType("application/json").content(objectMapper.writeValueAsString(registerActivityRequest)))
                .andExpect(status().isNotFound());

        RegisterExpenseRequest registerExpenseRequest = new RegisterExpenseRequest(new Date(), "Test Spend Pearls", 20);
        mockMvc.perform(post("/alchemists/" + "00000-00" + "/expenses").contentType("application/json").content(objectMapper.writeValueAsString(registerExpenseRequest)))
                .andExpect(status().isNotFound());

    }

    //Verifier qu'on ne peut pas valider une activité qui est à un.e autre alchimiste
    //Vérifier qu'on ne peux valider expense et activity que si on a les bons droits
}
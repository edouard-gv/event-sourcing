package com.gomezvaez.eventsourcing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gomezvaez.eventsourcing.api.CreateAlchemistRequest;
import com.gomezvaez.eventsourcing.api.RegisterActivityRequest;
import com.gomezvaez.eventsourcing.api.RegisterExpenseRequest;
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

    @Test
    public void testCreateAlchemistAndAddActivityAndSpending() throws Exception {
        // Créer un alchimiste
        CreateAlchemistRequest createAlchemistRequest = new CreateAlchemistRequest("Test Alchemist", "test@example.com");
        String alchemistId = mockMvc.perform(post("/alchemists").contentType("application/json").content(objectMapper.writeValueAsString(createAlchemistRequest))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        // Ajouter une activité +30
        RegisterActivityRequest registerActivityRequest = new RegisterActivityRequest(new Date(), "Test Activity", 30);
        mockMvc.perform(post("/alchemists/" + alchemistId + "/activities").contentType("application/json").content(objectMapper.writeValueAsString(registerActivityRequest))).andExpect(status().isOk());

        // Ajouter un spending -20
        RegisterExpenseRequest registerExpenseRequest = new RegisterExpenseRequest(new Date(), "Test Spend Pearls", 20);
        mockMvc.perform(post("/alchemists/" + alchemistId + "/expenses").contentType("application/json").content(objectMapper.writeValueAsString(registerExpenseRequest))).andExpect(status().isOk());

        ResultActions alchemistResult = mockMvc.perform(get("/alchemists/" + alchemistId)).andExpect(status().isOk());
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectMapper.readValue(alchemistResult
                .andReturn().getResponse().getContentAsString(), Object.class)));
        alchemistResult.andExpect(jsonPath("$.balance").value(10));

        // Récupérer tous les Alchimistes
        mockMvc.perform(post("/alchemists").contentType("application/json").content(objectMapper.writeValueAsString(createAlchemistRequest))).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        mockMvc.perform(get("/alchemists")).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

    }
}
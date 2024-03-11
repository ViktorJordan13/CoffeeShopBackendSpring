package com.codebrains.coffeeshopbackendspring.controllers;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
import com.codebrains.coffeeshopbackendspring.entities.Coffee;
import com.codebrains.coffeeshopbackendspring.entities.CoffeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CoffeeController.class)
public class CoffeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CoffeeRepository coffeeRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGetCoffees() throws Exception {
        Coffee coffee = new Coffee(1L, "Espresso", 2, 0, 1, false, false);
        given(coffeeRepository.findAll()).willReturn(Arrays.asList(coffee));

        mockMvc.perform(get("/coffees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Espresso")));
    }

    @Test
    void testGetCoffee() throws Exception {
        Coffee coffee = new Coffee(1L, "Espresso", 2, 0, 1, false, false);
        given(coffeeRepository.findById(1L)).willReturn(Optional.of(coffee));

        mockMvc.perform(get("/coffees/{coffeeId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Espresso")));
    }

    @Test
    void testNewCoffee() throws Exception {
        Coffee coffeeToSave = new Coffee(null, "Latte", 1, 2, 0, true, false);
        Coffee savedCoffee = new Coffee(1L, "Latte", 1, 2, 0, true, false);

        given(coffeeRepository.save(Mockito.<Coffee>any())).willReturn(savedCoffee);

        mockMvc.perform(post("/coffees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coffeeToSave)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Latte")))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.coffeeDoses", is(1)))
                .andExpect(jsonPath("$.milkDoses", is(2)))
                .andExpect(jsonPath("$.sugarPacks", is(0)))
                .andExpect(jsonPath("$.cream", is(true)))
                .andExpect(jsonPath("$.selected", is(false)));
    }

    @Test
    void testUpdateCoffee() throws Exception {
        Coffee existingCoffee = new Coffee(1L, "Espresso", 2, 0, 1, false, false);
        Coffee updatedCoffee = new Coffee(1L, "Espresso", 3, 1, 2, true, false);

        given(coffeeRepository.findById(eq(1L))).willReturn(Optional.of(existingCoffee));
        given(coffeeRepository.save(Mockito.<Coffee>any())).willReturn(updatedCoffee);

        mockMvc.perform(put("/coffees/{coffeeId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCoffee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.coffeeDoses", is(3)))
                .andExpect(jsonPath("$.milkDoses", is(1)))
                .andExpect(jsonPath("$.sugarPacks", is(2)))
                .andExpect(jsonPath("$.cream", is(true)));
    }

    @Test
    void testDeleteCoffee() throws Exception {
        mockMvc.perform(delete("/coffees/{coffeeId}", 1L))
                .andExpect(status().isOk());
    }
}

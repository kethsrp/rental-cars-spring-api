package com.challenge.rental_cars_spring_api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarrosRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testListarCarros() throws Exception {
        mockMvc.perform(get("/carros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].modelo").isNotEmpty());
    }
}

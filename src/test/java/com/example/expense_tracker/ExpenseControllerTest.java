package com.example.expense_tracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addExpense_returnsCreated() throws Exception {
        String payload = """
                {
                  "title": "Coffee",
                  "amount": 4.5,
                  "category": "Food",
                  "date": "%s"
                }
                """.formatted(LocalDate.now());

        mockMvc.perform(post("/api/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Coffee"))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void addExpense_rejectsNegativeAmount() throws Exception {
        String payload = """
                {
                  "title": "Bad expense",
                  "amount": -10.0,
                  "category": "Food",
                  "date": "%s"
                }
                """.formatted(LocalDate.now());

        mockMvc.perform(post("/api/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllExpenses_returnsOk() throws Exception {
        mockMvc.perform(get("/api/expenses"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteNonexistentExpense_returnsNotFound() throws Exception {
        mockMvc.perform(delete("/api/expenses/does-not-exist"))
                .andExpect(status().isNotFound());
    }
}
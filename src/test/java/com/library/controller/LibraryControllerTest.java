package com.library.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LibraryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCheckoutBook() throws Exception {
        mockMvc.perform(post("/api/library/checkoutBook")
                .param("userId", "1")
                .param("bookId", "2"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Book checked out successfully")));
    }

    @Test
    void testReturnBook() throws Exception {
        mockMvc.perform(post("/api/library/returnBook")
                .param("userId", "1")
                .param("bookId", "2"))
                .andExpect(status().isInternalServerError());

    }
}

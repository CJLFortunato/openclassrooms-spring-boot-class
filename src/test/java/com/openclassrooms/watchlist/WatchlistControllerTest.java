package com.openclassrooms.watchlist;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@WebMvcTest
@ExtendWith(SpringExtension.class)
public class WatchlistControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetWatchlist() throws Exception {
        mockMvc.perform(get("/watchlist"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("watchlist"))
        .andExpect(model().size(2))
        .andExpect(model().attributeExists("watchlistItems"))
        .andExpect(model().attributeExists("numberOfMovies"));
    }

    @Test
    public void testShowWatchlistItemForm() throws Exception {
        mockMvc.perform(get("/watchlistItemForm"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("watchlistItemForm"))
        .andExpect(model().size(1))
        .andExpect(model().attributeExists("watchlistItem"));

    }

    

    @Test
    public void testSubmitWatchlistItemForm() throws Exception {
        mockMvc.perform(post("/watchlistItemForm"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/watchlist"));
    }

}

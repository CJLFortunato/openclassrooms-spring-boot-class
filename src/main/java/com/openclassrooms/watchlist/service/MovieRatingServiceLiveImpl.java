package com.openclassrooms.watchlist.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tools.jackson.databind.node.ObjectNode;

@Service
@ConditionalOnProperty(name="app.environment", havingValue="prod")
public class MovieRatingServiceLiveImpl implements MovieRatingService {
    private final Logger logger = LoggerFactory.getLogger(MovieRatingServiceLiveImpl.class);
    String apiUrl = "http://www.omdbapi.com/?apikey=d971f933&t=";

    @Override
    public String getMovieRating(String title) {
        try {
            logger.info("OMDb API called with URL: {}", apiUrl + title);
            RestTemplate template = new RestTemplate();

            ResponseEntity<ObjectNode> response = template.getForEntity(apiUrl + title, ObjectNode.class);
            ObjectNode jsonObject = response.getBody();

            logger.debug("OMDb API response: {}", jsonObject);

            return jsonObject.path("imdbRating").asString();
        } catch (Exception e) {
            logger.warn("Something went wrong while calling OMDb API" + e.getMessage());
            return null;
        }
    }
}

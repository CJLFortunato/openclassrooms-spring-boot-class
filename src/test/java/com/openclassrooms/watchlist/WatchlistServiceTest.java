package com.openclassrooms.watchlist;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.watchlist.domain.WatchlistItem;
import com.openclassrooms.watchlist.repository.WatchlistRepository;
import com.openclassrooms.watchlist.service.MovieRatingService;
import com.openclassrooms.watchlist.service.WatchlistService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class WatchlistServiceTest {
    
    @InjectMocks
    private WatchlistService watchlistService;
    
    @Mock
    private WatchlistRepository watchlistRepositoryMock;
    
    @Mock
    private MovieRatingService movieRatingServiceMock;
    
    @Test
    public void testGetWatchlistItemsReturnsAllItemsFromRepository() {
        
        //Arrange
        WatchlistItem item1 = new WatchlistItem("Star Wars", "7.7", "M" , "");
        WatchlistItem item2 = new WatchlistItem("Star Trek", "8.8", "M" , "");
        List<WatchlistItem> mockItems = Arrays.asList(item1, item2);
        
        when(watchlistRepositoryMock.getList()).thenReturn(mockItems);
        
        //Act
        List<WatchlistItem> result = watchlistService.getWatchlistItems();
        
        //Assert
        assertTrue(result.size() == 2);
        assertTrue(result.get(0).getTitle().equals("Star Wars"));
        assertTrue(result.get(1).getTitle().equals("Star Trek"));
    }

    @Test
    public void testGetwatchlistItemsRatingFormOmdbServiceOverrideTheValueInItems() {

        //Arrange
        WatchlistItem item1 = new WatchlistItem("Star Wars", "7.7", "M" , "");
        List<WatchlistItem> mockItems = Arrays.asList(item1);

        when(watchlistRepositoryMock.getList()).thenReturn(mockItems);  
        when(movieRatingServiceMock.getMovieRating(any(String.class))).thenReturn("10");

        //Act
        List<WatchlistItem> result = watchlistService.getWatchlistItems();

        //Assert
        assertTrue(result.get(0).getRating().equals("10"));
    }
}
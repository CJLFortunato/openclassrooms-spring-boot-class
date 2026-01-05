package com.openclassrooms.watchlist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.watchlist.repository.WatchlistRepository;
import com.openclassrooms.watchlist.domain.WatchlistItem;
import com.openclassrooms.watchlist.exceptions.DuplicateTitleException;
import com.openclassrooms.watchlist.exceptions.WatchlistTooLongException;

@Service
public class WatchlistService {

    WatchlistRepository watchlistRepository;
    MovieRatingService movieRatingService;

    public WatchlistService (WatchlistRepository watchlistRepository, MovieRatingService movieRatingService) {
        super();
        this.watchlistRepository = watchlistRepository;
        this.movieRatingService = movieRatingService;
    }

    @Autowired
    public List<WatchlistItem> getWatchlistItems() {
        List<WatchlistItem> watchlistItems =  watchlistRepository.getList();
        for (WatchlistItem watchlistItem : watchlistItems) {
            String rating = movieRatingService.getMovieRating(watchlistItem.getTitle());

            if(rating != null) {
                watchlistItem.setRating(rating);
            }
        }

        return watchlistItems;
    }

    public int getWatchlistSize() {
        return watchlistRepository.getList().size();
    }

    public WatchlistItem findWatchlistItemById(Integer id) {
        return watchlistRepository.findById(id);
    }

    public void addOrUpdateWatchlistItem (WatchlistItem item) throws DuplicateTitleException, WatchlistTooLongException {
        WatchlistItem existingItem = findWatchlistItemById(item.getId());

        if (existingItem == null) {
            if (watchlistRepository.findByTitle(item.getTitle()) != null) {
                throw new DuplicateTitleException();
            }
            if (getWatchlistSize() > 5) {
                throw new WatchlistTooLongException();
            }
            item.setId(WatchlistItem.index++);
            watchlistRepository.addItem(item);
        }
        else {
            existingItem.setComment(item.getComment());
            existingItem.setPriority(item.getPriority());
            existingItem.setRating(item.getRating());
            existingItem.setTitle(item.getTitle());
        }
    }
}

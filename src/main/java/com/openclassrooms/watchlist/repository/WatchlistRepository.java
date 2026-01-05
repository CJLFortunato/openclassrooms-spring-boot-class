package com.openclassrooms.watchlist.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.watchlist.domain.WatchlistItem;

@Service
public class WatchlistRepository {
    private List<WatchlistItem> watchlistItems = new ArrayList<WatchlistItem>();

    public List<WatchlistItem> getList() {
        return watchlistItems;
    }

    public void addItem(WatchlistItem watchlistItem) {
        watchlistItems.add(watchlistItem);
    }

    public WatchlistItem findById(Integer id) {
        for (WatchlistItem item: watchlistItems) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public WatchlistItem findByTitle(String title) {
        for (WatchlistItem item: watchlistItems) {
            if (item.getTitle().equals(title)) {
                return item;
            }
        }

        return null;
    }
}

package com.openclassrooms.watchlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class WatchlistController {

    private List<WatchlistItem> watchlistItems = new ArrayList<WatchlistItem>();

    @GetMapping("/watchlist")
    public ModelAndView getWatchlist() {

        String viewName = "watchlist";
        Map<String, Object> model = new HashMap<String, Object>();
        
        model.put("watchlistItems", watchlistItems);
        model.put("numberOfMovies", watchlistItems.size());

        return new ModelAndView(viewName, model);
    }

    @GetMapping("/watchlistItemForm")
    public ModelAndView showWatchlistItemForm(@RequestParam(required = false) Integer id) {
        String viewName = "watchlistItemForm";
        
        Map<String,Object> model = new HashMap<String,Object>();
        WatchlistItem item = findWatchlistItemById(id);
        if (item == null) {
            model.put("watchlistItem", new WatchlistItem());
        }
        else {
            model.put("watchlistItem", item);
        }
        
        return new ModelAndView(viewName,model); 
    }

    private WatchlistItem findWatchlistItemById(Integer id) {
        for (WatchlistItem item: watchlistItems) {
            if (item.getId().equals(id)) {
                return item;
            }
        }

        return null;
    }

    @PostMapping("/watchlistItemForm")
    public ModelAndView submitForm(@Valid WatchlistItem item, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("watchlistItemForm");
        }

        WatchlistItem existingItem = findWatchlistItemById(item.getId());

        if (existingItem == null) {
            if (checkForExistingItem(item.getTitle())) {
                bindingResult.rejectValue("title", "", "You have already saved this movie in your watchlist");
                return new ModelAndView("watchlistItemForm");
            }
            item.setId(WatchlistItem.index++);
            watchlistItems.add(item);
            if (watchlistItems.size() >= 5) {
                bindingResult.rejectValue(null, "", "You have saved too many movies in your watchlist. Go watch some of them!");
                return new ModelAndView("watchlistItemForm");
            }
        }
        else {
            existingItem.setComment(item.getComment());
            existingItem.setPriority(item.getPriority());
            existingItem.setRating(item.getRating());
            existingItem.setTitle(item.getTitle());
        }
        RedirectView redirect = new RedirectView();
        redirect.setUrl("/watchlist");
        
        return new ModelAndView(redirect);
    }

    private boolean checkForExistingItem(String title) {
        for (WatchlistItem item: watchlistItems) {
            if (item.getTitle().equals(title)) {
                return true;
            }
        }

        return false;
    }
    
}

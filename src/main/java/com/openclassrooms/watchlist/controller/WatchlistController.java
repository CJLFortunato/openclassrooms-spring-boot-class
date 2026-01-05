package com.openclassrooms.watchlist.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.openclassrooms.watchlist.domain.WatchlistItem;
import com.openclassrooms.watchlist.exceptions.DuplicateTitleException;
import com.openclassrooms.watchlist.exceptions.WatchlistTooLongException;
import com.openclassrooms.watchlist.service.WatchlistService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class WatchlistController {

    private WatchlistService watchlistService;

    @Autowired
    public WatchlistController (WatchlistService watchlistService) {
        super();
        this.watchlistService = watchlistService;
    }

    @GetMapping("/watchlist")
    public ModelAndView getWatchlist() {

        String viewName = "watchlist";
        Map<String, Object> model = new HashMap<String, Object>();
        
        model.put("watchlistItems", watchlistService.getWatchlistItems());
        model.put("numberOfMovies", watchlistService.getWatchlistSize());

        return new ModelAndView(viewName, model);
    }

    @GetMapping("/watchlistItemForm")
    public ModelAndView showWatchlistItemForm(@RequestParam(required = false) Integer id) {
        String viewName = "watchlistItemForm";
        
        Map<String,Object> model = new HashMap<String,Object>();
        WatchlistItem item = watchlistService.findWatchlistItemById(id);
        if (item == null) {
            model.put("watchlistItem", new WatchlistItem());
        }
        else {
            model.put("watchlistItem", item);
        }
        
        return new ModelAndView(viewName,model); 
    }

    @PostMapping("/watchlistItemForm")
    public ModelAndView submitForm(@Valid WatchlistItem item, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("watchlistItemForm");
        }

        try {
            watchlistService.addOrUpdateWatchlistItem(item);
        }
        catch (DuplicateTitleException e) {
            bindingResult.rejectValue("title", "", "You have already saved this movie in your watchlist");
            return new ModelAndView("watchlistItemForm");
        }
        catch (WatchlistTooLongException e) {
            bindingResult.rejectValue(null, "", "You have saved too many movies in your watchlist. Go watch some of them!");
            return new ModelAndView("watchlistItemForm");
        }

        RedirectView redirect = new RedirectView();
        redirect.setUrl("/watchlist");
        
        return new ModelAndView(redirect);
    }
}

package com.openclassrooms.watchlist;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@GoodMovie
public class WatchlistItem {

    @NotBlank(message = "Please enter a title")
    private String title;
    @Rating
    private String rating;
    @Priority
    private String priority;
    @Size(max = 50, message = "Maximum 50 characters")
    private String comment;
    private Integer id;
    public static int index = 0;

    public WatchlistItem() {
        this.id = index ++;
    }


    public WatchlistItem(String title, String rating, String priority, String comment) {
        super();

        this.id = index ++;
        this.title = title;
        this.rating = rating;
        this.priority = priority;
        this.comment = comment;
    }


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }


    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }


    public String getPriority() {
        return priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }


    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
}

package com.udacity.jwdnd.course1.cloudstorage.model;

public class Notes {
    private Integer noteId;
    private String title;
    private String description;
    private Integer userId;

    public Notes(Integer noteId, String title, String description, Integer userId) {
        this.noteId = noteId;
        this.title = title;
        this.description = description;
        this.userId = userId;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

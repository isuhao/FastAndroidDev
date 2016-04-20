package com.example.myapplication.model;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "NOTE".
 */
public class Note {

    private Long id;
    private String text;
    private String comment;
    private String content;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Note() {
    }

    public Note(Long id) {
        this.id = id;
    }

    public Note(Long id, String text, String comment, String content) {
        this.id = id;
        this.text = text;
        this.comment = comment;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
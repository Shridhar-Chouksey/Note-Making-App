package com.example.notesapp;

import com.google.firebase.database.Exclude;

public class Note {

       String noteTitle,noteContent;

    @Exclude
       String noteId;

       public Note() {
           //this is required for firebase

       }


    public Note(String noteTitle, String noteContent) {
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
    }

    @Exclude
    public String getNoteId() {
        return noteId;
    }

    @Exclude
    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }



    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }
}


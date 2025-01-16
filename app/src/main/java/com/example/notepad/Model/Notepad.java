package com.example.notepad.Model;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import java.io.Serializable;

@Entity(tableName = "notepad", primaryKeys = {"title", "date"})
public class Notepad implements Serializable {
    @NonNull
    String title;
    @NonNull
    String content;
    @NonNull
    String date;


    public Notepad(String title,String content, String date){
        this.title=title;
        this.content=content;
        this.date=date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}

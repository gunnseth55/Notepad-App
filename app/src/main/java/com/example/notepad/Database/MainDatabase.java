package com.example.notepad.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.notepad.Model.Notepad;
import java.util.List;


@Dao
public interface MainDatabase {
@Insert
    void insert(Notepad notes);

@Delete
    void delete(Notepad note);
@Query("UPDATE notepad SET title = :title, content = :content, date = :date WHERE title = :originalTitle AND date = :originalDate")
    void update(String title, String content, String date, String originalTitle, String originalDate);

@Query("SELECT * FROM notepad")
    LiveData<List<Notepad>>getAllUsers();

}

package com.example.notepad.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notepad.Model.Notepad;


@Database(entities = {Notepad.class},version=2,exportSchema = false)
public abstract class Roomdb extends RoomDatabase{
private static  Roomdb database;
private static String DATABASE_NAME="Notepad";
public synchronized static Roomdb getInstance(Context context){
    if(database==null){
        database= Room.databaseBuilder(context.getApplicationContext(), Roomdb.class,DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();

    }
    return database;
}
public abstract MainDatabase maindao();
}

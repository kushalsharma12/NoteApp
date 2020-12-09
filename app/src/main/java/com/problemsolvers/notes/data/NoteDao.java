package com.problemsolvers.notes.data;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.problemsolvers.notes.Note;

import java.util.List;

@Dao
public interface NoteDao {
//CRUD
    @Insert()
    void insert(Note note);

    @Delete()
    void delete( Note note);

    @Update
    void update(Note note);

    @Query("DELETE FROM Notes_Table " )
    void deleteAllNotes();

    @Query("SELECT * FROM Notes_Table ORDER BY priority DESC")
    LiveData<List<Note>> getAllNotes();
}

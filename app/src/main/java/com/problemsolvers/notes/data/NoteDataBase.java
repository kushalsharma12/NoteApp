package com.problemsolvers.notes.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.problemsolvers.notes.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDataBase extends RoomDatabase {

    public static volatile NoteDataBase INSTANCE;
    public abstract NoteDao noteDao();


    public static NoteDataBase getINSTANCE(final Context context) {
        if (INSTANCE == null) {
            synchronized (NoteDataBase.class) {
                if (INSTANCE == null) {
                    //create our db
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NoteDataBase.class, "note_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulatedbAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulatedbAsyncTask extends AsyncTask<Void,Void,Void>{
        private NoteDao noteDao;
        private PopulatedbAsyncTask(NoteDataBase db){
            noteDao = db.noteDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title 1","Description 1",1));
            noteDao.insert(new Note("Title 2","Description 2",2));
            noteDao.insert(new Note("Title 3","Description 3",3));
            return null;
        }
    }
}

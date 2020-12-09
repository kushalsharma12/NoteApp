package com.problemsolvers.notes.util;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.problemsolvers.notes.Note;
import com.problemsolvers.notes.data.NoteDao;
import com.problemsolvers.notes.data.NoteDataBase;

import java.util.List;

public class NoteReopsitory {
    private LiveData<List<Note>> allNotes;
    private NoteDao noteDao;


    public NoteReopsitory(Application application) {

        NoteDataBase db = NoteDataBase.getINSTANCE(application);
        noteDao = db.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public void insert(Note note) {
        new insertAsyncTask(noteDao).execute(note);

    }

    public void update(Note note) {
        new UpdateAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note) {
        new deleteAsyncTask(noteDao).execute(note);

    }

    public void deleteAll() {
        new DeleteAllAsyncTask(noteDao).execute();

    }
    //these above methods are the apis that our repository exposes outside
    // so our viewModel only have to call these methods and that viewModel
    // will not care that what are in that methods ,how the data is fetch in these methods.

    private static class insertAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        public insertAsyncTask(NoteDao notedao) {
            this.noteDao = notedao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        public UpdateAsyncTask(NoteDao notedao) {
            this.noteDao = notedao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }
    private static class deleteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        public deleteAsyncTask(NoteDao notedao) {
            this.noteDao = notedao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }
    private static class DeleteAllAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        public DeleteAllAsyncTask(NoteDao notedao) {
            this.noteDao = notedao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.deleteAllNotes();
            return null;
        }
    }

}
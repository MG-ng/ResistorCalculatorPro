package website.dango.resistor;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Repository {

    private ResistorDao mResistorDao;
    private LiveData<List<Resistor>> mAllResistors;

    Repository ( Application application ) {

        ResistorRoomDatabase db = ResistorRoomDatabase.getDatabase( application );
        mResistorDao = db.resistorDao();
        mAllResistors = mResistorDao.getAllResistorsByResistance();

    }

    LiveData<List<Resistor>> getAllResistors() {
        return mAllResistors;
    }

    void deleteResistorByResistance( int id ) {
        new deleteAsyncTask( mResistorDao ).execute( id );
    }

    private static class deleteAsyncTask extends AsyncTask<Integer, Void, Void> {

        private ResistorDao mAsyncTaskDao;

        deleteAsyncTask( ResistorDao dao ) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            mAsyncTaskDao.deleteResistor( params[ 0 ] );
            return null;
        }
    }


    public void insert( Resistor resistor ) {
        new insertAsyncTask( mResistorDao ).execute( resistor );
    }

    private static class insertAsyncTask extends AsyncTask<Resistor, Void, Void> {

        private ResistorDao mAsyncTaskDao;

        insertAsyncTask( ResistorDao dao ) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Resistor... params) {
            mAsyncTaskDao.insert( params[ 0 ] );
            return null;
        }
    }



}

package website.dango.resistor;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database( entities = { Resistor.class}, version = 3 )
public abstract class ResistorRoomDatabase extends RoomDatabase {


    public abstract ResistorDao resistorDao();

    private static ResistorRoomDatabase INSTANCE;

    public static ResistorRoomDatabase getDatabase( final Context context ) {
        if( INSTANCE == null ) {
            synchronized( ResistorRoomDatabase.class ) {
                if( INSTANCE == null ) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder( context.getApplicationContext(),
                            ResistorRoomDatabase.class, "resistor_database" )
                            .fallbackToDestructiveMigration()
                            .addCallback( sRoomDatabaseCallback )
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen( db );
                    new PopulateDbAsync( INSTANCE ).execute();
                }

            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ResistorDao mDao;

        PopulateDbAsync( ResistorRoomDatabase db ) {
            mDao = db.resistorDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            /* Example Data
            Resistor resistor = new Resistor( 200 );
            mDao.insert( resistor );
            resistor = new Resistor( 500 );
            mDao.insert( resistor );
            */
            return null;
        }
    }

}

package website.dango.resistor;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ResistorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    void insert( Resistor resistor );

    @Query("DELETE FROM resistor_table")
    void deleteAll();

    @Query("DELETE FROM resistor_table WHERE id == :id")
    void deleteResistor( int id );

    @Query("SELECT * FROM resistor_table ORDER BY resistance ASC")
    LiveData<List<Resistor>> getAllResistorsByResistance();

    @Query("SELECT * FROM resistor_table WHERE resistance == :resistance")
    Resistor getResistorByResistance( int resistance );


}

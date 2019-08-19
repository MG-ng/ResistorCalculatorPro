package website.dango.resistor;


import androidx.room.TypeConverter;

import java.sql.Timestamp;



public class ConverterTimestamp {

    @TypeConverter
    public static Timestamp toTimestamp( Long timestamp ) {
        return timestamp == null ? null : new Timestamp( timestamp );
    }

    @TypeConverter
    public static Long fromTimestamp( Timestamp timestamp ) {
        return timestamp == null ? null : timestamp.getTime();
    }
}


package website.dango.resistor;


import androidx.room.TypeConverter;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class ConverterBandColor {

    @TypeConverter
    public static List toBandColorList( String bandColors ) {
        return new Gson().fromJson( bandColors, ArrayList.class );
    }

    @TypeConverter
    public static String fromBandColor( List<BandColor> bandColors ) {
        return new Gson().toJson( bandColors );
    }
}


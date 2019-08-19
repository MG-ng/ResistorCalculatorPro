package website.dango.resistor;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;

import static website.dango.resistor.Helper.getBandColorByValue;
import static website.dango.resistor.Helper.getMultiplicatorColorByValue;
import static website.dango.resistor.Helper.getToleranceColorByValue;

@Entity( tableName = "resistor_table")
@TypeConverters( { ConverterBandColor.class } )
public class Resistor {

    private static final String TAG = "Restistor TAG";

    @PrimaryKey( autoGenerate = true )
    private int id;

    private double resistance;

    private double tolerance;

    private int amount;

    private List<BandColor> bandColors;


    Resistor( double resistance, int amount, double tolerance ) {
        setResistance( resistance );
        setAmount( amount );
        setTolerance( tolerance );
        setBandColors( new ArrayList<>() );
    }



    public void setId( int id ) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public double getResistance() {
        return resistance;
    }

    public void setResistance( double resistance ) {
        this.resistance = resistance;
    }

    public double getTolerance() {
        return tolerance;
    }

    public void setTolerance( double addedTime ) {
        this.tolerance = addedTime;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount( int amount ) {
        this.amount = amount;
    }


    public void setBandColors( List<BandColor> bandColors ) {
        this.bandColors = bandColors;
    }

    public List<BandColor> getBandColors() {

        if( bandColors != null && !bandColors.isEmpty() ) {
            Log.d( TAG, "bandColors: " + bandColors.toString() );
            return bandColors;
        }

        String valueWithoutComma = "";


        String resistanceS = String.format( "%1.2e", getResistance() );
        Log.d( TAG, "resistanceS : " + resistanceS );

        int exponent = Integer.parseInt( String.valueOf( resistanceS.charAt( resistanceS.length() - 3 ) ) +
                resistanceS.charAt( resistanceS.length() - 1 ) );

        Log.d( TAG, "exponent: " + exponent );

        double[] doubles = new double[] {
                Float.parseFloat( String.valueOf( resistanceS.charAt( 0 ) ) ),
                Float.parseFloat( String.valueOf( resistanceS.charAt( 2 ) ) ),
                Float.parseFloat( String.valueOf( resistanceS.charAt( 3 ) ) ),
        };
        int shift = 2;


        if( exponent == -2 ) {
            doubles = new double[]{
                    0, 0, Float.parseFloat( String.valueOf( resistanceS.charAt( 0 ) ) ),
            };
            shift = 0;

        } else if( exponent == -1 ) {
            doubles = new double[]{
                    0,
                    Float.parseFloat( String.valueOf( resistanceS.charAt( 0 ) ) ),
                    Float.parseFloat( String.valueOf( resistanceS.charAt( 2 ) ) ),
            };
            shift = -1;

        } else if( exponent == 0 ) {
            shift = -2;

        } else if( exponent == 1  ) {
            shift = -2;

        } else if( exponent > 1  ) {
            shift = -2;

        }



        for( double c : doubles ) {
            Log.d( TAG, "character is: " + c );
            bandColors.add( getBandColorByValue( c ) );
        }

        Log.d( TAG, "shift is: " + shift );
        bandColors.add( getMultiplicatorColorByValue( Math.pow( 10, exponent + shift ) ) );

        Log.d( TAG, bandColors.toString() );


        // get tol
        bandColors.add( getToleranceColorByValue( getTolerance() ) );

        Log.d( TAG, bandColors.toString() );

        return bandColors;
    }

}
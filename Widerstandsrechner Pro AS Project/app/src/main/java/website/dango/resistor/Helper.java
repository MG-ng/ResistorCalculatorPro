package website.dango.resistor;

import android.util.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Helper {

    private static final String TAG = "Thrown in Helper";

    public static double round( double value, int places ) {
        if( places < 0 ) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal( value );
        bd = bd.setScale( places, RoundingMode.HALF_UP );
        return bd.doubleValue();
    }

    public static int fact( int number ) {
        int result = 1;
        for( ; number > 0; number-- ) {
            result *= number;
        }
        return result;
    }

    public static String shortFloat( double numberWithPoint ) {
        // 2147483648d is the max int value --> wrong value if casted
        if( numberWithPoint % 1 != 0 || numberWithPoint > Integer.MAX_VALUE ) {
            return String.format( "%3.2f", numberWithPoint );
        } else {
            return String.valueOf( (int) numberWithPoint );
        }
    }


    private static final String[] KMG = new String[] {"", "K", "M", "G"};

    public static String addEnding( double number ) {
        int i = 0;
        while( number >= 1000 ) {
            i++;
            number /= 1000;
        }
        return shortFloat( number ) + KMG[i];
    }


    public static List<BandColor> getToleranceColors() {
        ArrayList<BandColor> list = new ArrayList();
        list.add( new BandColor( 0.0005d, R.string.color_grey, R.color.grey ) );
        list.add( new BandColor( 0.001d, R.string.color_pink, R.color.pink ) );
        list.add( new BandColor( 0.0025d, R.string.color_blue, R.color.blue ) );
        list.add( new BandColor( 0.005d, R.string.color_green, R.color.green ) );
        list.add( new BandColor( 0.01d, R.string.color_brown, R.color.brown ) );
        list.add( new BandColor( 0.02d, R.string.color_red, R.color.red) );
        list.add( new BandColor( 0.05d, R.string.color_gold, R.color.gold ) );
        list.add( new BandColor( 0.1d, R.string.color_silver, R.color.silver ) );
        return list;
    }

    /**
     * @param value represented in the color
     * @return BandColor if value was found
     */
    public static BandColor getToleranceColorByValue( double value ) {
        Log.d( TAG, "Searching Tol for: " + value );
        for( BandColor color : getToleranceColors() ) {
            if( color.getValue() == value ) {
                return color;
            }
        }
        return null;
    }


    public static List<BandColor> getMultiplicatorColors() {
        ArrayList<BandColor> list = new ArrayList();
        list.add( new BandColor( 0.01d, R.string.color_silver, R.color.silver ) );
        list.add( new BandColor( 0.1d, R.string.color_gold, R.color.gold ) );
        list.add( new BandColor( 1, R.string.color_black, R.color.black ) );
        list.add( new BandColor( 10, R.string.color_brown, R.color.brown ) );
        list.add( new BandColor( 100, R.string.color_red, R.color.red ) );
        list.add( new BandColor( 1000, R.string.color_orange, R.color.orange ) );
        list.add( new BandColor( 10000, R.string.color_yellow, R.color.yellow ) );
        list.add( new BandColor( 100000, R.string.color_green, R.color.green ) );
        list.add( new BandColor( 1000000, R.string.color_blue, R.color.blue ) );
        list.add( new BandColor( 10000000, R.string.color_pink, R.color.pink ) );
        list.add( new BandColor( 100000000, R.string.color_grey, R.color.grey ) );
        list.add( new BandColor( 1000000000, R.string.color_white, R.color.white ) );
        return list;
    }

    /**
     * @param value represented in the color
     * @return BandColor if value was found
     */
    public static BandColor getMultiplicatorColorByValue( double value ) {
        Log.d( TAG, "Searching Multi for: " + value );
        for( BandColor color : getMultiplicatorColors() ) {
            if( color.getValue() == value ) {
                return color;
            }
        }
        return null;
    }



    public static List<BandColor> getBandColors() {
        ArrayList<BandColor> list = new ArrayList();
        list.add( new BandColor( 0, R.string.color_black, R.color.black ) );
        list.add( new BandColor( 1, R.string.color_brown, R.color.brown ) );
        list.add( new BandColor( 2, R.string.color_red, R.color.red ) );
        list.add( new BandColor( 3, R.string.color_orange, R.color.orange ) );
        list.add( new BandColor( 4, R.string.color_yellow, R.color.yellow ) );
        list.add( new BandColor( 5, R.string.color_green, R.color.green ) );
        list.add( new BandColor( 6, R.string.color_blue, R.color.blue ) );
        list.add( new BandColor( 7, R.string.color_pink, R.color.pink ) );
        list.add( new BandColor( 8, R.string.color_grey, R.color.grey ) );
        list.add( new BandColor( 9, R.string.color_white, R.color.white ) );
        return list;
    }

    /**
     * @param value represented in the color
     * @return BandColor if value was found
     */
    public static BandColor getBandColorByValue( double value ) {
        Log.d( TAG, "Searching Band for: " + value );
        for( BandColor color : getBandColors() ) {
            if( color.getValue() == value ) {
                Log.d( TAG, "returned color" );
                return color;
            }
        }
        Log.d( TAG, "returned null as BandColor" );
        return null;
    }



}

package website.dango.resistor;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class ResistorCalculator extends AsyncTask<ArrayList<Double>, Integer, ResistorCircuit> {
    private static final String TAG = "ResistorCalculator   ";

    private Callback callback;
    private List<Double> resistors = new ArrayList<>();
    private List<Double[]> alleWiderstandsKombinationen = new ArrayList<>();
    private static List<Double[]> allResistorPerms = new ArrayList<>();

    private Double zwischenErgebnis = 0d;
    private ResistorCircuit betterCircuit = null;
    private final double wanted;
    private int numberUsedInOneCircuit;


    ResistorCalculator( Callback callback, double wanted, int numberUsedInOneCircuit ) {

        this.callback = callback;
        this.wanted = wanted;
        this.numberUsedInOneCircuit = numberUsedInOneCircuit;
    }

    private void berechne1() {

        if( numberUsedInOneCircuit != 1
                || alleWiderstandsKombinationen == null
                || alleWiderstandsKombinationen.size() < 1 ) {
            Log.e( TAG, "Returned" );
            return;
        }

        stats();

        for( int i = 0; !isCancelled(); i++ ) {

            ResistorCircuit1 circuit = null;

            switch( i ) {
                case 0:
                    circuit = new Circuit1_1();
                    break;
            }
            if( circuit == null )
                break;

            runAllPossibilities( circuit );
        }
    }

    private void berechne2() {

        stats();

        for( int i = 0; !isCancelled(); i++ ) {

            ResistorCircuit2 circuit = null;

            switch( i ) {
                case 0:
                    circuit = new Circuit2_1();
                    break;

                case 1:
                    circuit = new Circuit2_2();
                    break;
            }

            if( circuit == null )
                break;

            runAllPossibilities( circuit );
        }

    }

    private void berechne3() {

        stats();

        for( int i = 0; !isCancelled(); i++ ) {

            ResistorCircuit3 circuit = null;

            switch( i ) {
                case 0:
                    circuit = new Circuit3_1();
                    break;

                case 1:
                    circuit = new Circuit3_2();
                    break;

                case 2:
                    circuit = new Circuit3_3();
                    break;

                case 3:
                    circuit = new Circuit3_4();
                    break;

                default:
                    break;
            }

            if( circuit == null )
                break;

            runAllPossibilities( circuit );
        }

    }

    private void berechne4() {

        stats();

        for( int i = 0; !isCancelled(); i++ ) {

            ResistorCircuit4 circuit = null;

            switch( i ) {

                case 0:
                    circuit = new Circuit4_1();
                    break;

                case 1:
                    circuit = new Circuit4_2();
                    break;

                case 2:
                    circuit = new Circuit4_3();
                    break;

                case 3:
                    circuit = new Circuit4_4();
                    break;

                case 4:
                    circuit = new Circuit4_5();
                    break;

                case 5:
                    circuit = new Circuit4_6();
                    break;

                case 6:
                    circuit = new Circuit4_7();
                    break;

                case 7:
                    circuit = new Circuit4_8();
                    break;

                case 8:
                    circuit = new Circuit4_9();
                    break;

                case 9:
                    circuit = new Circuit4_10();
                    break;

                default:
                    break;
            }

            if( null == circuit ) {
                break;
            }

            runAllPossibilities( circuit );
        }

    }

    private void runAllPossibilities( ResistorCircuit circuit ) {

        for( Double[] kombination : alleWiderstandsKombinationen ) {
            if( isCancelled() ) {
                break;
            }

            if( circuit.isOrderImportant() ) {

                permute( kombination );

                for( Double[] resistorPermutation : allResistorPerms )
                    adjustBestCircuitIfMorePreciseOrEqual( circuit, resistorPermutation );

            } else {
                adjustBestCircuitIfMorePreciseOrEqual( circuit, kombination );
            }

            Log.w( TAG, "best circuit: " + betterCircuit.getDescription() +
                    " totalResistor: " + betterCircuit.getTotalResistance() );
        }
    }

    private void adjustBestCircuitIfMorePreciseOrEqual( ResistorCircuit circuit, Double[] kombination ) {

        zwischenErgebnis = circuit.calcTotalResistance( kombination );

        if( betterCircuit == null ) {
            betterCircuit = circuit.duplicate( circuit );
            return;
        }

        double neueDifferenz = circuit.getDeviation( wanted );
        double alteDifferenz = betterCircuit.getDeviation( wanted );


        if( neueDifferenz <= alteDifferenz ) {

            betterCircuit = circuit.duplicate( circuit );

            Log.w( TAG, "Alte Differenz: " + alteDifferenz + " neue Differenz: " + neueDifferenz );
            Log.w( TAG, "Circuit getTotalResistance: " + circuit.getTotalResistance() );
            Log.d( TAG, "Verbessert! " + betterCircuit.getDescription() );


            if( betterCircuit.getDeviation( wanted ) < 0.0001 ) {
                Log.e( TAG, "onPost: " + betterCircuit.getDeviation( wanted ) + betterCircuit.getDescription() );
                this.onPostExecute( betterCircuit );
            }
        }
    }

    /**
     * The main function that prints all combinations of size r
     * in arr[] of size n. This function mainly uses combinationUtil()
     */
    public void fillCombinations( Double arr[], int n, int r ) {

        alleWiderstandsKombinationen = new ArrayList<>();

        // A temporary array to store all combination one by one
        Double data[] = new Double[ r ];

        // Print all combination using temprary array 'data[]'
        combinationUtil( arr, data, 0, n - 1, 0, r );
    }

    /**
     * @param arr   ----> Input Array
     * @param data  ---> Temporary array to store current combination
     * @param start --> Starting index in arr[]
     * @param end   ----> Ending index in arr[]
     * @param index --> Current index in data[]
     * @param r     -----> Size of a combination to be printed
     **/
    private void combinationUtil( Double arr[], Double data[], int start,
                                  int end, int index, int r ) {

        // Current combination is ready to be printed, print it
        if( index == r ) {
            Double[] resistors = Arrays.copyOfRange( data, 0, r );
            System.out.println( Arrays.toString( resistors ) );

            alleWiderstandsKombinationen.add( resistors );
            return;
        }
        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for( int i = start; i <= end && end - i + 1 >= r - index; i++ ) {
            data[ index ] = arr[ i ];
            combinationUtil( arr, data, i + 1, end, index + 1, r );

            // Since the elements are sorted, all occurrences of an element
            // must be together
            while( i < arr.length - 1 && arr[ i ] == arr[ i + 1 ] )
                i++;
        }
    }

    public static boolean isOrderAlreadySaved( final List<Integer[]> list, final Integer[] kombination ) {

        for( Integer[] savedCombination : list ) {
            if( compareArrays( savedCombination, kombination ) ) {
                return true;
            }
        }
        return false;
    }


    public static boolean compareArrays( final Integer[] array1, final Integer[] array2 ) {
        boolean b = true;
        if( array1 != null && array2 != null ) {
            if( array1.length != array2.length )
                b = false;
            else
                for( int i = 0; i < array2.length; i++ ) {
                    if( !array1[ i ].equals( array2[ i ] ) ) {
                        b = false;
                    }
                }
        } else {
            b = false;
        }
        return b;
    }


    private void stats() {

        for( Double[] kombination : alleWiderstandsKombinationen ) {
            StringBuilder builder = new StringBuilder( "Kombination: " );
            for( Double resistor : kombination ) {
                builder.append( resistor );
                builder.append( ", " );
            }
            Log.i( TAG, builder.toString() );
        }

    }

    public static void permute( Double[] arr ) {
        permuteHelper( arr, 0 );
    }

    private static void permuteHelper( Double[] arr, int index ) {

        if( index >= arr.length - 1 ) { //If we are at the last element - nothing left to permute
            System.out.println( Arrays.toString( arr ) );
            allResistorPerms.add( arr );
            return;
        }

        for( int i = index; i < arr.length; i++ ) { //For each index in the sub array arr[index...end]

            //Swap the elements at indices index and i
            double t = arr[ index ];
            arr[ index ] = arr[ i ];
            arr[ i ] = t;

            //Recurse on the sub array arr[index+1...end]
            permuteHelper( arr, index + 1 );

            //Swap the elements back
            t = arr[ index ];
            arr[ index ] = arr[ i ];
            arr[ i ] = t;
        }
    }

    @Override
    protected ResistorCircuit doInBackground( ArrayList<Double>... resistances ) {

        resistors = resistances[0];

        if( resistors.size() < numberUsedInOneCircuit ) {

            Log.e( TAG, "Array too small" );
            return null;
        }

        Integer[] progress;

        if( numberUsedInOneCircuit >= 1 ) {
            int tmp = numberUsedInOneCircuit;
            numberUsedInOneCircuit = 1;
            fillCombinations( resistors.toArray( new Double[]{} ), resistors.size(), numberUsedInOneCircuit );
            progress = new Integer[]{ 1, alleWiderstandsKombinationen.size() };
            publishProgress( progress );
            berechne1();
            adjustBestCircuitIfMorePreciseOrEqual( (int) wanted );
            numberUsedInOneCircuit = tmp;
        } else
            Log.e( TAG, "skipped: " + numberUsedInOneCircuit + isCancelled() );

        if( numberUsedInOneCircuit >= 2 && !isCancelled() ) {
            int tmp = numberUsedInOneCircuit;
            numberUsedInOneCircuit = 2;
            fillCombinations( resistors.toArray( new Double[]{} ), resistors.size(), numberUsedInOneCircuit );
            progress = new Integer[]{ 2, alleWiderstandsKombinationen.size() };
            publishProgress( progress );
            berechne2();
            adjustBestCircuitIfMorePreciseOrEqual( (int) wanted );
            numberUsedInOneCircuit = tmp;
        } else
            Log.e( TAG, "skipped: " + numberUsedInOneCircuit + isCancelled() );

        if( numberUsedInOneCircuit >= 3 && !isCancelled() ) {
            int tmp = numberUsedInOneCircuit;
            numberUsedInOneCircuit = 3;
            fillCombinations( resistors.toArray( new Double[]{} ), resistors.size(), numberUsedInOneCircuit );
            progress = new Integer[]{ 3, alleWiderstandsKombinationen.size() };
            publishProgress( progress );
            berechne3();
            adjustBestCircuitIfMorePreciseOrEqual( (int) wanted );
            numberUsedInOneCircuit = tmp;
        } else
            Log.e( TAG, "skipped: " + numberUsedInOneCircuit + isCancelled() );

        if( numberUsedInOneCircuit >= 4 && !isCancelled() ) {
            int tmp = numberUsedInOneCircuit;
            numberUsedInOneCircuit = 4;
            fillCombinations( resistors.toArray( new Double[]{} ), resistors.size(), numberUsedInOneCircuit );
            progress = new Integer[]{ 4, alleWiderstandsKombinationen.size() };
            publishProgress( progress );
            berechne4();
            adjustBestCircuitIfMorePreciseOrEqual( (int) wanted );
            numberUsedInOneCircuit = tmp;
        } else
            Log.e( TAG, "skipped: " + numberUsedInOneCircuit + isCancelled() );

        return bestCircuit;
    }

    @Override
    public void onProgressUpdate( Integer... progress ) {
        if( callback != null ) {
            callback.circuitsProcessed( bestCircuit, progress );
        }
    }

    @Override
    public void onPostExecute( ResistorCircuit circuit ) {

        if( callback != null ) {
            callback.allCircuitsProcessed( circuit );
        }
        Log.e( TAG, "Async Cancelled" );
        super.onPostExecute( circuit );
        Log.e( TAG, "Has already completed: " + cancel( true ) );
    }


    private ResistorCircuit bestCircuit = null;

    private void adjustBestCircuitIfMorePreciseOrEqual( int wanted ) {

        if( betterCircuit == null ) {
            Log.e( TAG, "betterCircuit is null!" );
        }

        if( bestCircuit == null ) {
            bestCircuit = betterCircuit.duplicate( betterCircuit );
            return;
        }

        double neueDifferenz = betterCircuit.getDeviation( wanted );
        double alteDifferenz = bestCircuit.getDeviation( wanted );

        Log.w( TAG, "Alte Differenz: " + alteDifferenz + " neue Differenz: " + neueDifferenz );

        if( neueDifferenz <= alteDifferenz ) {

            bestCircuit = betterCircuit.duplicate( betterCircuit );
            publishProgress( (int) bestCircuit.getDeviation( wanted ) );

            Log.d( TAG, "Verbessert! " + bestCircuit.getDescription() );
        }
    }

}

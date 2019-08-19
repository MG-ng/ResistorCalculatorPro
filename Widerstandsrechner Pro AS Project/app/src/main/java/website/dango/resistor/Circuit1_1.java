package website.dango.resistor;

import android.util.Log;

public class Circuit1_1 extends ResistorCircuit1 {


    public Double calcTotalResistance( Double[] widerstandsPermutation ) {

        setTotalResistance( null );
        if( widerstandsPermutation != null && widerstandsPermutation.length == 1 ) {

            setRes1( widerstandsPermutation[0] );

            setTotalResistance( getRes1() );
        } else
            Log.e( getClass().getSimpleName(), "Something wrong!" );

        return getTotalResistance();
    }

    public boolean isOrderImportant() {
        return false;
    }


    public String getDescription() {
        return "Benutze den einen Widerstand";
    }


}

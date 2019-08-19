package website.dango.resistor;

public class Circuit4_9 extends ResistorCircuit4 {


    public Double calcTotalResistance( Double[] widerstandsPermutation ) {

        setTotalResistance( null );
        if ( widerstandsPermutation != null && widerstandsPermutation.length == 4 ) {

            setRes1( widerstandsPermutation[0] );
            setRes2( widerstandsPermutation[1] );
            setRes3( widerstandsPermutation[2] );
            setRes4( widerstandsPermutation[3] );

            Double widertemp = ( getRes1() + getRes2() );

            setTotalResistance(
                    ( widertemp * getRes4() * getRes3() ) /
                            ( ( widertemp * getRes3() ) + ( getRes3() * getRes4() )
                                    + ( getRes4() * widertemp ) ) );

        }
        return getTotalResistance();
    }

    public boolean isOrderImportant() {
        return false;
    }


    public String getDescription() {
        return "Schließe Widerstand 1 und 2 in Reihe\n" +
                "Schließe Widerstand 3 und 4 dazu parallel\n";
    }
}
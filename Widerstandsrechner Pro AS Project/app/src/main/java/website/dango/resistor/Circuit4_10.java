package website.dango.resistor;

public class Circuit4_10 extends ResistorCircuit4 {


    public Double calcTotalResistance( Double[] widerstandsPermutation ) {

        setTotalResistance( null );
        if( widerstandsPermutation != null && widerstandsPermutation.length == 4 ) {

            setRes1( widerstandsPermutation[0] );
            setRes2( widerstandsPermutation[1] );
            setRes3( widerstandsPermutation[2] );
            setRes4( widerstandsPermutation[3] );


            setTotalResistance(
                    ( ( getRes1() * getRes2() * getRes3() ) /
                            ( ( getRes1() * getRes2() ) + ( getRes2() * getRes3() )
                                    + ( getRes3() * getRes1() ) ) )
                    + getRes4() );

        }

        return getTotalResistance();
    }

    public boolean isOrderImportant() {
        return true;
    }


    public String getDescription() {
        return "Schließe Widerstand 1, 2 und 3 parallel\n" +
                "Schließe Widerstand 4 dazu in Reihe\n";
    }
}
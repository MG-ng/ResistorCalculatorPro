package website.dango.resistor;

public class Circuit4_3 extends ResistorCircuit4 {


    public Double calcTotalResistance( Double[] widerstandsPermutation ) {

        setTotalResistance( null );
        if( widerstandsPermutation != null && widerstandsPermutation.length == 4 ) {

            setRes1( widerstandsPermutation[0] );
            setRes2( widerstandsPermutation[1] );
            setRes3( widerstandsPermutation[2] );
            setRes4( widerstandsPermutation[3] );

            Double widertemp =
                    ( ( getRes1() * getRes2() ) / ( getRes1() + getRes2() ) ) + getRes3();

            setTotalResistance(
                    ( widertemp * getRes4() ) / ( widertemp + getRes4() )
            );

        }

        return getTotalResistance();
    }

    public boolean isOrderImportant() {
        return true;
    }

    public String getDescription() {
        return "Schließe Widerstand 1 und 2 parallel\n" +
                "Schließe Widerstand 3 dazu in Reihe\n" +
                "Schließe Widerstand 4 zu dieser Schaltung parallel";
    }
}
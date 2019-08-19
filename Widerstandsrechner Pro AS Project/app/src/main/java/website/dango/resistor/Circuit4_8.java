package website.dango.resistor;

public class Circuit4_8 extends ResistorCircuit4 {


    public Double calcTotalResistance( Double[] widerstandsPermutation ) {

        setTotalResistance( null );
        if ( widerstandsPermutation != null && widerstandsPermutation.length == 4 ) {

            setRes1( widerstandsPermutation[0] );
            setRes2( widerstandsPermutation[1] );
            setRes3( widerstandsPermutation[2] );
            setRes4( widerstandsPermutation[3] );

            Double totalResistorTmp = getRes1() + getRes2();


            setTotalResistance(
                    ( ( getRes3() * totalResistorTmp ) / ( getRes3() + totalResistorTmp ) ) + getRes4()
            );
        }

        return getTotalResistance();
    }

    public boolean isOrderImportant() {
        return true;
    }

    public String getDescription() {
        return "Schließe Widerstand 1 und 2 in Reihe\n" +
                "Schließe Widerstand 3 dazu parallel\n" +
                "Schließe zu dieser Schaltung Widerstand 4 in Reihe";
    }
}
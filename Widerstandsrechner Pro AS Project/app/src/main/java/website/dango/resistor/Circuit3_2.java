package website.dango.resistor;

public class Circuit3_2 extends ResistorCircuit3 {


    public Double calcTotalResistance( Double[] widerstandsPermutation ) {

        setTotalResistance( null );
        if( widerstandsPermutation != null && widerstandsPermutation.length == 3 ) {

            setRes1( widerstandsPermutation[0] );
            setRes2( widerstandsPermutation[1] );
            setRes3( widerstandsPermutation[2] );


            setTotalResistance(
                    (getRes1() * getRes2()) / (getRes1() + getRes2()) + getRes3()
            );

        }

        return getTotalResistance();
    }

    public boolean isOrderImportant() {
        return true;
    }


    public String getDescription() {
        return "Schließe die Widerstände 1 und 2 parallel\n" +
                "Schließe zu dieser Schaltung den Widerstand 3 in Reihe";
    }
}

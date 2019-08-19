package website.dango.resistor;

public class Circuit3_3 extends ResistorCircuit3 {


    public Double calcTotalResistance( Double[] widerstandsPermutation ) {

        setTotalResistance( null );
        if( widerstandsPermutation != null && widerstandsPermutation.length == 3 ) {

            setRes1( widerstandsPermutation[0] );
            setRes2( widerstandsPermutation[1] );
            setRes3( widerstandsPermutation[2] );

            Double totalResTmp = getRes1() + getRes2();

            setTotalResistance(
                    (totalResTmp * getRes3()) / (totalResTmp + getRes3())
            );
        }

        return getTotalResistance();
    }

    public boolean isOrderImportant() {
        return true;
    }


    public String getDescription() {
        return "Schließe die Widerstände 1 und 2 in Reihe\n" +
                "Schließe zu dieser Schaltung Widerstand 3 parallel";
    }
}

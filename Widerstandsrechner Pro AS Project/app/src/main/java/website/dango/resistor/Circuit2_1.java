package website.dango.resistor;

public class Circuit2_1 extends ResistorCircuit2 {


    public Double calcTotalResistance( Double[] widerstandsPermutation ) {

        setTotalResistance( null );
        if( widerstandsPermutation != null && widerstandsPermutation.length == 2 ) {

            setRes1( widerstandsPermutation[0] );
            setRes2( widerstandsPermutation[1] );

            setTotalResistance(
                    getRes1() + getRes2()
            );
        }
        return getTotalResistance();
    }

    public boolean isOrderImportant() {
        return false;
    }

    public String getDescription() {
        return "Schließe die Widerstände 1 und 2 in Reihe";
    }
}

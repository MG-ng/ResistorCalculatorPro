package website.dango.resistor;

public class Circuit3_1 extends ResistorCircuit3 {



    public Double calcTotalResistance( Double[] widerstandsPermutation ) {

        setTotalResistance( null );
        if( widerstandsPermutation != null && widerstandsPermutation.length == 3 ) {

            setRes1( widerstandsPermutation[0] );
            setRes2( widerstandsPermutation[1] );
            setRes3( widerstandsPermutation[2] );


            setTotalResistance(
                    getRes1() + getRes2() + getRes3()
            );

        }

        return getTotalResistance();
    }

    public boolean isOrderImportant() {
        return false;
    }


    public String getDescription() {
        return "Schließe alle Widerstände in Reihe";
    }
}

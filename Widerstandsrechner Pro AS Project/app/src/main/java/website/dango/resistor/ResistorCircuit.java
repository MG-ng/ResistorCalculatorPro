package website.dango.resistor;

import static java.lang.Math.abs;
import static website.dango.resistor.Helper.round;

public abstract class ResistorCircuit {

    private Double totalResistance;


    public Double getTotalResistance() {
        return totalResistance;
    }


    public void setTotalResistance( Double totalResistance ) {
        this.totalResistance = totalResistance;
    }

    public double getDeviation( double wanted ) {
        if( getTotalResistance() == null ) {
            return wanted;
        }
        double deviation = getTotalResistance() - wanted;
        return round( abs( deviation ), 2 );
    }


    abstract public Double calcTotalResistance( Double[] widerstandsPermutation );

    abstract public boolean isOrderImportant();

    abstract public String getDescription();

    abstract public ResistorCircuit duplicate( ResistorCircuit circuit );
}

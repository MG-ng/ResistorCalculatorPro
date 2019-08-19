package website.dango.resistor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import static website.dango.resistor.Helper.fact;
import static website.dango.resistor.Helper.shortFloat;

public class CalculationViewmodel extends ViewModel implements Callback {

    private static final String TAG = "CalculationViewmodel  ";

    private MutableLiveData<String> descriptionString = new MutableLiveData<>();
    private MutableLiveData<Double> totalResistanceAchieved = new MutableLiveData<>();
    private MutableLiveData<Double[]> calculatedResistors = new MutableLiveData<>();
    private MutableLiveData<String> progressString = new MutableLiveData<>();

    double totalResistanceWanted = 0;

    public MutableLiveData<Double> getTotalResistanceAchieved() {
        return totalResistanceAchieved;
    }

    public MutableLiveData<Double[]> getCalculatedResistors() {
        return calculatedResistors;
    }

    public MutableLiveData<String> getDescriptionString() {
        return descriptionString;
    }

    public LiveData<String> getProgress() {
        return progressString;
    }

    ResistorCalculator calculator;

    public void setParams( ArrayList<Double> resistorValues, int maxResistors, double totalResistanceWanted ) {

        this.totalResistanceWanted = totalResistanceWanted;

        calculator = new ResistorCalculator( this, totalResistanceWanted, maxResistors );

        calculator.execute( resistorValues );
    }

    @Override
    public void allCircuitsProcessed( ResistorCircuit bestCircuit ) {

        if( calculator.isCancelled() ) {
            return;
        }

        Double[]  floats;


        if( bestCircuit instanceof ResistorCircuit4 ) {

            ResistorCircuit4 circuit = (ResistorCircuit4) bestCircuit;

             floats = new Double[]{ circuit.getRes1(),
                    circuit.getRes2(), circuit.getRes3(), circuit.getRes4() };


        } else if( bestCircuit instanceof ResistorCircuit3 ) {

            ResistorCircuit3 circuit = (ResistorCircuit3) bestCircuit;

             floats = new Double[]{ circuit.getRes1(),
                    circuit.getRes2(), circuit.getRes3() };

        } else if( bestCircuit instanceof ResistorCircuit2 ) {

            ResistorCircuit2 circuit = (ResistorCircuit2) bestCircuit;

             floats = new Double[]{ circuit.getRes1(), circuit.getRes2() };

        } else if( bestCircuit instanceof ResistorCircuit1 ) {

            ResistorCircuit1 circuit = (ResistorCircuit1) bestCircuit;

             floats = new Double[]{ circuit.getRes1() };

        } else
            throw new IllegalArgumentException( "No instance found!" );


        calculatedResistors.postValue( floats );

        descriptionString.postValue( bestCircuit.getDescription() + "\n" );

        totalResistanceAchieved.postValue( bestCircuit.getTotalResistance() );

    }

    @Override
    public void circuitsProcessed( ResistorCircuit bestCircuit, Integer... progress ) {
        if( progress != null && progress.length == 2 ) {
            double allPerms = fact( progress[ 0 ]  ) * progress[ 1 ];
            int[] circuits = new int[]{ 1, 2, 4, 10 };
            String progressTmp = "Schritt: " + progress[ 0 ] + "\n\n" +
                    "Alle Kombinationen: " + progress[ 1 ] + "\n\n" +
                    "Alle Permutationen: " + progress[ 1 ] + " * " + progress[ 0 ] + "! = " +
                    fact( progress[ 0 ] ) * progress[ 1 ] + "\n\n" +
                    "Auf " + circuits[ progress[ 0 ] -1 ] + " Arten von Schaltungen: " + shortFloat( allPerms ) +
                    " * " + circuits[ progress[ 0 ] -1 ] + " = " + shortFloat( allPerms * circuits[ progress[ 0 ] -1 ] );
            if( bestCircuit != null ) {
                progressTmp += "\n\n\n\nBisher eine Abweichung von: " + bestCircuit.getDeviation( totalResistanceWanted ) + " Ω\n\n";
            }
            progressString.setValue( progressTmp );
        } else {
            progressString.setValue( "Lädt..." );
        }
    }
}

interface Callback {
    public void allCircuitsProcessed( ResistorCircuit bestCircuit );
    public void circuitsProcessed( ResistorCircuit bestCircuit, Integer... progress );
}

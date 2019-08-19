package website.dango.resistor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewResistorViewmodel extends ViewModel {


    private MutableLiveData<Double> resistanceBeige = new MutableLiveData<>();
    private MutableLiveData<Double> resistanceBlue = new MutableLiveData<>();

    private MutableLiveData<Boolean> isBeige = new MutableLiveData<>();
    private MutableLiveData<Integer> bandColor1 = new MutableLiveData<>(),
            bandColor2 = new MutableLiveData<>(),
            bandColor3 = new MutableLiveData<>();
    private MutableLiveData<Double> multiplicator = new MutableLiveData<>(),
            tolerance = new MutableLiveData<>();


    public NewResistorViewmodel() {
        resistanceBeige.setValue( 0d );
        resistanceBlue.setValue( 0d );
        isBeige.setValue( false );
        bandColor1.setValue( 0 );
        bandColor2.setValue( 0 );
        bandColor3.setValue( 0 );
        multiplicator.setValue( 1d );
        tolerance.setValue( 0d );
    }


    @SuppressWarnings( "ConstantConditions" )
    private void recalculate() {

        if( isBeige.getValue() ) {

            resistanceBeige.setValue(

                    Float.parseFloat(
                            String.valueOf( bandColor1.getValue() ) +
                                    String.valueOf( bandColor2.getValue() )
                    )
                            * multiplicator.getValue()
            );

        } else {

            resistanceBlue.setValue(

                    Integer.parseInt(
                            String.valueOf( bandColor1.getValue() ) +
                                    String.valueOf( bandColor2.getValue() ) +
                                    String.valueOf( bandColor3.getValue() )
                    )
                            * multiplicator.getValue()
            );

        }

    }


    public void setIsBeige( boolean isBeige ) {
        this.isBeige.setValue( isBeige );
        recalculate();
    }

    public void setBand1( int band1 ) {
        bandColor1.setValue( band1 );
        recalculate();
    }

    public void setBand2( int band2 ) {
        bandColor2.setValue( band2 );
        recalculate();
    }

    public void setBand3( int band3 ) {
        bandColor3.setValue( band3 );
        recalculate();
    }

    public void setMulti( double multi ) {
        multiplicator.setValue( multi );
        recalculate();
    }

    public void setTol( double tol ) {
        tolerance.setValue( tol );
    }



    public LiveData<Double> getResistanceBeige() {
        return resistanceBeige;
    }

    public LiveData<Double> getResistanceBlue() {
        return resistanceBlue;
    }

    public LiveData<Integer> getBandColor1() {
        return bandColor1;
    }

    public LiveData<Integer> getBandColor2() {
        return bandColor2;
    }

    public LiveData<Integer> getBandColor3() {
        return bandColor3;
    }

    public LiveData<Double> getMultiplicator() {
        return multiplicator;
    }

    public LiveData<Double> getTolerance() {
        return tolerance;
    }
}

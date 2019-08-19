package website.dango.resistor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

import static website.dango.resistor.Helper.round;

public class CalculationActivity extends AppCompatActivity {

    private static final String TAG = "CalculationActivity";
    public static final String keyAvailableResistors = "list of all selected resistors";
    public static final String keyMaxResistors = "the max amount of resistors to realize the circuit";
    public static final String keyTotalResistor = "the total resistance of the resistor circuit";


    CalculationViewmodel viewmodel;

    @SuppressLint( "SetTextI18n" )
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_calculation );

        viewmodel = ViewModelProviders.of( this ).get( CalculationViewmodel.class );

        View loadingView = findViewById( R.id.loadingView );
        LinearLayout resultView = findViewById( R.id.resultView );

        TextView tvTotalResistanceWanted = findViewById( R.id.totalResistanceWanted );
        TextView selectedResistors = findViewById( R.id.selectedResistors );
        TextView tvTotalResistanceAchieved = findViewById( R.id.totalResistanceAchieved );
        TextView tvDeviation = findViewById( R.id.deviation );
        TextView result = findViewById( R.id.result );
        TextView tvLoadingProgress = findViewById( R.id.loadingProgress );

        Intent intent = getIntent();

        // TODO: Refresh intent
        ArrayList<Double> resistorValues = (ArrayList<Double>) intent.getSerializableExtra( keyAvailableResistors );
        int maxResistors = intent.getIntExtra( keyMaxResistors, 0 );
        double totalResistancWanted = intent.getDoubleExtra( keyTotalResistor, 0 );

        if( resistorValues == null|| resistorValues.size() == 0 || maxResistors == 0 || totalResistancWanted == 0 ) {
            finish();
            return;
        }

        viewmodel.getDescriptionString().observe( this, s -> {
            resultView.setVisibility( View.VISIBLE );
            loadingView.setVisibility( View.GONE );
            result.setText( "\n" + result.getText() + "\n\n" + s );
        } );
        viewmodel.getTotalResistanceAchieved().observe( this, d -> {
            tvTotalResistanceAchieved.setText(
                    tvTotalResistanceAchieved.getText() +
                            " ( " +
                            round( d, 2 ) +
                            " Ω )"
            );
            tvDeviation.setText( tvDeviation.getText().toString() +
                    round( Math.abs( totalResistancWanted - d ), 4 ) +
                    " Ω ( " +
                    round( 100 * Math.abs( (totalResistancWanted - d) / totalResistancWanted ), 4 ) +
                    " % )"
            );
        } );
        viewmodel.getCalculatedResistors().observe( this, resistors -> {
            int startIndexChild = 1;
            for( int i = 0; i < resistors.length; i++ ) {
                ResistorView view = new ResistorView( this );
                // TODO: Change ALL Circuits --> calculate with Resistors, not with Floats!!!

                view.setResistor( i, new Resistor( resistors[ i ], 1, 0f ) );
                resultView.addView( view, startIndexChild + i );
            }
            resultView.invalidate();
        } );
        viewmodel.getProgress().observe( this, progress -> {
            tvLoadingProgress.setText(
                    progress
            );
        } );

        tvTotalResistanceWanted.setText(
                getString( R.string.you_wanted_an_resistor_with, Helper.shortFloat( totalResistancWanted ), maxResistors )
        );

        viewmodel.setParams( resistorValues, maxResistors, totalResistancWanted );

    }
}

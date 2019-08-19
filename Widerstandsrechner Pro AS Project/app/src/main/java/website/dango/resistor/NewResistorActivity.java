package website.dango.resistor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;

import static website.dango.resistor.Helper.addEnding;
import static website.dango.resistor.Helper.getBandColors;
import static website.dango.resistor.Helper.getMultiplicatorColors;
import static website.dango.resistor.Helper.getToleranceColors;
import static website.dango.resistor.Helper.shortFloat;


public class NewResistorActivity extends AppCompatActivity implements Button.OnClickListener, Switch.OnCheckedChangeListener {
    private static final String TAG = "NewResistorActivity ";


    public static final String RESISTANCE = "Resistor Resistance";
    public static final String AMOUNT = "Resistor Amount";
    public static final String TOLERANCE = "Resistor Tolerance";

    private boolean isBeige = false;
    private double newestResistance = 0;
    private double newestTolerance = 0;

    private NewResistorViewmodel viewmodel;
    private ViewGroup mLayout;
    private EditText etResistance, etAmount;
    private View beigeColorPicker, blueColorPicker;

    private TextView blueChangeBand1,
            blueChangeBand2,
            blueChangeBand3,
            blueChangeMulti,
            blueChangeTol;

    private TextView blueBand1,
            blueBand2,
            blueBand3,
            blueMulti,
            blueTol;

    private TextView beigeChangeBand2,
            beigeChangeBand3,
            beigeChangeMulti,
            beigeChangeTol;

    private TextView beigeBand2,
            beigeBand3,
            beigeMulti,
            beigeTol;

    private TextView resultOfPicker;
    private Button saveButton;


    @Override
    public void onCreate( Bundle savedInstanceState ) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_new_resistor );
        mLayout = findViewById( R.id.new_resistor_layout );
        etAmount = findViewById( R.id.edit_amount );
        etResistance = findViewById( R.id.edit_resistance );
        etResistance.requestFocus();

        saveButton = findViewById( R.id.button_save );
        saveButton.setOnClickListener( this );

        beigeColorPicker = findViewById( R.id.colorPickerBeige );
        blueColorPicker = findViewById( R.id.colorPickerBlue );

        Switch codeSwitcher = findViewById( R.id.codeSwitcher );
        codeSwitcher.setOnCheckedChangeListener( this );

        viewmodel = ViewModelProviders.of( this ).get( NewResistorViewmodel.class );

        resultOfPicker = findViewById( R.id.resultOfPicker );
        resultOfPicker.setOnClickListener( this );

        blueChangeBand1 = blueColorPicker.findViewById( R.id.changeBand1 );
        blueChangeBand2 = blueColorPicker.findViewById( R.id.changeBand2 );
        blueChangeBand3 = blueColorPicker.findViewById( R.id.changeBand3 );
        blueChangeMulti = blueColorPicker.findViewById( R.id.changeMultiplicator );
        blueChangeTol = blueColorPicker.findViewById( R.id.changeTolerance );

        blueBand1 = blueColorPicker.findViewById( R.id.band1 );
        blueBand2 = blueColorPicker.findViewById( R.id.band2 );
        blueBand3 = blueColorPicker.findViewById( R.id.band3 );
        blueMulti = blueColorPicker.findViewById( R.id.multiplicator );
        blueTol = blueColorPicker.findViewById( R.id.tolerance );

        beigeChangeBand2 = beigeColorPicker.findViewById( R.id.changeBand2 );
        beigeChangeBand3 = beigeColorPicker.findViewById( R.id.changeBand3 );
        beigeChangeMulti = beigeColorPicker.findViewById( R.id.changeMultiplicator );
        beigeChangeTol = beigeColorPicker.findViewById( R.id.changeTolerance );

        beigeBand2 = beigeColorPicker.findViewById( R.id.band2 );
        beigeBand3 = beigeColorPicker.findViewById( R.id.band3 );
        beigeMulti = beigeColorPicker.findViewById( R.id.multiplicator );
        beigeTol = beigeColorPicker.findViewById( R.id.tolerance );


        blueBand1.setOnClickListener( this );
        blueBand2.setOnClickListener( this );
        blueBand3.setOnClickListener( this );
        blueMulti.setOnClickListener( this );
        blueTol.setOnClickListener( this );

        blueChangeBand1.setOnClickListener( this );
        blueChangeBand2.setOnClickListener( this );
        blueChangeBand3.setOnClickListener( this );
        blueChangeMulti.setOnClickListener( this );
        blueChangeTol.setOnClickListener( this );

        beigeBand2.setOnClickListener( this );
        beigeBand3.setOnClickListener( this );
        beigeMulti.setOnClickListener( this );
        beigeTol.setOnClickListener( this );

        beigeChangeBand2.setOnClickListener( this );
        beigeChangeBand3.setOnClickListener( this );
        beigeChangeMulti.setOnClickListener( this );
        beigeChangeTol.setOnClickListener( this );


        viewmodel.getResistanceBeige().observe( this, resistance -> {
            newestResistance = resistance;
            resultOfPicker.setText( addEnding( newestResistance ) + " ± " +
                    shortFloat( newestTolerance * 100 ) + " %" );
        } );
        viewmodel.getResistanceBlue().observe( this, resistance -> {
            newestResistance = resistance;
            resultOfPicker.setText( addEnding( newestResistance ) + " ± " +
                    shortFloat( newestTolerance * 100 ) + " %" );
        } );

        viewmodel.getBandColor1().observe( this, band1 -> {
            blueChangeBand1.setText( band1 + "" );
        } );
        viewmodel.getBandColor2().observe( this, band2 -> {
            if( (isBeige) ) {
                beigeChangeBand2.setText( band2 + "" );
            } else {
                blueChangeBand2.setText( band2 + "" );
            }
        } );
        viewmodel.getBandColor3().observe( this, band3 -> {
            if( isBeige) {
                beigeChangeBand3.setText( band3 + "" );
            } else {
                blueChangeBand3.setText( band3 + "" );
            }
        } );

        viewmodel.getMultiplicator().observe( this, multi -> {
            if( isBeige ) {
                beigeChangeMulti.setText( "x " + addEnding( multi ) );
            } else {
                blueChangeMulti.setText( "x " + addEnding( multi ) );
            }
        } );

        viewmodel.getTolerance().observe( this, tol -> {
            newestTolerance = tol;
            resultOfPicker.setText( shortFloat( newestResistance ) + " ± " +
                    shortFloat( newestTolerance * 100 ) + " %" );
            if( isBeige ) {
                beigeChangeTol.setText( "± " + shortFloat(tol * 100) + " %" );
            } else {
                blueChangeTol.setText( "± " + shortFloat(tol * 100) + " %" );
            }
        } );

        etAmount.setOnEditorActionListener( ( view, actionId, event ) -> {
            boolean handled = false;
            if( actionId == EditorInfo.IME_ACTION_SEND ) {
                save();
                handled = true;
            }
            return handled;
        } );
    }

    @Override
    public void onCheckedChanged( CompoundButton compoundButton, boolean isBeige ) {
        this.isBeige = isBeige;
        if( isBeige ) {
            beigeColorPicker.setVisibility( View.VISIBLE );
            blueColorPicker.setVisibility( View.GONE );
            viewmodel.setBand1( 0 );

        } else {
            blueColorPicker.setVisibility( View.VISIBLE);
            beigeColorPicker.setVisibility( View.GONE );

        }
    }

    @Override
    public void onClick( View view ) {
        switch( view.getId() ) {

            case R.id.button_save:
                save();
                break;

            case R.id.resultOfPicker:
                moveResultToEdidText();
                break;

            case R.id.band1:
            case R.id.changeBand1:
                changeBand1();
                break;

            case R.id.band2:
            case R.id.changeBand2:
                changeBand2();
                break;

            case R.id.band3:
            case R.id.changeBand3:
                changeBand3();
                break;

            case R.id.multiplicator:
            case R.id.changeMultiplicator:
                changeMulti();
                break;

            case R.id.tolerance:
            case R.id.changeTolerance:
                changeTolerance();
                break;
        }
    }

    private void changeBand1() {
        BaseAdapter adapter = new BandAdapter( this, getBandColors(), false );

        getColorDialog().setAdapter( adapter, ( dialog, item ) -> {
            viewmodel.setBand1( item );
        } ).create().show();
    }

    private void changeBand2() {
        BaseAdapter adapter = new BandAdapter( this, getBandColors(), false );

        getColorDialog().setAdapter( adapter, ( dialog, item ) -> {
            viewmodel.setBand2( item );
        } ).create().show();
    }

    private void changeBand3() {
        BaseAdapter adapter = new BandAdapter( this, getBandColors(), false );

        getColorDialog().setAdapter( adapter, ( dialog, item ) -> {
            viewmodel.setBand3( item );
        } ).create().show();
    }

    private AlertDialog.Builder getColorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle( getResources().getString( R.string.choose_color) );
        return builder;
    }



    private void changeMulti() {
        BaseAdapter adapter = new BandAdapter( this, getMultiplicatorColors(), false );

        getColorDialog().setAdapter( adapter, ( dialog, item ) -> {
            viewmodel.setMulti( getMultiplicatorColors().get( item ).getValue() );
        } ).create().show();
    }

    private void changeTolerance() {
        BaseAdapter adapter = new BandAdapter( this, getToleranceColors(), true );

        getColorDialog().setAdapter( adapter, ( dialog, item ) -> {
            viewmodel.setTol( getToleranceColors().get( item ).getValue() );
        } ).create().show();
    }


    private void save() {
        try {
            int amount = Integer.parseInt( etAmount.getText().toString() );
            save( amount );
        } catch( Exception e ) {
            invalidInput();
        }
    }

    private void save( int amount ) {

        double resistance = 0;
        try {
            resistance = Float.parseFloat( etResistance.getText().toString() );
        } catch( NumberFormatException e ) {
            invalidInput();
            return;
        }

        double tolerance = newestTolerance;

        if( resistance == 0 || amount == 0 ) {
            Snackbar.make( etResistance, "No 0 allowed!", Snackbar.LENGTH_INDEFINITE );

        } else {
            Intent replyIntent = new Intent();
            replyIntent.putExtra( RESISTANCE, resistance );
            replyIntent.putExtra( AMOUNT, amount );
            replyIntent.putExtra( TOLERANCE, tolerance);
            setResult( RESULT_OK, replyIntent );
            finish();
        }

    }

    private void invalidInput() {
        Log.e( TAG, "Check your Input!" );
        Snackbar.make( etResistance, "Check your Input!", Snackbar.LENGTH_INDEFINITE );
    }

    private void moveResultToEdidText() {
        etResistance.setText( shortFloat( newestResistance ) );
    }

}
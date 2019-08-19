package website.dango.resistor;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static website.dango.resistor.CalculationActivity.keyAvailableResistors;
import static website.dango.resistor.CalculationActivity.keyMaxResistors;
import static website.dango.resistor.CalculationActivity.keyTotalResistor;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Thrown in MainActivity";

    private MainActivityViewmodel mViewModel;
    private RecyclerView recyclerView;
    private CheckBox cbAllSelected;

    private ResistorListAdapter adapter;
    private List<Resistor> mResistors;
    private ArrayList<Double> selectedResistors;
    private int mode = 0;
    private double totalResistance = 0;

    public static final int NEW_RESISTOR_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        recyclerView = findViewById( R.id.recyclerview );
        FloatingActionsMenu fabMenu = findViewById( R.id.fab );
        cbAllSelected = findViewById( R.id.selectAll );

        mViewModel = ViewModelProviders.of( this ).get( MainActivityViewmodel.class );


        com.getbase.floatingactionbutton.FloatingActionButton newResistorFab = findViewById( R.id.new_resistor );
        newResistorFab.setOnClickListener( view -> {

            Intent intent = new Intent( MainActivity.this, NewResistorActivity.class );
            startActivityForResult( intent, NEW_RESISTOR_ACTIVITY_REQUEST_CODE );
        } );

        LayoutInflater layoutInflater = LayoutInflater.from( this );
        final View newCalcForm = layoutInflater.inflate( R.layout.new_calculation, null );

        com.getbase.floatingactionbutton.FloatingActionButton newCalcFab = findViewById( R.id.new_calc );
        newCalcFab.setOnClickListener( fab -> {

            selectedResistors = new ArrayList<>();

            for( int z = 0; z < mResistors.size(); z++ ) {

                boolean selected = adapter.isChecked( mResistors.get( z ).getResistance() );
                if( selected ) {
                    int amount = mResistors.get( z ).getAmount();
                    for( int i = 0; i < amount; i++ ) {
                        selectedResistors.add( mResistors.get( z ).getResistance() );
                    }
                }
            }
            if( selectedResistors.isEmpty() || selectedResistors.size() == 1 ) {
                fabMenu.collapse();
                Snackbar.make( recyclerView, getBaseContext().getString( R.string.min_2_resistors), Snackbar.LENGTH_LONG ).show();
                return;
            }


            if( newCalcForm.getParent() != null ) {
                ((ViewGroup) newCalcForm.getParent()).removeView( newCalcForm );
            }

            new AlertDialog.Builder( MainActivity.this )
                    .setCancelable( true )
                    .setView( newCalcForm )
                    .setTitle( getString( R.string.new_calculation ) )
                    .setPositiveButton( getBaseContext().getText( R.string.find_best_combination), ( dialogInterface, i ) -> {

                        if( validNewCalccForm() ) {

                            Intent intent = new Intent( this, CalculationActivity.class );

                            intent.putExtra( keyAvailableResistors, selectedResistors );
                            intent.putExtra( keyMaxResistors, mode );
                            intent.putExtra( keyTotalResistor, totalResistance );
                            startActivity( intent );

                        } else
                            Toast.makeText( this, getBaseContext().getString( R.string.something_invalid ), Toast.LENGTH_SHORT ).show();

                    } ).show();

            TextView tvSelectedResistors = newCalcForm.findViewById( R.id.numberOfAvilableResistors );
            tvSelectedResistors.setText( getString( R.string.circuit_realized_N_res, selectedResistors.size() ) );

            Spinner spinnerMaxResistors = newCalcForm.findViewById( R.id.numberMaxUsedInNextCircuit );
            ArrayAdapter<String> adapter = new ArrayAdapter<>( this,
                    android.R.layout.simple_spinner_item, new String[]{ "   1   ", "   2   ", "   3   ", "   4   " } );
            spinnerMaxResistors.setAdapter( adapter );
            spinnerMaxResistors.setOnItemSelectedListener( new Spinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected( AdapterView<?> adapterView, View view, int position, long id ) {
                    mode = position + 1;
                }
                @Override
                public void onNothingSelected( AdapterView<?> adapterView ) {
                    Toast.makeText( MainActivity.this, getString( R.string.choose_max_amount), Toast.LENGTH_LONG ).show();
                }
            } );

            EditText totalResistance = newCalcForm.findViewById( R.id.totalResistance );
            totalResistance.addTextChangedListener( new TextWatcher() {
                @Override
                public void beforeTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {}
                @Override
                public void onTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {}
                @Override
                public void afterTextChanged( Editable editable ) {
                    if( !editable.toString().isEmpty() )
                        MainActivity.this.totalResistance = Float.parseFloat( editable.toString() );
                }
            } );
        });

        adapter = new ResistorListAdapter( this );
        recyclerView.setAdapter( adapter );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener( this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick( View view, int position ) {

                        double resistance = mResistors.get( position ).getResistance();
                        boolean oldValue = adapter.isChecked( resistance );
                        adapter.setChecked( !oldValue, resistance );
                        CheckBox checkBox = view.findViewById( R.id.checkbox );
                        checkBox.setChecked( !oldValue );
                    }
                    @Override
                    public void onLongItemClick( View view, int position ) {

                        Resistor resistor = mResistors.get( position );

                        // TODO: extract array
                        String[] options = { "Edit Amount", "Delete", "Add Similar" };

                        AlertDialog.Builder builder = new AlertDialog.Builder( MainActivity.this );
                        builder.setTitle( "Select Action" )
                                .setCancelable( true )
                                .setItems( options, ( DialogInterface dialog, int which ) -> {
                                    dialog.dismiss();
                                    dialog.cancel();
                                    switch( which ) {
                                        case 0:
                                            editResistor( resistor );
                                            break;
                                        case 1:
                                            delete( resistor );
                                            break;
                                        case 2:
                                            addSimilar( resistor );
                                    }
                        } );
                        builder.show();

                    }
                } )
        );

        mViewModel.getAllResistors().observe(this, resistors -> {
            // Update the cached copy of the words in the adapter.
            adapter.setResistors( resistors, cbAllSelected.isChecked() );
            mResistors = resistors;
            if( resistors.size() == 0 )
                findViewById( R.id.help_short ).setVisibility( View.VISIBLE );
            if( resistors.size() != 0 )
                findViewById( R.id.help_short ).setVisibility( View.GONE );
        } );
    }

    private boolean validNewCalccForm(){
        boolean valid = mode != 0 && selectedResistors.size() != 0
                && mode <= selectedResistors.size() && totalResistance != 0;
        return valid;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_main, menu );

        if(menu instanceof MenuBuilder ){
            MenuBuilder m = (MenuBuilder) menu;
            //noinspection RestrictedApi
            m.setOptionalIconsVisible( true );
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch( item.getItemId() ) {

            case R.id.action_about :

                intent = new Intent( this, AboutActivity.class );
                startActivity( intent );
                return true;

            case R.id.action_help :

                intent = new Intent( this, HelpActivity.class );
                startActivity( intent );
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult( int requestCode, int resultCode, Intent data ) {
        super.onActivityResult( requestCode, resultCode, data );

        if( requestCode == NEW_RESISTOR_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK ) {
            Resistor resistor = new Resistor(
                    data.getDoubleExtra( NewResistorActivity.RESISTANCE, 0 ),
                    data.getIntExtra( NewResistorActivity.AMOUNT, 0 ),
                    data.getDoubleExtra( NewResistorActivity.TOLERANCE, 0 )
            );
            mViewModel.insert( resistor );
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG ).show();
        }
    }


    public void selectAll( View view ) {
        if( view.getId() == R.id.selectAllText )
            cbAllSelected.setChecked( !cbAllSelected.isChecked() );
        adapter.setResistors( mResistors, cbAllSelected.isChecked() );
    }



    public void delete( Resistor resistor ) {
        new AlertDialog.Builder( MainActivity.this )
                .setCancelable( true )
                .setMessage( getString( R.string.really_delete) )
                .setTitle( getString( R.string.delete) )
                .setPositiveButton( getString( R.string.yes), ( dialogInterface, i ) -> {

                    mViewModel.deleteResistorByResistance( resistor.getId() );
                } )
                .setNegativeButton( getString( R.string.no), (( dialogInterface, i ) -> {
                    dialogInterface.dismiss();
                }) )
                .show();
    }

    public void editResistor( Resistor resistor ) {
        Dialog d = new Dialog( MainActivity.this );
        d.setCancelable( true );
        d.setContentView( R.layout.alert_edit_resistor );
        Button confirm = d.findViewById( R.id.confirm );
        NumberPicker np = d.findViewById( R.id.numberPicker );
        np.setMinValue( 0 );
        np.setMaxValue( 1000 );
        np.setWrapSelectorWheel( true );
        confirm.setOnClickListener( button -> {
            resistor.setAmount( np.getValue() );
            mViewModel.insert( resistor );
            d.dismiss();
        } );
        d.show();
    }

    public void addSimilar( Resistor resistor ) {

        Dialog d = new Dialog( MainActivity.this );
        d.setCancelable( true );
        d.setContentView( R.layout.alert_add_similar );

        RecyclerView recyclerView = d.findViewById( R.id.similarResistors );
        SimilarResistorListAdapter adapterS = new SimilarResistorListAdapter( this );
        recyclerView.setAdapter( adapterS );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener( this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick( View view, int position ) {

                        Resistor resistor = adapterS.getResistors().get( position );
                        boolean oldValue = adapterS.isChecked( resistor );
                        adapterS.setChecked( !oldValue, resistor );
                        CheckBox checkBox = view.findViewById( R.id.checkbox );
                        checkBox.setChecked( !oldValue );
                    }
                    @Override
                    public void onLongItemClick( View view, int position ) {}
                } )
        );
        adapterS.setResistor( resistor, false );

        CheckBox checkAll = d.findViewById( R.id.checkAll );

        checkAll.setOnClickListener( button -> {
            adapterS.setResistor( resistor, checkAll.isChecked() );
        } );

        Button confirm = d.findViewById( R.id.confirm );

        confirm.setOnClickListener( button -> {

            HashMap<Resistor, Boolean> checkMap = adapterS.getLiveChecks().getValue();

            Iterator it = checkMap.entrySet().iterator();
            while( it.hasNext() ) {
                Map.Entry pair = (Map.Entry) it.next();
                if( (Boolean) pair.getValue() )
                    mViewModel.insert( (Resistor) pair.getKey() );

                System.out.println( pair.getKey() + " = " + pair.getValue() );
                it.remove(); // avoids a ConcurrentModificationException
            }

            d.dismiss();
        } );
        d.show();

        Window window = d.getWindow();
        window.setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT );
    }
}

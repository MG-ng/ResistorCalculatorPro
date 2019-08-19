package website.dango.resistor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Math.pow;
import static website.dango.resistor.Helper.addEnding;

public class SimilarResistorListAdapter extends RecyclerView.Adapter<SimilarResistorListAdapter.ResistorViewHolder> {

    private static final String TAG = "SimilarResistorListAdap";


    class ResistorViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvResistanceValue, tvResistorTimestamp, tvAmount;
        private final AppCompatCheckBox resistorCheckBox;

        private ResistorViewHolder( View itemView ) {
            super( itemView );
            tvResistanceValue = itemView.findViewById( R.id.resistanceTitle );
            tvResistorTimestamp = itemView.findViewById( R.id.resistanceTimeStamp );
            resistorCheckBox = itemView.findViewById( R.id.checkbox );
            tvAmount = itemView.findViewById( R.id.amount );
        }
    }

    private final LayoutInflater mInflater;
    private List<Resistor> mResistors; // Cached copy of words
    private boolean mAllSelected;
    private MutableLiveData<HashMap<Resistor, Boolean>> checkHashMap = new MutableLiveData<>();


    SimilarResistorListAdapter( Context context ) {
        mInflater = LayoutInflater.from( context );
    }

    @Override
    public ResistorViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View itemView = mInflater.inflate( R.layout.recyclerview_item, parent, false );
        return new ResistorViewHolder( itemView );
    }

    @SuppressLint( "SetTextI18n" )
    @Override
    public void onBindViewHolder( @NonNull final ResistorViewHolder holder, final int position ) {
        if( mResistors != null ) {
            Resistor current = mResistors.get( position );
            holder.tvResistanceValue.setText( addEnding( current.getResistance() ) + " Ω" );
            holder.tvAmount.setText( current.getAmount() + "" );
            holder.tvResistorTimestamp.setText( "± "  + (100 * current.getTolerance()) + " %" );
            holder.resistorCheckBox.setChecked( mAllSelected );

            holder.resistorCheckBox.setOnCheckedChangeListener( ( compoundButton, isChecked ) -> {
                setChecked( isChecked, current );
            } );

        } else {
            // Covers the case of data not being ready yet.
            holder.tvResistanceValue.setText( "Einen Moment..." );
        }
    }

    /**
     * @param baseResistor is the base of the calculation of similar resistors
     * @param allSelected sets all checkboxes at once
     */
    void setResistor( Resistor baseResistor, boolean allSelected ) {
        List<Resistor> resistors = new ArrayList<>();

        double resistance = baseResistor.getResistance();

        String resistanceS = String.format( "%1.3e", resistance );
        Log.d( TAG, "resistanceS : " + resistanceS );

        int exponent = Integer.parseInt( String.valueOf( resistanceS.charAt( resistanceS.length() - 3 ) ) +
                resistanceS.charAt( resistanceS.length() - 1 ) );

        Log.d( TAG, "exponent: " + exponent );

        // Largest multi is white (10^9 | G)
        // Adds all other multis
        for( int i = -2; i < 10; i++ ) {
            if( i != exponent ) {
                resistors.add( new Resistor( resistance * pow( 10d, i - exponent ), 1, 0f ) );
                Log.d( TAG, "added i: " + i );
            }
        }

        mResistors = resistors;
        mAllSelected = allSelected;

        HashMap<Resistor, Boolean> tmp = new HashMap<Resistor, Boolean>();
        for( Resistor resistor : resistors ) {
            tmp.put( resistor, allSelected );
        }
        checkHashMap.setValue( tmp );
        notifyDataSetChanged();
    }

    public void setChecked( boolean isChecked, Resistor resistor ){
        HashMap<Resistor, Boolean> tmp = (HashMap<Resistor, Boolean>) checkHashMap.getValue().clone();
        tmp.put( resistor, isChecked );
        checkHashMap.setValue( tmp );
    }

    public boolean isChecked( Resistor resistor ){
        HashMap<Resistor, Boolean> tmp = checkHashMap.getValue();
        return tmp.get( resistor );
    }

    public LiveData<HashMap<Resistor, Boolean>> getLiveChecks() {
        return checkHashMap;
    }
    public List<Resistor> getResistors() {
        return mResistors;
    }


    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if( mResistors != null ) {
            return mResistors.size();
        } else {
            return 0;
        }
    }
}

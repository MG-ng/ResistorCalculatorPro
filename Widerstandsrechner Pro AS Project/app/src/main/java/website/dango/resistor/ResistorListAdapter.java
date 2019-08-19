package website.dango.resistor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import static website.dango.resistor.Helper.addEnding;

public class ResistorListAdapter extends RecyclerView.Adapter<ResistorListAdapter.ResistorViewHolder> {

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
    private MutableLiveData<HashMap<Double, Boolean>> checkHashMap = new MutableLiveData<>();
    private Context context;


    ResistorListAdapter( Context context ) {
        mInflater = LayoutInflater.from( context );
        this.context = context;
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
                setChecked( isChecked, current.getResistance() );
            } );

        } else {
            // Covers the case of data not being ready yet.
            holder.tvResistanceValue.setText( context.getString( R.string.wait ) );
        }
    }

    void setResistors( List<Resistor> resistors, boolean allSelected ) {
        mResistors = resistors;
        mAllSelected = allSelected;

        HashMap<Double, Boolean> tmp = new HashMap<Double, Boolean>();
        for( Resistor resistor : resistors ) {
            tmp.put( resistor.getResistance(), allSelected );
        }
        checkHashMap.setValue( tmp );
        notifyDataSetChanged();
    }

    public void setChecked( boolean isChecked, double keyResistance ){
        HashMap<Double, Boolean> tmp = (HashMap<Double, Boolean>) checkHashMap.getValue().clone();
        tmp.put( keyResistance, isChecked );
        checkHashMap.setValue( tmp );
    }

    public boolean isChecked( double keyResistance ){
        HashMap<Double, Boolean> tmp = checkHashMap.getValue();
        return tmp.get( keyResistance );
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

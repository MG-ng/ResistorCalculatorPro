package website.dango.resistor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

import static website.dango.resistor.Helper.shortFloat;

public class ResistorView extends ConstraintLayout {

    private static final String TAG = "ResistorView";


    private Resistor mResistor;
    private Integer mIndex;

    private Context context;

    public ResistorView( Context context ) {
        super( context );
        this.context = context;
    }

    public void setResistor( int index, Resistor resistor ) {
        mResistor = resistor;
        mIndex = index;


        if( mResistor == null || mIndex == null ) return;


        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate( R.layout.compound_resistor_view, this, true );


        TextView indexView = view.findViewById( R.id.index );
        indexView.setText( context.getString( R.string.resistor ) + " " + ( mIndex + 1 ) + ": "  );

        TextView title = view.findViewById( R.id.resistance );
        title.setText( shortFloat( mResistor.getResistance() ) + " Ω" );

        LinearLayout layout = view.findViewById( R.id.colors );

        List<BandColor> colors = mResistor.getBandColors();
        Log.e( TAG, "colors: " + colors.toString() );

        for( BandColor color : colors ) {
            View colorView = new View( context );

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    getResources().getInteger( R.integer.resistor_view_color_width ),
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            layoutParams.setMargins( 10, 0, 10, 0 );
            colorView.setLayoutParams( layoutParams );
            colorView.setBackgroundResource( R.drawable.dashed_border );

            if( color != null )
                colorView.setBackgroundResource( color.getResColorId() );
            else
                Log.e( TAG, "color == null" );

            layout.addView( colorView, layoutParams );
        }

    }
}

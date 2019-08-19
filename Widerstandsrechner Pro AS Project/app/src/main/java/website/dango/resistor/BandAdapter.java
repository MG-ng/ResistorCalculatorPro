package website.dango.resistor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import static website.dango.resistor.Helper.addEnding;
import static website.dango.resistor.Helper.shortFloat;


public class BandAdapter extends BaseAdapter {

    // ArrayList<String> name, company, email, id, status;
    List<BandColor> colors;
    boolean displayInPercent = false;
    Context c;

    public BandAdapter( Context c, List<BandColor> list, boolean displInPercent ) {
        colors = list;
        this.c = c;
        displayInPercent = displInPercent;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return colors.size();
    }

    @Override
    public Object getItem( int position ) {
        // TODO Auto-generated method stub
        return colors.get( position );
    }

    @Override
    public long getItemId( int position ) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent ) {
        // TODO Auto-generated method stub
        View row = null;
        LayoutInflater inflater = (LayoutInflater) c
                .getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        if( convertView == null ) {
            row = inflater.inflate( R.layout.band_color_list_item, parent,
                    false );
        } else {
            row = convertView;
        }
        BandColor bandColor = colors.get( position );
        TextView name = row.findViewById( R.id.name );
        name.setText( c.getResources().getString( bandColor.getName() ) );
        TextView value = row.findViewById( R.id.value );
        if( displayInPercent )
            value.setText( "( " + shortFloat(bandColor.getValue() * 100) + "% )" );
        else {
            value.setText( "( " + addEnding( bandColor.getValue() ) + " )" );
        }

        View box = row.findViewById( R.id.bandColor );
        box.setBackgroundColor( c.getResources().getColor( bandColor.getResColorId() ) );
        return row;
    }

}

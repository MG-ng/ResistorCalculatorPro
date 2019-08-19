package website.dango.resistor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_about );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        Objects.requireNonNull( getSupportActionBar() ).setDisplayHomeAsUpEnabled( true );


        FloatingActionButton fabWebsite = findViewById( R.id.visitWebsite );
        FloatingActionButton fabCode = findViewById( R.id.visitCode );


        fabWebsite.setOnClickListener( view -> {

            Intent webIntent = new Intent( Intent.ACTION_VIEW, Uri.parse( "https://play.google.com/store/apps/developer?id=DANGO+Web" ) );
            startActivity( webIntent );
        } );

        fabCode.setOnClickListener( view -> {

            Intent webIntent = new Intent( Intent.ACTION_VIEW, Uri.parse( "https://github.com/MG-ng/ResistorCalculatorPro" ) );
            startActivity( webIntent );
        } );
    }

}

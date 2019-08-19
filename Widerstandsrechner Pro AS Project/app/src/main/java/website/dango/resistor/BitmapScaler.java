package website.dango.resistor;

import android.content.res.Resources;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class BitmapScaler {

  // Scale and maintain aspect ratio given a desired width

  public static Bitmap scaleToFitWidth( Bitmap bitmap, int width, boolean widthInDP )
  {

    if( widthInDP ) {
      width = dpToPx( width );
    }

    double factor = width / (double) bitmap.getWidth();

    return Bitmap.createScaledBitmap( bitmap, width, (int) (bitmap.getHeight() * factor), true );
  }

  // Scale and maintain aspect ratio given a desired height

  public static Bitmap scaleToFitHeight( @NonNull Bitmap bitmap, int height, boolean heightInDP )
  {
    if( heightInDP ) {
      height = dpToPx( height );
    }

    double factor = height / (double) bitmap.getHeight();

    return Bitmap.createScaledBitmap( bitmap, (int) (bitmap.getWidth() * factor), height, true );
  }


  public static int dpToPx( int dp )
  {
    return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
  }

  public static int pxToDp( int px )
  {
    return (int) (px / Resources.getSystem().getDisplayMetrics().density);
  }


}

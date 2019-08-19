package website.dango.resistor;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainActivityViewmodel extends AndroidViewModel {

    private Repository mRepository;
    private LiveData<List<Resistor>> mAllResistors;

    public MainActivityViewmodel( Application application ) {

        super(application);
        mRepository = new Repository( application );
        mAllResistors = mRepository.getAllResistors();

    }

    LiveData<List<Resistor>> getAllResistors() { return mAllResistors; }

    void deleteResistorByResistance( int resistance ) {
        mRepository.deleteResistorByResistance( resistance );
    }

    public void insert( Resistor resistor ) {
        mRepository.insert( resistor );
    }

}

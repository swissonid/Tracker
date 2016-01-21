package ch.swissonid.tracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ch.swissonid.tracker.di.components.AndroidComponent;
import ch.swissonid.tracker.di.components.AppComponent;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceStat) {
        super.onCreate(savedInstanceStat);
//        getAppComponent().inject(this);
    }

    protected TrackerApp getApp(){
        return ((TrackerApp) getApplication());
    }

    protected AppComponent getAppComponent(){
        return getApp().getAppComponent();
    }

    protected AndroidComponent getAndroidComponent(){
        return getApp().getAndroidComponent();
    }
}

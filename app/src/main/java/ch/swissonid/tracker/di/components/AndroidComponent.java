package ch.swissonid.tracker.di.components;

import javax.inject.Singleton;

import ch.swissonid.tracker.OldMainActivity;
import ch.swissonid.tracker.di.modules.AndroidModule;
import ch.swissonid.tracker.di.modules.AppModule;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, AndroidModule.class})
public interface AndroidComponent {
    void inject(final OldMainActivity baseActivity);
}

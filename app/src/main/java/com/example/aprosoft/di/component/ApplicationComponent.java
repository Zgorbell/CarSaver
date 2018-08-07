package com.example.aprosoft.di.component;

import android.content.Context;

import com.example.aprosoft.App;
import com.example.aprosoft.data.db.AppDatabase;
import com.example.aprosoft.di.module.ApplicationModule;
import com.example.aprosoft.di.module.RepositoryModule;
import com.example.aprosoft.di.module.StorageModule;
import com.example.aprosoft.ui.base.Repository;
import com.example.aprosoft.ui.car.CarPresenter;
import com.example.aprosoft.ui.filter.FilterPresenter;
import com.example.aprosoft.ui.main.MainPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, StorageModule.class, RepositoryModule.class})
public interface ApplicationComponent {
    void inject(App app);

    void injectPresenter(MainPresenter presenter);

    void injectPresenter(FilterPresenter presenter);

    void injectPresenter(CarPresenter presenter);

    Context applicationContext();

    AppDatabase database();

    Repository repository();
}

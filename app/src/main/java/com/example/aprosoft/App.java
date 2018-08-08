package com.example.aprosoft;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.aprosoft.data.db.AppDatabase;
import com.example.aprosoft.data.db.model.Manufacturer;
import com.example.aprosoft.di.component.ApplicationComponent;
import com.example.aprosoft.di.component.DaggerApplicationComponent;
import com.example.aprosoft.di.module.ApplicationModule;
import com.example.aprosoft.di.module.StorageModule;

import java.util.List;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class App extends Application {
    private static final String TAG = App.class.getSimpleName();
    private static final String DATABASE_NAME = "database";
    private static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .storageModule(new StorageModule(DATABASE_NAME)).build();

        applicationComponent.inject(this);
        Log.e(TAG, getDatabasePath(DATABASE_NAME).getAbsolutePath());

        read();
    }

    public static Context getContext() {
        return applicationComponent.applicationContext();
    }

    public static AppDatabase getDatabase() {
        return applicationComponent.database();
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public void read() {
        getDatabase().manufacturerDao().getManufacturers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Manufacturer>>() {
                    @Override
                    public void onSuccess(List<Manufacturer> manufacturers) {
                        Log.d(TAG, " " + manufacturers.size());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

}

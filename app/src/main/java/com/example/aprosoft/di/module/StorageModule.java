package com.example.aprosoft.di.module;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;

import com.example.aprosoft.App;
import com.example.aprosoft.data.db.AppDatabase;
import com.example.aprosoft.data.state.FilterStateHelper;
import com.example.aprosoft.data.state.SortStateHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

@Module
public class StorageModule {
    private String databaseName;

    public StorageModule(String databaseName) {
        this.databaseName = databaseName;
    }

    @Provides
    @Singleton
    AppDatabase provideDatabase(Context context){
        return Room.databaseBuilder(context, AppDatabase.class, databaseName)
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Observable.fromCallable(() -> {
                            App.getDatabase().prePopulateDao().prepare();
                            return new Object();
                        })
                                .subscribeOn(Schedulers.io())
                                .subscribe();
                        ;
                    }
                })
                .build();
    }

    @Provides
    @Singleton
    FilterStateHelper provideFilterStateHelper(){
        return new FilterStateHelper();
    }

    @Provides
    @Singleton
    SortStateHelper provideSortStateHelper(){return new SortStateHelper();}
}

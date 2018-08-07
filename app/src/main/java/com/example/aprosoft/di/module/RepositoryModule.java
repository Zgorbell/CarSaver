package com.example.aprosoft.di.module;

import com.example.aprosoft.data.db.AppDatabase;
import com.example.aprosoft.ui.base.Repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    Repository provideBaseRepository(AppDatabase appDatabase){
        return new Repository(appDatabase);
    }
}

package com.example.aprosoft.data.db.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.aprosoft.data.db.model.Model;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class ModelDao {

//    @Query("Select * from model")
//    public abstract Single<List<Model>> getModels();

    @Query("Select * from model where :manufacturerId = model.manufacturer_id")
    public abstract Single<List<Model>> getModels(int manufacturerId);

    @Query("Select * from model")
    public abstract Single<List<Model>> getModels();

    @Insert
    public abstract void insert(Model model);

    @Delete
    public abstract void delete(Model model);
}

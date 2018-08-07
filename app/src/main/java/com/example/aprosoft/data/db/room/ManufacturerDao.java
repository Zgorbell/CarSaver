package com.example.aprosoft.data.db.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.aprosoft.data.db.model.Manufacturer;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class ManufacturerDao {

    @Query("Select * from manufacturer")
    public abstract Single<List<Manufacturer>> getManufacturers();

    @Insert
    public abstract void insert(Manufacturer manufacturer);

    @Delete
    public abstract void delete(Manufacturer manufacturer);
}

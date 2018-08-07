package com.example.aprosoft.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.aprosoft.data.db.model.Car;
import com.example.aprosoft.data.db.model.Manufacturer;
import com.example.aprosoft.data.db.model.Model;
import com.example.aprosoft.data.db.room.CarDao;
import com.example.aprosoft.data.db.room.ManufacturerDao;
import com.example.aprosoft.data.db.room.ModelDao;
import com.example.aprosoft.data.db.room.PrePopulateDao;

@Database(entities = {Car.class, Manufacturer.class, Model.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{

    public abstract CarDao carDao();

    public abstract ManufacturerDao manufacturerDao();

    public abstract ModelDao modelDao();

    public abstract PrePopulateDao prePopulateDao();
}

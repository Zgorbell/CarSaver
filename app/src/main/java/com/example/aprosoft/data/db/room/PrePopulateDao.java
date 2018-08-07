package com.example.aprosoft.data.db.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Transaction;

import com.example.aprosoft.App;
import com.example.aprosoft.data.db.model.Car;
import com.example.aprosoft.data.db.model.Manufacturer;
import com.example.aprosoft.data.db.model.Model;

@Dao
public abstract class PrePopulateDao {

    @Transaction
    public void prepare(){
        App.getDatabase().manufacturerDao().insert(new Manufacturer("Skoda"));
        App.getDatabase().manufacturerDao().insert(new Manufacturer("BMW"));
        App.getDatabase().manufacturerDao().insert(new Manufacturer("Volkswagen"));

        App.getDatabase().modelDao().insert(new Model(1, "Octavia"));
        App.getDatabase().modelDao().insert(new Model(1, "Rapid"));
        App.getDatabase().modelDao().insert(new Model(1, "Kodiaq"));
        App.getDatabase().modelDao().insert(new Model(2, "2 Series"));
        App.getDatabase().modelDao().insert(new Model(2, "X5"));
        App.getDatabase().modelDao().insert(new Model(2, "i8"));
        App.getDatabase().modelDao().insert(new Model(3, "Passat B3"));
        App.getDatabase().modelDao().insert(new Model(3, "Golf 6"));
        App.getDatabase().modelDao().insert(new Model(3, "Sharan"));

        App.getDatabase().carDao().insert(
                new Car(1,30000.0,null,"Auto","Wagon","Petrol",300.0));
        App.getDatabase().carDao().insert(
                new Car(2,10000.0, null, "Auto","Sedan","Petrol",150.0));
        App.getDatabase().carDao().insert(
                new Car(1,32000.0, null,"Manual","Wagon","Diesel",260.0));
        App.getDatabase().carDao().insert(
                new Car(3,25000.0,null,"Auto","Crossover",	"Diesel",220.0));
        App.getDatabase().carDao().insert(
                new Car(7,3000.0, null, "Manual","Wagon","Diesel",110.0));
        App.getDatabase().carDao().insert(
                new Car(8,10000.0, null, "Auto","Hatchback","Petrol",140.0));
        App.getDatabase().carDao().insert(
                new Car(9,10000.0, null, "Manual","Minivan","Diesel",130.0));
        App.getDatabase().carDao().insert(
                new Car(4,40000.0, null, "Auto","Coupe","Petrol",250.0));
        App.getDatabase().carDao().insert(
                new Car(5,60000.0, null, "Auto","Crossover","Petrol",320.0));
        App.getDatabase().carDao().insert(
                new Car(5,80000.0, null, "Auto","Crossover","Petrol",400.0));
        App.getDatabase().carDao().insert(
                new Car(6,25000.0, null,"Robotic","Roadster","Petrol",370.0));
        App.getDatabase().carDao().insert(
                new Car(1,3500.0,null, "Manual","Wagon","Diesel",110.0));
    }
}

package com.example.aprosoft.data.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;
import static android.arch.persistence.room.ForeignKey.SET_NULL;

@Entity(foreignKeys = @ForeignKey(entity = Model.class,
        parentColumns = "id",
        childColumns = "model_id",
        deferred = true, onDelete = SET_NULL, onUpdate = CASCADE))
public class Car {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "model_id")
    private int modelId;
    private double price;
    private String photoPath;
    private String transmission;
    private String body;
    private String engine;
    private double engineCapacity;

    public Car(int modelId, double price, String photoPath, String transmission, String body, String engine, double engineCapacity) {
        this.modelId = modelId;
        this.price = price;
        this.photoPath = photoPath;
        this.transmission = transmission;
        this.body = body;
        this.engine = engine;
        this.engineCapacity = engineCapacity;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public int getModelId() {
        return modelId;
    }

    public double getPrice() {
        return price;
    }

    public String getTransmission() {
        return transmission;
    }

    public String getBody() {
        return body;
    }

    public String getEngine() {
        return engine;
    }

    public double getEngineCapacity() {
        return engineCapacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

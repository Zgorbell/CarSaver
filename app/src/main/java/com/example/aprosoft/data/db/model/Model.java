package com.example.aprosoft.data.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Manufacturer.class,
        parentColumns = "id",
        childColumns = "manufacturer_id",
        deferred = true,onDelete = CASCADE, onUpdate = CASCADE))
public class Model extends BaseModel{

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "manufacturer_id")
    private int manufactureId;
    private String name;

    public Model(int manufactureId, String name) {
        this.manufactureId = manufactureId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getManufactureId() {
        return manufactureId;
    }

    public String getName() {
        return name;
    }
}

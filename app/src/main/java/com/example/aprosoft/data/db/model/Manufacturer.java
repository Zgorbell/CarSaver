package com.example.aprosoft.data.db.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Manufacturer extends BaseModel {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    public Manufacturer(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
}

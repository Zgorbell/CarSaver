package com.example.aprosoft.data.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.os.Parcel;
import android.os.Parcelable;

public class CarTotalInfo implements Parcelable {

    private int id;
    private double price;
    private String photoPath;
    private String transmission;
    private String body;
    private String engine;
    private double engineCapacity;
    @ColumnInfo(name = "model_name")
    private String modelName;
    @ColumnInfo(name = "model_id")
    private int modelId;
    @ColumnInfo(name = "manufacturer_name")
    private String manufacturerName;
    @ColumnInfo(name = "manufacturer_id")
    private int manufacturerId;


    public CarTotalInfo(int id,
                        double price,
                        String photoPath,
                        String transmission,
                        String body,
                        String engine,
                        double engineCapacity,
                        String modelName,
                        int modelId,
                        String manufacturerName,
                        int manufacturerId) {
        this.id = id;
        this.price = price;
        this.photoPath = photoPath;
        this.transmission = transmission;
        this.body = body;
        this.engine = engine;
        this.engineCapacity = engineCapacity;
        this.modelName = modelName;
        this.manufacturerName = manufacturerName;
        this.manufacturerId = manufacturerId;
        this.modelId = modelId;
    }

    public CarTotalInfo(Parcel in) {
        this.id = in.readInt();
        this.price = in.readDouble();
        this.photoPath = in.readString();
        this.transmission = in.readString();
        this.body = in.readString();
        this.engine = in.readString();
        this.engineCapacity = in.readDouble();
        this.modelName = in.readString();
        this.manufacturerName = in.readString();
        this.manufacturerId = in.readInt();
        this.modelId = in.readInt();
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getPhotoPath() {
        return photoPath;
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

    public String getModelName() {
        return modelName;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public int getModelId() {
        return modelId;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public Car getCar() {
        Car car =  new Car(modelId, price, photoPath, transmission, body, engine, engineCapacity);
        car.setId(id);
        return car;
    }

    public Car getNewCar(){
        return new Car(modelId, price, photoPath, transmission, body, engine, engineCapacity);
    }

    @Override
    public String toString() {
        return "CarTotalInfo{" +
                "id=" + id +
                ", price=" + price +
                ", photoPath='" + photoPath + '\'' +
                ", transmission='" + transmission + '\'' +
                ", body='" + body + '\'' +
                ", engine='" + engine + '\'' +
                ", engineCapacity=" + engineCapacity +
                ", modelName='" + modelName + '\'' +
                ", modelId=" + modelId +
                ", manufacturerName='" + manufacturerName + '\'' +
                ", manufacturerId=" + manufacturerId +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(price);
        dest.writeString(photoPath);
        dest.writeString(transmission);
        dest.writeString(body);
        dest.writeString(engine);
        dest.writeDouble(engineCapacity);
        dest.writeString(modelName);
        dest.writeString(manufacturerName);
        dest.writeInt(modelId);
        dest.writeInt(manufacturerId);
    }

    public static final Parcelable.Creator<CarTotalInfo> CREATOR = new Parcelable.Creator<CarTotalInfo>() {
        @Override
        public CarTotalInfo createFromParcel(Parcel source) {
            return new CarTotalInfo(source);
        }

        @Override
        public CarTotalInfo[] newArray(int size) {
            return new CarTotalInfo[size];
        }
    };
}

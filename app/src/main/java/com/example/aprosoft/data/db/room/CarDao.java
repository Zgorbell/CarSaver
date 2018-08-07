package com.example.aprosoft.data.db.room;

import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import com.example.aprosoft.data.db.model.Car;
import com.example.aprosoft.data.db.model.CarTotalInfo;

import java.io.File;
import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class CarDao {

    public static final String QUERY_SELECT_TOTAL_INFO =
            "Select car.*, manufacturer_id, manufacturer.name as manufacturer_name,"
                    + " model_id, model.name as model_name from car, manufacturer, model" +
                    " where car.model_id = model.id and model.manufacturer_id == manufacturer.id ";
    public static final String QUERY_LIMIT_END_START = "limit ? , ?";
    public static final String SORT_PRICE_ASC = "ORDER BY CAR.PRICE ASC ";
    public static final String SORT_PRICE_DESC = "ORDER BY CAR.PRICE DESC ";
    public static final String NO_SORT = " ORDER BY CAR.ID DESC ";
//    public static final String QUERY_ORDER_BY_CAR_PRICE = "ORDER BY CAR.PRICE ";
//    public static final String QUERY_ORDER_ASC = "ASC ";
//    public static final String QUERY_ORDER_DESC = "DESC ";
    public static final String QUERY_FILTER_BY_MANUFACTURER = "and manufacturer_id = ?";
    public static final String QUERY_FILTER_BY_MODEL = "and model_id = ?";
    public static final String QUERY_NO_FILTER = " ";

    @Update
    public abstract int update(Car car);

    @Insert
    public abstract long insert(Car car);

    @Delete
    public abstract int deleteCar(Car car);

    @RawQuery
    public abstract List<CarTotalInfo> getCarTotalInfoSort(SupportSQLiteQuery query);

    @Query("Select car.*, manufacturer_id, manufacturer.name as manufacturer_name,"
            + " model_id, model.name as model_name from car, manufacturer, model" +
            " where car.model_id = model.id and model.manufacturer_id == manufacturer.id " +
            " and car.id == :id ")
    public abstract Single<CarTotalInfo> getCarTotalInfo(int id);


//    @Query("Select car.*, manufacturer_id, manufacturer.name as manufacturer_name,"
//            + " model_id, model.name as model_name from car, manufacturer, model" +
//            " where car.model_id = model.id and model.manufacturer_id == manufacturer.id " +
//            "and manufacturer_id = :manufacturerId order by :sort limit :start , :end")
//    public abstract List<CarTotalInfo> getCarTotalInfoFilterManufacturerSort(int start, int end, int manufacturerId, String sort);
//
//    @Query("Select car.*, manufacturer_id, manufacturer.name as manufacturer_name,"
//            + " model_id, model.name as model_name from car, manufacturer, model" +
//            " where car.model_id = model.id and model.manufacturer_id == manufacturer.id " +
//            "and model_id = :modelId order by :sort limit :start, :end")
//    public abstract List<CarTotalInfo> getCarTotalInfoFilterModelSort(int start, int end, int modelId, String sort);

    @Transaction
    public int delete(Car car){
        if(car.getPhotoPath() != null) {
            File file = new File(car.getPhotoPath());
            file.delete();
        }
        return deleteCar(car);
    }
}

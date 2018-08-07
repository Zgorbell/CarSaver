package com.example.aprosoft.ui.base;

import android.arch.persistence.db.SupportSQLiteProgram;
import android.arch.persistence.db.SupportSQLiteQuery;

import com.example.aprosoft.data.db.AppDatabase;
import com.example.aprosoft.data.db.model.Car;
import com.example.aprosoft.data.db.model.CarTotalInfo;
import com.example.aprosoft.data.db.model.Manufacturer;
import com.example.aprosoft.data.db.model.Model;
import com.example.aprosoft.data.db.room.CarDao;
import com.example.aprosoft.ui.car.CarPresenter;
import com.example.aprosoft.ui.filter.FilterPresenter;
import com.example.aprosoft.ui.main.MainPresenter;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class Repository {
    private AppDatabase appDatabase;
    private MainPresenter mainPresenter;
    private FilterPresenter filterPresenter;
    private CarPresenter carPresenter;

    public Repository(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    public void setMainPresenter(MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
    }

    public void setFilterPresenter(FilterPresenter filterPresenter) {
        this.filterPresenter = filterPresenter;
    }

    public void setCarPresenter(CarPresenter carPresenter) {
        this.carPresenter = carPresenter;
    }

    public List<CarTotalInfo> downloadCarTotalInfoPageFromDb(
            int start, int limit, String sort, String filter, int filterId) {
        return appDatabase.carDao()
                .getCarTotalInfoSort(new SupportSQLiteQuery() {
                    @Override
                    public String getSql() {
                        return CarDao.QUERY_SELECT_TOTAL_INFO + filter + sort + CarDao.QUERY_LIMIT_END_START;
                    }

                    @Override
                    public void bindTo(SupportSQLiteProgram statement) {
                        int index = 1;
                        if (!filter.equals(CarDao.QUERY_NO_FILTER))
                            statement.bindLong(index++, filterId);
                        statement.bindLong(index++, start);
                        statement.bindLong(index, start + limit);
                    }

                    @Override
                    public int getArgCount() {
                        if (!filter.equals(CarDao.QUERY_NO_FILTER)) return 3;
                        else return 2;
                    }
                });
    }

    public void downloadCarTotalInfo(int carTotalInfoId) {
        appDatabase.carDao().getCarTotalInfo(carTotalInfoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<CarTotalInfo>() {
                    @Override
                    public void onSuccess(CarTotalInfo carTotalInfo) {
                        carPresenter.setCarTotalInfo(carTotalInfo);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void downloadManufacturers() {
        appDatabase.manufacturerDao().getManufacturers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Manufacturer>>() {
                    @Override
                    public void onSuccess(List<Manufacturer> manufacturers) {
                        if (filterPresenter != null)
                            filterPresenter.setManufacturerSpinner(manufacturers);
                        if (carPresenter != null)
                            carPresenter.setSpinnerManufacturer(manufacturers);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void downloadModels(){
        appDatabase.modelDao().getModels()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Model>>() {
                    @Override
                    public void onSuccess(List<Model> models) {
                        if (filterPresenter != null) filterPresenter.setModelSpinner(models);
                        if (carPresenter != null) carPresenter.setSpinnerModel(models);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void downloadModels(int manufacturerId) {
        appDatabase.modelDao().getModels(manufacturerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Model>>() {
                    @Override
                    public void onSuccess(List<Model> models) {
                        if (filterPresenter != null) filterPresenter.setModelSpinner(models);
                        if (carPresenter != null) carPresenter.setSpinnerModel(models);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void updateCar(Car car){
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        Disposable disposable = Observable.fromCallable(() -> appDatabase.carDao().update(car))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    if(carPresenter != null) carPresenter.setUpdateCarResult(integer);
                    if(mainPresenter != null) mainPresenter.setUpdateCarResult(integer);
                    compositeDisposable.dispose();
                });
        compositeDisposable.add(disposable);
    }

    public void insertCar(Car car){
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        Disposable disposable = Observable.fromCallable(() -> appDatabase.carDao().insert(car))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    if(carPresenter != null) carPresenter.setInsertCarResult(integer.intValue());
                    if(mainPresenter != null) mainPresenter.setInsertCarResult(integer.intValue());
                    compositeDisposable.dispose();
                });
        compositeDisposable.add(disposable);
    }

    public void deleteCar(Car car){
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        Disposable disposable = Observable.fromCallable(() -> appDatabase.carDao().delete(car))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    if(carPresenter != null) carPresenter.setDeleteCarResult(integer);
                    if(mainPresenter != null) mainPresenter.setDeleteCarResult(integer);
                    compositeDisposable.dispose();
                });
        compositeDisposable.add(disposable);
    }
}

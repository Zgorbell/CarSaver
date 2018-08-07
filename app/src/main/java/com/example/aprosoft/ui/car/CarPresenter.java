package com.example.aprosoft.ui.car;

//import android.util.Log;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.aprosoft.App;
import com.example.aprosoft.data.db.model.CarTotalInfo;
import com.example.aprosoft.data.db.model.Manufacturer;
import com.example.aprosoft.data.db.model.Model;
import com.example.aprosoft.ui.base.Repository;

import java.io.File;
import java.util.List;

import javax.inject.Inject;


@InjectViewState
public class CarPresenter extends MvpPresenter<CarMvpView> implements CarMvpPresenter{
    private static final String TAG = CarPresenter.class.getSimpleName();
    public static final int STATE_LIVE = 1;
    public static final int STATE_KILL = 0;
    public int state;
    @Inject
    Repository repository;
    private int idManufacturer, idModel;


    public CarPresenter() {
        super();
        App.getApplicationComponent().injectPresenter(this);
        repository.setCarPresenter(this);
        state = STATE_KILL;
    }

    @Override
    public void onCreate(int carTotalInfoId) {
        if(carTotalInfoId != CarActivity.MODE_NEW) {
            repository.downloadCarTotalInfo(carTotalInfoId);
            getViewState().changeMode(CarActivity.MODE_VIEW);
        }else if( carTotalInfoId == CarActivity.MODE_NEW){
            repository.downloadManufacturers();
            repository.downloadModels();
            getViewState().changeMode(CarActivity.MODE_NEW);
        }
    }

    @Override
    public void onSavedInstanceState() {
        state = STATE_LIVE;
        Log.e(TAG, "on saved instance state " + state);
    }

    @Override
    public void onRestoreInstanceState(int mode, CarTotalInfo carTotalInfo) {
        getViewState().changeMode(mode);
        if(carTotalInfo != null) setCarTotalInfo(carTotalInfo);
        state = STATE_KILL;
        Log.e(TAG, "on restore " + state);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        repository.setCarPresenter(null);
    }

    @Override
    public void onDestroyed(File photoFile) {
        Log.e(TAG, "on destroyed state " + state);
        if(state == STATE_KILL && photoFile != null) photoFile.delete();
    }

    @Override
    public void onSetDataComplete(int idManufacturer, int idModel) {
        this.idManufacturer = idManufacturer;
        this.idModel = idModel;
    }

    @Override
    public void onItemEditSelected() {
        getViewState().changeMode(CarActivity.MODE_EDIT);
    }

    @Override
    public void onItemSaveSelected(int mode, CarTotalInfo carTotalInfo) {
//        Log.e(TAG, "on save selected");
        if(mode == CarActivity.MODE_EDIT) repository.updateCar(carTotalInfo.getCar());
        else if(mode == CarActivity.MODE_NEW) repository.insertCar(carTotalInfo.getNewCar());
    }

    @Override
    public void onDeleteItemSelected(CarTotalInfo carTotalInfo) {
        repository.deleteCar(carTotalInfo.getCar());
    }

    @Override
    public void onManufacturerSpinnerItemSelected(int manufacturerId) {
        repository.downloadModels(manufacturerId);
    }

    @Override
    public void onAddPhotoClicked() {
        getViewState().startCameraActivity();
    }

    @Override
    public void onPhotoActivityResult(boolean status, File photoFile) {
        if(status) getViewState().setImageView(photoFile);
        else getViewState().deleteTemporaryPhotoFile();
    }

    public void setCarTotalInfo(CarTotalInfo carTotalInfo){
        repository.downloadManufacturers();
        repository.downloadModels();
        getViewState().setCarData(carTotalInfo);
    }

    public void setSpinnerManufacturer(List<Manufacturer> manufacturers){
        getViewState().setSpinnerManufacturer(manufacturers, idManufacturer);
    }

    public void setSpinnerModel(List<Model> models){
        getViewState().setSpinnerModel(models, idModel);
    }

    public void setUpdateCarResult(int count){
//        Log.e(TAG, "update car result, updated " + count);
        if(count == 1) getViewState().setResult();
        else getViewState().showMessage("Not saved, something is bad");
    }

    public void setInsertCarResult(int count){
//        Log.e(TAG, "insert car result, inserted " + count);
        if(count != 0) getViewState().setResult();
        else getViewState().showMessage("Not saved, something is bad");
    }

    public void setDeleteCarResult(int count){
//        Log.e(TAG, "delete car result, deleted " + count);
        if(count == 1) getViewState().setResult();
        else getViewState().showMessage("Not deleted, something is bad");
    }

}
